import java.awt.*;
import javax.swing.*;


public class AnalysisScreen extends BaseScreen {
	private static final long serialVersionUID = 1L;
    private Controller controller;
    private String[][] tableData;
    private final String[] columns= {"プレイヤ名", "結果", "順番", "コマ数"};

    public AnalysisScreen(Controller controller, String[][] tableData, Rectangle rectangle) {
        super("OTHELLO -- 記録解析", rectangle);

        // controller
        this.controller = controller;

        this.tableData = tableData;

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                initAnalysisUI();
            }
        });
    }

    void initAnalysisUI() {
        JPanel wholePanel = new JPanel();
        JTable recordTable = new JTable(tableData, columns);
        recordTable.setBackground(new Color(244, 226, 208));
        JScrollPane recordScrollPane = new JScrollPane(recordTable);
        recordScrollPane.setPreferredSize(new Dimension(400, 200));
        recordScrollPane.getViewport().setBackground(new Color(244, 226, 208));

        // resign button
        JPanel backPanel = new JPanel();
        backPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        backPanel.setPreferredSize(new Dimension(600, 100));
        backPanel.setBackground(getBackgroundColor());
        JButton backButton = new JButton(new ImageIcon("images/back.png"));
        backButton.setOpaque(false);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.setPreferredSize(new Dimension(100, 80));
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
