import java.awt.*;
import projectlearning1.*;
import javax.swing.*;
import java.io.*;

public class LoginScreen extends BaseScreen {
	private static final long serialVersionUID = 1L;

    private Controller controller;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginScreen(Controller controller) {
        super("OTHELLO -- ログイン画面", new Rectangle(100, 100, 600, 700), 0, new Player());

        // set controller
        this.controller = controller;

        EventQueue.invokeLater(new Runnable() {
            public void run() {
                initLoginUI();
            }
        });
    }

    void initLoginUI() {
         // whole panel
        JPanel wholePanel = new JPanel();
        wholePanel.setBackground(getBackgroundColor());

        // login form
        JPanel formPanel = new JPanel();
        formPanel.setPreferredSize(new Dimension(600, 150));
        formPanel.setBackground(getBackgroundColor());
        //// username
        JLabel usernameLabel = new JLabel("ユーザ名");
        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/g_brushtappitsu_freeH.ttf")).deriveFont(Font.BOLD, 18);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (font != null)
            usernameLabel.setFont(font);

        usernameLabel.setHorizontalAlignment(JLabel.CENTER);
        usernameLabel.setPreferredSize(new Dimension(160, 40));
        usernameField = new JTextField("");
        usernameField.setBackground(new Color(244, 226, 208));
        usernameField.setPreferredSize(new Dimension(280, 50));
        usernameField.addActionListener(this.controller);
        usernameField.setActionCommand("login");
        //// password
        JLabel passwordLabel = new JLabel("パスワード");
        if (font != null)
            passwordLabel.setFont(font);
        passwordLabel.setHorizontalAlignment(JLabel.CENTER);
        passwordLabel.setPreferredSize(new Dimension(160, 40));
        passwordField = new JPasswordField();
        passwordField.setBackground(new Color(244, 226, 208));
        passwordField.setPreferredSize(new Dimension(280, 50));
        passwordField.addActionListener(this.controller);
        passwordField.setActionCommand("login");
        //// add
        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        // login button
        JPanel loginPanel = new JPanel();
        loginPanel.setPreferredSize(new Dimension(600, 100));
        loginPanel.setBackground(getBackgroundColor());
        JButton loginButton = new JButton(new ImageIcon("images/login.png"));
        loginButton.setOpaque(false);
        loginButton.setContentAreaFilled(false);
        loginButton.setBorderPainted(false);

        loginButton.setPreferredSize(new Dimension(200, 60));
        loginButton.addActionListener(this.controller);
        loginButton.setActionCommand("login");
        loginPanel.add(loginButton);

        // register button
        JPanel registerPanel = new JPanel();
        registerPanel.setPreferredSize(new Dimension(600, 100));
        registerPanel.setBackground(getBackgroundColor());
        JButton registerButton = new JButton(new ImageIcon("images/register.png"));
        registerButton.setOpaque(false);
        registerButton.setContentAreaFilled(false);
        registerButton.setBorderPainted(false);

        registerButton.setPreferredSize(new Dimension(200, 60));
        //registerButton.setBackground(new Color(190, 190, 190));
        registerButton.addActionListener(this.controller);
        registerButton.setActionCommand("register");
        registerPanel.add(registerButton);

        // transparent
        wholePanel.setOpaque(false);
        formPanel.setOpaque(false);
        loginPanel.setOpaque(false);
        registerPanel.setOpaque(false);

        // add
        wholePanel.add(formPanel);
        wholePanel.add(loginPanel);
        wholePanel.add(registerPanel);
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
