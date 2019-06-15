import java.net.ConnectException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class client {

	public static void main(String[] args) throws IOException {

		  if ((args.length != 2) ) { // Test for correct # of args
		      throw new IllegalArgumentException("Parameter(s): <Server> <Port>");
		    }

		String server = args[0]; // Server name or IP address
		// from keyboard
		BufferedReader fromKeyboard = new BufferedReader(new InputStreamReader(System.in)); 
		ArrayList<String> obj = new ArrayList<String>();
		InetAddress serverAddress = InetAddress.getByName(args[0]); // Server
																	// address
		int servPort = Integer.parseInt(args[1]); // get port number

		String clientInput, name; // from user
		Socket socket = null;
		
		try {
			// Create socket that is connected to server on specified port
			socket = new Socket(serverAddress, servPort);
			data1 toSend, toReceive=null;
			String[] b = new String[9]; 
			data1 d = new data1(null,null,b); 
		    System.out.print("Enter your name: ");
		    name = fromKeyboard.readLine();
		    
		    d.createBoard();
		    String[] board = d.getBoard(); 
		    d.toStringBoard();
		    
		    int newGame = 0; 
		    int odds = 1; 
	    
		while (true) {
			if (newGame == 0){
				if(odds == 1){
					System.out.print("Enter move ex. (A,1,9). You enter odds: ");
				}
				else{
					System.out.print("Enter move ex. (A,1,8). You enter evens: ");
				}
			}
			if (newGame == 1){
				newGame = 0; 
			}
			clientInput = fromKeyboard.readLine();
			// user want to stop having fun with the server
			if(clientInput.equals("yes")){
			    board = d.clearBoard(); 
			    if(odds == 1){
			    	odds = 0;
			    	System.out.print("Enter move ex. (A,1,9). You enter evens(game2): ");
			    }
			    else{
			    	odds = 1; 
			    	System.out.print("Enter move ex. (A,1,9). You enter odds(2): ");
			    }
				clientInput = fromKeyboard.readLine();
			}
			if (clientInput.equals("exit")) 
				break;
			//System.out.println("FROM CONSOLE (size of input string is " + sendbuf.length + "): " + clientInput);
			System.out.println("Connected to server...sending object data");
			toSend = new data1(Integer.toString(odds),clientInput,board);
			System.out.println("Client: " + toSend.toString());
		    OutputStream os = socket.getOutputStream(); 
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.flush();
			oos.writeObject(toSend);   //send object to server
			oos.flush();
			
		    // Receive an object back from the server
		    InputStream is = socket.getInputStream();  
		    ObjectInputStream ois = new ObjectInputStream(is);  
			try {
				toReceive = (data1)ois.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}  
		    if (toReceive != null){
		    	
		    	if(toReceive.toString().contains("invalid")){
		    		System.out.println("Invalid move! Try again ...");
			    	board = toReceive.getBoard();
		    	}
		    	if(toReceive.toString().contains("draw")){
		    		System.out.println("Game is a draw. Want to play again ('yes' or 'exit')? ");
		    		newGame = 1; 
		    		board = toReceive.clearBoard(); 
		    		String f = "game is a draw";
		    		obj.add(f);
		    	}
		    	if(toReceive.toString().contains("user")){
		    		System.out.println("Game won by user. Want to play again ('yes' or 'exit')? ");
		    		newGame = 1; 
		    		board = toReceive.clearBoard(); 
		    		String f = "user won"; 
		    		obj.add(f);
		    	}
		    	if(toReceive.toString().contains("comp")){
		    		System.out.println("Game won by computer. Want to play again ('yes' or 'exit')? ");
		    		newGame = 1; 
		    		board = toReceive.clearBoard(); 
		    		String f = "computer won";
		    		obj.add(f);
		    	}
		    	else if (!toReceive.toString().contains("invalid") && !toReceive.toString().contains("draw") && !toReceive.toString().contains("user") && !toReceive.toString().contains("comp")){
			    	System.out.println("from server: " + toReceive.toString());
			    	toReceive.toStringBoard();
			    	toReceive.setBoard(toReceive.getBoard());
			    	board = toReceive.getBoard();
		    	}
		    }  		    
			
		}
		int x = 0;
		int j = 0; 
		ArrayList<String> finalScore = new ArrayList<String>();
		
		int dr = 0;
		int compW = 0;
		int userW = 0; 
		
		while(x < obj.size()){
			System.out.println(obj.get(x));
			if(obj.get(x) == "computer won"){
				compW++; 
			}
			if(obj.get(x) == "user won"){
				userW++;
			}
			if(obj.get(x) == "game is a draw"){
				dr++; 
			}
			x++; 
		}
		
		finalScore.add("Computer won: " + compW);
		finalScore.add("User won: " + userW);
		finalScore.add("Draw: " + dr); 
		
		System.out.println("List of echo strings from server is:" + finalScore);
		socket.close(); // Close the socket and its streams
		
	} catch (ConnectException e) {
		System.out.println("Connection refused, probably no server running");
	}
  }
}
