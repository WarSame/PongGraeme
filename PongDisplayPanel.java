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
	private static final int ballWidth = PongData.getBallWidth();
	//Game/frame data
	private static final int paddleWidth = PongData.getPaddleWidth();
	private static final int paddleHeight = PongData.getPaddleHeight();
	private static final int frameHeight = PongData.getFrameHeight();
	private static final int frameWidth = PongData.getFrameWidth();
	private static final int scoreDistFromSide = 80;
	private static final int controlsOffset = 20;
	private static final int keyOffset = 50;
	private static final int victoryXOffset = 100;
	private static final int victoryYOffset = 15;
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
		g.drawString("Controls:", frameWidth/2 - controlsOffset, controlsOffset);
		g.drawString("Shoot Ball = Space", frameWidth/2 - keyOffset, 2*controlsOffset);
		g.drawString("Move = Arrows", frameWidth/2 - keyOffset, 3*controlsOffset);
		g.drawString("End Game = Escape", frameWidth/2 - keyOffset, 4*controlsOffset);
	}
	private void gatherData() {//Determine paddle locations
		getPaddleVariables();
		getBallVariables();
	}
	private void getPaddleVariables() {
		aiPaddleYLocation = PongData.getAiPaddleYLocation();
		playerPaddleYLocation = PongData.getPlayerPaddleYLocation();
	}
	private void getBallVariables() {
		//Determine ball location
		ballXLocation = PongData.getBallXLocation();
		ballYLocation = PongData.getBallYLocation();
	}
	private void paintVictory(Graphics g) {//Determine if there is a win, display victory
		if (PongData.isGameWon()){
			g.setColor(Color.RED);
			g.drawString("Victory! Player Score: "+PongData.getPlayerScore() + " AI Score: "+PongData.getAiScore(),
					frameWidth/2 - victoryXOffset, frameHeight/2 - victoryYOffset);
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