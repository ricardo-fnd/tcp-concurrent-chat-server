package server;

import utils.Date;
import utils.Messages;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class Server {

    private ServerSocket serverSocket;
    private ExecutorService executors;
    private Map<String, ClientConnection> clientsList;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        clientsList = new ConcurrentHashMap<>();
        executors = Executors.newCachedThreadPool();

        Logger.getGlobal().info(Messages.SERVER_INITIALIZED + port);
    }

    public void listen() throws IOException {
        while (true) {
            Socket clientSocket = serverSocket.accept();
            Logger.getGlobal().info(Messages.CLIENT_CONNECTED + clientSocket.getInetAddress().getHostAddress());

            ClientConnection client = new ClientConnection(clientSocket, this);
            executors.submit(client);
        }
    }

    public ClientConnection getClientByUsername(String username) {
        return clientsList.get(username);
    }

    public Set<String> getAllClients() {
        return clientsList.keySet();
    }

    public boolean addClient(ClientConnection client, String username) {
        synchronized (clientsList) {
            if (clientsList.keySet().contains(username)) {
                return false;
            }
            clientsList.put(username, client);
            Logger.getGlobal().info(clientsList.size() + " clients chatting.");
            return true;
        }
    }

    public void removeClient(ClientConnection client) {
        synchronized (clientsList) {
            clientsList.remove(client.getName());
        }
    }

    public void broadcast(String sender, String message) {
        String date = Date.getDateAndTime();

        synchronized (clientsList) {
            for (ClientConnection client : clientsList.values()) {
                client.send(date + sender + ": " + message);
            }
        }
    }

    public void closeConnection(ClientConnection client) {
        removeClient(client);
        client.close();
    }
}
