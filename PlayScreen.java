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
    private Timer timer;
    private int time = 121;

    private OthelloButton board[][] = new OthelloButton[8][8];
    private JLabel totalPiecesLabel;
    PlayScreen(Controller controller, Player player, Rectangle rectangle) {
        super("OTHELLO -- プレイ画面", rectangle, 2, player);

        // init variables
        this.controller = controller;
        this.othello = new Othello(player);
        this.player = player;

        // init puttable
        othello.initPuttable();

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                initPlayUI();
            }
        });
    }

    private void initPlayUI() {
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
                OthelloButton square = new OthelloButton(4);
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
        JButton resignButton = new JButton(new ImageIcon("images/resign.png"));
        resignButton.setOpaque(false);
        resignButton.setContentAreaFilled(false);
        resignButton.setBorderPainted(false);
        resignButton.setPreferredSize(new Dimension(100, 100));
        resignButton.setActionCommand("resign");
        resignButton.addActionListener(this.controller);
        resignPanel.add(resignButton);

        // info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new FlowLayout());
        infoPanel.setPreferredSize(new Dimension(300, 100));
        infoPanel.setBackground(getBackgroundColor());
        // opponentLabel
        JLabel opponentLabel = new JLabel("対 " + player.getOpponentname());
        opponentLabel.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
        opponentLabel.setOpaque(false);
        opponentLabel.setPreferredSize(new Dimension(300, 40));
        opponentLabel.setHorizontalAlignment(JLabel.CENTER);
        infoPanel.add(opponentLabel);
        // totalPiecesLabel
        totalPiecesLabel = new JLabel("黒2   白2");
        totalPiecesLabel.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 20));
        totalPiecesLabel.setOpaque(false);
        totalPiecesLabel.setPreferredSize(new Dimension(300, 40));
        totalPiecesLabel.setHorizontalAlignment(JLabel.CENTER);
        infoPanel.add(totalPiecesLabel);

        // timer label
        JPanel timerPanel = new JPanel();
        timerPanel.setBackground(getBackgroundColor());
        timerPanel.setPreferredSize(new Dimension(150, 100));
        timerLabel = new JLabel("");
        timerLabel.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 16));
        timerPanel.add(timerLabel);

        // timer
        ActionListener listener = new ActionListener(){
            public void actionPerformed(ActionEvent event){
                if(othello.isMyTurn){
                    time -= 1;
                    timerLabel.setText(String.valueOf(time));
                } else {
                    timerLabel.setText("相手の番だ");
                }
                if(time <= 0) {
                    controller.controllResign();
                    return;
                }
            }
        };
        timer = new Timer(1000, listener);
        timer.setRepeats(true);
        timer.setInitialDelay(0);
        timer.start();


        // middle panel
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BorderLayout());
        middlePanel.setPreferredSize(new Dimension(600, 100));
        middlePanel.setBackground(getBackgroundColor());
        middlePanel.add(resignPanel, BorderLayout.EAST);
        middlePanel.add(infoPanel, BorderLayout.CENTER);
        middlePanel.add(timerPanel, BorderLayout.WEST);

        // transparent
        resignPanel.setOpaque(false);
        infoPanel.setOpaque(false);
        timerPanel.setOpaque(false);
        boardPanel.setOpaque(false);
        middlePanel.setOpaque(false);
        wholePanel.setOpaque(false);

        // add
        wholePanel.add(boardPanel);
        wholePanel.add(middlePanel);
        backgroundPanel.add(wholePanel, BorderLayout.CENTER);
    }


    public boolean updateBorder(int[] put, int color) {
        boolean pass = othello.getNextBorder(put, color);

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                drawBoard();
                int[] totalPieces = othello.getTotalPieces();
                totalPiecesLabel.setText("黒"+totalPieces[0]+"   白"+totalPieces[1]);
            }
        });

        // reset timer
        time = 121;
        timer.restart();

        return pass;
    }


    private void drawBoard() {
        for(int i = 0; i < othello.ROW; ++i) {
            for(int j = 0; j < othello.COLUMN; ++j) {

                board[i][j].setStatus(othello.getStatus(i, j, player.getMyColor()));
                board[i][j].restartTimer();

                // puttable
                if(!othello.puttableEquals(i, j))
                    board[i][j].setEnabled(false);
                else if(othello.puttableEquals(i, j))
                    board[i][j].setEnabled(true);
            }
        }
    }

    public int[] getTotalPieces() {
        return othello.getTotalPieces();
    }

    public Player getPlayer() {
        return player;
    }

    public void stopTimer() {
        timer.stop();
    }
}


