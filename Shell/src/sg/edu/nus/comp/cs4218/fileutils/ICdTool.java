package sg.edu.nus.comp.cs4218.fileutils;

import java.io.File;

import sg.edu.nus.comp.cs4218.ITool;

/**
 * change directory
 *
 */
public interface ICdTool extends ITool {
	File changeDirectory(String newDirectory);
}
