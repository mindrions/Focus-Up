import javax.swing.SwingUtilities;

public class App {

	public static void main(String args[]) {

//		try {
//			for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
//				if ("Nimbus".equals(info.getName())) {
//					UIManager.setLookAndFeel(info.getClassName());
//					break;
//				}
//			}
//		} catch (Exception e) {
//
//		}
// testing
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new MainFrame();
			}
		});
	}

}
