import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;


public class PongFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private static boolean gameWon;
	private static final int updateTime = 100;//Use this to lower/raise the rate of refresh on the ball.
	private static int ballXLocation;
	private static int ballYLocation;
	private static int ballXSpeed;
	private static int ballYSpeed;
	private static final int frameHeightOffset = 40;
	private static final int frameWidthOffset = 10;
	
	public PongFrame() {
		@SuppressWarnings("unused")
		PongData data = new PongData();
		createFrame();
		createPongPanel();
		setVisible(true);
		runGame();
	}
	
	private void runGame() {
		while(!gameWon){
			PongData.setBallXLocation(PongData.getBallXLocation() + PongData.getBallXSpeed());
			PongData.setBallYLocation(PongData.getBallYLocation() + PongData.getBallYSpeed());
			
			ballXLocation = PongData.getBallXLocation();
			ballXSpeed = PongData.getBallXSpeed();
			ballYLocation = PongData.getBallYLocation();
			ballYSpeed = PongData.getBallYSpeed();
			if (ballXLocation - ballXSpeed < 0||ballXLocation + ballXSpeed > PongData.getFrameWidth() - frameWidthOffset){
				PongData.setBallXSpeed(PongData.getBallXSpeed()*-1);
			} 
			if (ballYLocation - ballYSpeed < 0 || ballYLocation + ballYSpeed > PongData.getFrameHeight() - frameHeightOffset){
				PongData.setBallYSpeed(PongData.getBallYSpeed()*-1);
			}
			repaint();
			try {
				Thread.sleep(updateTime);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void createPongPanel() {
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
	}

	private void createFrame() {
		//Create the overall game frame
		setSize(PongData.getFrameWidth(), PongData.getFrameHeight());
		setTitle("Pong - Graeme Cliffe");
		gameWon = false;
		requestFocus();
		setResizable(false);
	}

	private void sendKeyEvent(KeyEvent e){
		PongDisplayPanel.keyPressed(e);
		repaint();
	}
	
	
	public static void main(String[] args) {
		new PongFrame();
	}

}
