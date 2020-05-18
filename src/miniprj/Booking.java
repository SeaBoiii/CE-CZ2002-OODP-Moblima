package miniprj;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/** Represents a booking entry
 * @author Oh Yao Wen
 * @author Fong Hao Wei
 * @author Aleem Siddique
 * @author Alex Neo
 * @author Cheng Chee How
 * @version 1.0
 * @since 1.0
*/
public class Booking implements Serializable{
	
	/**
	 * Booking Identification number
	 */
	private String bookingID;
	/**
	 * Name of user who made the booking
	 */
	private String name;
	/**
	 * Contact number of user who made the booking
	 */
	private String contactNum;
	/**
	 * Email of user who made the booking
	 */
	private String email;
	/**
	 * Age of user who made the booking
	 */
	private int age;
	/**
	 * Status of booking
	 */
	private BookingStatus status;
	/**
	 * Date of booking
	 */
	private Date date;
	/**
	 * Payment details
	 */
	private PaymentEntry paymentEntry;
	/**
	 * List of all the movie tickets under this booking
	 */
	private List<MovieTicket> movieTickets;
	
	/**
	 * Construct booking object
	 * @param bk Booking ID
	 * @param pe Payment details
	 * @param status Booking status
	 * @param name Name of user that created the booking
	 * @param contact Contact number of user that created the booking
	 * @param email Email of user that created the booking
	 * @param age Age of the user that created the booking
	 * @param st Movie show time details
	 * @param movieTickets List of movie tickets
	 */
	public Booking(String bk,
			PaymentEntry pe,
			BookingStatus status, 
			String name,
			String contact,
			String email,
			int age, 
			ShowTimeItems st,
			List<MovieTicket> movieTickets){
		
		this.bookingID = bk; 
		this.paymentEntry = pe;
		this.status = status; 
		this.name = name;
		this.age = age;
		this.contactNum = contact;
		this.email = email;
		this.date = Calendar.getInstance().getTime();
		this.movieTickets = movieTickets;

		
		
	}
	/**
	 * Get Booking ID
	 * @return Returns bookingID
	 */
	public String getBookingID() {
		return bookingID;
	}
	/**
	 * Set Booking ID
	 * @param bookingID Booking Identification Number
	 */
	public void setBookingID(String bookingID) {
		this.bookingID = bookingID;
	}
	/**
	 * Get status of booking
	 * @return Returns a enum containing the booking status
	 */
	public BookingStatus getStatus() {
		return status;
	}
	/**
	 * Set booking status
	 * @param status BookingStatus enum value
	 */
	public void setStatus(BookingStatus status) {
		this.status = status;
	}
	/**
	 * Get name of user that created this booking
	 * @return Returns name of user as a string
	 */
	public String getName() {
		return name;
	}
	/**
	 * Get email of user that created this booking
	 * @return Returns email of user as a string
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * Get contact number of user that created this booking
	 * @return Returns contact number of user as a string
	 */
	public String getContact() {
		return contactNum;
	}
	/**
	 * Get date of creation for the booking
	 * @return Returns date of booking as a date object
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * Set date of booking
	 * @param date Date object containing the date to update
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * Get booking payment details
	 * @return Returns payment details as a PaymentEntry object
	 */
	public PaymentEntry getPaymentEtry() {
		return paymentEntry;
	}
	/**
	 * Set booking payment details
	 * @param paymentEntry Details of payment to be updated
	 */
	public void setPaymentEntry(PaymentEntry paymentEntry) {
		this.paymentEntry = paymentEntry;
	}
	/**
	 * Add movie tickets 
	 * @param ticket Movie ticket details
	 */
	public void addMovieTicket(MovieTicket ticket) {
		movieTickets.add(ticket);
	}
	/**
	 * Remove movie tickets from the booking
	 * @param ticket Movie ticket to remove
	 */
	public void removeMovieTicket(MovieTicket ticket) {
		movieTickets.remove(ticket);
	}
	/**
	 * Get all the movie tickets under this booking
	 * @return Returns a List of movie tickets
	 */
	public List<MovieTicket> getMovieTickets() {
		return movieTickets;
	}
	
}
