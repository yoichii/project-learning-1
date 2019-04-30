import java.awt.event.*;
import javax.swing.*;

public class Controller implements ActionListener {
    // screen
    LoginScreen loginScreen;
    HomeScreen homeScreen;
    RegisterScreen registerScreen;
    PlayScreen playScreen;
    AnalysisScreen analysisScreen;
    // player
    Player player;

    public static void main(String[] args) {
        Controller controller = new Controller();

        LoginScreen loginScreen = new LoginScreen(controller);
        controller.loginScreen = loginScreen;
        loginScreen.setText(" 久しぶりだね！");
        loginScreen.setVisible(true);


    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if(command.equals("login")) {
            controllLogin();
        } else if(command.equals("register")) {
            controllRegister();
        } else if(command.equals("register send")) {
            controllRegisterSend();
        } else if(command.equals("play")) {
            controllPlay();
        } else if(command.equals("resign")) {
            controllResign();
        } else if(command.equals("analysis")) {
            controllAnalysis();
        } else if(command.equals("back")) {
            controllBack();
        }

    }

    void controllLogin() {
        homeScreen = new HomeScreen(this);
        homeScreen.setText(" このオセロは制限時間があるから注意だ！");
        homeScreen.setVisible(true);
        loginScreen.setVisible(false);
    }

    void controllRegister() {
        registerScreen = new RegisterScreen(this);
        registerScreen.setText(" ようこそ！これからよろしくね！");
        registerScreen.setVisible(true);
        loginScreen.setVisible(false);
    }

    void controllRegisterSend() {
        loginScreen.setVisible(true);
        registerScreen.setVisible(false);
    }


    void controllPlay() {
        this.player =  new Player();
        player.setMyColor(2);
        playScreen = new PlayScreen(this, player);
        playScreen.setText(" 君は後手だ！有利だぞ！");
        playScreen.setVisible(true);
        homeScreen.setVisible(false);
    }

    void controllResign() {
        playScreen.setText("中断したから負けだよ！");

        ActionListener listener = new ActionListener(){
            public void actionPerformed(ActionEvent event){
                homeScreen.setVisible(true);
                playScreen.setVisible(false);
            }
        };
        Timer timer = new Timer(3000, listener);
        timer.setRepeats(false);
        timer.start();
    }

    void controllAnalysis() {
        analysisScreen = new AnalysisScreen(this);
        analysisScreen.setText(" 君は強いぞ！");
        analysisScreen.setVisible(true);
        homeScreen.setVisible(false);
    }

    void controllBack() {
        homeScreen.setVisible(true);
        analysisScreen.setVisible(false);
    }
}


















