package validations;

import server.ClientConnection;
import server.Server;
import utils.Messages;

public class NullMessage implements Validatable {

    @Override
    public boolean isValid(Server server, ClientConnection client, String message) {
        if (message == null) {
            server.closeConnection(client);
            server.broadcast("SERVER", client.getName() + Messages.LEFT_CHAT);
            return false;
        }
        return true;
    }
}
