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
        String err2 = client.sendMessage(message);
        if(!err2.equals("")) {
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    loginScreen.setText(err2);
                }
            });
            return;
        }

    }


    private void controllRegister() {
        if(registerScreen == null)
            registerScreen = new RegisterScreen(this);
        registerScreen.setText(" 新入り！　よろしく頼もう");
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                registerScreen.setVisible(true);
                loginScreen.setVisible(false);
            }
        });
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
        String err2 = client.sendMessage(message);
        if(!err2.equals("")) {
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    registerScreen.setText(err2);
                }
            });
            return;
        }
    }


    private void controllPlay() {
         // set a message
        Message message = new Message();
        message.setType(Type.play);
        message.setUsername(player.getUsername());

        // send a message
        String err2 = client.sendMessage(message);
        if(!err2.equals("")) {
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    homeScreen.setText(err2);
                }
            });
            return;
        }

           }


    private void controllResign() {
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


    private void controllAnalysis() {
        analysisScreen = new AnalysisScreen(this);
        analysisScreen.setText(" 君は強いぞ！");
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                analysisScreen.setVisible(true);
                homeScreen.setVisible(false);
            }
        });
    }


    private void controllBack() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                homeScreen.setVisible(true);
                analysisScreen.setVisible(false);
            }
        });
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
            homeScreen.setText(" 時間制限に注意して戦いに挑め！");
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    homeScreen.setVisible(true);
                    loginScreen.setVisible(false);
                }
            });
        } else {
            loginScreen.showError(message);
        }
    }

    private void processRegister(Message message) {
        // process
        if (message.getStatus() == Status.success) {
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    loginScreen.setVisible(true);
                    registerScreen.setVisible(false);
                }
            });
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
            playScreen.setText(text);
            // transit
            EventQueue.invokeLater(new Runnable() {
                public void run() {
                    playScreen.setVisible(true);
                    homeScreen.setVisible(false);
                }
            });

        } else {
            homeScreen.showError(message);
        }
    }


    private void processPut(Message message) {
       if(message.getStatus() == Status.success) {
           playScreen.updateBorder(message.getPut(), 3 - player.getMyColor());
       }
    }
}

