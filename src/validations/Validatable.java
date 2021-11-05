package validations;

import server.ClientConnection;
import server.Server;

public interface Validatable {

    boolean isValid(Server server, ClientConnection client, String message);
}
