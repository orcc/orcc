/*
 * Copyright (c) 2011, IRISA
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of the IRISA nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */
package net.sf.orcc.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.network.Connection;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.serialize.XDFParser;
import net.sf.orcc.util.OrccUtil;
import net.sf.orcc.util.WriteListener;

import org.eclipse.core.runtime.IProgressMonitor;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;

/**
 * This class defines an abstract network analyzer.
 * 
 * @author Herve Yviquel
 * @author Matthieu Wipliez
 *
 */
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
				analyzer.analyzeXDF(inputFile);
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
	 * the process that launched this analyzer
	 */
	protected WriteListener listener;

	private IProgressMonitor monitor;

	/**
	 * Path where output files will be written.
	 */
	protected String path;

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

	public void analyzeVTL(WriteListener listener, List<String> vtlFolders)
			throws OrccException {
		this.listener = listener;
		this.vtlFolders = vtlFolders;

		// lists actors
		listener.writeText("Lists actors...\n");
		List<File> vtlFiles = new ArrayList<File>();
		for (String folder : vtlFolders) {
			findFiles(vtlFiles, new File(folder));
		}

		Collections.sort(vtlFiles, new Comparator<File>() {

			@Override
			public int compare(File f1, File f2) {
				return f1.compareTo(f2);
			}

		});
		doVtlAnalyzer(vtlFiles);

	}

	@Override
	final public void analyzeXDF(String inputFile) throws OrccException {
		// parses top network
		listener.writeText("Parsing XDF network...\n");
		Network network = new XDFParser(inputFile).parseNetwork();
		network.updateIdentifiers();
		if (isCanceled()) {
			return;
		}

		listener.writeText("Instantiating actors...\n");
		network.instantiate(vtlFolders);
		Network.clearActorPool();
		listener.writeText("Instantiation done\n");

		if (isCanceled()) {
			return;
		}
		doXdfAnalyzer(network);
	}

	abstract protected void doVtlAnalyzer(List<File> files)
			throws OrccException;

	abstract protected void doXdfAnalyzer(Network network) throws OrccException;

	private void findFiles(List<File> vtlFiles, File vtl) {
		for (File file : vtl.listFiles()) {
			if (file.isDirectory()) {
				findFiles(vtlFiles, file);
			} else if (file.getName().endsWith(".json")) {
				vtlFiles.add(file);
			}
		}
	}

	abstract public void importResults();

	/**
	 * Returns true if this process has been canceled.
	 * 
	 * @return true if this process has been canceled
	 */
	protected boolean isCanceled() {
		if (monitor == null) {
			return false;
		} else {
			return monitor.isCanceled();
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

	@Override
	public void setProgressMonitor(IProgressMonitor monitor) {
		this.monitor = monitor;
	}

	public void setVtlFolders(List<String> vtlFolders) {
		this.vtlFolders = vtlFolders;
	}

	@Override
	public void setWriteListener(WriteListener listener) {
		this.listener = listener;
	}

}
