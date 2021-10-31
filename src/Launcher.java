import server.Server;
import utils.Messages;
import java.io.IOException;

public class Launcher {

    public static void main(String[] args) {
        try {
            int port = args.length != 0 ? Integer.parseInt(args[0]) : Messages.DEFAULT_PORT;

            Server server = new Server(port);
            server.listen();

        } catch (IOException ex) {
            System.out.println("INTERNAL SERVER ERROR");
            System.out.println(ex.getMessage());
        } catch (NumberFormatException ex) {
            System.out.println("Usage: java -jar TcpChat.jar <port>");
        }
    }
}
