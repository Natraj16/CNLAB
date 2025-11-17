package CN;

import java.io.*;
import java.net.*;
public class client
{
	public static void main(String[] args) {
		Socket socket=null;
		try{
		    socket=new Socket("localhost",9876);
		    System.out.println("Connected to server");
		    BufferedReader inFromServer=new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    PrintWriter outToServer=new PrintWriter(socket.getOutputStream(),true);
		    BufferedReader userInput=new BufferedReader(new InputStreamReader(System.in));
		    
		    System.out.println("Enter the file to be reuqested from the server");
		    String fileName=userInput.readLine();
		    
		    outToServer.println(fileName);
		    
		    String serverResponse=inFromServer.readLine();
		    
		    if("FILE_FOUND".equals(serverResponse)){
		        System.out.println("File Found! Extracting file from the Server");String line;
		        while((line=inFromServer.readLine())!=null){
		            System.out.println(line);
		            
		        }
		    }
		    else if("FILE_NOT_FOUND".equals(serverResponse)){
		        System.out.println("File not found");
		    }
		}
		catch(IOException e){
		    e.printStackTrace();
		   
		}finally{
		    try{
		        if(socket!=null){
		            socket.close();
		            
		        }
		    }
		    catch(IOException e){
		        e.printStackTrace();
		    }
		}
	}
}
