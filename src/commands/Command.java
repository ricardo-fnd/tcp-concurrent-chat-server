package commands;

public abstract class Command implements Executable {

    protected String[] keywords;

    public Command(String[] keywords) {
        this.keywords = keywords;
    }
}
