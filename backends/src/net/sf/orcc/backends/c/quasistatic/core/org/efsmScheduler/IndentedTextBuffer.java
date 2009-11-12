package net.sf.orcc.backends.c.quasistatic.core.org.efsmScheduler;

/**
 * 
 * @author vimartin
 */
public class IndentedTextBuffer {
	
	public void add(String str) {
		s.append(str);
	}

	public void addLine(String line) {
		addIndent();
		s.append(line);
		s.append("\n");
	}
	
	public void indent() {
		indentLevel += 1;
	}
	
	public void unindent() {
		if (indentLevel > 0) {
			indentLevel -= 1;
		}
	}
	
	public String getString() {
		return s.toString();
	}
	
	public void reset() {
		s = new StringBuffer();
		indentLevel = 0;
	}
	
	 
	
	private void addIndent() {
		for (int i = 0; i < indentLevel; i++)
			s.append(indentString);
	}
	
	private StringBuffer s = new StringBuffer();
	private int indentLevel = 0;
	private String indentString = "  ";

}
