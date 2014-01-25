package sg.edu.nus.comp.cs4218;

public interface IShell {
	
	/**
	 * Parses the commandline and instantiates the corresponding tool.
	 * @param commandline
	 * @return
	 */
	public ITool parse(String commandline);
	
	/**
	 * Executes the tool, starts a new thread, and returns the thread handle.
	 * @param tool
	 * @return
	 */
	public Runnable execute(ITool tool);
	
	/**
	 * Called upon Ctrl+C
	 * @param toolExecution
	 */
	public void stop(Runnable toolExecution);
}
