package miniprj;
/** Movie rating values
 * @author Oh Yao Wen
 * @author Fong Hao Wei
 * @author Aleem Siddique
 * @author Alex Neo
 * @author Cheng Chee How
 * @version 1.0
 * @since 1.0
*/
public enum MovieRatingType {
	PG("PG"), PG13("PG13"), NC16("NC16"), M18("M18"), R21("R21");
	
	private final String name;
	private MovieRatingType(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
} 