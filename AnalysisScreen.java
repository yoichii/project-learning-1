import java.awt.*;
import javax.swing.*;


public class AnalysisScreen extends BaseScreen {
	private static final long serialVersionUID = 1L;
    private Controller controller;
    private String[][] sampleData;
    private final String[] sampleColumns = {"プレイヤ名", "結果", "順番", "コマ数"};

    public AnalysisScreen(Controller controller) {
        super("OTHELLO -- 記録解析");

        // controller
        this.controller = controller;

        String[][] sampleData = {
            {"tommy", "勝利", "先手", "43-23"},
            {"shirashira", "負け", "先手", "12-52"},
            {"勉", "引き分け", "後手", "32-32"},
            {"tommy", "勝利", "先手", "43-23"},
            {"shirashira", "負け", "先手", "12-52"},
            {"勉", "引き分け", "後手", "32-32"},
            {"tommy", "勝利", "先手", "43-23"},
            {"shirashira", "負け", "先手", "12-52"},
            {"勉", "引き分け", "後手", "32-32"},
            {"tommy", "勝利", "先手", "43-23"},
            {"shirashira", "負け", "先手", "12-52"},
            {"勉", "引き分け", "後手", "32-32"},
            {"tommy", "勝利", "先手", "43-23"},
            {"shirashira", "負け", "先手", "12-52"},
            {"勉", "引き分け", "後手", "32-32"},
            {"tommy", "勝利", "先手", "43-23"},
            {"shirashira", "負け", "先手", "12-52"},
            {"勉", "引き分け", "後手", "32-32"},
            {"tommy", "勝利", "先手", "43-23"},
            {"shirashira", "負け", "先手", "12-52"},
            {"勉", "引き分け", "後手", "32-32"},
            {"tommy", "勝利", "先手", "43-23"},
            {"shirashira", "負け", "先手", "12-52"},
            {"勉", "引き分け", "後手", "32-32"}
        };

        this.sampleData = sampleData;

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                initAnalysisUI();
            }
        });
    }

    void initAnalysisUI() {
        JPanel wholePanel = new JPanel();
        JTable recordTable = new JTable(sampleData, sampleColumns);
        JScrollPane recordScrollPane = new JScrollPane(recordTable);
        recordScrollPane.setPreferredSize(new Dimension(400, 200));

        // resign button
        JPanel backPanel = new JPanel();
        backPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        backPanel.setPreferredSize(new Dimension(600, 100));
        backPanel.setBackground(getBackgroundColor());
        JButton backButton = new JButton("戻る");
        backButton.setPreferredSize(new Dimension(80, 30));
        backButton.setActionCommand("back");
        backButton.addActionListener(this.controller);
        backPanel.add(backButton);

        // transparent
        recordScrollPane.setOpaque(false);
        backPanel.setOpaque(false);
        wholePanel.setOpaque(false);

        wholePanel.add(recordScrollPane);
        wholePanel.add(backPanel);
        wholePanel.setBackground(getBackgroundColor());

        backgroundPanel.add(wholePanel, BorderLayout.CENTER);
    }
}
