package sg.edu.nus.comp.cs4218.impl.extended1;

import java.io.File;
import java.util.Arrays;

import sg.edu.nus.comp.cs4218.ITool;
import sg.edu.nus.comp.cs4218.extended1.IPipingTool;
import sg.edu.nus.comp.cs4218.impl.ATool;
import sg.edu.nus.comp.cs4218.impl.CommandInterpreter;

/**
 * The pipe tools allows the output of one program to 
 * be sent to the input of another program. With the 
 * help of pipe tool multiple small (and simple) programs
 * can be connected to accomplish large number of tasks.
 * 
 * Command Format - PROGRAM-1-STANDARD_OUTPUT | PROGRAM-2-STANDARD_INPUT
 * Where "|" is the pipe operator and PROGRAM-1-STANDARD_OUTPUT is the standard output of
 * program 1 and PROGRAM-2-STANDARD_INPUT is the standard input of program 2.
 *
 */
public class PIPINGTool extends ATool implements IPipingTool {
	
	private File workingDir;
	private int startIdx;
	private int endIdx;
	
	/**
	 * Initialize pipe tool
	 * 
	 * @param arguments
	 */
	public PIPINGTool(String[] arguments) {
		super(arguments);
	}

	/**
	 * Pipe the stdout of from to stdin of to
	 * 
	 * @param from ITool
	 * @param to ITool
	 * 
	 * @return The stdout of to
	 */
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

	/**
	 * Pipe the stdout as stdin to to
	 * 
	 * @param stdout String
	 * @param to ITool
	 * 
	 * @return The stdout of to
	 */
	@Override
	public String pipe(String stdout, ITool to) {
		String newStdout = to.execute(workingDir, stdout);

		if (to.getStatusCode() != 0) {
			setStatusCode(to.getStatusCode());
		}
		
		return newStdout;
	}

	/**
	 * Execute pipe tool
	 * 
	 * @param workingDir File
	 * @param stdin String
	 * 
	 * @return stdout from pipe tool
	 */
	@Override
	public String execute(File workingDir, String stdin) {
		this.workingDir = workingDir;
		this.endIdx = -1;
		
		String stdout = null;
		
		try {
			ITool fromTool = getITool();
			ITool toTool = getITool();

			stdout = pipe(fromTool, toTool);

			// exit if previous pipe runs to error
			if (getStatusCode() != 0) {
				return stdout;
			}

			// any more tools in pipe?
			while (endIdx < args.length) {
				ITool nextTool = getITool();
				stdout = pipe(stdout, nextTool);

				// exit if previous pipe runs to error
				if (getStatusCode() != 0) {
					return stdout;
				}
			}

			return stdout;
		} catch (IllegalArgumentException e) {
			return e.getMessage();
		}
	}
	
	/**
	 * Find the next ITool from the list of arguments
	 * 
	 * @return ITool
	 */
	private ITool getITool() {
		startIdx = endIdx + 1;
		endIdx = readToPipeOrEnd(startIdx);
		
		if (startIdx == endIdx) {
			setStatusCode(8);
			throw new IllegalArgumentException("Error: Parse Error Near '|'");
		}
		
		ITool tool = CommandInterpreter.cmdToITool(args[startIdx], Arrays.copyOfRange(args, startIdx + 1, endIdx));
		
		if (tool == null) {
			setStatusCode(7);
			throw new IllegalArgumentException("Error: Command Not Found '" + args[startIdx] + "'");
		}
		
		return tool;
	}
	
	/**
	 * Find the index of next pipe "|" in arguments from startIdx
	 * 
	 * @param startIdx int
	 * 
	 * @return the index of "|" or startIdx
	 */
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