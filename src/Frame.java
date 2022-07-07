import javax.swing.*;
import java.awt.*;

public class Frame extends JFrame{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    
    public static String title = "WELL GUARDED";
    public static Dimension size = new Dimension(854,660);

    public Frame(){
        setTitle(title);
        setSize(size);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        init();
    }

    public void init(){
        setLayout(new GridLayout(1, 1, 0 , 0));

        Screen screen = new Screen(this);
        add(screen);

        setVisible(true);
    }

    public static void main(String[] args){
        Frame frame = new Frame();

    }
}
