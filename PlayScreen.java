import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.*;


public class PlayScreen extends BaseScreen implements ActionListener {
	private static final long serialVersionUID = 1L;

    private Controller controller;
    private Othello othello;
    private Player player;

    private int[] move = new int[2];
    private JButton board[][] = new JButton[8][8];

    public PlayScreen(Controller controller, Player player) {
        super("OTHELLO -- プレイ画面");

        // init variables
        this.controller = controller;
        this.othello = new Othello();
        this.player = player;

        // init puttable
        othello.initPuttable(player.getMyColor());

        // whole panel
        JPanel wholePanel = new JPanel();
        wholePanel.setBackground(getBackgroundColor());

        // boards
        JPanel boardPanel = new JPanel();
        boardPanel.setPreferredSize(new Dimension(300, 300));
        boardPanel.setLayout(new GridLayout(8, 8));

        // buttons
        for(int i = 0; i < othello.row; ++i) {
            for(int j = 0; j < othello.column; ++j) {
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

        othello.getNextBorder(move, player.getMyColor());

        drawBoard();
    }

    void drawBoard() {
        for(int i = 0; i < othello.row; ++i) {
            for(int j = 0; j < othello.column; ++j) {

                // pieces
                if (othello.piecesEqual(i, j, 0)) {
                    board[i][j].setIcon(new ImageIcon("images/green.png"));
                    board[i][j].setOpaque(true);
                } else if(othello.piecesEqual(i, j, 1)) {
                    board[i][j].setIcon(new ImageIcon("images/black.png"));
                    board[i][j].setOpaque(true);
                } else {
                    board[i][j].setIcon(new ImageIcon("images/white.png"));
                    board[i][j].setOpaque(true);
                }

                // puttable
                if(othello.puttableEqual(i, j, 0))
                    board[i][j].setEnabled(false);
                else if(othello.puttableEqual(i, j, 1))
                    board[i][j].setEnabled(true);

            }
        }
    }
}
