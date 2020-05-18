package miniprj;

import java.math.BigDecimal;
/** Public holiday entry 
 * @author Oh Yao Wen
 * @author Fong Hao Wei
 * @author Aleem Siddique
 * @author Alex Neo
 * @author Cheng Chee How
 * @version 1.0
 * @since 1.0
*/
public class HolidayPriceEntry extends PriceEntry{

	/**
	 *  Day of the month
	 */
	private int day;
	/**
	 *  Store the month value
	 */
	private int month;
	
	/**
	 * Construct a HolidayPriceEntry object
	 * @param name Name of holiday
	 * @param price Price offset amount
	 * @param day Day of the holiday
	 * @param month Month of the hoiday
	 */
	public HolidayPriceEntry(String name, BigDecimal price, int day, int month) {
		super(name, price);
		this.setDay(day);
		this.setMonth(month);
	}
	/**
	 * Get month of holiday
	 * @return Returns the month of the holiday as a integer.
	 */
	public int getMonth() {
		return month;
	}
	/**
	 * Set month of holiday
	 * @param month Month of holiday
	 */
	public void setMonth(int month) {
		this.month = month;
	}
	/**
	 * Get day of the holiday
	 * @return returns the day as a integer
	 */
	public int getDay() {
		return day;
	}
	/**
	 * Set day of the holiday
	 * @param day Day of the month for the holiday
	 */
	public void setDay(int day) {
		this.day = day;
	}

}
