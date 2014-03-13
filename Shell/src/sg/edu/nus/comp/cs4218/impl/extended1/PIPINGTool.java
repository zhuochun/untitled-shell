package sg.edu.nus.comp.cs4218.impl.extended1;

import java.io.File;
import java.util.Arrays;

import sg.edu.nus.comp.cs4218.ITool;
import sg.edu.nus.comp.cs4218.extended1.IPipingTool;
import sg.edu.nus.comp.cs4218.impl.ATool;
import sg.edu.nus.comp.cs4218.impl.CommandInterpreter;

public class PIPINGTool extends ATool implements IPipingTool {
	
	private File workingDir;
	private int startIdx;
	private int endIdx;
	
	public PIPINGTool(String[] arguments) {
		super(arguments);
	}

	@Override
	public String pipe(ITool from, ITool to) {
		String stdout = from.execute(workingDir, "");
		
		if (from.getStatusCode() != 0) {
			setStatusCode(from.getStatusCode());
			return stdout;
		}
		
		stdout = to.execute(workingDir, stdout);

		if (to.getStatusCode() != 0) {
			setStatusCode(to.getStatusCode());
			return stdout;
		}
		
		return stdout;
	}

	@Override
	public String pipe(String stdout, ITool to) {
		String newStdout = to.execute(workingDir, stdout);

		if (to.getStatusCode() != 0) {
			setStatusCode(to.getStatusCode());
		}
		
		return newStdout;
	}

	@Override
	public String execute(File workingDir, String stdin) {
		this.workingDir = workingDir;

		endIdx = -1;
		
		String stdout = null;
		
		try {
			ITool fromTool = getITool();
			ITool toTool = getITool();

			stdout = pipe(fromTool, toTool);

			if (getStatusCode() != 0) {
				return stdout;
			}
		} catch (RuntimeException e) {
			return e.getMessage();
		}
		
		// any more tools in pipe?
		while (endIdx < args.length) {
			try {
				ITool nextTool = getITool();

				stdout = pipe(stdout, nextTool);

				if (getStatusCode() != 0) {
					return stdout;
				}
			} catch (RuntimeException e) {
				return e.getMessage();
			}
		}
		
		return stdout;
	}
	
	private ITool getITool() {
		startIdx = endIdx + 1;
		endIdx = readToPipeOrEnd(startIdx);
		
		if (startIdx == endIdx) {
			setStatusCode(8);
			throw new RuntimeException("Error: Parse Error Near '|'");
		}
		
		ITool tool = CommandInterpreter.cmdToITool(args[startIdx], Arrays.copyOfRange(args, startIdx + 1, endIdx));
		
		if (tool == null) {
			setStatusCode(7);
			throw new RuntimeException("Error: Command Not Found '" + args[startIdx] + "'");
		}
		
		return tool;
	}
	
	private int readToPipeOrEnd(int startIdx) {
		int i = startIdx;

		for (; i < args.length; i++) {
			if (args[i].equals("|")) {
				return i;
			}
		}
		
		return i;
	}
}
