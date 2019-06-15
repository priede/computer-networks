import java.net.ConnectException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;

public class TCPEchoClient {

	public static void main(String[] args) throws IOException, InterruptedException {

		  if ((args.length != 2) ) { // Test for correct # of args
		      throw new IllegalArgumentException("Parameter(s): <Server> <Port>");
		    }
		String server = args[0]; 
		// from keyboard
		BufferedReader fromKeyboard = new BufferedReader(new InputStreamReader(System.in)); 
		ArrayList<String> obj = new ArrayList<String>();
		InetAddress serverAddress = InetAddress.getByName(args[0]); 
		int servPort = Integer.parseInt(args[1]);

		String clientInput, name; // from user
		Socket socket = null;
		
		String fileRequest; 
		
		Integer register;
		String fileName; 

		try {
		// Create socket that is connected to server on specified port
		
		echoData toSend, toReceive=null;
	    System.out.print("Enter your name: ");
	    name = fromKeyboard.readLine();
	    
		while (true) {
			
			System.out.print("Do you want to register, unregister or request a file, or wait: ");
			clientInput = fromKeyboard.readLine();
			// user want to stop having fun with the server
			if (clientInput.equals("exit")) 
				break;
			
			//System.out.println("Connected to server...sending object data");
			if(clientInput.equals("unregister")) {
				socket = new Socket(serverAddress, 4444);
				System.out.print("Enter file name to unregsiter: ");
				clientInput = fromKeyboard.readLine();
				register = -1; 
				fileName = clientInput; 
				toSend = new echoData(name, register, fileName, args[0], servPort , null);
				
				OutputStream os = socket.getOutputStream(); 
				ObjectOutputStream oos = new ObjectOutputStream(os);
				oos.flush();
				oos.writeObject(toSend);   //send object to server
				oos.flush();

			    // Receive from SERVER 
			    InputStream is = socket.getInputStream();  
			    ObjectInputStream ois = new ObjectInputStream(is);  
				try {
					toReceive = (echoData) ois.readObject();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}  
			    if (toReceive != null){
			    	System.out.println("from server: " + toReceive.toStringREG());
			    	toReceive.printResponse(); 
			    }  	
			    socket.close();
			}
			//System.out.println("Connected to server...sending object data");
			if(clientInput.equals("register")) {
				socket = new Socket(serverAddress, 4444);
				System.out.print("Enter file name to regsiter: ");
				clientInput = fromKeyboard.readLine();
				register = 1; 
				fileName = clientInput; 
				toSend = new echoData(name, register, fileName, args[0], servPort , null);
				
				OutputStream os = socket.getOutputStream(); 
				ObjectOutputStream oos = new ObjectOutputStream(os);
				oos.flush();
				oos.writeObject(toSend);   //send object to server
				oos.flush();
				
			    // Receive from SERVER 
			    InputStream is = socket.getInputStream();  
			    ObjectInputStream ois = new ObjectInputStream(is);  
				try {
					toReceive = (echoData) ois.readObject();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}  
			    if (toReceive != null){
			    	System.out.println("from server: " + toReceive.toStringREG());
			    	toReceive.printResponse(); 
			    }  	
			    socket.close();
			}
			if(clientInput.equals("request")) {
				socket = new Socket(serverAddress, 4444);
				System.out.print("Enter file name to request: ");
				clientInput = fromKeyboard.readLine();
				fileRequest = clientInput; 
				register = 0; 
				fileName = clientInput; 
				toSend = new echoData(name, register, fileName, args[0], servPort, null);
				
				OutputStream os = socket.getOutputStream(); 
				ObjectOutputStream oos = new ObjectOutputStream(os);
				oos.flush();
				oos.writeObject(toSend);   //send object to server
				oos.flush();
				
			    // Receive from BROKER 
			    InputStream is = socket.getInputStream();  
			    ObjectInputStream ois = new ObjectInputStream(is);  
				try {
					toReceive = (echoData) ois.readObject();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}  
			    if (toReceive != null){
				    //	System.out.println("from server: " + toReceive.toStringREG());
				    	toReceive.printArrayResponse(); 
			    }  	
			    
			    //Determine user to retrieve file from .. renamed variable to send
				System.out.print("From which user would you like to grab the file (or none): ");
				clientInput = fromKeyboard.readLine();
				if(!clientInput.equals("none")) {
					//socket.close();
					register = 2; //request user info for file 
					toSend = new echoData(clientInput, register, fileName, args[0], servPort, null);
					OutputStream os2 = socket.getOutputStream(); 
					ObjectOutputStream oos2 = new ObjectOutputStream(os2);
					oos2.flush();
					oos2.writeObject(toSend);   //send object to server
					oos2.flush();
					
					//Receive user info from BROKER .. renamed variable to send
				    InputStream is2 = socket.getInputStream();  
				    ObjectInputStream ois2 = new ObjectInputStream(is2);  
					try {
						toReceive = (echoData) ois2.readObject();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}  
					
					
				    if (toReceive != null){
					    	//System.out.println("from server: " + toReceive.toStringREG());
					    	String IPPORT = toReceive.printArrayResponse2(); 
					    	ArrayList aList= new ArrayList(Arrays.asList(IPPORT.split(",")));
					    	
					    	String IPnew = (String) aList.get(0);
					    	String Portnew = (String) aList.get(1);
					    	
					    	toReceive.readArray();
					    	System.out.println("file request : " + aList); 
					    	socket.close();
					    	
							class Multi extends Thread{ 
								private Client client; 
								public Multi(Client CLIENT) {
									this.client = CLIENT; 
								}
								public void run(){  
								 	
									try {
									
								 	System.out.println("connected to client...");
									client.makeRequest();
									System.out.println("make request done...");
									client.sendRequest();
									System.out.println("send request done...");
									
//									while(!clientInput.equals("new")) {
//										try {

											client.getResponse();
											System.out.println("got response done...");
											client.useResponse();
											System.out.println("if it exists, save the file locally done...");
											client.close();
											System.out.println("close stuff done..."); 	
								}  catch (NumberFormatException | IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}}
							}
							try {
								Client client = new Client(IPnew, Integer.parseInt(Portnew), fileRequest);
								Thread t = new Multi(client); 
								t.run();
							
//								System.out.println("Taking too long ? Type (new) to quit");
//								clientInput = fromKeyboard.readLine();
//								if(clientInp)
							}
							catch (Exception e) {
								System.out.println("Client with file is not available .. try again");
//								System.out.println("Giving user a bad rating ...");
//								
//								socket = new Socket(serverAddress, 4444);
//								register = -2; //register rating 
//								fileName = clientInput; 
//								ArrayList<ArrayList> d =  new ArrayList<ArrayList>();
//								ArrayList<String> ee = new ArrayList<String>();
//								d.add(ee);
//								
//								toSend = new echoData(Portnew, register, fileName, args[0], servPort , d);
//								
//								OutputStream os4 = socket.getOutputStream(); 
//								ObjectOutputStream oos4 = new ObjectOutputStream(os4);
//								oos4.flush();
//								oos4.writeObject(toSend);   //send object to server
//								oos4.flush();
//							
//								 // Receive from SERVER 
//							    InputStream is4 = socket.getInputStream();  
//							    ObjectInputStream ois4 = new ObjectInputStream(is4);  
//								try {
//									toReceive = (echoData) ois.readObject();
//								} catch (ClassNotFoundException e1) {
//									e1.printStackTrace();
//								}  
//							    if (toReceive != null){
//							   // 	System.out.println("from server: " + toReceive.toStringREG());
//							   // 	toReceive.printResponse(); 
//							    }  	
//								//socket.close();
//								System.out.print("Lack of response registered");
							}
							
							
//								
//							}
//							catch (NullPointerException e) {
//								System.out.print("Taking too long ? Keep waiting for client to send or (new)...: ");
//								clientInput = fromKeyboard.readLine();
//								if(clientInput.equals("new")) {
//									socket = new Socket(serverAddress, 4444);
//									register = -2; //register rating 
//									fileName = clientInput; 
//									ArrayList<ArrayList> d =  new ArrayList<ArrayList>();
//									ArrayList<String> ee = new ArrayList<String>();
//									d.add(ee);
//									toSend = new echoData(name, register, fileName, args[0], servPort , d);
//									
//									OutputStream os4 = socket.getOutputStream(); 
//									ObjectOutputStream oos4 = new ObjectOutputStream(os4);
//									oos4.flush();
//									oos.writeObject(toSend);   //send object to server
//									oos4.flush();
//								
//									socket.close();
//									System.out.print("Lack of response registered");
//									break;
//								}
//							}
//						}

				    }  	
				}
				
				socket.close();
				
			}  
			
			while(clientInput.equals("wait")) {

				Boolean t = false; 
				ServerSocket listenSock = new ServerSocket (servPort);
				System.out.println("server ready...");
				Server server1 = new Server (listenSock.accept ());
        			System.out.println("connected...");
				server1.getRequest ();    
				System.out.println("got request complete...");
				server1.process ();
				System.out.println("process complete...");
				server1.sendResponse ();
				System.out.println("sending to client complete...");
				server1.close ();    //closes tcp data socket to client => not persistent data
				System.out.println("server done...");
				server1.close();
				break; 
			}

		}
			
		socket.close();
	} catch (ConnectException e) {
		System.out.println("Connection refused, probably no server running");
	}
  }
}
