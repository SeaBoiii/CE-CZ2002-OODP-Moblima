package miniprj;
/** Movie status values
 * @author Oh Yao Wen
 * @author Fong Hao Wei
 * @author Aleem Siddique
 * @author Alex Neo
 * @author Cheng Chee How
 * @version 1.0
 * @since 1.0
*/
public enum MovieStatus {
	COMING_SOON ("Coming Soon"),
	PREVIEW ("Preview"),
	NOW_SHOWING ("Now Showing"),
	END_OF_SHOWING ("End of Showing");
	
	private final String name;
	private MovieStatus(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
}
