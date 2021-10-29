import utils.Messages;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
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

    public boolean addClient(ClientConnection client, String username) {
        if (clientsList.keySet().contains(username)) {
            return false;
        }
        clientsList.put(username, client);
        return true;
    }
}
