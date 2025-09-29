import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientGUI {

    private String name;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;

    private JFrame frame;
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;

    public ClientGUI(String serverHost, int serverPort){
        setupConnection(serverHost, serverPort);
        buildGUI();
        startMessageReceiver();
    }
    private void setupConnection(String host, int port){
        try{
            name = JOptionPane.showInputDialog(null, "Enter your name:", "Chat Login", JOptionPane.PLAIN_MESSAGE);

            socket = new Socket(host, port);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            out.println(name); // To send the name to the server

        }catch (IOException e){
            JOptionPane.showMessageDialog(null, "⚠\uFE0F Could not connect to server: " + e.getMessage());
            System.exit(1);
        }
    }

    private void buildGUI(){
        frame = new JFrame("Java Chat - " + name);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        inputField = new JTextField();
        sendButton = new JButton("Send");

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        frame.getContentPane().add(inputPanel, BorderLayout.SOUTH);

        sendButton.addActionListener(e -> sendMessage());
        inputField.addActionListener(e -> sendMessage());

        frame.setVisible(true);
    }

    private void sendMessage(){
        String message = inputField.getText();
        if(!message.isEmpty()){
            out.println(message);
            inputField.setText("");
        }
    }

    private void startMessageReceiver(){
        Thread receiveThread = new Thread(() ->{
            String message;
            try{
                while((message = in.readLine()) != null){
                    chatArea.append(message + "\n");
                }
            }catch (IOException e){
                chatArea.append("Connection closed.\n");
            }
        });
        receiveThread.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ClientGUI("localhost", 12345));
    }
}
