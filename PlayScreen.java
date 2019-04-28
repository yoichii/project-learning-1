import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;


public class PlayScreen extends BaseScreen implements ActionListener {
	private static final long serialVersionUID = 1L;

    private int pieces[][] = {
        {0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0},
        {0,0,0,2,1,0,0,0},
        {0,0,0,1,2,0,0,0},
        {0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0}
    };
    private int puttable[][] = {
        {0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0},
        {0,0,0,0,0,0,0,0}
    };
    private int myColor = 0;
    private int[] move = new int[2];
    private JButton board[][] = new JButton[8][8];
    private Controller controller;
    private Othello othello;

    public PlayScreen(Controller controller, int myColor) {
        super("OTHELLO -- プレイ画面");

        // init variables
        this.controller = controller;
        this.myColor = myColor;
        this.othello = new Othello();

        // init puttable
        if (myColor==1) {
            puttable[2][3] = 1;
            puttable[3][2] = 1;
            puttable[4][5] = 1;
            puttable[5][4] = 1;
        } else if (myColor==2) {
            puttable[2][4] = 1;
            puttable[4][2] = 1;
            puttable[3][5] = 1;
            puttable[5][3] = 1;
        }

        // whole panel
        JPanel wholePanel = new JPanel();
        wholePanel.setBackground(getBackgroundColor());

        // boards
        JPanel boardPanel = new JPanel();
        boardPanel.setPreferredSize(new Dimension(300, 300));
        boardPanel.setLayout(new GridLayout(8, 8));
 
        // buttons
        for(int i = 0; i < pieces.length; ++i) {
            for(int j = 0; j < pieces[0].length; ++j) {
                JButton square = new JButton();
                square.setBorder(new LineBorder(Color.black, 1, false));
                square.setActionCommand(String.valueOf(i)+","+String.valueOf(j));
                square.addActionListener(this);
                board[i][j] = square;

                boardPanel.add(square);
            }
        }
        drawBoard();

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

    public void actionPerformed(ActionEvent e) {
        // set action command like "2,4" to move[]
        String[] command = e.getActionCommand().split(",");
        for(int i = 0; i < 2; ++i)
            move[i] = Integer.parseInt(command[i]);

        othello.getNextBorder(pieces, puttable, move, myColor);

        drawBoard();
    }

    void drawBoard() {
        for(int i = 0; i < pieces.length; ++i) {
            for(int j = 0; j < pieces[0].length; ++j) {

                // pieces
                if (pieces[i][j] == 0) {
                    board[i][j].setIcon(new ImageIcon("images/green.png"));
                    board[i][j].setOpaque(true);
                } else if(pieces[i][j] == 1) {
                    board[i][j].setIcon(new ImageIcon("images/black.png"));
                    board[i][j].setOpaque(true);
                } else {
                    board[i][j].setIcon(new ImageIcon("images/white.png"));
                    board[i][j].setOpaque(true);
                }

                // puttable
                if(puttable[i][j] == 0)
                    board[i][j].setEnabled(false);
                else if(puttable[i][j] == 1)
                    board[i][j].setEnabled(true);

            }
        }
    }
}
