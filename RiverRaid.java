import javax.swing.JFrame;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import javax.swing.SwingUtilities;
import java.awt.event.WindowEvent;

public class RiverRaid extends JFrame implements KeyListener, Runnable {
  private String gameTitle = "River Raid 2";
  private int ScreenWidth = 600;
  private int ScreenHeight = 400;
  private	Graphics canvas;
  private int ticks = 0;
  private boolean left = false;
  private boolean right = false;
  private boolean up = false;
  private boolean down = false;
  private boolean shoot = false;
  public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new RiverRaid();
			}
		});
	}
  public RiverRaid() {
		setTitle(gameTitle);
		setSize(ScreenWidth, ScreenHeight);
		setLocationRelativeTo(null); // Center jframe on screen
		setAlwaysOnTop(true); // Make the jframe stay on top of all other windows
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		addKeyListener(this);
		canvas = this.getGraphics();
		new Thread(this).start();
	}
	public void run() {
		long frames_pr_second = 30;
		long time_pr_frame = 1000 / frames_pr_second; // In milliseconds
    long t1;
    long t2;
    long time_used;
    long time_remaining;
    try {
  		while (true) {
  			t1 = System.nanoTime();
  			updateModel();
  			// drawModel();
  			t2 = System.nanoTime();
        time_used = (t1 - t2) / 1000000; // In milliseconds
  			time_remaining = time_pr_frame - time_used;
  			System.out.println("Sleep time: " + time_remaining + " Start time: " + t1);
  			if (time_remaining > 0) {
          Thread.sleep(time_remaining);
        }
  			ticks += 1;
  		}
    } catch (Exception e) {
      System.out.println("Error: " + e.toString());
    }
	}
  public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_LEFT) {
      left = true;
		}
		if(keyCode == KeyEvent.VK_RIGHT) {
      right = true;
		}
		if(keyCode == KeyEvent.VK_UP) {
      up = true;
		}
		if(keyCode == KeyEvent.VK_DOWN) {
      down = true;
		}
		if(keyCode == KeyEvent.VK_CONTROL) {
      shoot = true;
		}
	}
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_LEFT) {
      left = false;
		}
		if(keyCode == KeyEvent.VK_RIGHT) {
      right = false;
		}
		if(keyCode == KeyEvent.VK_UP) {
      up = false;
		}
		if(keyCode == KeyEvent.VK_DOWN) {
      down = false;
		}
		if(keyCode == KeyEvent.VK_CONTROL) {
      shoot = false;
		}
    if(keyCode == KeyEvent.VK_ESCAPE) {
			dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		}
	}
	public void keyTyped(KeyEvent e) {
		/* Not used */
	}
  updateModel() {

  }
}
