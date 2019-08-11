package testprogetto;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ShowGui {
	
	private int n;
	private final JFrame frame = new JFrame();
	private final JPanel panel = new JPanel();
	
	ShowGui(){
		frame.setTitle("gui");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400,400);
		frame.setResizable(false);
		frame.getContentPane().add(panel);
		frame.addWindowListener(new WindowAdapter(){
			public void windowOpened(WindowEvent e) {
				System.out.println("works");
				n = JOptionPane.showOptionDialog(frame,"scegli il simbolo", "selezione", 
						JOptionPane.DEFAULT_OPTION,JOptionPane.INFORMATION_MESSAGE,
						null,new String[]{"line","circle","square"},
						null);
				//System.out.println(n);
				/*BufferedImage bi = null;
				try {
		    	    bi = ImageIO.read(new File(getValue() + ".jpg"));
		    	} catch (IOException ex) {
		    		System.out.println("loading image failed...");
		    	}
				JLabel imgLabel = new JLabel(new ImageIcon(bi));
				panel.add(imgLabel);
				panel.updateUI();*/
			}
		});
		frame.setVisible(true);
	}
	
	public String getValue() {
		switch (n) {
			case 0:  return "line";
			case 1:  return "circle";
			case 2:  return "square";
		}
		System.exit(1);;
		return null;
	}
	
	public JFrame getFrame() {
		return frame;
	}
	
	public JPanel getPanel() {
		return panel;
	}
}
