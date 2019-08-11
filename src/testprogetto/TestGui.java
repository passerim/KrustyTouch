package testprogetto;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JOptionPane;

public class TestGui {

	public static void main(String[] args) {
		ShowGui gui = new ShowGui();
		ImgToMatrixLoader imgLoader = new ImgToMatrixLoader(gui.getPanel().getWidth(),gui.getPanel().getHeight());
		MouseListener listener = new MouseListener(){
			
			ComparatorThread th;
			int i;
			
			private boolean pressed = false;
			MouseMotionListener l = new MouseMotionListener() {

				@Override
				public void mouseDragged(MouseEvent e) {
					//System.out.println("moved");
					//System.out.println(e.getX() + " " +e.getY());
					th.add(e.getX(), e.getY());
					//System.out.println(++i);
				}

				@Override
				public void mouseMoved(MouseEvent e) {	
				}
			};

			public void mouseExited(MouseEvent e) {
				if (pressed) {mouseReleased(e);}
			}

			public void mousePressed(MouseEvent e) {
				pressed = true;
				th = new ComparatorThread(gui.getPanel().getHeight(),gui.getPanel().getWidth(), imgLoader);
				th.addImgToList(gui.getValue());
				gui.getPanel().addMouseMotionListener(l);
				i = 0;
			}

			public void mouseReleased(MouseEvent e) {
				if (th.getState()!=Thread.State.RUNNABLE&&th.getState()!=Thread.State.TERMINATED) {
					gui.getPanel().removeMouseMotionListener(l);
					th.start();
					while (th.getState()!=Thread.State.TERMINATED){
						try {
							Thread.sleep(200);
						} catch (InterruptedException ex) {
							ex.printStackTrace();
						}
					}
					JOptionPane.showMessageDialog(gui.getFrame(), Double.toString((th.getValue())));
					//gui.getLabel().setText(Double.toString((th.getValue())));
				}
				pressed = false;
			}

			public void mouseClicked(MouseEvent e) {	
				//System.out.println("ciao");
			}

			public void mouseEntered(MouseEvent e) {
				//System.out.println("hey");
			}
		};
		gui.getPanel().addMouseListener(listener);
		
	}
}
