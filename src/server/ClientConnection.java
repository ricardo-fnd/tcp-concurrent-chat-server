package server;

import commands.CommandType;
import commands.Executable;
import utils.Date;
import utils.Messages;
import validations.Validator;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public class ClientConnection implements Runnable {

    private Server server;
    private Socket clientSocket;
    private BufferedReader bReader;
    private PrintWriter pWriter;
    private String name;
    private ClientConnection lastContact;

    public ClientConnection(Socket clientSocket, Server server) throws IOException {
        this.server = server;
        this.clientSocket = clientSocket;
        this.bReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.pWriter = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
    }

    @Override
    public void run() {
        send(Messages.WELCOME);

        try {
            chooseName();
            send(Messages.AVAILABLE_COMMANDS);

            server.broadcast("SERVER", name + Messages.ENTERED_CHAT);

            while (!clientSocket.isClosed()) {
                listen();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private void listen() throws IOException {
        String message = bReader.readLine().trim();

        if (!isValid(message)) {
            return;
        }

        if (!message.startsWith(Messages.COMMAND_IDENTIFIER)) {
            server.broadcast(name, message);
            return;
        }

        Executable command = CommandType.getCommand(message);
        command.execute(server, this, message);
    }

    private void chooseName() throws IOException {
        send(Messages.CHOOSE_USERNAME);
        String username = bReader.readLine();

        if (!isValid(username)) {
            send(Messages.CHOOSE_VALID_USERNAME);
            chooseName();
            return;
        }

        if (!server.addClient(this, username)) {
            send(Messages.NAME_IN_USE);
            chooseName();
            return;
        }
        name = username;
        Logger.getGlobal().info(clientSocket.getInetAddress().getHostAddress() + " is " + name);
    }

    private boolean isValid(String message) {
        return Validator.isValid(server, this, message);
    }

    public void send(String message) {
        pWriter.println(message);
        pWriter.flush();
    }

    public void whisper(ClientConnection receiver, String message) {
        String date = Date.getDateAndTime();
        receiver.send(date + name + Messages.WHISPER + message);

        receiver.setLastContact(this);
        this.setLastContact(receiver);
        Logger.getGlobal().info(name + " whispered " + receiver.getName());
    }

    public void close() {
        try {
            Logger.getGlobal().info(name + "(" + clientSocket.getInetAddress().getHostAddress() + ")" + Messages.LEFT_CHAT);
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setLastContact(ClientConnection client) {
        this.lastContact = client;
    }

    public ClientConnection getLastContact() {
        return lastContact;
    }

    public String getName() {
        return name;
    }
}
