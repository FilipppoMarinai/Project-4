package com.example;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try{
            ServerSocket server = new ServerSocket(3000);

            while(true){
                Socket socket = server.accept();
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String message;
                message = in.readLine();
                String[] stringhe = message.split(" ");
                String path = stringhe[1].substring(1);
            

                while(!message.isEmpty()){
                    System.out.println(message + "\n");
                    message = in.readLine();
                }

                File document = new File(path);
                
                try{
                    if(document.exists()){
                        System.out.println("Il file esiste\n");
                    }
                    else{
                        System.out.println("Il file non esiste\n");
                    }
                }
                catch(Exception e){
                    System.out.println(e.getMessage());
                }
                socket.close();
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
}
