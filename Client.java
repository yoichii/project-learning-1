import java.io.*;
import java.net.*;

class Client {
    private Socket socket = null;
    private ObjectOutputStream out = null;
    private ObjectInputStream in = null;
    private Controller controller = null;


    public Client(Controller controller) {
        this.controller = controller;
    }


    public String establishConnection() {
        try {
            if (socket == null) {
                // client -> server : socket
                socket = new Socket("127.0.0.1", 8765);
            }
            if(out == null) {
                // client -> server : outputStream
                out = new ObjectOutputStream(socket.getOutputStream());
            }
            if(in == null) {
                // client <- server : inputStream
                in = new ObjectInputStream(socket.getInputStream());
            }

            (new ReceiveThread()).start();

        } catch (UnknownHostException uhe) {
            socket=null;
            out=null;
            in=null;
            return "サーバが見つからないよ";
        } catch (IOException ioe) {
            socket=null;
            out=null;
            in=null;
            return "接続に失敗したよ";
        }
               return "";
    }


    public String sendMessage(Message message) {
        try {
            if (out != null) {
                // client -> server
                out.writeObject(message);
            } else {
                return "接続されてないよ";
            }
        } catch (IOException ioe) {
            return "送信できなかったよ";
        }
        return "";
    }


    public void close() {
        try {
            if (socket != null)
                socket.close();
            if (out != null)
                out.close();
            if (in != null)
                in.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    class ReceiveThread extends Thread {

        public void run() {
            Message received;

            try {
                while(true) {
                    if (in != null) {
                        // client <- server´
                        received = (Message)(in.readObject());
                    } else {
                        received = new Message();
                        received.setType(Type.none);
                        received.setStatus(Status.nullObject);
                    }

                    // process
                    controller.processMessage(received);
                }
            } catch (ClassNotFoundException nfe) {
                received = new Message();
                received.setType(Type.none);
                received.setStatus(Status.classNotFoundException);
            } catch (IOException ioe) {
                received = new Message();
                received.setType(Type.none);
                received.setStatus(Status.ioException);
            }

        }
    }

}
