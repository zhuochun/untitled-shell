package sg.edu.nus.comp.cs4218.impl;

import sg.edu.nus.comp.cs4218.ITool;
import sg.edu.nus.comp.cs4218.impl.fileutils.CDTool;

public class ToolRunnable implements Runnable {
	
	private Thread thread;
	private ITool tool;
	private String stdin;
	private boolean stopped;
	private boolean finished;

	public ToolRunnable(ITool tool, String stdin) {
		this.thread = new Thread(this);
		this.tool = tool;
		this.stdin = stdin;
		this.stopped = false;
		this.finished = false;
	}

	@Override
	public void run() {
		String stdout = tool.execute(Directory.get(), stdin);
		
		if (!stopped) {
			if (tool.getClass().equals(CDTool.class)) {
				if (tool.getStatusCode() == 0) {
					Directory.set(stdout);
				} else {
					System.err.println(stdout);
				}
			} else {
				if (tool.getStatusCode() == 0) {
					System.out.println(stdout);
				} else {
					System.err.println(stdout);
				}
			}
		}
		
		finished = true;
	}
	
	public void start() {
		thread.start();
	}
	
	public synchronized void stop() {
		stopped = true;
		thread.stop();
	}
	
	public boolean isAlive() {
		return thread.isAlive();
	}
	
	public boolean isFinished() {
		return finished;
	}

}