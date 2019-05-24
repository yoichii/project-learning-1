import java.awt.*;
import javax.swing.*;

public class RegisterScreen extends BaseScreen {
	private static final long serialVersionUID = 1L;

    private Controller controller;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField passwordCheckField;

    public RegisterScreen(Controller controller, Rectangle rectangle) {
        super("OTHELLO -- 会員登録", rectangle, 0, new Player());

        // controller
        this.controller = controller;

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                initRegisterUI();
            }
        });
    }

    void initRegisterUI() {
        // whole panel
        JPanel wholePanel = new JPanel();
        wholePanel.setBackground(getBackgroundColor());

        // login form
        JPanel formPanel = new JPanel();
        formPanel.setPreferredSize(new Dimension(600, 200));
        formPanel.setBackground(getBackgroundColor());
        //// username
        JLabel usernameLabel = new JLabel("ユーザ名");
        usernameLabel.setHorizontalAlignment(JLabel.CENTER);
        usernameLabel.setPreferredSize(new Dimension(160, 40));
        usernameField = new JTextField("");
        usernameField.setPreferredSize(new Dimension(280, 50));
        usernameField.setBackground(new Color(244, 226, 208));
        usernameField.setActionCommand("register send");
        usernameField.addActionListener(this.controller);

        //// password
        JLabel passwordLabel = new JLabel("パスワード");
        passwordLabel.setHorizontalAlignment(JLabel.CENTER);
        passwordLabel.setPreferredSize(new Dimension(160, 40));
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(280, 50));
        passwordField.setBackground(new Color(244, 226, 208));
        passwordField.setActionCommand("register send");
        passwordField.addActionListener(this.controller);
        /*
        //// password check
        JLabel passwordCheckLabel = new JLabel("パスワード確認");
        passwordCheckLabel.setHorizontalAlignment(JLabel.CENTER);
        passwordCheckLabel.setPreferredSize(new Dimension(160, 40));
        passwordCheckField = new JPasswordField();
        passwordCheckField.setPreferredSize(new Dimension(280, 50));
        */

       

        //// add
        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);
        //formPanel.add(passwordCheckLabel);
        //formPanel.add(passwordCheckField);

        // register button
        JPanel registerPanel = new JPanel();
        registerPanel.setPreferredSize(new Dimension(600, 100));
        registerPanel.setBackground(getBackgroundColor());
        JButton registerButton = new JButton("会員登録");
        registerButton.setPreferredSize(new Dimension(200, 60));
        registerButton.setActionCommand("register send");
        registerButton.addActionListener(this.controller);
        registerPanel.add(registerButton);

         // back button
        JPanel backPanel = new JPanel();
        backPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        backPanel.setPreferredSize(new Dimension(600, 100));
        backPanel.setBackground(getBackgroundColor());
        JButton backButton = new JButton(new ImageIcon("images/back.png"));
        backButton.setOpaque(false);
        backButton.setContentAreaFilled(false);
        backButton.setBorderPainted(false);
        backButton.setPreferredSize(new Dimension(100, 80));
        backButton.setActionCommand("back-login");
        backButton.addActionListener(this.controller);
        backPanel.add(backButton);

        // transparent
        formPanel.setOpaque(false);
        registerPanel.setOpaque(false);
        backPanel.setOpaque(false);
        wholePanel.setOpaque(false);

        // add
        wholePanel.add(formPanel);
        wholePanel.add(registerPanel);
        wholePanel.add(backPanel);
        backgroundPanel.add(wholePanel, BorderLayout.CENTER);
    }


    public String[] getFormData() {
        String[] data = {"", ""};
        data[0] = usernameField.getText();
        char[] tmp = passwordField.getPassword();
        data[1] = new String(tmp);

        return data;
    }
}
