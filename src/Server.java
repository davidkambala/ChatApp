import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        final int PORT = 12345;

        try (ServerSocket serverSocket = new ServerSocket(PORT)){
            System.out.println("🔌 Server started on port " + PORT);
            Socket clientSocket = serverSocket.accept();
            System.out.println("✅ Client connected: " + clientSocket.getInetAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String message;
            while((message = in.readLine()) != null){
                System.out.println("📩 Received: " + message);
                out.println("Echo: " + message);
            }

            clientSocket.close();

        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
