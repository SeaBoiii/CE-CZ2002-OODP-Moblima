package miniprj;

import java.io.Serializable;
import java.util.ArrayList;
/** Represents a Movie
 * @author Oh Yao Wen
 * @author Fong Hao Wei
 * @author Aleem Siddique
 * @author Alex Neo
 * @author Cheng Chee How
 * @version 1.0
 * @since 1.0
*/
public class Movie implements Serializable, Comparable<Movie>{

	private static final long serialVersionUID = -8018368790718296077L;
	/**
	 * Title of movie
	 */
	private String title;
	/**
	 * Movie synopsis
	 */
	private String synopsis;
	/**
	 * Movie director
	 */
	private String director;
	/**
	 * Movie status (COMING_SOON etc..)
	 */
	private MovieStatus movieStatus;
	/**
	 * Type of movie (BLOCKBUSTER etc..)
	 */
	private MovieType movieType;
	/**
	 * Movie rating (M18 etc..)
	 */
	private MovieRatingType movieRatingType;
	/**
	 * Movie cast
	 */
	private String[] cast;
	/**
	 * List of reviews regarding the movie
	 */
	private ArrayList<Review> reviews = new ArrayList<Review>();

	/**
	 * Construct a new Movie
	 * @param title Movie title
	 * @param director Movie director 
	 * @param cast Movie cast
	 * @param synopsis Movie synopsis
	 * @param movieStatus Movie status
	 * @param type Type of movie
	 * @param movieRatingType Movie rating
	 */
	public Movie(String title, String director ,String[] cast, String synopsis, MovieStatus movieStatus, MovieType type, MovieRatingType movieRatingType) {
		this.title = title;
		this.director =director;
		this.cast = cast;
		this.synopsis = synopsis;
		this.movieStatus = movieStatus;
		this.movieType = type;
		this.movieRatingType = movieRatingType;
	}
	
	/**
	 * Get movie title
	 * @return Returns the movie title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * Get movie rating
	 * @return movieRatingType Returns movie rating
	 */
	public MovieRatingType getMovieRatingType() {
		return movieRatingType;
	}
	/**
	 * Get movie rating
	 * @param movieRatingtype Returns movie rating as a MovieRatingType enum
	 */
	public void setMovieRatingType(MovieRatingType movieRatingtype) {
		this.movieRatingType = movieRatingtype;
	}
	/**
	 * Get movie status
	 * @return Returns movie status as a MovieStatus enum
	 */
	public MovieStatus getStatus() {
		return movieStatus;
	}
	/**
	 * Set movie status
	 * @param status Movie status 
	 */
	public void setStatus(MovieStatus status) {
		this.movieStatus = status; 
	}
	/**
	 * Set movie title
	 * @param title Movie title
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * Get movie Synopsis 
	 * @return Returns movie synopsis as a string
	 */
	public String getSynopsis() {
		return synopsis;
	}
	/**
	 * Set movie synopsis 
	 * @param synopsis Synopsis content
	 */
	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}
	/**
	 * Set movie director
	 * @param director Name of movie director
	 */
	public void setDirector(String director) {
		this.director = director;
	}
	/**
	 * Get movie director name
	 * @return Returns movie director's name
	 */
	public String getDirector() {
		return director;
	}
	/**
	 * Set list of movie cast members
	 * @param cast String array of cast members names
	 */
	public void setCast(String[] cast) {
		this.cast = cast;
	}
	/**
	 * Get all the movie cast members
	 * @return Returns a string array of movie cast members
	 */
	public String[] getCast() {
		return cast;
	}
	/**
	 * Set review for movie
	 * @param reviews Review entry 
	 */
	public void setReview(Review reviews) {
		this.reviews.add(reviews);
	}
	/**
	 * Get all the reviews of the movie
	 * @return List of reviews
	 */
	public ArrayList<Review> getReviews() {
		return reviews;
	}
	/**
	 * Get rating  
	 * @return Return rating value 
	 */
	public double getRating() {//geting review from array of reviews
		double avg=0,temp=0;
		
		if(reviews.isEmpty())
			return 0;
		
		for(int i=0; i< reviews.size() ; i++ ) {
			reviews.get(i).getRating();
			temp += reviews.get(i).getRating();
		}
			avg = temp / reviews.size();
			return CommandUI.round(avg, 1);
	}

	/**
	 * Get movie type
	 * @return Returns movie type as a enum
	 */
	public MovieType getMovieType() {
		return movieType;
	}
	/**
	 * Get move type based on movie array index
	 * @param i Index for movie
	 * @return Movie type
	 */
	public String getMovieType(int i) {
		return movieType.getName();
	}

	/**
	 * Set movie type for movie
	 * @param movieType MovieType enum value
	 */
	public void setMovieType(MovieType movieType) {
		this.movieType = movieType;
	}

	@Override
	public int compareTo(Movie o) {
		// TODO Auto-generated method stub
		double temp = getRating();
		if (temp < o.getRating())
			return 1;
		else if(temp > o.getRating())
			return -1;
		else
			return 0;
	}
	
	
}
