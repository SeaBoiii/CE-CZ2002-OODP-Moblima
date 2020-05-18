package miniprj;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;



/** Represents the menu for the customer to use when creating a new booking.
 * @author Oh Yao Wen
 * @author Fong Hao Wei
 * @author Aleem Siddique
 * @author Alex Neo
 * @author Cheng Chee How
 * @version 1.0
 * @since 1.0
*/
public class BookingApp {
	
	/**
	 * Status of booking
	 */
	private BookingStatus status;
	/**
	 * DateTime formatter for parsing the date entered by the user
	 */
	private DateTimeFormatter parseDate = DateTimeFormatter.ofPattern( "dd-MM-yyyy" );
	/**
	 * DateTime formatter for parsing the time entered by the user
	 */
    private  DateTimeFormatter parseTime = DateTimeFormatter.ofPattern("HH:mm");
	
	private Scanner sc = new Scanner(System.in);
	
	/**
	 * Construct BookingApp object
	 */
	public BookingApp() {
		
	}
	
	/**
	 * Open BookingApp menu
	 */
	public void open() throws InputMismatchException{
		
		int index = 0, ticketQty = 0, age = 0, index2 = 0;
		String seatID;
		String seats[];
		String name,contact,email;
		Booking booking;
		String seatIDs ="";
		
		//MUST CHANGE//
		if(!Manager.mm.printMoviesForCustomer()) {
			return;
		}
		ArrayList<ShowTimeItems> temp_st = null;
		while (true) {
			try {
				System.out.print("\nEnter movie index [-1 to return]: ");
				index = sc.nextInt();
				if (index == -1)
					return;
				
				
				ArrayList<String> dates=new ArrayList<String>();
				LocalDateTime ldt = LocalDateTime.now();
				String Time = ldt.format(parseTime);
				
				for(int i=0;i<8;i++) {
					dates.add(ldt.format(parseDate).toString());
					ldt = ldt.plusDays(1);
				}
				
				temp_st = Manager.sm.getAndPrintShowTimeSim(Manager.mm.getMovieForCustomer(index).getTitle(),dates,ldt.format(parseTime).toString());
				break;
			}
			catch (InputMismatchException e) {
				System.out.println("Please enter a valid index!");
				sc.nextLine();
			}
			catch (IndexOutOfBoundsException f) {
				System.out.println("Please enter a valid index!");
			}
		}
		if(temp_st.isEmpty()) {
			System.out.println("No valid show times for this movie please try again.");
			return;
		}
		
		System.out.println("\nOnly showtimes 7 days in advance from current date is shown\n");
		
		ShowTimeItems si = null;
		while (true) {
			try {
				System.out.print("Select show time: ");
				index2 = sc.nextInt();
				
				si = Manager.sm.getShowTime(Manager.mm.getMovieForCustomer(index).getTitle(),
						temp_st.get(index2-1).getCineplex(),
						temp_st.get(index2-1).getCinema(),
						temp_st.get(index2-1).getDate(),
						temp_st.get(index2-1).getTiming());
				break;
			}
			catch (InputMismatchException e) {
				System.out.println("Please select a valid show time!");
				sc.nextLine();
			}
			catch (IndexOutOfBoundsException f) {
				System.out.println("Please select a valid show time!");
				sc.nextLine();
			}
		}

		System.out.print("Select number of tickets: ");
		ticketQty = CommandUI.getNumberInput(1, 10);
		sc.nextLine();
		seats = new String[ticketQty];


		Cineplex temp = Manager.cm.getCineplex(si.getCineplex());
		Cinema c = temp.getCinema(si.getCinema());
		temp.printAvailableSeats(si);
		int a1;
		int a2;
		String s1;
		String s2;
		String[] tempo = new String[ticketQty];
		do {
			try {	
				for(int i = 0 ; i <seats.length ; i++) {
					System.out.print("Enter individual seat number(eg: A1): ");
					seatID = sc.nextLine();
					tempo[i]= seatID;
				}
				Arrays.sort(tempo);
				
				for(int i = 0 ; i< tempo.length ; i ++) {
					if(Manager.bm.checkBooking(si.getDate(), si.getCineplex(), si.getCinema(), si.getTiming(), tempo[i]) == true) {
						throw new NoSuchElementException("Reset");
					}
					
					if(!c.checkValidSeat(tempo[i])) {
						throw new NoSuchElementException("Reset");
					}
				}
				
				for(int i=0;i<tempo.length;i++) {
					if((i+1)==tempo.length) {
						break;
					}
					
					if(tempo[i].charAt(0) != tempo[i+1].charAt(0)) {
						continue;
					}
					
					s1=tempo[i].substring(1);
					s2=tempo[i+1].substring(1);
					
					a1=Integer.parseInt(s1);
					a2=Integer.parseInt(s2);

					if((a1+1)!=a2) {
						throw new NoSuchElementException("Reset");
					}
					
				}
				break;
			}catch(NoSuchElementException n) {
				System.out.println("Invalid input");
			}
		}while(true);
		
		

		for(int i=0; i < tempo.length ; i++) {
			seatIDs += tempo[i] + ". ";
		}
		
		System.out.println();
		System.out.println("Please Enter Your Details");
		System.out.println("==============================");
		
		System.out.print("Name: ");
		name = sc.nextLine();
		name= CommandUI.namechk(name);

		System.out.print("Age in years: ");
		age = CommandUI.getNumberInput(1, 200);
		
		System.out.print("Mobile No: ");
		contact = sc.nextLine();
		contact = CommandUI.phonenumchk(contact);
		
		System.out.print("Email: ");
		email = sc.nextLine();
		email = CommandUI.emailchk(email);
		
		double ticket_price = Manager.pm.getTicketPrice(si, age);
		double total_price_gst = (double)((tempo.length * ticket_price + Manager.bm.getBookingFee()) * 1.07);
		
		System.out.println();
		System.out.println("YOUR BOOKING SUMMARY");
		System.out.println("-----------------------------");
		System.out.printf("%-20s : %-50s\n", "Cineplex", si.getCinema());
		System.out.printf("%-20s : %-50s\n", "Title", si.getMovie());
		System.out.printf("%-20s : %-50s\n", "Date", si.getDate());
		System.out.printf("%-20s : %-50s\n", "Time", si.getTiming());
		System.out.printf("%-20s : %-50s\n", "Rating", Manager.mm.getMovie(si.getMovie()).getMovieRatingType());
		System.out.printf("%-20s : %-50s\n", "Seats", seatIDs);
		System.out.printf("%-20s : %-50s\n", "No. of tickets", tempo.length);
		System.out.printf("%-20s : %-50s\n", "Price", CommandUI.round(ticket_price, 2));
		System.out.printf("%-20s : %-50s\n", "Booking Fee", CommandUI.round(Manager.bm.getBookingFee(), 2));
		System.out.printf("%-20s : %-50s\n", "Grand Total (GST 7% Inclusive)", CommandUI.round(total_price_gst, 2));
		
		System.out.println();
		System.out.println("[1] CONFIRM BOOKING: ");
		System.out.println("[2] CANCEL BOOKING: ");
		
		
		do{
			try {
				System.out.print("Please enter a choice: ");
				int result = sc.nextInt();
				sc.nextLine();
				switch(result) {
					case 1:
						paymentMethod(name, contact, email, age, si, tempo, ticket_price, total_price_gst);
						return;
					case 2:
						System.out.println("Booking Cancelled");
						return;
					default:
						break;
				}
			} catch (InputMismatchException e) {
				System.out.println("Invalid input");
				sc.nextLine();
			}
		} while (true);
	}

	/**
	 * Open payment selection for user to make payment
	 * @param name Name of user
	 * @param contact Contact number of user
	 * @param email Email of user
	 * @param age Age of user
	 * @param si Show time details 
	 * @param seats Seat details
	 * @param ticket_price Ticket pricing
	 * @param total_price_gst Total price including GST
	 */
	public void paymentMethod(String name, String contact, String email, int age, ShowTimeItems si, String[] seats, double ticket_price, double total_price_gst){
		System.out.println();
		System.out.println("Select Payment Method");
		System.out.printf("%-15s : %-15s : %-15s : %-15s :%-15s\n", "[1] VISA", "[2]MASTERCARD", "[3]AMEX" ,"[4]PAYPAL" , "[5]RETURN");
		do {
			try {
				System.out.println("Please enter a choice :");
				int result =sc.nextInt();
				sc.nextLine();
				PaymentType pt;
				switch(result) {
					case 1:
						pt = PaymentType.VISA;
						paymentConfirmation(name, contact, email, age, si, seats, ticket_price, total_price_gst, pt);
						return;
					case 2:
						pt = PaymentType.MASTERCARD;
						paymentConfirmation(name, contact, email, age, si, seats, ticket_price, total_price_gst, pt);
						return;
					case 3:
						pt = PaymentType.AMEX;
						paymentConfirmation(name, contact, email, age, si, seats, ticket_price, total_price_gst, pt);
						return;
					case 4:
						pt = PaymentType.PAYPAL;
						paymentConfirmation(name, contact, email, age, si, seats, ticket_price, total_price_gst, pt);
						return;
					case 5:
						return;
					default:
						break;
				}
			}catch (InputMismatchException e) {
				System.out.println("Invalid input");
				sc.nextLine();
			}
		}while(true);
	}
	
	/**
	 * Open payment confirmation menu for the user to confirm their payment
	 * @param name Name of user
	 * @param contact Contact number of user
	 * @param email Email of user
	 * @param age Age of user
	 * @param si Show time details 
	 * @param seats Seat details
	 * @param ticket_price Ticket pricing
	 * @param total_price_gst Total price including GST
	 * @param paymentmethod Payment method (NETS,VISA etc.)
	 */
	public void paymentConfirmation(String name, String contact, String email, int age, ShowTimeItems si, String[] seats, double ticket_price, double total_price_gst, PaymentType paymentmethod) {
		
		System.out.println("Are you sure you want to pay?");
		do {
			try {
				System.out.println("[1] Yes    [2] No");
				System.out.print("Please enter your choice : ");
				int result =sc.nextInt();
				sc.nextLine();
				
				switch(result) {
					case 1:
						Manager.bm.createBooking(name, contact, email, age, si, seats ,ticket_price , total_price_gst, paymentmethod);
						System.out.println("BOOKING PURCHASE INVOICE");
						System.out.println("=========================");
						System.out.println();
						System.out.printf("%-5s : %-50s\n", "TID", Manager.bm.getBookingID());
						System.out.println();
						System.out.println("Description\t\tQty\tAmount");
						System.out.println("-------------------------------------------------");
						System.out.println("Ticket "+"("+ticket_price+")"+ "\t\t" + seats.length+ "\t" + CommandUI.round((ticket_price*seats.length), 2) ) ;
						System.out.println("Online Booking Fee "+ "\t\t" + Manager.bm.getBookingFee() ) ;
						System.out.println("\t\t\t\t" + CommandUI.round((total_price_gst), 2));
						System.out.println();
						System.out.println("Booking Successful");
						return;
					case 2:
						System.out.println("Booking Unsuccessful");
						return;
					default:
						break;
					}
			}catch (InputMismatchException e) {
				System.out.println("Invalid input");
				sc.nextLine();
			}
		}while(true);
	}
	
	/**
	 * Retrieve booking history and select booking entry to delete
	 */
	public void retrieveAndDeleteDetails(){
		int choice;
		String input;
		
		System.out.println("==============================");
		System.out.println("       RETRIEVAL METHOD");
		System.out.println("==============================");
		System.out.printf("%-20s\n", "[1] By e-mail");
		System.out.printf("%-20s\n", "[2] By contact No.");
		System.out.printf("%-20s\n", "[3] By Booking Ref No.");
		System.out.printf("%-20s\n", "[4] Retrieve All");
		System.out.printf("%-20s\n", "[5] Return");

		do {
			try {
				System.out.printf("%-30s\n", "Please enter your choice: ");
				choice = sc.nextInt();
				
				switch(choice) {
				case 1: 
					sc.nextLine();
					System.out.print("Enter e-mail : ");
					input = sc.nextLine();
					CommandUI.emailchk(input);
					if(Manager.bm.printBookingListbyEmail(input) == 1) {
						removeDetails();
					}
					else {
						System.out.println();
						System.out.println("You have no bookings!");
					}
					return;
				case 2:
					sc.nextLine();
					System.out.printf("Enter Mobile No : ");
					input = sc.nextLine();
					CommandUI.phonenumchk(input);
					if(Manager.bm.printBookingListbyContact(input) == 1) {
						removeDetails();
					}
					else {
						System.out.println();
						System.out.println("You have no bookings!");
					}
					return;
				case 3:
					sc.nextLine();
					System.out.printf("Enter Booking Ref No : ");
					input = sc.nextLine();
					if(Manager.bm.printBookingListbyRefNo(input) == 1) {
						removeDetails();
					}
					else {
						System.out.println();
						System.out.println("You have no bookings!");
					}
					return;
				case 4:
					if(Manager.bm.printBookingListbyAll() == 1) {
						removeDetails();
					}
					else {
						System.out.println();
						System.out.println("You have no bookings!");
					}
					return;
				case 5:
					return;
				default:
					System.out.println("Invalid Input! Try Again: ");
					break;
				}
			}catch (InputMismatchException e) {
				System.out.println("Invalid input!");
				sc.nextLine();
			}
		}while(true);
		
	}
	/**
	 * Retrieve booking history for Admin
	 */
	public void retrieveDetailsAdmin(){
		int choice;
		String input;
		
		System.out.println("==============================");
		System.out.println("       RETRIEVAL METHOD");
		System.out.println("==============================");
		System.out.printf("%-20s\n", "[1] By e-mail");
		System.out.printf("%-20s\n", "[2] By contact No.");
		System.out.printf("%-20s\n", "[3] By Booking Ref No.");
		System.out.printf("%-20s\n", "[4] Retrieve All");
		System.out.printf("%-20s\n", "[5] Return");
		System.out.printf("%-30s\n", "Please enter your choice: ");
		//sc.nextLine();
		choice = sc.nextInt();
		do {
			try {
				switch(choice) {
				case 1: 
					sc.nextLine();
					System.out.print("Enter e-mail : ");
					input = sc.nextLine();
					CommandUI.emailchk(input);
					if(Manager.bm.printBookingListbyEmail(input) == 0) {
						System.out.println();
						System.out.println("You have no bookings!");
					}
					return;
				case 2:
					sc.nextLine();
					System.out.printf("Enter contact No : ");
					input = sc.nextLine();
					CommandUI.phonenumchk(input);
					if(Manager.bm.printBookingListbyContact(input) == 0) {
						System.out.println();
						System.out.println("You have no bookings!");
					}
					return;
				case 3:
					sc.nextLine();
					System.out.printf("Enter Booking Ref No : ");
					input = sc.nextLine();
					if(Manager.bm.printBookingListbyRefNo(input) == 0) {
						System.out.println();
						System.out.println("You have no bookings!");
					}
					return;
				case 4:
					Manager.bm.printBookingListbyAll();
					return;
				case 5:
					return;
				default:
					System.out.println("Invalid Input! Try Again: ");
					retrieveDetails();
				}
			}catch (InputMismatchException e) {
				System.out.println("Invalid input");
				sc.nextLine();
			}
		}while(true);
	}


	
	/**
	 * Retrieve booking history for customer
	 */
	public void retrieveDetails(){
		int choice;
		String input;
		
		System.out.println("==============================");
		System.out.println("       RETRIEVAL METHOD");
		System.out.println("==============================");
		System.out.printf("%-20s\n", "[1] By e-mail");
		System.out.printf("%-20s\n", "[2] By contact No.");
		System.out.printf("%-20s\n", "[3] By Booking Ref No.");
		System.out.printf("%-20s\n", "[4] Return");
		System.out.printf("%-30s\n", "Please enter your choice: ");
		//sc.nextLine();
		choice = sc.nextInt();
		do {
			try {
				switch(choice) {
				case 1: 
					sc.nextLine();
					System.out.print("Enter e-mail : ");
					input = sc.nextLine();
					CommandUI.emailchk(input);
					if(Manager.bm.printBookingListbyEmail(input) == 0) {
						System.out.println();
						System.out.println("You have no bookings!");
					}
					return;
				case 2:
					sc.nextLine();
					System.out.printf("Enter contact No : ");
					input = sc.nextLine();
					CommandUI.phonenumchk(input);
					if(Manager.bm.printBookingListbyContact(input) == 0) {
						System.out.println();
						System.out.println("You have no bookings!");
					}
					return;
				case 3:
					sc.nextLine();
					System.out.printf("Enter Booking Ref No : ");
					input = sc.nextLine();
					if(Manager.bm.printBookingListbyRefNo(input) == 0) {
						System.out.println();
						System.out.println("You have no bookings!");
					}
					return;
				case 4:
					return;
				default:
					System.out.println("Invalid Input! Try Again: ");
					retrieveDetails();
				}
			}catch (InputMismatchException e) {
				System.out.println("Invalid input");
				sc.nextLine();
			}
		}while(true);
	}

	
	/**
	 * Remove booking from booking history
	 */
	public void removeDetails(){
		
		int option;
		System.out.print("please index to remove : ");
		option = CommandUI.getNumberInput(1, Manager.bm.getBookingList().size());
		sc.hasNextLine();
		Manager.bm.deleteBooking(Manager.bm.bookingselect(option-1));
	}
	
}
