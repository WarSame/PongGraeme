import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;


public class PongFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	//private static boolean gameWon;
	
	public PongFrame() {
		@SuppressWarnings("unused")
		PongData data = new PongData();
		
		//Create the overall game frame
		setSize(PongData.getFrameWidth(), PongData.getFrameHeight());
		setTitle("Pong - Graeme Cliffe");
		//gameWon = false;
		requestFocus();
		setResizable(false);
		
		//Create the game panel
		PongDisplayPanel pongPanel = new PongDisplayPanel();
		pongPanel.setBackground(Color.BLACK);
		add(pongPanel, BorderLayout.CENTER);
		addKeyListener(new KeyAdapter(){//Add a listener for up/down keys
			public void keyPressed(KeyEvent e){
				keyEvent(e);
			}
			private void keyEvent(KeyEvent e) {
				sendKeyEvent(e);
			}
			public void keyReleased(KeyEvent e){
				keyEvent(e);
			}
		});
		setVisible(true);
	}
	
	private void sendKeyEvent(KeyEvent e){
		PongDisplayPanel.keyPressed(e);
		repaint();
	}
	
	
	public static void main(String[] args) {
		new PongFrame();
	}

}
