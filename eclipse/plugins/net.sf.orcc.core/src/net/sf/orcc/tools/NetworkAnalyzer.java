package net.sf.orcc.tools;

import java.util.List;

import net.sf.orcc.OrccException;
import net.sf.orcc.debug.model.OrccProcess;

public interface NetworkAnalyzer {
	/**
	 * Analyzes the VTL by loading IR files, transforming actors and printing
	 * them.
	 * 
	 * @param process
	 *            the process that launched the back-end, so we can report
	 *            messages to it
	 * @param vtlFolders
	 *            absolute path of folders that contains IR files
	 * @throws OrccException
	 *             if something goes wrong
	 */
	public void analyzeVTL(OrccProcess process, List<String> vtlFolders)
			throws OrccException;

	/**
	 * Loads a hierarchical XDF network and analyze it. Analyze may include
	 * instantiation of the network, flattening it, transforming it, analyze it
	 * and printing the result, or a subset of these steps.
	 * 
	 * @param process
	 *            the process that launched the analyzer, so we can report
	 *            messages to it
	 * @param inputFile
	 *            absolute path of top-level input network
	 * @throws OrccException
	 *             if something goes wrong
	 */
	public void analyzeXDF(OrccProcess process, String inputFile) throws OrccException;

	/**
	 * Sets the output folder of this back-end. This is the folder where files
	 * will be generated.
	 * 
	 * @param outputFolder
	 *            output folder
	 */
	public void setOutputFolder(String outputFolder);
}
