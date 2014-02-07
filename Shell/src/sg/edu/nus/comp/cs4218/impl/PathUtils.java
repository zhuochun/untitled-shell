package sg.edu.nus.comp.cs4218.impl;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class PathUtils {

	public static String PathResolver(String currentPath, String newPath) {
		String finalAbsolutePath;
		
		if (newPath.equals("~")) {
			newPath = System.getProperty("user.home") + "/";
		} else
		if (newPath.startsWith("~/")) {
			newPath = System.getProperty("user.home").
							 concat('/' + newPath.substring(2));
		}
		
		Path curPath = Paths.get(currentPath).resolve(newPath).normalize();
		
		finalAbsolutePath = curPath.toString();
		
		// if new path is a directory exist, put "/" behind
		if (curPath.toFile().exists() && curPath.toFile().isDirectory()) {
			if (!finalAbsolutePath.endsWith("/")) {
				finalAbsolutePath = finalAbsolutePath + "/";
			}
		}
		
		// if new path does not exist but should be a directory, put a "/"
		// behind
		if (newPath.endsWith("/") && !finalAbsolutePath.endsWith("/")) {
			finalAbsolutePath = finalAbsolutePath + "/";
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
