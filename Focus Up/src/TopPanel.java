import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class TopPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public ArrayList<JButton> buttonList = new ArrayList<JButton>();
	public JButton button;
	public JButton buttonLEFT, buttonRIGHT;
	
	private GridBagConstraints c = new GridBagConstraints();
	private int numButtons = MainFrame.midPanel.cycleAmounts/2;
	
	public TopPanel() {

		Dimension d = new Dimension(600, 50);
		this.setPreferredSize(d);
		this.setBackground(Color.DARK_GRAY);
		this.setBorder(BorderFactory.createEtchedBorder());
		
		this.setLayout(new GridBagLayout());
		
		createButtons();
	}

	private void createButtons() {
	
		int x = 0, y = 0;
		Insets i = new Insets(1, 5, 1, 5);
		Dimension d = new Dimension(50, 25);
		
		buttonLEFT = new JButton();
		buttonLEFT.setEnabled(false);
		buttonLEFT.setBackground(Color.WHITE);
		buttonLEFT.setPreferredSize(d);
		
		c = new GridBagConstraints();
		c.insets = i;
		
		c.gridx = x;
		c.gridy = y;
		c.anchor = GridBagConstraints.WEST;
		c.weightx = 1;
		this.add(buttonLEFT, c);
		x++;
		
		d = new Dimension(30, 10);
		
		while(numButtons != 0) {
			
			JButton button = new JButton();
			button.setEnabled(false);
			button.setBackground(Color.DARK_GRAY);
			button.setPreferredSize(d);
			button.setActionCommand(String.valueOf(x));
			
			c = new GridBagConstraints();
			c.insets = i;
			
			c.gridx = x;
			c.gridy = y;
			c.anchor = GridBagConstraints.CENTER;
			this.add(button, c);
			buttonList.add(button);
			
			x++;
			numButtons--;
		}
		
		d = new Dimension(50, 25);
		
		buttonRIGHT = new JButton();
		buttonRIGHT.setEnabled(false);
		buttonRIGHT.setBackground(Color.WHITE);
		buttonRIGHT.setPreferredSize(d);
		
		c = new GridBagConstraints();
		c.insets = i;
		
		c.gridx = x;
		c.gridy = y;
		c.anchor = GridBagConstraints.EAST;
		c.weightx = 1;
		
		buttonRIGHT.setForeground(Color.BLACK);
		Font font = new Font("Arial", Font.PLAIN, 20);
		buttonRIGHT.setText("0");
		buttonRIGHT.setFont(font);
		this.add(buttonRIGHT, c);
		
	}
	
}
