
public class PongData {
	private static int playerPaddleLocationX;
	private static int playerPaddleLocationY;
	private static int aiPaddleLocationX;
	private static int aiPaddleLocationY;
	private static int frameWidth;
	private static int frameHeight;
	private static int ballLocationX;
	private static int ballLocationY;
	private final static int defaultWidth = 600;
	private final static int defaultHeight = 600;
	
	
	PongData(){
		setFrameWidth(defaultWidth);
		setFrameHeight(defaultHeight);
		setBallLocationX(frameWidth/2);
		setBallLocationY(frameHeight/2);
		
	}

	public static int getBallLocationX() {
		return ballLocationX;
	}

	public static void setBallLocationX(int ballLocationX) {
		PongData.ballLocationX = ballLocationX;
	}

	public static int getBallLocationY() {
		return ballLocationY;
	}

	public static void setBallLocationY(int ballLocationY) {
		PongData.ballLocationY = ballLocationY;
	}

	public int getPlayerPaddleLocationX() {
		return playerPaddleLocationX;
	}

	public void setPlayerPaddleLocationX(int playerPaddleLocationX) {
		PongData.playerPaddleLocationX = playerPaddleLocationX;
	}

	public static int getPlayerPaddleLocationY() {
		return playerPaddleLocationY;
	}

	public static void setPlayerPaddleLocationY(int playerPaddleLocationY) {
		PongData.playerPaddleLocationY = playerPaddleLocationY;
	}

	public int getAiPaddleLocationX() {
		return aiPaddleLocationX;
	}

	public void setAiPaddleLocationX(int aiPaddleLocationX) {
		PongData.aiPaddleLocationX = aiPaddleLocationX;
	}

	public int getAiPaddleLocationY() {
		return aiPaddleLocationY;
	}

	public static void setAiPaddleLocationY(int aiPaddleLocationY) {
		PongData.aiPaddleLocationY = aiPaddleLocationY;
	}

	public static int getFrameWidth() {
		return frameWidth;
	}
	
	public static int getDefaultWidth() {
		return defaultWidth;
	}
	
	public static int getDefaultHeight() {
		return defaultHeight;
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
