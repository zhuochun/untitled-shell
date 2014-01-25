package sg.edu.nus.comp.cs4218.fileutils;

import java.io.File;

import sg.edu.nus.comp.cs4218.ITool;

public interface IMoveTool extends ITool {
	boolean move(File from, File to);
}
