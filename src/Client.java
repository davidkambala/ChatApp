import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        final String HOST = "localhost";
        final int PORT = 12345;

        try (Socket socket = new Socket(HOST, PORT)){
            BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            System.out.println("Connected to server. Enter your name:");
            String userInput;

            while((userInput = input.readLine()) != null){
                out.println(userInput);
                String response = in.readLine();
                System.out.println("Server says: " + response);
            }
            socket.close();

        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
