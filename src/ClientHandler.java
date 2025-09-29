import java.io.*;
import java.net.*;
import java.util.*;

public class ClientHandler implements Runnable{
    private static List<ClientHandler> clientHandlers = Collections.synchronizedList(new ArrayList<>());
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    private String clientName;

    public ClientHandler(Socket socket){
        this.clientSocket = socket;
        try{
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Get client name as input and add to the list
            //out.println("Enter your name: ");
            this.clientName = in.readLine();
            synchronized (clientHandlers){
                clientHandlers.add(this);
            }
            ClientHandler.broadcast("🔔 " + clientName + " has joined the chat!");

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void run(){
        String message;
        try {
            while ((message = in.readLine()) != null){
                ClientHandler.broadcast("💬 " + clientName + ": " + message);
            }
        }catch (IOException e){
            System.out.println("Client disconnected: " + clientName);
        } finally {
            try {
                synchronized (clientHandlers){
                    clientHandlers.remove(this);
                }
                clientSocket.close();
                ClientHandler.broadcast("👋 " + clientName + " has left the chat.");
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private static void broadcast(String message){
        synchronized (clientHandlers){
            for (ClientHandler handler : clientHandlers){
                handler.out.println(message);
            }
        }
    }
}
