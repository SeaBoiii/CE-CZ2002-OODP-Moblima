package miniprj;

import java.util.InputMismatchException;
import java.util.Scanner;

/**Represents the menu for the customer to use.
 * @author Oh Yao Wen
 * @author Fong Hao Wei
 * @author Aleem Siddique
 * @author Alex Neo
 * @author Cheng Chee How
 * @version 1.0
 * @since 1.0
*/
public class CustomerApp {
	
	private Scanner sc =  new Scanner(System.in);
	
	/**
	 * Create a BookingApp object
	 */
	BookingApp b_app = new BookingApp();
	
	/**
	 * Open the customer menu
	 */
	public void open() {
		int index;
		app:
		do {
			try {
				CommandUI.printBox("Welcome to Customer APP", 5, 7);
				System.out.println("\n[1] Create new booking");
				System.out.println("[2] View movie details");
				System.out.println("[3] View booking history");
				System.out.println("[4] View ranking");
				System.out.println("[5] Exit");
				
				System.out.print("\nPlease select an option: ");
				index = sc.nextInt();
				sc.nextLine();
				switch(index) {
					case 1:
						b_app.open();
						break;
					case 2:
						int movieindex;
						System.out.println();
						System.out.println();
						if(!Manager.mm.printMovies()) {
							System.out.println();
							System.out.println();
							break;
						}
						System.out.println();
						while (true) {
							System.out.print("Please select a movie to view its details [-1 to return]: ");
							try {
								movieindex = sc.nextInt();
								if (movieindex == -1) {
									sc.nextLine();
									continue app;
								}
								Manager.mm.printMovieDetails(movieindex);
								break;
							}
							catch (IndexOutOfBoundsException e) {
								sc.nextLine();
								System.out.println("Please select a valid movie index!");
							}
							catch (InputMismatchException e) {
								sc.nextLine();
								System.out.println("Please select a valid movie index!");
							}
						}
						System.out.println();
						System.out.println("[1] View reviews for movie");
						System.out.println("[2] Write review for movie");
						System.out.println("[3] Return");
						Scanner sc = new Scanner(System.in);
						reviews:
						while (true) {
							try {
								System.out.print("\nPlease select an option: ");
								index = sc.nextInt();
								sc.nextLine();
								switch(index) {
									case 1:
										Manager.mm.viewReview(movieindex);
										break;
									case 2:
										Manager.mm.writeReview(movieindex);
										break;
									case 3:
										break reviews;
									default:
										break;
								}
								break;
							}
							catch (InputMismatchException e) {
								sc.nextLine();
								System.out.println("Please select a valid option!");
								continue;
							}
						}
						break;
					case 3:
						BookingApp ba =new BookingApp();
						ba.retrieveDetails();
						break;
					case 4:
						RatingMenu();
						break;
					case 5:
						return;
					default:
						System.out.println("Please enter a valid index!");
						break;
				}
			}
			catch (InputMismatchException e) {
				sc.nextLine();
				System.out.println("Error! Please try again...");
				continue;
			}
		}while(true);
	}
	
	public void RatingMenu(){
		
		int index;
		
		do {
			try {
				CommandUI.printBox("VIEW TOP 5 MOVIES", 5, 7);
				System.out.println("\n[1] By Move Rating");
				System.out.println("[2] By Sales Revenue");
				System.out.println("[3] Exit");
				
				System.out.print("\nPlease select an option: ");
				index = sc.nextInt();
				sc.nextLine();
				switch(index) {
					case 1:
						Manager.mm.getRanking();
						break;
					case 2:
						Sale sales = new Sale();
						sales.top5();
						break;
					case 3:
						return;
					default:
						System.out.println("Please enter a valid index!");
						break;
				}
			}
			catch (InputMismatchException e) {
				sc.nextLine();
				System.out.println("Error! Please try again...");
				continue;
			}
		}while(true);
	}
	
}
