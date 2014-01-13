import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;


public class PongDisplayPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	//Player data
	private static final int playerPaddleXLocation = PongData.getPlayerPaddleXLocation();
	private static int playerPaddleYLocation;
	
	//Ai data
	private static final int aiPaddleXLocation = PongData.getAiPaddleXLocation();
	private static int aiPaddleYLocation;
	
	//Ball data
	private static int ballXLocation;
	private static int ballYLocation;
	private static int ballWidth;
	
	//Game/frame data
	private  int paddleWidth = PongData.getPaddleWidth();
	private static int paddleHeight = PongData.getPaddleHeight();
	private static final int frameHeight = PongData.getFrameHeight();
	private static final int frameWidth = PongData.getFrameWidth();
	private static final int scoreDistFromSide = 80;

	public PongDisplayPanel(){
		initializeFrame();
		initializePaddles();
		initializeBall();
	}

	private void initializeFrame() {//Create the frame
		PongData.setFrameWidth(frameWidth);
		PongData.setFrameHeight(frameHeight);
	}

	private void initializeBall() {//Create the ball
		ballXLocation = PongData.getBallXLocation();
		ballYLocation = PongData.getBallYLocation();
		ballWidth = PongData.getBallWidth();
	}

	private void initializePaddles() {//Create the player and AI paddles
		playerPaddleYLocation = PongData.getPlayerPaddleYLocation();
		aiPaddleYLocation = PongData.getAiPaddleYLocation();
		
		paddleWidth = PongData.getPaddleWidth();
		paddleHeight = PongData.getPaddleHeight();
	}

	public void paintComponent(Graphics g){//Paint everything on screen
		super.paintComponent(g);

		displayInstructions(g);
		gatherData();
		paintUserPaddle(g);
		paintAIPaddle(g);
		paintBall(g);
		paintStats(g);
		paintVictory(g);
	}

	private void displayInstructions(Graphics g) {
		//Show the user what the controls are
		g.setColor(Color.RED);
		g.drawString("Controls:", frameWidth/2 - 20, 20);
		g.drawString("Shoot Ball = Space", frameWidth/2 - 50, 40);
		g.drawString("Move = Arrows", frameWidth/2 - 50, 60);
		g.drawString("End Game = Escape", frameWidth/2 - 50, 80);
	}
	
	private void getBallVariables() {
		//Determine ball location
		ballXLocation = PongData.getBallXLocation();
		ballYLocation = PongData.getBallYLocation();
	}
	
	private void gatherData() {//Determine paddle locations
		aiPaddleYLocation = PongData.getAiPaddleYLocation();
		playerPaddleYLocation = PongData.getPlayerPaddleYLocation();
		getBallVariables();
	}

	private void paintVictory(Graphics g) {//Determine if there is a win, display victory
		if (PongData.isGameWon()){
			g.setColor(Color.RED);
			g.drawString("The Game is won! Player: "+PongData.getPlayerScore() + " AI: "+PongData.getAiScore(),
					frameWidth/2 - 100, frameHeight/2-15);
		}
	}

	private void paintStats(Graphics g) {//Display score for each player
		g.setColor(Color.GRAY);
		g.drawString(Integer.toString(PongData.getPlayerScore()), scoreDistFromSide, scoreDistFromSide);
		g.drawString(Integer.toString(PongData.getAiScore()), frameWidth - scoreDistFromSide, scoreDistFromSide);
	}

	private void paintUserPaddle(Graphics g){//Draw the user paddle
		g.setColor(Color.WHITE);
		playerPaddleYLocation = PongData.getPlayerPaddleYLocation();
		g.fillRect(playerPaddleXLocation, playerPaddleYLocation, 
				paddleWidth, paddleHeight);
	}
	
	private void paintAIPaddle(Graphics g){//Draw the ai paddle
		g.setColor(Color.WHITE);
		g.fillRect(aiPaddleXLocation, aiPaddleYLocation, 
				paddleWidth, paddleHeight);
	}
	
	private void paintBall(Graphics g){//Draw the ball
		g.setColor(Color.WHITE);
		getBallVariables();
		g.fillRect(ballXLocation, ballYLocation, ballWidth, ballWidth);
		
	}

	
}
