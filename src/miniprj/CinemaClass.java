package miniprj;
/** Represents all the available cinema classes
 * @author Oh Yao Wen
 * @author Fong Hao Wei
 * @author Aleem Siddique
 * @author Alex Neo
 * @author Cheng Chee How
 * @version 1.0
 * @since 1.0
*/
public enum CinemaClass {
	 NORMAL,
	 ELITE,
	 PLATINUM;
	public static CinemaClass valueIgnoreCase(String cinemaClass) {
        cinemaClass = cinemaClass.toUpperCase();
        return valueOf(cinemaClass);
    }
}
