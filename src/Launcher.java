import server.Server;
import utils.Messages;
import java.io.IOException;
import java.util.logging.Logger;

public class Launcher {

    public static void main(String[] args) {
        Server server = null;

        try {
            int port = args.length != 0 ? Integer.parseInt(args[0]) : Messages.DEFAULT_PORT;

            server = new Server(port);
            server.listen();

        } catch (IOException ex) {
            server.broadcast("SERVER", "INTERNAL SERVER ERROR.");
            Logger.getGlobal().severe(ex.getMessage());
        } catch (NumberFormatException ex) {
            Logger.getGlobal().warning(ex.getMessage());
        }
    }
}
