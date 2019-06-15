import java.io.Serializable;
import java.util.Random;


public class data1 implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String data;
	private String[] updatedBoard = new String[9]; 
	//private String[] c = new String[9];
	static int odd = 1; 
	
	static String[] b = new String[9]; 
    
	
	public data1(String n, String d, String[] board)
	{
		this.name = n;
		this.data = d;
		this.updatedBoard = board; 
		//this.c = nums; 
	}
	public String getName()
	{
		return name;	
	}
	public String getData()
	{
		return data;	
	}
	public String[] getBoard()
	{
		return updatedBoard; 
	}
	public void setBoard(String[] b)
	{
		this.updatedBoard = b; 
	}
	public void setName(String n)
	{
		this.name = n;	
	}
	public void setData(String d)
	{
		this.data = d;	
	}
	public String[] clearBoard(){
        int x = 0;
        while(x < b.length){
        	b[x] = " ";
        	this.updatedBoard[x] = " "; 
        	x++; 
        } 
        return this.updatedBoard;
	}
	public void createBoard()
	{
        int x = 0;
        while(x < b.length){
        	b[x] = " ";
        	this.updatedBoard[x] = " "; 
        	x++; 
        } 
	}
	public void updateBoard(String y , int z, int num){
		
        String let = "";
        int index = 0; 
        int place = 0; 
        
        for (int i = 0; i < 3; i++){
        	for (int j = 0; j < 4; j++){
        		if (i == 0){
        			let = "A"; 
        			index = -1; 
        		}
        		else if(i == 1){
        			let = "B";
        			index = 2; 
        		}
        		else if (i == 2){
        			let = "C"; 
        			index = 5; 
        		}
        		
        		if(y.equals(let) && z == j){
        			place = index + z; 
        			this.updatedBoard[place] = Integer.toString(num); 
        		}
        	}
        }    
        
        int x = 0;
        while(x < b.length){
        	if(b[x] != null){
        		this.updatedBoard[x] = b[x]; 
        	}
        	x++; 
        } 
        
    }
	public boolean isTaken(String x, int y){	
		String let = "";
        int index = 0; 
        int place = 0; 
        
        for (int i = 0; i < 3; i++){
        	for (int j = 0; j < 4; j++){
        		if (i == 0){
        			let = "A"; 
        			index = -1; 
        		}
        	    if(i == 1){
        			let = "B";
        			index = 2; 
        		}
        	    if (i == 2){
        			let = "C"; 
        			index = 5; 
        		}
    
        		if(x.equals(let) && y == j){
        			place = index + y; 
        			if (this.updatedBoard[place].equals(" ")){
        				return false; 
        			}
        		}
        	}
        }
         
        return true;
	}
	public boolean getNum(Integer Guess){
		int x = 0;
		while (x < this.updatedBoard.length){
			if (this.updatedBoard[x].equals(Integer.toString(Guess))){
				return true; 
			}
			x++; 
		}
		return false; 
	}
	public static boolean isEven(int guess){
		if (guess%2 == 0){
			return true;
		}
		else{
			return false; 
		}
	}
	public boolean isValid(String A, Integer B, Integer Guess, Integer pos){
		boolean match = false; 
		boolean numExists = false; 
		if (isEven(Guess) && pos == 0){
			match = true;
		}
		else if (!isEven(Guess) && pos == 1){
			match = true; 
		}
		
		if (getNum(Guess)){
			numExists = true; 
		}
		
		if(isTaken(A,B) || match == false || numExists){
			return false;
		}
		else{
			return true; 
		}
	}
	public boolean checkGame(){
		if (!this.updatedBoard[0].equals(" ") && !this.updatedBoard[3].equals(" ") && !this.updatedBoard[6].equals(" ") ){
			return true;
		}
		if (!this.updatedBoard[1].equals(" ") && !this.updatedBoard[4].equals(" ") && !this.updatedBoard[7].equals(" ") ){
			return true;
		}
		if (!this.updatedBoard[2].equals(" ") && !this.updatedBoard[5].equals(" ") && !this.updatedBoard[8].equals(" ") ){
			return true;
		}
		if (!this.updatedBoard[0].equals(" ") && !this.updatedBoard[4].equals(" ") && !this.updatedBoard[8].equals(" ") ){
			return true;
		}
		if (!this.updatedBoard[2].equals(" ") && !this.updatedBoard[4].equals(" ") && !this.updatedBoard[6].equals(" ") ){
			return true;
		}
		if (!this.updatedBoard[0].equals(" ") && !this.updatedBoard[1].equals(" ") && !this.updatedBoard[3].equals(" ") ){
			return true;
		}
		if (!this.updatedBoard[3].equals(" ") && !this.updatedBoard[4].equals(" ") && !this.updatedBoard[5].equals(" ") ){
			return true;
		}
		if (!this.updatedBoard[6].equals(" ") && !this.updatedBoard[7].equals(" ") && !this.updatedBoard[8].equals(" ") ){
			return true;
		}
		return false; 
	}
	public boolean fullBoard(){
		int x = 0;
		while (x < this.updatedBoard.length){
			if(this.updatedBoard[x].equals(" ")){
				return false;
			}
			x++; 
		}
		return true; 
	}
	public boolean getWinner(){
		int temp = 0; 
		int temp1 = 0;
		int temp2= 0; 
		int sum = 0; 
		if (!this.updatedBoard[0].equals(" ") && !this.updatedBoard[3].equals(" ") && !this.updatedBoard[6].equals(" ") ){
			temp = Integer.parseInt((this.updatedBoard[0])); 
			temp1 = Integer.parseInt((this.updatedBoard[3])); 
			temp2 = Integer.parseInt((this.updatedBoard[6])); 
			sum = temp + temp1 + temp2; 
		}
		if (!this.updatedBoard[1].equals(" ") && !this.updatedBoard[4].equals(" ") && !this.updatedBoard[7].equals(" ") ){
			temp = Integer.parseInt((this.updatedBoard[1])); 
			temp1 = Integer.parseInt((this.updatedBoard[4])); 
			temp2 = Integer.parseInt((this.updatedBoard[7])); 
			sum = temp + temp1 + temp2; 
		}
		if (!this.updatedBoard[2].equals(" ") && !this.updatedBoard[5].equals(" ") && !this.updatedBoard[8].equals(" ") ){
			temp = Integer.parseInt((this.updatedBoard[2])); 
			temp1 = Integer.parseInt((this.updatedBoard[5])); 
			temp2 = Integer.parseInt((this.updatedBoard[8])); 
			sum = temp + temp1 + temp2; 
		}
		if (!this.updatedBoard[0].equals(" ") && !this.updatedBoard[4].equals(" ") && !this.updatedBoard[8].equals(" ") ){
			temp = Integer.parseInt((this.updatedBoard[0])); 
			temp1 = Integer.parseInt((this.updatedBoard[4])); 
			temp2 = Integer.parseInt((this.updatedBoard[8])); 
			sum = temp + temp1 + temp2; 
		}
		if (!this.updatedBoard[2].equals(" ") && !this.updatedBoard[4].equals(" ") && !this.updatedBoard[6].equals(" ") ){
			temp = Integer.parseInt((this.updatedBoard[2])); 
			temp1 = Integer.parseInt((this.updatedBoard[4])); 
			temp2 = Integer.parseInt((this.updatedBoard[6])); 
			sum = temp + temp1 + temp2; 
		}
		if (!this.updatedBoard[0].equals(" ") && !this.updatedBoard[1].equals(" ") && !this.updatedBoard[2].equals(" ") ){
			temp = Integer.parseInt((this.updatedBoard[0])); 
			temp1 = Integer.parseInt((this.updatedBoard[1])); 
			temp2 = Integer.parseInt((this.updatedBoard[2])); 
			sum = temp + temp1 + temp2; 
		}
		if (!this.updatedBoard[3].equals(" ") && !this.updatedBoard[4].equals(" ") && !this.updatedBoard[5].equals(" ") ){
			temp = Integer.parseInt((this.updatedBoard[3])); 
			temp1 = Integer.parseInt((this.updatedBoard[4])); 
			temp2 = Integer.parseInt((this.updatedBoard[5])); 
			sum = temp + temp1 + temp2; 
		}
		if (!this.updatedBoard[6].equals(" ") && !this.updatedBoard[7].equals(" ") && !this.updatedBoard[8].equals(" ") ){
			temp = Integer.parseInt((this.updatedBoard[6])); 
			temp1 = Integer.parseInt((this.updatedBoard[7])); 
			temp2 = Integer.parseInt((this.updatedBoard[8])); 
			sum = temp + temp1 + temp2; 
		}
		
		if(sum == 15){
			return true; 
		}
		return false; 
	}
	public void printIt(){
		System.out.println("Name: " + name + ", data: " + data);
	}
	public String toString(){
		return (data);
	}
	public void toStringBoard(){
        
        System.out.println("     1   2   3  "); 
        System.out.println("   +---+---+---+");
        System.out.println(" A | " + this.updatedBoard[0] + " | " + this.updatedBoard[1] + " | " + this.updatedBoard[2] + " |"); 
        System.out.println("   +---+---+---+"); 
        System.out.println(" B | " + this.updatedBoard[3] + " | " + this.updatedBoard[4] + " | " + this.updatedBoard[5] + " |"); 
        System.out.println("   +---+---+---+"); 
        System.out.println(" C | " + this.updatedBoard[6] + " | " + this.updatedBoard[7] + " | " + this.updatedBoard[8] + " |"); 
        System.out.println("   +---+---+---+");

	}
	public static String sepL(String L){
		String[] split = L.split(",");
		
		String let = split[0];
		let = let.replace("(", "");
		String n = split[1]; 
		String numGuess = split[2];
		numGuess = numGuess.replace(")", ""); 
		return let;
	}
	public static int sepFir(String L){
		String[] split = L.split(",");
		
		String let = split[0];
		let = let.replace("(", "");
		String n = split[1]; 
		String numGuess = split[2];
		numGuess = numGuess.replace(")", ""); 
		int move_n = Integer.parseInt(n); 

		return (move_n);
	}
	public static int sepSec(String L){
		String[] split = L.split(",");
		
		String let = split[0];
		let = let.replace("(", "");
		String n = split[1]; 
		String numGuess = split[2];
		numGuess = numGuess.replace(")", ""); 
		int num_put = Integer.parseInt(numGuess); 
		return (num_put);
	}
	
	public String[] serverMove(Integer pos){
		Random rand = new Random();
		
    	int letter = rand.nextInt(3) + 1;
    	String letterReal = ""; 
    	
    	if (letter == 1){
    		letterReal = "A"; 
    	}
    	if (letter == 2){
    		letterReal = "B"; 
    	}
    	if(letter == 3){
    		letterReal = "C"; 
    	}
    	int B = rand.nextInt(3) + 1;
    	int Guess = rand.nextInt(9) + 1; 
    	boolean isV = isValid(letterReal, B, Guess, pos); 

	    while(!isV){

	    	letter = rand.nextInt(3) + 1;
	        letterReal = ""; 
	    	if (letter == 1){
	    		letterReal = "A"; 
	    	}
	    	if (letter == 2){
	    		letterReal = "B"; 
	    	}
	    	if(letter == 3){
	    		letterReal = "C"; 
	    	}
	    	B = rand.nextInt(3) + 1;
	        Guess = rand.nextInt(9) + 1; 
	        isV = isValid(letterReal, B, Guess, pos); 

	    }
	    
	    String[] fin = new String[4]; 
	    fin[0] = letterReal; 
	    fin[1] = Integer.toString(B); 
	    fin[2] = Integer.toString(Guess);
	    return fin; 
	}
}