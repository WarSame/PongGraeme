import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.util.Random;
import javax.swing.JFrame;
public class PongData {
	//Frame/game data
	private static boolean pointScored;
	private static int frameWidth = 750;
	private static int frameHeight = 600;
	private static JFrame frame;
	private static final int paddleOffset = 5;
	private static final int paddleHeight = frameHeight/7;
	private static final int paddleWidth = frameWidth/40;
	private static final int paddleChange = 7;
	private static final int maxPoints = 3;
	private static final int paddleYLocationInitial = frameHeight/2 - paddleHeight/2;
	private static boolean gameWon;
	private static KeyEvent e;
	//Ball data
	private static final int updateTime = 16;//Use this to lower/raise the rate of refresh on the ball and AI. ms. Currently at 60fps
	private static int ballYLocation;
	private static int ballXLocation;
	private static final int ballWidth = 5;
	private static int ballXSpeed;
	private static int ballYSpeed;
	//private static final double ballSpeedIncrease = 1;
	//Flat amount of how much the ball speeds up on collisions. Removed due to collision bugs.
	//Player data
	private static final int playerPaddleXLocation = paddleOffset;
	private static int playerPaddleYLocation;
	private static int playerScore;
	private static boolean keyDown;
	private static boolean keyUp;
	//Ai data
	private static final int aiPaddleXLocation = frameWidth - paddleWidth - paddleOffset - 5;
	private static int aiPaddleYLocation;
	private static int aiScore;
	PongData(PongFrame pongFrame){
		//Set your game data to default
		ballXLocation = frameWidth/2;
		ballYLocation = frameHeight/2;
		playerPaddleYLocation = paddleYLocationInitial;
		aiPaddleYLocation = paddleYLocationInitial;
		playerScore = 0;
		aiScore = 0;
		gameWon = false;
		pointScored = true;
		e = null;
		keyDown = false;
		keyUp = false;
		frame = pongFrame;
	}
	public static boolean isGameWon() {
		return gameWon;
	}
	public static int getPlayerScore() {
		return playerScore;
	}
	public static int getAiScore() {
		return aiScore;
	}
	public static int getPlayerPaddleYLocation() {
		return playerPaddleYLocation;
	}
	public static int getAiPaddleYLocation() {
		return aiPaddleYLocation;
	}
	public static int getBallYLocation() {
		return ballYLocation;
	}
	public static int getPaddleOffset() {
		return paddleOffset;
	}
	public static int getPaddleHeight() {
		return paddleHeight;
	}
	public static int getPaddleWidth() {
		return paddleWidth;
	}
	public static int getPlayerPaddleXLocation() {
		return playerPaddleXLocation;
	}
	public static int getAiPaddleXLocation() {
		return aiPaddleXLocation;
	}
	public static int getBallWidth() {
		return ballWidth;
	}
	public static int getBallXLocation() {
		return ballXLocation;
	}
	public static int getFrameWidth() {
		return frameWidth;
	}
	public static int getFrameHeight() {
		return frameHeight;
	}	
	public void runGame() {//Heartbeat cycle. Updates all the data and does logic. Sleep is to slow down the game.
		while(!gameWon){
			keyHandling(e);
			edgeCollision();
			playerPaddleCollision();
			aiPaddleCollision();
			playerMove();
			aiMove();
			updateBall();
			frame.repaint();
			try {
				Thread.sleep(updateTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private static void playerMove() {//When user hits arrows, move their paddle
		if (keyDown){//Move paddle down when down key is hit and won't go off the board
			if (playerPaddleYLocation < frameHeight - paddleHeight){
				playerPaddleYLocation += paddleChange;
			}
		}
		if (keyUp){//Vice versa
			if (playerPaddleYLocation > 0){
				playerPaddleYLocation -= paddleChange;
			}
		}
	}
	private static void aiMove() {//AI tracks the ball except when it hits the edges
		if (ballYLocation > aiPaddleYLocation + paddleHeight/2){//Move down when ball is below you
			if (aiPaddleYLocation + paddleHeight < frameHeight){
				aiPaddleYLocation += paddleChange;
			}
		}
		if (ballYLocation < aiPaddleYLocation + paddleHeight /2){//Opposite
			if (aiPaddleYLocation > 0){
				aiPaddleYLocation -= paddleChange;
			}
		}
	}
	private static void updateBall() {//Change the data for the ball
		ballXLocation += ballXSpeed;
		ballYLocation += ballYSpeed;
	}
	private static void playerPaddleCollision() {//Determines if ball is colliding with player, if so makes it bounce
		if(ballIsInPlayerPaddleY() && ballIsInPlayerPaddleX()){
			ballXSpeed = (int) (-1*(ballXSpeed));
		}
	}
	private static boolean ballIsInPlayerPaddleX() {//Determines if the ball is within the X range of the paddle
		if (ballXLocation > playerPaddleXLocation && ballXLocation < playerPaddleXLocation + paddleWidth){
			return true;
		}
		return false;
	}
	private static boolean ballIsInPlayerPaddleY() {//" Y range "
		if (ballYLocation > playerPaddleYLocation && ballYLocation < playerPaddleYLocation + paddleHeight){
			return true;
		}
		return false;
	}
	private static void aiPaddleCollision() {//Determines if the ball is colliding with the AI, if so makes it bounce
		if(ballIsInAiPaddleY() && ballIsInAiPaddleX()){
			ballXSpeed = (int) (-1 * (ballXSpeed));
		}
	}
	private static boolean ballIsInAiPaddleX() {//Determines if the ball is within the X range of the paddle
		if (ballXLocation > aiPaddleXLocation && ballXLocation < aiPaddleXLocation + paddleWidth){
			return true;
		}
		return false;
	}
	private static boolean ballIsInAiPaddleY() {// " Y range "
		if (ballYLocation > aiPaddleYLocation && ballYLocation < aiPaddleYLocation + paddleHeight){
			return true;
		}
		return false;
	}
	private static void resetBall() {//On point or new game, put the ball in the middle
		ballXLocation = frameWidth/2;
		ballYLocation = frameHeight/2;
	}
	private static void resetPaddles() {//On point or new game, put the paddles in their initial spot
		playerPaddleYLocation = paddleYLocationInitial;
		aiPaddleYLocation = paddleYLocationInitial;
	}
	private static void edgeCollision() {//Determine if the ball is colliding with the edges
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
			ballYSpeed = (int) (-1*(ballYSpeed));
		}
	}
	private static void checkVictory() {//If one player has earned enough points end the game and display the victory
		if (aiScore>=maxPoints){
			gameWon = true;
		}
		if (playerScore>=maxPoints){
			gameWon = true;
		}
	}
	public static void keyHandling(KeyEvent e){//Handles user pressing key
		if (e!=null){//e will be null upon key release, when we want to stop cycling the input
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_KP_DOWN || key == KeyEvent.VK_DOWN){
			//On down key start moving the paddle down
			if (playerPaddleYLocation < frameHeight - paddleHeight){
				playerPaddleYLocation+=paddleChange;
				keyDown = true;
			}
		}
		if (key == KeyEvent.VK_KP_UP || key == KeyEvent.VK_UP){
			//On up key start moving the paddle up
			if (playerPaddleYLocation > 0){
				playerPaddleYLocation -= paddleChange;
				keyUp = true;
			}
		}
		if (key == KeyEvent.VK_SPACE){
			//On a space key launch the ball semi-randomly
			if (pointScored && !gameWon){
				Random rn = new Random();
				ballXSpeed = (int)(rn.nextInt()%2+6);
				ballYSpeed = (int)(rn.nextInt()%2+8);
				if (Math.random() > 0.5){
					ballXSpeed*= -1;
				}
				if (Math.random() > 0.5){
					ballYSpeed*=-1;
				}
				pointScored = false;
			}
		}
		if (key == KeyEvent.VK_ESCAPE){
			//On escape close the window
			WindowEvent wev = new WindowEvent(frame, WindowEvent.WINDOW_CLOSING);
			Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
		}
		frame.repaint();
		}
	}
	public static void keyReleased(KeyEvent e2) {//Handles user releasing key
		int key2 = e2.getKeyCode();
		if (key2 == KeyEvent.VK_KP_DOWN || key2 == KeyEvent.VK_DOWN){//If they release the down key stop moving the paddle down
			keyDown = false;
		}
		if (key2 == KeyEvent.VK_KP_UP || key2 == KeyEvent.VK_UP){//"up"up
			keyUp = false;
		}
	}
}