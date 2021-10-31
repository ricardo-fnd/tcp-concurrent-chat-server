package commands;

import server.ClientConnection;
import server.Server;
import utils.Messages;

public class ListExecutor implements Executable {

    @Override
    public void execute(Server server, ClientConnection client, String message) {
        StringBuilder list = new StringBuilder();

        list.append(Messages.ONE_LINE_RETURN + Messages.CLIENTS_LIST);
        for (String username : server.getAllClients()) {
            list.append(username).append(Messages.ONE_LINE_RETURN);
        }

        list.append(Messages.ONE_LINE_RETURN);
        client.send(list.toString());
    }
}
