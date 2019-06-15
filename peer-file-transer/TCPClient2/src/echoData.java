import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;


public class echoData implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username, filename, IP;
	private Integer register, PORT; 
	
	private String userIP, userPORT; 
	
	private ArrayList<ArrayList> send; 
	
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
	public Boolean getRequest() {
		if (register == 0) {
			return false;
		}
		else {
			return true; 
		}
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
	public String requestFile(ArrayList<ArrayList> DB) {
		//ArrayList<ArrayList> fin = new ArrayList<ArrayList>(); 
		String fin = ""; 
		for (int i = 0; i < DB.size(); i++) {
			if (DB.get(i).get(2).equals(filename)) {
				for (int j = 0; j < DB.get(i).size();j++) {
					fin += DB.get(i).get(j); 
				}
			}
		}
		return fin; 
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
	public String printArrayResponse2() {
		String IPnew = this.username.substring(9,18);
		String PORTnew = this.username.substring(31,35);
		//String fileNAME2 = this.username.substring(48,this.username.length()-1);
		//Integer PORTnew_int = Integer.parseInt(PORTnew);
		System.out.println(IPnew + " " + PORTnew);
		return IPnew + "," + PORTnew; 
	}
	void readArray() {
		System.out.println(this.send);
	}
}