import java.io.Serializable;
public class Data implements Serializable
{
	private static final long serialVersionUID = 1L;
	private String data;
	private Integer serverNum; 
	private Integer count; 
	private Integer isC; 

	public Data(String d)
	{
		this.data = d;
	
	}
	public void setData(String d)
	{
		this.data = d;
	}
	public void counter(Integer n){
		this.count = n; 
	}
	
	public Integer getCount(){
		return this.count; 
	}
	public void numFromServer(Integer n){
		this.serverNum = n; 
	}
	public String getData()
	{
		int randomNumber = this.serverNum;  
		
		isC = 0;
		
		if (this.count==0){
			return this.data; 
		}
		int numberGuessed = Integer.parseInt(this.data);
		if (numberGuessed == this.serverNum){
			isC = 1; 
			return "You have correctly guessed the number! It took you " + this.count + " tries.";
		}
		if (numberGuessed < this.serverNum){
			return "You need to guess a higher number. You have guessed " + this.count + " times.";
		}
		if (numberGuessed > this.serverNum){
			return "You need to guess a lower number. You have guessed " + this.count + " times."; 
		}
		return "none"; 
	}
	public void printIt(){
		System.out.println(" data: " + data);
	}
	public String toString(){
		return (" data: " + data);
	}
}
