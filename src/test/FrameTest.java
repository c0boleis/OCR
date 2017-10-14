package test;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

public class FrameTest extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel panelCenter;
	
	private List<JPanel> panels = new ArrayList<JPanel>();
	
	private Point p;
	
	private Point posPanel1;

	public static void main(String[] args) {
		FrameTest frameTest = new FrameTest();
		frameTest.setVisible(true);
	}
	
	public FrameTest() {
		super("Test Panel");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(new Dimension(700, 600));
		this.add(getPanelCenter(), BorderLayout.CENTER);
	}

	/**
	 * @return the panelCenter
	 */
	public JPanel getPanelCenter() {
		if(panelCenter == null) {
			panelCenter = new JPanel(null);
			panelCenter.setBackground(Color.black);

			JPanel panel1 = new JPanel();
			panel1.addComponentListener(new ComponentListener() {
				
				@Override
				public void componentShown(ComponentEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void componentResized(ComponentEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void componentMoved(ComponentEvent e) {
					posPanel1 = e.getComponent().getLocation();
				}
				
				@Override
				public void componentHidden(ComponentEvent e) {
					// TODO Auto-generated method stub
					
				}
			});
			panel1.addMouseMotionListener(new MouseMotionListener() {
				
				@Override
				public void mouseMoved(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void mouseDragged(MouseEvent e) {
					int dx = e.getX()-p.x;
					int dy = e.getY()-p.y;
					Point pos = new Point(dx+posPanel1.x, dy+posPanel1.y);
					panel1.setLocation(pos);
					p = e.getPoint();
				}
			});
			panel1.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				p= null;
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				p = e.getPoint();
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				panel1.setBorder(null);
				System.out.println("mouseExited");
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				panel1.setBorder(new BevelBorder(BevelBorder.RAISED));
				System.out.println("mouseEntered");
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
//			panel1.addFocusListener(new FocusListener() {
//				
//				@Override
//				public void focusLost(FocusEvent e) {
//					panel1.setBorder(null);
//					System.out.println("focusLost");
//				}
//				
//				@Override
//				public void focusGained(FocusEvent e) {
//					panel1.setBorder(new BevelBorder(BevelBorder.RAISED));
//					System.out.println("focusGained");
//				}
//			});
			panel1.setBackground(Color.blue);
			panel1.setLocation(50, 100);
			panel1.setSize(50, 50);
			panelCenter.add(panel1);
			panels.add(panel1);
			panelCenter.setFocusable(true);
		}
		return panelCenter;
	}

}
