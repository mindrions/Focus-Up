import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.time.Duration;
import java.time.LocalDateTime;

import javax.swing.JFrame;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public static LocalDateTime startTime;
	public static LocalDateTime pausedTime;
	public static Duration d1;
	public static MidPanel midPanel;
	public static TopPanel topPanel;
	public static BottomPanel bottomPanel;

	public MainFrame() {
		super("FocusUp");

		//this.setUndecorated(true);
		this.setLocation(400, 200);
		this.setBackground(Color.DARK_GRAY);
		// TODO
		// Add menu bar that accesses save and load functions as well as others

		setLayout(new BorderLayout());

		midPanel = new MidPanel();
		bottomPanel = new BottomPanel();
		topPanel = new TopPanel();
		
		add(topPanel, BorderLayout.NORTH);
		add(midPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
		
		// (x, y)
		setSize(600, 600);
		setMinimumSize(new Dimension(600, 600));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
	}

}
