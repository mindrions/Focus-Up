import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class MidPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public Timer timer;
	public int cycleAmounts = 3;

	private Duration duration;
	private JLabel display;
	private Duration staticTime, oneSec;
	private int cyclesCompleted = 0;
	private int cycle = 1;
	private int workBlocksCompleted = 0;
	private CustomButton centerImage;

	// public LocalDateTime latest; //not sure what this does

	public MidPanel() {

		Dimension d = new Dimension(600, 450);
		this.setPreferredSize(d);
		this.setBackground(Color.DARK_GRAY);
		this.setBorder(BorderFactory.createEtchedBorder());
		this.setLayout(new BorderLayout());

		cycleAmounts = cycleAmounts * 2;
		oneSec = Duration.ofSeconds(1);
		staticTime = Duration.ofSeconds(1);

		display = new JLabel();
		display.setForeground(Color.WHITE);
		display.setBackground(Color.DARK_GRAY);
		display.setFont(new Font("Arial", Font.PLAIN, 100));
		display.setVerticalAlignment(SwingConstants.CENTER);
		display.setHorizontalAlignment(SwingConstants.CENTER);

		this.add(display, BorderLayout.CENTER);

		centerImage = new CustomButton();

		d = new Dimension(75, 75);
		centerImage.setPreferredSize(d);
		centerImage.setMinimumSize(d);

		centerImage.setPressedBackgroundColor(Color.DARK_GRAY);
		centerImage.setForeground(Color.DARK_GRAY);
		centerImage.setBackground(Color.DARK_GRAY);
		centerImage.setFocusPainted(false);
		centerImage.setBorder(BorderFactory.createEmptyBorder());

		this.add(centerImage, BorderLayout.SOUTH);

		runTimerDown(display);
	}

	private ImageIcon createIcon(String path) {
		URL url = getClass().getResource(path);

		if (url == null) {
			System.err.println("Unable to load image: " + path);
		}

		ImageIcon icon = new ImageIcon(url, "Description here");

		return icon;
	}

	public void runTimerUp(JLabel display) {

		/*
		 * MUST INSERT SNIPPET INTO BottomPanel BEFORE USE AROUND LINE 	125
		 * 
		 * //uncomment if you want timer to go up and pause button to work if
		 * (MainFrame.startTime == null) { MainFrame.startTime = LocalDateTime.now(); }
		 * 
		 * if(MainFrame.pausedTime != null) { if(MainFrame.d1 != null) { MainFrame.d1 =
		 * MainFrame.d1.plus(Duration.between(MainFrame.pausedTime,
		 * LocalDateTime.now())); System.out.println("Paused for " + MainFrame.d1); }
		 * else { MainFrame.d1 = Duration.between(MainFrame.pausedTime,
		 * LocalDateTime.now()); System.out.println("Paused for " + MainFrame.d1); } }
		 * 
		 * //MainFrame.startTime = LocalDateTime.now();
		 * 
		 */

		timer = new Timer(1000, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				LocalDateTime now = LocalDateTime.now();
				duration = Duration.between(MainFrame.startTime, now);
				if (MainFrame.d1 != null) {
					System.out.println("Subtracting " + format(MainFrame.d1) + " from " + format(duration));
					duration = duration.minus(MainFrame.d1);
				}

				display.setText(format(duration));
			}
		});
	}

	public void runTimerDown(JLabel display) {

		timer = new Timer(1000, new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				staticTime = staticTime.minus(oneSec);

				// Font font = new Font("Bahnschrift_Light", Font.PLAIN,
				// display.getFont().getSize());
				// display.setFont(font);

				display.setText(format(staticTime));
				
				if (cycle == cycleAmounts + 1 && staticTime.equals(Duration.ofSeconds(0))) {
					System.out.println("One cycle complete...");
					cyclesCompleted++;
					MainFrame.topPanel.buttonRIGHT.setText(String.valueOf(cyclesCompleted));
					playCycleCompleteSound();
					
					//don't do this VVV because variables shouldn't be public I guess
					//MainFrame.bottomPanel.resumeButton.doClick();
					MainFrame.bottomPanel.pressClick();
					
					MainFrame.bottomPanel.calculateTotalPauseTime(true);
					
					MainFrame.bottomPanel.currentPauseTime = Duration.ofSeconds(0);
					
					System.out.println(MainFrame.bottomPanel.currentPauseTime);
					
					for (int i = 0; i < MainFrame.topPanel.buttonList.size(); i++) {
						MainFrame.topPanel.buttonList.get(i).setBackground(Color.DARK_GRAY);
					}
					cycle = 1;
					workBlocksCompleted = 0;
					staticTime = oneSec;
					timer.stop();
				}

				if (cycle == cycleAmounts && staticTime.equals(Duration.ofSeconds(0))) {
					System.out.println("Long break...");
					centerImage.setIcon(createIcon("/images/longBreak.png"));
					playBreakSound();
					staticTime = Duration.ofSeconds(30);
					MainFrame.topPanel.buttonList.get(MainFrame.topPanel.buttonList.size() - 1)
							.setBackground(Color.WHITE);
					cycle++;
				}

				if ((cycle % 2 == 0 && staticTime.equals(Duration.ofSeconds(0))) && cycle != 0) {
					System.out.println("Short break...");
					centerImage.setIcon(createIcon("/images/shortBreak.png"));

					for (int i = 0; i < MainFrame.topPanel.buttonList.size(); i++) {
						if (MainFrame.topPanel.buttonList.get(i).getActionCommand()
								.equals(Integer.toString(workBlocksCompleted))) {
							MainFrame.topPanel.buttonList.get(i).setBackground(Color.WHITE);

						}
					}

					playBreakSound();
					staticTime = Duration.ofSeconds(5);
					cycle++;
				}

				if (cycle % 2 != 0 && staticTime.equals(Duration.ofSeconds(0))) {
					System.out.println("Work...");
					centerImage.setIcon(createIcon("/images/work.png"));
					System.out.println("Total cycles completed: " + cyclesCompleted);
					playWorkSound();
					staticTime = Duration.ofSeconds(10);
					cycle++;
					workBlocksCompleted++;
				}

			}
		});
	}

	protected String format(Duration duration) {
		long hours = duration.toHours();
		long mins = duration.minusHours(hours).toMinutes();
		long seconds = duration.minusMinutes(mins).toMillis() / 1000;
		return String.format("%02d:%02d", mins, seconds);
	}

	public void skipInterval() {
		staticTime = Duration.ofSeconds(1);
	}

	public void playBreakSound() {
		try {
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(this.getClass().getResource("/sounds/break.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (Exception e) {

		}
	}

	public void playWorkSound() {
		try {
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(this.getClass().getResource("/sounds/work.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (Exception e) {

		}
	}

	public void playCycleCompleteSound() {
		try {
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(this.getClass().getResource("/sounds/cycleComplete.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (Exception e) {

		}
	}

}
