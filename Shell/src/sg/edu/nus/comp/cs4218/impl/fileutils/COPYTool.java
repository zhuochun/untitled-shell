package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;

import sg.edu.nus.comp.cs4218.fileutils.ICopyTool;
import sg.edu.nus.comp.cs4218.impl.ATool;
import sg.edu.nus.comp.cs4218.impl.ArgList;
import sg.edu.nus.comp.cs4218.impl.PathUtils;

public class COPYTool extends ATool implements ICopyTool {

	private ArgList argList = new ArgList();
	private String lastError;

	public COPYTool(String[] arguments) {
		super(arguments);

		argList.invalidOptionCheck = true;
	}

	@Override
	public boolean copy(File from, File to) {
		boolean result;

		try {
			if (from.isDirectory()) {
				result = false;

				setStatusCode(7);
				lastError = "Error: source is a directory";
			} else if (to.isDirectory()) {
				result = false;

				setStatusCode(7);
				lastError = "Error: target is a directory";
			} else {
				result = Files.copy(from.toPath(), to.toPath()) != null;
			}
		} catch (FileAlreadyExistsException e) {
			result = false;

			setStatusCode(7);
			lastError = "Error: target file already exists";
		} catch (IOException e) {
			result = false;

			setStatusCode(7);
			lastError = "Error: missing source or broken source";
		} catch (SecurityException e) {
			result = false;

			setStatusCode(6);
			lastError = "Error: no permission to access";
		}

		return result;
	}

	@Override
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

		File from = new File(PathUtils.PathResolver(workingDir,
				argList.getParam(0)));
		File to = new File(PathUtils.PathResolver(workingDir,
				argList.getParam(1)));

		boolean success = copy(from, to);

		if (success) {
			return "";
		} else {
			return lastError;
		}
	}
}
