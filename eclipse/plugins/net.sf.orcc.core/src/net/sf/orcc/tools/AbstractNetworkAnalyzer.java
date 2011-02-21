package net.sf.orcc.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.debug.model.OrccProcess;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.serialize.XDFParser;
import net.sf.orcc.util.OrccUtil;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

public abstract class AbstractNetworkAnalyzer implements NetworkAnalyzer {

	/**
	 * 
	 * @param args
	 */
	public static void main(Class<? extends AbstractNetworkAnalyzer> clasz,
			String[] args) {
		if (args.length == 3) {
			String inputFile = args[0];
			List<String> vtlFolders = Arrays.asList(args[1]
					.split(File.pathSeparator));
			String outputFolder = args[2];

			try {
				AbstractNetworkAnalyzer analyzer = clasz.newInstance();
				analyzer.setOutputFolder(outputFolder);
				analyzer.setVtlFolders(vtlFolders);
				analyzer.analyzeXDF(null, inputFile);
			} catch (Exception e) {
				System.err.println("Could not print \"" + args[0] + "\"");
				e.printStackTrace();
			}
		} else {
			System.err.println("Usage: " + clasz.getSimpleName()
					+ " <input XDF network> <VTL folder> <output folder>");
		}
	}

	protected AbstractActorAnalyzer actorAnalyzer;

	protected Map<String, Object> analysis;

	/**
	 * Path where output files will be written.
	 */
	protected String path;

	/**
	 * the process that launched this analyzer
	 */
	private OrccProcess process;

	/**
	 * Path of the folder that contains VTL under IR form.
	 */
	private List<String> vtlFolders;

	public AbstractNetworkAnalyzer(AbstractActorAnalyzer actorAnalyzer) {
		this.actorAnalyzer = actorAnalyzer;
		this.analysis = new HashMap<String, Object>();
	}

	/**
	 * Analyze the given connection.
	 * 
	 * @param connection
	 *            the connection
	 */
	abstract protected void analyze(Connection connection) throws OrccException;

	/**
	 * Analyze the given network.
	 * 
	 * @param network
	 *            the network
	 */
	public void analyze(Network network) throws OrccException {
		for (Instance instance : network.getInstances()) {
			if (instance.isActor()) {
				actorAnalyzer.transform(instance.getActor());
				actorAnalyzer.analyze(instance.getActor());
			} else if (instance.isNetwork()) {
				analyze(instance.getNetwork());
			}
		}
		for (Connection connection : network.getConnections()) {
			analyze(connection);
		}
	}

	@Override
	final public void analyzeXDF(OrccProcess process, String inputFile)
			throws OrccException {
		this.process = process;

		// parses top network
		write("Parsing XDF network...\n");
		Network network = new XDFParser(inputFile).parseNetwork();
		network.updateIdentifiers();
		if (isCanceled()) {
			return;
		}

		write("Instantiating actors...\n");
		network.instantiate(vtlFolders);
		Network.clearActorPool();
		write("Instantiation done\n");

		if (isCanceled()) {
			return;
		}
		doXdfAnalyzer(network);
	}

	public void analyzeVTL(OrccProcess process, List<String> vtlFolders)
			throws OrccException {

	}

	abstract protected void doXdfAnalyzer(Network network) throws OrccException;

	abstract public void importResults();

	/**
	 * Returns true if this process has been canceled.
	 * 
	 * @return true if this process has been canceled
	 */
	protected boolean isCanceled() {
		if (process == null) {
			return false;
		} else {
			return process.getProgressMonitor().isCanceled();
		}
	}

	public void printResults(String templateName, String instanceName,
			String fileName) throws IOException {
		STGroup group = OrccUtil.loadGroup(templateName,
				"net/sf/orcc/tools/templates/",
				AbstractNetworkAnalyzer.class.getClassLoader());
		ST template = group.getInstanceOf(instanceName);
		template.add("analysis", analysis);

		byte[] b = template.render(80).getBytes();
		OutputStream os = new FileOutputStream(path + File.separator + fileName);
		os.write(b);
		os.close();
	}

	final public void setOutputFolder(String outputFolder) {
		// set output path
		path = new File(outputFolder).getAbsolutePath();
	}

	public void setVtlFolders(List<String> vtlFolders) {
		this.vtlFolders = vtlFolders;
	}

	/**
	 * Writes the given text to the process's normal output.
	 * 
	 * @param text
	 *            a string
	 */
	final public void write(String text) {
		if (process != null) {
			process.write(text);
		}
	}

}
