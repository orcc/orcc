package net.sf.orcc.tools;

import net.sf.orcc.OrccException;
import net.sf.orcc.debug.model.OrccProcess;

public interface ActorAnalyzer {

	void analyzeFunctionalUnit(OrccProcess process, String functionalUnit, String vtlFolder)
			throws OrccException;

	/**
	 * Sets the output folder of this analyzer. This is the folder where files
	 * will be generated.
	 * 
	 * @param outputFolder
	 *            output folder
	 */
	public void setOutputFolder(String outputFolder);

}
