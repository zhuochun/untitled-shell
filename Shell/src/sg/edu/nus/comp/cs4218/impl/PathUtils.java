package sg.edu.nus.comp.cs4218.impl;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class deals with path issues.
 */
public class PathUtils {

	/**
	 * This functions takes in the current working path and the new path, and
	 * will return the resolved path. If the path represents a possible 
	 * directory, the returned path will have a '/' at behind. Any duplicate
	 * '/' will be resolved to only one '/'. E.g. "adsf///asdf//sd/s/" will be
	 * resolved as "adsf/asdf/sd/s/", and "aa/bb" will be resolved as "aa/bb"
	 * if "bb" does not exist or is a file under aa, if "bb" exists and is a
	 * directory, "aa/bb/" will be returned. Further more, "/" is not allowed
	 * in a file/folder name.
	 * 
	 * @param currentPath
	 * @param newPath
	 * @return
	 */
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
	
	public static Path GetRandomSubpath(Path path) {
		int depth = path.getNameCount();
		// TODO this will cause problem, check depth - 2 > 0 first !!!
		int newDepth = 2 + (int)(Math.random() * 1000000) % (depth - 2);
		
		return Paths.get("/" + path.subpath(0, newDepth).toString());
	}
}
