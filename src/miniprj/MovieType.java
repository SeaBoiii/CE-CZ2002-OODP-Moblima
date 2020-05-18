package miniprj;
/** Movie type values
 * @author Oh Yao Wen
 * @author Fong Hao Wei
 * @author Aleem Siddique
 * @author Alex Neo
 * @author Cheng Chee How
 * @version 1.0
 * @since 1.0
*/
public enum MovieType{
	 TWOD("2D"), THREED("3D"), BLOCKBUSTER("Blockbuster");
	
	private final String name;
	private MovieType(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
}
