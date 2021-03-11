package com.client;

import com.PrintUtil;

import java.io.*;
import java.net.Socket;

public class Client{
    public static void main(String[] args) throws IOException{
        Socket socket = new Socket("localhost", 4538);
        DataInputStream inputStream = new DataInputStream(socket.getInputStream());
        String[] candidates = inputStream.readUTF().split(",");
    
        int studentNumber = PrintUtil.promptInt("Enter your student number:")-1;
        int candidate = PrintUtil.option("Enter the name of who you are voting or leave blank and press ENTER", -1, candidates);
    
        DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
        outputStream.writeByte(studentNumber);
        outputStream.writeByte(candidate);
        outputStream.flush();
    
        boolean b = inputStream.readBoolean();
        if(b){
            PrintUtil.info("Your vote was successfully counted!");
        }else{
            PrintUtil.info("Your vote was not counted because you already voted.");
        }
    
        socket.close();
    }
}
