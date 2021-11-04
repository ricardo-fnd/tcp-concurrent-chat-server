package commands;

import server.ClientConnection;
import server.Server;
import utils.Messages;

public class Quit extends Command {

    public Quit() {
        super(new String[]{"/q", "/quit"});
    }

    @Override
    public void execute(Server server, ClientConnection client, String message) {
        server.broadcast("SERVER", client.getName() + Messages.LEFT_CHAT);
        server.closeConnection(client);
    }
}
