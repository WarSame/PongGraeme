import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.Random;
import javax.swing.JFrame;

public class PongFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	//Frame/game data
	private static boolean gameWon;
	private static boolean pointScored;
	private static final int maxPoints = PongData.getMaxPoints();
	private static final int paddleChange = PongData.getPaddleChange();
	private static int frameHeight;
	private static int frameWidth;
	private static final int paddleHeight = PongData.getPaddleHeight();
	private static final int paddleWidth = PongData.getPaddleWidth();
	private static KeyEvent e;
	
	//Ball data
	private static final int updateTime = 16;//Use this to lower/raise the rate of refresh on the ball and AI. ms. Currently at 60fps
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
		initializeData();
		createFrame();
		createPongPanel();
		setVisible(true);
		runGame();
	}

	private void runGame() {//Heartbeat cycle. Updates all the data and does logic. Sleep is to slow down the game.
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

	private void playerMove() {//When user hits arrows, move their paddle
		if (keyDown){//Move paddle down when down key is hit and won't go off the board
			if (playerPaddleYLocation < frameHeight - paddleHeight){
				playerPaddleYLocation += paddleChange;
				PongData.setPlayerPaddleYLocation(playerPaddleYLocation);
			}
		}
		if (keyUp){//Vice versa
			if (playerPaddleYLocation > 0){
				playerPaddleYLocation -= paddleChange;
				PongData.setPlayerPaddleYLocation(playerPaddleYLocation);
			}
		}
	}

	private void aiMove() {//AI tracks the ball except when it hits the edges
		if (ballYLocation > aiPaddleYLocation + paddleHeight/2){//Move down when ball is below you
			if (aiPaddleYLocation + paddleHeight < frameHeight){
				aiPaddleYLocation += paddleChange;
				PongData.setAiPaddleYLocation(aiPaddleYLocation);
			}
		}
		if (ballYLocation < aiPaddleYLocation + paddleHeight /2){//Opposite
			if (aiPaddleYLocation > 0){
				aiPaddleYLocation -= paddleChange;
				PongData.setAiPaddleYLocation(aiPaddleYLocation);
			}
		}
	}

	private void updateData() {//Change the data stored to current data
		updateBall();
		updateScore();
		updatePaddles();
	}

	private void updatePaddles() {//Change the data for paddles
		PongData.setPlayerPaddleYLocation(playerPaddleYLocation);
		PongData.setAiPaddleYLocation(aiPaddleYLocation);
	}

	private void updateScore() {//Change the data for score
		PongData.setPlayerScore(playerScore);
		PongData.setAiScore(aiScore);
	}

	private void updateBall() {//Change the data for the ball
		PongData.setBallXLocation(ballXLocation + ballXSpeed);
		PongData.setBallYLocation(ballYLocation + ballYSpeed);
		PongData.setBallXSpeed(ballXSpeed);
		PongData.setBallYSpeed(ballYSpeed);
	}

	private void gatherData() {//Get the data currently stored
		gatherPaddleData();
		gatherBallData();
	}

	private void gatherBallData() {//Get ball data currently stored
		ballXLocation = PongData.getBallXLocation();
		ballXSpeed = PongData.getBallXSpeed();
		ballYLocation = PongData.getBallYLocation();
		ballYSpeed = PongData.getBallYSpeed();
	}

	private void gatherPaddleData() {//Get paddle data currently stored
		playerPaddleYLocation = PongData.getPlayerPaddleYLocation();
		aiPaddleYLocation = PongData.getAiPaddleYLocation();
	}

	private void playerPaddleCollision() {//Determines if ball is colliding with player, if so makes it bounce
		if(ballIsInPlayerPaddleY() && ballIsInPlayerPaddleX()){
			ballXSpeed*= -1*(ballSpeedIncrease);
		}
	}

	private boolean ballIsInPlayerPaddleX() {//Determines if the ball is within the X range of the paddle
		if (ballXLocation > playerPaddleXLocation && ballXLocation < playerPaddleXLocation + paddleWidth){
			return true;
		}
		return false;
	}

	private boolean ballIsInPlayerPaddleY() {//" Y range "
		if (ballYLocation > playerPaddleYLocation && ballYLocation < playerPaddleYLocation + paddleHeight){
			return true;
		}
		return false;
	}

	private void aiPaddleCollision() {//Determines if the ball is colliding with the AI, if so makes it bounce
		if(ballIsInAiPaddleY() && ballIsInAiPaddleX()){
			ballXSpeed*= -1 * (ballSpeedIncrease);
		}
	}

	private boolean ballIsInAiPaddleX() {//Determines if the ball is within the X range of the paddle
		if (ballXLocation > aiPaddleXLocation && ballXLocation < aiPaddleXLocation + paddleWidth){
			return true;
		}
		return false;
	}

	private boolean ballIsInAiPaddleY() {// " Y range "
		if (ballYLocation > aiPaddleYLocation && ballYLocation < aiPaddleYLocation + paddleHeight){
			return true;
		}
		return false;
	}

	private void resetBall() {//On point or new game, put the ball in the middle
		ballXLocation = frameWidth/2;
		ballYLocation = frameHeight/2;
	}
	
	private void resetPaddles() {//On point or new game, put the paddles in their initial spot
		playerPaddleYLocation = PongData.getPaddleYLocationInitial();
		aiPaddleYLocation = PongData.getPaddleYLocationInitial();
	}

	private void edgeCollision() {//Determine if the ball is colliding with the edges
		if (ballXLocation - ballXSpeed < 0){//When it collides with the left side give a point to the AI
			aiScore++;
			ballXSpeed = 0;
			ballYSpeed = 0;
			pointScored = true;
			checkVictory();
			resetBall();
			resetPaddles();
		} 
		if (ballXLocation + ballXSpeed > frameWidth){//When it collides with the right side give a point to the player
			playerScore++;
			ballXSpeed = 0;
			ballYSpeed = 0;
			pointScored = true;
			checkVictory();
			resetBall();
			resetPaddles();
		}
		if (ballYLocation < 0||ballYLocation + ballYSpeed > frameHeight){//When it collides with the top or bottom make it bounce
			ballYSpeed*= -1*(ballSpeedIncrease);
		}
	}

	private void checkVictory() {//If one player has earned enough points end the game and display the victory
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
		addKeyListener(new KeyAdapter(){//Add a listener for user pressing keys
			public void keyPressed(KeyEvent e){//As soon as user presses up/down keep moving in that direction
				keyHandling(e);
			}
			public void keyReleased(KeyEvent e){//Once they release the up/down stop moving them in that direction
				//This avoids a delay before the key starts repeating from the OS
				keyHandling(null);
				keyDown = false;
				keyUp = false;
			}
		}); 
	}
	
	private void keyHandling(KeyEvent e){//Handles user pressing key
		if (e!=null){//e will be null upon key release, when we want to stop cycling the input
		int key = e.getKeyCode();
		playerPaddleYLocation = PongData.getPlayerPaddleYLocation();
		
		if (key == KeyEvent.VK_KP_DOWN || key == KeyEvent.VK_DOWN){
			//On down key start moving the paddle down
			if (playerPaddleYLocation < frameHeight - paddleHeight){
				playerPaddleYLocation+=paddleChange;
				PongData.setPlayerPaddleYLocation(playerPaddleYLocation);
				keyDown = true;
			}
		}
		if (key == KeyEvent.VK_KP_UP || key == KeyEvent.VK_UP){
			//On up key start moving the paddle up
			if (playerPaddleYLocation > 0){
				playerPaddleYLocation -= paddleChange;
				PongData.setPlayerPaddleYLocation(playerPaddleYLocation);
				keyUp = true;
			}
		}
		if (key == KeyEvent.VK_SPACE){
			//On a space key launch the ball semi-randomly
			if (pointScored && !gameWon){
				Random rn = new Random();
				ballXInitialSpeed = (int)(rn.nextInt()%2+6);
				ballYInitialSpeed = (int)(rn.nextInt()%2+8);
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
		if (key == KeyEvent.VK_ESCAPE){
			//On escape close the window
			WindowEvent wev = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
			Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
		}
		repaint();
		}
	}
	
	private void initializeData() {
		//Set your game data to default
		gameWon = false;
		pointScored = true;
		e = null;
		keyDown = false;
		keyUp = false;
		frameHeight = PongData.getFrameHeight();
		frameWidth = PongData.getFrameWidth();
	}
	
	private void createFrame() {
		//Create the overall game frame
		setSize(PongData.getFrameWidth(), PongData.getFrameHeight());
		setTitle("Pong - Graeme Cliffe");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setUndecorated(true);
	}
	
	public static void main(String[] args) {
		//Initialize a new pong frame
		new PongFrame();
	}

}
