package teamProject;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

/**
 * The main JFrame for the project. There are two separate screens that can be
 * cycled through, the menu screen and the billing screen. The user can navigate
 * between the two with buttons on the screen. The menu screen provides a list of
 * menu items for the user to choose from. When the user is done, they can move on
 * to the next screen to see a billing summary of the items they ordered. They have
 * an option to choose a gratuity to be added to their total. When they are done, they
 * can confirm their order and a receipt will be written to a text file for the user to see.
 * 
 * @author Tomas Olvera
 * @author Chad Zuniga
 */
@SuppressWarnings("serial")
public class SummaryWindow extends JPanel {
	private JLabel totalPriceLbl;
	private JLabel responseLbl;
	private JButton backBtn = new JButton("Back to Menu");
	
	/**
	 * Create the panel.
	 */
	public SummaryWindow() {
		setVisible(false);
		setLayout(null);
		
		createBackBtn();
		
		// adding items to summary
		int offset = 0;
		for (Item el : StartWindow.b.getCartList()) {
			createBillingItem(this, offset, el.getName(), el.getPrice());
			offset += 50;
		}
		
		// create subtotal item
		createBillingItem(this, offset, "Subtotal", StartWindow.b.calculateSubtotal());
		
		JPanel gratuityPanel = new JPanel();
		createGratuitySection(this, gratuityPanel);
		createTotalSection(this);
	}

	private void createBackBtn() {
		backBtn.setBounds(20, 20, 120, 30);
		backBtn.setFont(new Font("Arial", Font.BOLD, 13));
		backBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartWindow.frame.setSize(600, 800);
				StartWindow.frame.setLocationRelativeTo(null);
				StartWindow.sp.getVerticalScrollBar().setValue(0);
				StartWindow.summary.setVisible(false);
				StartWindow.menu.setVisible(true);
			}
		});
		add(backBtn);
	}

	private void createTotalSection(JPanel panelBilling) {
		// ------------ Labels ---------------
		JLabel itemTotalLbl = new JLabel("Total");
		itemTotalLbl.setBounds(47, 765, 100, 50);
		itemTotalLbl.setFont(new Font("Arial", Font.BOLD, 35));
		panelBilling.add(itemTotalLbl);
		
		totalPriceLbl = new JLabel(String.format("$%5.2f", StartWindow.b.getTotalPrice()));
		totalPriceLbl.setBounds(637, 790, 61, 16);
		totalPriceLbl.setFont(new Font("Arial", Font.BOLD, 15));
		panelBilling.add(totalPriceLbl);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(47, 825, 630, 2);
		panelBilling.add(separator);
		
		responseLbl = new JLabel("");
		responseLbl.setBounds(260, 880, 200, 50);
		responseLbl.setForeground(Color.GRAY);
		panelBilling.add(responseLbl);
		
		JButton payButton = new JButton("Pay");
		payButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String[] response = Receipt.printReceipt(StartWindow.b.getCartList(),
						StartWindow.b.getGratuity(),StartWindow.b.getSubtotal(),
						StartWindow.b.getTotalPrice());
				responseLbl.setText(response[0]);
				try {
					File u = new File(".\\receipts\\receipt"+response[1]); 
		            Desktop d = Desktop.getDesktop(); 
		            d.open(u); 
		        } catch (Exception ex) { 
		        	System.err.println(ex);
		        }
			}
		});
		payButton.setBounds(300, 840, 100, 50);
		payButton.setFont(new Font("Arial", Font.BOLD, 13));
		panelBilling.add(payButton);
	}

	private void createGratuitySection(JPanel panelBilling, JPanel gratuityPanel) {
		// ----- Labels ---------
		JLabel lblNewLabel = new JLabel("Gratuity");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 35));
		lblNewLabel.setBounds(47, 590, 322, 49);
		panelBilling.add(lblNewLabel);
		
		// ------ Gratuity buttons --------
		gratuityPanel.setBackground(new Color(240,240,240));
		gratuityPanel.setBounds(100, 675, 500, 49);
		panelBilling.add(gratuityPanel);
		gratuityPanel.setLayout(new GridLayout(1, 3, 0, 0));
		
		JButton tenPercentBtn = new JButton("10%");
		gratuityPanel.add(tenPercentBtn);
		tenPercentBtn.setFont(new Font("Arial", Font.BOLD, 18));
		tenPercentBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartWindow.b.setGratuity(0.10);
				totalPriceLbl.setText(String.format("$%.2f", StartWindow.b.getTotalPrice()));
			}
		});
		
		JLabel lblNewLabel_2 = new JLabel("");
		gratuityPanel.add(lblNewLabel_2);
		
		JButton fifteenPercentBtn = new JButton("15%");
		gratuityPanel.add(fifteenPercentBtn);
		fifteenPercentBtn.setFont(new Font("Arial", Font.BOLD, 18));
		fifteenPercentBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartWindow.b.setGratuity(0.15);
				totalPriceLbl.setText(String.format("$%.2f", StartWindow.b.getTotalPrice()));
			}
		});
		
		JLabel lblNewLabel_1 = new JLabel("");
		gratuityPanel.add(lblNewLabel_1);
		
		JButton twentyPercentBtn = new JButton("20%");
		gratuityPanel.add(twentyPercentBtn);
		twentyPercentBtn.setFont(new Font("Arial", Font.BOLD, 18));
		twentyPercentBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StartWindow.b.setGratuity(0.20);
				totalPriceLbl.setText(String.format("$%.2f", StartWindow.b.getTotalPrice()));
			}
		});
	}

	private void createBillingItem(JPanel panelBilling, int offset, String name, double price) {
		JLabel lblYourOrder = new JLabel("Your Order");
		lblYourOrder.setFont(new Font("Arial", Font.BOLD, 50));
		lblYourOrder.setBounds(47, 75, 427, 96);
		add(lblYourOrder);
		
		JLabel lblItem = new JLabel(name);
		lblItem.setFont(new Font("Arial", Font.BOLD, 15));
		lblItem.setBounds(47, 193 + offset, 200, 14);
		add(lblItem);
		
		JSeparator separator1 = new JSeparator();
		separator1.setBounds(47, 221 + offset, 630, 2);
		add(separator1);
		
		JLabel lblPrice = new JLabel(String.format("$%5.2f", price));
		lblPrice.setFont(new Font("Arial", Font.BOLD, 15));
		lblPrice.setBounds(633, 193 + offset, 70, 14);
		add(lblPrice);
	}
}
