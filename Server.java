
import java.io.*;
import java.net.*;

public class Server{

	public static final int ECHO_PORT = 8765;
	
	public static void main(String[] args) {
		  ServerSocket serverSocket = null;
		  ThreadController threadcontroller = new ThreadController();
	
		   try {
			      serverSocket = new ServerSocket();
			      serverSocket.bind(new InetSocketAddress("127.0.0.1",ECHO_PORT));
			      
			      
    while (true) {
        Socket socket = serverSocket.accept();
       
        new PlayerThread(socket,threadcontroller).start();
      }
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (serverSocket != null) {
          serverSocket.close();
        }
      } catch (IOException e) {}
    }
  }
  
 }
 
