package miniprj;

import java.io.Serializable;
/**Represents a seat inside a cinema
 * @author Oh Yao Wen
 * @author Fong Hao Wei
 * @author Aleem Siddique
 * @author Alex Neo
 * @author Cheng Chee How
 * @version 1.0
 * @since 1.0
*/
public class Seat implements Serializable{

	/**
	 * Seat idnetification number
	 */
	private String seatID;
	
	/**
	 * Construct seat 
	 * @param seatID Seat identification number
	 */
	public Seat(String seatID){
		this.seatID = seatID;
	}
	/**
	 * Get seat identification number
	 * @return Returns seat identification number
	 */
	public String getSeatID() {
		return seatID;
	}
}
