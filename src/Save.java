import java.io.File;
import java.io.InputStreamReader;
import java.util.*;

//SAVE is the CLASS for LOADING AND SAVING PROGRESS
//READS THE STAGE FILES IN THE SAVE DIRECTORY

public class Save {
    public static int killsToWin = 0;
    public static int level = 1;

    public static int maxLevel = 3;

    public void loadSave(InputStreamReader inputStreamReader){
        try{

            Scanner loadScanner = new Scanner(inputStreamReader);

            while(loadScanner.hasNext()){

                killsToWin = loadScanner.nextInt();

                Player.money = loadScanner.nextInt();

                Player.health = loadScanner.nextInt();

                for(int x = 0; x < Screen.space.block.length; x++){
                    for(int y = 0; y < Screen.space.block[0].length; y++){

                        Screen.space.block[x][y].groundID = loadScanner.nextInt();

                    }
                }

                for(int x = 0; x < Screen.space.block.length; x++){
                    for(int y = 0; y < Screen.space.block[0].length; y++){

                        Screen.space.block[x][y].airID = loadScanner.nextInt();

                    }
                }
            }

            loadScanner.close();

        }catch(Exception e) {
            throw new RuntimeException(e);
        }



    }
}
