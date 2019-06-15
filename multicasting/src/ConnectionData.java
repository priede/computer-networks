import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
public class ConnectionData implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String name, data;
	private String multicast = "224.0.0.0"; 
	private int port = 9999; 
	private int playernum; 
	private int rand; 
	private int gameOver = 0; 
	String  rooms; 
	private int roomNum; 
	private String g; 
	private String gamesOpen;
//	public HiLoData(String n, String d)
//	{
//		this.name = n;
//		this.data = d;
//	}
	public ConnectionData(String multicast, int port, int playernum, String g, String gamesOpen)  {
		this.multicast = multicast; 
		this.port = port; 
		this.playernum = playernum; 
		this.g = g;
		this.gamesOpen = gamesOpen;  
		//this.rooms = main;
	}
	public String getmulticast() {
		return multicast; 
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

	public String readFromKeyboard() throws Exception {
		BufferedReader stdin; /* input from keyboard */
		String sendString; /* string to be sent */
		stdin = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("");
		sendString = stdin.readLine();
		return sendString;
	}
	
	public String getRooms(){
		return rooms; 
	}
	public String getGamesOpen(){
		return gamesOpen; 
	}
	public String getG(){
		return g; 
	}
	public int getport() {
		return port; 
	}
	public int getplayernum() {
		return playernum; 
	}
	public void setGameOver(int g){
		this.gameOver = g; 
	}
	public int gameOver(){
		return gameOver;
	}
	public String getName()
	{
		return name;
	}
	public String getData()
	{
		return data;
	}
	public Integer getRand()
	{
		return rand;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public void setMulticast(String multicast) {
		this.multicast = multicast; 
	}
	public void setplayernum(int playernum) {
		this.playernum = playernum; 
	}
	public void setRand() throws IOException {
		BufferedReader stdin; /* input from keyboard */
		String sendString; /* string to be sent */
		stdin = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("");
		sendString = stdin.readLine();
		this.rand = Integer.parseInt(sendString); 
	}
	public void setName(String n)
	{
		this.name = n;
	}
	public void setData(String d)
	{
		this.data = d;
	}
	public void printIt(){
		System.out.println("Name: " + name + " data: " + data);
	}
	public String toString(){
		return ("Name: " + name + " data: " + data);
	}
}
