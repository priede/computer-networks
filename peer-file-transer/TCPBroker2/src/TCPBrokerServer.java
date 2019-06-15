import java.net.*; // for Socket, ServerSocket, and InetAddress
import java.util.ArrayList;
import java.io.*; // for IOException and Input/OutputStream

public class TCPBrokerServer {

	public static void main(String[] args) throws IOException {

		if (args.length != 1) // Test for correct # of args
			throw new IllegalArgumentException("Parameter(s): <Port>");

		int servPort = Integer.parseInt(args[0]); //5555

		// Create a server socket to accept client connection requests
		ServerSocket servSock = new ServerSocket(servPort);
		echoData toSend=null, toReceive;
		SocketAddress clientAddress;
		int port;
		System.out.println("server waiting...... ");
		
		ArrayList<ArrayList> DB = new ArrayList<ArrayList>();
		
		while (true) { // Run forever, accepting and servicing connections
			Socket clntSock = servSock.accept(); // Get client connection		  
			clientAddress = clntSock.getRemoteSocketAddress();
			port = clntSock.getPort();
			//System.out.println("Handling client at " + clientAddress + " with port# " + port);
			// Receive until client closes connection, indicated by -1 return
			
			try {
				while (true ) {
					String found = ""; 
					ArrayList<ArrayList> tosender = new ArrayList<ArrayList> (); 
					
					InputStream is = clntSock.getInputStream();  
				    ObjectInputStream ois = new ObjectInputStream(is);  
					toReceive = (echoData)ois.readObject();
					//System.out.println("From client ( " + clientAddress + ") : " + toReceive.toString());
//					if (toReceive.getRequest() == -2) {
//						System.out.println(toReceive.brokerReceived()); 
//						//user wants to register filename
//						DB = toReceive.rateFile(DB);
//						System.out.println(DB);
//						found = "file rated"; 
//					}
					if (toReceive.getRequest() == -1) {
						//System.out.println(toReceive.brokerReceived()); 
						//user wants to unregister filename
						found = toReceive.unregisterFile(DB);
						System.out.println(DB);
						//found = "file unregistered"; 
					}
					if (toReceive.getRequest() == 1) {
						System.out.println(toReceive.brokerReceived()); 
						//user wants to register filename
						DB = toReceive.registerFile(DB);
						System.out.println(DB);
						found = "file registered"; 
					}
					if (toReceive.getRequest() == 0){
						//user is requesting 
						System.out.println("user is requesting: " + toReceive.brokerReceived()); 
						//user wants to register filename
						found = toReceive.requestFile(DB);
						//System.out.println("broker says: " + found);
					}
					if (toReceive.getRequest() == 2){
						//user is requesting user file info (!!) 
						System.out.println("user is requesting: " + toReceive.brokerReceived()); 
						
	
//						tosender = toReceive.userInfo1(DB);
//						System.out.println(tosender);
						found = toReceive.userInfo(DB);
						//System.out.println("broker says: " + found);
					}
								
					OutputStream os = clntSock.getOutputStream();  
				    ObjectOutputStream oos = new ObjectOutputStream(os);  
				    toSend = new echoData(found, 0, "filename", "IP", 4444, tosender);
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
