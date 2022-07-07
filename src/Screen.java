import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.*;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Screen extends JPanel implements Runnable {
    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;
    public Thread thread = new Thread(this);
    private final Frame frame;

    public static double fps = 60.0;

    public static Image[] cover = new Image[10];                // cover image
    public static Image[] tileset_ground = new Image[100];      // tileset for terrain
    public static Image[] tileset_air = new Image[100];         // tileset for layer above terrain
    public static Image[] tileset_res = new Image[100];         // tileset for GUI and Tower icons
    public static Image[] tileset_enemy = new Image[100];       // tileset for Enemy mobs

    public static int myWidth, myHeight;

    public static int maxLevel = 3;                             // time to wait before next level

    public static boolean isTitle = true;
    public static boolean isFirst = true;
    public static boolean isDebug = false;
    public static boolean won = false;

    public static Point mse = new Point(0, 0);                  // Current mouse position

    public static Space space;
    public static Save save;
    public static Shop shop;

    public static Enemy[] enemy = new Enemy[100];               //Enemy Arrays

    public Screen(Frame frame) {

        this.frame = frame;

        frame.addKeyListener(new KeyHandler(this));

        frame.addMouseListener(new KeyHandler(this));
        frame.addMouseMotionListener(new KeyHandler(this));

        thread.start();

    }

    // Defines variables for the package
    public void define() {
        space = new Space();            // Defines Game GUI
        save = new Save();              // Defines Current/Last Level Played
        shop = new Shop();              // Defines HUD (tower purchases and health)

        for (int i = 0; i < tileset_ground.length; i++) {
            tileset_ground[i] = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("terrain.png"))).getImage();
            tileset_ground[i] = createImage(
                    new FilteredImageSource(tileset_ground[i].getSource(), new CropImageFilter(0, 35 * i, 35, 35)));
        }

        for (int i = 0; i < tileset_air.length; i++) {
            tileset_air[i] = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("t_air.png"))).getImage();
            tileset_air[i] = createImage(
                    new FilteredImageSource(tileset_air[i].getSource(), new CropImageFilter(0, 35 * i, 35, 35)));
        }

        tileset_res[0] = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("item.png"))).getImage();
        tileset_res[1] = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("health.png"))).getImage();
        tileset_res[2] = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("money.png"))).getImage();

        tileset_enemy[0] = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("enemies/slime1.png"))).getImage();
        tileset_enemy[1] = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("enemies/slime2.png"))).getImage();
        tileset_enemy[2] = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("enemies/slimeking.png"))).getImage();

        InputStream in;

        in = getClass().getClassLoader().getResourceAsStream("stage" + Save.level + ".stage");

        assert in != null;
        save.loadSave(new InputStreamReader(in));

        for (int i = 0; i < enemy.length; i++) {
            enemy[i] = new Enemy();
        }
    }

    // Paints everything in the package
    public void paintComponent(Graphics g) {

        // TITLE SCREEN
        if (isTitle) {
//            cover[0] = new ImageIcon("resources/cover.png").getImage();
            cover[0] = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("cover.png"))).getImage();

            g.clearRect(0, 0, this.frame.getWidth(), this.frame.getHeight());

            g.setColor(Color.BLUE);
            g.drawImage(cover[0], 0, 0, null);
        } else if (!isTitle) {

            if (isFirst) {
                myWidth = getWidth();
                myHeight = getHeight();
                define();

                isFirst = false;
            }

            if (!won) {
                g.setColor(new Color(60, 60, 60));
                g.fillRect(0, 0, getWidth(), getHeight());

                space.draw(g);

                // Spawned Enemy
                for (int i = enemy.length - 1; i >= 0; i--) {
                    if (enemy[i].inGame) {
                        enemy[i].draw(g);
                    }
                }

                shop.draw(g); // Draws the HUD

                if (Player.health <= 0) {
                    g.setColor(new Color(240, 20, 20));
                    g.fillRect(0, 0, myWidth, myHeight);
                    g.setColor(new Color(255, 255, 255));
                    g.setFont(new Font("Courier New", Font.BOLD, 14));
                    
                    g.drawString("GAME OVER! The Slimes Have Won!", myWidth / 3, myHeight / 2);
                    
                    for (int i = 0; i < enemy.length; i++) {

                        if (!enemy[i].inGame) {
                            enemy[i] = new Enemy();
                            enemy[i].spawnEnemy(i);

                            break;
                        }
                    }

                    InputStream in = getClass().getClassLoader().getResourceAsStream("stage" + Save.level + ".stage");
                    assert in != null;
                    save.loadSave(new InputStreamReader(in));
                }
            } else {
                g.setColor(new Color(200, 200, 200));
                g.fillRect(0, 0, myWidth, myHeight);
                g.setColor(new Color(0, 0, 0));
                g.setFont(new Font("Courier New", Font.BOLD, 14));

                if (Save.level >= maxLevel) {
                    g.drawString("YOU WON! The Well is Safe!", myWidth / 3, myHeight / 2);
                } else if (won) {

                    g.drawString("You won this round! Please wait to proceed to next level.", myWidth / 5,
                            myHeight / 2);
                    for (int i = 0; i < enemy.length; i++) {

                        if (!enemy[i].inGame) {
                            enemy[i] = new Enemy();
                            enemy[i].spawnEnemy(i);

                            break;
                        }
                    }

                }

            }
        }

        }

    public double spawnTime = 1 * (fps), spawnFrame = spawnTime - fps;

    public void enemySpawner(){

        if(spawnFrame >= spawnTime){

            for(int i = 0; i < enemy.length; i++){

                if(!enemy[i].inGame){
                    enemy[i] = new Enemy();
                    enemy[i].spawnEnemy(i);

                    break;
                }
            }
            spawnFrame = 1;

        }else{

            spawnFrame ++;

        }
    }

    public static double timera = 0;

    public static double winFrame = 1, winTime = 5 * (fps);

    //RUN method
    public void run() {
		long lastTime = System.nanoTime();
		long timer = System.currentTimeMillis();
		final double ns = 1000000000.0 / fps;
		double delta = 0;
		int updates = 0;

        while (true) {

			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;

			// Update 60 times a second
			while (delta >= 1) {
				//update();
				timera++;
				updates++;

				if (!isFirst && Player.health > 0 && !won) {

                    space.physics();
                    
                    enemySpawner();
                    
					for (int i = 0; i < enemy.length; i++) {
						if (enemy[i].inGame) enemy[i].physics(i);
					}
				} else if (Player.health < 1){
					won = false;
					define();
					winFrame = 1;
				} else if (won) {
					if (winFrame >= winTime) {
						if (Save.level >= maxLevel) {
							System.exit(0);
						} else {
							won = false;
							Save.level++;
							define();
						}
						winFrame = 1;
					} else {
						winFrame++;
					}
				}

				delta--;
			}

			repaint();

			// Keep track of and display the game's ups and fps every second
			if (System.currentTimeMillis() - timer >= 1000) {
				timer += 1000;
				frame.setTitle(Frame.title + " | ups: " + updates);
				updates = 0;
			}
		}
	}

}


