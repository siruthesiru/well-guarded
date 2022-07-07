import java.awt.*;

public class Enemy extends Block{
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public int health, maxHealth;

    public int healthSpace = 3, healthHeight = 6;

    public int damage;
    public int turn;
    public int xC, yC;
    public int enemySize = 35;
    public int enemyWalk = 0;
    public int up = 0, down = 1, right = 2, left = 3;
    public int direction = right;
    public static int enemyID = Value.enemySlime1;
    public boolean inGame = false;
    public boolean died = false;
    public int xd, yd;
    public static int secondID;

    public static int[] deathReward = {5, 10, 20};
    public static int[] enemyHealth = {50, 50, 120};
    public static int walkSpeed = 1;

    public Enemy() {
    }


    public void spawnEnemy(int turn) {

        for (int y = 0; y < Screen.space.block.length; y++) {
            if (Screen.space.block[y][0].groundID == Value.groundRoad) {

                setBounds(Screen.space.block[y][0].x, Screen.space.block[y][0].y, enemySize, enemySize);

                xd = x;
                yd = this.y;

                xC = 0;
                yC = y;

                enemyWalk = 0;

                direction = right;

                break;

            }
        }

        secondID = enemyID;

        if(secondID >= 3){
            secondID = 2;
        }

        health = enemyHealth[secondID];

        inGame = true;
    }

    public void deleteEnemy(){
        inGame = false;
    }

    //METHOD for updating enemy HP
    public void loseHealth(int id){

        switch (id){
            case 2: damage = 5;
                    break;

            case 3: damage = 20;
                    break;
            default: damage = 0;
                    break;
        }

        health -= damage;

        if(checkDeath()){
            Player.killed++;
        };
    }

    public void damageBase(){
        Player.health -= 10;
    }


    //Finding Path Basic AI
    public void physics(int i) {

        // if (walkFrame >= walkSpeed) {
            if (direction == up) {
                yd -= walkSpeed;
                y = yd;
            } else if (direction == down) {
                yd += walkSpeed;
                y = yd;
            } else if (direction == right) {
                xd += walkSpeed;
                x = xd;
            } else if (direction == left) {
                xd -= walkSpeed;
                x = xd;
            }

            enemyWalk += walkSpeed;

            if (enemyWalk >= Screen.space.blockSize) {
                if (direction == right) {
                    xC++;
                } else if (direction == up) {
                    yC--;
                } else if (direction == down) {
                    yC++;
                }else if (direction == left) {
                    xC--;
                }



                if(i % 2 == 0){

                    intersection();

                }else{
                    try{
                        if (direction != down && direction != up) {
                            if (Screen.space.block[yC + 1][xC].groundID == Value.groundRoad) {
                                direction = down;
                            } else if (Screen.space.block[yC - 1][xC].groundID == Value.groundRoad) {
                                direction = up;
                            }
                        } else if (direction != right && direction != left) {
                            if (Screen.space.block[yC][xC + 1].groundID == Value.groundRoad && direction != left) {
                                direction = right;
                            } else if (Screen.space.block[yC][xC - 1].groundID == Value.groundRoad && direction != right) {
                                direction = left;
                            }
                        }
                    }catch (Exception e){

                    }
                }

                if(Screen.space.block[yC][xC].airID == Value.airWell){
                    deleteEnemy();
                    damageBase();
                }

                enemyWalk -= Screen.space.blockSize;
            }
    }

    public void intersection(){
        try{

            if (direction == right) {
                if (Screen.space.block[yC][xC + 1].groundID == Value.groundRoad) {
                    direction = right;
                }else if (Screen.space.block[yC + 1][xC].groundID == Value.groundRoad) {
                    direction = down;
                } else if (Screen.space.block[yC - 1][xC].groundID == Value.groundRoad) {
                    direction = up;
                }
            }else if(direction == left){
                if (Screen.space.block[yC][xC - 1].groundID == Value.groundRoad) {
                    direction = left;
                }else if (Screen.space.block[yC + 1][xC].groundID == Value.groundRoad) {
                    direction = down;
                } else if (Screen.space.block[yC - 1][xC].groundID == Value.groundRoad) {
                    direction = up;
                }
            }else if (direction != right && direction != left) {
                if (Screen.space.block[yC][xC + 1].groundID == Value.groundRoad) {
                    direction = right;
                } else if (Screen.space.block[yC][xC - 1].groundID == Value.groundRoad) {
                    direction = left;
                }
            }
        }catch (Exception e){

        }
    }

    //Checks if enemy health is 0
    public boolean checkDeath() {
		
        if (this.health <= 0) {
			deleteEnemy();
			died = true;
			Player.getMoney();

		}else{
            died = false;
        }

        return died;
	}


    public void draw(Graphics g){

        //ENEMY SPRITE
        g.drawImage(Screen.tileset_enemy[Save.level - 1], x, y, width, height, null);


        //ENEMY HEALTH BARS
		g.setColor(new Color(180, 50, 50));
		g.fillRect(x, y - healthSpace - healthHeight, enemySize, healthHeight);

		g.setColor(new Color(50, 180, 50));
		g.fillRect(x, y - healthSpace - healthHeight, enemySize * health / enemyHealth[secondID], healthHeight);

		g.setColor(new Color(0, 0, 0));
		g.drawRect(x, y - healthSpace - healthHeight, enemySize, healthHeight);

    }
}
