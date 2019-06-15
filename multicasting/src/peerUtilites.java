import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

public class peerUtilites{
	private MulticastSocket socket;
	private int PORT;
	private int ttl = 64; /* time to live */
	private InetAddress group; 
	private String IPadrress;
	private String name;
	private String number = "0"; 
	private String response; 
	private Integer end = 0; 
	private Integer playernum;
	private Integer setNum = 0;
	
	private Integer roomNum = 1;
	
	public peerUtilites(String IPadrress,int PORT, int playernum)throws Exception{
		this.PORT = PORT;
		this.IPadrress = IPadrress;
		/* instantiate a MulticastSocket */
		System.setProperty("java.net.preferIPv4Stack", "true");  //IMPORTANT IMPORTANT IMPORTANT
		this.socket = new MulticastSocket(PORT);
		/* set the time to live */
		socket.setTimeToLive(ttl);
		this.playernum = playernum;
		this.group = InetAddress.getByName(IPadrress);
	}
	public void setName() throws Exception {
	    System.out.print("Please enter a screen name ... ");
		name = readFromKeyboard();
	}
	public void setMandP(String m , int p){
		this.IPadrress = m;
		this.PORT = p;
	}
	public void setPlayerNum(int i){
		this.playernum = i; 
	}
	
	public Integer setRoom() throws NumberFormatException, Exception {
	    System.out.print("Please enter room number you'd like to join ... ");
		roomNum = Integer.parseInt(readFromKeyboard());
		return roomNum; 
	}
	
	public String getM(int i){
		String m = "";
		if (i == 1){
			m = "224.0.0.0";
		}
		else if (i == 2){
			m = "224.0.0.12"; 
		}
		else if (i == 3){
			m = "224.0.0.14"; 
		}
		else if (i == 4){
			m = "224.0.0.16"; 
		}
		else{
			m = "224.0.0.18"; 
		}
		return m;
	}
	
	public int getP(int i){
		int m = 0;
		if (i == 1){
			m = 9999;
		}
		else if (i == 2){
			m = 8888; 
		}
		else if (i == 3){
			m = 7777; 
		}
		else if (i == 4){
			m = 6666; 
		}
		else{
			m = 6655; 
		}
		return m;
	}
	
	public String getName() throws Exception {
	    return name;
	}
	public void setNumber() throws Exception {
	    //System.out.print("Please enter a number ... ");
		number = readFromKeyboard();
		System.out.println("TY. Ready to play now.");
	}
	
	public void setResponse(String response){
		this.response = response;
	}
	
	public String getResponse(){
		return response; 
	}
	public void setRand(String g){
		this.number = g;
	}
	public String getNumber() throws Exception {
	    return number;
	}
	
	public String doform(String guess, Integer playernum, int rand) throws Exception{
			String s = checker(guess, rand);
			if(checker(guess, rand).contains("CORRECT")){
				return "Correct, game over!";
			}
			if (guess.equals("bye")){
				return "bye";
			}
			if (playernum == -1){
				s = guess;
			}
			if (guess.contains("too") || guess.contains("Correct")){
				return guess; 
			}
			return s;
	}
	
	public String checker(String guess, int rand) throws Exception{
		int num_gues = Integer.parseInt(guess);
		int final_num = rand;
		
		if (num_gues == final_num){
			return num_gues + " Correct, game over!";
		}
		else if (num_gues > final_num){
			return num_gues + " too high";
		}
		else{
			return num_gues + " too low";
		}
	}
	public void setEnd(){
		this.end = 1;
	}
	
	public Integer end(){
		return end;
	}
	
	public void joinGroup() throws Exception {
		//IMPORTANT IMPORTANT IMPORTANT
		//the following two lines are needed for MACs and BC
		InetAddress IP=InetAddress.getLocalHost();  //now this is what is called the nat address, and should work for us since we are only talking to machines on our segment
		socket.setInterface(IP);
		group = InetAddress.getByName(IPadrress);
		socket.joinGroup(group);
	}
	
	public void leaveGroup() throws Exception {
		end = 1;
		socket.leaveGroup(group);
		socket.close();
	}

	public String readFromKeyboard() throws Exception {
		if(playernum == 2 || playernum == 4){
			//System.out.println("Enter a guess: ");
		}
		BufferedReader stdin; /* input from keyboard */
		String sendString; /* string to be sent */
		stdin = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("");
		sendString = stdin.readLine();
		return sendString;
	}
	
	public void sendToSocket(String msg) throws Exception{
		/* remember to convert keyboard input (in msg) to bytes */
		 DatagramPacket sendPacket = new DatagramPacket(msg.getBytes(), msg.length(),group, PORT);
		 socket.send(sendPacket);
	}
	
	public String readFromSocket() throws Exception{
		String socketString = null; /* string from socket */
		// get their responses!
		//byte[] buf is a byte array from the socket
		 byte[] buf = new byte[1000];
		 DatagramPacket recv = new DatagramPacket(buf, buf.length);
		 socket.receive(recv);
		 socketString = new String(recv.getData(), 0, recv.getLength());
		return 	socketString;	
	}
	public void sendToTerminal(String msg) throws Exception{
		//System.out.println("Multicast text: " + msg);
		System.out.println(msg);
	}
}
