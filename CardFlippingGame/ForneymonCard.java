/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * File name  :  ForneymonCard.java
 * Purpose    :  Set up the Forneymon Cards 
 * @author    :  Salem Tesfu 
 * Date       :  2019-09-11
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

package forneymon.cardgame;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic card class for the Forneymon Trading Card game
 */
abstract public class ForneymonCard {
	
	private String name; 
	private String type; 
	private List<String> forneymonTypes = new ArrayList<>();
	
 /**
  *  Default Constructor that instantiates the name and type of the Forneymon
  *  in the absence of parameters 
  */
	public ForneymonCard() {
		this.name = "MissingNu"; 
		this.type = "Burnymon"; 
	}
	
/**
 *  Constructor that instantiates the name and type of Forneymon with the use
 *  of inputed parameters
 *  @param name The name of the given ForneymonCard
 *  @param type The type of the given ForneymonCard (ex. Burnymon) 
 */
	public ForneymonCard(String name, String type) {
		
		forneymonTypes.add("Burnymon");
		forneymonTypes.add("Dampymon");
		forneymonTypes.add("Leafymon");
		
		if(!(forneymonTypes.contains(type))) {
			throw new IllegalArgumentException();
		}
		if(name.isBlank()) {
			throw new IllegalArgumentException();
		}
		this.name = name;
		this.type = type;
	}
	
/**
 * Method to seek the name of the ForneymonCard
 * @return The name (string) of given ForneymonCard
 */
	public String getName() {return name;}
	
/**
 * Method to seek the type of the ForneymonCard
 * @return The type (string) of given ForneymonCard
 */
	public String getType() {return type;}
	
/**
 * Method to provide the name and type of the ForneymonCard 
 * @return A string of the Forneymon's type and name
 */	
	public String toString() {return(type + ": " + name);}
	
}




