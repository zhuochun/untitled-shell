package sg.edu.nus.comp.cs4218.impl;

import java.util.Scanner;

public class InputRunnable implements Runnable {
	
	private Thread thread;
	private Scanner scanner;
	private String input;

	public InputRunnable() {
		thread = new Thread(this);
		scanner = new Scanner(System.in);
		input = null;
	}

	@Override
	public void run() {
		while (true) {
			set(scanner.nextLine());
		}
	}
	
	public void start() {
		thread.start();
	}
	
	private synchronized void set(String line) {
		input = line;
	}
	
	public synchronized String get() {
		String in = input;
		input = null;
		return in;
	}
	
}
