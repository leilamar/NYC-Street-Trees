/**
 * This class represents a TreeCollection, which implements MyBST to store Tree objects. 
 * The class contains methods to get information from the TreeCollection, such as 
 * getting the total number of trees in the TreeCollection and getting the number of 
 * trees with a specific species name or borough name.
 * 
 * @author Leila Mardoum
 * @version 4/22/17
 */

import java.util.*;

public class TreeCollection extends MyBST<Tree> {
	// to store all species names in this TreeCollection
	ArrayList<String> speciesInCollection = new ArrayList<String>();
	//to store all boroughs in this TreeCollection
	ArrayList<Borough> boroNames = new ArrayList<Borough>(); 
	
	/**
	 * Default constructor that creates an empty TreeCollection object
	 */
	public TreeCollection(){
		
	}
	
	/**
	 * Overrides the MyBST add method, additionally storing the species name and borough of the given tree if 
	 * the tree is added to the TreeCollection. The specified tree is added if it is not already present in 
	 * the TreeCollection. If the element is already present, the call leaves the TreeCollection unchanged and 
	 * the method returns false. Does not accept null elements. 
	 * 
	 * @return true if the tree was successfully added; otherwise, return false
	 * @param t Tree to be added to the TreeCollection
	 * @throws NullPointerException if the Tree to be added is null; this TreeCollection does not permit null elements
	 */
	@Override
	public boolean add(Tree t){
		if(t == null)
			throw new NullPointerException("Error: cannot add  null");
		
		//try to add tree to TreeCollection
		boolean added = super.add(t);
		if(added){
			collectInfo(t); //store species name and borough
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Stores the species and borough of the given tree parameter in the TreeCollection
	 * in a list of species names and boroughs. The lists of species and boroughs does 
	 * not accept repeat names as new entries. 
	 * 
	 * @param t the Tree whose info must be stored
	 */
	private void collectInfo(Tree t){
		if(t == null) return;
		
		//add species name to list of species in TreeCollection if not already in list
		String spc = t.getTreeSpecies().toLowerCase();
		if(!speciesInCollection.contains(spc)){
			speciesInCollection.add(spc);
		}
	
		
		String boroName = t.getBorough().toLowerCase();
		Borough boro = new Borough(boroName);
		//for every borough in this TreeCollection
		for(Borough b : boroNames){
			//if this tree's borough matches b
			if(b.getName().equalsIgnoreCase(boroName)) {
				//increment the count for trees in that borough
				int boroIndex = boroNames.indexOf(b);
				boroNames.get(boroIndex).inc(); //increments count
				return;
			} 
		}
		//otherwise, add borough to the list of borough names (where count = 1)
		boroNames.add(boro);
	}
	
	/**
	 * Returns the total number of Tree objects stored in this TreeCollection
	 * 
	 * @return the number of trees in TreeCollection
	 */
	public int getTotalNumberOfTrees(){
		return size();
	}
	
	/**
	 * Returns the number of Tree objects in the TreeCollection whose species matches 
	 * the speciesName specified by the parameter. The method is case insensitive. 
	 * Returns zero if method is called with non-existent species.
	 *
	 * @param spcName species name to match with objects in the TreeCollection
	 * @return total number of Tree objects matching the specified species name 
	 * (return 0 if none found)
	 */
	public int getCountByTreeSpecies(String spcName){
		//get all matching species and store in a Collection object
		Collection<String> matchingSpecies = getMatchingSpecies(spcName);
		int totalCount = 0; //to keep track of total count of trees with matching species
		
		//for each species in the Collection
		for(String species : matchingSpecies){
			//count the number of Trees with given species and add to total count
			totalCount += countTreesInSpecies(species, root);
		}
		
		return totalCount;
	}
	
	/**
	 * Helper method to getCountByTreeSpecies. Counts the number of trees in the list 
	 * that have the exact same actual species as the first parameter.
	 * 
	 * @param species Actual species name to match with objects in the TreeCollection
	 * @param current Current node in search
	 * @return number of trees in the TreeCollection with the exact given species name
	 */
	private int countTreesInSpecies(String species, BSTNode<Tree> current){
		if(current == null) return 0;
		
		String currentSpecies = current.getData().getTreeSpecies();
		//if species is correct, call recursive method on both subtrees and increment count
		if(species.compareToIgnoreCase(currentSpecies) == 0){
			return countTreesInSpecies(species, current.getLeft()) 
					+ countTreesInSpecies(species, current.getRight()) + 1;
		} else {
			//if species less than current species, go left
			if(species.compareToIgnoreCase(currentSpecies) < 0){ 
				return countTreesInSpecies(species, current.getLeft());
			//if species greater than current species, go right
			} else { 
				return countTreesInSpecies(species, current.getRight());
			}
		}
	}
	
	/**
	 * Returns the number of Tree objects in the list that are located in the
	 * borough specified by the parameter (ignoring the case).
	 * 
	 * @param boroName the specified borough
	 * @return the number of Trees objects located in the specified borough 
	 * (return 0 if none found)
	 */
	public int getCountByBorough(String boroName){
		int count = 0;
		
		for(Borough b : boroNames){
			if(b.getName().equalsIgnoreCase(boroName))
				count = b.getCount();
		}
		
		return count;
		
	}
	
	/**
	 * Returns the number of Tree objects in the list whose species matches the 
	 * speciesName specified by the first parameter and which are located in the 
	 * borough specified by the second parameter.
	 * 
	 * @param spcName species name to match with objects in the TreeCollection
	 * @param boroName borough name to match with objects in the TreeCollection
	 * @return number of trees whose species and borough match the specified parameters 
	 * (return 0 if none found)
	 */
	public int getCountByTreeSpeciesBorough (String spcName, String boroName){
		Collection<String> matchingSpecies = getMatchingSpecies(spcName);
		int totalCount = 0;
		
		//for every species in the list of matching species
		for(String species : matchingSpecies){
			//count the number of Trees whose species and borough match parameters add to total count
			totalCount += countSpeciesBorough(species, boroName, root);
		}
		
		return totalCount;
	}
	
	/**
	 * Helper method to getCountByTreeSpeciesBorough. Counts the number of trees in the list that have the 
	 * exact same actual species as the first parameter and are located in the borough specified 
	 * by the second parameter.
	 * 
	 * @param species Actual species name to exactly match with objects in the TreeCollection
	 * @param boro Borough name to match with objects in the TreeCollection
	 * @param current Current node in search
	 * @return number of trees in the TreeCollection that have the exact given species name and 
	 * that are located in the specified borough
	 */
	private int countSpeciesBorough(String species, String boro, BSTNode<Tree> current){
		if(current == null) return 0;
		
		String currentSpecies = current.getData().getTreeSpecies();
		String currentBorough = current.getData().getBorough();
		
		//if correct species name
		if(species.compareToIgnoreCase(currentSpecies) == 0){
			//and correct borough name
			if(currentBorough.equalsIgnoreCase(boro))
				//increment count and call on both subtrees
				return countSpeciesBorough(species, boro, current.getLeft()) + countSpeciesBorough(species, boro, current.getRight()) + 1;
			//if incorrect borough, call on both subtrees but do not increment count 
			else
				return countSpeciesBorough(species, boro, current.getLeft()) + countSpeciesBorough(species, boro, current.getRight());
		//if incorrect species name
		} else {
			//if the species we are looking for is less than the current species, go left
			if(species.compareToIgnoreCase(currentSpecies) < 0){
				return countSpeciesBorough(species, boro, current.getLeft());
			//if the species we are looking for is greater than current species, go right
			} else { 
				return countSpeciesBorough(species, boro, current.getRight());
			}
		}
	}
	
	/**
	 * Returns a Collection object containing a list of all the actual tree 
	 * species that match a given parameter string species name. The actual species 
	 * matches speciesName if speciesName is a substring of the actual name (case
	 * insensitive). The returned list does not contain repeats.
	 * 
	 * @param speciesName species name to search for in the TreeCollection
	 * @return Collection object (in the form of an ArrayList) with a list of matching 
	 * species names
	 */
	Collection<String> getMatchingSpecies(String speciesName){ 
		Collection<String> actualSpecies = new ArrayList<String>();
		
		//loop through list of species names in TreeCollection
		for(String s : speciesInCollection){
			String fullSpcName = s.toLowerCase();
			
			//check if species name contains given parameter
			if(fullSpcName.contains(speciesName.toLowerCase())){
				//if species name not already in list, add it
				if(!actualSpecies.contains(fullSpcName))
					actualSpecies.add(fullSpcName);
			}
		}
		return actualSpecies;
	}
	
	/**
	 * Returns a string representation of the TreeCollection. First, the representation
	 * includes how many trees, species, and boroughs are represented in the 
	 * TreeCollection. Then, it includes how many trees are in each boroughs. Finally,
	 * it includes a list of the trees in the TreeCollection, which is ordered first
	 * alphabetically by species name and then by tree id. 
	 * 
	 * @return String representation of the TreeCollection
	 */
	@Override
	public String toString(){
		String intro = "This TreeCollection has " + size() + " trees in it from " + speciesInCollection.size() + " different species"
				+ " and " + boroNames.size() + " different boroughs.\n";

		//for each borough in the list of borough names
		for(Borough b : boroNames){
			//add "Bourough: # trees" to string representation
			intro += b.getName() + ": " + getCountByBorough(b.getName()) + " trees\n";
		}
		
		//create a list of all the trees in the TreeCollection 
		String list = "[";
		list += recInOrderTraversal(root);
		list += "]";
		
		return intro + list; //return the description plus the list of trees
	}
	
	/**
	 * Recursive helper method to toString method which returns an itemized list of
	 * the trees in the TreeCollection. Completes an inorder traversal of 
	 * the TreeCollection, adding the elements of the TreeCollection to the string 
	 * list of the trees. The order of the items is organized first by alphabetical 
	 * species name and then by tree id. The trees are listed with both their tree 
	 * species name and their borough. 
	 * 
	 * @param current Current node in traversal
	 * @return String list of elements (as list in brackets)
	 */
	private String recInOrderTraversal(BSTNode<Tree> current){
		StringBuilder newPart = new StringBuilder(); //will store new part to be added to list
		
		if(current != null){
			Tree currTree = current.getData();
			//traverse left side of TreeCollection
			newPart.append(recInOrderTraversal(current.getLeft()));
			//process current node
			newPart.append(currTree.getTreeSpecies() + " in " + currTree.getBorough());
			//unless current item is the last item, add comma between elements
			if(!(currTree.compareTo(last()) == 0))
				newPart.append(", ");
			//traverse right side of TreeCollection
			newPart.append(recInOrderTraversal(current.getRight()));
		}
		return newPart.toString(); //add new part to list
	}
}
