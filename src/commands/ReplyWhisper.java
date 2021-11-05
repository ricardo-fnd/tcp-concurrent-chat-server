package commands;

import server.ClientConnection;
import server.Server;

public class ReplyWhisper extends Command{

    public ReplyWhisper() {
        super(new String[]{"/r", "/reply"});
    }

    @Override
    public void execute(Server server, ClientConnection client, String message) {
        ClientConnection lastContact = client.getLastContact();

        String[] messageSplit = message.split(" ");

        StringBuilder reply = new StringBuilder();
        for (int i = 1; i < messageSplit.length; i++) {
            reply.append(messageSplit[i]).append(" ");
        }

        client.whisper(lastContact, reply.toString());
    }
}
