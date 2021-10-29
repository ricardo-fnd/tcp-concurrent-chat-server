import utils.Messages;

import java.io.*;
import java.net.Socket;

public class ClientConnection implements Runnable {

    private PrintWriter pWriter;
    private BufferedReader bReader;

    public ClientConnection(Socket clientSocket) throws IOException {
        bReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        pWriter = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);
    }

    @Override
    public void run() {
        send(Messages.WELCOME);
    }

    public void send(String message) {
        pWriter.println(message);
    }
}
