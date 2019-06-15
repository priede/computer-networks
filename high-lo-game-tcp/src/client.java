/*Alexandra Daisy Priede
 * 21 Sep. 2017
 * PS2
 */

import java.net.ConnectException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class client {

	public static void main(String[] args) throws IOException {

		  if ((args.length != 2) ) { // Test for correct # of args
		      throw new IllegalArgumentException("Parameter(s): <Server> <Port>");
		    }

		String server = args[0]; // Server name or IP address
		// from keyboard
		BufferedReader fromKeyboard = new BufferedReader(new InputStreamReader(System.in)); 
		ArrayList<String> obj = new ArrayList<String>();
		InetAddress serverAddress = InetAddress.getByName(args[0]); // Server
																	// address
		int servPort = Integer.parseInt(args[1]); // get port number

		String clientInput, name; // from user
		Socket socket = null;
		
		try {
		// Create socket that is connected to server on specified port
		socket = new Socket(serverAddress, servPort);
		data toSend, toReceive=null;
	    System.out.print("Hey, what's your name?: ");
	    name = fromKeyboard.readLine();
	    
		while (true) {
			System.out.print("Hey " + name + ", enter a number guess (1-100): ");
			clientInput = fromKeyboard.readLine();
			// user want to stop having fun with the server
			if (clientInput.equals("exit")) 
				break;
			//System.out.println("FROM CONSOLE (size of input string is " + sendbuf.length + "): " + clientInput);
			System.out.println("Connected to server...sending object data");
			toSend = new data(name,clientInput);
			//System.out.println("Client: " + toSend.toString());
		    OutputStream os = socket.getOutputStream(); 
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.flush();
			oos.writeObject(toSend);   //send object to server
			oos.flush();
			
		    // Receive an object back from the server
		    InputStream is = socket.getInputStream();  
		    ObjectInputStream ois = new ObjectInputStream(is);  
			try {
				toReceive = (data)ois.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}  
		    if (toReceive != null){
		    	System.out.println("from server: " + toReceive.toString());
		    	if(toReceive.toString().contains("you've guessed the number")){
		    		obj.add(toReceive.toString());
		    		break;
		    	}
		    }  		    
			obj.add(toReceive.toString());
			
		}
		System.out.println("List of echo strings from server is:" + obj);
		socket.close(); // Close the socket and its streams
		
	} catch (ConnectException e) {
		System.out.println("Connection refused, probably no server running");
	}
  }
}
