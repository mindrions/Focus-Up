import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JPanel;

public class BottomPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	// Contains stop, resume, and skip buttons

	private LocalDateTime pauseStart, pauseEnd;
	private boolean resumeOrPause = true;
	private CustomButton pauseButton, resumeButton, skipButton, stopButton;
	public Duration currentPauseTime = Duration.ofSeconds(0);
	
	public Duration totalPauseTime = Duration.ofSeconds(0);
//	
//	private void calculateTotalPauseTime() {
//		
//	}
	
	public BottomPanel() {

		Dimension d = new Dimension(600, 100);
		this.setPreferredSize(d);
		this.setBackground(Color.DARK_GRAY);
		this.setBorder(BorderFactory.createEtchedBorder());

		this.setLayout(new BorderLayout());

		pauseButton = new CustomButton();
		resumeButton = new CustomButton();
		skipButton = new CustomButton();
		stopButton = new CustomButton();

		d = new Dimension(200, 100);

		// PAUSE BUTTON \\
		pauseButton.setPressedBackgroundColor(Color.GRAY);
		pauseButton.setForeground(Color.DARK_GRAY);
		pauseButton.setBackground(Color.DARK_GRAY);
		pauseButton.setFocusPainted(false);
		pauseButton.setBorder(BorderFactory.createBevelBorder(0));
		pauseButton.setPreferredSize(d);
		pauseButton.setIcon(createIcon("/images/pauseButton.png"));
		
		this.add(pauseButton, BorderLayout.EAST);

		// RESUME BUTTON \\
		resumeButton.setPressedBackgroundColor(Color.GRAY);
		resumeButton.setForeground(Color.DARK_GRAY);
		resumeButton.setBackground(Color.DARK_GRAY);
		resumeButton.setFocusPainted(false);
		resumeButton.setBorder(BorderFactory.createBevelBorder(0));
		resumeButton.setPreferredSize(d);
		resumeButton.setIcon(createIcon("/images/playButton.png"));

		this.add(resumeButton, BorderLayout.CENTER);

		// SKIP BUTTON \\
		skipButton.setPressedBackgroundColor(Color.GRAY);
		skipButton.setForeground(Color.DARK_GRAY);
		skipButton.setBackground(Color.DARK_GRAY);
		skipButton.setFocusPainted(false);
		skipButton.setBorder(BorderFactory.createBevelBorder(0));
		skipButton.setPreferredSize(d);
		skipButton.setIcon(createIcon("/images/skipButton.png"));

		this.add(skipButton, BorderLayout.WEST);

		// STOP BUTTON \\
		stopButton.setPressedBackgroundColor(Color.GRAY);
		stopButton.setForeground(Color.DARK_GRAY);
		stopButton.setBackground(Color.DARK_GRAY);
		stopButton.setFocusPainted(false);
		stopButton.setBorder(BorderFactory.createBevelBorder(0));
		stopButton.setPreferredSize(d);
		stopButton.setIcon(createIcon("/images/stopButton.png"));

		this.add(stopButton, BorderLayout.EAST);

		// ------------------------------------------------------- \\

		// ---------------- // STOP BUTTON LISTENER \\ --------------- \\

		stopButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

			}
		});

		// ---------------- // PAUSE BUTTON LISTENER \\ --------------- \\
//		Might not be necessary anymore
//		pauseButton.addActionListener(new ActionListener() {
//
//			public void actionPerformed(ActionEvent e) {
//
//				if (MainFrame.midPanel.timer.isRunning()) {
//					MainFrame.midPanel.timer.stop();
//					System.out.println("Timer stopped...");
//					pauseStart = LocalDateTime.now();
//					MainFrame.pausedTime = LocalDateTime.now();
//					
//				}
//
//			}
//
//		});

		// ---------------- // RESUME BUTTON LISTENER \\ --------------- \\

		resumeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if (resumeOrPause) {
					// resume button functionality
					//VVV when implementing set amount of pauses only
					//resumeButton.setIcon(createIcon(setPauseButtonIcon(2)));
					
					resumeButton.setIcon(createIcon("/images/pauseButton.png"));
					if (!MainFrame.midPanel.timer.isRunning()) {
						// insert snippet here
						if (pauseStart != null) {
							pauseEnd = LocalDateTime.now();
							calculateTotalPauseTime(false);
							System.out.println("C: " + currentPauseTime.toString());
							System.out.println("T: " + totalPauseTime.toString());
							//playResumeSound();
						}
						MainFrame.midPanel.timer.start();
						System.out.println("Timer starting...");
						resumeOrPause = false;
						
						//MainFrame.midPanel.cycle = 1;
						// now moves to pause button functionality
					}

				} else {
					// pause button functionality
					//playPauseSound();
					resumeButton.setIcon(createIcon("/images/playButton.png"));
					if (MainFrame.midPanel.timer.isRunning()) {
						MainFrame.midPanel.timer.stop();
						System.out.println("Timer stopped...");
						pauseStart = LocalDateTime.now();
						MainFrame.pausedTime = LocalDateTime.now();
					}
					resumeOrPause = true;
					// now moves to resume button functionality
				}
			}
		});

		// ---------------- // SKIP BUTTON LISTENER \\ --------------- \\

		skipButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (MainFrame.midPanel.timer.isRunning()) {
					MainFrame.midPanel.skipInterval();
				}
			}
		});

	}

	private ImageIcon createIcon(String path) {
		URL url = getClass().getResource(path);

		if (url == null) {
			System.err.println("Unable to load image: " + path);
		}

		ImageIcon icon = new ImageIcon(url, "Description here");

		return icon;
	}

	public void calculateTotalPauseTime(boolean t) {
		if(t) { 
			totalPauseTime = totalPauseTime.plus(currentPauseTime);
		} else {
			currentPauseTime = currentPauseTime.plus(Duration.between(pauseStart, pauseEnd));
		}
		
	}

	private String setPauseButtonIcon(int numPausesUsed) {
		String zero = "/images/pauseButtonFull.png";
		String one = "/images/pauseButton13.png";
		String two = "/images/pauseButton23.png";
		String three = "/images/pauseButton33.png";
				
		String toReturn = "";
		
		switch (numPausesUsed) {
		
		case 0:
			toReturn = zero;
			break;
		case 1:
			toReturn = one;
			break;
		case 2: 
			toReturn = two;
			break;
		case 3: 
			toReturn = three;
			break;
		}
		return toReturn;
	}

	public void playPauseSound() {
		try {
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(this.getClass().getResource("/sounds/pause.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (Exception e) {

		}
	}
	
	public void playResumeSound() {
		try {
			AudioInputStream audioInputStream = AudioSystem
					.getAudioInputStream(this.getClass().getResource("/sounds/resume.wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
		} catch (Exception e) {

		}
	}
	
	public void pressClick() {
		resumeButton.doClick();
	}
	
}
