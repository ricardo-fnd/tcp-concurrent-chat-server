package commands;

import utils.Messages;

public enum CommandType {
    COMMANDS(new Commands()),
    LIST(new ClientsList()),
    WHISPER(new Whisper()),
    QUIT(new Quit()),
    INVALID(new Invalid());

    private Command command;

    CommandType(Command commandHandler) {
        this.command = commandHandler;
    }

    public static Executable getCommand(String message) {
        String chosenCommand = message.split(" ")[0];

        for (CommandType commandType : values()) {
            String[] keywords = commandType.command.keywords;

            for (String keyword : keywords) {
                if (keyword.equals(chosenCommand)) {
                    return commandType.command;
                }
            }
        }

        return INVALID.command;
    }

    public static String getAllAvailableCommands() {
        StringBuilder commands = new StringBuilder();
        commands.append("Commands available:" + Messages.ONE_LINE_RETURN);

        for (CommandType commandType : values()) {
            if (commandType == INVALID) {
                continue;
            }

            String[] keywords = commandType.command.keywords;
            for (String keyword : keywords) {
                commands.append(keyword).append(" ");
            }
            commands.append(Messages.ONE_LINE_RETURN);
        }

        return commands.toString();
    }
}