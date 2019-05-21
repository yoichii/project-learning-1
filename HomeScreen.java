import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


public class HomeScreen extends BaseScreen implements ActionListener {
	private static final long serialVersionUID = 1L;

    private Controller controller;

    public HomeScreen(Controller controller) {
        super("OTHELLO -- ホーム画面", new Rectangle(100, 100, 600, 650));

        // controller
        this.controller = controller;

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                initHomeUI();
            }
        });
    }

    void initHomeUI() {
         // whole panel
        JPanel wholePanel = new JPanel();
        wholePanel.setBackground(getBackgroundColor());

        // start button
        JPanel startPanel = new JPanel();
        startPanel.setPreferredSize(new Dimension(600, 100));
        startPanel.setBackground(getBackgroundColor());
        //JButton startButton = new JButton("プレイ開始");
        JButton startButton = new JButton(new ImageIcon("images/start.png"));
        startButton.setOpaque(false);
        startButton.setContentAreaFilled(false);
        startButton.setBorderPainted(false);
        startButton.setPreferredSize(new Dimension(300, 100));
        startButton.setActionCommand("play");
        startButton.addActionListener(this.controller);
        startPanel.add(startButton);

        // record button
        JPanel analysisPanel = new JPanel();
        analysisPanel.setPreferredSize(new Dimension(600, 100));
        analysisPanel.setBackground(getBackgroundColor());
        //JButton analysisButton = new JButton("記録解析");
        JButton analysisButton = new JButton(new ImageIcon("images/analysis.png"));
        analysisButton.setOpaque(false);
        analysisButton.setContentAreaFilled(false);
        analysisButton.setBorderPainted(false);
        analysisButton.setPreferredSize(new Dimension(200, 100));
        analysisButton.setActionCommand("analysis");
        analysisButton.addActionListener(controller);
        analysisPanel.add(analysisButton);

        // rule button
        JPanel rulePanel = new JPanel();
        rulePanel.setPreferredSize(new Dimension(600, 100));
        rulePanel.setBackground(getBackgroundColor());
        JButton ruleButton = new JButton("ルール説明");
        ruleButton.setPreferredSize(new Dimension(150, 30));
        ruleButton.addActionListener(this);
        rulePanel.add(ruleButton);

        // transparent
        startPanel.setOpaque(false);
        analysisPanel.setOpaque(false);
        rulePanel.setOpaque(false);
        wholePanel.setOpaque(false);

        // add
        wholePanel.add(startPanel);
        wholePanel.add(analysisPanel);
        wholePanel.add(rulePanel);
        backgroundPanel.add(wholePanel, BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                JLabel label = new JLabel("<html>基本的には普通のオセロのルールと同じです。<br />制限時間があるので気を付けましょう</html>");
                JOptionPane.showMessageDialog(HomeScreen.this,label,"ルール説明",JOptionPane.PLAIN_MESSAGE);
            }
        });
    }
}
