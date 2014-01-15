import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
public class PongFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	public PongFrame() {
		PongData data = new PongData(this);
		createFrame();
		createPongPanel();
		setVisible(true);
		data.runGame();
	}
	private void createPongPanel() {
		//Create the game panel
		PongDisplayPanel pongPanel = new PongDisplayPanel();
		setSize(new Dimension(PongData.getFrameWidth(), PongData.getFrameHeight()));
		pongPanel.setBackground(Color.BLACK);
		add(pongPanel, BorderLayout.CENTER);
		addKeyListener(new KeyAdapter(){//Add a listener for user pressing keys
			public void keyPressed(KeyEvent e){//As soon as user presses up/down keep moving in that direction
				PongData.keyHandling(e);
			}
			public void keyReleased(KeyEvent e){//Once they release the up/down stop moving them in that direction
				//This avoids a delay before the key starts repeating from the OS
				PongData.keyHandling(null);
				PongData.keyReleased(e);
			}
		}); 
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