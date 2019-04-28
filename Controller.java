import java.awt.event.*;
import javax.swing.*;

public class Controller implements ActionListener {
    LoginScreen loginScreen;
    HomeScreen homeScreen;
    RegisterScreen registerScreen;
    PlayScreen playScreen;
    AnalysisScreen analysisScreen;

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
            homeScreen = new HomeScreen(this);
            homeScreen.setText(" このオセロは制限時間があるから注意だ！");
            homeScreen.setVisible(true);
            loginScreen.setVisible(false);
        } else if(command.equals("register")) {
            registerScreen = new RegisterScreen(this);
            registerScreen.setText(" ようこそ！これからよろしくね！");
            registerScreen.setVisible(true);
            loginScreen.setVisible(false);
        } else if(command.equals("register send")) {
            loginScreen.setVisible(true);
            registerScreen.setVisible(false);
        } else if(command.equals("play start")) {
            playScreen = new PlayScreen(this, 1);
            playScreen.setText(" 君は後手だ！有利だぞ！");
            playScreen.setVisible(true);
            homeScreen.setVisible(false);
        } else if(command.equals("resign")) {
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
        } else if(command.equals("analysis")) {
            analysisScreen = new AnalysisScreen(this);
            analysisScreen.setText(" 君は強いぞ！");
            analysisScreen.setVisible(true);
            homeScreen.setVisible(false);
        } else if(command.equals("back")) {
            homeScreen.setVisible(true);
            analysisScreen.setVisible(false);
        }
    }
}


















