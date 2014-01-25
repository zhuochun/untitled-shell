package sg.edu.nus.comp.cs4218.fileutils;

import java.io.File;
import java.util.List;

import sg.edu.nus.comp.cs4218.ITool;

/**
 * list the contents of a directory	
 */
public interface ILsTool extends ITool {
	List<File> getFiles(File directory);
	String getStringForFiles(List<File> files);
}
