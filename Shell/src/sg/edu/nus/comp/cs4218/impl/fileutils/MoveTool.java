package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;

import sg.edu.nus.comp.cs4218.impl.ATool;
import sg.edu.nus.comp.cs4218.impl.PathUtils;
import sg.edu.nus.comp.cs4218.fileutils.IMoveTool;
import sg.edu.nus.comp.cs4218.impl.ArgList;

public class MoveTool extends ATool implements IMoveTool {

	private ArgList argList = new ArgList();
	private String currentDir;

	public MoveTool(String[] arguments) {
		super(arguments);
		
		currentDir = System.getProperty("user.dir");
	}

	@Override
	public boolean move(File origin, File copy) {
		boolean result = false;
		
		if (origin.equals(copy)) {
			result = true;
		} else {
			result = origin.renameTo(copy);
		}
		
		return result;
	}

	@Override
	public String execute(File workingDir, String stdin) {
		String output;

		// at this moment we assume no stdin. confirm with others
		// how to parse stdin!!!

		// parse arguments
		try {
			argList.parseArgs(this.args);
		} catch (IllegalArgumentException e) {
			setStatusCode(9);
			return e.getMessage();
		}
		
		if (argList.getParams().length >= 2) {
			String originPath = PathUtils.PathResolver(currentDir, argList.getParam(0));
			String copyPath = PathUtils.PathResolver(currentDir, argList.getParam(1));
			
			File originFile = new File(originPath);
			File copyFile = new File(copyPath);
			
			if (!originFile.exists()) {
				setStatusCode(9);
				output = "No such file or directory!";
			} else
			if (!originFile.isFile()) {
				setStatusCode(9);
				output = "Origin is not a file!";
			} else 
			if (!new File(copyFile.getParent()).exists()) {
				setStatusCode(9);
				output = "No such file or directory!";
			} else {
				if (move(originFile, copyFile)) {
					setStatusCode(0);
					output = "";
				} else {
					setStatusCode(9);
					output = "File cannot be moved!";
				}
			}
		} else {
			setStatusCode(9);
			output = "Move Command need at least 2 params";
		}
		
		return output;
	}
}
