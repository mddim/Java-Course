package bg.sofia.uni.fmi.mjt.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class ClientHandler extends Thread {

    private Socket socket;
    private String clientName;
    private final ArrayList<ClientHandler> onlineClients;
    private PrintWriter out = null;
    private BufferedReader in = null;
    private final static int SPLIT_BY = 3;

    public ClientHandler(Socket socket, ArrayList<ClientHandler> onlineClients) {

        this.socket = socket;
        this.onlineClients = onlineClients;

    }

    public String getClientName() {
        return clientName;
    }

    @Override
    public void run() {

        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {

                //out.println("Please choose a command: nick, send, send-all, list-users, disconnect");
                String line = in.readLine();
                //System.out.println("Message received from client: " + line);
                String command = line.split(" ")[0];

                switch (command) {

                    case "nick":

                        String name = line.split(" ")[1];
                        registerClient(name);

                        break;

                    case "send":

                        sendPrivateMessage(line, clientName);

                        break;

                    case "send-all":

                        sendMessageToAll(line, clientName);

                        break;

                    case "list-users":

                        listUsers();

                        break;

                    case "disconnect":

                        disconnect();
                        break;

                }

            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private void registerClient(String name) throws IOException {

        if (!onlineClients.isEmpty()) {
            synchronized (this) {
                for (ClientHandler client : onlineClients) {
                    if (client.getClientName() != null) {
                        if (client.getClientName().equals(name)) {
                            out.println("Username is taken. Please select again! Write username only.");
                            name = in.readLine();
                            break;
                        }
                    }
                }
            }
        }

        for (ClientHandler client : onlineClients) {
            if (client == this) {
                clientName = name;
                out.println("Username set to " + clientName);
                break;
            }
        }

        System.out.println(onlineClients.size());

    }

    private void sendPrivateMessage(String line, String name) {
        String[] words = line.split(" ", SPLIT_BY);
        String receiverName = words[1].trim();
        String message = words[2];

        if (message != null) {

            if (!receiverName.equals("")) {

                for (ClientHandler client : onlineClients) {

                    System.out.println(client.getClientName());
                    if (client != null && client != this && client.getClientName() != null
                            && client.getClientName().equals(receiverName)) {

                        client.out.println("[" + LocalDateTime.now().withNano(0) + "] " + name + ": " + message);

                        out.println("Message sent to " + receiverName);

                        break;
                    }
                }
            }
        }
    }

    private void sendMessageToAll(String line, String name) {
        String message = line.split(" ", 2)[1];

        synchronized (this) {

            for (ClientHandler client : onlineClients) {

                if (client != null && client.getClientName() != null &&
                        !client.getClientName().equals(this.getClientName())) {

                    client.out.println("[" + LocalDateTime.now().withNano(0) + "] " + name + ": " + message);

                }
            }

            this.out.println("Broadcast message sent successfully.");
            System.out.println("Broadcast message sent by " + this.clientName);
        }
    }

    private void listUsers() {
        for (ClientHandler client : onlineClients) {

            this.out.println(client.getClientName());

        }
    }

    private void disconnect() {

        System.out.println(clientName + " disconnected.");
        onlineClients.remove(this);
        out.println("Disconnected");

    }

}
