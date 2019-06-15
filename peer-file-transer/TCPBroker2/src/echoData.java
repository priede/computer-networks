import java.io.Serializable;
import java.util.ArrayList;


public class echoData implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username, filename, IP;
	private Integer register, PORT; 

	ArrayList<ArrayList> send; 
	
	public echoData(String n, Integer reg, String fn, String ip, Integer port, ArrayList<ArrayList> sender)
	{
		this.username = n;
		this.register = reg; 
		this.filename = fn;
		this.IP = ip;
		this.PORT = port; 
		this.send = sender; 
	}
	public String getUser()
	{
		return username;	
	}
	public String getFile()
	{
		return filename;	
	}
	public Integer getRequest() {
		return register;
	}
	public ArrayList<ArrayList> registerFile(ArrayList<ArrayList> DB) {
		ArrayList<String> l = new ArrayList<String>();
		l.add(username);
		l.add(register.toString());
		l.add(filename);
		l.add(IP);
		l.add(PORT.toString());
		DB.add(l);
		return DB;
	}
	public String unregisterFile(ArrayList<ArrayList> DB) {
		for (int i = 0; i < DB.size(); i++) {
			if (DB.get(i).get(2).equals(filename) && DB.get(i).get(0).equals(username)) {
				DB.remove(i);
			}
		}
		return "file is unregistered";
	}
	public ArrayList<ArrayList> rateFile(ArrayList<ArrayList> DB) {
		//System.out.println(send.get(0));
		//String rating = (String) send.get(0).get();
		for (int i = 0; i < DB.size(); i++) {
			if (DB.get(i).get(2).equals(filename) && DB.get(i).get(4).equals(username)) {
				DB.get(i).add("Rating: 0"); 
			}
		}
		return DB; 
	}
	public String requestFile(ArrayList<ArrayList> DB) {
		//ArrayList<ArrayList> fin = new ArrayList<ArrayList>(); 
		String fin = ""; 
		for (int i = 0; i < DB.size(); i++) {
			if (DB.get(i).get(2).equals(filename)) {
				fin += "User: " + DB.get(i).get(0) + " has file : " + DB.get(i).get(2) + "\n"; 
			}
		}
		return fin; 
	}
	
	public String userInfo(ArrayList<ArrayList> DB) {
		String userRequest = username; //requested username from client .. find info
		String userinfo = ""; 
		for (int i = 0; i < DB.size(); i++) {
			if (DB.get(i).get(2).equals(filename) && DB.get(i).get(0).equals(userRequest)) {
				userinfo = "";
				userinfo += "user IP: " + DB.get(i).get(3) + " user PORT : " + DB.get(i).get(4) + "file name: "+ DB.get(i).get(2)+"\n"; 
			}
			else {
				userinfo = "user not found"; 
			}
		}
		return userinfo; 
	}
	
	public ArrayList<ArrayList> userInfo1(ArrayList<ArrayList> DB) {
		String userRequest = username; //requested username from client .. find info
		ArrayList<ArrayList> userinfo = new ArrayList<ArrayList>(); 
		
		for (int i = 0; i < DB.size(); i++) {
			if (DB.get(i).get(2).equals(filename) && DB.get(i).get(0).equals(userRequest)) {
				userinfo.add(DB.get(i));
				System.out.println("added");
			}
		}
		return userinfo; 
	}
	
	public String brokerReceived() {
		return "from : " + username + " filename: " + filename; 
	}
	public String toStringREG() {
		return "file successfully registered"; 
	}
	void printResponse() {
		System.out.println(this.username);
	}
	void printArrayResponse() {
		System.out.println(this.username);
	}
}