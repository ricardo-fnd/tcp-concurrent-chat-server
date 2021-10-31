import commands.Command;
import commands.Executable;
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
            send(Messages.AVAILABLE_COMMANDS);

            server.broadcast(name + Messages.ENTERED_CHAT);

            while (!clientSocket.isClosed()) {
                listen();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void listen() throws IOException {
        String message = bReader.readLine();

        if(message == null) {
            closeConnection();
            return;
        }

        if(!message.startsWith(Messages.COMMAND_IDENTIFIER)) {
            server.broadcast(message);
            return;
        }

        Executable command = Command.getCommand(message);
        command.execute(server, this, message);
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
    }

    private boolean isValid(String username) {
        return !username.trim().isEmpty();
    }

    public void closeConnection(){
        try {
            server.removeClient(this);
            clientSocket.close();
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public void send(String message) {
        pWriter.print(message);
        pWriter.flush();
    }

    public String getName() {
        return name;
    }
}
