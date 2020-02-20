package commands;

import java.io.IOException;

public interface Command {
	String execute() throws IOException;
	String getName();
}
