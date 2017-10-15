/**
 * This class represents a tree and stores various info about the tree, including
 * the id number, the tree diameter, the status, health, species name, zipcode, 
 * and NYC borough in which the tree is located. 
 * 
 * @author Leila Mardoum
 * @version 4/22/17
 */

public class Tree implements Comparable<Tree>{
	
	private int id; //non-negative
	private int diam; //non-negative
	private String status; //valid values: "Alive", "Dead", "Stump", empty string, or null
	private String health; //valid values: "Good", "Fair", "Poor", empty string, or null
	private String spc; //cannot be null
	private int zip; //5 digits, between 0-99999
	private String boro; //valid: "Manhattan", "Bronx", "Brooklyn", "Queens", "Staten Island"
	private double xCoord; //x coordinate
	private double yCoord; //y coordinate

	/**
	 * Constructor takes 9 arguments, and initializes the data fields if the arguments 
	 * are valid. If not, throws IllegalArgumentException and does not construct the
	 * object.
	 * 
	 * @param id the tree id number
	 * @param diam the diameter of the tree at breast height (in)
	 * @param status String indicating if tree is alive, standing dead, or a stump
	 * @param health String indicating whether tree is in good, fair, or poor health
	 * @param spc the common species name of the tree
	 * @param zip the zip code where the tree is located
	 * @param boro the NYC borough where the tree is located
	 * @param xCoord the x coordinate of the tree in state plane (ft)
	 * @param yCoord the y coordinate of the tree in state plane (ft)
	 */
	public Tree(int id, int diam, String status, String health, String spc, 
			int zip, String boro, double xCoord, double yCoord){
		
		//initialize tree id
		if(id >= 0){
			this.id = id;
		} else {
			throw new IllegalArgumentException("The tree id cannot be negative");
		}
		
		//initialize diameter
		if(diam >= 0){
			this.diam = diam;
		} else {
			throw new IllegalArgumentException("The tree diameter cannot be negative");
		}
		
		//initialize status
		if(status == null || status.trim().isEmpty() || status.equalsIgnoreCase("Alive") 
				|| status.equalsIgnoreCase("Dead") || status.equalsIgnoreCase("Stump")){
			this.status = status;
		} else {
			throw new IllegalArgumentException("Invalid status");
		}
		
		//initialize health
		if(health == null || health.trim().isEmpty() || health.equalsIgnoreCase("Good") 
				|| health.equalsIgnoreCase("Fair") || health.equalsIgnoreCase("Poor")){
			this.health = health;
		} else {
			throw new IllegalArgumentException("Invalid health entry");
		}
		
		//initialize spc
		if(spc != null){
			this.spc = spc;
		} else {
			throw new IllegalArgumentException("The tree species cannot be null");
		}
		
		//initialize zipcode
		if(zip < 0 || zip > 99999){
			throw new IllegalArgumentException("Zipcode must be between 0 and 99999");
		} else {
			this.zip = zip;
		}
		
		//initialize boro
		if(boro == null)
			throw new IllegalArgumentException("The borough name cannot be null");
		if(boro.equalsIgnoreCase("Manhattan") || boro.equalsIgnoreCase("Bronx") 
				|| boro.equalsIgnoreCase("Brooklyn") || boro.equalsIgnoreCase("Queens") 
				|| boro.equalsIgnoreCase("Staten Island")){
			this.boro = boro;
		} else {
			throw new IllegalArgumentException("Not a valid NYC borough");
		}
		
		//initialize x and y coordinates
		this.xCoord = xCoord;
		this.yCoord = yCoord;
	}
	
	/**
	 * Gets ID number for the given tree
	 * 
	 * @return int representing the tree ID number
	 */
	public int getTreeId(){
		return id;
	}
	
	/**
	 * Gets common species name for the given tree
	 * 
	 * @return String of the tree species name
	 */
	public String getTreeSpecies(){
		return spc;
	}
	
	/**
	 * Gets the borough in NYC in which the given tree is located
	 * 
	 * @return String of the borough name
	 */
	public String getBorough(){
		return boro;
	}
	
	/**
	 * Returns the zip code in which the given tree is located, with five digits 
	 * (including leading zeros if necessary)
	 * 
	 * @return String representation of zip code with appropriate formatting
	 */
	public String getZipCode(){
		return String.format("%05d", zip);
	}
	
	/**
	 * Compares two tree objects to see if they are equal. Two trees are equal if
	 * their ID's and species name are the same (ignoring the case). 
	 * 
	 * @param other tree to compare to the tree the method is called on
	 * 
	 * @return true if both trees have same id and same species name
	 * false, otherwise
	 * 
	 * @throws IllegalArgumentException if two trees have the same id, but a different species name
	 */
	@Override
	public boolean equals(Object other){
		if( id == ((Tree)other).getTreeId() ) { //check if ids equal
			if( spc.equalsIgnoreCase(((Tree)other).getTreeSpecies()) ){ //check if spc equal
				return true;
			} else {
				throw new IllegalArgumentException("Cannot have the same ID number but be different species");
			}
		} else {
			return false;
		}
	}
	
	/**
	 * Compares two tree objects alphabetically by the species name as the 
	 * first key and by tree id as the second key (when two objects that have 
	 * the same species name are compared)
	 * 
	 * @param other tree to compare with the tree the method is called on
	 * 
	 * @return int positive integer if tree > other
	 * negative integer if tree < other
	 * 0 if tree == other
	 */
	public int compareTo(Tree other){
		//compare species names
		int speciesNameComparison = spc.compareToIgnoreCase(other.getTreeSpecies());
		
		//if species names are different, return alphabetical comparison from String class
		if(speciesNameComparison != 0){
			return speciesNameComparison;
		//otherwise, compare by tree id
		} else if( getTreeId() > other.getTreeId()){
			return 1;
		} else if( getTreeId() < other.getTreeId()){
			return -1;
		} else {
			return 0;
		}
	}
	
	
	/**
	 * Returns a String representation of a tree object, which includes the tree id, the 
	 * species name, the borough the tree is located in, the zipcode, the status, and 
	 * the health of the tree.
	 * 
	 * @return String representation of a tree object
	 */
	@Override
	public String toString(){
		return "Tree number " + id + " is a " + spc.toLowerCase()
				+ " tree in " + getBorough() + " in zipcode " + getZipCode() 
				+ ". \nStatus: " + status.toLowerCase()
				+ "\nHealth: " + health.toLowerCase();
	} 
	
	
	public boolean sameName(Tree t){
		if(spc.equalsIgnoreCase(t.getTreeSpecies()))
			return true;
		return false;
	}
	
	public int compareName(Tree t){
		return spc.compareToIgnoreCase(t.getTreeSpecies());
	}
	
}
