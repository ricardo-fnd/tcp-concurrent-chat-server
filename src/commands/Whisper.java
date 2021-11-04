package commands;

import server.ClientConnection;
import server.Server;
import utils.Date;
import utils.Messages;

import java.util.logging.Logger;

public class Whisper extends Command {

    public Whisper() {
        super(new String[]{"/w", "/whisper <username> <message>"});
    }

    @Override
    public void execute(Server server, ClientConnection client, String message) {
        String[] messageSplit = message.split(" ");

        if(messageSplit.length < 3) {
            client.send(Messages.WHISPER_USAGE);
            return;
        }

        ClientConnection sender = client;
        ClientConnection receiver = server.getClientByUsername(messageSplit[1]);

        if (receiver == null) {
            client.send(Messages.USERNAME_NOT_FOUND);
            return;
        }

        if (receiver == sender) {
           client.send(Messages.SELF_WHISPER);
           return;
        }

        StringBuilder whisper = new StringBuilder();
        for (int i = 2; i < messageSplit.length; i++) {
            whisper.append(messageSplit[i]).append(" ");
        }

        String date = Date.getDateAndTime();
        receiver.send(date + sender.getName() + Messages.WHISPER + whisper);
        Logger.getGlobal().info(sender.getName() + " whispered " + receiver.getName());
    }
}
