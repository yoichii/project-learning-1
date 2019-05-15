import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;


public class PlayScreen extends BaseScreen { 
	private static final long serialVersionUID = 1L;

    private Controller controller;
    private Othello othello;
    private Player player;
    private JLabel timerLabel;

    private JButton board[][] = new JButton[8][8];

    PlayScreen(Controller controller, Player player) {
        super("OTHELLO -- プレイ画面");

        // init variables
        this.controller = controller;
        this.othello = new Othello();
        this.player = player;

        // init puttable
        othello.initPuttable(player.getMyColor());

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                initPlayUI();
            }
        });
    }

    void initPlayUI() {
        // whole panel
        JPanel wholePanel = new JPanel();
        wholePanel.setBackground(getBackgroundColor());

        // boards
        JPanel boardPanel = new JPanel();
        boardPanel.setPreferredSize(new Dimension(300, 300));
        boardPanel.setLayout(new GridLayout(8, 8));

        // buttons
        for(int i = 0; i < othello.ROW; ++i) {
            for(int j = 0; j < othello.COLUMN; ++j) {
                JButton square = new JButton();
                square.setBorder(new LineBorder(Color.black, 1, false));
                square.setActionCommand(String.valueOf(i)+","+String.valueOf(j));
                square.addActionListener(this.controller);
                board[i][j] = square;

                boardPanel.add(square);
            }
        }

        drawBoard();

        JPanel resignPanel = new JPanel();
        // resign button
        resignPanel.setLayout(new FlowLayout());
        resignPanel.setPreferredSize(new Dimension(150, 100));
        resignPanel.setBackground(getBackgroundColor());
        JButton resignButton = new JButton("中断");
        resignButton.setPreferredSize(new Dimension(80, 30));
        resignButton.setActionCommand("resign");
        resignButton.addActionListener(this.controller);
        resignPanel.add(resignButton);

        // timer label
        JPanel timerPanel = new JPanel();
        timerPanel.setBackground(getBackgroundColor());
        timerPanel.setPreferredSize(new Dimension(150, 100));
        timerLabel = new JLabel("120");
        timerLabel.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 16));
        timerPanel.add(timerLabel);

        // middle panel
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BorderLayout());
        middlePanel.setPreferredSize(new Dimension(600, 100));
        middlePanel.setBackground(getBackgroundColor());
        middlePanel.add(resignPanel, BorderLayout.EAST);
        middlePanel.add(timerPanel, BorderLayout.WEST);

        // transparent
        boardPanel.setOpaque(false);
        middlePanel.setOpaque(false);
        wholePanel.setOpaque(false);

        // add
        wholePanel.add(boardPanel);
        wholePanel.add(middlePanel);
        backgroundPanel.add(wholePanel, BorderLayout.CENTER);
    }

    void drawBoard() {
        for(int i = 0; i < othello.ROW; ++i) {
            for(int j = 0; j < othello.COLUMN; ++j) {

                // pieces
                if (othello.pieceEquals(i, j, 0)) {
                    board[i][j].setIcon(new ImageIcon("images/green-able.png"));
                    board[i][j].setDisabledIcon(new ImageIcon("images/green.png"));
                    board[i][j].setOpaque(true);
                } else if(othello.pieceEquals(i, j, 1)) {
                    ImageIcon black = new ImageIcon("images/black.png");
                    board[i][j].setIcon(black);
                    board[i][j].setDisabledIcon(black);
                    board[i][j].setOpaque(true);
                } else {
                    ImageIcon white = new ImageIcon("images/white.png");
                    board[i][j].setIcon(white);
                    board[i][j].setDisabledIcon(white);
                    board[i][j].setOpaque(true);
                }

                // puttable
                if(!othello.puttableEquals(i, j))
                    board[i][j].setEnabled(false);
                else if(othello.puttableEquals(i, j))
                    board[i][j].setEnabled(true);

            }
        }
    }

    public void updateBorder(int[] move) {
        othello.getNextBorder(move, player.getMyColor());

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                drawBoard();
            }
        });
    }

    /*
     public void actionPerformed(ActionEvent e) {
        // set action command like "2,4" to move[]
        String[] command = e.getActionCommand().split(",");
        int[] move = new int[2];
        for(int i = 0; i < 2; ++i)
            move[i] = Integer.parseInt(command[i]);

        othello.getNextBorder(move, player.getMyColor());

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                drawBoard();
            }
        });
    }
    */


}
