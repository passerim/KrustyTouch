package test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import model.Comparator;

/**
* Test class for testing and debugging {@link ComparatorThread}, some magic numbers are present due to the purpose of the class.
* This was made to test the recognition system as a standalone, this doesnt'work with the full version of the game.
*/
public class ShowGui {

    private final JFrame frame = new JFrame();
    private final JPanel panel = new JPanel();

    ShowGui() {
        final JPanel panel = new JPanel() {
            private static final long serialVersionUID = -3881289804519169474L;
            private final List<Point> points = new ArrayList<Point>();
            {
                addMouseListener(new MouseListener() {

                    private Comparator cmp;
                    private Thread th;
                    private boolean pressed;
                    private final MouseMotionListener l = new MouseMotionListener() {

                        @Override
                        public void mouseDragged(final MouseEvent e) {
                            cmp.add(e.getX(), e.getY());
                            points.add(e.getPoint());
                            repaint();
                        }

                        @Override
                        public void mouseMoved(final MouseEvent e) {  
                        }
                    };

                    public void mouseExited(final MouseEvent e) {
                        if (pressed) {
                            mouseReleased(e);
                        }
                    }

                    public void mousePressed(final MouseEvent e) {
                        pressed = true;
                        th = new Thread(cmp);
                        addMouseMotionListener(l);
                        points.add(e.getPoint());
                    }

                    public void mouseReleased(final MouseEvent e) {
                        if (th.getState() != Thread.State.RUNNABLE && th.getState() != Thread.State.TERMINATED) {
                            removeMouseMotionListener(l);
                            th.start();
                            while (th.getState() != Thread.State.TERMINATED) {
                                try {
                                    Thread.sleep(200);
                                } catch (InterruptedException ex) {
                                    ex.printStackTrace();
                                }
                            }
                            JOptionPane.showMessageDialog(getFrame(), ((cmp.getValue())));
                        }
                        pressed = false;
                        points.clear();
                    }

                    public void mouseClicked(final MouseEvent e) {    
                    }

                    public void mouseEntered(final MouseEvent e) {
                    }
                });
            }
            public void paint(final Graphics g) {
                super.paint(g);
                if (points.size() >= 2) {
                    g.setColor(Color.RED);
                    for (int i = 0; i < points.size() - 1; i++) {
                        g.drawLine(points.get(i).x, points.get(i).y, points.get(i + 1).x, points.get(i + 1).y);
                    }
                }
            }
        };

        this.frame.setTitle("gui");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(400, 400);
        this.frame.setResizable(false);
        this.frame.getContentPane().add(panel);
        this.frame.setLocationByPlatform(true);
        this.frame.setVisible(true);
    }

    /**
     * 
     * @return
     *          test class gui frame
     */
    public JFrame getFrame() {
        return this.frame;
    }

    /**
     * 
     * @return
     *          test class gui panel
     */
    public JPanel getPanel() {
        return this.panel;
    }
    /*
    public static void main(String[] args) {
        new ShowGui();
    }
     */       
}
