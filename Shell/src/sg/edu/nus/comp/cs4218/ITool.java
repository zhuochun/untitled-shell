package sg.edu.nus.comp.cs4218;

import java.io.File;

public interface ITool {
	String execute(File workingDir, String stdin);
	int getStatusCode();
}
