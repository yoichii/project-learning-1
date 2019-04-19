package othelloScreen;

import java.awt.*;
import javax.swing.*;


public class HomeScreen extends BaseScreen {

    public HomeScreen() {
        super("OTHELLO -- ホーム画面");

        // whole panel
        JPanel wholePanel = new JPanel();
        wholePanel.setBackground(getBackgroundColor());

        // start button
        JPanel startPanel = new JPanel();
        startPanel.setPreferredSize(new Dimension(600, 100));
        startPanel.setBackground(getBackgroundColor());
        JButton startButton = new JButton("プレイ開始");
        startButton.setPreferredSize(new Dimension(200, 60));
        startPanel.add(startButton);

        // record button
        JPanel recordPanel = new JPanel();
        recordPanel.setPreferredSize(new Dimension(600, 100));
        recordPanel.setBackground(getBackgroundColor());
        JButton recordButton = new JButton("記録解析");
        recordButton.setPreferredSize(new Dimension(200, 60));
        recordPanel.add(recordButton);

        // rule button
        JPanel rulePanel = new JPanel();
        rulePanel.setPreferredSize(new Dimension(600, 100));
        rulePanel.setBackground(getBackgroundColor());
        JButton ruleButton = new JButton("ルール説明");
        ruleButton.setPreferredSize(new Dimension(150, 30));
        rulePanel.add(ruleButton);

        // add
        wholePanel.add(startPanel);
        wholePanel.add(recordPanel);
        wholePanel.add(rulePanel);
        add(wholePanel, BorderLayout.CENTER);
    }
}
