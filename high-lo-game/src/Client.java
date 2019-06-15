/*
 * This is an example of a CLIENT conversing with a server
 * using Datagram (UDP) Packets and Serializable Object
 * Client Sends Serializable Object BUT receives a String
 */
import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
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

public class Client {
  private static final int ECHOMAX = 255; // Maximum size of echo datagram
  public static void main(String[] args) throws IOException {
    if ((args.length != 2) ) { // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Server> <Port>");
    }
    ArrayList<String> obj = new ArrayList<String>();
    DatagramSocket clientSocket = new DatagramSocket(); //for network
	  BufferedReader fromKeyboard = new BufferedReader(new InputStreamReader(System.in)); //from keyboard
    InetAddress serverAddress = InetAddress.getByName(args[0]);  // Server address
    int servPort = Integer.parseInt(args[1]);  //get port number
    String clientInput;  //from user
    String echoString;   //echo from server
    String name;
    Data toSend;
    
    Integer count = 0; 
    
   	System.out.println("HEY! What's your name? ");
   	clientInput = fromKeyboard.readLine();

    while(true) {

		if (clientInput.equals("exit"))  //user want to stop having fun with the server
			break;
		byte[] buf=clientInput.getBytes();  //byte array for data to SEND to server
		
		//System.out.println("FROM CONSOLE (size of input string is " + buf.length + "): " + clientInput);
		toSend = new Data(clientInput);
		//System.out.println(toSend.toString());
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream(ECHOMAX);
		ObjectOutputStream os = new ObjectOutputStream(new BufferedOutputStream(byteStream));
		os.flush();
		os.writeObject(toSend);
		os.flush();
        //retrieves byte array
		byte[] sendBuf = byteStream.toByteArray();  //send an Object
		DatagramPacket sendPacket = new DatagramPacket(sendBuf, sendBuf.length, serverAddress, servPort);
		int byteCount = sendPacket.getLength();
		clientSocket.send(sendPacket);
		//System.out.println("packet sent of size: " + byteCount);
		os.close();
		//now to receive echo from server
		byte[] recvBuf = new byte[256];  //receive a String back
	    DatagramPacket receivePacket = new DatagramPacket(recvBuf,recvBuf.length);
	    clientSocket.receive(receivePacket); //receive it
	    echoString = new String(receivePacket.getData(),0,receivePacket.getLength());
	    
	    if(count == 0){
			System.out.println("FROM Server: Hi " + echoString + "! Let's play a game ... Guess a number between 1 - 100: ");
			clientInput = fromKeyboard.readLine();
			count++;
		}
		else{
			System.out.println("FROM Server: " + echoString);
			if (echoString.contains("You have correctly guessed the number!")){
				System.out.println("Thanks for playing!");
				break; 
			}
			clientInput = fromKeyboard.readLine();
			count++; 
		}
	    
		obj.add(clientInput);
		}

    System.out.println("List of echo strings from server is:" + obj);
	clientSocket.close();
	}
}
