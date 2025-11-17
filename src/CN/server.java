package CN;

import java.net.*;
import java.io.*;

public class server{
    public static void main (String[] args) {
   
        
        ServerSocket serverSocket= null;
        Socket clientSocket=null;
        
        try{
            serverSocket=new ServerSocket(9876);
            System.out.println("Server started...Waiting for client");
            
            clientSocket=serverSocket.accept();
            System.out.println("Client Connected");
            
            BufferedReader inFromClient=new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter outToClient= new PrintWriter(clientSocket.getOutputStream());
            
            String fileName=inFromClient.readLine();
            System.out.println("Client Requested File"+fileName);
            
            File file=new File(fileName);
            if(file.exists() && !file.isDirectory()){
                BufferedReader fileReader=new BufferedReader(new FileReader(file));String line;
                outToClient.println("FILE_FOUND");
                
                while ((line=fileReader.readLine())!=null){
                    outToClient.println(line);
                }
            
            fileReader.close();
            System.out.println("File sent successfully");}
            else{
                outToClient.println("FILE_NOT_FOUND");
                System.out.println("Requested file not found" );
        }
    }catch(IOException e){
        e.printStackTrace();}
        finally{
            
        
        try{
            if (clientSocket!=null)
                clientSocket.close();
            if (serverSocket!=null)
                serverSocket.close();
            }catch(IOException e){
                e.printStackTrace();
            } 
        }
    }
    }
