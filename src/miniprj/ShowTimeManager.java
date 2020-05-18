package miniprj;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;


/**Control class for show time related tasks
 * @author Oh Yao Wen
 * @author Fong Hao Wei
 * @author Aleem Siddique
 * @author Alex Neo
 * @author Cheng Chee How
 * @version 1.0
 * @since 1.0
*/

public class ShowTimeManager implements Serializable, FileMethods{

	/**
	 * List of showtimes
	 */
	private static ArrayList<ShowTimeItems> showTimes = new ArrayList<ShowTimeItems>();
	/**
	 * DateTimeFormatter to parse date
	 */
	private DateTimeFormatter parseDate = DateTimeFormatter.ofPattern( "dd-MM-yyyy" );
	/**
	 * DateTimeFormatter to parse time
	 */
    private DateTimeFormatter parseTime = DateTimeFormatter.ofPattern("HH:mm");

	private Scanner sc = new Scanner(System.in);

	/**
	 * Construct ShowTimeManager object
	 */
	public ShowTimeManager() {
		showTimes.clear();
		showTimes.addAll(FileIO.readFile(Directory.DIR_SHOWTIMES));
	}

	/**
	 * Print show time menu to add new show time movie
	 */
	public void addShowTime() {
		LocalDate ld;
		LocalTime lt;
		int index;
		int cineplexID;
		int[] choices;

		String timing; String date; String cineplex; String cinema; String movie;

		System.out.println("+-------------------------+");
		System.out.println("|      ShowTime Adder     |");
		System.out.println("+-------------------------+");
		System.out.println();
		try {
			choices = addCustom();
			if (choices[0] == -1)
				return;
		}
		catch (InputMismatchException e) {
			sc.nextLine();
			System.out.println("Error! ");
			return;
		}

		while (true) {
			try {
				System.out.print("Please insert date (dd-mm-yyyy): ");
				date = sc.nextLine();
				ld = LocalDate.parse( date , parseDate );
				date = ld.format(parseDate);

				System.out.print("Please insert timing (hh:mm): ");
				timing = sc.nextLine();
				lt = LocalTime.parse( timing , parseTime );
				timing = lt.format(parseTime);
				break;
			}
			catch (DateTimeParseException e) {
				System.out.println("Please enter according to format! ");
				continue;
			}
		}

		while (true) {
			try {
				Manager.cm.printCineplexes();
				System.out.println("Please insert Cineplex: ");
				cineplexID = sc.nextInt();

				Cineplex temp = Manager.cm.getCineplex(cineplexID);
				cineplex = temp.getName();

				temp.printCinemas();
				System.out.println("Please insert cinema: ");
				index = sc.nextInt();
				cinema = temp.getCinema(index).getCinemaName();

				Manager.mm.printMovies();
				System.out.println("Please insert movie name: ");
				index = sc.nextInt();
				movie = Manager.mm.getMovie(index).getTitle();

				showTimes.add(new ShowTimeItems(timing, date, cineplex, cinema, movie));
				choices[0]--;
				sc.nextLine();
				
				inside:
				for (int i=0; i<choices[0]; i++) {
					try {
						if (choices[1] == 0) {
							System.out.print("Please insert date (dd-mm-yyyy): ");
							date = sc.nextLine();
							do {
								try {
									ld = LocalDate.parse( date , parseDate );
									date = ld.format(parseDate);
									break;
								}catch(DateTimeParseException g) {
									System.out.println("Please enter accordingly to format");
									System.out.print("Please re-insert date (dd-mm-yyyy): ");
									date = sc.nextLine();
									continue;
								}
							}while(true);

						}
						else {}
						
						if (choices[2] == 0) {
							System.out.print("Please insert timing (hh:mm): ");
							timing = sc.nextLine();
							do {
								try {
									lt = LocalTime.parse( timing , parseTime );
									timing = lt.format(parseTime);
									break;
								}catch(DateTimeParseException g) {
									System.out.println("Please enter accordingly to format");
									System.out.print("Please re-insert timing (hh:mm): ");
									timing =sc.nextLine();
									continue;
								}
							}while(true);
						}
						else {}
						
						if (choices[3] == 0) {
							Manager.cm.printCineplexes();
							System.out.println("Please insert Cineplex: ");
							cineplexID = sc.nextInt();
	
							temp = Manager.cm.getCineplex(cineplexID);
							cineplex = temp.getName();
						}
						else {}
	
						if (choices[4] == 0) {
							temp.printCinemas();
							System.out.println("Please insert cinema: ");
							index = sc.nextInt();
							cinema = temp.getCinema(index).getCinemaName();
						}
						else {}
						
						if (choices[5] == 0) {
							Manager.mm.printMovies();
							System.out.println("Please insert movie name: ");
							index = sc.nextInt();
							movie = Manager.mm.getMovie(index).getTitle();
						}
	
						showTimes.add(new ShowTimeItems(timing, date, cineplex, cinema, movie));
						sc.nextLine();
						System.out.println("Showtime created successfully! ");
						saveAndReloadData();
					}
					catch (InputMismatchException f ) {
						System.out.println("Error when getting the index!");
						sc.nextLine();
						continue inside;
					}
				}
			}
			catch (InputMismatchException e) {
				System.out.println("Error when getting the index!");
				sc.nextLine();
				continue;
			}
		break;
		}
	}


	/**
	 * Add show time entries
	 * @return Returns all the choices the user entered
	 * @throws InputMismatchException Invalid input
	 */
	protected int[] addCustom() throws InputMismatchException{
		int [] choices = new int[6];
		choices[0] = -1;
		for (int i=1; i<choices.length; i++) {
			choices[i] = 0;
		}

		int selector = 0;
		do {
			System.out.print("Please enter number of ShowTime to be created [-1 to quit]: ");
			choices[0] = sc.nextInt();
			if (choices[0] == -1)
				return choices;
			else if (choices[0] == 1) {
				sc.nextLine();
				return choices;
			}
			else if (choices[0] < 1)
				System.out.println("Please enter a number > 0");
		} while (choices[0] < 1);

		do {
			printCustomMenu(choices);
			selector = sc.nextInt();
			switch(selector) {
			case 0:
				do {
					System.out.print("Please update number of ShowTime to be created: ");
					choices[0] = sc.nextInt();
					if (choices[0] < 1)
						System.out.println("Please enter a number > 0");
				} while (choices[0] < 1);
				break;
			case 1:
				if (choices[1] == 1)
					choices[1] = 0;
				else if (choices[1] == 0)
					choices[1] = 1;
				break;
			case 2:
				if (choices[2] == 1)
					choices[2] = 0;
				else if (choices[2] == 0)
					choices[2] = 1;
				break;
			case 3:
				if (choices[3] == 1)
					choices[3] = 0;
				else if (choices[3] == 0)
					choices[3] = 1;
				break;
			case 4:
				if (choices[4] == 1)
					choices[4] = 0;
				else if (choices[4] == 0)
					choices[4] = 1;
				break;
			case 5:
				if (choices[5] == 1)
					choices[5] = 0;
				else if (choices[5] == 0)
					choices[5] = 1;
				break;
			}

		} while (selector != 6);

		sc.nextLine();
		return choices;
	}

	/**
	 * Add multiple showtime for specific date
	 * @param choice Choices given by add custom to fixed certain values
	 */
	protected void printCustomMenu(int[] choice) {
		System.out.println(" Options                  Are they Repetitive");
		for (int i=0; i<choice.length; i++) {
			if (choice[i] == 0) {
				switch(i) {
				case 0:
					System.out.println("[0] Creating " + choice[0] + " showtimes");
					break;
				case 1:
					System.out.println("[1] Date                    (n) ");
					break;
				case 2:
					System.out.println("[2] Timing                  (n) ");
					break;
				case 3:
					System.out.println("[3] Cineplex                (n) ");
					break;
				case 4:
					System.out.println("[4] Cinema                  (n) ");
					break;
				case 5:
					System.out.println("[5] Movie                   (n) ");
					break;
				}
			}
			else {
				switch(i) {
				case 0:
					System.out.println("[0] Creating " + choice[0] + " showtimes");
					break;
				case 1:
					System.out.println("[1] Date                    (y) ");
					break;
				case 2:
					System.out.println("[2] Timing                  (y) ");
					break;
				case 3:
					System.out.println("[3] Cineplex                (y) ");
					break;
				case 4:
					System.out.println("[4] Cinema                  (y) ");
					break;
				case 5:
					System.out.println("[5] Movie                   (y) ");
					break;
				}
			}
		}
		System.out.println("[6] Start adding ShowTime/s");
		System.out.print("Please select an option to change: ");
	}


	/**
	 * Update cineplex name if changed by cineplex manager
	 * @param oldname Old cineplex name
	 * @param newname New cineplex name
	 */
	public void updateCineplexNameForShowTime(String oldname, String newname) {

		for (int i = 0;i<showTimes.size();i++) {
			if(showTimes.get(i).getCineplex().compareTo(oldname) == 0){
				System.out.print(newname);
				showTimes.get(i).setCineplex(newname);
			}
		}
		saveAndReloadData();
	}

	/**
	 * Uppdate Cinema name if changed by admin
	 * @param oldname Old cinema name
	 * @param newname New cinema name
	 */
	public void updateCinemaNameForShowTime(String oldname, String newname) {

		for (int i = 0;i<showTimes.size();i++) {
			if(showTimes.get(i).getCinema().compareTo(oldname) == 0){
				System.out.print(newname);
				showTimes.get(i).setCinema(newname);
			}
		}
		FileIO.saveToFile(showTimes, Directory.DIR_SHOWTIMES);
		reloadData();
	}


	/**
	 * Get and print show time based on current date and time
	 * @param movie Movie name
	 * @param dates Dates of the week(7 Days)
	 * @param currentTime Current system time
	 * @return Return the created ArrayList after printing all the show time
	 */
	public ArrayList<ShowTimeItems> getAndPrintShowTimeSim(String movie, ArrayList<String> dates, String currentTime) {


		ArrayList<ShowTimeItems> temp = new ArrayList<>();
		for(int i=0;i<showTimes.size();i++) {
			if(showTimes.get(i).getMovie().compareTo(movie) == 0 && dates.contains(showTimes.get(i).getDate())) {

				if(showTimes.get(i).getDate().compareTo(dates.get(0))==0) {
					if(showTimes.get(i).getTiming().compareTo(currentTime)>=0) {
						temp.add(showTimes.get(i));
					}
				}else {
					temp.add(showTimes.get(i));
				}
			}
		}

	    Collections.sort(temp); // sort array

	    String check="";
	    String cineCheck="";
	    int k=0;
		for(int i=0;i<temp.size();i++) {
			if(temp.get(i).getCineplex().compareTo(cineCheck) != 0) {
				System.out.println();
				System.out.println();
				CommandUI.printBox(temp.get(i).getCineplex(), 1, 3);
				cineCheck = temp.get(i).getCineplex();
				check = "";
				k=0;
			}
			if(temp.get(i).getDate().compareTo(check) != 0) {
				System.out.println();
				System.out.println();
				System.out.println("--- "+temp.get(i).getDate()+" ---");
				check = temp.get(i).getDate();
				k=0;
			}
			System.out.printf("[%-2d] %s  ", i+1, temp.get(i).getTiming());
			k++;
			if (k%4 == 0) {
				System.out.println();
				k=0;
			}
		}

		System.out.println();
		System.out.println();

		return temp;
	}

	/**
	 * Gets the show times based on the one selected by the user.
	 * @param movie		The movie chosen by the user.
	 * @param cineplex	The cineplex based on the selected cineplex.
	 * @param cinema	The cinema based on the chosen cineplex.
	 * @param date		The date chosen by the user.
	 * @param timing	The timing chosen by the user.
	 * @return the show time based on the parameters.
	 */
	public ShowTimeItems getShowTime(String movie, String cineplex, String cinema, String date, String timing) {
		for(int i=0;i<showTimes.size();i++) {
			if(showTimes.get(i).getMovie().compareTo(movie) == 0
					&&showTimes.get(i).getCineplex().compareTo(cineplex) == 0
					&&showTimes.get(i).getCinema().compareTo(cinema) == 0
					&&showTimes.get(i).getDate().compareTo(date) == 0
					&&showTimes.get(i).getTiming().compareTo(timing) == 0) {

				return showTimes.get(i);

			}
		}

		return null;
	}

	/**
	 * List all showtime
	 */
	protected void listShowTime() {
		System.out.println("Index : Date        Timing   Movie                       Cineplex");
		for (int i=0; i<showTimes.size(); i++) {
			System.out.printf("[%-2d] : %s   %s    %-25s   %s%n", i,showTimes.get(i).getDate(), showTimes.get(i).getTiming(),
					showTimes.get(i).getMovie(), showTimes.get(i).getCineplex());
		}
	}
	/**
	 * List of show time based on show time index
	 * @param index Index of show time entry
	 */
	protected void listShowTime(int index) {
		System.out.println("Index : Date        Timing   Movie                       Cineplex                Cinema");
		System.out.printf("[%-2d] : %s   %s    %-25s   %-20s    %s%n", index,showTimes.get(index).getDate(), showTimes.get(index).getTiming(),
				showTimes.get(index).getMovie(), showTimes.get(index).getCineplex(), showTimes.get(index).getCinema());
	}

	/**
	 * Remove show time
	 */
	public void removeShowTime() {
		if (showTimes.size() == 0) {
			System.out.println("No showtime to remove!");
			return;
		}

		int index;
		char choice = 'n';

		System.out.println("+-------------------------+");
		System.out.println("|    ShowTime Deleter     |");
		System.out.println("+-------------------------+");
		listShowTime();

		while (choice == 'n') {
			try {
				System.out.print("Please select the index to remove [-1 to return]: ");
				index = sc.nextInt();
				if (index >= showTimes.size()) {
					sc.nextLine();
					System.out.println("!!WARNING!! Select within index range");
					continue;
				}
				else if (index == -1)
					return;
			}
			catch (InputMismatchException e) {
				System.out.println("!!WARNING!! Please enter an index! ");
				sc.nextLine();
				continue;
			}

			sc.nextLine();
				System.out.println("Are you sure to remove index " + index + " ? (y/n)");
				choice = sc.nextLine().charAt(0);
				if (choice == 'y' || choice == 'Y') {
					showTimes.remove(index);
					break;
				}
				else {
					System.out.println("Do you want to quit? (y/n)");
					choice = sc.nextLine().charAt(0);
					if (choice == 'Y' || choice == 'y') {
						return;
					}
					else {
						continue;
					}
				}
		}


		saveAndReloadData();

	}

	/**
	 * Print update show time menu and update show time
	 */
	public void updateShowTime() {
		if (showTimes.size() == 0) {
			System.out.println("No showtime to update!");
			return;
		}

		int index;
		char choice = 'n';
		int updater = 6;

		LocalDate ld;
		LocalTime lt;
		int indexing;
		int cineplexID;

		String timing; String date; String cineplex; String cinema; String movie;

		System.out.println("+-------------------------+");
		System.out.println("|    ShowTime Updater     |");
		System.out.println("+-------------------------+");
		listShowTime();
		while (choice == 'n') {
			try {
				System.out.print("Please select the index to update [-1 to return]: ");
				index = sc.nextInt();
				if (index >= showTimes.size()) {
					sc.nextLine();
					System.out.println("!!WARNING!! Select within index range");
					continue;
				}
				else if (index == -1)
					return;
			}
			catch (InputMismatchException e) {
				System.out.println("!!WARNING!! Please enter an index");
				sc.nextLine();
				continue;
			}
			do {
				try {
					System.out.println();
					System.out.println(">>   Currently Updating   <<");
					listShowTime(index);
					System.out.println();
					System.out.println("[1] Date ");
					System.out.println("[2] Timing ");
					System.out.println("[3] Movie ");
					System.out.println("[4] Cineplex & Cinema");
					System.out.println("[5] Quit");
					System.out.println("What would you like to update: ");
					updater = sc.nextInt();

					switch (updater) {
					case 1:
						sc.nextLine();
						System.out.print("Please update date (dd-mm-yyyy): ");
						date = sc.nextLine();
						ld = LocalDate.parse( date , parseDate );
						date = ld.format(parseDate);
						showTimes.get(index).setDate(date);
						break;
					case 2:
						sc.nextLine();
						System.out.print("Please update timing (hh:mm): ");
						timing = sc.nextLine();
						lt = LocalTime.parse( timing , parseTime );
						timing = lt.format(parseTime);
						showTimes.get(index).setTiming(timing);
						break;
					case 3:
						Manager.mm.printMovies();
						System.out.println("Please update movie name: ");
						indexing = sc.nextInt();
						movie = Manager.mm.getMovie(indexing).getTitle();
						showTimes.get(index).setMovie(movie);
						break;
					case 4:
						Manager.cm.printCineplexes();
						System.out.println("Please update Cineplex: ");
						cineplexID = sc.nextInt();

						Cineplex temp = Manager.cm.getCineplex(cineplexID);
						cineplex = temp.getName();
						showTimes.get(index).setCineplex(cineplex);

						temp.printCinemas();
						System.out.println("Please update cinema: ");
						indexing = sc.nextInt();
						cinema = temp.getCinema(indexing).getCinemaName();

						showTimes.get(index).setCinema(cinema);
						break;
					}
				}
				catch (InputMismatchException e) {
					sc.nextLine();
					System.out.println("Error! Please try again...");
					continue;
				}
				catch (DateTimeParseException ex) {
					System.out.println("Error! Please follow the format!");
					continue;
				}
			} while (updater != 5);
			sc.nextLine();
			System.out.println("Do you want to quit? (y/n)");
			choice = sc.nextLine().charAt(0);
			if (choice == 'Y' || choice == 'y') {
				return;
			}
			else {
				continue;
			}
		}
		saveAndReloadData();
	}

	/**
	 * Remove show time based on cineplex name
	 * @param cineplex Cineplex name
	 */
	public void removeShowTimeFromCineplex(String cineplex) {
		for(int i=0;i<showTimes.size();i++) {
			if(showTimes.get(i).getCineplex().compareTo(cineplex) == 0) {
				showTimes.remove(i);
			}
		}
		saveAndReloadData();

	}

	/**
	 * Remove show time based on cinema name
	 * @param cinema Cinema name
	 */
	public void removeShowTimeFromCinema(String cinema) {
		for(int i=0;i<showTimes.size();i++) {
			if(showTimes.get(i).getCinema().compareTo(cinema) == 0) {
				showTimes.remove(i);
			}
		}
		FileIO.saveToFile(showTimes, Directory.DIR_SHOWTIMES);
		reloadData();

	}

	/**
	 * Reloads the data from showtimes.txt
	 */
	@Override
	public void reloadData() {
		showTimes.clear();
		showTimes.addAll(FileIO.readFile(Directory.DIR_SHOWTIMES));
	}

	/**
	 * Saves and reloads the data from showtimes.txt
	 */
	@Override
	public void saveAndReloadData() {
		FileIO.saveToFile(showTimes, Directory.DIR_SHOWTIMES);
		reloadData();

	}
}
