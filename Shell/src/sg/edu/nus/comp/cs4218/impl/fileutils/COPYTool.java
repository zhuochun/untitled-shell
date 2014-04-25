package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;

import sg.edu.nus.comp.cs4218.fileutils.ICopyTool;
import sg.edu.nus.comp.cs4218.impl.ATool;
import sg.edu.nus.comp.cs4218.impl.ArgList;
import sg.edu.nus.comp.cs4218.impl.PathUtils;

/**
 * copy - copy the chosen file to a target location
 *
 * copy [file] [location]
 * 
 * If the file cannot be found or the chosen file is a directory or the location cannot be found, an error message will be returned and an
 * error code will be set.
 */

public class COPYTool extends ATool implements ICopyTool {

	private ArgList argList = new ArgList();
	private String lastError;

	public COPYTool(String[] arguments) {
		super(arguments);

		argList.invalidOptionCheck = true;
	}

	@Override
	/*
	 * This function is used to copy the chosen file to a given location
	 * @param from  is the chosen file
	 * @param to  the given target location
	 * @return the copy operation information, whether it is successful or not
	 */
	
	public boolean copy(File from, File to) {
		boolean result = false;
		File newDir = to;

		try {
			if (from.isDirectory()) {
				setStatusCode(7);
				lastError = "Error: " + from.getName() + " is a directory";
			} else {
				if (to.isDirectory()) {
					newDir = new File(to, from.getName());
				}

				result = Files.copy(from.toPath(), newDir.toPath()) != null;
			}
		} catch (FileAlreadyExistsException e) {
			setStatusCode(7);
			lastError = "Error: " + to.getName() + " already exists";
		} catch (IOException e) {
			setStatusCode(7);
			lastError = "Error: missing source or missing target path";
		} catch (SecurityException e) {
			setStatusCode(6);
			lastError = "Error: no permission to access";
		}

		return result;
	}

	@Override
	/*
	 * This function is used to execute and call the respective methods in copy command
	 * @param workingDir the directory of current working environment
	 * @param stdin the input command
	 * @return the execute result and information 
	 */
	public String execute(File workingDir, String stdin) {
		// parse arguments
		try {
			argList.parseArgs(this.args);
		} catch (IllegalArgumentException e) {
			setStatusCode(9);
			return e.getMessage();
		}

		if (argList.isEmpty() || argList.getParams().length < 2) {
			setStatusCode(9);
			return "Error: at least 2 parameters required";
		}

		File from = new File(PathUtils.pathResolver(workingDir,
				argList.getParam(0)));
		File to = new File(PathUtils.pathResolver(workingDir,
				argList.getParam(1)));

		boolean success = copy(from, to);

		if (success) {
			return "";
		} else {
			return lastError;
		}
	}
}
