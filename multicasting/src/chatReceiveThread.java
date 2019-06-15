
public class chatReceiveThread implements Runnable{
	peerUtilites peerU;
	peerUtilites peerL; 
	public chatReceiveThread(peerUtilites peerL , peerUtilites peerU){
		this.peerU = peerU;
		this.peerL = peerL; 
	}
	public void run(){
		String fromSocket = null;
		try {
			while (true) {
				fromSocket = peerU.readFromSocket();
				if(fromSocket.contains("switch")){
					peerU.leaveGroup();
				}
				peerU.sendToTerminal(fromSocket);
			}

		} catch (Exception E) {
		}
	}	
}
