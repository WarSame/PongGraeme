import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;
public class PongDisplayPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	//Player data
	private PongData data;
	//Ball data
	private static int ballXLocation;
	private static int ballYLocation;
	//Game/frame data
	private final int scoreDistFromSide = 80;
	private final int controlsOffset = 20;
	private final int keyOffset = 50;
	private final int victoryXOffset = 100;
	private final int victoryYOffset = 15;
	public PongDisplayPanel(PongData incomingData) {
		data = incomingData;
	}
	public void paintComponent(Graphics g){//Paint everything on screen
		super.paintComponent(g);
		displayInstructions(g);
		getBallLocation();
		paintUserPaddle(g);
		paintAIPaddle(g);
		paintBall(g);
		paintStats(g);
		paintVictory(g);
	}
	private void displayInstructions(Graphics g) {
		//Show the user what the controls are
		g.setColor(Color.RED);
		g.drawString("Controls:", data.getFrameWidth()/2 - controlsOffset, controlsOffset);
		g.drawString("Shoot Ball = Space", data.getFrameWidth()/2 - keyOffset, 2*controlsOffset);
		g.drawString("Move = Arrows", data.getFrameWidth()/2 - keyOffset, 3*controlsOffset);
		g.drawString("End Game = Escape", data.getFrameWidth()/2 - keyOffset, 4*controlsOffset);
	}
	private void getBallLocation() {
		//Determine ball location
		ballXLocation = data.getBallXLocation();
		ballYLocation = data.getBallYLocation();
	}
	private void paintVictory(Graphics g) {//Determine if there is a win, display victory
		if (data.isGameWon()){
			g.setColor(Color.RED);
			g.drawString("Victory! Player Score: "+ data.getPlayerScore() + " AI Score: "+ data.getAiScore(),
					data.getFrameWidth()/2 - victoryXOffset, data.getFrameHeight()/2 - victoryYOffset);
		}
	}
	private void paintStats(Graphics g) {//Display score for each player
		g.setColor(Color.GRAY);
		g.drawString(Integer.toString(data.getPlayerScore()), scoreDistFromSide, scoreDistFromSide);
		g.drawString(Integer.toString(data.getAiScore()), data.getFrameWidth() - scoreDistFromSide, scoreDistFromSide);
	}
	private void paintUserPaddle(Graphics g){//Draw the user paddle
		g.setColor(Color.WHITE);
		g.fillRect(data.getPlayerPaddleXLocation(), data.getPlayerPaddleYLocation(), 
				data.getPaddleWidth(), data.getPaddleHeight());
	}
	private void paintAIPaddle(Graphics g){//Draw the ai paddle
		g.setColor(Color.WHITE);
		g.fillRect(data.getAiPaddleXLocation(), data.getAiPaddleYLocation(), 
				data.getPaddleWidth(), data.getPaddleHeight());
	}
	private void paintBall(Graphics g){//Draw the ball
		g.setColor(Color.WHITE);
		g.fillRect(ballXLocation, ballYLocation, data.getBallWidth(), data.getBallWidth());
	}
}