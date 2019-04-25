import java.awt.*;
import javax.swing.*;


public class PlayScreen extends BaseScreen {

    private int pieces[] = new int[64];
    private Controller controller;

    public PlayScreen(Controller controller) {
        super("OTHELLO -- プレイ画面");

        // controller
        this.controller = controller;

        // whole panel
        JPanel wholePanel = new JPanel();
        wholePanel.setBackground(getBackgroundColor());

        // boards
        //JPanel boardPanel = new JPanel();
        //boardPanel.setPreferredSize(new Dimension(400, 400));
        //boardPanel.setLayout(new GridLayout(8, 8));

        JLabel boardLabel = new JLabel(new ImageIcon("./images/board.png"));

        for(int i = 0; i < 64; ++i) {
            pieces[i] = 0;
            if(i == 26 || i == 35) {
                pieces[i] = 1;
            } else if( i == 27 || i == 34) {
                pieces[i] = 2;
            }
        }

        // resign button
        JPanel resignPanel = new JPanel();
        resignPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        resignPanel.setPreferredSize(new Dimension(600, 100));
        resignPanel.setBackground(getBackgroundColor());
        JButton resignButton = new JButton("中断");
        resignButton.setPreferredSize(new Dimension(80, 30));
        resignButton.setActionCommand("resign");
        resignButton.addActionListener(this.controller);
        resignPanel.add(resignButton);

        // add
        wholePanel.add(boardLabel);
        wholePanel.add(resignPanel);
        add(wholePanel, BorderLayout.CENTER);

        /*
        for(int i = 0; i < pieces.length; ++i) {
            JPanel square;

            if (pieces[i] == 0) {
                square = new JPanel();
                square.setBorder(new EtchedBorder());
                square.setColor(Color.green);
                square.setOpaque(true);
            } else if(pieces[i] == 1) {
                square = new JPanel();
                square.setBorder(new EtchedBorder());
                square.setColor(Color.green);
                square.setOpaque(true);
                square.add(new 
            } else {
                square = new JPanel();
                square.setBorder(new EtchedBorder());
                square.setColor(Color.green);
                square.setOpaque(true);
            }

            square.setIcon(image);

            boardPanel.add(square);
        }
        */

        //wholePanel.add(boardPanel);
    }

    public void paint(Graphics g) {
        super.paint(g);
 

    }
}
