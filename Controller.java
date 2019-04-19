import othelloScreen.*;
import java.awt.*;
import javax.swing.*;


public class Controller {
    public static void main(String[] args) {
        JFrame loginScreen = new LoginScreen();
        loginScreen.setVisible(false);

        JFrame registerScreen = new RegisterScreen();
        registerScreen.setVisible(false);

        JFrame homeScreen = new HomeScreen();
        homeScreen.setVisible(false);

        JFrame playScreen = new PlayScreen();
        playScreen.setVisible(false);

        JFrame analysisScreen = new AnalysisScreen();
        analysisScreen.setVisible(true);
    }
}


















