package sg.edu.nus.comp.cs4218.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathUtils {

	public static String PathResolver(String currentPath, String newPath) {
		// TODO: Implement ~ !!
		String finalAbsolutePath;
		
		Path curPath = Paths.get(currentPath).resolve(newPath);
		
		try {
			finalAbsolutePath = curPath.toRealPath().toString();
		} catch (NoSuchFileException noSuchFile) {
			finalAbsolutePath = null;
		} catch (IOException e) {
			finalAbsolutePath = null;
		}
		
		return finalAbsolutePath;
	}
	
	public static String PathResolver(File currentPath, String newPath) {
		return PathResolver(currentPath.toString(), newPath);
	}

	public static String PathResolver(File currentPath, File newPath) {
		return PathResolver(currentPath, newPath.toString());
	}

	public static String PathResolver(String currentPath, File newPath) {
		return PathResolver(currentPath, newPath.toString());
	}

	public static String GetLastElementOfPath(String path) {
		return Paths.get(path).getFileName().toString();
	}
	
	public static String GetLastElementOfPath(File path) {
		return GetLastElementOfPath(path.toString());
	}
}
