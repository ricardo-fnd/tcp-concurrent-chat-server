package commands;

import server.ClientConnection;
import server.Server;
import utils.Messages;

public class CommandsList extends Command {

    public CommandsList() {
        super(new String[]{"/commands"});
    }

    @Override
    public void execute(Server server, ClientConnection client, String message) {
        message = Messages.ONE_LINE_RETURN + CommandType.getAllAvailableCommands();
        client.send(message);
    }
}
