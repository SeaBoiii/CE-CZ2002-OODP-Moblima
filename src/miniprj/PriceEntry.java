package miniprj;

import java.io.Serializable;
import java.math.BigDecimal;
/** Represents a price entry or payment transaction
 * @author Oh Yao Wen
 * @author Fong Hao Wei
 * @author Aleem Siddique
 * @author Alex Neo
 * @author Cheng Chee How
 * @version 1.0
 * @since 1.0
*/
public class PriceEntry implements Serializable {
	
	
	/**
	 * Name of price entry for identification purposes
	 */
	private String name;
	/**
	 * Price value/Price offset value
	 */
	private BigDecimal price;
	
	/**
	 * Construct a PriceEntry object
	 * @param name Name of price entry
	 * @param price Price value 
	 */
	public PriceEntry(String name, BigDecimal price) {
		this.name=name;
		this.price=price;
	}
	/**
	 * Get name of price entry
	 * @return Returns name of price entry 
	 */
	public String getName() {
		return name;
	}
	/**
	 * Set name of price entry
	 * @param name New name of price entry
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * Get price offset value
	 * @return Returns price offset value
	 */
	public BigDecimal getPrice() {
		return price;
	}
	/**
	 * Set price offset 
	 * @param price New price offset
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
}
