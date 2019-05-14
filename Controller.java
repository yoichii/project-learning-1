import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

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
        } else {
            controllPut(command);
        }

    }

    void controllLogin() {

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
        String err2 = client.sendMessage(message);
        if(!err2.equals("")) {
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    loginScreen.setText(err2);
                }
            });
                return;
        }

        // receive a message
        Message received = client.receiveMessage();

        // process
        if (received.getStatus() == Status.success) {
            homeScreen = new HomeScreen(this);
            homeScreen.setText(" このオセロは制限時間があるから注意だ！");
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    homeScreen.setVisible(true);
                    loginScreen.setVisible(false);
                }
            });
        } else {
            loginScreen.showError(received);
        }
    }

    void controllRegister() {
        registerScreen = new RegisterScreen(this);
        registerScreen.setText(" ようこそ！これからよろしくね！");
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                registerScreen.setVisible(true);
                loginScreen.setVisible(false);
            }
        });
    }

    void controllRegisterSend() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                loginScreen.setVisible(true);
                registerScreen.setVisible(false);
            }
        });
    }


    void controllPlay() {
        this.player =  new Player();
        player.setMyColor(2);
        playScreen = new PlayScreen(this, player);
        playScreen.setText(" 君は後手だ！有利だぞ！");
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                playScreen.setVisible(true);
                homeScreen.setVisible(false);
            }
        });
    }

    void controllResign() {
        playScreen.setText("中断したから負けだよ！");

        ActionListener listener = new ActionListener(){
            public void actionPerformed(ActionEvent event){
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        homeScreen.setVisible(true);
                        playScreen.setVisible(false);
                    }
                });
            }
        };
        Timer timer = new Timer(3000, listener);
        timer.setRepeats(false);
        timer.start();
    }

    void controllAnalysis() {
        analysisScreen = new AnalysisScreen(this);
        analysisScreen.setText(" 君は強いぞ！");
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                analysisScreen.setVisible(true);
                homeScreen.setVisible(false);
            }
        });
    }

    void controllBack() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                homeScreen.setVisible(true);
                analysisScreen.setVisible(false);
            }
        });
    }

    void controllPut(String command) {
        // command("i,j") to move[](i,j)
        String[] splited = command.split(",");
        int[] put = new int[2];
        for(int i = 0; i < 2; ++i)
            put[i] = Integer.parseInt(splited[i]);

         // set a message
        Message message = new Message();
        message.setType(Type.put);
        message.setPut(put);

        // send a message
        String err = client.sendMessage(message);
        if(!err.equals("")) {
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    playScreen.setText(err);
                }
            });
                return;
        }

        // update border
        playScreen.updateBorder(put);
    }

    void transit(String from, String to) {

    }
}

