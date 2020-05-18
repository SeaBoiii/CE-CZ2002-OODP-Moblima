package miniprj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
/** Control class to manage booking related tasks
 * @author Oh Yao Wen
 * @author Fong Hao Wei
 * @author Aleem Siddique
 * @author Alex Neo
 * @author Cheng Chee How
 * @version 1.0
 * @since 1.0
*/
public class CineplexManager implements Serializable, FileMethods{
	
	/**
	 * Stores all the cineplex objects
	 */
	private ArrayList<Cineplex> listOfCineplexes = new ArrayList<Cineplex>();
	private transient Scanner sc = new Scanner(System.in);
	
	/**
	 * Construct CineplexManager object
	 */
	public CineplexManager() {
		listOfCineplexes.clear();
		listOfCineplexes.addAll(FileIO.readFile(Directory.DIR_CINEPLEX));
	}
	
	/**
	 * Construct CineplexManager object with preset number of cineplexes
	 * @param size Number of cinemas
	 */
	public CineplexManager(int size){
		
		listOfCineplexes = new ArrayList<Cineplex>();
		
		for(int i=0;i<size;i++) {
			CommandUI.printBox("Please enter details for Cineplex "+(i+1), 5, 3);
			listOfCineplexes.add(new Cineplex());
		}
		
	}
	
	/**
	 * Add a cineplex
	 */
	public void addCineplex() {
		CommandUI.printBox("Please enter details for new Cineplex ", 5, 3);
		listOfCineplexes.add(new Cineplex());
		FileIO.saveToFile(listOfCineplexes, Directory.DIR_CINEPLEX);
		reloadData();
	}
	
	/**
	 * Update cineplex details base on cineplex index
	 * @param index Position of cineplex object inside the list
	 */
	public void updateCineplex(int index) {
		
		if(index < 0 || index > listOfCineplexes.size()) {
			System.out.println("Invalid cineplex");
			return;
		}
		
		
		String name,loc;
		String oldname;
		char ans,ans1;

		
		do {
		CommandUI.printBox("Cineplex UPDATE menu for "+listOfCineplexes.get(index-1).getName(), 5, 3);
		System.out.println("[1] Change Name");
		System.out.println("[2] Change Location");
		System.out.println("[3] Exit");
		
		
		
		int userInput = CommandUI.getNumberInput(1, 3);
			switch (userInput) {
				case 1:
					System.out.print("Please enter new name for cineplex: ");
					name = sc.nextLine();

					System.out.println("Entered name is "+name);
					System.out.print("Please confirm your name [Y/N]: ");
					ans = sc.nextLine().charAt(0);
					
					if(ans == 'Y' || ans == 'y') {
						
						oldname = listOfCineplexes.get(index-1).getName();
						
						// Update showtime entries
						Manager.sm.updateCineplexNameForShowTime(oldname, name);
						Manager.bm.updateTicketCineplexNameForShowTime(oldname, name);
						listOfCineplexes.get(index-1).setName(name);
						FileIO.saveToFile(listOfCineplexes, Directory.DIR_CINEPLEX);
						reloadData();
						
						System.out.println("Cineplex name updated.");
					}else {
						System.out.println("Cineplex name not updated.");
					}
					break;
				case 2:
					System.out.print("Please enter new location for cineplex: ");
					loc = sc.nextLine();
					
					System.out.println("Entered location is "+loc);
					System.out.print("Please confirm your location [Y/N]: ");
					ans1 = sc.nextLine().charAt(0);
					
					if(ans1 == 'Y') {
						listOfCineplexes.get(index-1).setLocation(loc);
						FileIO.saveToFile(listOfCineplexes, Directory.DIR_CINEPLEX);
						reloadData();
						System.out.println("Cineplex location updated.");
					}else {
						System.out.println("Cineplex location not updated.");
					}
					break;
				case 3:
					return;
				default:
					break;
			}
		}while(true);
	}
	
	/**
	 * Remove cineplex details base on cineplex index
	 * @param index Position of cineplex object inside the list
	 */
	public void removeCineplex(int index) {
		Manager.sm.removeShowTimeFromCineplex(listOfCineplexes.get(index-1).getName());
		listOfCineplexes.remove(index-1);
		FileIO.saveToFile(listOfCineplexes, Directory.DIR_CINEPLEX);
		reloadData();
	}
	
	/**
	 * Print a list of all the cineplexes 
	 */
	public void printCineplexes() {
		CommandUI.printBox("List of cineplexes",3,7);
		System.out.println();
		for(int k=0;k<listOfCineplexes.size();k++) {
			System.out.println("["+(k+1)+"] "+listOfCineplexes.get(k).getName());
		}
	}
	
	/**
	 * Get cineplex based on cineplex index
	 * @param cineplex Position of cineplex object inside the list
	 * @return Returns the corresponding cineplex object
	 */
	public Cineplex getCineplex(int cineplex) {
		return listOfCineplexes.get(cineplex-1);
	}
	
	/**
	 * Get cineplex object based on name of cineplex
	 * @param cineplex Name of cineplex
	 * @return Returns the corresponding cineplex object
	 */
	public Cineplex getCineplex(String cineplex) {
		Cineplex temp;
		for(int i=0;i<listOfCineplexes.size();i++) {
			temp = listOfCineplexes.get(i);
			if(temp.getName().compareTo(cineplex) == 0) {
				return temp;
			}
		}
		
		return null;
	}
	
	
	@Override
	public void reloadData() {
		listOfCineplexes.clear();
		listOfCineplexes.addAll(FileIO.readFile(Directory.DIR_CINEPLEX));
	}
	
	@Override
	public void saveAndReloadData() {
		FileIO.saveToFile(listOfCineplexes, Directory.DIR_CINEPLEX);
		reloadData();
		
	}
	
}
