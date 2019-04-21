import javax.swing.JFrame;
import java.awt.event.KeyListener;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import javax.swing.SwingUtilities;
import java.awt.event.WindowEvent;

public class RiverRaid extends JFrame implements KeyListener, Runnable {
  private int ticks = 0;
  private boolean left = false;
  private boolean right = false;
  private boolean up = false;
  private boolean down = false;
  private boolean shoot = false;
  private Map<Integer, BufferedImage> maps = new HashMap<>();
  private Map<Integer, Map<Integer, List<Enemy>>> enemies = new HashMap<>();
  private Player player;
  public static void main(String[] args){
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new RiverRaid();
			}
		});
	}
  public RiverRaid() {
		setTitle(Config.gameTitle);
      createMaps();
      createPlayer();
    }
    setupModel();
		setSize(getScreenWidth(), getScreenWidth() * Config.screenHeightFraction);
		setLocationRelativeTo(null); // Center jframe on screen
		setAlwaysOnTop(true); // Make the jframe stay on top of all other windows
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		addKeyListener(this);
		new Thread(this).start();
	}
	public void run() {
		long time_pr_frame = 1000 / Config.fps; // In milliseconds
    long t1;
    long t2;
    long time_used;
    long time_remaining;
    try {
  		while (true) {
  			t1 = System.nanoTime();
        doStuff();
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
      System.out.println("Error in game loop: " + e.toString());
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
  private void createPlayer() {
    BufferedImage player = ImageIO.read(new File("img/player.png"));
    int playerX,
    int playerY, player
    player = new Player()
  }
  private void createMaps() {
    BufferedImage water = ImageIO.read(new File("img/water.png"));
    BufferedImage land = ImageIO.read(new File("img/land.png"));
    int tileWidth = water.getWidth;
    for (int i = 0; i < Config.numberOfMaps; i++) {
      Map<Integer, List<Enemy>> enemies_in_map = new HashMap<>();
      BufferedImage map = ImageIO.read(new File("img/map" + i + ".png"));
      int mapWidth = map.getWidth();
      int mapHeight = map.getHeight();
      BufferedImage tileMap = new BufferedImage(mapWidth*tileWidth, mapHeight*tileWidth, BufferedImage.TYPE_INT_RGB);
      Graphics tileMapGraphics = (Graphics) tileMap.getGraphics();
      for (int y = 0; y < mapHeight; y++) {
        List<Enemy> enemies_at_y = new ArrayList<>();
        for (int x = 0; x < mapWidth; x++) {
          int mapPixel = map.getRGB(x, y);
          if (mapPixel < -10) { // Color is black => add land tile to screen
            tileMapGraphics.drawImage(land, x*tileWidth, y*tileWidth, null);
            enemies_at_y.add(new Enemy(x*tileWidth, y*tileWidth, tileWidth, tileWidth));
          } else { // Color is white => add water tile to screen
            tileMapGraphics.drawImage(water, x*tileWidth, y*tileWidth, null);
          }
        }
        enemies_in_map.put(y, enemies_at_y);
      }
      enemies.put(i, enemies_in_map);
      maps.put(i, tileMap);
    }
  }
  private int getScreenWidth() {
    BufferedImage map1 = maps.get(1);
    return map1.getWidth();
  }
  private void doStuff() {

  }
}
