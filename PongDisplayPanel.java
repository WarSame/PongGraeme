import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;


public class PongDisplayPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	static final int playerPaddleXLocation = PongData.getPlayerPaddleXLocation();
	static int playerPaddleYLocation;
	static final int aiPaddleXLocation = PongData.getAiPaddleXLocation();
	static int aiPaddleYLocation;
	static int ballXLocation;
	static int ballYLocation;
	static int ballWidth;
	static int paddleWidth;
	static int paddleHeight;
	static int frameHeight;
	static int frameWidth;
	static int paddleChange;
	static int ballXSpeed;
	static int ballYSpeed;
	private static final int frameHeightOffset = 40;

	public PongDisplayPanel(){
		initializePanel();
		initializePaddles();
		initializeBall();
	}
	
	private void initializePanel() {//Create the initial display panel
		frameHeight = PongData.getFrameHeight();
		frameWidth = PongData.getFrameWidth();
	}

	private void initializeBall() {//Create the ball
		ballXLocation = PongData.getBallXLocation();
		ballYLocation = PongData.getBallYLocation();
		ballWidth = PongData.getBallWidth();
		ballXSpeed = 0;
		ballYSpeed = 0;
	}

	private void initializePaddles() {//Create the player and AI paddles
		playerPaddleYLocation = PongData.getPlayerPaddleYLocation();
		aiPaddleYLocation = PongData.getAiPaddleYLocation();
		
		paddleWidth = PongData.getPaddleWidth();
		paddleHeight = PongData.getPaddleHeight();
		paddleChange = PongData.getPaddleChange();
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		paintUserPaddle(g);
		paintAIPaddle(g);
		paintBall(g);
		g.drawString(Integer.toString(ballXSpeed), 50, 50);
	}
	
	public void paintUserPaddle(Graphics g){
		g.setColor(Color.WHITE);
		playerPaddleYLocation = PongData.getPlayerPaddleYLocation();
		g.fillRect(playerPaddleXLocation, playerPaddleYLocation, 
				paddleWidth, paddleHeight);
	}
	
	public void paintAIPaddle(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(aiPaddleXLocation, aiPaddleYLocation, 
				paddleWidth, paddleHeight);
	}
	
	public void paintBall(Graphics g){
		g.setColor(Color.WHITE);
		getBallVariables();
		g.fillRect(ballXLocation, ballYLocation, ballWidth, ballWidth);
		
	}

	private void getBallVariables() {
		ballXLocation = PongData.getBallXLocation();
		ballYLocation = PongData.getBallYLocation();
		ballXSpeed = PongData.getBallXSpeed();
		ballYSpeed = PongData.getBallYSpeed();
	}

	public static void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_KP_DOWN || key == KeyEvent.VK_DOWN){
			if (playerPaddleYLocation < frameHeight - paddleHeight - frameHeightOffset){//Frame offset counteracts the top bar width
				playerPaddleYLocation+=paddleChange;
				PongData.setPlayerPaddleYLocation(playerPaddleYLocation);
			}
		}
		if (key == KeyEvent.VK_KP_UP || key == KeyEvent.VK_UP){
			if (playerPaddleYLocation > 0){
				playerPaddleYLocation-=paddleChange;
				PongData.setPlayerPaddleYLocation(playerPaddleYLocation);
			}
		}
		if (key == KeyEvent.VK_SPACE || key == KeyEvent.VK_KP_UP){
			PongData.setBallXSpeed(10);
			PongData.setBallYSpeed(10);
		}
	}
}
