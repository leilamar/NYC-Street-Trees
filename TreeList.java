/**
 * This class represents a TreeList, a subclass of ArrayList that stores Tree objects. 
 * The class contains methods to get information from the list, such as getting the 
 * total number of trees in the list and getting the number of trees with a specific 
 * species name or borough name.
 * 
 * @author Leila Mardoum
 * @version 02/14/17
 */

import java.util.*;

public class TreeList extends ArrayList<Tree> {
	
	/**
	 * Default constructor that creates an empty list
	 */
	public TreeList(){
		
	}
	
	/**
	 * Returns the total number of trees in the TreeList
	 * 
	 * @return int number of tree objects in the TreeList
	 */
	public int getTotalNumberOfTrees(){
		return size();
	}
	
	
	/**
	 * Returns the number of Tree objects in the list whose species matches
	 * the species name specified by the parameter (ignoring the case)
	 * 
	 * @param spcName species name to match with objects in the TreeList
	 * 
	 * @return int number of tree objects matching the specified species name 
	 * (return 0 if none found)
	 *
	 */
	public int getCountByTreeSpecies(String spcName){
		int count = 0;
		
		//loop through TreeList
		for(int i = 0; i < size(); i++){
			String fullSpcName = get(i).getTreeSpecies();
			
			//compare species name with spcName; if a match, increase count by 1
			if((fullSpcName.toLowerCase()).contains(spcName.toLowerCase())){ 
				count++;
			}
		}
		
		return count;
	}
	
	/**
	 * Returns the number of Tree objects in the list that are located in the
	 * borough specified by the parameter (ignoring the case)
	 * 
	 * @param boroName borough name to match with objects in the TreeList
	 * 
	 * @return int number of tree objects in the specified borough in the TreeList 
	 * (return 0 if none found)
	 *
	 */
	public int getCountByBorough(String boroName){
		
		int count = 0;
		
		//loop through TreeList
		for(int i = 0; i < size(); i++){
			
			//compare tree borough to boroName; if a match, increase count by 1
			if((get(i).getBorough()).equalsIgnoreCase(boroName)){
				count++;
			}
		}
		
		return count;
	}
	
	
	/**
	 * Returns the number of Tree objects in the list whose species matches
	 * the speciesName specified by the first parameter and which are located in the 
	 * borough specified by the second parameter (ignoring the case)
	 * 
	 * @param spcName species name to match with objects in the TreeList
	 * @param boroName borough name to match with objects in the TreeList
	 * 
	 * @return int number of tree objects matching the specified species name and 
	 * borough name (return 0 if no trees fitting criteria found)
	 *
	 */
	public int getCountByTreeSpeciesBorough(String spcName, String boroName){
		int count = 0;
		
		//loop through TreeList
		for(int i = 0; i < size(); i++){
			String fullSpcName = get(i).getTreeSpecies();
			
			//check to see if tree species name contains the specified species name
			if((fullSpcName.toLowerCase()).contains(spcName.toLowerCase())){
				
				//check to see if tree borough is the specified borough
				if((get(i).getBorough()).equalsIgnoreCase(boroName))
					count++;
			}
		}
		
		return count;
		
	}
	
	/**
	 * Creates an ArrayList<String> object containing a list of all the actual tree 
	 * species that match a given parameter string species name. The actual species 
	 * matches speciesName if speciesName is a substring of the actual name (case
	 * insensitive). The list does not contain repeats, and all elements added are 
	 * converted to lowercase.
	 * 
	 * @param spcName species name to search for in the TreeList
	 * 
	 * @return ArrayList<String> list of matching species names, without repeats 
	 *
	 */
	public ArrayList<String> getMatchingSpecies(String spcName){
		//construct ArrayList to store matching species names
		ArrayList<String> actualSpecies = new ArrayList<String>();
		
		//loop through TreeList
		for(int i = 0; i < size(); i++){
			String fullSpcName = get(i).getTreeSpecies().toLowerCase();
			
			//check if species name contains given parameter
			if(fullSpcName.contains(spcName.toLowerCase())){
				
				//if species name not already in list, add it
				if(!(actualSpecies.contains(fullSpcName))){
					actualSpecies.add(fullSpcName);
				}
			}
		}
		
		return actualSpecies;
	}
	
	
	/**
	 * Creates a representation of the TreeList, including how many trees are in the 
	 * list and how many trees from each burrow are included in the list. The items 
	 * in the list are not returned. 
	 * 
	 * @return String representation of the TreeList
	 *
	 */
	@Override
	public String toString(){
		return "There are " + getTotalNumberOfTrees() + " elements in this TreeList. "
				+ "There are " + getCountByBorough("Manhattan") + " trees from Manahattan, "
				+ getCountByBorough("Bronx") + " trees from The Bronx, "
				+ getCountByBorough("Brooklyn") + " trees from Brooklyn, " 
				+ getCountByBorough("Queens") + " trees from Queens, and "
				+ getCountByBorough("Staten Island") + " trees from Staten Island in this list.";
		
	}
}
