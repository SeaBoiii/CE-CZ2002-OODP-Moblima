package miniprj;

import java.io.Serializable;
import java.time.DayOfWeek;
/** Represents a movie ticket
 * @author Oh Yao Wen
 * @author Fong Hao Wei
 * @author Aleem Siddique
 * @author Alex Neo
 * @author Cheng Chee How
 * @version 1.0
 * @since 1.0
*/
public class MovieTicket implements Serializable{
	/**
	 * Movie title
	 */
	private String movieTitle;
	/**
	 * Ticket price
	 */
	private double price;
	/**
	 * Seat Identification number
	 */
	private String seatID;
	/**
	 * Date of the movie 
	 */
	private String date;
	/**
	 * Time of the movie
	 */
	private String time;
	/**
	 * Cineplex name
	 */
	private String cineplex;
	/**
	 * Cinema name
	 */
	private String cinema;
	/**
	 * Movie ticket identification number
	 */
	private String ticketID;
	/**
	 * Day of the week (MONDAY,TUESDAY etc..)
	 */
	private DayOfWeek day;
	/**
	 * Cinema class
	 */
	private CinemaClass cinemaClass;
	/**
	 * Movie type
	 */
	private MovieType movieType;
	
	/**
	 * Construct MovieTicket object
	 * @param movieTitle Movie Title
	 * @param price Movie ticket price
	 * @param seatID Seat allocation
	 * @param date Date of movie
	 * @param time Time of movie
	 * @param cineplex Cineplex name
	 * @param cinema Cinema name
	 * @param ticketID Ticket Identification Number
	 * @param day Day of the week (MONDAY,TUESDAY etc..)
	 * @param cc Cinema class
	 * @param mt Movie type
	 */
	public MovieTicket(String movieTitle, double price, String seatID, String date, String time, String cineplex, String cinema, String ticketID, DayOfWeek day, CinemaClass cc, MovieType mt) {
		this.movieTitle = movieTitle;
		this.price = price;
		this.seatID = seatID;
		this.date = date;
		this.time = time;
		this.cineplex = cineplex;
		this.cinema = cinema;
		this.ticketID = ticketID;
		this.day = day;
		this.cinemaClass = cc;
		this.movieType = mt;
	}
	
	/**
	 * Get movie title
	 * @return Returns movie title as a string
	 */
	public String getmovieTitle() {
		return movieTitle;
	}
	/**
	 * Set movie title
	 * @param movieTitle Movie title 
	 */
	public void setmovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}
	/**
	 * Get price 
	 * @return Returns Movie ticket price as a double
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * Set price of movie ticket
	 * @param price New price to set into movie ticket
	 */
	public void setPrice(double price) {
		this.price = price;
	}
	/**
	 * Get seat allocation
	 * @return Returns seat allocation as a string
	 */
	public String getSeatID() {
		return seatID;
	}
	/**
	 * Set seat allocation
	 * @param seatID Seat Identification number
	 */
	public void setSeatID(String seatID) {
		this.seatID=seatID;
	}
	/**
	 * Get date of the movie
	 * @return Returns movie date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * Set date of the movie ticket
	 * @param date Movie ticket date
	 */
	public void setDate(String date) {
		this.date=date;
	}
	
	/**
	 * Get time of movie ticket
	 * @return Returns time of movie ticket
	 */
	public String getTime(){
		return time;
	}
	/**
	 * Set time of movie ticket
	 * @param time New time value for movie ticket
	 */
	public void setTime(String time) {
		this.time = time;
	}
	/**
	 * Get cineplex name
	 * @return Returns cineplex name 
	 */
	public String getCineplex() {
		return cineplex;
	}
	/**
	 * Set cineplex name 
	 * @param cineplex New cineplex name
	 */
	public void setCineplex(String cineplex) {
		this.cineplex = cineplex;
	}public String getCinema() {
		return cinema;
	}
	/**
	 * Set cinema name
	 * @param cinema New cinema name
	 */
	public void setCinema(String cinema) {
		this.cinema= cinema;
	}
}
