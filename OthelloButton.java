import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;

public class OthelloButton extends JButton {
    private String[] whiteCatPaths = {"images/cat0.png", "images/cat1.png", "images/cat2.png", "images/cat3.png", "images/cat4.png"};
    private String[] blackCatPaths = {"images/cat0-black.png", "images/cat1-black.png", "images/cat2-black.png", "images/cat3-black.png", "images/cat4-black.png"};
    private int i = 0;
    private ActionListener listener;
    private int status = 0;
    private Timer timer;

    // status
    // 0: empty, 1: puttable-black, 2: puttable-white, 3: empty -> black, 4: empty -> white, 5: white -> black, 6: black -> white, 7: black, 8: white

    OthelloButton(int status) {
        super();

        this.status = status;

         // timer
        ActionListener listener = new ActionListener(){
            public void actionPerformed(ActionEvent event){
                if(i < 9)
                    i += 1;
                else
                    ((Timer)event.getSource()).stop();

                repaint();
            }
        };
        timer = new Timer(80, listener);
        timer.setRepeats(true);
        timer.setInitialDelay(0);
        timer.start();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        switch(status) {
            case 0:
                break;
            case 1:
                if(i < 9)
                    break;
                else
                    g.drawImage((new ImageIcon(blackCatPaths[0])).getImage(), 0, 0, null);
                break;
            case 2:
                if(i < 9)
                    break;
                else
                    g.drawImage((new ImageIcon(whiteCatPaths[0])).getImage(), 0, 0, null);
                break;
            case 3:
                if(i < 5)
                    g.drawImage((new ImageIcon(blackCatPaths[0])).getImage(), 0, 0, null);
                else
                    g.drawImage((new ImageIcon(blackCatPaths[i-5])).getImage(), 0, 0, null);
                break;
            case 4:
                if(i < 5)
                    g.drawImage((new ImageIcon(whiteCatPaths[0])).getImage(), 0, 0, null);
                else
                    g.drawImage((new ImageIcon(whiteCatPaths[i-5])).getImage(), 0, 0, null);
                break;
            case 5:
                if(i < 5)
                    g.drawImage((new ImageIcon(whiteCatPaths[4-i])).getImage(), 0, 0, null);
                else
                    g.drawImage((new ImageIcon(blackCatPaths[i-5])).getImage(), 0, 0, null);
                break;
            case 6:
                if(i < 5)
                    g.drawImage((new ImageIcon(blackCatPaths[4-i])).getImage(), 0, 0, null);
                else
                    g.drawImage((new ImageIcon(whiteCatPaths[i-5])).getImage(), 0, 0, null);
                break;
            case 7:
                g.drawImage((new ImageIcon(blackCatPaths[4])).getImage(), 0, 0, null);
                break;
            case 8:
                g.drawImage((new ImageIcon(whiteCatPaths[4])).getImage(), 0, 0, null);
                break;
        }
    }

    public void restartTimer() {
        i = 0;
        /*
        timer = new Timer(200, listener);
        timer.setRepeats(true);
        timer.setInitialDelay(1000);
        timer.start();
        */
        if(status != 0 && status != 7 && status != 8)
            timer.restart();
    }

    public void setStatus(int status) {
        this.status = status;
    }
}


