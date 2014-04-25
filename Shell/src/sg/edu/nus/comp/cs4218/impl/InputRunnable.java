package sg.edu.nus.comp.cs4218.impl;

import java.util.Scanner;

public class InputRunnable implements Runnable {
	
	private Thread thread;
	private Scanner scanner;
	private String input;

	/**
	 * initialize an input runnable
	 */
	public InputRunnable() {
		thread = new Thread(this);
		scanner = new Scanner(System.in);
		input = null;
	}

	/**
	 * run loop on scanning inputs from stdin
	 */
	@Override
	public void run() {
		while (true) {
			set(scanner.nextLine());
		}
	}
	
	/**
	 * start the thread
	 */
	public void start() {
		thread.start();
	}
	
	/**
	 * set the last input line
	 * 
	 * @param line
	 */
	private synchronized void set(String line) {
		input = line;
	}
	
	/**
	 * get the last input line
	 * 
	 * @return last line
	 */
	public synchronized String get() {
		String in = input;
		input = null;
		return in;
	}
	
}
