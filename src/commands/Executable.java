package commands;

import server.ClientConnection;
import server.Server;

public interface Executable {

    void execute(Server server, ClientConnection client, String message);
}
