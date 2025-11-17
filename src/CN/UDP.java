package CN;

public class UDP {
	import java.net.*;

	import java.io.*;
	public class UDP_Server {
	public static void main(String[] args) throws IOException {
	// Server creates a socket and binds it to port 9876 and listens on port 9876
	DatagramSocket serverSocket = new DatagramSocket(9876);
	//Buffer (1 KB) to hold incoming data.
	byte[] receiveBuffer = new byte[1024];
	byte[] sendBuffer;
	BufferedReader reader = new BufferedReader(new
	InputStreamReader(System.in));
	System.out.println(&quot;Server started. Waiting for client messages...&quot;);
	while (true) {

	// Receiving data from client -Server waits (blocking) until client sends a message.
	Once received, it is stored in receivePacket
	DatagramPacket receivePacket = new DatagramPacket(receiveBuffer,
	receiveBuffer.length);
	//Server waits (blocking) until client sends a message.
	serverSocket.receive(receivePacket);

	//Converts received bytes into a readable string and prints it.
	String clientMessage = new String(receivePacket.getData(), 0,
	receivePacket.getLength());
	System.out.println(&quot;Client: &quot; + clientMessage);
	// Server typing and sending response
	System.out.print(&quot;Server: &quot;);
	String serverMessage = reader.readLine();

	//Reply is converted into bytes.

	sendBuffer = serverMessage.getBytes();
	//Uses client’s IP and port (extracted from received packet) to send the reply
	back.
	InetAddress clientAddress = receivePacket.getAddress();
	int clientPort = receivePacket.getPort();
	DatagramPacket sendPacket = new DatagramPacket(sendBuffer,
	sendBuffer.length, clientAddress, clientPort);
	// Send the message to client
	serverSocket.send(sendPacket);
	// Exit on typing &quot;exit&quot;

	if (serverMessage.equalsIgnoreCase(&quot;exit&quot;)) {
	System.out.println(&quot;Server exited.&quot;);
	break;
	}
	}
	serverSocket.close();
	}
	}

	UDP_Receiver.java (Client)
	import java.net.*;
	import java.io.*;
	public class UDP_Receiver {
	public static void main(String[] args) throws IOException {
	//Client creates a UDP socket.
	DatagramSocket clientSocket = new DatagramSocket();
	//Looks up server’s IP address (here &quot;localhost&quot; → same machine).
	InetAddress serverAddress = InetAddress.getByName(&quot;localhost&quot;);

	byte[] sendBuffer;
	byte[] receiveBuffer = new byte[1024];
	BufferedReader reader = new BufferedReader(new
	InputStreamReader(System.in));

	while (true) {
	System.out.print(&quot;Client: &quot;);
	//Reads user input, converts to bytes, and sends a datagram to server:9876.
	String clientMessage = reader.readLine();
	sendBuffer = clientMessage.getBytes();
	DatagramPacket sendPacket = new DatagramPacket(sendBuffer,
	sendBuffer.length, serverAddress, 9876);
	// Send the message to the server
	clientSocket.send(sendPacket);
	// Receiving data from server
	DatagramPacket receivePacket = new DatagramPacket(receiveBuffer,
	receiveBuffer.length);
	// Block until data is received
	clientSocket.receive(receivePacket);

	//Converts reply bytes into string and displays it.
	String serverMessage = new String(receivePacket.getData(), 0,
	receivePacket.getLength());
	System.out.println(&quot;Server: &quot; + serverMessage);

	// Exit on typing &quot;exit&quot;

	if (clientMessage.equalsIgnoreCase(&quot;exit&quot;)) {
	System.out.println(&quot;Client exited.&quot;);
	break;
	}
	}
	clientSocket.close();
	}
	}