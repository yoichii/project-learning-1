import java.io.*;
import java.net.*;
import java.util.*;

class Server {

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        ObjectInputStream in = null;
        ObjectOutputStream out = null;

        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress("127.0.0.1",8765));

            // accept client message
            socket = serverSocket.accept();

            // client -> server : inputStream
            in = new ObjectInputStream(socket.getInputStream());
            // client <- server : outputStream
            out = new ObjectOutputStream(socket.getOutputStream());

            Message received = null;
            while ( (received = (Message)(in.readObject())) != null ) {

                // log
                System.out.println(socket.getRemoteSocketAddress()+" --> "+received.getType()+", "+received.getUsername()+", "+received.getPassword());

                // if bye, disconnect
                if (received.getUsername().equals("bye") || received.getPassword().equals("bye"))
                    break;

                // the message server want to send to client
                Message message = new Message();

                // empty username rejected
                if (received.getUsername().equals(""))
                    message.setStatus(Status.invalidUsername);
                // empty username rejected
                else if (received.getPassword().equals(""))
                    message.setStatus(Status.invalidPassword);
                else
                    message.setStatus(Status.success);

                // client <- server
                out.writeObject(message);

                // log
                System.out.println(socket.getRemoteSocketAddress()+" <-- "+message.getStatus());

            }
        } catch (Exception e) {
            System.out.println(socket.getRemoteSocketAddress() + " : disconected");
        } finally {
            try {

                if (socket != null)
                    socket.close();
                if (serverSocket != null)
                    serverSocket.close();
                if (in != null)
                    in.close();
                if (out != null)
                    out.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
