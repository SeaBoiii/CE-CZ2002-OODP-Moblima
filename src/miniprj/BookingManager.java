package miniprj;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/** Control class to manage booking related tasks
 * @author Oh Yao Wen
 * @author Fong Hao Wei
 * @author Aleem Siddique
 * @author Alex Neo
 * @author Cheng Chee How
 * @version 1.0
 * @since 1.0
*/
public class BookingManager implements Serializable, FileMethods{

	/**
	 * Booking fee
	 */
	private static double bookingFee = 1.5d;
	/**
	 * Goods and services tax
	 */
	private static double GST = 1.07d;

	/**
	 * Payment object for processing user's payment
	 */
	private PaymentManager pay = new PaymentManager();
	/**
	 * List of all the bookings
	 */
	private ArrayList<Booking> booking = new ArrayList<Booking>();

	/**
	 * Construct BookingManger object
	 */
	public BookingManager() {
		booking.clear();
		booking.addAll(FileIO.readFile(Directory.DIR_BOOKING));
	}

	/**
	 * Get booking fee
	 * @return Returns a booking fee as a double value
	 */
	public double getBookingFee() {
		return bookingFee;
	}
	/**
	 * Get Booking Identification Number
	 * @return Returns BookingID as a String
	 */
	public String getBookingID() {
		return booking.get(booking.size()-1).getPaymentEtry().getPaymentId();
	}

	/**
	 * Get all the bookings
	 * @return Returns list of bookings as a ArrayList
	 */
	public ArrayList<Booking> getBookingList(){
		return booking;
	}

	/**
	 * Create new booking and store inside booking list
	 * @param name Name of user
	 * @param contact Contact number of user
	 * @param email Email of user
	 * @param age Age of user
	 * @param showTimeItem Show time details
	 * @param seatStringArray Array of all the seat positions
	 * @param ticketprice Price for a single ticket
	 * @param totalprice Total price for all tickets including GST
	 * @param paymentmethod Payment method selected by user
	 * @return Returns booking entry as a Booking object
	 */
	public Booking createBooking(String name, String contact, String email, int age, ShowTimeItems showTimeItem, String[] seatStringArray, double ticketprice, double totalprice ,PaymentType paymentmethod){
		PaymentEntry paymentEntry;
		List<MovieTicket> movieTicketList;
		MovieTicket movieTicket; //may need to edit raw code

		//to get back the cinema we are in.
		String cinema = showTimeItem.getCinema();
		Date current = Calendar.getInstance().getTime();

	    Calendar calendar = Calendar.getInstance();
	    int day = calendar.get(Calendar.DAY_OF_WEEK);
	    DayOfWeek day_week = null;
	    switch(day){
	    	case 0:
	    		day_week = DayOfWeek.SUNDAY;
	    		break;
	    	case 1:
	    		day_week = DayOfWeek.MONDAY;
	    		break;
	    	case 2:
	    		day_week = DayOfWeek.TUESDAY;
	    		break;
	    	case 3:
	    		day_week = DayOfWeek.WEDNESDAY;
	    		break;
	    	case 4:
	    		day_week = DayOfWeek.THURSDAY;
	    		break;
	    	case 5:
	    		day_week = DayOfWeek.FRIDAY;
	    		break;
	    	case 6:
	    		day_week = DayOfWeek.SATURDAY;
	    		break;
	    }

		//to get to the part 2 of the corresponding BookingID as required from Assignment
		SimpleDateFormat dateTime =  new SimpleDateFormat("yyyyMMddHHmm");

		Cineplex cplex = Manager.cm.getCineplex(showTimeItem.getCineplex());

		Cinema cinemaObj = cplex.getCinema(showTimeItem.getCinema());

		int cinemaId = cinemaObj.getCinemaID(); //need to get cinemaId from Alex/Aleem
		String dateTimeId = dateTime.format(current.getTime());

		//BookingID is derived from cinemaId and dateTimeId ("XXXXyyyyMMddHHmm")
		String BookingID = cinemaId + dateTimeId;


		int ticket_quantity = seatStringArray.length;

		paymentEntry = pay.createPayment(BookingID, totalprice, paymentmethod);

		movieTicketList = new ArrayList<MovieTicket>();
		//create movie ticket
		for(String seatNo : seatStringArray) {

			//need to create movie ticket
			//String movieTitle, double price, String seatID, String date, String time, String cineplex, String cinema, String ticketID, Day day, CinemaClass cc, MovieType mt
			movieTicketList.add(new MovieTicket(showTimeItem.getMovie(),ticketprice ,seatNo,showTimeItem.getDate(),showTimeItem.getTiming(),showTimeItem.getCineplex(),showTimeItem.getCinema(),BookingID ,day_week,cinemaObj.getCinemaClass(),Manager.mm.getMovie(showTimeItem.getMovie()).getMovieType()));

		}

		//create booking
		Booking bookingEntry = new Booking(String.valueOf(booking.size()-1), paymentEntry, BookingStatus.ACCEPTED,name,contact,email,age,showTimeItem, movieTicketList);
	    booking.add(bookingEntry);

		FileIO.saveToFile(booking, Directory.DIR_BOOKING);
		reloadData();

		return booking.get(booking.size()-1);


	}

	/**
	 * Get booking from booking list
	 * @param bookingID Booking Identification
	 * @return Returns booking entry as a Booking object
	 */
	public Booking getBooking(String bookingID) {
		for(Booking i : booking) {
			if(i.getBookingID().equals(bookingID));
			{
				return i;
			}
		}
		return null;
	}

	/**
	 * Check if booking exists
	 * @param date Date of booking
	 * @param cineplex Cineplex name
	 * @param cinema Cinema name
	 * @param time Time of booking
	 * @param seatID Seat Identification
	 * @return Returns TRUE if booking exist otherwise FALSE
	 */
	public boolean checkBooking(String date, String cineplex, String cinema, String time, String seatID) {

		List<MovieTicket> temp;

		for(int i=0;i<booking.size();i++) {
			temp = booking.get(i).getMovieTickets();
			for (int k=0;k<temp.size();k++) {
				if(temp.get(k).getCineplex().compareTo(cineplex) == 0
						&&temp.get(k).getCinema().compareTo(cinema) == 0
						&&temp.get(k).getDate().compareTo(date) == 0
						&&temp.get(k).getTime().compareTo(time) == 0
						&&temp.get(k).getSeatID().compareTo(seatID) == 0) {

					return true;

				}
			}
		}

		return false;
	}

	/**
	 * Print out booking list base on user details
	 * @param name Name of user
	 * @param contactnum Contact number of user
	 * @param email Email of user
	 */
	public void printBookingList(String name, String contactnum, String email) {
		System.out.println("==================================================");
		System.out.println("               YOUR BOOKING DETAILS               ");
		System.out.println("==================================================");

		for(int i=0;i<booking.size();i++) {
			if(booking.get(i).getName().compareTo(name) == 0 && booking.get(i).getContact().compareTo(contactnum) == 0 && booking.get(i).getEmail().compareTo(email) == 0) {
				System.out.println("["+ (i+1) +"] " + "TID" + booking.get(i).getPaymentEtry().getPaymentId() +booking.get(i).getDate().toString());
				for(MovieTicket j : booking.get(i).getMovieTickets() ){
					System.out.println(j.getSeatID());
				}
				System.out.println();
			}
		}

	}
	/**
	 * Print out booking list for ALL 
	 * @return 1 if Successful, otherwise 07
	 */
	public int printBookingListbyAll() {
		if(booking.size()==0) {
			return 0;
		}
		System.out.println("==================================================");
		System.out.println("               YOUR BOOKING DETAILS               ");
		System.out.println("==================================================");

		for(int i=0;i<booking.size();i++) {
		 {
				System.out.println("["+ (i+1) +"]");
				System.out.println(booking.get(i).getDate().toString());
				System.out.println("========================================================================");
				System.out.printf("%-30s : %-30s\n", "Booking Ref No", booking.get(i).getBookingID());
				System.out.printf("%-30s : %-30s\n", "TID", booking.get(i).getPaymentEtry().getPaymentId());
				System.out.printf("%-30s : %-30s\n", "Movie Title", booking.get(i).getMovieTickets().get(0).getmovieTitle());
				System.out.printf("%-30s : %-30s\n", "Cineplex", booking.get(i).getMovieTickets().get(0).getCineplex());
				System.out.printf("%-30s : %-30s\n", "Cinema", booking.get(i).getMovieTickets().get(0).getCinema());
				System.out.printf("%-30s : %-30s\n", "Date", booking.get(i).getMovieTickets().get(0).getDate());
				System.out.printf("%-30s : %-30s\n", "Time", booking.get(i).getMovieTickets().get(0).getTime());
				
				System.out.printf("%-30s : ", "Seats");
				for(int j=0 ; j < booking.get(i).getMovieTickets().size() ; j++) {
					System.out.print(booking.get(i).getMovieTickets().get(j).getSeatID() + ". ");
				}
				System.out.println();
				System.out.printf("%-30s : %.2f\n", "Total Amount", CommandUI.round(booking.get(i).getPaymentEtry().getPrice(), 2));
				System.out.println();
			}
		}
		return 1;
	}
	/**
	 * Print out booking list base on booking ID
	 * @param bookingid Name of user
	 * @return 1 if Successful, otherwise 07
	 */
	public int printBookingListbyRefNo(String bookingid) {
		int list =0;
		for(int j= 0 ; j < booking.size() ; j++) {
			if(booking.get(j).getPaymentEtry().getPaymentId().compareTo(bookingid)==0) {
				list += 1;
			}
		}
		if(list == 0) {
			return 0;
		}
		System.out.println("==================================================");
		System.out.println("               YOUR BOOKING DETAILS               ");
		System.out.println("==================================================");

		for(int i=0;i<booking.size();i++) {
			if(booking.get(i).getPaymentEtry().getPaymentId().compareTo(bookingid)==0) {
				System.out.println("["+ (i+1) +"]");
				System.out.println(booking.get(i).getDate().toString());
				System.out.println("========================================================================");
				System.out.printf("%-30s : %-30s\n", "Booking Ref No", booking.get(i).getBookingID());
				System.out.printf("%-30s : %-30s\n", "TID", booking.get(i).getPaymentEtry().getPaymentId());
				System.out.printf("%-30s : %-30s\n", "Movie Title", booking.get(i).getMovieTickets().get(0).getmovieTitle());
				System.out.printf("%-30s : %-30s\n", "Cineplex", booking.get(i).getMovieTickets().get(0).getCineplex());
				System.out.printf("%-30s : %-30s\n", "Cinema", booking.get(i).getMovieTickets().get(0).getCinema());
				System.out.printf("%-30s : %-30s\n", "Date", booking.get(i).getMovieTickets().get(0).getDate());
				System.out.printf("%-30s : %-30s\n", "Time", booking.get(i).getMovieTickets().get(0).getTime());
				
				System.out.printf("%-30s : ", "Seats");
				for(int j=0 ; j < booking.get(i).getMovieTickets().size() ; j++) {
					System.out.print(booking.get(i).getMovieTickets().get(j).getSeatID() + ". ");
				}
				System.out.println();
				System.out.printf("%-30s : %.2f\n", "Total Amount", CommandUI.round(booking.get(i).getPaymentEtry().getPrice(), 2));
				System.out.println();
			}
		}
		return 1;
	}
	/**
	 * Print out booking list base on Email
	 * @param email Email of user
	 * @return Return 1 when successful, 0 otherwise
	 */
	public int printBookingListbyEmail(String email) {
		int list =0;
		for(int j= 0 ; j < booking.size() ; j++) {
			if(booking.get(j).getEmail().compareTo(email)==0) {
				list += 1;
			}
		}
		if(list == 0) {
			return 0;
		}
		else {

			System.out.println("==================================================");
			System.out.println("               YOUR BOOKING DETAILS               ");
			System.out.println("==================================================");

			for(int i=0;i<booking.size();i++) {
				if(booking.get(i).getEmail().compareTo(email) == 0) {
					System.out.println("["+ (i+1) +"]");
					System.out.println(booking.get(i).getDate().toString());
					System.out.println("========================================================================");
					System.out.printf("%-30s : %-30s\n", "Booking Ref No", booking.get(i).getBookingID());
					System.out.printf("%-30s : %-30s\n", "TID", booking.get(i).getPaymentEtry().getPaymentId());
					System.out.printf("%-30s : %-30s\n", "Movie Title", booking.get(i).getMovieTickets().get(0).getmovieTitle());
					System.out.printf("%-30s : %-30s\n", "Cineplex", booking.get(i).getMovieTickets().get(0).getCineplex());
					System.out.printf("%-30s : %-30s\n", "Cinema", booking.get(i).getMovieTickets().get(0).getCinema());
					System.out.printf("%-30s : %-30s\n", "Date", booking.get(i).getMovieTickets().get(0).getDate());
					System.out.printf("%-30s : %-30s\n", "Time", booking.get(i).getMovieTickets().get(0).getTime());
					
					System.out.printf("%-30s : ", "Seats");
					for(int j=0 ; j < booking.get(i).getMovieTickets().size() ; j++) {
						System.out.print(booking.get(i).getMovieTickets().get(j).getSeatID() + ". ");
					}
					System.out.println();
					System.out.printf("%-30s : %.2f\n", "Total Amount", CommandUI.round((booking.get(i).getPaymentEtry().getPrice()),2));
					System.out.println();
				}
			}
		}
		return 1;
	}

	/**
	 * Print out booking list base on contact number
	 * @param contact Contact number of user
	 * @return Return 1 when successful, 0 otherwise
	 */
	public int printBookingListbyContact(String contact) {
		int list =0;
		for(int j= 0 ; j < booking.size() ; j++) {
			if(booking.get(j).getContact().compareTo(contact)==0) {
				list += 1;
			}
		}
		if(list == 0) {
			return 0;
		}
		else {

			System.out.println("==================================================");
			System.out.println("               YOUR BOOKING DETAILS               ");
			System.out.println("==================================================");

			for(int i=0;i<booking.size();i++) {
				if(booking.get(i).getContact().compareTo(contact)==0) {
					System.out.println();
					System.out.println("["+ (i+1) +"]  "+booking.get(i).getDate().toString());
					System.out.println("========================================================================");
					System.out.printf("%-30s : %-30s\n", "Booking Ref No", booking.get(i).getBookingID());
					System.out.printf("%-30s : %-30s\n", "TID", booking.get(i).getPaymentEtry().getPaymentId());
					System.out.printf("%-30s : %-30s\n", "Movie Title", booking.get(i).getMovieTickets().get(0).getmovieTitle());
					System.out.printf("%-30s : %-30s\n", "Cineplex", booking.get(i).getMovieTickets().get(0).getCineplex());
					System.out.printf("%-30s : %-30s\n", "Cinema", booking.get(i).getMovieTickets().get(0).getCinema());
					System.out.printf("%-30s : %-30s\n", "Date", booking.get(i).getMovieTickets().get(0).getDate());
					System.out.printf("%-30s : %-30s\n", "Time", booking.get(i).getMovieTickets().get(0).getTime());
					
					System.out.printf("%-30s : ", "Seats");
					for(int j=0 ; j < booking.get(i).getMovieTickets().size() ; j++) {
						System.out.print(booking.get(i).getMovieTickets().get(j).getSeatID() + ". ");
					}
					System.out.println();
					System.out.printf("%-30s : %.2f\n", "Total Amount", CommandUI.round(booking.get(i).getPaymentEtry().getPrice(), 2));
					System.out.println();
				}
			}
		}
		return 1;
	}

	/**
	 * Get payment identification by booking ID
	 * @param choice Booking Identification Number
	 * @return Returns payment identification number
	 */
	public String bookingselect(int choice) {
		for(int i=0;i<booking.size();i++) {
			if(booking.get(i).getPaymentEtry().getPaymentId().equals(booking.get(choice).getPaymentEtry().getPaymentId())){
				return booking.get(choice).getPaymentEtry().getPaymentId();
			}
		}
		return null;
	}


	/**
	 * Update movie tickets cineplex name if show time cineplex name is updated
	 * @param oldname Old cineplex name
	 * @param newname New cineplex name
	 */
	public void updateTicketCineplexNameForShowTime(String oldname, String newname) {

		List<MovieTicket> temp;
		Date current = Calendar.getInstance().getTime();

		for(int i=0;i<booking.size();i++) {
			if(booking.get(i).getDate().after(current)) {
				temp = booking.get(i).getMovieTickets();

				for(int k=0;k<temp.size();k++) {
					if(temp.get(k).getCineplex().compareTo(oldname) == 0) {
						temp.get(k).setCineplex(newname);
					}else {
						break;
					}
				}
			}
		}

		saveAndReloadData();
	}

	/**
	 * Update movie tickets cinema name if show time cinema name is updated
	 * @param oldname Old cinema name
	 * @param newname New cinema name
	 */
	public void updateTicketCinemaNameForShowTime(String oldname, String newname) {

		List<MovieTicket> temp;
		Date current = Calendar.getInstance().getTime();

		for(int i=0;i<booking.size();i++) {
			if(booking.get(i).getDate().after(current)) {
				temp = booking.get(i).getMovieTickets();

				for(int k=0;k<temp.size();k++) {
					if(temp.get(k).getCinema().compareTo(oldname) == 0) {
						temp.get(k).setCinema(newname);
					}else {
						break;
					}
				}
			}
		}

		saveAndReloadData();
	}

	/**
	 * Delete booking from booking list base on booking ID
	 * @param bookingID Booking identification number
	 */
	public void deleteBooking(String bookingID) {
		Date current = Calendar.getInstance().getTime();
		for(int i= 0 ; i < booking.size() ; i++) {
			if(booking.get(i).getPaymentEtry().getDate().compareTo(current)<0 && booking.get(i).getPaymentEtry().getPaymentId().compareTo(bookingID)==0 && booking.size() != 0){
				booking.remove(i);
				saveAndReloadData();
				System.out.println("===============================");
				System.out.println(" BOOKING SUCCESSFULLY REMOVED");
				break;
			}
		}
		return;
	}


	@Override
	public void reloadData() {
		booking.clear();
		booking.addAll(FileIO.readFile(Directory.DIR_BOOKING));

	}

	@Override
	public void saveAndReloadData() {
		FileIO.saveToFile(booking, Directory.DIR_BOOKING);
		reloadData();

	}

}
