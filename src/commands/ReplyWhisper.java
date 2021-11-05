package commands;

import server.ClientConnection;
import server.Server;
import utils.Messages;

public class ReplyWhisper extends Command{

    public ReplyWhisper() {
        super(new String[]{"/r", "/reply"});
    }

    @Override
    public void execute(Server server, ClientConnection client, String message) {
        ClientConnection lastContact = client.getLastContact();

        if (lastContact == null) {
            client.send(Messages.REPLY_ERROR);
            return;
        }

        String[] messageSplit = message.split(" ");

        StringBuilder reply = new StringBuilder();
        for (int i = 1; i < messageSplit.length; i++) {
            reply.append(messageSplit[i]).append(" ");
        }

        client.whisper(lastContact, reply.toString());
    }
}
