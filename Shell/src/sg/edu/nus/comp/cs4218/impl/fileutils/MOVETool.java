package sg.edu.nus.comp.cs4218.impl.fileutils;

import java.io.File;

import sg.edu.nus.comp.cs4218.impl.ATool;
import sg.edu.nus.comp.cs4218.impl.PathUtils;
import sg.edu.nus.comp.cs4218.fileutils.IMoveTool;
import sg.edu.nus.comp.cs4218.impl.ArgList;

public class MOVETool extends ATool implements IMoveTool {

	private ArgList argList = new ArgList();
	private String currentDir;

	/**
	 * Constructor of the MOVETool class
	 * @param arguments
	 *        the string array that being parsed into the console
	 */
	public MOVETool(String[] arguments) {
		super(arguments);
		
		currentDir = System.getProperty("user.dir");
	}
/**
 * This boolean function is to check whether the move file is valid
 * 
 * @param origin the original file location
 * @param copy the destination file location
 * @return true if the move file is valid otherwise false
 */
	@Override
	public boolean move(File origin, File copy) {
		boolean result = false;
		File newDir = copy;
		
		if (origin.equals(copy)) {
			result = true;
		} else {
			if (copy.isDirectory()) {
				newDir = new File(copy.toString() + "/" + origin.getName());
			}
			
			result = origin.renameTo(newDir);
		}
		
		return result;
	}
/**
 * This function is to execute the MOVETool methods and get the output
 * @param workingDir the workingDir of the file
 * @param stdin the standard input string
 * Use {@link #argList} to get the arguments being parsed and check the length of the arguments
 * @return output string according to difference conditions
 * @exception IllegalArgumentException
 */
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
			String originPath = PathUtils.pathResolver(currentDir, argList.getParam(0));
			String copyPath = PathUtils.pathResolver(currentDir, argList.getParam(1));
			
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
			if (!copyFile.toString().equals("/") && !new File(copyFile.getParent()).exists()) {
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
