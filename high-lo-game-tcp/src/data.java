/*Alexandra Daisy Priede
 * 21 Sep. 2017
 * PS2
 */

import java.io.Serializable;


public class data implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name, data;
	private Integer randomNum; 
	
	public data(String n, String d)
	{
		this.name = n;
		this.data = d;
	}
	public String getName()
	{
		return name;	
	}
	public String getData()
	{
		return data;	
	}
	public void numFromServer(Integer n){
		this.randomNum = n; 
	}
	public String getLoc()
	{
		int d = Integer.parseInt(this.data); 
		if (d > randomNum)
			return " you guessed " + this.data +" and your guess was too high";
		else if (d < randomNum)
			return " you guessed " + this.data +" and your guess was too low"; 
		else{
			return " you've guessed the number!";
		}
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
		System.out.println("Name: " + name + ", Num Guess: " + data);
	}
	public String toString(){
		return (name + data);
	}
}