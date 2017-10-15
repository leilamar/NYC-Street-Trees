/**
 * This class hosts the main method which runs the program. The program takes a 
 * csv file as an argument, stores the trees from that file in a TreeCollection object, 
 * and allows users to search for tree species in NYC from the file. The program prints out 
 * a list of matching species and the popularity of these species in each borough and 
 * NYC as a whole. Program keeps running in a loop until the user enters "quit".
 * 
 * @author Leila Mardoum
 * @version 4/22/17 
 */


import java.util.*;
import java.io.*;

public class NYCStreetTrees {
	
	public static void main(String[] args) {
		
		//check to see if user has entered a file name as command line argument
		if(args.length == 0){
			System.err.println("Usage Error: the program expects a file name as an argument");
			System.exit(1);
		}
		
		File file = new File(args[0]);
		
		//create Scanner object, but catch exceptions Scanner might throw 
		Scanner input = null;
		try{
			input = new Scanner(file);
		} catch(Exception e) {
			System.err.println(e.getMessage()); //print specific error message
			System.exit(1);
		}
		
		TreeCollection trees = new TreeCollection();
		input.nextLine(); //skip data headers in file
		
		//read file line-by-line
		while(input.hasNextLine()){
			String textLine = input.nextLine();
			ArrayList<String> treeInfo = splitCSVLine(textLine);
			
			if(treeInfo.size() != 41)
				continue; //if there are not 41 data slots, tree is invalid, skip line
			
			Tree entry;
			
			//add tree entry to the TreeList if the entry is valid
			try {
				entry = new Tree(Integer.parseInt(treeInfo.get(0)), Integer.parseInt(treeInfo.get(3)), 
						treeInfo.get(6), treeInfo.get(7), treeInfo.get(9), Integer.parseInt(treeInfo.get(25)), 
						treeInfo.get(29), Double.parseDouble(treeInfo.get(39)), Double.parseDouble(treeInfo.get(40)));
			} catch(IllegalArgumentException e) {
				continue; //if entry invalid, skip line
			}
			
			trees.add(entry); //if entry is valid, add entry to list
		}
		
		input.close();
		
		//loop that allows user to check popularity of different tree names (or exit program)
		boolean userWantsToQuit = false;
		Scanner user = new Scanner(System.in);
		
		while(!userWantsToQuit){
			//Ask for and process user request for a species name (or quit)
			System.out.println("Enter a tree species to learn more about it (\"quit\" to stop): ");
			String userRequest = user.nextLine();
			
			//if user enters "quit", exit loop
			if(userRequest.toLowerCase().contains("quit")){
				userWantsToQuit = true;
				user.close();
				continue;
			} else {
				int spcCount = trees.getCountByTreeSpecies(userRequest);
				
				//if no results, print appropriate message
				if(spcCount == 0){
					System.err.println("There are no records of " + userRequest + " on NYC streets");
					System.out.println();
					continue;
				} else { 
					//otherwise, make a list of matching species, without repeats
					Collection<String> spcSearchResult = trees.getMatchingSpecies(userRequest);
					System.out.println("All matching species: ");
					
					//print out list of matching species
					for(String spc: spcSearchResult) {
						System.out.println("   " + spc); 
					}
					System.out.println();
					
					System.out.println("Popularity in the city:");
					
					String[] validAreas = {"NYC", "Manhattan", "Bronx", "Brooklyn", "Queens", "Staten Island"};
					
					//for each valid area, print popularity info
					for(int i = 0; i < validAreas.length; i++){
						getPopularity(trees, validAreas[i], userRequest);
					}
					
					System.out.println();
					continue; 
				}
			}
		}
	}
	
	/**
	 * This method will print species popularity for a given area, formatted in columns. 
	 * The information printed is the area name, the species abundance in the area, 
	 * the total trees in the area, and the percentage of trees in the area that are the 
	 * given species.
	 * 
	 * @param trees TreeList containing values to be searched
	 * @param area NYC plus the five boroughs
	 * @param userRequest the user species request used to find how many trees 
	 * of the species are in each borough
	 */
	public static void getPopularity(TreeCollection trees, String area, String userRequest){
		
		
		int speciesAmount; //number of trees of specified species in area
		int totalAmount; //number of trees in area
		double percentage; //percentage of specified tree species in area
		
		//check if area is NYC (must use getTotalNumberOfTrees() instead of getCountByBorough())
		if(area.equals("NYC")){
			speciesAmount = trees.getCountByTreeSpecies(userRequest);
			totalAmount = trees.getTotalNumberOfTrees();
			//check to see if dividing by 0
			if(trees.getTotalNumberOfTrees() == 0){
				//if no trees, percentage should be 0
				percentage = 0; 
			} else {
				//otherwise, compute percentage normally
				percentage = (double) speciesAmount/totalAmount * 100;
			}
		} else { 
			//for all boroughs, do the same but using getCountByBorough() method
			speciesAmount = trees.getCountByTreeSpeciesBorough(userRequest, area);
			totalAmount = trees.getCountByBorough(area);
			if(trees.getCountByBorough(area) == 0){
				percentage = 0;
			} else {
				percentage = (double) speciesAmount/totalAmount * 100;
			}
		}
		//print out popularity in appropriate format
		System.out.printf("%-15s:  " + "%,d" + " (" + "%,d" + ")        %s%.2f%%%n", 
				area, speciesAmount, totalAmount, "\t" , percentage);
	}
	
	/**
	 * Splits the given line of a CSV file according to commas and double quotes
	 * (double quotes are used to surround multi-word entries that may contain commas). 
	 * 
	 * @param textLine  line of text to be parsed
	 * @return an ArrayList object containing all individual entries/tokens
	 *         found on the line.
	 * @author Joanna Klukowska
	 */
	public static ArrayList<String> splitCSVLine(String textLine) {
		ArrayList<String> entries = new ArrayList<String>();
		int lineLength = textLine.length();
		StringBuffer nextWord = new StringBuffer();
		char nextChar;
		boolean insideQuotes = false;
		boolean insideEntry= false;
		
		//iterate over all characters in the textLine
		for (int i = 0; i < lineLength; i++) {
			nextChar = textLine.charAt(i);
			
			//handle smart quotes as well as regular quotes 
			if (nextChar == '"' || nextChar == '\u201C' || nextChar =='\u201D') { 
				//change insideQuotes flag when nextChar is a quote
				if (insideQuotes) {
					insideQuotes = false;
					insideEntry = false; 
				}
				else {
					insideQuotes = true; 
					insideEntry = true; 
				}
			}
			else if (Character.isWhitespace(nextChar)) {
				if  ( insideQuotes || insideEntry ) {
					// add it to the current entry
					nextWord.append( nextChar );
				}
				else  { // skip all spaces between entries 
					continue;
				}
			}
			else if ( nextChar == ',') {
				if (insideQuotes) //comma inside an entry 
					nextWord.append(nextChar);
				else { //end of entry found 
					insideEntry = false; 
					entries.add(nextWord.toString());
					nextWord = new StringBuffer();
				}
			}
			else {
				//add all other characters to the nextWord 
				nextWord.append(nextChar);
				insideEntry = true; 
			}

		}
		// add the last word (assuming not empty)
		// trim the white space before adding to the list
		if (!nextWord.toString().equals("")) {
			entries.add(nextWord.toString().trim());
		}

		return entries;
	}

}
