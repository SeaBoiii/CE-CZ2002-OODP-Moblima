package miniprj;
import java.util.*;

/** Represents the menu for the admin to use.
 * @author Oh Yao Wen
 * @author Fong Hao Wei
 * @author Aleem Siddique
 * @author Alex Neo
 * @author Cheng Chee How
 * @version 1.0
 * @since 1.0
*/
public class AdminApp{

	/**Store admin's username
	*/
	private String username;
	/**Store admin's password
	 */
	private String password;
	private Scanner sc = new Scanner(System.in);

	/**Construct AdminApp object
	*/
	public AdminApp() {

	}



	/**Show the login page for the user to login
	*/
	public void loginPrint() {
		int i=0;
		int attempts = 2;
		while(i<attempts) {
			CommandUI.printBox("============== ADMIN LOGIN ===============", 1, 3);
			System.out.println();
			System.out.print("Please enter the username: ");
			Scanner scan = new Scanner(System.in);
			username = scan.nextLine();
			System.out.print("Please enter the password: ");
			password = scan.nextLine();
			if(Manager.am.authenticate(username,password)) {
				System.out.println("Login Successfully!");
				adminMenu();
				return;
			}else {
				i ++;
				System.out.println("Login Failed! " + (attempts-i) + " attempt/s left");
				continue;
			}
		}
		System.out.println("Too many attempts, returning you back to the main menu...");
	}

	/** Open admin menu after successfully verifying the admin
	*/
	private void adminMenu() {

		do {
			try {
				System.out.println();
				CommandUI.printBox("=============== ADMIN MENU ===============", 1, 3);
				System.out.println("\nWelcome! Please select what you want to do: \n");
				System.out.println("[1] Cineplex settings");
				System.out.println("[2] Cinema settings");
				System.out.println("[3] Movie settings");
				System.out.println("[4] Show time settings");
				System.out.println("[5] Booking settings");
				System.out.println("[6] Change ticket prices");
				System.out.println("[7] Configure system Settings");
				System.out.println("[8] View sales report");
				System.out.println("[9] Exit");
				System.out.print("Please select an option: ");
				int userInput = sc.nextInt();
				switch (userInput) {
					case 1:
						cineplexMenu();
						break;
					case 2:
						cinemaMenu();
						break;
					case 3:
						movieMenu();
						break;
					case 4:
						showtimeMenu();
						break;
					case 5:
						bookingMenu();
						break;
					case 6:
						priceMenu();
						break;
					case 7:
						systemMenu();
						break;
					case 8:
						Sale sales = new Sale();
						sales.SalesReport();
						break;
					case 9:
						return;
					default:
						break;
				}
			}
			catch (InputMismatchException e) {
				sc.nextLine();
				System.out.println("Error!");
				continue;
			}
		}while(true);
	}

	/** Open cineplex settings
	 * @throws InputMismatchException Thrown if invalid input
	*/
	private void cineplexMenu() throws InputMismatchException{
		do {
			try {
				CommandUI.printBox("=========== CINEPLEX SETTINGS ============", 1, 3);
				System.out.println("[1] Add Cineplex");
				System.out.println("[2] Modify Cineplex");
				System.out.println("[3] Remove Cineplex");
				System.out.println("[4] Return");
				System.out.print("Please select an option: ");
				int adminInput = CommandUI.getNumberInput(1,4);
				switch(adminInput) {
				case 1:
					Manager.cm.addCineplex();
					break;
				case 2:
					Manager.cm.printCineplexes();
					System.out.print("\nPlease select Cineplex to update [Enter -1 to cancel]: ");
					int index = CommandUI.getNumberInput();

					if(index == -1)
						break;

					Manager.cm.updateCineplex(index);
					break;
				case 3:
					Manager.cm.printCineplexes();
					System.out.print("Please select Cineplex to remove [Enter -1 to cancel]: ");
					int index1 = sc.nextInt();

					if(index1 == -1)
						break;

					Manager.cm.removeCineplex(index1);
					break;
				case 4:
					return;
				default:
					break;
				}
			}
			catch (InputMismatchException e) {
				System.out.println("Error!");
				continue;
			}
		}while(true);
	}

	/** Open ticket price settings
	 * @throws InputMismatchException Thrown if invalid input
	*/
	private void priceMenu() {

		do {
		CommandUI.printBox("============= PRICE SETTINGS =============", 1, 3);
		System.out.println("[1] Add new holiday");
		System.out.println("[2] Update pricing");
		System.out.println("[3] Remove holiday");
		System.out.println("[4] Return");
		Scanner input = new Scanner(System.in);
		int adminInput = CommandUI.getNumberInput(1, 4);
		switch(adminInput) {
		case 1:
			CommandUI.printTitleWithLine("Add new holiday", 23);
			System.out.print("\nPlease enter name of holiday [Enter -1 to cancel]: ");
			String name = input.nextLine();

			if(name.compareTo("-1")==0)
				break;

			System.out.print("\nPlease enter day of the month between 1-31: ");
			int day = CommandUI.getNumberInput(1, 31);


			System.out.print("\nPlease enter month between 1-12: ");
			int month = CommandUI.getNumberInput(1, 12);

			System.out.print("\nPlease enter price: ");
			double price = CommandUI.getDoubleInput();

			Manager.pm.addPublicHoliday(name,day,month,price);

			System.out.println("Public holiday added successfully");

			break;
		case 2:
			priceUpdateMenu();
			break;
		case 3:
			Manager.pm.printPriceEntries(PriceManager.PMLabel.PUBLIC_HOLIDAY.ordinal()+1);
			System.out.print("Please select holiday to remove [Enter -1 to cancel]: ");
			int index1 = CommandUI.getNumberInput(-1, Integer.MAX_VALUE);

			if(index1 == -1)
				break;
			try {
				Manager.pm.removeHoliday(index1);
			}catch(Exception e){
				System.out.println("\nUnable to remove holiday");
				break;
			}
			System.out.println("\nPublic holiday removed successfully");
			break;
		case 4:
			return;
		default:
			break;
		}
		}while(true);
	}

	/** Open ticket price update settings
	 * @throws InputMismatchException Thrown if invalid input
	*/
	private void priceUpdateMenu() {

		do {
		CommandUI.printBox("============= PRICE SETTINGS =============", 1, 3);
		Manager.pm.printPriceList();
		System.out.print("\nPlease select category to update price[Enter -1 to cancel]: ");
		Scanner input = new Scanner(System.in);
		int category = CommandUI.getNumberInput();


		if(category == -1)
			break;


		CommandUI.printBox("============= PRICE UPDATE SETTINGS =============", 1, 3);
		Manager.pm.printPriceEntries(category);
		System.out.println("\nPlease select entry to update [Enter -1 to cancel]: ");
		int entry = CommandUI.getNumberInput();

		if(entry == -1)
			break;

		System.out.print("\nPlease enter new pricing: ");
		double price = CommandUI.getDoubleInput();

		Manager.pm.updatePricing(category, entry, price);

		System.out.println("\nUpdate Successful");
		}while(true);
	}

	/** Open cinema settings
	 * @throws InputMismatchException Thrown if invalid input
	*/
	private void cinemaMenu() throws InputMismatchException{
		Cineplex c;
		int index;
		do {
			try {
				CommandUI.printBox("============ CINEMA SETTINGS =============", 1, 3);
				System.out.println("[1] Add Cinema");
				System.out.println("[2] Modify Cinema");
				System.out.println("[3] Remove Cinema");
				System.out.println("[4] Return");
				System.out.print("Please select an option: ");
				int adminInput = sc.nextInt();
				sc.nextLine();
				switch(adminInput) {
				case 1:
					Manager.cm.printCineplexes();
					System.out.print("Please select Cineplex to add cinema [Enter -1 to cancel]: ");
					index = sc.nextInt();

					if(index == -1)
						break;

					Manager.cm.getCineplex(index).addCinema(1);

					Manager.cm.saveAndReloadData();

					break;
				case 2:
					Manager.cm.printCineplexes();
					System.out.print("Please select Cineplex to update cinema [Enter -1 to cancel]: ");
					index = sc.nextInt();

					if(index == -1)
						break;

					c = Manager.cm.getCineplex(index);
					c.printCinemas();
					System.out.print("Please select Cinema to update [Enter -1 to cancel]: ");
					index = sc.nextInt();

					if(index == -1)
						break;

					c.updateCinema(index);
					Manager.cm.saveAndReloadData();

					break;
				case 3:
					Manager.cm.printCineplexes();
					System.out.print("Please select Cineplex to remove cinema [Enter -1 to cancel]: ");
					index = sc.nextInt();

					if(index == -1)
						break;
					c = Manager.cm.getCineplex(index);
					c.printCinemas();
					System.out.print("Please select Cinema to remove [Enter -1 to cancel]: ");
					index = sc.nextInt();

					if(index == -1)
						break;

					c.removeCinema(index);

					Manager.cm.saveAndReloadData();

					break;
				case 4:
					return;
				default:
					break;
				}
			}
			catch (InputMismatchException e) {
				System.out.println("Error!");
				sc.next();
				continue;
			}
		}while(true);
	}

	/** Open movie settings
	 * @throws InputMismatchException Thrown if invalid input
	*/
	private void movieMenu() throws InputMismatchException{

		do {
			try {
				CommandUI.printBox("============= MOVIE SETTINGS =============", 1, 3);
				System.out.println("[1] Add Movie");
				System.out.println("[2] Modify Movie");
				System.out.println("[3] Remove Movie");
				System.out.println("[4] Return");
				System.out.print("Please select an option: ");
				Scanner input = new Scanner(System.in);
				int adminInput = input.nextInt();
				input.nextLine();
				switch(adminInput) {
				case 1:
					Manager.mm.addMovie();
					break;
				case 2:
					Manager.mm.updateMovie();
					break;
				case 3:
					//System.out.println("Please enter the movie title to remove: ");
					//String movieTitle = sc.next();
					Manager.mm.removeMovie();
					break;
				case 4:
					return;
				default:
					break;
				}
			}
			catch (InputMismatchException e) {
				System.out.println("Error!");
				continue;
			}
		}while(true);
	}

	/** Open booking settings
	 * @throws InputMismatchException Thrown if invalid input
	*/
	private void bookingMenu() throws InputMismatchException{

		do {
			try {
				CommandUI.printBox("============ BOOKING SETTINGS ============", 1, 3);
				System.out.println("[1] Add new booking");
				System.out.println("[2] Remove booking");
				System.out.println("[3] View booking");
				System.out.println("[4] Return");
				System.out.print("Please select an option: ");

				BookingApp ba =new BookingApp();
				Scanner input = new Scanner(System.in);
				int adminInput = input.nextInt();
				input.nextLine();
				switch(adminInput) {
				case 1:
					ba.open();
					break;
				case 2:
					ba.retrieveAndDeleteDetails();
					break;
				case 3:
					ba.retrieveDetailsAdmin();
					break;
				case 4:
					return;
				default:
					break;
				}
			}
			catch (InputMismatchException e) {
				System.out.println("Error!");
				continue;
			}
		}while(true);
	}

	/** Open show time settings
	 * @throws InputMismatchException Thrown if invalid input
	*/
	private void showtimeMenu() throws InputMismatchException{

		do {
			try {
				CommandUI.printBox("=========== SHOW TIME SETTINGS ===========", 1, 3);
				System.out.println("[1] Add new show times");
				System.out.println("[2] Update show times");
				System.out.println("[3] Remove show times");
				System.out.println("[4] Return");
				System.out.print("Please select an option: ");
				Scanner input = new Scanner(System.in);
				int adminInput = input.nextInt();
				input.nextLine();
				switch(adminInput) {
				case 1:
					Manager.sm.addShowTime();
					break;
				case 2:
					Manager.sm.updateShowTime();
					break;
				case 3:
					Manager.sm.removeShowTime();
					break;
				case 4:
					return;
				default:
					break;
				}
			}
			catch (InputMismatchException e) {
				System.out.println("Error!");
				continue;
			}
		}while(true);
	}

	/** Open system settings
	 * @throws InputMismatchException Thrown if invalid input
	*/
	private void systemMenu() throws InputMismatchException{

		do {
			try {
				CommandUI.printBox("============= SYSTEM SETTINGS ============", 1, 3);
				System.out.println("[1] Add Admins");
				System.out.println("[2] Modify Admins");
				System.out.println("[3] Remove Admins");
				System.out.println("[4] Return");
				System.out.print("Please select an option: ");
				Scanner input = new Scanner(System.in);
				int adminInput = input.nextInt();
				input.nextLine();
				switch(adminInput) {
				case 1:
					Manager.am.adminSignup();
					break;
				case 2:
					Manager.am.updateAdmin();
					break;
				case 3:
					//System.out.println("Please enter the username to remove: ");
					//String username = sc.next();
					if(Manager.am.removeAdmin()) {
						System.out.println("Admin removed sucessfully");
					}
					break;
				case 4:
					return;
				default:
					break;
				}
			}
			catch (InputMismatchException e) {
				System.out.println("Error!");
				continue;
			}
		}while(true);
	}
}
