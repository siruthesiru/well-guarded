import java.awt.*;
import java.awt.Rectangle;

//SHOP is the CLASS for the HUD

public class Shop {
    public static int shopWidth = 5;
    static int buttonSize = 75;
    public static int cellSpace = 2;
    public static int inBetween = 50;
    public static int awayFromSpace = 27;
    public static int iconSize = 30;
    public static int iconSpace = 20;
    public static int itemIn = 4;
    public static int heldID = -1;
    public static int realID = 0;

    public static int[] buttonID = {Value.airTowerRay, Value.airTowerSnipe, Value.airAir, Value.airAir, Value.airCancel};
    public static int[] buttonPrice = {50, 100, 0, 0, 0};
    public static int[] towerDamage = {5, 30};
    public static int[] towerSpeed = {};

    public Rectangle[] button = new Rectangle[shopWidth];
    public Rectangle buttonHealth;
    public Rectangle buttonWealth;

    public boolean holdsItem = false;

    public Shop(){

        define();

    }

    public void purchase(int mouseButton){
        if(mouseButton == 1){
            for(int i = 0; i < button.length; i++){
                if(button[i].contains(Screen.mse)){
                    if(buttonID[i] != Value.airAir) {
                        if(buttonID[i] == Value.airCancel){              //Cancels purchase
                            holdsItem = false;
                        }else if(buttonPrice[i] <= Player.money){
                            heldID = buttonID[i];
                            realID = i;
                            holdsItem = true;
                        }
                    }
                }
            }

            if(holdsItem){
                if(Player.money >= buttonPrice[realID]){
                    for(int y = 0; y < Screen.space.block.length; y++){
                        for(int x = 0; x < Screen.space.block[0].length; x++){
                            if(Screen.space.block[y][x].contains(Screen.mse)){
                                if(Screen.space.block[y][x].groundID != Value.groundRoad && Screen.space.block[y][x].airID == Value.airAir){
                                    Screen.space.block[y][x].airID = heldID;
                                    Player.money -= buttonPrice[realID];
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void define(){

        //Tower Icon
        for (int i = 0; i < button.length; i++){
            button[i] = new Rectangle((Screen.myWidth/2) - ((shopWidth * (buttonSize + cellSpace)) / 2) + ((buttonSize + cellSpace) * i), (Screen.space.block[Screen.space.worldHeight - 1][0].y) + Screen.space.blockSize + awayFromSpace, buttonSize, buttonSize);
        }

        buttonHealth = new Rectangle(Screen.space.block[0][0].x - 1, button[0].y, iconSize, iconSize);
        buttonWealth = new Rectangle(Screen.space.block[0][0].x - 1, button[0].y + button[0].height - iconSize, iconSize, iconSize);
    }

    public void draw (Graphics g){
        for(int i = 0; i< button.length; i++){

            //Tower Icon Highlight
            if(button[i].contains(Screen.mse)){
                if(buttonPrice[i] > Player.money) {
                    g.setColor(new Color(255,20,20));
                    g.fillRect(button[i].x, button[i].y, button[i].width - 1, button[i].height - 1);
                }else{
                    g.setColor(new Color(255,255,255, 100));
                    g.fillRect(button[i].x, button[i].y, button[i].width - 1, button[i].height - 1);
                }
            }

            //Actual Icon Containers
            g.drawImage(Screen.tileset_res[0], button[i].x, button[i].y, button[i].width, button[i].height, null);

            if(buttonID[i] != Value.airAir){
                g.drawImage(Screen.tileset_air[buttonID[i]], button[i].x + itemIn, button[i].y + itemIn, button[i].width - (itemIn * 2), button[i].height  - (itemIn * 2), null);
            }

            if(buttonPrice[i] > 0){
                g.setColor(new Color(255,255,255));
                g.setFont(new Font("Courier New", Font.PLAIN, 15));
                g.drawString("₱" + buttonPrice[i], button[i].x + ((Screen.space.blockSize / 2) + 8), button[i].y + (Screen.space.blockSize * 2) + 20);
            }
        }

        //Health and Money Icons
        g.drawImage(Screen.tileset_res[1], buttonHealth.x + inBetween, buttonHealth.y, buttonHealth.width, buttonHealth.height, null);

        g.drawImage(Screen.tileset_res[2], buttonWealth.x + inBetween, buttonWealth.y, buttonWealth.width, buttonWealth.height, null);

        g.drawImage(Screen.tileset_enemy[Save.level - 1], buttonWealth.x + (inBetween * 13), buttonHealth.y - 5, buttonHealth.width + 5, buttonHealth.height + 5, null);


        //Health and Money Text
        g.setFont(new Font("Courier New", Font.BOLD, 20));

        g.setColor(new Color(255, 255, 255));

        //Note to self: ask sir why toString doesn't work here
        g.drawString("" + Player.health, buttonHealth.x + buttonHealth.width + iconSpace + inBetween, buttonHealth.y + iconSpace);
        g.drawString("" + Player.money, buttonWealth.x + buttonWealth.width + iconSpace + inBetween, buttonWealth.y + iconSpace);
        g.drawString((Save.killsToWin - Player.killed) + " left", buttonHealth.x + buttonHealth.width + iconSpace + (inBetween * 13), buttonHealth.y + iconSpace);

        if(holdsItem){
            g.drawImage(Screen.tileset_air[heldID], Screen.mse.x -  ((Screen.space.blockSize - (itemIn * 2))/2) + itemIn, Screen.mse.y - ((Screen.space.blockSize - (itemIn * 2))/2) + itemIn, Screen.space.blockSize, Screen.space.blockSize, null);
        }

    }

}
