package validations;

import server.ClientConnection;
import server.Server;

public enum Validator {
    IS_NULL(new NullMessage()),
    IS_EMPTY(new EmptyMessage());

    private Validatable validator;

    Validator(Validatable validator) {
        this.validator = validator;
    }

    public static boolean isValid(Server server, ClientConnection client, String message) {
        for (int i = 0; i < values().length; i++) {
            Validatable validator = values()[i].validator;

            if (!validator.isValid(server, client, message)) {
                return false;
            }
        }
        return true;
    }
}
