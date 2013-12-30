import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;


public class PongDisplayPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	//Player data
	static final int playerPaddleXLocation = PongData.getPlayerPaddleXLocation();
	static int playerPaddleYLocation;
	
	//Ai data
	static final int aiPaddleXLocation = PongData.getAiPaddleXLocation();
	static int aiPaddleYLocation;
	
	//Ball data
	static int ballXLocation;
	static int ballYLocation;
	static int ballWidth;
	static int ballXSpeed;
	static int ballYSpeed;
	
	//Game/frame data
	static int paddleWidth;
	static int paddleHeight;
	static final int frameHeight = PongData.getFrameHeight();
	static final int frameWidth = PongData.getFrameWidth();
	static int paddleChange;

	public PongDisplayPanel(){
		initializePaddles();
		initializeBall();
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
	}

	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		paintUserPaddle(g);
		paintAIPaddle(g);
		paintBall(g);
		paintStats(g);
		paintVictory(g);
	}
	
	private void paintVictory(Graphics g) {
		if (PongData.isGameWon()){
			g.setColor(Color.RED);
			g.drawString("The Game is won! Player: "+PongData.getPlayerScore() + " AI: "+PongData.getAiScore(),
					frameWidth/2 - 125, frameHeight/2-15);
		}
	}

	private void paintStats(Graphics g) {
		g.setColor(Color.GRAY);
		g.drawString(Integer.toString(PongData.getPlayerScore()), 50, 50);
		g.drawString(Integer.toString(PongData.getAiScore()), frameWidth - 50, 50);
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
}
