 import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.sound.sampled.Clip;
import javax.swing.Timer;


public class Controller implements ActionListener {
    // screen
    private LoginScreen loginScreen = null;
    private HomeScreen homeScreen = null;
    private RegisterScreen registerScreen = null;
    private PlayScreen playScreen = null;
    private AnalysisScreen analysisScreen = null;
    // player and client
    private Player player = null;
    private Client client = null;

    // BGM
    Clip clip = null;
    Clip clip1 = null;

    public static void main(String[] args) {
        Controller controller = new Controller();

        LoginScreen loginScreen = new LoginScreen(controller);
        controller.loginScreen = loginScreen;
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                loginScreen.setText(" 久しぶりだね！");
                loginScreen.setVisible(true);
            }
        });

        /*
        controller.clip = BaseScreen.createClip(new File("sounds/preparation.wav"));
      	controller.clip.loop(Clip.LOOP_CONTINUOUSLY);
        */
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
            try {
                controllPlay();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        } else if(command.equals("resign")) {
            controllResign();
        } else if(command.equals("analysis")) {
            controllAnalysis();
        } else if(command.equals("back")) {
            controllBack();
        } else {
            controllPut(command);
        }

    }

    private void controllLogin() {

        if (client == null) {
            client = new Client(this);
        }

        // establish connection
        String err = client.establishConnection();
        if (!err.equals("")) {
            loginScreen.setText(err);
            return;
        }

        // set a message
        Message message = new Message();
        message.setType(Type.login);
        String[] data = loginScreen.getFormData();
        message.setUsername(data[0]);
        message.setPassword(data[1]);

        // send a message
        sendMessage(message, loginScreen);
    }


    private void controllRegister() {
        if(registerScreen == null)
            registerScreen = new RegisterScreen(this);
            transit(loginScreen, registerScreen, " 新入り！　よろしく頼もう");
    }


    void controllRegisterSend() {
        if (client == null) {
            client = new Client(this);
        }

        // establish connection
        String err = client.establishConnection();
        if (!err.equals("")) {
            registerScreen.setText(err);
            return;
        }

        // set a message
        Message message = new Message();
        message.setType(Type.login);
        String[] data = registerScreen.getFormData();
        message.setUsername(data[0]);
        message.setPassword(data[1]);

        // send a message
        sendMessage(message, registerScreen);
    }


    private void controllPlay() throws Exception {
         // set a message
        Message message = new Message();
        message.setType(Type.play);
        message.setUsername(player.getUsername());

        // send a message
        sendMessage(message, homeScreen);
    }


    private void controllResign() {
        ActionListener listener = new ActionListener(){
            public void actionPerformed(ActionEvent event){
                transit(playScreen, homeScreen, " 中断は敗北である！");
            }
        };
        Timer timer = new Timer(3000, listener);
        timer.setRepeats(false);
        timer.start();
    }


    private void controllAnalysis() {
        analysisScreen = new AnalysisScreen(this);
        transit(homeScreen, analysisScreen, " お主、なかなか強いの");
    }


    private void controllBack() {
        transit(analysisScreen, homeScreen, " 制限時間に注意だ");
    }


    private void controllPut(String command) {
        // command("i,j") to move[](i,j)
        String[] splited = command.split(",");
        int[] put = new int[2];
        for(int i = 0; i < 2; ++i)
            put[i] = Integer.parseInt(splited[i]);

         // set a message
        Message message = new Message();
        message.setType(Type.put);
        message.setPut(put);

        sendMessage(message, playScreen);

        // update border
        playScreen.updateBorder(put, player.getMyColor());
    }


    public void processMessage(Message message) {
        switch(message.getType()) {
            case login:
                processLogin(message);
                break;

            case register:
                processRegister(message);
                break;

            case play:
                processPlay(message);
                break;

            case put:
                processPut(message);
                break;

            default:
                break;
        }
    }


    private void processLogin(Message message) {
        // process
        if (message.getStatus() == Status.success) {
            // create player
            this.player =  new Player();
            player.setUsername(message.getUsername());

            // transition
            homeScreen = new HomeScreen(this);
            transit(loginScreen, homeScreen, " 時間制限に注意して戦いに挑め！");

        } else {
            loginScreen.showError(message);
        }
    }

    private void processRegister(Message message) {
        // process
        if (message.getStatus() == Status.success) {
            transit(registerScreen, loginScreen, " 登録できたらログインだ");
        } else {
            registerScreen.showError(message);
        }
    }

    private void processPlay(Message message) {
        // process
        if (message.getStatus() == Status.success) {
            String text = "";
            // player
            if (message.getOrder() == Order.first) {
                player.setMyColor(1);
                text = " 先手必勝！黒がお主の色だ";
            } else if (message.getOrder() == Order.passive) {
                player.setMyColor(2);
                text = " 虎視眈々！白がお主の色だ";
            }
            // play screen
            playScreen = new PlayScreen(this, player);
            transit(homeScreen, playScreen, text);
        }
            // change bgm
            /*
            clip.stop();
            clip.flush();
            clip.setFramePosition(0);
            clip1 = BaseScreen.createClip(new File("sounds/last-war.wav"));
            clip1.loop(Clip.LOOP_CONTINUOUSLY);
            */
    }


    private void processPut(Message message) {
       if(message.getStatus() == Status.success) {
           playScreen.updateBorder(message.getPut(), 3 - player.getMyColor());
       }
    }


    private void transit(BaseScreen fromScreen, BaseScreen toScreen, String text) {
        // set bounds
        toScreen.setText(text);
        Rectangle rectangle = fromScreen.getBounds();
        toScreen.setBounds((int)(rectangle.getX()), (int)(rectangle.getY()), (int)(rectangle.getWidth()), (int)(rectangle.getHeight()));

        // transit
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                toScreen.setVisible(true);
                fromScreen.setVisible(false);
            }
        });
    }

    private void sendMessage(Message message, BaseScreen errorScreen) {
        String err2 = client.sendMessage(message);
        if(!err2.equals("")) {
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    errorScreen.setText(err2);
                }
            });
            return;
        }
    }
}

