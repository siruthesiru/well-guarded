public class Player{
    public static int money = 100;                                          //starting money
    public static int health = 100;                                         //starting health
    public static boolean isWin = false;
    public static int killed = 0;
    public static int prevMoney;

    public static void hasWon(){
        if(Player.killed >= Save.killsToWin){

            Player.killed = 0;
            Screen.won = true;
        }
    }

    public static void getMoney(){
        prevMoney = money;

            Player.money += Enemy.deathReward[Enemy.enemyID];

        checkMoney();

    }

    public static void checkMoney(){
        if(prevMoney > (money + 40)){
            System.out.println(prevMoney);
            money = prevMoney + 20;
        }
    }

}
