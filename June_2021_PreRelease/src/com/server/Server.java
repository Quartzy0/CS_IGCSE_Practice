package com.server;

import com.PrintUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Server{
    public int[] votes;
    public ServerSocket serverSocket;
    public String[] candidates;
    
    public synchronized boolean hasVoted(int student){
        return votes[student]!=-2;
    }
    
    public synchronized void setStudentVote(int student, int vote){
        if(votes[student]!=-2)return;
        votes[student] = vote;
        PrintUtil.info("Student %d voted for candidate %d", student+1, vote+1);
    
        boolean allVoted = true;
        for(int i = 0; i < votes.length; i++){
            if(votes[i]==-2){
                allVoted = false;
                break;
            }
        }
        if(allVoted){
            PrintUtil.info("Everyone has finished voting!");
        }
    }
    
    public void startVotingSystem(int studentAmount, String[] candidates1) throws IOException{
        votes = new int[studentAmount];
        this.candidates = candidates1;
        resetVotes();
        serverSocket = new ServerSocket(4538);
        Thread thread = new Thread("Server thread"){
            @Override
            public void run(){
                while(true){
                    try{
                        Socket clientSocket = serverSocket.accept();
                        new ClientVoteThread(clientSocket, Server.this, candidates).start();
                    } catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.setDaemon(true);
        thread.start();
    }
    
    public void resetVotes(){
        for(int i = 0; i < votes.length; i++){
            votes[i] = -2;
        }
    }
    
    public static String[] doElections(Server server, String[] candidates, String tutorGroup) throws IOException{
        PrintUtil.info("Students can now begin voting");
        PrintUtil.newLine();
    
        PrintUtil.info("You will be notified when all students have voted");
        PrintUtil.blockUntilPress("Press ENTER to stop the voting");

        int[] votes = server.votes;
    
        //Predefined votes for testing
//        int[] votes = new int[]{1, 1, 0, 0};
    
        StringBuilder candidateVotesStr = new StringBuilder();
        int nonAbstentionVotes = 0;
        int votesCount = 0;
        int[] candidateResults = new int[candidates.length];
    
        for(int i = 0; i < votes.length; i++){
            if(votes[i]>=-1){
                votesCount++;
                if(votes[i]>=0){
                    if(candidateResults[votes[i]]==0)candidateResults[votes[i]] = 1;
                    else candidateResults[votes[i]]++;
                    nonAbstentionVotes++;
                }
            }
        }
    
        for(int i = 0; i < candidateResults.length; i++){
            candidateVotesStr.append(candidates[i] + " - " + candidateResults[i] + " - " + Math.round((candidateResults[i]/((float) nonAbstentionVotes))*1000f)/10 + "%\n");
        }
    
        System.out.println(
                "_______________________________________\n" +
                        tutorGroup + " election results:\n\n" +
                        candidateVotesStr.toString() + "\n\n" +
                        votesCount + " total votes\n" +
                        nonAbstentionVotes + " non abstention votes\n" +
                        (votesCount-nonAbstentionVotes) + " abstentions\n" +
                        "_______________________________________\n"
        );
        
        //Detect is there is a tie
        int largestAmountOfVotes = 0;
        List<String> largestCandidates = new ArrayList<>();
        for(int i = 0; i < candidateResults.length; i++){
            if(candidateResults[i]>largestAmountOfVotes){
                largestAmountOfVotes = candidateResults[i];
                largestCandidates.clear();
                largestCandidates.add(candidates[i]);
            }else if(candidateResults[i]==largestAmountOfVotes){
                largestCandidates.add(candidates[i]);
            }
        }
        
        return largestCandidates.toArray(new String[largestCandidates.size()]);
    }
    
    public static void main(String[] args) throws IOException{
        String tutorGroup = PrintUtil.prompt("Enter the name of the tutor group:");
        int numberOfStudents = PrintUtil.promptInt("Please enter the number of students:");
        String[] candidates = PrintUtil.promptList("Please enter the names of the candidates (separated by commas):", 4, new Object[]{});
        
        PrintUtil.newLine(2);
        Server server = new Server();
        server.startVotingSystem(numberOfStudents, candidates);
        while(true){
            String[] strings = doElections(server, candidates, tutorGroup);
            if(strings.length==1)break;
            
            server.candidates = strings;
            candidates = strings;
            server.resetVotes();
            
            if(PrintUtil.option("There was a tie! Do you wish to vote again between the winners to break the tie?", 0, new String[]{"Yes", "No"})==1)break;
        }
        server.serverSocket.close();
        
        PrintUtil.newLine(2);
        if(candidates.length>1){
            StringBuilder winnersString = new StringBuilder();
            for(int i = 0; i < candidates.length; i++){
                if(i==0){
                    winnersString.append(candidates[i] + " and ");
                }else if(i==candidates.length-1){
                    winnersString.append(candidates[i]);
                }else{
                    winnersString.append(candidates[i] + ", ");
                }
            }
            PrintUtil.info("%s are the winners!", winnersString.toString());
        }else{
            PrintUtil.info("%s is the winner!", candidates[0]);
        }
    }
}
