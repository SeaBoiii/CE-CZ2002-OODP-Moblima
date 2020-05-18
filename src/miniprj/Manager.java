package miniprj;

/** Store all the manager objects
 * @author Oh Yao Wen
 * @author Fong Hao Wei
 * @author Aleem Siddique
 * @author Alex Neo
 * @author Cheng Chee How
 * @version 1.0
 * @since 1.0
*/

public class Manager {
	public static AdminManager am = new AdminManager();
	public static MovieManager mm = new MovieManager();
	public static CineplexManager cm = new CineplexManager();
	public static ShowTimeManager sm = new ShowTimeManager();
	public static BookingManager bm = new BookingManager();
	public static PriceManager pm = new PriceManager(9,-3);
}
