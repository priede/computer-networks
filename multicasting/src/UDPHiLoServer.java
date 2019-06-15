/*
 * This is the SERVER waiting for Datagram (UDP) packets from a client
 * using Datagram (UDP) Packets and Serializable Object
 * Client Sends Serializable Object BUT Server sends a String
 */
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

public class UDPHiLoServer {
  private static final int ECHOMAX = 255; // Maximum size of echo datagram
  public static void main(String[] args) throws Exception {
	  BufferedReader fromKeyboard = new BufferedReader(new InputStreamReader(System.in)); //from keyboard
    if (args.length != 1) { // Test for correct argument list
      throw new IllegalArgumentException("Parameter(s): <Port>");
    }
	String multicast = "224.0.0.0"; 
	int port = 9999; 
	
    int countPlayer = 0; 
    ConnectionData data; 
    String gameChat = ""; 
    String gamesOpen = " ";
    String multiPorts = "";
    
    ArrayList<ArrayList> main = new ArrayList<ArrayList>();
	ArrayList<String> pair = new ArrayList<String>();
	pair.add(multicast);
	pair.add(Integer.toString(port));
	main.add(pair);
    
    int servPort = Integer.parseInt(args[0]);
    byte[] recvBuf = new byte[ECHOMAX];
    DatagramSocket socket = new DatagramSocket(servPort);
    DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
    System.out.println("I am waiting for two players to join the game...... ");
    while (true) { 
    		socket.receive(packet); 
    		//countPlayer++; 
      		System.out.println("Handling client " + countPlayer+ " at " + 
    		packet.getAddress().getHostAddress() + " on port " + packet.getPort());
      		
	        int byteCount2 = packet.getLength();
		    ByteArrayInputStream byteStream2 = new ByteArrayInputStream(recvBuf);
		    ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(byteStream2));
		    ByteArrayOutputStream byteStreamO = new ByteArrayOutputStream(ECHOMAX);
		    ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(byteStreamO));
		    String readIN = (String) is.readObject(); 
		    
		    if(readIN.equals("game")){
		    	countPlayer++;
		    	gameChat = "game";
		    	if(countPlayer > 2){
			    	multicast = "224.0.0.12"; 
			    	port = 8888; 
			    	pair.add(multicast);
			    	pair.add(Integer.toString(port));
			    	main.add(pair);
			    }
			    if(countPlayer > 4){
			    	multicast = "224.0.0.14"; 
			    	port = 7777; 
			    	pair.add(multicast);
			    	pair.add(Integer.toString(port));
			    	main.add(pair);
			    }
			    if(countPlayer > 6){
			    	multicast = "224.0.0.16"; 
			    	port = 6666; 
			    	pair.add(multicast);
			    	pair.add(Integer.toString(port));
			    	main.add(pair);
			    }
			    if(countPlayer > 8){
			    	multicast = "224.0.0.18"; 
			    	port = 6655; 
			    	pair.add(multicast);
			    	pair.add(Integer.toString(port));
			    	main.add(pair);
			    }
		    	System.out.println("gameer!");
		    }
		    else if(readIN.equals("chatters")){
		    	gameChat = "chat"; 
		    	multicast = "224.0.0.99"; 
		    	port = 9998; 
		    	if(countPlayer <= 2){
		    		gamesOpen = "Room 1";
		    		//multiPorts = "224.0.0.0, 9999";
		    		
		    	}
		    	else if(countPlayer > 2){
			    	gamesOpen = "Room 1, Room 2";
			    	//multiPorts = multiPorts + " | m: 224.0.0.12 | p: 8888" ;
			    }
		    	else if(countPlayer > 4){
			    	gamesOpen = "Room 1, Room 2, Room 3";
			    	//multiPorts = multiPorts + " | m: 224.0.0.14 | p: 7777" ;
			 
			    }
		    	else if(countPlayer > 6){
			    	gamesOpen = "Room 1, Room 2, Room 3, Room 4";
			    	//multiPorts = multiPorts + " | m: 224.0.0.16 | p: 6666" ;
			    	
			    }
		    	else if(countPlayer > 8){
			    	gamesOpen = "Room 1, Room 2, Room 3, Room 4, Room 5";
			    	//multiPorts = multiPorts + " | m: 224.0.0.16 | p: 6655" ;
			    }
		    	System.out.println("chatter!");
		    	System.out.println(gamesOpen);
		    	System.out.println(countPlayer);
		    	//data = new ConnectionData(multicast, port, countPlayer, gameChat, gamesOpen);
		    }
			    
			data = new ConnectionData(multicast, port, countPlayer, gameChat, gamesOpen);
			os.writeObject(data);
			os.flush(); 
			byte[] sendBuffer1 = byteStreamO.toByteArray(); 
			DatagramPacket packetSend = new DatagramPacket(sendBuffer1, sendBuffer1.length,packet.getAddress(), packet.getPort()); 
			socket.send(packetSend);
		    packet.setLength(ECHOMAX); // Reset length to avoid shrinking buffer
		}
    }
  }
