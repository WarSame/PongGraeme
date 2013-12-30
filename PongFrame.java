import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

import javax.swing.JFrame;

public class PongFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	//Frame/game data
	private static final int frameHeightOffset = 40;
	private static final int frameWidthOffset = 10;
	private static boolean gameWon;
	private static boolean pointScored;
	private static final int maxPoints = 3;
	private static final int paddleChange = PongData.getPaddleChange();
	private static final int frameHeight = PongData.getFrameHeight();
	private static final int frameWidth = PongData.getFrameWidth();
	private static final int paddleHeight = PongData.getPaddleHeight();
	private static final int paddleWidth = PongData.getPaddleWidth();
	
	//Ball data
	private static final int updateTime = 16;//Use this to lower/raise the rate of refresh on the ball. ms.
	private static int ballXLocation;
	private static int ballYLocation;
	private static int ballXSpeed;
	private static int ballYSpeed;
	private static int ballXInitialSpeed;
	private static int ballYInitialSpeed;
	
	//Player data
	private static int playerPaddleYLocation;
	private static int playerScore;
	
	//Ai data
	private static int aiPaddleYLocation;
	private static int aiScore;
	
	public PongFrame() {
		new PongData();
		createFrame();
		createPongPanel();
		setVisible(true);
		runGame();
	}
	
	private void runGame() {
		while(!gameWon){
			gatherData();
			edgeCollision();
			playerPaddleCollision();
			aiPaddleCollision();
			updateData();
			repaint();
			
			try {
				Thread.sleep(updateTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void updateData() {
		PongData.setBallXLocation(ballXLocation + ballXSpeed);
		PongData.setBallYLocation(ballYLocation + ballYSpeed);
		PongData.setBallXSpeed(ballXSpeed);
		PongData.setBallYSpeed(ballYSpeed);
		PongData.setPlayerScore(playerScore);
		PongData.setAiScore(aiScore);
		PongData.setPlayerPaddleYLocation(playerPaddleYLocation);
		PongData.setAiPaddleYLocation(aiPaddleYLocation);
	}

	private void gatherData() {
		gatherPaddleData();
		gatherBallData();
	}

	private void gatherBallData() {
		ballXLocation = PongData.getBallXLocation();
		ballXSpeed = PongData.getBallXSpeed();
		ballYLocation = PongData.getBallYLocation();
		ballYSpeed = PongData.getBallYSpeed();
	}

	private void gatherPaddleData() {
		playerPaddleYLocation = PongData.getPlayerPaddleYLocation();
		aiPaddleYLocation = PongData.getAiPaddleYLocation();
	}

	private void playerPaddleCollision() {
		if(ballIsInPlayerPaddleY() && ballIsInPlayerPaddleX()){
			ballXSpeed*= -1;
		}
	}

	private boolean ballIsInPlayerPaddleX() {
		int playerPaddleXLocation = PongData.getPlayerPaddleXLocation();
		if (ballXLocation > playerPaddleXLocation && ballXLocation < playerPaddleXLocation + paddleWidth){
			return true;
		}
		return false;
	}

	private boolean ballIsInPlayerPaddleY() {
		if (ballYLocation > playerPaddleYLocation && ballYLocation < playerPaddleYLocation + paddleHeight){
			return true;
		}
		return false;
	}

	private void aiPaddleCollision() {
		if(ballIsInAiPaddleY() && ballIsInAiPaddleX()){
			ballXSpeed*= -1;
		}
	}

	private boolean ballIsInAiPaddleX() {
		int aiPaddleXLocation = PongData.getAiPaddleXLocation();
		if (ballXLocation > aiPaddleXLocation && ballXLocation < aiPaddleXLocation + paddleWidth){
			return true;
		}
		return false;
	}

	private boolean ballIsInAiPaddleY() {
		if (ballYLocation > aiPaddleYLocation && ballYLocation < aiPaddleYLocation + paddleHeight){
			return true;
		}
		return false;
	}

	private void resetBall() {
		ballXLocation = frameWidth/2;
		ballYLocation = frameHeight/2;
	}
	
	private void resetPaddles() {
		playerPaddleYLocation = PongData.getPaddleYLocationInitial();
		aiPaddleYLocation = PongData.getPaddleYLocationInitial();
	}

	private void edgeCollision() {
		if (ballXLocation < 0){
			aiScore++;
			ballXSpeed = 0;
			ballYSpeed = 0;
			pointScored = true;
			checkVictory();
			resetBall();
			resetPaddles();
		} 
		if (ballXLocation + ballXSpeed > frameWidth - frameWidthOffset){
			playerScore++;
			ballXSpeed = 0;
			ballYSpeed = 0;
			pointScored = true;
			checkVictory();
			resetBall();
			resetPaddles();
		}
		if (ballYLocation < 0||ballYLocation + ballYSpeed > frameHeight - frameHeightOffset){
			ballYSpeed*= -1.1;
		}
	}

	private void checkVictory() {
		if (aiScore>=maxPoints){
			gameWon = true;
			PongData.setGameWon(gameWon);
		}
		if (playerScore>=maxPoints){
			gameWon = true;
			PongData.setGameWon(gameWon);
		}
	}

	private void createPongPanel() {
		//Create the game panel
		PongDisplayPanel pongPanel = new PongDisplayPanel();
		setSize(new Dimension(600, 600));
		pongPanel.setBackground(Color.BLACK);
		add(pongPanel, BorderLayout.CENTER);
		addKeyListener(new KeyAdapter(){//Add a listener for up/down/space keys
			public void keyPressed(KeyEvent e){
				int key = e.getKeyCode();
				playerPaddleYLocation = PongData.getPlayerPaddleYLocation();
				if (key == KeyEvent.VK_KP_DOWN || key == KeyEvent.VK_DOWN){
					if (playerPaddleYLocation < frameHeight - paddleHeight - frameHeightOffset){//Frame offset counteracts the top bar width
						playerPaddleYLocation+=paddleChange;
						PongData.setPlayerPaddleYLocation(playerPaddleYLocation);
					}
				}
				if (key == KeyEvent.VK_KP_UP || key == KeyEvent.VK_UP){
					if (playerPaddleYLocation > 0){
						playerPaddleYLocation -= paddleChange;
						PongData.setPlayerPaddleYLocation(playerPaddleYLocation);
					}
				}
				if (key == KeyEvent.VK_SPACE || key == KeyEvent.VK_KP_UP){
					if (pointScored && !gameWon){
						Random rn = new Random();
						ballXInitialSpeed = (int)(rn.nextInt()%2+4);
						ballYInitialSpeed = (int)(rn.nextInt()%3+6);
						if (Math.random() > 0.5){
							ballXInitialSpeed*= -1;
						}
						if (Math.random() > 0.5){
							ballYInitialSpeed*=-1;
						}
						PongData.setBallXSpeed(ballXInitialSpeed);
						PongData.setBallYSpeed(ballYInitialSpeed);
						pointScored = false;
					}
				}
				repaint();
			}
			private void keyEvent(KeyEvent e) {
				//sendKeyEvent(e);
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
		pointScored = true;
		requestFocus();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
	}
	
	public static void main(String[] args) {
		new PongFrame();
	}

}
