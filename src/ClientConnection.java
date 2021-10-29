import utils.Messages;

import java.io.*;
import java.net.Socket;

public class ClientConnection implements Runnable {

    private Server server;
    private BufferedReader bReader;
    private PrintWriter pWriter;
    private String clientNamme;

    public ClientConnection(Socket clientSocket, Server server) throws IOException {
        this.server = server;
        bReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        pWriter = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
    }

    @Override
    public void run() {
        send(Messages.WELCOME);

        try {
            chooseName();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    private void chooseName() throws IOException {
        send(Messages.CHOOSE_USERNAME);
        String username = bReader.readLine();

        if(!isValid(username)) {
            send(Messages.CHOOSE_VALID_NAME);
            chooseName();
            return;
        }

        if(!server.addClient(this, username)) {
            send(Messages.NAME_IN_USE);
            chooseName();
            return;
        }
        clientNamme = username;
    }

    private boolean isValid(String username) {
        return !username.trim().isEmpty();
    }

    public void send(String message) {
        pWriter.print(message);
        pWriter.flush();
    }
}
