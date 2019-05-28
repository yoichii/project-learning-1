import projectlearning1.*;
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
    private AnalysisScreen analysisScreen = null;
    private PlayScreen playScreen = null;

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
                loginScreen.setText("よく来た！");
                loginScreen.setVisible(true);
            }
        });

        //controller.clip = BaseScreen.createClip(new File("sounds/preparation.wav"));
      	//controller.clip.loop(Clip.LOOP_CONTINUOUSLY);
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
        } else if(command.equals("back-login")) {
            transit(registerScreen, loginScreen, "制限時間に注意して戦え！");
        } else {
            controllPut(command);
        }

    }

    private void controllLogin() {

        if (client == null) {
            client = new Client(this);
        }

        if (client.shouldEstablishConnection) {
            // establish connection
            String err = client.establishConnection();
            if (!err.equals("")) {
                loginScreen.setText(err);
                return;
            }
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
        if(registerScreen == null) {
            registerScreen = new RegisterScreen(this, loginScreen.getBounds());
        }
        transit(loginScreen, registerScreen, "新入り！　これからよろしく！");
    }


    private void controllRegisterSend() {
        if (client == null) {
            client = new Client(this);
        }

        if (client.shouldEstablishConnection) {
            // establish connection
            String err = client.establishConnection();
            if (!err.equals("")) {
                registerScreen.setText(err);
                return;
            }
        }

        // set a message
        Message message = new Message();
        message.setType(Type.register);
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


    void controllResign() {
        // set a message
        Message message = new Message();
        message.setType(Type.resign);
        message.setTotalPieces(playScreen.getTotalPieces());

        // send a message
        sendMessage(message, playScreen);
    }


    private void controllAnalysis() {
        // set a message
        Message message = new Message();
        message.setType(Type.analysis);

        // send a message
        sendMessage(message, homeScreen);

    }


    private void controllBack() {
        transit(analysisScreen, homeScreen, "制限時間に注意して戦え！");
    }


    private void controllPut(String command) {
        // command("i,j,l,m") to move[](i,j), totalPieces[](l,m)
        String[] splited = command.split(",");
        int[] put = new int[2];
        int[] totalPieces = new int[2];

        for(int i = 0; i < 2; ++i)
            put[i] = Integer.parseInt(splited[i]);

         // set a message
        Message message = new Message();
        message.setType(Type.put);
        message.setPut(put);
        message.setTotalPieces(playScreen.getTotalPieces());

        System.out.println("send: "+command);
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

            case analysis:
                processAnalysis(message);
                break;

            case finish:
                processFinish(message);
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
            player.setmyPoint(message.getMyPoint());

            // transition
            homeScreen = new HomeScreen(this, player);
            transit(loginScreen, homeScreen, "時間制限に注意して戦え！");

        } else {
            loginScreen.showError(message);
        }
    }

    private void processRegister(Message message) {
        // process
        if (message.getStatus() == Status.success) {
            transit(registerScreen, loginScreen, "登録できたらログインだ");
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
                text = "先手必勝！黒がお主の色だ";
            } else if (message.getOrder() == Order.passive) {
                player.setMyColor(2);
                text = "後手有利！白がお主の色だ！";
            }
            player.setOpponentname(message.getOpponentname());

            playScreen = new PlayScreen(this, player, homeScreen.getBounds());

            // play screen
            transit(homeScreen, playScreen, text);
        }

        /*
        // change bgm
        clip.stop();
        clip.flush();
        clip.setFramePosition(0);
        clip1 = BaseScreen.createClip(new File("sounds/last-war.wav"));
        clip1.loop(Clip.LOOP_CONTINUOUSLY);
        */
    }


    private void processPut(Message message) {
       if(message.getStatus() == Status.success) {

           System.out.println("receive: "+String.valueOf(message.getPut()[0])+String.valueOf(message.getPut()[1]));

            boolean pass = playScreen.updateBorder(message.getPut(), 3 - player.getMyColor());

            if(pass) {
                playScreen.setText("置く場所がない！相手の番だ！");
                
                ActionListener listener = new ActionListener(){
                    public void actionPerformed(ActionEvent event){
                        controllPut("-1,-1");
                    }
                };
                Timer timer = new Timer(1000, listener);
                timer.setRepeats(false);
                timer.start();

            }
       }
    }

    private void processAnalysis(Message message) {
        if(message.getStatus() == Status.success) {
            analysisScreen = new AnalysisScreen(this, message.getHistory(), homeScreen.getBounds(), player);
            transit(homeScreen, analysisScreen, "お主、なかなか強いの");
        }
    }

    private void processFinish(Message message) {
        String result = "";

        if(message.getStatus() == Status.success) {
            if(message.getResult() == Result.win) {
                result = "お主の勝ちだ！よくやった！";
            } else if(message.getResult() == Result.lose) {
                    result = "お主の負けだ！無念！";
            } else if(message.getResult() == Result.draw) {
                result = "引き分けだ！善戦だ！";
            }

            playScreen.stopTimer();
            playScreen.setText(result);

            ActionListener listener = new ActionListener(){
                public void actionPerformed(ActionEvent event){
                    transit(playScreen, homeScreen, "次の戦を楽しむんだ！");
                }
            };
            Timer timer = new Timer(3000, listener);
            timer.setRepeats(false);
            timer.start();
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

