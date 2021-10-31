package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private ServerSocket serverSocket;
    private ExecutorService executors;
    private Map<String, ClientConnection> clientsList;

    public Server(int port) throws IOException {
        serverSocket = new ServerSocket(port);
    }

    public void listen() throws IOException {
        clientsList = new ConcurrentHashMap<>();
        executors = Executors.newCachedThreadPool();

        while (true) {
            Socket clientSocket = serverSocket.accept();

            ClientConnection client = new ClientConnection(clientSocket, this);
            executors.submit(client);
        }
    }

    public Set<String> getAllClients(){
        return clientsList.keySet();
    }

    public boolean addClient(ClientConnection client, String username) {
        if (clientsList.keySet().contains(username)) {
            return false;
        }
        clientsList.put(username, client);
        return true;
    }

    public void removeClient(ClientConnection client) {
        synchronized (clientsList) {
            clientsList.remove(client.getName());
        }
    }

    public void broadcast(String message) {
        synchronized (clientsList) {
            for (ClientConnection client : clientsList.values()) {
                client.send(message);
            }
        }
    }
}
