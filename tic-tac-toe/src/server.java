import java.net.*; // for Socket, ServerSocket, and InetAddress
import java.io.*; // for IOException and Input/OutputStream

public class server {

	public static void main(String[] args) throws IOException {

		if (args.length != 1) // Test for correct # of args
			throw new IllegalArgumentException("Parameter(s): <Port>");

		int servPort = Integer.parseInt(args[0]);

		// Create a server socket to accept client connection requests
		ServerSocket servSock = new ServerSocket(servPort);
		data1 toSend=null, toReceive;
		SocketAddress clientAddress;
		int port;
		System.out.println("server waiting...... ");
		
		while (true) { // Run forever, accepting and servicing connections
			Socket clntSock = servSock.accept(); // Get client connection		  
			clientAddress = clntSock.getRemoteSocketAddress();
			port = clntSock.getPort();
			System.out.println("Handling client at " + clientAddress + " with port# " + port);
			// Receive until client closes connection, indicated by -1 return
			try {
				while (true ) {
					
					int now = 0; 
					
					InputStream is = clntSock.getInputStream();  
				    ObjectInputStream ois = new ObjectInputStream(is);  
					toReceive = (data1)ois.readObject();
					System.out.println("From client ( " + clientAddress + ") : " + toReceive.toString());
					OutputStream os = clntSock.getOutputStream();  
				    ObjectOutputStream oos = new ObjectOutputStream(os);  
				    
				    String let = data1.sepL(toReceive.toString()); 
				    Integer n = data1.sepFir(toReceive.toString()); 
				    Integer num = data1.sepSec(toReceive.toString()); 
				    		
				    Integer odds = Integer.parseInt(toReceive.getName()); 
				    //check if valid pos
				    if(!toReceive.isValid(let, n, num, odds)){
					    toSend = new data1(toReceive.getName(),"invalid", toReceive.getBoard());
					    now = 1; 
				    }
				    else{
				    	toReceive.updateBoard(let, n, num);
				    	toSend = new data1(toReceive.getName(),toReceive.getData(), toReceive.getBoard());
				    }
				    //check if board is full
				    if(toReceive.fullBoard()){
				    	toSend = new data1(toReceive.getName(),"draw", toReceive.getBoard());
				    	now = 1; 
				    }
				    //check if board is won by user
				    if(toReceive.getWinner()){
				    	toSend = new data1(toReceive.getName(),"user", toReceive.getBoard());
				    	now = 1; 
				    }

				    
				    if(now!=1){
					    //Server
				    	Integer pos; 
					    if (odds == 1){
					    	pos = 0;
					    }
					    else{
					    	pos = 1; 
					    }
					    String[] serve = toReceive.serverMove(pos); 
					    String letterReal = serve[0]; 
					    Integer B = Integer.parseInt(serve[1]); 
					    Integer Guess = Integer.parseInt(serve[2]); 

					    toReceive.updateBoard(letterReal, B, Guess);
					    if(toReceive.fullBoard()){
					    	toSend = new data1(toReceive.getName(),"draw", toReceive.getBoard());
					    }
					    //check if board is won by comp.
					    System.out.println(toReceive.getWinner());
					    if(toReceive.getWinner()){
					    	toSend = new data1(toReceive.getName(),"comp", toReceive.getBoard());
					    }
					    toSend = new data1(toReceive.getName(),toReceive.getData(), toReceive.getBoard());
					    now = 0; 
				    }
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
