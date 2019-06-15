/*Alexandra Daisy Priede
 * 21 Sep. 2017
 * PS2
 */

import java.net.*; // for Socket, ServerSocket, and InetAddress
import java.util.Random;
import java.io.*; // for IOException and Input/OutputStream

public class server {

	public static void main(String[] args) throws IOException {

		if (args.length != 1) // Test for correct # of args
			throw new IllegalArgumentException("Parameter(s): <Port>");

		int servPort = Integer.parseInt(args[0]);

		// Create a server socket to accept client connection requests
		ServerSocket servSock = new ServerSocket(servPort);
		data toSend=null, toReceive;
		SocketAddress clientAddress;
		int port;
		System.out.println("server waiting...... ");
		
	    Random rand = new Random();
	    int serverNum = rand.nextInt(100) + 1;
	    System.out.println("serverNum: " + serverNum);
		
		while (true) { // Run forever, accepting and servicing connections
			Socket clntSock = servSock.accept(); // Get client connection		  
			clientAddress = clntSock.getRemoteSocketAddress();
			port = clntSock.getPort();
			System.out.println("Handling client at " + clientAddress + " with port# " + port);
			// Receive until client closes connection, indicated by -1 return
			try {
				while (true ) {
					InputStream is = clntSock.getInputStream();  
				    ObjectInputStream ois = new ObjectInputStream(is);  
					toReceive = (data)ois.readObject();
					
					toReceive.numFromServer(serverNum); 
					
					System.out.println("From client ( " + clientAddress + ") : " + toReceive.toString());
					OutputStream os = clntSock.getOutputStream();  
				    ObjectOutputStream oos = new ObjectOutputStream(os);  
				    toSend = new data(toReceive.getName(), toReceive.getLoc());
				    oos.writeObject(toSend); 
				}
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (EOFException e) {  //needed to catch when client is done
				System.out.println("goodbye client at " + clientAddress + " with port# " + port);
				clntSock.close(); // Close the socket. We are done with this client!
			}
		}
		/* NOT REACHED */
	}
}
