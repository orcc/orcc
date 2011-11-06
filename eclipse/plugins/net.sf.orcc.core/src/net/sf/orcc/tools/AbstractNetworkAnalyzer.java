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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.util.OrccUtil;
import net.sf.orcc.util.WriteListener;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
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
			List<String> vtlFolderNames = Arrays.asList(args[1]
					.split(File.pathSeparator));
			List<IFolder> vtlFolders = new ArrayList<IFolder>(
					vtlFolderNames.size());
			IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			for (String folderName : vtlFolderNames) {
				IFile file = root.getFileForLocation(new Path(folderName));
				IFolder folder = (IFolder) file.getParent();
				vtlFolders.add(folder);
			}

			String outputFolder = args[2];

			try {
				AbstractNetworkAnalyzer analyzer = clasz.newInstance();
				analyzer.setOutputFolder(outputFolder);
				analyzer.analyzeVTL(vtlFolders);
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

	private ResourceSet set;

	public AbstractNetworkAnalyzer(AbstractActorAnalyzer actorAnalyzer) {
		this.actorAnalyzer = actorAnalyzer;
		this.analysis = new HashMap<String, Object>();
		set = new ResourceSetImpl();
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
	public void analyzeVTL(List<IFolder> vtlFolders) throws OrccException {
		// lists actors
		listener.writeText("Lists actors...\n");
		List<IFile> vtlFiles = OrccUtil.getAllFiles("ir", vtlFolders);
		doVtlAnalyzer(vtlFiles);
	}

	@Override
	final public void analyzeXDF(String inputFile) throws OrccException {
		// parses top network
		listener.writeText("Parsing XDF network...\n");

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		IFile xdfFile = root.getFile(new Path(inputFile));

		Network network = IrUtil.deserializeEntity(set, xdfFile);
		if (isCanceled()) {
			return;
		}

		listener.writeText("Instantiating actors...\n");
		EcoreUtil.resolveAll(set);
		listener.writeText("Instantiation done\n");

		if (isCanceled()) {
			return;
		}
		doXdfAnalyzer(network);
	}

	abstract protected void doVtlAnalyzer(List<IFile> files)
			throws OrccException;

	abstract protected void doXdfAnalyzer(Network network) throws OrccException;

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
		STGroup group = OrccUtil.loadGroup("net/sf/orcc/tools/templates/"
				+ templateName + ".stg",
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

	@Override
	public void setWriteListener(WriteListener listener) {
		this.listener = listener;
	}

}
