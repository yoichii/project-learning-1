package othelloScreen;

import java.awt.*;
import javax.swing.*;

public class LoginScreen extends BaseScreen {

    public LoginScreen() {
        super("OTHELLO -- ログイン画面");
        // whole panel
        JPanel wholePanel = new JPanel();
        wholePanel.setBackground(getBackgroundColor());

        // login form
        JPanel formPanel = new JPanel();
        formPanel.setPreferredSize(new Dimension(600, 150));
        formPanel.setBackground(getBackgroundColor());
        //// username
        JLabel usernameLabel = new JLabel("ユーザ名");
        usernameLabel.setHorizontalAlignment(JLabel.CENTER);
        usernameLabel.setPreferredSize(new Dimension(160, 40));
        JTextField usernameField = new JTextField("");
        usernameField.setPreferredSize(new Dimension(280, 50));
        //// password
        JLabel passwordLabel = new JLabel("パスワード");
        passwordLabel.setHorizontalAlignment(JLabel.CENTER);
        passwordLabel.setPreferredSize(new Dimension(160, 40));
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(280, 50));
        //// add
        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        // login button
        JPanel loginPanel = new JPanel();
        loginPanel.setPreferredSize(new Dimension(600, 100));
        loginPanel.setBackground(getBackgroundColor());
        JButton loginButton = new JButton("ログイン");
        loginButton.setPreferredSize(new Dimension(200, 60));
        loginPanel.add(loginButton);

        // register button
        JPanel registerPanel = new JPanel();
        registerPanel.setPreferredSize(new Dimension(600, 100));
        registerPanel.setBackground(getBackgroundColor());
        JButton registerButton = new JButton("会員登録はこちらから");
        registerButton.setPreferredSize(new Dimension(200, 30));
        registerButton.setBackground(new Color(190, 190, 190));
        //registerButton.setForeground(Color.white);
        registerButton.setOpaque(true);
        registerButton.setBorderPainted(false);
        registerPanel.add(registerButton);

        // add
        wholePanel.add(formPanel);
        wholePanel.add(loginPanel);
        wholePanel.add(registerPanel);
        add(wholePanel, BorderLayout.CENTER);
    }
}
