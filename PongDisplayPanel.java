import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;


public class PongDisplayPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private static final int paddleOffset = 5;
	private static final int frameWidth = PongData.getFrameWidth();
	private static final int frameHeight = PongData.getFrameHeight();
	private static final int paddleHeight = frameHeight/10;
	private static final int paddleWidth = frameWidth/20;
	private static final int paddleYLocationInitial = frameHeight/2 - paddleHeight/2;
	private static final int playerPaddleXLocation = paddleOffset;
	private static int playerPaddleYLocation;
	private static final int aiPaddleXLocation = frameWidth - paddleWidth - paddleOffset - 5;
	private static int aiPaddleYLocation;
	private static final int paddleChange = 5;
	private static int ballYLocation;
	private static int ballXLocation;
	private static final int ballWidth = 5;

	public PongDisplayPanel(){
		@SuppressWarnings("unused")
		PongData data = new PongData();
		
		playerPaddleYLocation = paddleYLocationInitial;
		PongData.setPlayerPaddleLocationY(paddleYLocationInitial);
		aiPaddleYLocation = paddleYLocationInitial;
		PongData.setAiPaddleLocationY(paddleYLocationInitial);
		ballXLocation = PongData.getBallLocationX();
		ballYLocation = PongData.getBallLocationY();
		
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		playerPaddleYLocation = PongData.getPlayerPaddleLocationY();
		
		paintUserPaddle(g);
		paintAIPaddle(g);
		paintBall(g);
	}
	
	public void paintUserPaddle(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(playerPaddleXLocation, playerPaddleYLocation, paddleWidth, paddleHeight);
	}
	
	public void paintAIPaddle(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(aiPaddleXLocation, aiPaddleYLocation, 
				paddleWidth, paddleHeight);
	}
	
	public void paintBall(Graphics g){
		g.setColor(Color.WHITE);
		g.fillRect(ballXLocation, ballYLocation, ballWidth, ballWidth);
		
	}

	public static void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_KP_DOWN || key == KeyEvent.VK_DOWN){
			if (PongData.getPlayerPaddleLocationY() < frameHeight - paddleHeight){
				PongData.setPlayerPaddleLocationY(playerPaddleYLocation + paddleChange);
				playerPaddleYLocation = PongData.getPlayerPaddleLocationY();
			}
		}
		if (key == KeyEvent.VK_KP_UP || key == KeyEvent.VK_UP){
			if (PongData.getPlayerPaddleLocationY() > 0){
				PongData.setPlayerPaddleLocationY(playerPaddleYLocation - paddleChange);
				playerPaddleYLocation = PongData.getPlayerPaddleLocationY();
			}
		}
	}
}
