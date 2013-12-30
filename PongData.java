
public class PongData {
	//Frame/game data
	private final static int defaultWidth = 600;
	private final static int defaultHeight = 600;
	private static int frameWidth = defaultWidth;
	private static int frameHeight = defaultHeight;
	private static final int paddleOffset = 5;
	private static final int paddleHeight = frameHeight/10;
	private static final int paddleWidth = frameWidth/20;
	private static final int paddleYLocationInitial = frameHeight/2 - paddleHeight/2;
	private static final int paddleChange = 5;
	public static boolean gameWon;
	
	public static boolean isGameWon() {
		return gameWon;
	}

	public static void setGameWon(boolean gameWon) {
		PongData.gameWon = gameWon;
	}

	//Ball data
	private static int ballYLocation;
	private static int ballXLocation;
	private static final int ballWidth = 5;
	private static int ballXSpeed;
	private static int ballYSpeed;
	
	//Player data
	private static final int playerPaddleXLocation = paddleOffset;
	private static int playerPaddleYLocation;
	private static int playerScore;
	
	//Ai data
	private static final int aiPaddleXLocation = frameWidth - paddleWidth - paddleOffset - 5;
	private static int aiPaddleYLocation;
	private static int aiScore;
	
	public static int getBallXSpeed() {
		return ballXSpeed;
	}

	public static void setBallXSpeed(int ballXSpeed) {
		PongData.ballXSpeed = ballXSpeed;
	}

	public static int getBallYSpeed() {
		return ballYSpeed;
	}

	public static void setBallYSpeed(int ballYSpeed) {
		PongData.ballYSpeed = ballYSpeed;
	}

	PongData(){
		setFrameWidth(defaultWidth);
		setFrameHeight(defaultHeight);
		setBallXLocation(frameWidth/2);
		setBallYLocation(frameHeight/2);
		setPlayerPaddleYLocation(paddleYLocationInitial);
		setAiPaddleYLocation(paddleYLocationInitial);
		setPlayerScore(0);
		setAiScore(0);
	}

	public static int getPlayerScore() {
		return playerScore;
	}

	public static void setPlayerScore(int playerScore) {
		PongData.playerScore = playerScore;
	}

	public static int getAiScore() {
		return aiScore;
	}

	public static void setAiScore(int aiScore) {
		PongData.aiScore = aiScore;
	}
	
	public static int getPlayerPaddleYLocation() {
		return playerPaddleYLocation;
	}

	public static void setPlayerPaddleYLocation(int playerPaddleYLocation) {
		PongData.playerPaddleYLocation = playerPaddleYLocation;
	}

	public static int getAiPaddleYLocation() {
		return aiPaddleYLocation;
	}

	public static void setAiPaddleYLocation(int aiPaddleYLocation) {
		PongData.aiPaddleYLocation = aiPaddleYLocation;
	}

	public static int getBallYLocation() {
		return ballYLocation;
	}

	public static void setBallYLocation(int ballYLocation) {
		PongData.ballYLocation = ballYLocation;
	}

	public static void setBallXLocation(int ballXLocation) {
		PongData.ballXLocation = ballXLocation;
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

	public static int getPaddleYLocationInitial() {
		return paddleYLocationInitial;
	}

	public static int getPlayerPaddleXLocation() {
		return playerPaddleXLocation;
	}

	public static int getAiPaddleXLocation() {
		return aiPaddleXLocation;
	}

	public static int getPaddleChange() {
		return paddleChange;
	}

	public static int getBallWidth() {
		return ballWidth;
	}

	public static int getBallXLocation() {
		return ballXLocation;
	}

	public static int getDefaultWidth() {
		return defaultWidth;
	}
	
	public static int getDefaultHeight() {
		return defaultHeight;
	}

	public static int getFrameWidth() {
		return frameWidth;
	}
	
	public static void setFrameWidth(int frameWidth) {
		PongData.frameWidth = frameWidth;
	}

	public static int getFrameHeight() {
		return frameHeight;
	}

	public static void setFrameHeight(int frameHeight) {
		PongData.frameHeight = frameHeight;
	}
	
}
