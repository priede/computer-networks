/**
* 	This is a TCP server program that receives the name of a file from the client. 
*   It then reads the contents of
*	the file as a string and sends the string back to the file. The job of 
*	reading the contents of the file and changing it to a string is done by a 
*	helping class named Processor. 
*/

import java.net.*;
import java.io.*;

public class Server 
{
	Socket sock;
	InputStream recvStream;
	OutputStream sendStream; 
	String request;
	String response;
	
	public Server (Socket s) throws IOException, UnknownHostException
	{  
		sock = s;  
		recvStream = sock.getInputStream ();  
		sendStream = sock.getOutputStream ();
	} // End constructor
	
	void getRequest ()    
	{   
		try  
		{
			int dataSize;
			while ((dataSize = recvStream.available ()) == 0);
			byte [] recvBuff = new byte [dataSize];
			recvStream.read (recvBuff, 0, dataSize);
			request = new String (recvBuff, 0, dataSize);   
		} 
		catch (IOException ex)  
		{
			System.err.println ("IOException in getRequest");  
		}
	}  
	
	void process()    
	{
		ServerProcessor processor = new ServerProcessor();
		response =  processor.getContents(request);	
	}  
	
	void sendResponse ()   
	{  
		try   
		{
			if (response == null){
				byte [] sendBuff = new byte [100];
				sendBuff = "File Does Not Exist\n".getBytes ();
				sendStream.write (sendBuff, 0, sendBuff.length); 
				return;
			}
			byte [] sendBuff = new byte [response.length ()];
			sendBuff = response.getBytes ();
			sendStream.write (sendBuff, 0, sendBuff.length);  
			sendStream.flush();
		}  
		catch (IOException ex)  
		{
			System.err.println ("IOException in sendResponse");   
		}
	}
	
	void close ()   
	{  
		try  
		{
			recvStream.close ();                  
			sendStream.close ();
			sock.close ();  
		}  
		catch (IOException ex)
		{
			System.err.println ("IOException in close");
		}
	}  
	
/*
	public static void main (String [] args) throws IOException   
	{
		final int port = Integer.parseInt (args[0]);
		ServerSocket listenSock = new ServerSocket (port);
		while (true)
		{
			System.out.println("server ready...");
			Server server = new Server (listenSock.accept ());
			server.getRequest ();    
			System.out.println("got request...");
			server.process ();
			System.out.println("process...");
			server.sendResponse ();
			System.out.println("sending to client...");
			server.close ();  
			System.out.println("server done...");
		}

	} // End of main 
*/
	
} // End of TCPServer class