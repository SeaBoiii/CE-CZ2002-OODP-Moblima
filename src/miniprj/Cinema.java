package miniprj;

import java.io.Serializable;

/** Represents a cinema 
 * @author Oh Yao Wen
 * @author Fong Hao Wei
 * @author Aleem Siddique
 * @author Alex Neo
 * @author Cheng Chee How
 * @version 1.0
 * @since 1.0
*/

public class Cinema implements Serializable{

	private static final long serialVersionUID = 1L;
	/**
	 * Cinema Identification Number
	 */
	private int cinemaID;
	/**
	 * Name of cinema
	 */
	private String cinemaName;
	/**
	 * Class of cinema
	 */
	private CinemaClass cinemaClass;
	/**
	 * List to store all the seat information
	 */
	private Seat[][] seats;
	/**
	 * Number of seats inside the cinema
	 */
	private int noOfSeats = 0;
	/**
	 * Number of seats per row
	 */
	private int noOfSeatsPerRow = 0;
	
	/**
	 * Construct cinema object
	 * @param id Cinema Identification Number
	 * @param cc Cinema Class
	 * @param name Name of Cinema
	 * @param numSeats Number of seats inside the cinema
	 * @param numSeatsPerRow Number of seats per row
	 */
	public Cinema(int id, CinemaClass cc, String name, int numSeats, int numSeatsPerRow) {
		cinemaID = id;
		cinemaClass = cc;
		cinemaName = name;
		noOfSeats = numSeats;
		noOfSeatsPerRow = numSeatsPerRow;
		
		int r,frontRow=noOfSeatsPerRow, offset=0;
		r = noOfSeats/noOfSeatsPerRow;
		
		if(r == 0) {
			seats = new Seat[1][noOfSeatsPerRow];
			frontRow=noOfSeats;
			if((noOfSeatsPerRow-frontRow)%2 == 0){
				offset = (noOfSeatsPerRow-frontRow)/2;
			}else {
				offset = noOfSeatsPerRow-frontRow;
			}
		}else if(noOfSeats%noOfSeatsPerRow != 0){
			seats = new Seat[r+1][noOfSeatsPerRow];
			frontRow = noOfSeats%noOfSeatsPerRow;
			if((noOfSeatsPerRow-frontRow)%2 == 0){
				offset = (noOfSeatsPerRow-frontRow)/2;
			}else {
				offset = noOfSeatsPerRow-frontRow;
			}
		}else {
			seats = new Seat[r][noOfSeatsPerRow];
		}
		
		char a = (char) ('A'+(seats.length-1));
		for(int i=0;i<seats.length;i++) {
			
			for(int j=0;j<seats[i].length;j++) {
				if(i == 0 && (j < offset || j > frontRow+offset-1)) {
					seats[i][j]=null;
				}else {
					seats[i][j]=new Seat(a+Integer.toString(j+1));
				}
			}
			a--;
		}
		
		
		
	}
	/**
	 * Get Cinema Identification Number
	 * @return Returns cinema identification number as a integer
	 */
	public int getCinemaID() {
		return cinemaID;
	}
	
	/**
	 * Get cinema name 
	 * @return Returns cinema name as a string
	 */
	public String getCinemaName() {
		return cinemaName;
	}
	/**
	 * Get number of seats inside the cinema
	 * @return Returns the number of seats as a integer
	 */
	public int getNoOfSeats() {
		return noOfSeats;
	}
	/**
	 * Get number of seats per row
	 * @return Returns the number of seats per row as a integer
	 */
	public int getNoOfSeatsPerRow() {
		return noOfSeatsPerRow;
	}
	/**
	 * Get array of all the seat objects
	 * @return Returns 2D array with all the seat object
	 */
	public Seat[][] getSeatLayout() {
		return seats;
	}
	/**
	 * Get class of cinema
	 * @return Returns class of cinama as a CinemaClass enum
	 */
	public CinemaClass getCinemaClass() {
		return cinemaClass;
	}
	/**
	 * Set cinema name
	 * @param name New name for cinema
	 */
	public void setCinemaName(String name) {
		cinemaName = name;
	}
	/**
	 * Set class for cinema 
	 * @param cc New cinema class for cinema
	 */
	public void setCinemaClass(CinemaClass cc) {
		cinemaClass = cc;
	}
	/**
	 * Check valid seatID
	 * @param seat Seat Identification number
	 * @return Returns TRUE if seat number is inside cinema layout, otherwise FALSE
	 */
	public boolean checkValidSeat(String seat) {
		for(int i=0;i<seats.length;i++) {
			for(int k=0;k<seats[i].length;k++) {
				if(seats[i][k] != null) {
					if(seats[i][k].getSeatID().compareTo(seat)==0) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
	
}
