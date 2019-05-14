import java.awt.*;
import javax.swing.*;

class ImagePanel extends JPanel {

    private Image img;

    public ImagePanel() {
        this(new ImageIcon("images/background.jpg").getImage());
    }

    public ImagePanel(String img) {
        this(new ImageIcon(img).getImage());
    }

    public ImagePanel(Image img) {
        this.img = img;
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, null);
    }

}

