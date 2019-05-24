import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class ImagePanel extends JPanel {

    private Image img;
    private Image fuji;
    private String[] imageURL = {"images/dog.png", "images/horse.png", "images/daruma.png", "images/dragon.png", "images/oni.png"};
    private int[] imageX = {20, 0, 10, 0, 0};
    private int[] imageY = {550, 550, 520, 480, 400};
    private Image characterImage;
    private int i = 0;
    private int characterLevel;

    public ImagePanel(int characterLevel) {
        this(new ImageIcon("images/background.jpg").getImage());
        fuji = new ImageIcon("images/fuji.png").getImage();
        this.characterLevel = characterLevel;
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
        g.drawImage(fuji, 0, 1100 - (int)(800*Math.sin(i*Math.PI/60)), null);
        g.drawImage((new ImageIcon(imageURL[characterLevel])).getImage(), imageX[characterLevel], imageY[characterLevel], null);
    }

    public void changeLevel(int characterLevel) {
        this.characterLevel = characterLevel;
        repaint();
    }
}

