import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
public class PongData {
	//Frame/game data
	private static boolean pointScored;
	private static int frameWidth = 750;
	private static int frameHeight = 600;
	private static JFrame frame;
	private static boolean gameWon;
	private static KeyEvent e;
	private static int maxPoints = 3;
	//Ball data
	private static final int updateTime = 16;//Use this to lower/raise the rate of refresh on the ball and AI. ms. Currently at 60fps
	private PongBall ball = new PongBall(this);
	//Player data
	private static int paddleHeight;
	private static int paddleWidth;
	private final int paddleOffset = 8;
	private final int playerPaddleXLocation;
	private static int playerScore;
	private static boolean keyDown;
	private static boolean keyUp;
	private PongPaddle playerPaddle;
	//Ai data
	private final int aiPaddleXLocation;
	private static int aiScore;
	private PongPaddle aiPaddle;
	
	PongData(PongFrame pongFrame){
		//Set your game data to default
		paddleHeight = frameHeight/7;
		paddleWidth = frameWidth/40;
		playerPaddleXLocation = paddleOffset;
		aiPaddleXLocation  = frameWidth - paddleWidth - paddleOffset;
		playerPaddle = new PongPaddle(this, playerPaddleXLocation);
		aiPaddle = new PongPaddle(this, aiPaddleXLocation);
		playerScore = 0;
		aiScore = 0;
		gameWon = false;
		pointScored = true;
		keyDown = false;
		keyUp = false;
		frame = pongFrame;
		resetPaddles();
		ball.reset();
	}
	public PongBall getBall() {
		return ball;
	}
	public static int getPaddleYLocationInitial() {
		return PongPaddle.getYLocationInitial();
	}
	public boolean isGameWon() {
		return gameWon;
	}
	public int getPlayerScore() {
		return playerScore;
	}
	public int getAiScore() {
		return aiScore;
	}
	public int getPlayerPaddleYLocation() {
		return playerPaddle.getYLocation();
	}
	public int getAiPaddleYLocation() {
		return aiPaddle.getYLocation();
	}
	public int getBallYLocation() {
		return ball.getYLocation();
	}
	public int getPaddleOffset() {
		return paddleOffset;
	}
	public int getPaddleHeight() {
		return paddleHeight;
	}
	public int getPaddleWidth() {
		return paddleWidth;
	}
	public int getPaddleChange(){
		return PongPaddle.getPaddleChange();
	}
	public int getPlayerPaddleXLocation() {
		return playerPaddle.getXLocation();
	}
	public int getAiPaddleXLocation() {
		return aiPaddle.getXLocation();
	}
	public int getBallWidth() {
		return ball.getBallWidth();
	}
	public int getBallXLocation() {
		return ball.getXLocation();
	}
	public int getFrameWidth() {
		return frameWidth;
	}
	public int getFrameHeight() {
		return frameHeight;
	}	
	public void runGame() {//Heartbeat cycle. Updates all the data and does logic. Sleep is to slow down the game.
		while(!gameWon){
			keyHandling(e);
			edgeCollision();
			playerPaddle.collision();
			aiPaddle.collision();
			playerMove();
			aiMove();
			ball.update();
			frame.repaint();
			try {
				Thread.sleep(updateTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	private void playerMove() {//When user hits arrows, move their paddle
		if (keyDown){//Move paddle down when down key is hit and won't go off the board
			if (playerPaddle.getYLocation() < frameHeight - PongPaddle.getPaddleHeight()){
				playerPaddle.moveDown();
			}
		}
		if (keyUp){//Vice versa
			if (playerPaddle.getYLocation() > 0){
				playerPaddle.moveUp();
			}
		}
	}
	private void aiMove() {//AI tracks the ball except when it hits the edges
		if (ball.getYLocation() > aiPaddle.getYLocation() + PongPaddle.getPaddleHeight()/2){//Move down when ball is below you
			if (aiPaddle.getYLocation() + PongPaddle.getPaddleHeight() < frameHeight){
				aiPaddle.moveDown();
			}
		}
		if (ball.getYLocation() < aiPaddle.getYLocation() + PongPaddle.getPaddleHeight() /2){//Opposite
			if (aiPaddle.getYLocation() > 0){
				aiPaddle.moveUp();
			}
		}
	}
	private void resetPaddles() {//On point or new game, put the paddles in their initial spot
		playerPaddle.reset();
		aiPaddle.reset();
	}
	private void edgeCollision() {//Determine if the ball is colliding with the edges
		if (ball.getXLocation() - ball.getXSpeed() < 0){//When it collides with the left side give a point to the AI
			aiScore++;
			pointScored = true;
			checkVictory();
			ball.reset();
			resetPaddles();
		} 
		if (ball.getXLocation() + ball.getXSpeed() > frameWidth){//When it collides with the right side give a point to the player
			playerScore++;
			pointScored = true;
			checkVictory();
			ball.reset();
			resetPaddles();
		}
		if (ball.getYLocation() < 0||ball.getYLocation() + ball.getYSpeed() > frameHeight){//When it collides with the top or bottom make it bounce
			ball.reverseY();
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
	public void keyHandling(KeyEvent e){//Handles user pressing key
		if (e!=null){//e will be null upon key release, when we want to stop cycling the input
		int key = e.getKeyCode();

		if (key == KeyEvent.VK_KP_DOWN || key == KeyEvent.VK_DOWN){
			keyDown(key);
		}
		if (key == KeyEvent.VK_KP_UP || key == KeyEvent.VK_UP){
			keyUp(key);
		}
		if (key == KeyEvent.VK_SPACE){
			keySpace(key);
		}
		if (key == KeyEvent.VK_ESCAPE){
			keyEscape(key);
		}
		frame.repaint();
		}
	}
	private void keyEscape(int key) {
		//On escape close the window
		WindowEvent wev = new WindowEvent(frame, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
	}
	private void keySpace(int key) {
		//On a space key launch the ball semi-randomly
		if (pointScored && !gameWon){
			ball.launch();
			pointScored = false;
		}
	}
	private void keyDown(int key) {
		//On down key start moving the paddle down
		if (playerPaddle.getYLocation() < frameHeight - PongPaddle.getPaddleHeight()){
			playerPaddle.moveDown();
			keyDown = true;
		}
	}
	private void keyUp(int key) {
		//On up key start moving the paddle up
		if (playerPaddle.getYLocation() > 0){
			playerPaddle.moveUp();
			keyUp = true;
		}
	}
	public void keyReleased(KeyEvent e2) {//Handles user releasing key
		int key2 = e2.getKeyCode();
		if (key2 == KeyEvent.VK_KP_DOWN || key2 == KeyEvent.VK_DOWN){//If they release the down key stop moving the paddle down
			keyDown = false;
		}
		if (key2 == KeyEvent.VK_KP_UP || key2 == KeyEvent.VK_UP){//"up"up
			keyUp = false;
		}
	}
}