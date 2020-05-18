package miniprj;

import java.io.Serializable;
import java.text.SimpleDateFormat;
/**Represents a show time entry
 * @author Oh Yao Wen
 * @author Fong Hao Wei
 * @author Aleem Siddique
 * @author Alex Neo
 * @author Cheng Chee How
 * @version 1.0
 * @since 1.0
*/
public class ShowTimeItems implements Comparable<ShowTimeItems>, Serializable{
	/**
	 * Movie show time
	 */
	private String timing;
	/**
	 * Movie show date
	 */
	private String date;
	/**
	 * Cineplex name
	 */
	private String cineplex; 
	/**
	 * Cinema name
	 */
	private String cinema;
	/**
	 * Movie name
	 */
	private String movie;
	
	
	/**
	 * Construct ShowTimeItems object
	 * @param timing Movie show time
	 * @param date Movie show date
	 * @param cineplex Cineplex name
	 * @param cinema Cinema name
	 * @param movie Movie name
	 */
	public ShowTimeItems(String timing, String date, String cineplex, String cinema, String movie) {
		//timeFormat.format(this.timing);
		//dateFormat.format(this.date);
		
		this.date = date;
		this.timing = timing;
		this.cineplex = cineplex;
		this.cinema = cinema;
		this.movie = movie;
	}
	/**
	 * Get movie name
	 * @return Retuns movie name
	 */
	public String getMovie() {
		return movie;
	}
	/**
	 * Set movie name
	 * @param movie Movie name
	 */
	public void setMovie(String movie) {
		this.movie = movie;
	}
	/**
	 * Get date
	 * @return Returns date
	 */
	public String getDate() {
		//date = dateFormat.format(date);
		return date;
	}
	/**
	 * Set date
	 * @param date New date value
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * Get Cineplex name
	 * @return Returns cineplex name
	 */
	public String getCineplex() {
		return cineplex;
	}
	/**
	 * Set cineplex name
	 * @param cineplex Name of cineplex
	 */
	public void setCineplex(String cineplex) {
		this.cineplex = cineplex;
	}
	/**
	 * Set cinema name
	 * @param cinema New cinema name
	 */
	public void setCinema(String cinema) {
		this.cinema = cinema;
	}
	/**
	 * Get cinema name
	 * @return Returns cinema name
	 */
	public String getCinema() {
		return cinema;
	}
	/**
	 * Get timing 
	 * @return Returns timing
	 */
	public String getTiming() {
		//timing = timeFormat.format(timing);
		return timing;
	}
	/**
	 * Set timing
	 * @param timing New timing
	 */
	public void setTiming(String timing) {
		this.timing = timing;
	}
	
	/**
	 * Print show time details
	 */
	public void printDetails() {
		System.out.println("Cineplex: " + cineplex);
		System.out.println("Date: " + date);
		System.out.println("Timing: " + timing);
		System.out.println("Cinema No.: " + cinema);
		System.out.println("Movie Title: " + movie);
	}

	/**
	 * Used for sorting
	 */
	public int compareTo(ShowTimeItems o) {
		String compare_cineplex=o.getCineplex();
        String compare_date=o.getDate();
        String compare_time=o.getTiming();
        
        /* For Ascending order*/
        if(this.cineplex.compareTo(compare_cineplex) == 0) {
            if(this.date.compareTo(compare_date) == 0) {
                return this.timing.compareTo(compare_time);
            } else {
                return this.date.compareTo(compare_date);
            }
        } else {
            return this.cineplex.compareTo(compare_cineplex);
        }
	}
}
