import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;


public abstract class BaseScreen extends JFrame {
	private static final long serialVersionUID = 1L;

    private String text;
    private ImageIcon icon;
    private Color backgroundColor;
    private JLabel speechBubbleLabel;

    public BaseScreen(String title) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                initBaseUI(title);
            }
        });
    }

    void initBaseUI(String title) {
        // config
        setTitle(title);
        setBounds(100, 100, 600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // top panel
        JPanel topPanel = new JPanel();
        backgroundColor = new Color(204, 187, 163);
        //backgroundColor = new Color(255, 193, 157);
        //backgroundColor = Color.white;
        topPanel.setBackground(backgroundColor);
        //// title
        JLabel titleLabel = new JLabel();
        ImageIcon titleimage = new ImageIcon("images/title.png");
        titleLabel.setIcon(titleimage);
        //// add
        topPanel.add(titleLabel);

        // bottom panel
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 10));
        bottomPanel.setBackground(backgroundColor);
        //// icon
        JLabel iconlabel = new JLabel();
        icon = new ImageIcon("images/samurai3.png");
        iconlabel.setIcon(icon);
        //// add
        bottomPanel.add(iconlabel);
        //// speech bubble
        text = " 調子がいいね！";
        speechBubbleLabel = new JLabel(text);
        speechBubbleLabel.setOpaque(true);
        speechBubbleLabel.setBackground(Color.white);
        speechBubbleLabel.setPreferredSize(new Dimension(400, 80));
        speechBubbleLabel.setBorder(new BubbleBorder());
        //// add
        bottomPanel.add(speechBubbleLabel);

        // add
        getContentPane().add(topPanel, BorderLayout.NORTH);
        getContentPane().add(bottomPanel, BorderLayout.SOUTH);

    }

    public void setText(String text) {
        this.text = text;

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                speechBubbleLabel.setText(text);
            }
        });
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public Color getBackgroundColor() {
        return this.backgroundColor;
    }

    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
    }


    // Border for speechBubbleLabel
    class BubbleBorder implements Border {

        private int inset;
        private int angleWidth;
        private int angleHeight;

        public Insets getBorderInsets(Component c){
            inset = 3;
            return new Insets(inset, inset, inset, inset);
        }

        public boolean isBorderOpaque(){
            return false;
        }

        public void paintBorder (Component c, Graphics g, int x, int y, int width, int height){
            /*                  1
             *       6 /-------------------\ 7
             *         |                   |
             *    5    |                   |
             *         |                   |
             *        <                    |   2
             *      10 |                   |
             *    4    |                   |
             *         |                   |
             *       9 \-------------------/ 8
             *                  3
             *
             *                                          */
            Insets insets = getBorderInsets(c);

            int top = insets.top;
            int bottom = insets.bottom;
            int right = insets.right;
            int left = insets.left;

            angleWidth = 0;
            angleHeight = 0;

            int xPoints[] = {x, x-angleWidth, x};
            int yPoints[] = {y+height/2-angleHeight/2, y+height/2, y+height/2+angleHeight/2};

            g.setColor(Color.black);
            g.fillRect(x+left, y, width-left-right, top); // 1
            g.fillRect(x+width-right, y+top, right, height-top-bottom); // 2
            g.fillRect(x+left, y+height-bottom, width-left-right, bottom); // 3
            g.fillRect(x, y+height/2+angleHeight/2, left, height/2-bottom-angleHeight/2); // 4
            g.fillRect(x, y+top, left, height/2-top-angleHeight/2); // 5
            g.fillArc(x, y, 2*left, 2*top, 90, 90); // 6
            g.fillArc(x+width-2*right, y, 2*right, 2*top, 0, 90); // 7
            g.fillArc(x+width-2*right, y+height-2*bottom, 2*right, 2*bottom, 270, 90); // 8
            g.fillArc(x, y+height-2*bottom, 2*left, 2*bottom, 180, 90); // 9
            g.drawPolygon(xPoints, yPoints, 3); // 10
        }

        void setInset(int inset) {
            this.inset = inset;
        }

        void setAngleWidth(int angleWidth) {
            this.angleWidth = angleWidth;
        }

        void setAngleHeight(int angleHeight) {
            this.angleHeight = angleHeight;
        }

    }
}
