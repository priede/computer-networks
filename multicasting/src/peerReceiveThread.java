
public class peerReceiveThread implements Runnable{
	peerUtilites peerU;
	int playernum;
	int rand;
	int set = 0;
	String response; 
	
	public peerReceiveThread(peerUtilites peerU , int playernum, int rand){
		this.peerU = peerU;
		this.playernum = playernum;
		this.rand = rand;
	}
	public void run(){
		String fromSocket = null;
		String response = null;
		//String set = null;
		try {
			while (true) {
				fromSocket = peerU.readFromSocket();
//				if(fromSocket.equals("new game") && playernum == 1){
//					System.out.println("Sweet, pick a number for player to guess: ");
//					peerU.setNumber();
//					//peerU.sendToTerminal("guess ");
//					//System.out.println(peerU.getNumber());
//				}
				if(playernum == 1 || playernum == 3 || playernum == 5 || playernum == 7 || playernum == 9){
				try{
					int guess = Integer.parseInt(fromSocket);
					rand = Integer.parseInt(peerU.getNumber());
					if(guess > rand){
						response = "Player 2 guess " + guess + " too high";
					}
					else if (guess < rand){
						response = "Player 2 guess " + guess + " too low"; 
					}
					else{
						response = "Player 2 guess " + guess + " correct, play again?";
					}
					peerU.sendToSocket(response);
				}
				catch  (NumberFormatException e){
					peerU.sendToTerminal(fromSocket);
					
				}
				}
				else{
					peerU.sendToTerminal(fromSocket);
					if(fromSocket.contains("correct")){
						peerU.leaveGroup();
					}
				}
			}

		} catch (Exception E) {
	}
	}
	public void set(String response){
		
	}
}
