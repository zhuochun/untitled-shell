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
								 int curLine, StringBuilder result) {
		while (curLine < lines.length && isSorted(lines[curLine], curFile)) {
			if (curFile == 1) {
				result.append("\t");
			}
			
			result.append(lines[curLine] + "\n");
			
			curLine ++;
		}
		
		if (curLine < lines.length) {
			result.append(String.format("comm: File %d is not in sorted order \n", 
										curFile + 1));
		}
	}
	
	private String compareFilesGeneric(String input1,
									 String input2,
									 boolean checkSorted,
									 boolean continueAfterUnsorted) throws IOException, RuntimeException {
		String[] linesA = FileUtils.readFileLines(new File(input1));
		String[] linesB = FileUtils.readFileLines(new File(input2));
		
		StringBuilder result = new StringBuilder();
		
		// see if current file is sorted, used to judge 
		boolean sortedA, sortedB;
		int i, j;
		
		prevLine = new String[2];
		
		sortedA = sortedB = true;
		i = j = 0;
		
		while (i < linesA.length && j < linesB.length) {
			// consecutive strings unique to file 1
			while (sortedA && i < linesA.length && 
				   linesA[i].compareTo(linesB[j]) < 0) {
				if (checkSorted) {
					if (!sortedA || !isSorted(linesA[i], 0)) {
						if (sortedA) {
							result.append("comm: File 1 is not in sorted order \n");
							sortedA = false;
						}
					}
				}
				
				if (sortedA || continueAfterUnsorted) {
					result.append(linesA[i]);
					result.append("\n");
					
					i ++;
				}
			}
			
			// if file A out of order, flush the rest of file B and break the
			// routine
			if (!sortedA && !continueAfterUnsorted) {
				flushRestOfFile(linesB, 1, j, result);
				break;
			}
			
			// consecutive strings unique to file 2
			while (sortedB && j < linesB.length &&
				   linesA[i].compareTo(linesB[j]) > 0) {
				if (checkSorted) {
					if (!sortedB || !isSorted(linesB[i], 1)) {
						if (sortedB) {
							result.append("comm: File 2 is not in sorted order \n");
							sortedB = false;
						}
					}
				}
				
				if (sortedB || continueAfterUnsorted) {
					result.append("\t" + linesB[j]);
					result.append("\n");
					
					j ++;
				}
			}
			
			// if file B out of order, flush the rest of file A and break the
			// routine
			if (!sortedB && !continueAfterUnsorted) {
				flushRestOfFile(linesA, 0, i, result);
				
				break;
			}
			
			// consecutive strings common to both files
			while (i < linesA.length && j < linesB.length &&
				   linesA[i].compareTo(linesB[j]) == 0) {
				if (checkSorted) {
					if (!sortedA || !isSorted(linesA[i], 0)) {
						if (sortedA) {
							result.append("comm: File 1 is not in sorted order \n");
							sortedA = false;
						}
					}
					
					if (!sortedB || !isSorted(linesB[i], 1)) {
						if (sortedB) {
							result.append("comm: File 2 is not in sorted order \n");
							sortedB = false;
						}
					}
				}
				
				if (sortedA && sortedB || continueAfterUnsorted) { 
					result.append("\t\t" + linesA[i]);
					result.append("\n");
					
					i ++;
					j ++;
				}
			}
		}
		
		// if both of the files are still sorted, we run out of at least one
		// of the file. We need to flush out the rest of the other file if we
		// still have remainings of the other file.
		if (sortedA && sortedB) {
			// NOTE: two conditions below will not satisfy together
			
			// if we still have remaining lines in file A, flush them out
			if (i < linesA.length) {
				flushRestOfFile(linesA, 0, i, result);
			}
			
			// if we still have remaining lines in file B, flush them out
			if (j < linesB.length) {
				flushRestOfFile(linesB, 0, j, result);
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
						"Brief information about supported options");
	}

	@Override
	public String compareFiles(String input1, String input2) {
		String result = "";
		
		try {
			result = compareFilesGeneric(input1, input2, true, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public String compareFilesCheckSortStatus(String input1, String input2) {
		String result = "";
		
		try {
			result = compareFilesGeneric(input1, input2, true, false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public String compareFilesDoNotCheckSortStatus(String input1, String input2) {
		String result = "";
		
		try {
			result = compareFilesGeneric(input1, input2, false, true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

		// remove the last trailing \n
		if (help.length() > 0) {
			help.deleteCharAt(help.length() - 1);
		}

		return help.toString();
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

		// help option?
		if (argList.hasOptions() && argList.getOption(0).equals("help")) {
			return getHelp();
		}
		
		// command does not have options and parameters
		if (!argList.hasOptions() && !argList.hasParams()) {
			return getHelp();
		}
		
		// if both -c -d appears, throw exception
		if (argList.hasOption("c") && argList.hasOption("d")) {
			throw new IllegalArgumentException("Option error!");
		}
		
		String[] filePaths = argList.getParams();
		String fileAPath, fileBPath;
		String result;
		
		if (filePaths.length != 2) {
			throw new IllegalArgumentException("Parameters in wrong format!");
		} else {
			try {
				fileAPath = PathUtils.PathResolver(workingDir, filePaths[0]);
				fileBPath = PathUtils.PathResolver(workingDir, filePaths[1]);
			} catch (RuntimeException e) {
				setStatusCode(2);
				return e.getMessage();
			}
			
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
