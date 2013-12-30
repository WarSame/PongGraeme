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
	private static final int paddleChange = 10;
	private static final int frameHeight = PongData.getFrameHeight();
	private static final int frameWidth = PongData.getFrameWidth();
	private static final int paddleHeight = PongData.getPaddleHeight();
	private static final int paddleWidth = PongData.getPaddleWidth();
	private static KeyEvent e;
	
	//Ball data
	private static final int updateTime = 16;//Use this to lower/raise the rate of refresh on the ball. ms.
	private static int ballXLocation;
	private static int ballYLocation;
	private static int ballXSpeed;
	private static int ballYSpeed;
	private static int ballXInitialSpeed;
	private static int ballYInitialSpeed;
	private static final double ballSpeedIncrease = 1.1;//Ratio of how fast the ball speeds up on collisions.
	
	//Player data
	private static final int playerPaddleXLocation = PongData.getPlayerPaddleXLocation();
	private static int playerPaddleYLocation;
	private static int playerScore;
	private static boolean keyDown;
	private static boolean keyUp;
	
	//Ai data
	private static final int aiPaddleXLocation = PongData.getAiPaddleXLocation();
	private static int aiPaddleYLocation;
	private static int aiScore;
	
	public PongFrame() {
		new PongData();
		createFrame();
		createPongPanel();
		setVisible(true);
		runGame();
	}

	protected void clearGame() {
		new PongDisplayPanel();
	}

	private void runGame() {
		while(!gameWon){
			gatherData();
			keyHandling(e);
			edgeCollision();
			playerPaddleCollision();
			aiPaddleCollision();
			playerMove();
			aiMove();
			updateData();
			repaint();
			
			try {
				Thread.sleep(updateTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void playerMove() {
		if (keyDown){
			if (playerPaddleYLocation < frameHeight - frameHeightOffset - paddleHeight){
				playerPaddleYLocation += paddleChange;
				PongData.setPlayerPaddleYLocation(playerPaddleYLocation);
			}
		}
		if (keyUp){
			if (playerPaddleYLocation > 0){
				playerPaddleYLocation -= paddleChange;
				PongData.setPlayerPaddleYLocation(playerPaddleYLocation);
			}
		}
	}

	private void aiMove() {
		if (ballYLocation > aiPaddleYLocation + paddleHeight/2){
			if (aiPaddleYLocation < frameHeight - frameHeightOffset){
				aiPaddleYLocation += paddleChange;
				PongData.setAiPaddleYLocation(aiPaddleYLocation);
			}
		}
		if (ballYLocation < aiPaddleYLocation + paddleHeight /2){
			if (aiPaddleYLocation > 0){
				aiPaddleYLocation -= paddleChange;
				PongData.setAiPaddleYLocation(aiPaddleYLocation);
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
			ballXSpeed*= -1*(ballSpeedIncrease);
		}
	}

	private boolean ballIsInPlayerPaddleX() {
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
			ballXSpeed*= -1 * (ballSpeedIncrease);
		}
	}

	private boolean ballIsInAiPaddleX() {
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
			ballYSpeed*= -1*(ballSpeedIncrease);
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
		setSize(new Dimension(PongData.getFrameWidth(), PongData.getFrameHeight()));
		pongPanel.setBackground(Color.BLACK);
		add(pongPanel, BorderLayout.CENTER);
		addKeyListener(new KeyAdapter(){//Add a listener for up/down/space keys
			public void keyPressed(KeyEvent e){
				keyHandling(e);
			}
			public void keyReleased(KeyEvent e){
				keyHandling(null);
				keyDown = false;
				keyUp = false;
			}
		}); 
	}
	
	private void keyHandling(KeyEvent e){
		if (e!=null){
		int key = e.getKeyCode();
		playerPaddleYLocation = PongData.getPlayerPaddleYLocation();
		if (key == KeyEvent.VK_KP_DOWN || key == KeyEvent.VK_DOWN){
			if (playerPaddleYLocation < frameHeight - paddleHeight - frameHeightOffset){
				playerPaddleYLocation+=paddleChange;
				PongData.setPlayerPaddleYLocation(playerPaddleYLocation);
				keyDown = true;
			}
		}
		if (key == KeyEvent.VK_KP_UP || key == KeyEvent.VK_UP){
			if (playerPaddleYLocation > 0){
				playerPaddleYLocation -= paddleChange;
				PongData.setPlayerPaddleYLocation(playerPaddleYLocation);
				keyUp = true;
			}
		}
		if (key == KeyEvent.VK_SPACE){
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
	}

	private void createFrame() {
		//Create the overall game frame
		setSize(PongData.getFrameWidth(), PongData.getFrameHeight());
		setTitle("Pong - Graeme Cliffe");
		gameWon = false;
		pointScored = true;
		e = null;
		keyDown = false;
		keyUp = false;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
	}
	
	public static void main(String[] args) {
		new PongFrame();
	}

}
