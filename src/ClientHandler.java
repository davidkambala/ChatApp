import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;

    public ClientHandler(Socket socket){
        this.clientSocket = socket;
        try{
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void run(){
        try {
            String message;
            while ((message = in.readLine()) != null){
                System.out.println("📨 Received from client: " + message);
                out.println("Echo from server: " + message);
            }
            clientSocket.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
