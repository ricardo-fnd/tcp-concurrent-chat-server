package utils;

public class Messages {

    public static final int DEFAULT_PORT = 4141;

    // carriage return related
    public static final String ONE_LINE_RETURN = "\r\n";
    public static final String DOUBLE_LINE_RETURN = "\r\r\n\n";

    // server related
    public static final String SERVER_INITIALIZED = "Server initialized with port ";
    public static final String CLIENT_CONNECTED = "Client connected from ";

    // client related
    public static final String WELCOME = ONE_LINE_RETURN + ".:: Welcome to tcp-chat Server ::." + DOUBLE_LINE_RETURN;
    public static final String CHOOSE_USERNAME = "Choose a username: ";
    public static final String NAME_IN_USE = "Name is already in use, try again.";
    public static final String ENTERED_CHAT = " has entered the chat.";
    public static final String LEFT_CHAT = " has left the chat.";
    public static final String CHOOSE_VALID_NAME = "You must choose a valid username.";
    public static final String WHISPER = "(whisper): ";
    public static final String SELF_WHISPER = "SERVER: Whispering yourself is not allowed.";
    public static final String USERNAME_NOT_FOUND = "SERVER: Username not found.";

    // commands related
    public static final String COMMAND_IDENTIFIER = "/";
    public static final String CLIENTS_LIST = "Clients connected:" + ONE_LINE_RETURN;
    public static final String INVALID_COMMAND = " is an invalid command.";
    public static final String AVAILABLE_COMMANDS = ONE_LINE_RETURN + "Type /commands to get a list of all available commands." + ONE_LINE_RETURN;
    public static final String WHISPER_USAGE = "Usage: /whisper <username> <message>";

}
