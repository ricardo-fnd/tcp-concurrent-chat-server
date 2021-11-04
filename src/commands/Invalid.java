package commands;

import server.ClientConnection;
import server.Server;
import utils.Messages;

public class Invalid extends Command {

    public Invalid() {
        super(new String[]{""});
    }

    @Override
    public void execute(Server server, ClientConnection client, String message) {
        String command = message.split(" ")[0];
        client.send(command + Messages.INVALID_COMMAND);
    }
}
