package teamProject;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Creates a Receipt Object with the ItemList, gratuity, subtotoal, and total price as params,
 * and prints a receipt to a file using FileI/O.
 * 
 * @author Thomas Olvera and Chad Zuniga
 */
public class Receipt {
    static String receipt = "receipts/receiptTest";
    static int count = 0;
    static Calendar cal = new GregorianCalendar();
    
    /**
     * Method that takes in items, and prints a receipt in the correct format. If an item name
     * is larger than 8 characters, it will chop off the remaining of the word to allow for
     * proper formatting. 
     * 
     * @param itemList		List of cart items from an instance of BillingSystem.
     * @param gratuity		Calculated gratuity from subtotal.
     * @param subtotal		Calculated subtotal of all cart items.
     * @param totalPrice	Calculated Total Price.
     * @return				Returns String array: ["Success/Error Message","Receipt#"]
     */
	public static String[] printReceipt(List<Item> itemList, double gratuity, double subtotal, double totalPrice) {
        String[] result = new String[] {"",""};
        count++;
        try(PrintWriter writer = new PrintWriter("receipts/receipt"+count)) {
            writer.printf("Receipt  #00%d  \n",count);
            writer.printf("               %s/%s/%d%n               %d:%d:%d%n%n", 
            		cal.get(Calendar.MONTH) + 1,cal.get(Calendar.DATE),cal.get(Calendar.YEAR),
            		cal.get(Calendar.HOUR),cal.get(Calendar.MINUTE),cal.get(Calendar.SECOND));
            for(Item el:itemList) {
                writer.printf("%s%12.2f%n",el.getName().length() < 7 ? el.getName() 
                		: el.getName().substring(0, 7),el.getPrice());
            }
            writer.println();
            writer.printf("SubTotal:%10.2f%n", subtotal);
            writer.printf("Gratuity:%10.2f%n", gratuity);
            writer.printf("Total:   %10.2f%n", totalPrice);
            result[0] = "Receipt printed successfully...";
            result[1] = ""+count;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            result[0] = "Receipt failed to print...";
        }
        System.out.println(result[0]);

        return result;
    }
}