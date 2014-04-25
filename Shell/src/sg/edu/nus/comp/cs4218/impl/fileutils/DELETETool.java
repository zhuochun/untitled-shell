package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;

import sg.edu.nus.comp.cs4218.fileutils.IDeleteTool;
import sg.edu.nus.comp.cs4218.impl.ATool;
import sg.edu.nus.comp.cs4218.impl.ArgList;
import sg.edu.nus.comp.cs4218.impl.PathUtils;

/**
 * delete - delete the chosen file or chosen empty directory
 *
 * delete [file / empty directory]
 * 

 * If the file or directory cannot be found, or the directory is not empty, an error message will be returned and an
 * error code will be set.
 */

public class DELETETool extends ATool implements IDeleteTool {

	private ArgList argList = new ArgList();
	private String lastError;

	public DELETETool(String[] arguments) {
		super(arguments);

		argList.invalidOptionCheck = true;
	}

	@Override
	/*
	 * This function is used to delete the given file or the directory if it is empty
	 * @param toDelete is the input file or directory
	 * @return the operation information successful or not
	 */
	public boolean delete(File toDelete) {
		boolean result = false;

		try {
			if (toDelete.isDirectory()) {
				setStatusCode(7);
				lastError = "Error: " + toDelete.getName() + " is a directory";
			} else {
				Files.delete(toDelete.toPath());
				result = true;
			}
		} catch (NoSuchFileException e) {
			setStatusCode(7);
			lastError = "Error: " + toDelete.getName() + " does not exists";
		} catch (IOException e) {
			setStatusCode(7);
			lastError = "Error: failed to delete";
		} catch (SecurityException e) {
			setStatusCode(7);
			lastError = "Error: no permission to access";
		}

		return result;
	}

	@Override
	/*
	 * This function is used to execute and call the respective methods in delete command
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

		if (argList.getParams().length >= 1) {
			File toDelete = new File(PathUtils.pathResolver(workingDir,
					argList.getParam(0)));

			boolean success = delete(toDelete);

			if (success) {
				return "";
			} else {
				return lastError;
			}
		} else {
			setStatusCode(9);
			return "Error: no file specified";
		}
	}

}
