import java.awt.event.*;
import java.awt.*;

public class KeyHandler implements MouseMotionListener, MouseListener, KeyListener {

    private Screen screen;
    // private Screen.KeyTyped keyTyped;

    public KeyHandler(Screen screen) {
        this.setScreen(screen);
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }

    public void mouseClicked(MouseEvent e) {

    }


    public void mousePressed(MouseEvent e) {

        if(!Screen.isTitle){
            Screen.shop.purchase(e.getButton());
        }


    }


    public void mouseReleased(MouseEvent e) {

    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    public void mouseDragged(MouseEvent e) {
        Screen.mse = new Point((e.getX()) - ((Frame.size.width - Screen.myWidth) / 2), (e.getY()) - (Frame.size.height - (Screen.myHeight - (Frame.size.width - Screen.myWidth) / 2)));
    }

    public void mouseMoved(MouseEvent e) {
        Screen.mse = new Point((e.getX()) - ((Frame.size.width - Screen.myWidth) / 2), (e.getY()) - (Frame.size.height - (Screen.myHeight - (Frame.size.width - Screen.myWidth) / 2)));
    }

    public void keyENTER(){
        Screen.isTitle = false;
    }

    public void keyTyped(KeyEvent e) {

    }

    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();

        if(keyCode == 10){
            keyENTER();
        }

    }

    public void keyReleased(KeyEvent e) {

    }
}
