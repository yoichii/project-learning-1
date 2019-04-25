import java.awt.*;
import javax.swing.*;
import javax.swing.border.*;


public class PlayScreen extends BaseScreen {
	private static final long serialVersionUID = 1L;

    private int pieces[] = new int[64];
    private JButton board[][] = new JButton[8][8];
    private Controller controller;

    public PlayScreen(Controller controller) {
        super("OTHELLO -- プレイ画面");

        // controller
        this.controller = controller;

        // whole panel
        JPanel wholePanel = new JPanel();
        wholePanel.setBackground(getBackgroundColor());

        // boards
        JPanel boardPanel = new JPanel();
        boardPanel.setPreferredSize(new Dimension(300, 300));
        boardPanel.setLayout(new GridLayout(8, 8));
        // pieces init
        for(int i = 0; i < 64; ++i) {
            pieces[i] = 0;
            if(i == 27 || i == 36) {
                pieces[i] = 1;
            } else if( i == 28 || i == 35) {
                pieces[i] = 2;
            }
        }
        // instanciate buttons
        for(int i = 0; i < pieces.length; ++i) {
            JButton square = new JButton();
            square.setBorder(new LineBorder(Color.black, 1, false));
            board[i/8][i%8] = square;

            if (pieces[i] == 0) {
                square.setIcon(new ImageIcon("images/green.png"));
                square.setOpaque(true);
            } else if(pieces[i] == 1) {
                square.setIcon(new ImageIcon("images/black.png"));
                square.setOpaque(true);
            } else {
                square.setIcon(new ImageIcon("images/white.png"));
                square.setOpaque(true);
            }

            boardPanel.add(square);
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
        wholePanel.add(boardPanel);
        wholePanel.add(resignPanel);
        add(wholePanel, BorderLayout.CENTER);
    }
}
