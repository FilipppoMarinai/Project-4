package com.example;

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
                WebService webService = new WebService(socket);
                webService.start();
            }
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }               
}
