package teamProject;

import java.util.List;

/**
 * Creates a BillingSystem object that takes in the name of a text file to be
 * read from. The text file should be a list of menu items, with each item
 * separated with a line break. Each menu item has 2 attributes, a name
 * and a price. They should both be included on the same line and separated by
 * a comma. The attributes are then read in and used to create a new menu object,
 * which is then added to a list of menu objects. 
 * 
 * @author Tomas Olvera and Chad Zuniga
 */
public class BillingSystem {
	private List<Item> cartList;
	private double totalPrice;
	private double subTotal;
	private double gratuity;
	
	/**
     * Initializes an instance of a BillingSystem given a list of type <Item>.
     * 
     * @param cartList
     */
	public BillingSystem(List<Item> cartList) {
		this.cartList = cartList;
	}
	
	/**
     * Sums the price of each item in the cartList.
     * 
     * @return the subtotal
     */
	public double calculateSubtotal() {
		for (Item el : cartList) {
			subTotal += el.getPrice();
		}
		
		totalPrice = subTotal;
		return subTotal;
	}
	
	/**
     * @return the cartList of type <code>Item</code>
     */
	public List<Item> getCartList() {
		return cartList;
	}
	
	/**
	 * Calculates the Total Price by adding the current Items subtotal with gratuity
	 */
	public void calculateTotal() {
		this.totalPrice = this.gratuity + this.subTotal;
	}
	
	/**
     * Calculates the gratuity by multiplying the gratuity percentage with the subtotal. 
     * This method also calls the calculateTotal() method.
     * 
     * @param gratuity the gratuity to set
     */
	public void setGratuity(double gratuity) {
		this.gratuity = subTotal * gratuity;
		calculateTotal();
	}

	/**
	 * @return the gratuity
	 */
	public double getGratuity() {
		return gratuity;
	}
	
	/**
	 * 
	 * @return the subtotal
	 */
	public double getSubtotal() {
		return subTotal;
	}

	/**
	 * @return the total price
	 */
	public double getTotalPrice() {
		return totalPrice;
	}
}
