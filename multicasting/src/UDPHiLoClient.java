/*
 * This is an example of a CLIENT conversing with a server
 * using Datagram (UDP) Packets and Serializable Object
 * Client Sends Serializable Object BUT receives a String
 */
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable; 
								
public class UDPHiLoClient {
	private static final int ECHOMAX = 255; // Maximum size of echo datagram
	
	public static void main(String[] args) throws Exception {
	    if ((args.length != 2) ) { // Test for correct # of args
	      throw new IllegalArgumentException("Parameter(s): <Server> <Port>");
	    }
	    DatagramSocket clientSocket = new DatagramSocket(); //for network
		BufferedReader fromKeyboard = new BufferedReader(new InputStreamReader(System.in)); //from keyboard
	    InetAddress serverAddress = InetAddress.getByName(args[0]);  // Server address
	    int servPort = Integer.parseInt(args[1]);  //get port number
	    String clientInput;  //from user
	    
	    peerUtilites peer; 
	    peerSendThread PST;
		peerReceiveThread PRT;
		
	    chatSendThread PST2;
		chatReceiveThread PRT2;
		
		Integer setNum = 0;
		Integer randNum = 0;
		
		Integer roundNum = 1;
	    
	    int connect = 0; 
	    while(true) {
	    	System.out.print("Join game or chatters? : ");
	    	clientInput = fromKeyboard.readLine();
	    	
			if (clientInput.equals("exit"))  //user want to stop having fun with the server
				break;
	    	
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream(ECHOMAX);
			ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(byteStream));
			os.flush();
			os.writeObject(clientInput);
			os.flush();
	        //retrieves byte array
			byte[] sendBuf = byteStream.toByteArray();  //send an Object
			DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, serverAddress, servPort);
			int byteCount = sendPacket.getLength();
			clientSocket.send(sendPacket);

			os.close();

			byte[] recvBuf = new byte[256];  //receive a String back
		    DatagramPacket receivePacket = new DatagramPacket(recvBuf,recvBuf.length);
		    clientSocket.receive(receivePacket); //receive it
		    ByteArrayInputStream byteStream1 = new ByteArrayInputStream(recvBuf);
			ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(byteStream1));
			
			
			ConnectionData data = (ConnectionData) is.readObject();

			String multicast = data.getmulticast(); 
			int port = data.getport(); 
			int playernum = data.getplayernum();
			
			while(true) {
				
				if(data.getG().equals("game")){
					
					while(!clientInput.equals("quit")){
						peer = new peerUtilites(multicast,port, playernum);
							
						if(roundNum > 0){
							playernum = data.getplayernum();
							if (data.getplayernum() == 1){
								data.setplayernum(2);
							}
							else if (playernum == 2){
								data.setplayernum(1);
							}
							if (data.getplayernum() == 3){
								data.setplayernum(4);
							}
							else if (playernum == 4){
								data.setplayernum(3);
							}
							if (data.getplayernum() == 5){
								data.setplayernum(6);
							}
							else if (playernum == 6){
								data.setplayernum(5);
							}
							if (data.getplayernum() == 7){
								data.setplayernum(8);
							}
							else if (playernum == 8){
								data.setplayernum(7);
							}
							if (data.getplayernum() == 8){
								data.setplayernum(9);
							}
							else if (playernum == 9){
								data.setplayernum(8);
							}
						}
						roundNum++;
							
						if(roundNum == 2){
							peer.setName();
						}
						peer.joinGroup();
	
						System.out.println("I am player: "+ playernum);
						System.out.println("The multicast group number is " + multicast);
						System.out.println("The port number is " + port);
						connect++;  
						
						if (playernum == 1 || playernum == 3 || playernum == 5 || playernum == 7 || playernum == 9) {
							
							if((playernum == 1 || playernum == 3 || playernum == 5 || playernum == 7 || playernum == 9)) {
								System.out.println("Set number ...");
								peer.setNumber();
								//setNum = 1;
							}
							
							//int rand = Integer.parseInt(peer.getNumber()); 
							
							PST = new peerSendThread(peer, playernum, Integer.parseInt(peer.getNumber()));
							Thread T = new Thread(PST);
							T.start();
							PRT = new peerReceiveThread(peer, playernum, Integer.parseInt(peer.getNumber()));
							Thread TT = new Thread(PRT);
							TT.setDaemon(true);
							TT.start();
							T.join();
							
						}
						else {
							int rand = 0; 
							if (setNum == 0){
								System.out.println("enter a number guess: ");
								setNum = 1;
							}
							PST = new peerSendThread(peer, playernum, Integer.parseInt(peer.getNumber()));
							Thread T = new Thread(PST);
							T.start();
							PRT = new peerReceiveThread(peer, playernum, Integer.parseInt(peer.getNumber()));
							Thread TT = new Thread(PRT);
							TT.setDaemon(true);
							TT.start();
							T.join();
					}	
						
					System.out.println("Quit? ");
					clientInput = fromKeyboard.readLine();
					
				}}
					
				else{
					
					while(!clientInput.equals("quit")){
						peerUtilites peerChat = new peerUtilites(multicast, port, -1);
						peerChat.setName();
						peerChat.joinGroup();
						
						System.out.println(data.getGamesOpen()); 
						//System.out.println(data.getRooms());
						int rmNum = data.setRoom();
						String mNum = data.getM(rmNum); 
						int pNum = data.getP(rmNum); 
						
						peerUtilites peerListen = new peerUtilites(mNum, pNum, -1);
						//peerListen.setName();
						peerListen.joinGroup();
						
						//chatSendThread PST_listen;
						chatReceiveThread PRT_listen;
						PRT_listen = new chatReceiveThread(peerListen, peerListen);
						Thread TT_l = new Thread(PRT_listen);
						TT_l.setDaemon(true);
						TT_l.start();
						
						PST2 = new chatSendThread(peerChat);
						Thread T = new Thread(PST2);
						T.start();
						PRT2 = new chatReceiveThread(peerListen, peerChat);
						Thread TT = new Thread(PRT2);
						TT.setDaemon(true);
						TT.start();
						T.join();
						
						System.out.println("Quit? ");
						clientInput = fromKeyboard.readLine();				
				
			}}}}}}
