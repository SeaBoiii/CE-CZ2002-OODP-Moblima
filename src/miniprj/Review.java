package miniprj;

import java.io.Serializable;
/**Represents a movie review
 * @author Oh Yao Wen
 * @author Fong Hao Wei
 * @author Aleem Siddique
 * @author Alex Neo
 * @author Cheng Chee How
 * @version 1.0
 * @since 1.0
*/
public class Review implements Serializable{
	/**
	 * Name of user who wrote the review
	 */
	private String name;
	/**
	 * Title of review
	 */
	private String title;
	/**
	 * Movie rating
	 */
	private int rating;
	/**
	 * Review content
	 */
	private String content;
	
	/**
	 * Create review 
	 * @param title Title of review
	 * @param name Name of user who wrote the review
	 * @param content Content of review
	 * @param rating Rating of review
	 */
	public Review(String title,String name,String content, int rating) {
		this.name = name;
		this.title = title;
		this.content = content;
		this.rating = rating;
	}
	
	/**
	 * Get review rating
	 * @return Returns movie rating
	 */
	public int getRating() {
		return rating;
	}
	
	/**
	 * Get movie content
	 * @return Returns review content as a string
	 */
	public String getContent() {
		return content;
	}

	/**
	 * Get name of user who wrote the review
	 * @return Return review name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get title of review
	 * @return Returns the title of the review
	 */
	public String getTitle() {
		return title;
	}

}
