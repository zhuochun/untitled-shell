package sg.edu.nus.comp.cs4218.fileutils;

import java.io.File;

import sg.edu.nus.comp.cs4218.ITool;

/**
 * cat copies each file or standard input ( denoted by ‘-’) 
 * if no files are given to the standard output
 */
public interface ICatTool extends ITool {
	String getStringForFile(File toRead);
}
