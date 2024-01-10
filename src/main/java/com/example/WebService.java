package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

public class WebService extends Thread{
    Socket socket;

    public WebService(Socket s){
        this.socket = s;
    }

    public void run() {
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            String message;
            message = in.readLine();
            String[] stringhe = message.split(" ");
            String path = stringhe[1].substring(1);

            while(!message.isEmpty()){
                System.out.println(message + "\n");
                message = in.readLine();
            }

            File file;
            if(path.equals("")){
                file = new File("test/index.html");
            }
            else{
                file = new File("test/" + path);
            }
            
            try{
                if(file.exists()){
                    sendBinaryFile(socket, file);
                }
                else{
                    String stringa = "Il file non esiste";
                    out.writeBytes("HTTP/1.1 404 Not Found\n");
                    out.writeBytes("Content-Length: " + stringa.length() + "\n");
                    out.writeBytes("Content-Type: text/plain\n");
                    out.writeBytes("\n");
                    out.writeBytes(stringa);
                }
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
            socket.close();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    private static void sendBinaryFile(Socket socket, File file) throws IOException{
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeBytes("HTTP/1.1 200 OK\n");
        out.writeBytes("Content-Length: " + file.length() + "\n");

        out.writeBytes("Content-Type: " + chooseType(file) + "\n");
        
        out.writeBytes("\n");
        InputStream input = new FileInputStream(file);
        byte[] buf = new byte[8192];
        int n;
        while((n = input.read(buf)) != -1){
            out.write(buf, 0, n);
        }
        input.close();
    }

    private static String chooseType(File file){
        String contentType = "";
        String path = file.getName();
        String[] stringhe = path.split("\\.");
        String extension = stringhe[1];

        switch (extension) {
            case "png":
                contentType = "image/png";
                break;
            case "html":
                contentType = "text/html";
                break;
            case "css":
                contentType = "text/css";
                break;
            case "js":
                contentType = "application/javascript";
                break;
        }

        return contentType;
    }
}
