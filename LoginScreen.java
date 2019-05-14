import java.awt.*;
import javax.swing.*;

public class LoginScreen extends BaseScreen {
	private static final long serialVersionUID = 1L;

    private Controller controller;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginScreen(Controller controller) {
        super("OTHELLO -- ログイン画面");

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
        usernameLabel.setHorizontalAlignment(JLabel.CENTER);
        usernameLabel.setPreferredSize(new Dimension(160, 40));
        usernameField = new JTextField("");
        usernameField.setPreferredSize(new Dimension(280, 50));
        //// password
        JLabel passwordLabel = new JLabel("パスワード");
        passwordLabel.setHorizontalAlignment(JLabel.CENTER);
        passwordLabel.setPreferredSize(new Dimension(160, 40));
        passwordField = new JPasswordField();
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
        loginButton.addActionListener(this.controller);
        loginButton.setActionCommand("login");
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


    public void showError(Message message) {
        switch(message.getStatus()) {
            case invalidUsername:
                setText("ユーザ名が間違ってるよ");
                break;
            case invalidPassword:
                setText("パスワードが間違ってるよ");
                break;
            case unknownError:
                setText("エラーが起きたよ\nもう一度試してね");
                break;
            case classNotFoundException:
                setText("サーバから受信できなかったよ\nもう一度試してね");
                break;
            case ioException:
                setText("サーバと通信ができなかったよ\nもう一度試してね");
                break;
            case nullObject:
               setText("サーバと接続ができてないよ\nもう一度試してね");
                break;
            default:
                setText("エラーが起きたよ\nもう一度試してね");
        }
    }

}
