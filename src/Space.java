import java.awt.*;

//SPACE is the CLASS for the MAIN GAME

public class Space extends Block{
    public int worldWidth = 24;
    public int worldHeight = 14;
    public int blockSize = 35;

    public Block[][] block;

    public Space() {
        super();

        define();
    }

    public void define() {
        block = new Block[worldHeight][worldWidth];
        for (int y = 0; y < block.length; y++) {
            for (int x = 0; x < block[0].length; x++) {
                block[y][x] = new Block(x * blockSize, y * blockSize, blockSize, blockSize, 0, 0);
            }
        }
    }

    public void physics() {
        for(int y = 0; y < block.length; y++){
            for(int x = 0; x < block[0].length; x++){
                block[y][x].physics();
            }
        }


    }

    public void draw(Graphics g) {

        for (int y = 0; y < block.length; y++) {
            for (int x = 0; x < block[0].length; x++) {
                block[y][x].draw(g);
            }
        }
        for (int y = 0; y < block.length; y++) {
            for (int x = 0; x < block[0].length; x++) {
                block[y][x].fight(g);
            }
        }
    }
}
