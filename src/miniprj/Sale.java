package miniprj;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**Print sales report
 * @author Oh Yao Wen
 * @author Fong Hao Wei
 * @author Aleem Siddique
 * @author Alex Neo
 * @author Cheng Chee How
 * @version 1.0
 * @since 1.0
*/
public class Sale {

	/**
	 * Booking status
	 */
	private BookingStatus status;
	/**
	 * DateTimeFormatter to parse date
	 */
	private DateTimeFormatter parseDate = DateTimeFormatter.ofPattern( "dd-MM-yyyy" );
	/**
	 * DateTimeFormatter to parse time
	 */
    private  DateTimeFormatter parseTime = DateTimeFormatter.ofPattern("HH:mm");


	private Scanner sc = new Scanner(System.in);

	/**
	 * Default constructor
	 */
	public Sale() {};

	/**
	 *  Print sales report menu
	 */
	public void SalesReport(){

		do{
			try {
				System.out.println();
				System.out.println("----------------------------------------------");
				System.out.println("Do you want to print the sales revenue report");
				System.out.println("----------------------------------------------");
				System.out.println("[1] By Movie");
				System.out.println("[2] By TOP 5 ticket Sales");
				System.out.println("[3] Return");

				int input;
				System.out.println("Please select a choice: ");
				input = sc.nextInt();

				switch(input) {
					case 1:
						printSalesByMovie();
						break;
					case 2:
						top5Sales();
						break;
					case 3:
						return;
					default:
						break;
				}
			}catch (InputMismatchException e) {
				System.out.println("Invalid input!");
				sc.nextLine();
			}
		}while(true);
	}

	/**
 	 * Print sales report by movie
 	 */
	public void printSalesByMovie() {


		System.out.println("--------------------------------");
		System.out.println("| Sale Revenue Report By Movie |");
		System.out.println("--------------------------------");
		System.out.println();

		ArrayList<Booking> bookingview = Manager.bm.getBookingList();
		Movie[] movieTitle = Manager.mm.getMovies();
		int[] ticAmt = new int[movieTitle.length];
		double[] totalrevenue = new double[movieTitle.length];
		MovieTicket mt;

		for(int k=0 ; k<ticAmt.length; k++) {
			ticAmt[k]=0;
			totalrevenue[k]=0;
		}

		for(int i = 0 ; i < movieTitle.length; i++) {
			System.out.println();
			System.out.println("Movie Title: "+ movieTitle[i].getTitle());
			System.out.println("===========================================================================");
			for(int j=0 ; j <bookingview.size() ; j++) {
				if(bookingview.get(j).getMovieTickets().get(0).getmovieTitle().compareTo(movieTitle[i].getTitle())==0 ){
					ticAmt[i] += bookingview.get(j).getMovieTickets().size();
					totalrevenue[i] += bookingview.get(j).getPaymentEtry().getPrice();

					mt=bookingview.get(j).getMovieTickets().get(0);


					System.out.println();
					System.out.println("Cineplex Name\t\tCinema\t\t\t\tIndividual Takings($)");
					System.out.println("-----------\t\t-----------------\t\t---------------------");
					System.out.println(mt.getCineplex() + "\t" + mt.getCinema()+ "\t\t\t\t" + CommandUI.round(bookingview.get(j).getPaymentEtry().getPrice(), 2) ) ;
				}
			}
			System.out.println("---------------------------------------------------------------------------");
			System.out.println();
			System.out.println("Total Sale revenue : "+ "$" + CommandUI.round(totalrevenue[i],2) );
			System.out.println("No. of tickets sold : "+ticAmt[i]);
		}
		return;
	}

	public void top5() {

		CommandUI.printBox("RANKING BY TOP 5", 12, 7);
		System.out.println("Ranking:              Title");

		ArrayList<Booking> bookingview = Manager.bm.getBookingList();
		Movie[] movieTitle = Manager.mm.getMovies();
		int[] ticAmt = new int[movieTitle.length];
		int[] TOP = new int[5];
		double[] totalrevenue = new double[movieTitle.length];
		MovieTicket mt;

		for(int k=0 ; k<ticAmt.length; k++) {
			ticAmt[k]=0;
			totalrevenue[k]=0;
		}

		for(int i = 0 ; i < movieTitle.length; i++) {
			for(int j=0 ; j <bookingview.size() ; j++) {
				if(bookingview.get(j).getMovieTickets().get(0).getmovieTitle().compareTo(movieTitle[i].getTitle())==0 ){
					ticAmt[i] += bookingview.get(j).getMovieTickets().size();
					totalrevenue[i] += bookingview.get(j).getPaymentEtry().getPrice();
					mt=bookingview.get(j).getMovieTickets().get(0);
				}
			}
		}

		double[] clonerevenue = totalrevenue.clone();

		for(int k=0 ; k < TOP.length; k++) {
			TOP[k]=-1;
		}
		if(totalrevenue.length > 4) {
			int biggest=0;

			for(int i=0 ; i < TOP.length ; i++ ) {
				for(int j=0; j < totalrevenue.length; j++) {
					if(clonerevenue[biggest] < clonerevenue[j]) {
						biggest = j;
					}
				}
				TOP[i] = biggest;
				clonerevenue[biggest]=Integer.MIN_VALUE;
				biggest = 0;
			}
			for(int i=0 ; i < TOP.length ; i++) {
				System.out.printf("(%d)                   %-15s\n", i+1, movieTitle[TOP[i]].getTitle());

			}
		}
		else {
			int biggest=0;

			for(int i=0 ; i < totalrevenue.length ; i++ ) {
				for(int j=0; j < totalrevenue.length; j++) {
					if(clonerevenue[biggest] < clonerevenue[j]) {
						biggest = j;
					}
				}
				TOP[i] = biggest;
				clonerevenue[biggest]=Integer.MIN_VALUE;
				biggest = 0;
			}
			for(int i=0 ; i < TOP.length ; i++) {
				if(TOP[i] != -1) {
					System.out.printf("(%d)                   %-15s\n", i+1, movieTitle[TOP[i]].getTitle());
				}
			}
		}
		return;
	}
	/**
	 * Print TOP 5 sales report
	 */
	public void top5Sales() {

		System.out.println("---------------------------------");
		System.out.println("| Sale Revenue Report for TOP 5 |");
		System.out.println("---------------------------------");
		System.out.println();

		ArrayList<Booking> bookingview = Manager.bm.getBookingList();
		Movie[] movieTitle = Manager.mm.getMovies();
		int[] ticAmt = new int[movieTitle.length];
		int[] TOP = new int[5];
		double[] totalrevenue = new double[movieTitle.length];
		MovieTicket mt;

		for(int k=0 ; k<ticAmt.length; k++) {
			ticAmt[k]=0;
			totalrevenue[k]=0;
		}

		for(int i = 0 ; i < movieTitle.length; i++) {
			for(int j=0 ; j <bookingview.size() ; j++) {
				if(bookingview.get(j).getMovieTickets().get(0).getmovieTitle().compareTo(movieTitle[i].getTitle())==0 ){
					ticAmt[i] += bookingview.get(j).getMovieTickets().size();
					totalrevenue[i] += bookingview.get(j).getPaymentEtry().getPrice();
					mt=bookingview.get(j).getMovieTickets().get(0);
				}
			}
		}

		double[] clonerevenue = totalrevenue.clone();

		for(int k=0 ; k < TOP.length; k++) {
			TOP[k]=-1;
		}
		if(totalrevenue.length > 4) {
			int biggest=0;

			for(int i=0 ; i < TOP.length ; i++ ) {
				for(int j=0; j < totalrevenue.length; j++) {
					if(clonerevenue[biggest] < clonerevenue[j]) {
						biggest = j;
					}
				}
				TOP[i] = biggest;
				clonerevenue[biggest]=Integer.MIN_VALUE;
				biggest = 0;
			}
			for(int i=0 ; i < TOP.length ; i++) {
				System.out.println("Movie Title\t\t\t\t Total Sales Revenue($)");
				System.out.println("-----------\t\t\t\t-----------------------");
				System.out.println(movieTitle[TOP[i]].getTitle()+ "\t\t\t\t\t" +CommandUI.round(+ totalrevenue[TOP[i]], 2) ) ;

			}
		}
		else {
			int biggest=0;

			for(int i=0 ; i < totalrevenue.length-1 ; i++ ) {
				for(int j=0; j < totalrevenue.length; j++) {
					if(clonerevenue[biggest] < clonerevenue[j]) {
						biggest = j;
					}
				}
				TOP[i] = biggest;
				clonerevenue[biggest]=Integer.MIN_VALUE;
				biggest = 0;
			}
			for(int i=0 ; i < TOP.length ; i++) {
				if(TOP[i] != -1) {
					System.out.println("Movie Title\t\t\t\t Total Sales Revenue($)");
					System.out.println("-----------\t\t\t\t-----------------------");
					System.out.println(movieTitle[TOP[i]].getTitle()+ "\t\t\t\t\t\t" +CommandUI.round(+ totalrevenue[TOP[i]], 2) ) ;

				}
			}
		}
		return;
	}
}
