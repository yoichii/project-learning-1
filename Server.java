import java.net.*;
import java.io.*;

public class Server{

	public static final int ECHO_PORT = 8765;

	public static void main(String[] args) {
        ServerSocket serverSocket = null;
        ThreadController threadcontroller = new ThreadController();

       try {
              serverSocket = new ServerSocket(ECHO_PORT);
             // serverSocket.bind(new InetSocketAddress("192.168.1.6",ECHO_PORT));

            while (true) {
                Socket socket = serverSocket.accept();
                //System.out.println(socket);
                new PlayerThread(socket,threadcontroller).start();
            }
        } catch (IOException e) {
          e.printStackTrace();
        } finally {
            try {
                if (serverSocket != null) {
                  serverSocket.close();
                }
            } catch (IOException e) {
            }
        }
    }

}

