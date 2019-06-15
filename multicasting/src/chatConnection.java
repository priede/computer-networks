import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
public class chatConnection implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String name, data;
	private String multicast = "224.0.0.0"; 
	private int port = 9999; 
	private int playernum; 
	private int rand; 
	private int gameOver = 0; 
	String  rooms; 
	private String g; 
	private String gamesOpen;
//	public HiLoData(String n, String d)
//	{
//		this.name = n;
//		this.data = d;
//	}
	public chatConnection(String multicast, int port, String g, String gamesOpen, String multiPort)  {
		this.multicast = multicast; 
		this.port = port; 
		this.g = g;
		this.gamesOpen = gamesOpen;  
		this.rooms = multiPort;
		//this.rooms = main;
	}
	public String getmulticast() {
		return multicast; 
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
