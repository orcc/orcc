package net.sf.orcc.oj;

public class CLIParameters {
	
	private String[] args;
	
	private static final CLIParameters instance = new CLIParameters();

	private CLIParameters() {	
	}
	
	public static CLIParameters getInstance() {
		return instance;
	}
	
	public void setArguments(String[] args) {
		this.args = args;
	}
	
	public String getSourceFile() {
		return args[0];
	}

}
