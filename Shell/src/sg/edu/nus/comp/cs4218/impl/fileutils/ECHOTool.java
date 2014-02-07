package sg.edu.nus.comp.cs4218.impl.fileutils;



import java.io.File;
import java.util.Arrays;

import sg.edu.nus.comp.cs4218.fileutils.IEchoTool;
import sg.edu.nus.comp.cs4218.impl.ATool;
public class ECHOTool extends ATool implements IEchoTool{

	public ECHOTool(String[] arguments) {
		super(arguments);

		// remove first -
		if (args != null && args.length > 0 && args[0].equals("-")) {
			if (args.length > 1) {
				args = Arrays.copyOfRange(args, 1, args.length);
			} else {
				args = new String[0];
			}
		}
	}

	@Override
	public String echo(String toEcho) {
		return toEcho + "\n";
	}

	@Override
	public String execute(File workingDir, String stdin) {
		StringBuilder stdout = new StringBuilder();

		if (args != null && args.length > 0) {
			for (String arg : args) {
				stdout.append(arg);
				stdout.append(" ");
			}

			stdout.deleteCharAt(stdout.length() - 1); // remove last space
		}
		
		stdout.append("\n");
		
		return stdout.toString();
	}
}
