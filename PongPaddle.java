public class PongPaddle {
	private PongData data;
	private PongBall ball;
	private int paddleXLocation;
	private int paddleYLocation;
	private static int paddleHeight;
	private static int paddleWidth;
	private static int paddleChange = 7;
	private static int yLocationInitial;
	public PongPaddle(PongData incomingData, int XLocation){
		data = incomingData;
		paddleHeight = data.getPaddleHeight();
		paddleWidth = data.getPaddleWidth();
		paddleXLocation = XLocation;
		yLocationInitial =  data.getFrameHeight()/2 - paddleHeight/2;
		paddleYLocation = yLocationInitial;
		ball = data.getBall();
	}
	public int getYLocation(){
		return paddleYLocation;
	}
	public int getXLocation(){
		return paddleXLocation;
	}
	public void moveUp() {
		paddleYLocation -= paddleChange;
	}
	public void moveDown() {
		paddleYLocation += paddleChange;
	}
	public void reset() {
		paddleYLocation = yLocationInitial;
	}
	public void collision() {
		//Collision of top or bottom edge of paddle
		boolean withinY = ballIsWithinY();
		boolean withinX = ballIsWithinX();
		boolean inXNext = ballInXNext();
		boolean inYNext = ballInYNext();
		if (!withinY && !withinX && inXNext && inYNext){
			ball.reverseX();
			ball.reverseY();
		}
		else if (withinY && !withinX && inXNext){
			ball.reverseX();
		}
		else if (withinX && !withinY && inYNext){
			ball.reverseY();
		}
	}
	private boolean ballIsWithinX() {
		if (ball.getXLocation() > paddleXLocation && ball.getXLocation() < paddleXLocation + paddleWidth){
			return true;
		}
		return false;
	}
	private boolean ballIsWithinY() {
		if (ball.getYLocation() > paddleYLocation && ball.getYLocation() < paddleYLocation + paddleHeight){
			return true;
		}
		return false;
	}
	private boolean ballInYNext(){
		if (enteringBottom()||enteringTop()){
			return true;
		}
		return false;
	}
	private boolean ballInXNext(){
		if (enteringLeft() || enteringRight() ){
			return true;
		}
		return false;
	}
	private boolean enteringRight(){
		if (ball.getXLocation() > paddleXLocation + paddleWidth && ball.getXLocation() + ball.getXSpeed() < paddleXLocation + paddleWidth){
			return true;
		}
		return false;
	}
	private boolean enteringLeft(){
		if (ball.getXLocation() < paddleXLocation && ball.getXLocation() + ball.getXSpeed() > paddleXLocation){
			return true;
		}
		return false;
	}
	private boolean enteringBottom(){
		if (ball.getYLocation() > paddleYLocation + paddleHeight && ball.getYLocation() + ball.getYSpeed() < paddleYLocation + paddleHeight){
			return true;
		}
		return false;
	}
	private boolean enteringTop(){
		if (ball.getYLocation() < paddleYLocation && ball.getYLocation() + ball.getYSpeed() > paddleYLocation){
			return true;
		}
		return false;
	}
	public static int getPaddleHeight() {
		return paddleHeight;
	}
	public static int getPaddleWidth() {
		return paddleWidth;
	}
	public static int getPaddleChange() {
		return paddleChange;
	}
	public static int getYLocationInitial() {
		return yLocationInitial;
	}
}
