import utils.Messages;

import java.io.*;
import java.net.Socket;

public class ClientConnection implements Runnable {

    private Server server;
    private Socket clientSocket;
    private BufferedReader bReader;
    private PrintWriter pWriter;
    private String name;

    public ClientConnection(Socket clientSocket, Server server) throws IOException {
        this.server = server;
        this.clientSocket = clientSocket;
        bReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        pWriter = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
    }

    @Override
    public void run() {
        send(Messages.WELCOME);

        try {
            chooseName();

            while (clientSocket.isBound()) {
                listen();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private void listen() throws IOException {
        String message = bReader.readLine();
        if(message == null) {
            server.removeClient(this);
            clientSocket.close();
            return;
        }
        server.broadcast(message);
    }

    private void chooseName() throws IOException {
        send(Messages.CHOOSE_USERNAME);
        String username = bReader.readLine();

        if (!isValid(username)) {
            send(Messages.CHOOSE_VALID_NAME);
            chooseName();
            return;
        }

        if (!server.addClient(this, username)) {
            send(Messages.NAME_IN_USE);
            chooseName();
            return;
        }
        name = username;
        server.broadcast(username + Messages.ENTERED_CHAT);
    }

    private boolean isValid(String username) {
        return !username.trim().isEmpty();
    }

    public void send(String message) {
        pWriter.print(message);
        pWriter.flush();
    }

    public String getName() {
        return name;
    }
}
