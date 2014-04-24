package sg.edu.nus.comp.cs4218.impl.extended2;

import java.io.File;
import java.io.IOException;

import sg.edu.nus.comp.cs4218.extended2.ICommTool;
import sg.edu.nus.comp.cs4218.impl.ATool;
import sg.edu.nus.comp.cs4218.impl.ArgList;
import sg.edu.nus.comp.cs4218.impl.FileUtils;
import sg.edu.nus.comp.cs4218.impl.PathUtils;
import sg.edu.nus.comp.cs4218.impl.ArgList.Option;

public class COMMTool extends ATool implements ICommTool {

	private ArgList argList = new ArgList();
	private String[] prevLine;
	
	private boolean isSorted(String curLine, int fileIndex) {
		boolean sortStatus;
		
		if (prevLine[fileIndex] == null) {
			prevLine[fileIndex] = curLine;
			
			sortStatus = true; 
		} else {
			sortStatus = prevLine[fileIndex].compareTo(curLine) <= 0;
		}
		
		return sortStatus;
	}
	
	private void flushRestOfFile(String[] lines, int curFile,
								 int curLineParam, boolean checkSorted,
								 boolean continueAfterUnsorted,
								 StringBuilder result) {
		boolean sorted = true;
		int curLine = curLineParam;
		
		while (curLine < lines.length) {
			if (checkSorted) {
				if (!sorted || !isSorted(lines[curLine], curFile)) {
					if (sorted) {
						result.append(String.format("comm: File %d is not in sorted order \n", 
									  curFile + 1));
						sorted = false;
					}
				}
			}
			
			if (sorted || continueAfterUnsorted) {
				if (curFile == 1) {
					result.append("\t");
				}
				
				result.append(lines[curLine]);
				result.append("\n");
				
				prevLine[curFile] = lines[curLine];
				curLine ++;
			} else {
				break;
			}
		}
	}
	
	private boolean updateSortedStatus(boolean checkSorted, boolean isSorted,
									   String line, int fileIndex,
									   StringBuilder result) {
		boolean updatedSortedStatus = true;
		
		if (checkSorted) {
			if (!isSorted(line, fileIndex)) {
				if (isSorted) {
					result.append(String.format("comm: File %d is not in sorted order \n", fileIndex + 1));
					updatedSortedStatus = false;
				}
			}
		}
		
		return updatedSortedStatus;
	}
	
	private boolean continueToNexPos(boolean isSorted, boolean continueAfterUnsorted,
									 String preced, String line,
									 StringBuilder result, int fileIndex) {
		if (isSorted || continueAfterUnsorted) {
			result.append(preced + line);
			result.append("\n");
			
			prevLine[fileIndex & 1] = line;
			prevLine[(fileIndex >> 1) | (fileIndex & 1)] = line;
			
			return true;
		}
		
		return false;
	}
	
	private String compareFilesGeneric(String input1,
									 String input2,
									 boolean checkSorted,
									 boolean continueAfterUnsorted) throws IOException, RuntimeException {
		StringBuilder result = new StringBuilder();
		String[] linesA = FileUtils.readFileLines(new File(input1));
		String[] linesB = FileUtils.readFileLines(new File(input2));
		
		prevLine = new String[2];
		
		boolean sortedA, sortedB;
		int curPosA, curPosB;
		
		sortedA = sortedB = true;
		curPosA = curPosB = 0;
		
		while (curPosA < linesA.length && curPosB < linesB.length) {
			// while we still can do comparing, output consecutive strings unique to file 1
			while ((sortedA && sortedB || continueAfterUnsorted) && 
					curPosA < linesA.length && curPosB < linesB.length &&
				    linesA[curPosA].compareTo(linesB[curPosB]) < 0) {
				
				sortedA = updateSortedStatus(checkSorted, sortedA, linesA[curPosA], 0, result);
				
				if (continueToNexPos(sortedA, continueAfterUnsorted, "", linesA[curPosA], result, 0)) {
					curPosA ++;
				}
			}
			
			// if file A out of order and we don't want to continue,
			// (flush the rest of file B -> this is not required) and break the routine
			if (checkSorted && !sortedA && !continueAfterUnsorted) {
				break;
			}
			
			// while we still can do comparing, output consecutive strings unique to file 2
			while ((sortedA && sortedB || continueAfterUnsorted) &&
					curPosA < linesA.length && curPosB < linesB.length &&
				    linesA[curPosA].compareTo(linesB[curPosB]) > 0) {
				
				sortedB = updateSortedStatus(checkSorted, sortedB, linesB[curPosB], 1, result);
				
				if (continueToNexPos(sortedB, continueAfterUnsorted, "\t", linesB[curPosB], result, 1)) {
					curPosB ++;
				}
			}
			
			// if file B out of order and we don't want to continue, flush
			// (the rest of file A-> this is not required) and break the routine
			if (checkSorted && !sortedB && !continueAfterUnsorted) {
				break;
			}
			
			// consecutive strings common to both files
			while ((sortedA && sortedB || continueAfterUnsorted) &&
					curPosA < linesA.length && curPosB < linesB.length &&
					linesA[curPosA].compareTo(linesB[curPosB]) == 0) {
				
				sortedA = updateSortedStatus(checkSorted, sortedA, linesA[curPosA], 0, result);
				sortedB = updateSortedStatus(checkSorted, sortedB, linesB[curPosB], 1, result);
				
				if (continueToNexPos(sortedA && sortedB, continueAfterUnsorted, "\t\t", linesA[curPosA], result, 2)) {
					curPosA ++;
					curPosB ++;
				}
			}
		}
		
		// if both of the files are still sorted, we run out of at least one
		// of the file. We need to flush out the rest of the other file if we
		// still have remainings of the other file.
		if (sortedA && sortedB || continueAfterUnsorted) {
			// NOTE: two conditions below will not satisfy together
			
			// if we still have remaining lines in file A, flush them out
			if (curPosA < linesA.length) {
				flushRestOfFile(linesA, 0, curPosA, checkSorted, continueAfterUnsorted, result);
			}
			
			// if we still have remaining lines in file B, flush them out
			if (curPosB < linesB.length) {
				flushRestOfFile(linesB, 1, curPosB, checkSorted, continueAfterUnsorted, result);
			}
		}
		
		return result.toString();
	}
	
	public COMMTool(String[] arguments) {
		super(arguments);
		
		argList.invalidOptionCheck = true;
		
		argList.registerAcceptableOption("c", 
						"Check that the input is correctly sorted.");
		argList.registerAcceptableOption("d",
						"Do not check that the input is correctly sorted.");
		argList.registerAcceptableOption("help",
						"Brief information about supported options.");
	}

	@Override
	public String compareFiles(String input1, String input2) {
		String result = "";
		
		try {
			result = compareFilesGeneric(input1, input2, true, true);
		} catch (IOException e) {
			setStatusCode(9);
			result = e.getMessage() + "\n";
		} catch (RuntimeException e) {
			setStatusCode(9);
			result = e.getMessage() + "\n";
		}
		
		return result;
	}

	@Override
	public String compareFilesCheckSortStatus(String input1, String input2) {
		String result = "";
		
		try {
			result = compareFilesGeneric(input1, input2, true, false);
		} catch (IOException e) {
			setStatusCode(9);
			result = e.getMessage() + "\n";
		} catch (RuntimeException e) {
			setStatusCode(9);
			result = e.getMessage() + "\n";
		}
		
		return result;
	}

	@Override
	public String compareFilesDoNotCheckSortStatus(String input1, String input2) {
		String result = "";
		
		try {
			result = compareFilesGeneric(input1, input2, false, true);
		} catch (IOException e) {
			setStatusCode(9);
			result = e.getMessage() + "\n";
		} catch (RuntimeException e) {
			setStatusCode(9);
			result = e.getMessage() + "\n";
		}
		
		return result;
	}

	@Override
	public String getHelp() {
		StringBuilder help = new StringBuilder();

		help.append("Command Format - cut [OPTIONS] [FILE]\n");
		help.append("FILE - Name of the file, when no file is present (denoted by \"-\") use standard input\n");
		help.append("OPTIONS\n");

		for (Option opt : argList.getAcceptableOptions()) {
			help.append("  " + opt.toString() + "\n");
		}

		return help.toString();
	}

	@Override
	public String execute(File workingDir, String stdin) {
		// by default we assume the routine executed successfully
		setStatusCode(0);
		
		// parse arguments
		try {
			argList.parseArgs(this.args);
		} catch (IllegalArgumentException e) {
			setStatusCode(9);
			return e.getMessage() + "\n" + getHelp();
		}

		// help option?
		if (argList.hasOptions() && argList.getOption(0).equals("help")) {
			setStatusCode(0);
			return getHelp();
		}
		
		// command does not have options and parameters
		if (!argList.hasOptions() && !argList.hasParams()) {
			setStatusCode(9);
			return getHelp();
		}
		
		// if both -c -d appears, throw exception
		if (argList.hasOption("c") && argList.hasOption("d")) {
			setStatusCode(9);
			return "Error: More than one option.\n" + getHelp();
		}
		
		String[] filePaths = argList.getParams();
		String fileAPath, fileBPath;
		String result;
		
		if (filePaths.length != 2) {
			result = "Error: No file is specified!\n" + getHelp();
			setStatusCode(9);
		} else {
			fileAPath = PathUtils.pathResolver(workingDir, filePaths[0]);
			fileBPath = PathUtils.pathResolver(workingDir, filePaths[1]);
			
			// process input
			if (argList.hasOption("c")) {
				result = compareFilesCheckSortStatus(fileAPath, fileBPath);
			} else
			if (argList.hasOption("d")) {
				result = compareFilesDoNotCheckSortStatus(fileAPath, fileBPath);
			} else {
				result = compareFiles(fileAPath, fileBPath);
			}
		}
		
		return result;
	}

}
