package teamProject;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

/**
 * Initializes the Menu User Interface based off of an underscore-separated value 
 * text file in the menus folder. The values are separated, parsed to the correct 
 * type, and turned into an <Item> and added to a List. Once the user chooses the
 * items they would like to purchase, then the continue button will send them to
 * the SummaryWindow to continue through the process.
 * 
 * @author Tomas Olvera
 * @author Chad Zuniga
 */
@SuppressWarnings("serial")
public class StartWindow extends JFrame {
	static StartWindow frame;
	static JPanel menu;
	static JPanel summary;
	static JScrollPane sp;
	JPanel cardContainer;
	private List<Item> itemList = new ArrayList<>();
	private List<Item> cartList = new ArrayList<>();
	static BillingSystem b;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new StartWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public StartWindow() {
		parseTextMenu("burgerJoint.txt");
		b = new BillingSystem(cartList);
		System.out.println(b.getCartList());
		
		// JFrame properties
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 800);
		setResizable(false);
		setTitle("Billing System");
		setLocationRelativeTo(null);
		
		cardContainer = new JPanel();
		cardContainer.setLayout(new CardLayout());
		
		menu = createMenu();
	}

	private JPanel createMenu() {
		menu = new JPanel();
		menu.setLayout(new BorderLayout(0, 0));
		
		sp = new JScrollPane(cardContainer, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		sp.getVerticalScrollBar().setUnitIncrement(16);
		
		cardContainer.add(menu);
		
		getContentPane().add(sp);
		
		JPanel leftContent = new JPanel();
		menu.add(leftContent, BorderLayout.WEST);
		leftContent.setLayout(new BoxLayout(leftContent, BoxLayout.Y_AXIS));
		leftContent.add(Box.createVerticalStrut(35));
		
		// title
		JLabel label = new JLabel("Menu");
		label.setFont(new Font("Arial", Font.BOLD, 50));
		leftContent.add(label);
		leftContent.setBorder(new EmptyBorder(0, 60, 0, 0));
		
		JPanel rightContent = new JPanel();
		menu.add(rightContent, BorderLayout.EAST);
		rightContent.setLayout(new BoxLayout(rightContent, BoxLayout.Y_AXIS));
		rightContent.setBorder(new EmptyBorder(0, 0, 0, 50));
		rightContent.add(Box.createVerticalStrut(85));
		
		JButton selectMenuBtn = new JButton("Choose Different Menu");
		selectMenuBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String response = JOptionPane.showInputDialog("Input file name");
				File f = new File("menus/" + response);
				if (response != null && f.exists()) {
					leftContent.removeAll();
					rightContent.removeAll();
					itemList.clear();
					
					leftContent.add(Box.createVerticalStrut(35));
					leftContent.add(label);
					rightContent.add(Box.createVerticalStrut(85));
					parseTextMenu(response);
					for (Item el : itemList) {
						createItemComponent(leftContent, rightContent, el);
					}
					menu.validate();
					menu.repaint();
				}
				
				else if (response == null && !f.exists()) {
					
				}
				
				else {
					JOptionPane.showMessageDialog(null, "File not found (remember to use .txt file extension).");
				}
			}
		});
		selectMenuBtn.setFont(new Font("Arial", Font.BOLD, 10));
		
		JPanel topContent = new JPanel();
		menu.add(topContent, BorderLayout.NORTH);
		topContent.setLayout(new BoxLayout(topContent, BoxLayout.Y_AXIS));
		topContent.add(selectMenuBtn);
		topContent.setBorder(new EmptyBorder(10, 410, 0, 0));
		
		// adding menu items
		for (Item el : itemList) {
			createItemComponent(leftContent, rightContent, el);
		}
		
		// 'continue' button at the bottom
		JButton continueBtn = new JButton("Continue");
		continueBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				summary = new SummaryWindow();
				cardContainer.add(summary);
				setSize(755, 935);
				setLocationRelativeTo(null);
				sp.getVerticalScrollBar().setValue(0);
				menu.setVisible(false);
				summary.setVisible(true);
			}
		});
		menu.add(continueBtn, BorderLayout.SOUTH);
		
		return menu;
	}

	private void createItemComponent(JPanel leftContent, JPanel rightContent, Item item) {
		// menuItem
		JPanel menuItem = new JPanel();
		leftContent.add(Box.createVerticalStrut(52));
		leftContent.add(menuItem);
		menuItem.setLayout(new BoxLayout(menuItem, BoxLayout.Y_AXIS));
		
		JLabel title = new JLabel(item.getName());
		title.setFont(new Font("Arial", Font.BOLD, 14));
		menuItem.add(title);
		menuItem.add(Box.createVerticalStrut(10));
		
		JLabel description = new JLabel(String.format("<html><div style=\\\"width:175px;\\\">%s</div><html>", item.getDescription()));
		description.setFont(new Font("Arial", Font.PLAIN, 12));
		menuItem.add(description);
		menuItem.add(Box.createVerticalStrut(10));
		
		JButton btnOrder = new JButton("Order");
		btnOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cartList.add(item);
				System.out.println(b.getCartList());
			}
		});
		menuItem.add(btnOrder);
		
		// picture
		ImageIcon icon = new ImageIcon(this.getClass().getResource(String.format("/images/%s.png",item.getName())));
		Image img = icon.getImage();
		Image scaledImg = img.getScaledInstance(125, 125, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(scaledImg);
		
		JLabel imgContainer = new JLabel();
		imgContainer.setIcon(scaledIcon);
		rightContent.add(Box.createVerticalStrut(40));
		rightContent.add(imgContainer);
	}
	
	private void parseTextMenu(String readFile) {
		try {
			FileReader reader = new FileReader(String.format("menus/%s", readFile));
			BufferedReader buffer = new BufferedReader(reader);
			String line = buffer.readLine();
			
			while (line != null) {
				try {
					String[] c = line.split("_");
					itemList.add(new Item(c[0], Double.parseDouble(c[1]), c[2]));
					line = buffer.readLine();
				}
				
				catch (IllegalArgumentException e) {
					line = buffer.readLine();
				}
			}
		}
		
		catch (IOException e) {
			JOptionPane.showMessageDialog(null, "An I/O exception has occured.");
			e.printStackTrace();
		}
	}
}