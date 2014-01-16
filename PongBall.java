import java.util.Random;
public class PongBall {
	private int xLocation;
	private int yLocation;
	private double xSpeed;
	private double ySpeed;
	private int frameWidth;
	private int frameHeight;
	private static final int ballWidth = 5;
	private static final double ballSpeedup = 0.05;
	private static final double initialSpeedX = 5;
	private static final double initialSpeedY = 7;
	private static final double initialSpeedVariation = 2;
	//Flat amount of how much the ball speeds up on collisions. Removed due to collision bugs.
	private PongData data;
	public PongBall(PongData incomingData){
		data = incomingData;
		reset();
		xSpeed = 0;
		ySpeed = 0;
		frameWidth = data.getFrameWidth();
		frameHeight = data.getFrameHeight();
	}
	public int getXLocation(){
		return xLocation;
	}
	public int getYLocation(){
		return yLocation;
	}
	public double getXSpeed(){
		return xSpeed;
	}
	public double getYSpeed(){
		return ySpeed;
	}
	public void reset(){
		xLocation = frameWidth/2;
		yLocation = frameHeight/2;
		xSpeed = 0;
		ySpeed = 0;
	}
	public void update(){
		xLocation += xSpeed;
		yLocation += ySpeed;
	}
	public void reverseX() {//Opposite of reverse Y
		xSpeed*=-1;
		if (ySpeed > 0){
			ySpeed += ballSpeedup;
		}
		else {
			ySpeed -= ballSpeedup;
		}
	}
	public void reverseY() {//When collision happens reverse the ball and speed it up
		//Speeds up the opposite speed of collision type to balance out the speeds
		ySpeed *=-1;
		if (xSpeed > 0){
			xSpeed += ballSpeedup;
		}
		else {
			xSpeed -= ballSpeedup;
		}
	}
	public void launch() {//Launches the ball semi-randomly
		Random rn = new Random();
		xSpeed = (int)(rn.nextInt()% initialSpeedVariation + initialSpeedX);
		ySpeed = (int)(rn.nextInt()% initialSpeedVariation + initialSpeedY);
		if (Math.random() > 0.5){
			reverseX();
		}
		if (Math.random() > 0.5){
			reverseY();
		}
	}
	public int getBallWidth() {
		return ballWidth;
	}
}
