/*
 * This is the SERVER waiting for Datagram (UDP) packets from a client
 * using Datagram (UDP) Packets and Serializable Object
 * Client Sends Serializable Object BUT Server sends a String
 */
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.Random;

public class Server {
  private static final int ECHOMAX = 255; // Maximum size of echo datagram
  public static void main(String[] args) throws IOException, ClassNotFoundException {

    if (args.length != 1) { // Test for correct argument list
      throw new IllegalArgumentException("Parameter(s): <Port>");
    }
    int servPort = Integer.parseInt(args[0]);
    byte[] recvBuf = new byte[ECHOMAX];
    DatagramSocket socket = new DatagramSocket(servPort);
    DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
    System.out.println("I am waiting...... ");
    
    //Generate the Servers Random Number
    Random rand = new Random();
    int serverNum = rand.nextInt(100) + 1;
    System.out.println("serverNum: " + serverNum);
    
    Integer count = 0; 
    
    while (true) { // Run forever, receiving and echoing datagrams from any client
      socket.receive(packet); // Receive packet from client
      System.out.println("Handling client at " + packet.getAddress().getHostAddress() + " on port " + packet.getPort());
      int byteCount2 = packet.getLength();
	    ByteArrayInputStream byteStream2 = new ByteArrayInputStream(recvBuf);
	    ObjectInputStream is = new ObjectInputStream(new BufferedInputStream(byteStream2));
	    Data data = (Data) is.readObject();
	    
	    data.numFromServer(serverNum);
	    data.counter(count);
	    
	    //System.out.println("Entry: " + data.getData());
	    byte[] buf=data.getData().getBytes();  //byte array for data to SEND to server
	    DatagramPacket sendPacket = new DatagramPacket(buf,buf.length, packet.getAddress(), packet.getPort());
	    socket.send(sendPacket);  //Send the input line back to client
	    count++; 
      packet.setLength(ECHOMAX); // Reset length to avoid shrinking buffer
    }
    /* NOT REACHED */
  }
}
