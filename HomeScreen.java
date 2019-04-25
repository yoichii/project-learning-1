import java.awt.*;
import javax.swing.*;


public class HomeScreen extends BaseScreen {
	private static final long serialVersionUID = 1L;

    private Controller controller;

    public HomeScreen(Controller controller) {
        super("OTHELLO -- ホーム画面");

        // controller
        this.controller = controller;

        // whole panel
        JPanel wholePanel = new JPanel();
        wholePanel.setBackground(getBackgroundColor());

        // start button
        JPanel startPanel = new JPanel();
        startPanel.setPreferredSize(new Dimension(600, 100));
        startPanel.setBackground(getBackgroundColor());
        JButton startButton = new JButton("プレイ開始");
        startButton.setPreferredSize(new Dimension(200, 60));
        startButton.setActionCommand("play start");
        startButton.addActionListener(this.controller);
        startPanel.add(startButton);

        // record button
        JPanel analysisPanel = new JPanel();
        analysisPanel.setPreferredSize(new Dimension(600, 100));
        analysisPanel.setBackground(getBackgroundColor());
        JButton analysisButton = new JButton("記録解析");
        analysisButton.setPreferredSize(new Dimension(200, 60));
        analysisButton.setActionCommand("analysis");
        analysisButton.addActionListener(controller);
        analysisPanel.add(analysisButton);

        // rule button
        JPanel rulePanel = new JPanel();
        rulePanel.setPreferredSize(new Dimension(600, 100));
        rulePanel.setBackground(getBackgroundColor());
        JButton ruleButton = new JButton("ルール説明");
        ruleButton.setPreferredSize(new Dimension(150, 30));
        rulePanel.add(ruleButton);

        // add
        wholePanel.add(startPanel);
        wholePanel.add(analysisPanel);
        wholePanel.add(rulePanel);
        add(wholePanel, BorderLayout.CENTER);
    }
}
