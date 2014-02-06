package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;

import sg.edu.nus.comp.cs4218.impl.ArgList;
import sg.edu.nus.comp.cs4218.fileutils.ICdTool;
import sg.edu.nus.comp.cs4218.impl.ATool;
import sg.edu.nus.comp.cs4218.impl.PathUtils;;

/**
 * cd - change current working directory
 *
 * cd [new working directory]
 * 
 * new working directory = '/', '~', '..', path
 * 
 * If the directory cannot be found, an error message will be returned and an
 * error code will be set.
 */
public class CDTool extends ATool implements ICdTool {
	
	private ArgList argList = new ArgList();
	
	public CDTool(String[] arguments) {
		super(arguments);
	}
	
	@Override
	public File changeDirectory(String newDirectory) {
		return new File(newDirectory);
	}

	@Override
	public String execute(File workingDir, String stdin) {
		String output;
		
		// make sure stdin exists
		if (stdin == null) { stdin = ""; }
		
		//TODO: at this moment we assume no stdin. confirm with others
		// how to parse stdin!!!
		
		// parse arguments
		try {
			argList.parseArgs(this.args);
		} catch (IllegalArgumentException e) {
			setStatusCode(9);
			return e.getMessage();
		}
		
		// if no error occurs, the first argument should be the path we want
		// to change to
		
		//TODO: confirm with Zhuochun if argList.getParam(0) return the first
		// param!!!!
		String newDir = PathUtils.PathResolver(workingDir,
											   argList.getParam(0));
		
		// if the new directory is null, the input file does not exist
		if (newDir == null) {
			//TODO: now the status code is some random number, confirm again!
			setStatusCode(9);
			output = "Directory does not exisit!";
		} else {
			// now we should check if the path is essentially a file
			if (new File(newDir).isFile()) {
				//TODO: now the status code is some random number, confirm
				//again!
				setStatusCode(9);
				output = PathUtils.GetLastElementOfPath(newDir) + "is a file!";
			} else {
				// everything is fine, we can change shell's default directory
				workingDir = changeDirectory(newDir);
				// in this case, user will see the new directory in the shell
				// when he executes the next command, so no explicit output is
				// needed
				output = "";
			}
		}

		return output;
	}
}
