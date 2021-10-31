package commands;

import utils.Messages;

public enum Command {
    COMMANDS("/commands", new CommandExecutor()),
    LIST("/list", new ListExecutor()),
    WHISPER("/whisper", new WhisperExecutor()),
    QUIT("/quit", new QuitExecutor()),
    INVALID("", new InvalidExecutor());

    private String name;
    private Executable commandExecutable;

    Command(String name, Executable commandHandler) {
        this.name = name;
        this.commandExecutable =  commandHandler;
    }

    public static Executable getCommand(String message) {
        String commandMessage = message.split(" ")[0];

        for (Command command : values()) {
            if (command.getName().equals(commandMessage)) {
                return command.getCommandExecutable();
            }
        }

        return INVALID.getCommandExecutable();
    }

    public static String getAllAvailableCommands(){
        StringBuilder commands = new StringBuilder();
        commands.append("Commands available:" + Messages.ONE_LINE_RETURN);

        for (Command command : values()) {
            if(command == INVALID){
                continue;
            }
            commands.append(command.getName()).append(Messages.ONE_LINE_RETURN);
        }

        return commands.toString();
    }

    public Executable getCommandExecutable() {
        return commandExecutable;
    }

    public String getName() {
        return name;
    }
}
