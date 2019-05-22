import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class ImagePanel extends JPanel {

    private Image img;
    private Image fuji;
    private int i = 0;

    public ImagePanel() {
        this(new ImageIcon("images/background.jpg").getImage());
        fuji = new ImageIcon("images/fuji.png").getImage();

    }

    public ImagePanel(String img) {
        this(new ImageIcon(img).getImage());
    }

    public ImagePanel(Image img) {
        this.img = img;


        // timer
        ActionListener listener = new ActionListener(){
            public void actionPerformed(ActionEvent event){
                if(i < 30)
                    i += 1;
                else
                    ((Timer)event.getSource()).stop();

                repaint();
            }
        };

        Timer timer = new Timer(30, listener);
        timer.setRepeats(true);
        timer.setInitialDelay(0);
        timer.start();

    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);
        //g.drawImage(fuji, 0, 0, null);
        g.drawImage(fuji, 0, 1100 - (int)(800*Math.sin(i*Math.PI/60)), null);
    }

}

