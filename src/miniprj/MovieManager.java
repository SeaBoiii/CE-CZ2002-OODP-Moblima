
package miniprj;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;
/** Control class for movie related tasks
 * @author Oh Yao Wen
 * @author Fong Hao Wei
 * @author Aleem Siddique
 * @author Alex Neo
 * @author Cheng Chee How
 * @version 1.0
 * @since 1.0
*/
public class MovieManager implements Serializable, FileMethods{

	/**
	 * List to store all the movies
	 */
	private static ArrayList<Movie> movies = new ArrayList<Movie>();
	private Scanner scan = new Scanner(System.in);

	/**
	 * Construct MovieManager object
	 */
	public MovieManager() {
		movies.clear();
		movies.addAll(FileIO.readFile(Directory.DIR_MOVIES));
	}

	/**
	 * Get all movies
	 * @return Array of Movie objects
	 */
	public Movie[] getMovies() {
		Movie[] movieList = new Movie[movies.size()];
	  	for(int i=0; i<movies.size(); i++) {
	  		movieList[i] = movies.get(i);
	  	}
	  	return movieList;
    }
	/**
	 * Get movie from list based on movie name
	 * @param movie Title of movie
	 * @return Returns movie object
	 */
	public Movie getMovie(String movie) {
		for(int i=0; i<movies.size(); i++) {
			if(movies.get(i).getTitle().equals(movie)) {
				return movies.get(i);
			}
		}
		return null;
	}
	/**
	 * Get movie from movie list based movie index
	 * @param movie Movie array index
	 * @return Returns the corresponding movie object
	 */
	public Movie getMovie(int movie) {
		return movies.get(movie-1);
	}
	/**
	 * Get movie for customer based on movie array index
	 * @param movie Array index of movie
	 * @return Returns the corresponding movie object
	 */
	public Movie getMovieForCustomer(int movie) {
		ArrayList<Movie> temp = new ArrayList<>();
		for(int i=0; i<movies.size(); i++) {
			if(movies.get(i).getStatus() == MovieStatus.NOW_SHOWING || movies.get(i).getStatus() == MovieStatus.PREVIEW) {
				temp.add(movies.get(i));
			}
		}
		return temp.get(movie-1);
	}
	/**
	 * Open a menu to update movie details
	 */
	public void updateMovie() {
		if(movies.size() == 0) {
			System.out.println("There is no movie available!");
			return;
		}
		int titleindex;
		CommandUI.printBox("Movie List", 5, 3);
		for(int x=0; x<movies.size(); x++) {
			int y = x+1;
			System.out.println("[" + y + "] " + movies.get(x).getTitle());
		}
		while (true) {
			try {
				System.out.println("Please enter the movie index that you wish to edit [-1 to return]: ");
				titleindex = scan.nextInt();
				if (titleindex == -1)
					return;
				else if (titleindex > movies.size() || titleindex < 1) {
					System.out.println("There is no such index: Please re-enter the correct index.");
					scan.nextLine();
					continue;
					}
				break;
			}
			catch (InputMismatchException e) {
				scan.nextLine();
				System.out.println("You have to input an index and not a character.");
				continue;
			}
		}
		System.out.println("Which of the following do you wish to edit?" );
		System.out.println("[1] Title ");
		System.out.println("[2] Director");
		System.out.println("[3] Cast");
		System.out.println("[4] Movie Status");
		System.out.println("[5] Movie Type");
		System.out.println("[6] Movie Rating Type");
		System.out.println("[7] Back");
		int choice = scan.nextInt();
		scan.nextLine();
		do {
		switch(choice) {
			case 1:
				System.out.println("Please enter the new movie title: ");
				String newTitle = scan.nextLine();
				for(int x=0; x<movies.size(); x++) {//search if the movie existed
					if(movies.get(x).getTitle().compareTo(newTitle) == 0) {
						System.out.println("Movie title exist!");
						return;
					}
				}
				movies.get(titleindex-1).setTitle(newTitle);
				System.out.println("Movie title updated!");
				saveAndReloadData();
				return;
			case 2:
				System.out.println("Please enter the new director: ");
				String newDirector = scan.nextLine();
				movies.get(titleindex-1).setDirector(newDirector);
				saveAndReloadData();
				return;
			case 3:
				int castcount;
				while(true) {
					try {
						System.out.println("Please enter the number of cast:");
						castcount = scan.nextInt();
						scan.nextLine();
						break;
					}
					catch(InputMismatchException e) {
						scan.nextLine();
						System.out.println("Please enter a number!");
						continue;
					}
				}
				if(castcount <=1) {
					System.out.println("You must have at least 2 cast!");
					System.out.println("Please enter the number of cast:");
					castcount = scan.nextInt();
					scan.nextLine();
					if(castcount <=1) {
						return;
					}
				}
				String[] newCast = new String[castcount];
				for(int x=0; x<castcount;x++) {
					System.out.println("Enter the Cast: ");
					newCast[x] = scan.nextLine();
				}
				movies.get(titleindex-1).setCast(newCast);
				System.out.println("Cast updated sucessfully!");
				saveAndReloadData();
				return;
			case 4:
				int status;
				for(int k=0;k<MovieStatus.values().length;k++) {
					System.out.println("["+(k+1)+"] "+MovieStatus.values()[k]);
				}
					while(true) {
								try {
									status = scan.nextInt();
									movies.get(titleindex-1).setStatus(MovieStatus.values()[status-1]);
									saveAndReloadData();
									break;
								}
								catch (InputMismatchException e) {
									scan.nextLine();
									System.out.println("You have to input an index and not a character.");
									System.out.println("Please re-try...");
									continue;
								}
								catch (ArrayIndexOutOfBoundsException f) {
									scan.nextLine();
									System.out.println("Invalid index! Please re-try!");
								}
					}

					System.out.println("Movie Status updated sucessfully!");
					return;
			case 5:
				CommandUI.printBox("Please select movie type", 5, 3);
				int type;
				for(int k=0;k<MovieType.values().length;k++) {
					System.out.println("["+(k+1)+"] "+MovieType.values()[k]);
				}
				while(true) {
					try {
						type = scan.nextInt();
						movies.get(titleindex-1).setMovieType(MovieType.values()[type-1]);
						saveAndReloadData();
						break;
					}
					catch(InputMismatchException e) {
						scan.nextLine();
						System.out.println("You have to input an index and not a character.");
						System.out.println("Please re-try...");
						continue;
					}
					catch (ArrayIndexOutOfBoundsException f) {
						scan.nextLine();
						System.out.println("Invalid index! Please re-try!");
					}
				}
				return;
			case 6:
				CommandUI.printBox("Please select movie rating type", 5, 3);
				int ratingtype;
				for(int k=0;k<MovieRatingType.values().length;k++) {
					System.out.println("["+(k+1)+"] "+MovieRatingType.values()[k]);
				}
				while(true) {
					try {
						ratingtype = scan.nextInt();
						movies.get(titleindex-1).setMovieRatingType(MovieRatingType.values()[ratingtype-1]);
						saveAndReloadData();
						break;
					}
					catch(InputMismatchException e) {
						scan.nextLine();
						System.out.println("You have to input an index and not a character.");
						System.out.println("Please re-try...");
						continue;
					}
					catch (ArrayIndexOutOfBoundsException f) {
						scan.nextLine();
						System.out.println("Invalid index! Please re-try!");
					}
				}
				return;
			case 7:
				return;
			default:
				break;
			}
			}while(true);
}


	/**
	 * Add a new movie
	 */
	public void addMovie() {
		String title;
		String director;
		String synopsis;
		String[] cast;
		System.out.println("Enter Movie Title [-1 to return]: ");
		title = scan.nextLine();
		if (title.equals("-1"))
			return;
		for(int i=0; i<movies.size(); i++) {
			if(movies.get(i).getTitle().contentEquals(title)) {
				System.out.println("The movie exist!");
				return;
			}
		}
		System.out.println("Enter the Director: ");
		director = scan.nextLine();
		System.out.println("Enter the Synopsis: ");
		synopsis = scan.nextLine();
		CommandUI.printBox("Please select movie status", 5, 3);
		try {
			for(int k=0;k<MovieStatus.values().length;k++) {
				if (MovieStatus.values()[k].equals(MovieStatus.END_OF_SHOWING))
					continue;
				System.out.println("["+(k+1)+"] "+MovieStatus.values()[k].getName());
			}
		}
		catch (ArrayIndexOutOfBoundsException e) {
		}
		int status;
		while(true) {
			try {
				status = CommandUI.getNumberInput(1, MovieStatus.values().length-1);
				break;
			}catch(InputMismatchException e) {
				scan.nextLine();
				System.out.println("Please enter a number!");
				continue;
			}
			catch (ArrayIndexOutOfBoundsException f) {
				scan.nextLine();
				System.out.println("Invalid number!");
			}
		}

		CommandUI.printBox("Please select movie type", 5, 3);
		for(int k=0;k<MovieType.values().length;k++) {
			System.out.println("["+(k+1)+"] "+ MovieType.values()[k].getName());
		}

		int type;

		while(true) {
			try {
				type = scan.nextInt();
				if(type>MovieType.values().length || type <= 0) {
					System.out.println("Invalid number!");
					continue;
				}
				break;
			}
			catch(InputMismatchException e) {
				scan.nextLine();
				System.out.println("Please re-try...");
				continue;
			}
			catch (ArrayIndexOutOfBoundsException f) {
				scan.nextLine();
				System.out.println("Invalid index! Please re-try!");
			}
		}
		CommandUI.printBox("Please select movie rating type", 5, 3);
		for(int k=0;k<MovieRatingType.values().length;k++) {
			System.out.println("["+(k+1)+"] "+MovieRatingType.values()[k]);
			}
		int ratingtype;
		while(true) {
			try {
				ratingtype = scan.nextInt();
				MovieRatingType temp = MovieRatingType.values()[ratingtype-1];
				break;
			}
			catch(InputMismatchException e) {
				scan.nextLine();
				System.out.println("You have to input an index and not a character.");
				System.out.println("Please re-try...");
				continue;
			}
			catch (ArrayIndexOutOfBoundsException f) {
				scan.nextLine();
				System.out.println("Invalid index! Please re-try!");
			}
		}
		int number = 0;
		while(true) {
			try {
				System.out.println("Please enter the number of cast: ");
				number = scan.nextInt();
				break;
			}
			catch(InputMismatchException e) {
				scan.nextLine();
				System.out.println("Please enter a number!");
				continue;
			}
		}

		scan.nextLine();
		if(number <= 1) {
			System.out.println("Must have at least 2 cast!");
			System.out.println("Please enter the number of cast:");
			number = scan.nextInt();
			scan.nextLine();
			if(number <=1) {
				return;
			}
		}
		cast = new String[number];

		//scan.nextLine();

		for(int x=0; x<number; x++) {
			System.out.println("Enter the Cast: ");
			cast[x] = scan.nextLine();
		}
		movies.add(new Movie(title,director,cast,synopsis,MovieStatus.values()[status-1],MovieType.values()[type-1],MovieRatingType.values()[ratingtype-1]));
		System.out.println("Added Successfully!");

		FileIO.saveToFile(movies, Directory.DIR_MOVIES);
		saveAndReloadData();
	}
	/**
	 * Remove a movie
	 */
	public void removeMovie() {
		if(movies.size() == 0) {
			System.out.println("There is no movie available!");
			return;
		}
		CommandUI.printBox("Movie List", 5, 3);
		//System.out.println("Movie List:");
		for(int x=0; x<movies.size(); x++) {
			int y = x+1;
			System.out.println(y + ") " + movies.get(x).getTitle());
		}
		int selection;
		while (true) {
			try {
				System.out.println("Please enter the movie index that you wish to edit [-1 to return]: ");
				selection = scan.nextInt();
				if (selection == -1)
					return;
				else if (selection > movies.size() || selection < 1) {
					System.out.println("There is no such index: Please re-enter the correct index.");
					scan.nextLine();
					continue;
				}
				break;
			}
			catch (InputMismatchException e) {
				scan.nextLine();
				System.out.println("You have to input an index and not a character.");
				continue;
			}
		}
		selection --;
		movies.remove(selection);
		System.out.println("Removed Successfully!");
		saveAndReloadData();
	}
	/**
	 * Print all the movies that exist in the Movie list
	 * @return Return TRUE if there exist atleast one movie inside the movie list otherwise FALSE
	 */
	public boolean printMovies() {
		if(movies.size() == 0) {
			System.out.println("There is no movie available!");
			return false;
		}else {
			CommandUI.printBox("Movie List", 5, 3);
			for(int i=0; i<movies.size(); i++) {
				System.out.println("[" + (i+1) + "] " + movies.get(i).getTitle());
			}
			return true;
		}
	}
	/**
	 * Print movies that have a MovieStatus of NOW_SHOWING or PREVIEW
	 * @return Return TRUE if there exist atleast one movie that satisfy the MovieStatus requirements otherwise FALSE
	 */
	public boolean printMoviesForCustomer() {

		boolean check = false;

		if(movies.size() == 0) {
			System.out.println("There is no movie available!");
			return false;
		}else {
			CommandUI.printBox("Movie List", 5, 3);
			int k=1;
			for(int i=0; i<movies.size(); i++) {

				if(movies.get(i).getStatus() == MovieStatus.NOW_SHOWING || movies.get(i).getStatus() == MovieStatus.PREVIEW) {
					System.out.println("[" + (k) + "] " + movies.get(i).getTitle());
					k++;
					check = true;
				}
			}
			return check;
		}
	}
	/**
	 * Print movie details based on movie array index
	 * @param index Movie array index
	 */
	public void printMovieDetails(int index) {

		System.out.println();
		System.out.println();

		movies.get(index-1);
		CommandUI.printBox("Movie Information", 3, 7);
		System.out.printf("Movie Title: 	 %s (%s) %s%n",movies.get(index-1).getTitle(),movies.get(index-1).getMovieType(1),movies.get(index-1).getMovieRatingType().toString());
		System.out.println("Director: 	 "+movies.get(index-1).getDirector());
		System.out.println("Synopsis: 	 "+movies.get(index-1).getSynopsis());
		if(movies.get(index-1).getReviews().size() < 2) {
			System.out.println("Rating: 	 NA");
		}else {
			System.out.println("Rating: 	 "+movies.get(index-1).getRating());
		}
		System.out.println("Show Status: 	 "+movies.get(index-1).getStatus().toString());



		System.out.println();
		String temp[] = movies.get(index-1).getCast();

		System.out.println("Movie Cast:");
		System.out.println("------------");
		for(int i=0;i<temp.length;i++) {
			if ((i)%4 == 0)
				System.out.println();
			System.out.print("("+(i+1)+") "+temp[i]+"  ");
		}

		System.out.println();
		System.out.println();


	}
	/**
	 * Write a review for movie
	 * @param index Movie array index
	 * @throws InputMismatchException If invalid input
	 */
	public void viewReview(int index) throws InputMismatchException{
		Movie temp = getMovie(index);
		Scanner sc = new Scanner(System.in);
		CommandUI.printBox("Review list", 3, 3);
		ArrayList<Review> review = temp.getReviews();
		if (review.size() == 0) {
			System.out.print("Movie do not have reviews.");
			System.out.println();
			return;
		}
		for(int i=0;i<review.size();i++) {
			System.out.println("["+(i+1)+"] "+review.get(i).getTitle());
		}
		while (true) {
			try {
				System.out.print("Please select review to view: ");
				index = sc.nextInt();
				sc.nextLine();
			CommandUI.printBox(review.get(index-1).getTitle(),5,3);
			System.out.println();
			System.out.println("Written by: "+review.get(index-1).getName());
			System.out.println("Rating: "+review.get(index-1).getRating());
			System.out.println();
			System.out.println(review.get(index-1).getContent());
			break;
			}
			catch (InputMismatchException e) {
				sc.nextLine();
				System.out.println("Please select a valid review!");
				continue;
			}
			catch (IndexOutOfBoundsException ex) {
				sc.nextLine();
				System.out.println("Please select a valid review");
				continue;
			}
		}
	}

	public void writeReview(int index) throws InputMismatchException {
		Movie temp = getMovie(index);
		String nameUser, title, content;
		int rating = 0;
		Scanner sc = new Scanner(System.in);
		System.out.print("Please enter your name: ");
		nameUser = sc.nextLine();
		while (true) {
			try {
				System.out.print("Please enter your rating [1-5]: ");
				rating = sc.nextInt();
				sc.nextLine();
				if (rating > 5 || rating < 1) {
					continue;
				}
				break;
			}
			catch (InputMismatchException e) {
				sc.nextLine();
				System.out.println("Please enter a valid rating!");
				continue;
			}
		}
		System.out.print("Please enter a title: ");
		title = sc.nextLine();
		System.out.print("Please enter review: ");
		content = sc.nextLine();
		temp.setReview(new Review(title,nameUser,content,rating));
		saveAndReloadData();
	}

	@SuppressWarnings("unchecked")
	/**
	 * Get top 5 best rated movies
	 */
	public void getRanking() {
		ArrayList<Movie> temp;
		if (movies.isEmpty()) {
			System.out.println("There are no movies");
			return;
		}
		temp = (ArrayList<Movie>) movies.clone();
		Collections.sort(temp);
		CommandUI.printBox("RANKING BY RATINGS", 12, 7); 
		System.out.println("Ranking:  Title                         Ratings");
		if(temp.size() > 4) {
			for (int i=0; i < 5; i++) {
				//System.out.println("(" + (i+1) + ")      " + temp.get(i).getTitle() + "     " + temp.get(i).getRating());
				System.out.printf("(%d)       %-30s %.2f%n", i+1, temp.get(i).getTitle(), temp.get(i).getRating());
			}
		}
		else {
			for (int i=0; i<temp.size(); i++) {
				//System.out.println("(" + (i+1) + ")      " + temp.get(i).getTitle() + "     " + temp.get(i).getRating());
				System.out.printf("(%d)       %-30s %.2f%n", i+1, temp.get(i).getTitle(), temp.get(i).getRating());
			}
			}
	}

	@Override
	public void reloadData() {
		movies.clear();
		movies.addAll(FileIO.readFile(Directory.DIR_MOVIES));
	}

	@Override
	public void saveAndReloadData() {
		FileIO.saveToFile(movies, Directory.DIR_MOVIES);
		reloadData();

	}
}
