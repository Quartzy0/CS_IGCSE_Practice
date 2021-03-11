package com.server;

import java.io.*;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class ClientVoteThread extends Thread{
    private Socket clientSocket;
    private Server serverInstance;
    private String[] candidates;
    
    public ClientVoteThread(Socket clientSocket, Server server, String[] candidates){
        super("Client Socket");
        this.clientSocket = clientSocket;
        this.serverInstance = server;
        this.candidates = candidates;
        setDaemon(true);
    }
    
    @Override
    public void run(){
        try{
            DataInputStream inputStream = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream outputStream = new DataOutputStream(clientSocket.getOutputStream());
            
            //Send student list of candidates
            StringBuilder sToSend = new StringBuilder();
            for(int i = 0; i < candidates.length; i++){
                if(i!=0){
                    sToSend.append("," + candidates[i]);
                }else{
                    sToSend.append(candidates[i]);
                }
            }
            outputStream.writeUTF(sToSend.toString());
            outputStream.flush();
            
            //Get students response
            byte studentNum = inputStream.readByte();
            byte candidate = inputStream.readByte();
            
            outputStream.writeBoolean(!serverInstance.hasVoted(studentNum));
            
            serverInstance.setStudentVote(studentNum, candidate);
            
            outputStream.flush();
            
            clientSocket.close();
        } catch(IOException e){
            e.printStackTrace();
        }
    }
}
