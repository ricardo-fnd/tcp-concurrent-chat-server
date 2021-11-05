package validations;

import server.ClientConnection;
import server.Server;

public class EmptyMessage implements Validatable {

    @Override
    public boolean isValid(Server server, ClientConnection client, String message) {
        return !message.isEmpty();
    }
}
