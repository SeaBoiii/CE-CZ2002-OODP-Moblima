package miniprj;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
/**Control class for ticket pricing tasks
 * @author Oh Yao Wen
 * @author Fong Hao Wei
 * @author Aleem Siddique
 * @author Alex Neo
 * @author Cheng Chee How
 * @version 1.0
 * @since 1.0
*/
public class PriceManager implements Serializable, FileMethods{
	
	/**
	 * Movie ticket base price
	 */
	private BigDecimal basePrice = new BigDecimal(9);
	/**
	 * Senior citizen ticket price offset
	 */
	private BigDecimal seniorPrice = new BigDecimal(-3);
	/**
	 * Price manager list labels
	 */	
	public enum PMLabel {DAY,CINEMA_CLASS,MOVIE_TYPE,PUBLIC_HOLIDAY};
	/**
	 * List of all the price offsets
	 */
	private ArrayList<ArrayList> listOfPrices = new ArrayList<ArrayList>();
	
	/**
	 * Construct PriceManager object
	 * @param basePrice Base pricing
	 * @param seniorPrice Senior price offset
	 */
	public PriceManager(int basePrice, int seniorPrice) { //constructor
		this.basePrice = new BigDecimal(basePrice);
		this.seniorPrice = new BigDecimal(seniorPrice);
		this.reloadData();
		
	}
	
	/**
	 * Print all the price offsets inside the price list
	 */
	public void printPriceList() {
		
		
		System.out.println("\n[INFORMATION]\n"
				+ "The following indicates the offset for the base price\n"
				+ "For example if MONDAY have a offset of $10.\n"
				+ "Every movie ticket pricing for MONDAY would add extra $10 on top of the base price\n"
				+ "To discount the price of the ticket enter a negative value for example -10\n");
		
		
		System.out.format("%-30s","BASE PRICE");
		System.out.print(CommandUI.round(basePrice.doubleValue(),2));
		System.out.println();
		System.out.println();
		CommandUI.printTitleWithLine("[1] DAY PRICE OFFSET",46);
		System.out.println();
		for(int i=0;i<listOfPrices.get(PMLabel.DAY.ordinal()).size();i++) {
			System.out.println();
			System.out.format("%-30s",DayOfWeek.values()[i].toString());
			System.out.print(CommandUI.round(((PriceEntry) listOfPrices.get(PMLabel.DAY.ordinal()).get(DayOfWeek.values()[i].ordinal())).getPrice().doubleValue(),2));
		
		}
		
		System.out.println("\n");
		CommandUI.printTitleWithLine("[2] CINEMA CLASS PRICE OFFSET", 46);
		System.out.println();
		for(int i=0;i<listOfPrices.get(PMLabel.CINEMA_CLASS.ordinal()).size();i++) {
			System.out.println();
			System.out.format("%-30s",CinemaClass.values()[i].toString());
			System.out.print(CommandUI.round(((PriceEntry) listOfPrices.get(PMLabel.CINEMA_CLASS.ordinal()).get(CinemaClass.values()[i].ordinal())).getPrice().doubleValue(),2));
		}
		
		System.out.println("\n");
		CommandUI.printTitleWithLine("[3] MOVIE TYPE PRICE OFFSET", 46);
		System.out.println();
		for(int i=0;i<listOfPrices.get(PMLabel.MOVIE_TYPE.ordinal()).size();i++) {
			System.out.println();
			System.out.format("%-30s",MovieType.values()[i].toString());
			System.out.print(CommandUI.round(((PriceEntry) listOfPrices.get(PMLabel.MOVIE_TYPE.ordinal()).get(MovieType.values()[i].ordinal())).getPrice().doubleValue(),2));
		}
		
		System.out.println("\n");
		CommandUI.printTitleWithLine("[4] PUBLIC HOLIDAY PRICE OFFSET", 46);
		System.out.println();
		System.out.println();
		for(int i=0;i<listOfPrices.get(PMLabel.PUBLIC_HOLIDAY.ordinal()).size();i++) {
			ArrayList<PriceEntry> temp = listOfPrices.get(PMLabel.PUBLIC_HOLIDAY.ordinal());
			System.out.format("%-30s",temp.get(i).getName()+"("+((HolidayPriceEntry) temp.get(i)).getDay()+"/"+((HolidayPriceEntry) temp.get(i)).getMonth()+") ");
			System.out.println(CommandUI.round(temp.get(i).getPrice().doubleValue(),2));

		}
		
		
	}
	
	/**
	 * Print price entries based on category
	 * @param category Price category
	 */
	public void printPriceEntries(int category) {
		
		if(category != PMLabel.PUBLIC_HOLIDAY.ordinal()+1) {
			ArrayList<PriceEntry> temp = listOfPrices.get(category-1);
			
			for(int i=0;i<temp.size();i++) {
				System.out.println();
				System.out.print("["+(i+1)+"] ");
				
				if((category-1) == PMLabel.DAY.ordinal()) {
					System.out.format("%-9s",DayOfWeek.values()[i].toString());	
				}else if((category-1) == PMLabel.CINEMA_CLASS.ordinal()) {
					System.out.format("%-9s",CinemaClass.values()[i].toString());	
				}else {
					System.out.format("%-9s",MovieType.values()[i].toString());	
				}
				System.out.print("		"+CommandUI.round(temp.get(i).getPrice().doubleValue(), 2));
			}
		}else {
			ArrayList<HolidayPriceEntry> temp = listOfPrices.get(category-1);
			
			for(int i=0;i<temp.size();i++) {
				System.out.println();
				System.out.print("["+(i+1)+"] ");
				System.out.format("%-9s",temp.get(i).getName()+" ("+temp.get(i).getDay()+"/"+temp.get(i).getMonth()+") ");
				System.out.print("		"+CommandUI.round(temp.get(i).getPrice().doubleValue(), 2));
			}	
		}
		
		System.out.println();
	}
	
	
	/**
	 * Update holiday names
	 * @param entry Holiday entry index
	 * @param name New name of holiday
	 */
	public void updateHolidayNames(int entry, String name) {
		((HolidayPriceEntry) listOfPrices.get(PMLabel.PUBLIC_HOLIDAY.ordinal()).get(entry)).setName(name);
		this.saveAndReloadData();
	}
	/**
	 * Update price offset 
	 * @param category Price category
	 * @param entry Price entry index
	 * @param price New price offset to be used
	 */
	public void updatePricing(int category, int entry, double price) {
		((PriceEntry) listOfPrices.get(category-1).get(entry-1)).setPrice(new BigDecimal(price));
		this.saveAndReloadData();
	}
	/**
	 * Add public holiday
	 * @param name Name of public holiday
	 * @param day Day of public holiday
	 * @param month Month of public holiday
	 * @param price Price offset of public holiday
	 */
	public void addPublicHoliday(String name, int day, int month, double price) {
		
		HolidayPriceEntry temp = new HolidayPriceEntry(name,new BigDecimal(price),day,month);
		
		listOfPrices.get(PMLabel.PUBLIC_HOLIDAY.ordinal()).add(temp);
		this.saveAndReloadData();
	}
	/**
	 * Get ticket price offset
	 * @param item Show time details
	 * @param age Age of user creating the booking
	 * @return Returns ticket price
	 */
	public double getTicketPrice(ShowTimeItems item,int age) {
		String date,cineplex,cinema,movie;
		
		cineplex = item.getCineplex();
		cinema = item.getCinema();
		movie = item.getMovie();
		LocalDate ld = LocalDate.parse(item.getDate() ,DateTimeFormatter.ofPattern( "dd-MM-yyyy" ));
		Cineplex c = Manager.cm.getCineplex(cineplex);
		Cinema cne = c.getCinema(cinema);
		
		PriceEntry p = (PriceEntry) listOfPrices.get(PMLabel.DAY.ordinal()).get(ld.getDayOfWeek().ordinal());
		PriceEntry m = (PriceEntry) listOfPrices.get(PMLabel.MOVIE_TYPE.ordinal()).get(Manager.mm.getMovie(movie).getMovieType().ordinal());
		PriceEntry cc = (PriceEntry) listOfPrices.get(PMLabel.CINEMA_CLASS.ordinal()).get(cne.getCinemaClass().ordinal());
		
		BigDecimal dayPrice = p.getPrice();
		BigDecimal moviePrice = m.getPrice();
		BigDecimal cinemaPrice = cc.getPrice();
		
		ArrayList<HolidayPriceEntry> pe = listOfPrices.get(PMLabel.PUBLIC_HOLIDAY.ordinal());
		
		BigDecimal holidayPrice = new BigDecimal(0);
		
		for(int i = 0;i<pe.size();i++) {
			if(pe.get(i).getDay()==ld.getDayOfMonth()) {
				if(pe.get(i).getMonth()==ld.getMonthValue()){
					holidayPrice=holidayPrice.add(pe.get(i).getPrice());	
				}
			}
		}
		BigDecimal result;
		if(age > 55) {
			result = basePrice.add(dayPrice).add(moviePrice).add(cinemaPrice).add(holidayPrice).add(seniorPrice);
		}else {
			result = basePrice.add(dayPrice).add(moviePrice).add(cinemaPrice).add(holidayPrice);	
		}
		
	
		
		return CommandUI.round(result.doubleValue(), 2);
	}
	
	

	@Override
	public void reloadData() {
		listOfPrices.clear();
		listOfPrices.addAll(FileIO.readFile(Directory.DIR_LIST_OF_PRICES));
		
		if(listOfPrices.size() == 0) {
			ArrayList<PriceEntry> dayPrice = new ArrayList<PriceEntry>();
			ArrayList<PriceEntry> cinemaClass = new ArrayList<PriceEntry>();
			ArrayList<PriceEntry> movieTypePrice = new ArrayList<PriceEntry>();
			ArrayList<HolidayPriceEntry> publicHolidayPrice = new ArrayList<HolidayPriceEntry>();
			
			BigDecimal zero = new BigDecimal(0);
			//Initialize the price
			for(int i=0;i<DayOfWeek.values().length;i++) {
				dayPrice.add(new PriceEntry(DayOfWeek.values()[i].toString(),zero));
			}
			for(int i=0;i<CinemaClass.values().length;i++) {
				cinemaClass.add(new PriceEntry(CinemaClass.values()[i].toString(),zero));
			}
			for(int i=0;i<MovieType.values().length;i++) {
				movieTypePrice.add(new PriceEntry(MovieType.values()[i].toString(),zero));
			}
			
			
			listOfPrices.add(dayPrice);
			listOfPrices.add(cinemaClass);
			listOfPrices.add(movieTypePrice);
			listOfPrices.add(publicHolidayPrice);	
		}
		
	}
	
	@Override
	public void saveAndReloadData() {
		FileIO.saveToFile(listOfPrices, Directory.DIR_LIST_OF_PRICES);
		reloadData();
		
	}

	public void removeHoliday(int index) {
		listOfPrices.get(PMLabel.PUBLIC_HOLIDAY.ordinal()).remove(index-1);
		this.saveAndReloadData();
		
	}

}
