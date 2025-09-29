import java.io.*;
import java.net.*;
public class Server {
    public static void main(String[] args) {
        final int PORT = 12345;

        try (ServerSocket serverSocket = new ServerSocket(PORT)){
            System.out.println("🔌 Multi-client server started on port " + PORT);

            while (true){
                Socket clientSocket = serverSocket.accept();
                System.out.println("✅ New client connected: " + clientSocket.getInetAddress());

                ClientHandler handler = new ClientHandler(clientSocket);
                Thread thread = new Thread(handler);
                thread.start();
            }

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
