package testprogetto;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ShowGui {
	
	private final JFrame frame = new JFrame();
	private final JPanel panel = new JPanel();
	
	ShowGui(){
		frame.setTitle("gui");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,400);
		frame.setResizable(false);
		frame.getContentPane().add(panel);
		frame.setVisible(true);
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public JPanel getPanel() {
		return panel;
	}
}
