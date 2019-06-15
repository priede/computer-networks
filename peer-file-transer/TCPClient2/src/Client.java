
/**
* 	This simulates a very simple version of FTP protocol. The client requests the name
*	of the file; the server sends the contents of the file. The name of the file is
*	passed to the program as the command argument. 
*/

import java.net.*;
import java.io.*;

public class Client {
	Socket sock;
	OutputStream sendStream;
	InputStream recvStream;
	String request;
	String response;
	String fileName;
	Boolean noFile=false;

	Client(String server, int port, String fileName) throws IOException, UnknownHostException {
		this.fileName = fileName;
		sock = new Socket(server, port);
		sendStream = sock.getOutputStream();
		recvStream = sock.getInputStream();
	}// End constructor
	
	public Socket getSock() {
		return sock; 
	}
	void makeRequest() {
		request = fileName;
	}// End makeRequest

	void sendRequest() {
		try {
			byte[] sendBuff = new byte[request.length()];
			sendBuff = request.getBytes();
			System.out.println("sending filename to server...");
			sendStream.write(sendBuff, 0, sendBuff.length);
			System.out.println("sent filename...");
		} catch (IOException ex) {
			System.err.println("IOException in sendRequest");
		}
	} // void SendRequest

	void getResponse() throws IOException {
		try {
			int dataSize = 9999999; //big but is it big enough? for this version ENTIRE file is one PACKET
			//int dataSize = Integer.MAX_VALUE; //big but is it big enough? for this version ENTIRE file is one PACKET
			byte[] recvBuff = new byte[dataSize];
			recvStream.read(recvBuff, 0, dataSize);
			response = new String(recvBuff, 0, dataSize);
			if (response.contains("File Does Not Exist")){
				System.out.println("no file");
				noFile = true;
			}
		}catch (SocketException e) {
			System.out.println("Connection closed prematurely");
			sock.close();

		} catch (IOException ex) {
			System.err.println("IOException in getResponse");
		}
	} // End getResponse

	void useResponse() {
		if (!noFile){
		 ClientUser user = new ClientUser();
		 user.store(fileName, response);
		}
		else{
			System.out.println("Remember, File Does Not Exist");
		}
	}// End useResponse

	void close() {
		try {
			sendStream.close();
			recvStream.close();
			sock.close();
		} catch (IOException ex) {
			System.err.println("IOException in close");
		}
	} // End close

}// End of TCPClient class