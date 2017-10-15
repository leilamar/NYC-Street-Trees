/**
 * Simple class to keep track of the boroughs in the TreeCollection class. Stores the borough name and the
 * number of trees in that borough. Includes simple getters and methods to increment and decrement the count
 * of trees in the borough. 
 * 
 * @author Leila Mardoum
 * @version 4/22/17
 */

public class Borough {
	private String name;
	private int count;
	
	/**
	 * Constructor takes a borough name and initializes the tree count at 1
	 * @param name borough name
	 */
	public Borough(String name){
		this.name = name;
		count = 1;
	}
	
	/**
	 * Increments count by 1
	 */
	public void inc(){
		count++;
	}
	
	/**
	 * Decrements count by 1
	 */
	public void dec(){
		count--;
	}
	
	/**
	 * Returns the name of the borough
	 * 
	 * @return the name of the borough
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Returns the number of trees located in this borough
	 * 
	 * @return number of trees in this borough
	 */
	public int getCount(){
		return count;
	}
}
