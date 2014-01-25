package sg.edu.nus.comp.cs4218.fileutils;

import java.io.File;

import sg.edu.nus.comp.cs4218.ITool;

/**
 * copy a file to a given location
 *
 */
public interface ICopyTool extends ITool {
	boolean copy(File from, File to);
}
