package othelloScreen;

import java.awt.*;
import javax.swing.*;


public class AnalysisScreen extends BaseScreen {

    public AnalysisScreen() {
        super("OTHELLO -- 記録解析");

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

        String[] sampleColumns = {"プレイヤ名", "結果", "順番", "コマ数"};

        JPanel wholePanel = new JPanel();
        JTable recordTable = new JTable(sampleData, sampleColumns);
        JScrollPane recordScrollPane = new JScrollPane(recordTable);
        recordScrollPane.setPreferredSize(new Dimension(400, 200));

        wholePanel.add(recordScrollPane);
        wholePanel.setBackground(getBackgroundColor());

        getContentPane().add(wholePanel, BorderLayout.CENTER);
    }
}
