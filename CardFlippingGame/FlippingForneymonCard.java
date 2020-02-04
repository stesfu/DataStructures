/** ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 * File name  :  FlippingForneymonCard.java
 * Purpose    :  A card flipping game that utilizes ForneymonCard 
 * @author    :  Salem Tesfu 
 * Date       :  2019-09-11
 *  ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ */

package forneymon.cardgame;

/**
 * Basic card class for the Forneymon Card Matching game
 */
public class FlippingForneymonCard extends ForneymonCard {
	
	private boolean face;
	
/**
 *  Default Constructor inherited from ForneymonCard 
 *  that also instantiates face orientation in addition to 
 *  the default name and type 
 */
	public FlippingForneymonCard(){
		super(); 
		this.face = true; 
	}
	
/**
 *  Constructor inherited from ForneymonCard 
 *  that also instantiates face orientation in addition to 
 *  the inputed name and type
 *  @param name The name of the given FlippingForneymonCard
 *  @param type The type of the given FlippingForneymonCard (ex.Burnymon) 
 *  @param face The face orientation of the given FlippingForneymonCard (ex.down)
 */
	public FlippingForneymonCard(String name, String type, boolean face){
		super(name,type); 
		this.face = face; 
	}
	
/**
 *  Method that reverses the face orientation of a FlippingForneymonCard 
 *  @return face The face orientation of the given FlippingForneymonCard (ex.down)
 */  
	public boolean flip(){
		face = !face; 
		return(face); 
	}
	
/**
 *  Method that compares the face orientation of two 
 *  different FlippingForneymonCards
 *  @param FlippingForneymonCard A second ForneymonCard to compare
 *  @return A boolean value dependent on if they are divergent or both face-up/ face-down 
 */  
   public int match(FlippingForneymonCard other) {
	   
	   boolean sameType = false; 
	   boolean sameName = false; 
	   int matchValue = 0;
	   
	   if (this.getType().contentEquals(other.getType())) {
		   sameType = true; 
	   }
	   if (this.getName().contentEquals(other.getName())) {
		   sameName = true; 
	   }
	   if(!sameType || !sameName) {
		   matchValue = 0;
	   }
	   if(sameType && sameName) {
		   matchValue =  1; 
	   }
	   if((this.face) || (other.face)) {
		   matchValue = 2; 
	   }
	   return matchValue; 
   }
   
/**
 * Method from ForneymonCard to provide the name and type
 * of the ForneymonCard with a additional string for a face
 * down orientation   
 * @return A string of the Forneymon's type and name
 */
   public String toString() {
	   if(this.face) {
		   return "?: ?";
	   }
		return super.toString();	
	}
 
}
