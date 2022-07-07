import java.awt.*;

//BLOCK (supposed to be named TOWER) is the CLASS for defining the package's TOWER AND GRID

public class Block extends Rectangle {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public Rectangle towerSquare;                       //Displays Tower Range
    public int towerSquareSize = 70;                    //Tower width and height
    public int towerRange;                              //Tower range
    public int groundID;                                //ID for low layer tiles (ground, cement, grass)
    public int airID;                                   //ID for upper layer tiles (tower, well, etc.)
    public int loseTime, loseFrame =0;                  //Used in for loops as timers

    public int shotEnemy = -1;                          //Increments when an enemy is being attacked
    public boolean shooting = false;                    //Identifies if the tower is attacking


    //Declares and Initiates new Towers
    public Block(int x, int y,int width, int height, int groundID, int airID){
        setBounds(x, y, width, height);

        this.groundID = groundID;
        this.airID = airID;

        towerSquare = new Rectangle(x - (towerSquareSize / 2), y - (towerSquareSize / 2),
                             width + (towerSquareSize), height + (towerSquareSize));

    }

    public Block() {

    }

    public void draw(Graphics g){
        g.drawImage(Screen.tileset_ground[groundID], x, y , width, height, null);

        if(airID != Value.airAir){
            g.drawImage(Screen.tileset_air[airID], x, y, width, height, null);
        }
    }

    //Defines the tower's physics, mainly placement
    public void physics(){
        switch(airID){

            case 2:     this.loseTime = 20;
                        break;
            case 3:     this.loseTime = 50;
                        break;
            default:    break;
        }

        if(shotEnemy != -1 && towerSquare.intersects(Screen.enemy[shotEnemy])){
            shooting = true;
        }else{
            shooting = false;
        }

        if(!shooting){
            if(airID == Value.airTowerRay || airID == Value.airTowerSnipe){
                for(int i = 0; i < Screen.enemy.length; i++){
                    if(Screen.enemy[i].inGame){
                        if(towerSquare.intersects(Screen.enemy[i])){
                            shooting = true;
                            shotEnemy = i;
                        }
                    }
                }
            }
        }

        if(shooting){
            if(loseFrame >= loseTime){
                Screen.enemy[shotEnemy].loseHealth(airID);

                loseFrame = 0;
            }else{
                loseFrame += 1;
            }

            if(Screen.enemy[shotEnemy].checkDeath()){

                shooting = false;
                shotEnemy = -1;

                if(Player.killed >= Save.killsToWin){
                    Player.hasWon();
                }
            }
        }
    }

    //Shows how each tower attacks
    public void fight(Graphics g){
        if(Screen.isDebug) {
            if (airID == Value.airTowerRay) {
                g.setColor(new Color(0, 0, 0));
                g.drawRect(towerSquare.x, towerSquare.y, towerSquare.width, towerSquare.height);
            } else if (airID == Value.airTowerSnipe) {
                g.setColor(new Color(0, 0, 0));
                g.drawRect(towerSquare.x, towerSquare.y, towerSquare.width, towerSquare.height);
            }
        }

            if(shooting){
                g.setColor(new Color(255, 50, 50));
                g.drawLine(x + (width/2), y + (height/ 2),Screen.enemy[shotEnemy].x +
                                (Screen.enemy[shotEnemy].width/2), Screen.enemy[shotEnemy].y +
                                (Screen.enemy[shotEnemy].height/2));


            }

    }
}
