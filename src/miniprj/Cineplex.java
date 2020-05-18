package miniprj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;
/** Represents a Cineplex 
 * @author Oh Yao Wen
 * @author Fong Hao Wei
 * @author Aleem Siddique
 * @author Alex Neo
 * @author Cheng Chee How
 * @version 1.0
 * @since 1.0
*/
public class Cineplex implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/**
	 * Name of cineplex
	 */
	private String name;
	/**
	 * Location of cineplex
	 */
	private String location;
	/**
	 * List of all cinemas under this cineplex
	 */
	private ArrayList<Cinema> listOfCinemas = new ArrayList<Cinema>();
	private transient Scanner sc = new Scanner(System.in);
	
	/**
	 * Construct cineplex object with existing values
	 * @param name Name of cineplex
	 * @param loc Location of cineplex
	 * @param noOfCinema List of cinemas for this cineplex
	 */
	public Cineplex(String name,String loc,ArrayList<Cinema> noOfCinema){
		this.name = name;
		this.location = loc;
		listOfCinemas = noOfCinema;
	}
	
	
	/**
	 * Construct cineplex object
	 */
	public Cineplex(){
		
		System.out.print("Please enter cineplex name: ");
		name = sc.nextLine();

		System.out.print("Please enter cineplex location: ");
		location = sc.nextLine();
		
		System.out.print("Please enter number of cinemas to create: ");
		
		int noOfCinema = CommandUI.getNumberInput(1, 10);
		addCinema(noOfCinema);

		
	}
	
	/**
	 * Get cineplex name
	 * @return Returns cineplex name as a string
	 */
	public String getName() {
		return name;
	}
	/**
	 * Get cineplex location
	 * @return Returns cineplex location as a string
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * Set cineplex name
	 * @param name Name of cineplex
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Set cineplex location
	 * @param loc Location of cineplex
	 */
	public void setLocation(String loc) {
		this.location = loc;
	}
	/**
	 * Get cinema object based on cinema index location
	 * @param cinema Index of cinema object 
	 * @return Returns cinema object
	 */
	public Cinema getCinema(int cinema) {
		return listOfCinemas.get(cinema-1);
	}
	/**
	 * Add new cinema 
	 * @param noOfCinema Number of cinemas to add
	 */
	public void addCinema(int noOfCinema) {
		
		sc = new Scanner(System.in);
		
		int noSeats, cClass, noSeatsPerRow, id;
		
		
		for(int i=0; i<noOfCinema; i++) {
			
			
			System.out.print("Please enter cinema name for Cinema "+(i+1)+": ");
			String cin_name = sc.nextLine();
			
			
			while(cin_name.length() == 0) {
				System.out.print("Invalid input! Please enter name for Cinema "+(i+1)+":");
				cin_name = sc.nextLine();
			}
			
			id = Math.abs(name.concat(cin_name).hashCode());
			
			System.out.print("Please enter number of seats for Cinema "+(i+1)+": ");
			noSeats = CommandUI.getNumberInput(20, 200);
			
			System.out.print("Please enter number of seats per row for Cinema "+(i+1)+": ");
			noSeatsPerRow = CommandUI.getNumberInput(5, 18);

			CommandUI.printBox("Please select Cinema Class for Cinema "+(i+1), 5, 3);
			for(int k=0;k<CinemaClass.values().length;k++) {
				System.out.println("["+(k+1)+"] "+CinemaClass.values()[k]);
			}
			cClass = CommandUI.getNumberInput(1, CinemaClass.values().length);
			
			listOfCinemas.add(new Cinema(id,CinemaClass.values()[cClass-1],cin_name,noSeats,noSeatsPerRow));
		}
	}
	
	/**
	 * Get cinema object based on name of cinema
	 * @param cinema Name of cinema
	 * @return Cinema object
	 */
	public Cinema getCinema(String cinema) {
		Cinema temp;
		for(int i=0;i<listOfCinemas.size();i++) {
			temp = listOfCinemas.get(i);
			if(temp.getCinemaName().compareTo(cinema) == 0) {
				return temp;
			}
		}
		
		return null;
	}
	/**
	 * Get cinema index based on cinema name
	 * @param cinema Name of cinema
	 * @return Cinema index
	 */
	public int getCinemaIndex(String cinema) {
		Cinema temp;
		for(int i=0;i<listOfCinemas.size();i++) {
			temp = listOfCinemas.get(i);
			if(temp.getCinemaName().compareTo(cinema) == 0) {
				return i;
			}
		}
		
		return -1;
	}
	/**
	 * Remove a cinema base on cinema index
	 * @param index Position of cinema object inside the list
	 */
	public void removeCinema(int index) {
		String cinema = listOfCinemas.get(index-1).getCinemaName();
		listOfCinemas.remove(index-1);
		Manager.sm.removeShowTimeFromCinema(cinema);
		Manager.cm.saveAndReloadData();
	}
	
	
	/**
	 * Print details of all the cinemas under the cineplex
	 */
	public void printCinemaDetails() {
		
		System.out.println();
		CommandUI.printBox("There are a total of "+listOfCinemas.size()+" cinemas",5,3);
		System.out.println();
		
		for(int i=0;i<listOfCinemas.size();i++) {
			System.out.println((i+1)+" Cinema "+listOfCinemas.get(i).getCinemaID());
			System.out.println("  No of Seats:  "+listOfCinemas.get(i).getNoOfSeats());
			System.out.println();
		}
	}
	
	/**
	 * Print the names all of the cinemas under the cineplex
	 */
	public void printCinemas() {
		System.out.println();
		CommandUI.printBox("List of cinemas under "+this.name,5,3);
		System.out.println();
		for(int k=0;k<listOfCinemas.size();k++) {
			System.out.println("["+(k+1)+"] "+listOfCinemas.get(k).getCinemaName());
			
		}
	}
	
	/**
	 * Update cinema object base on cinema index
	 * @param index Position of cinema object inside the list
	 */
	public void updateCinema(int index) {
		
		sc = new Scanner(System.in);
		
		String name,loc;
		String oldname;

		do {
		try {
		CommandUI.printBox("Cinema UPDATE menu for "+listOfCinemas.get(index-1).getCinemaName(), 5, 3);
		System.out.println("[1] Change Name");
		System.out.println("[2] Exit");
		
		}catch(IndexOutOfBoundsException e) {
			System.out.println("Unable to update cinema.");
			return;
		}
		
		int userInput = CommandUI.getNumberInput(1, 2);
			switch (userInput) {
				case 1:
					System.out.print("Please enter new name for cinema: ");
					name = sc.nextLine();
					
					oldname = listOfCinemas.get(index-1).getCinemaName();
					listOfCinemas.get(index-1).setCinemaName(name);
					Manager.sm.updateCinemaNameForShowTime(oldname, name);
					Manager.bm.updateTicketCinemaNameForShowTime(oldname, name);
					Manager.cm.saveAndReloadData();
					break;
				case 2:
					return;
				default:
					break;
			}
		}while(true);
	}
	
	
	/**
	 * Method used to print available seats inside a cinema for a specific movie,data and time
	 * @param st ShowTimeItems object with show time details
	 */
	public void printAvailableSeats(ShowTimeItems st) {
		
		System.out.println();
		System.out.println();
		
		String cineplex = st.getCineplex();
		String cinema = st.getCinema();
		String time = st.getTiming();
		String date = st.getDate();
		
		
		int CinemaIndex = getCinemaIndex(st.getCinema());
		Seat[][] temp = listOfCinemas.get(CinemaIndex).getSeatLayout();
		 
		
		char a = (char) ('A'+(temp.length-1));
		int noOfSeatsPerRow = listOfCinemas.get(CinemaIndex).getNoOfSeatsPerRow();
		int printOffset = (noOfSeatsPerRow*3)/2;
		int k;
				

		for(k=0;k<printOffset+4;k++) {
			System.out.print(" ");
		}

		
		System.out.println("S C R E E N");
		
		System.out.println();
		
		System.out.print("         ");
		
		for(k=0;k<noOfSeatsPerRow;k++) {
			System.out.print((k+1));
			
			if(noOfSeatsPerRow>10 && (k==1 || k==noOfSeatsPerRow-3)) {
				
				System.out.print("   ");
			}else if(noOfSeatsPerRow>8 && noOfSeatsPerRow <11 && k==(noOfSeatsPerRow-1)/2) {
				System.out.print("   ");
			}
			
			if((k+1)<9) {
				System.out.print("  ");
			}else {
				System.out.print(" ");
			}
		}
		
		System.out.println();
		
		
		if(noOfSeatsPerRow>10) {
			for(k=0;k<noOfSeatsPerRow*3+24;k++) {
				System.out.print("-");
			}
		} else {
			for(k=0;k<noOfSeatsPerRow*3+18;k++) {
				System.out.print("-");
			}
		}
		
		System.out.println();
		System.out.println();
		
		
		for(int i=0;i<temp.length;i++) {
			System.out.print("Row "+(a)+"   ");
			for(int j=0;j<temp[i].length;j++) {
				if(temp[i][j] != null) {
					if(Manager.bm.checkBooking(date,cineplex,cinema,time,temp[i][j].getSeatID())) {					// If seat assigned for that time slot print X
						System.out.print("[X]");
					}else {
						System.out.print("[ ]");
					}
				}else {
					System.out.print("   ");
				}
				
				if(noOfSeatsPerRow>10) {
					if(j == 1) {
						System.out.print("   ");
					}
					
					if(j == temp[i].length-3) {
						System.out.print("   ");
					}
				}else if(noOfSeatsPerRow>8){
					if(j == (temp[i].length-1)/2) {
						System.out.print("   ");
					}
				}
			}
			System.out.println();
			a--;
		}
		
		
		System.out.println();
		System.out.println();
		if(noOfSeatsPerRow>10) {
			for(k=0;k<noOfSeatsPerRow*3+24;k++) {
				System.out.print("-");
			}
		} else {
			for(k=0;k<noOfSeatsPerRow*3+18;k++) {
				System.out.print("-");
			}
		}
		System.out.println();

		for(k=0;k<printOffset+2;k++) {
			System.out.print(" ");
		}

		
		System.out.println("E N T R A N C E");
		
		System.out.println();
		
		for(k=0;k<printOffset+1;k++) {
			System.out.print(" ");
		}
		
		System.out.println("[ ] Seat not occupied");
		
		for(k=0;k<printOffset+1;k++) {
			System.out.print(" ");
		}
		
		System.out.println("[X] Seat occupied");
		
		System.out.println();
		
	}

	
}
