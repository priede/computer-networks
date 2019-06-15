public class peerSendThread implements Runnable {
	peerUtilites peerU;
	int playernum;
	String response; 
	int rand; 

	public peerSendThread(peerUtilites peerU, int playernum, int rand) {
		this.peerU = peerU;
		this.playernum = playernum; 
		this.rand = rand; 
	}

	public void run() {
		String fromKeyboard = null;
		String message = null;
		String fromSocket = null;
		try {
			while (!(fromKeyboard = peerU.readFromKeyboard()).equalsIgnoreCase("bye") ) {
				if(fromKeyboard.equals("bye")){
					peerU.setEnd();
					peerU.leaveGroup();
				}
				if(playernum == 1 && fromKeyboard.equals("yes")){
					peerU.leaveGroup();
					
					//System.out.println("Guess a number: ");
				}
//				if(playernum == 2 && fromKeyboard.equals("yes")){
//					peerU.setPlayerNum(1);
//					peerU.sendToSocket("new game");
//					//peerU.sendToSocket("new game");
//				}
				peerU.sendToSocket(fromKeyboard);
			}
			peerU.sendToSocket(fromKeyboard);
			
		} catch (Exception E) {
		}
	}
}
