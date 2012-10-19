/*
 * Copyright (c) 2010-2011, IETR/INSA of Rennes
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
 *   * Neither the name of the IETR/INSA of Rennes nor the names of its
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
package net.sf.orcc.simulators;

import static net.sf.orcc.OrccLaunchConstants.DEFAULT_FIFO_SIZE;
import static net.sf.orcc.OrccLaunchConstants.ENABLE_TRACES;
import static net.sf.orcc.OrccLaunchConstants.FIFO_SIZE;
import static net.sf.orcc.OrccLaunchConstants.GOLDEN_REFERENCE;
import static net.sf.orcc.OrccLaunchConstants.GOLDEN_REFERENCE_FILE;
import static net.sf.orcc.OrccLaunchConstants.INPUT_STIMULUS;
import static net.sf.orcc.OrccLaunchConstants.LOOP_NUMBER;
import static net.sf.orcc.OrccLaunchConstants.NO_DISPLAY;
import static net.sf.orcc.OrccLaunchConstants.PROJECT;
import static net.sf.orcc.OrccLaunchConstants.TRACES_FOLDER;
import static net.sf.orcc.OrccLaunchConstants.XDF_FILE;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.transform.Instantiator;
import net.sf.orcc.df.transform.NetworkFlattener;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.ir.util.ActorInterpreter;
import net.sf.orcc.runtime.SimulatorFifo;
import net.sf.orcc.runtime.impl.GenericDisplay;
import net.sf.orcc.runtime.impl.GenericSource;
import net.sf.orcc.util.Attribute;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.util.OrccUtil;
import net.sf.orcc.util.util.EcoreHelper;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

/**
 * This class implements a simulator using a slow, visitor-based approach.
 * 
 * @author Matthieu Wipliez
 * @author Pierre-Laurent Lagalaye
 * 
 */
public class SlowSimulator extends AbstractSimulator {

	private boolean enableTraces;

	protected List<SimulatorFifo> fifoList;

	private int fifoSize;

	protected Map<Actor, ActorInterpreter> interpreters;

	protected IProject project;

	private String stimulusFile;

	private String traceFolder;

	protected List<IFolder> vtlFolders;

	protected String xdfFile;

	private boolean hasGoldenReference;

	private String goldenReferenceFile;

	private int loopsNumber;

	private boolean noDisplay;

	/**
	 * Creates FIFOs and connects ports together.
	 * 
	 * @param src
	 *            name of the source actor
	 * @param srcPort
	 *            source port
	 * @param tgt
	 *            name of the target actor
	 * @param tgtPort
	 *            target port
	 * @param fifoSize
	 *            size of the FIFO
	 */
	@SuppressWarnings("unchecked")
	protected void connectFifos(String src, Port srcPort, String tgt,
			Port tgtPort, int fifoSize) {
		SimulatorFifo fifo = null;
		if (enableTraces) {
			String fifoName = src + "_" + srcPort.getName() + "_" + tgt + "_"
					+ tgtPort.getName();
			fifo = new SimulatorFifo(srcPort.getType(), fifoSize, traceFolder,
					fifoName, enableTraces);
		} else {
			fifo = new SimulatorFifo(srcPort.getType(), fifoSize);
		}

		tgtPort.setAttribute("fifo", fifo);

		Attribute attribute = srcPort.getAttribute("fifo");
		List<SimulatorFifo> fifos;
		if (attribute == null) {
			fifos = null;
		} else {
			fifos = (List<SimulatorFifo>) attribute.getPojoValue();
		}
		if (fifos == null) {
			fifos = new ArrayList<SimulatorFifo>();
			srcPort.setAttribute("fifo", fifos);
		}
		fifos.add(fifo);
	}

	/**
	 * Visit the network graph for building the required topology. Edges of the
	 * graph correspond to the connections between the actors. These connections
	 * should be implemented as FIFOs of specific size as defined in the CAL
	 * model or a common default size.
	 * 
	 * @param graph
	 */
	public void connectNetwork(Network network) {
		// Loop over the connections and ask for the source and target actors
		// connection through specified I/O ports.
		for (Connection connection : network.getConnections()) {
			Vertex srcVertex = connection.getSource();
			Vertex tgtVertex = connection.getTarget();

			if (srcVertex instanceof Actor && tgtVertex instanceof Actor) {
				// get FIFO size (user-defined nor default)
				Integer connectionSize = connection.getSize();
				int size = (connectionSize == null) ? fifoSize : connectionSize;

				// create the communication FIFO between source and target
				// actors
				Actor src = (Actor) srcVertex;
				Actor tgt = (Actor) tgtVertex;
				Port srcPort = connection.getSourcePort();
				Port tgtPort = connection.getTargetPort();
				// connect source and target actors
				if ((srcPort != null) && (tgtPort != null)) {
					connectFifos(src.getName(), srcPort, tgt.getName(),
							tgtPort, size);
				}
			}
		}
	}

	/**
	 * Visits the network and creates one interpreter per actor.
	 * 
	 * @param network
	 *            the network
	 */
	protected void createInterpreters(Network network) {
		for (Vertex vertex : network.getChildren()) {
			Actor actor = vertex.getAdapter(Actor.class);

			ConnectedActorInterpreter interpreter = new ConnectedActorInterpreter(
					actor);

			interpreters.put(actor, interpreter);
		}
	}

	protected void initializeNetwork(Network network) {
		GenericSource.setInputStimulus(stimulusFile);

		GenericSource.setNbLoops(loopsNumber);

		if (hasGoldenReference) {
			GenericDisplay.setGoldenReference(goldenReferenceFile);
		}

		if (noDisplay) {
			GenericDisplay.setDisplayDisabled();
		}

		for (Vertex vertex : network.getChildren()) {
			Actor actor = vertex.getAdapter(Actor.class);
			ActorInterpreter interpreter = interpreters.get(actor);
			interpreter.initialize();
		}
	}

	@Override
	protected void initializeOptions() {
		fifoSize = getAttribute(FIFO_SIZE, DEFAULT_FIFO_SIZE);
		stimulusFile = getAttribute(INPUT_STIMULUS, "");
		hasGoldenReference = getAttribute(GOLDEN_REFERENCE, false);
		goldenReferenceFile = getAttribute(GOLDEN_REFERENCE_FILE, "");
		xdfFile = getAttribute(XDF_FILE, "");
		String name = getAttribute(PROJECT, "");
		enableTraces = getAttribute(ENABLE_TRACES, false);
		traceFolder = getAttribute(TRACES_FOLDER, "");

		IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		project = root.getProject(name);

		vtlFolders = OrccUtil.getOutputFolders(project);

		loopsNumber = getAttribute(LOOP_NUMBER, DEFAULT_NB_LOOPS);

		noDisplay = getAttribute(NO_DISPLAY, false);
	}

	protected int runNetwork(Network network) {
		boolean hasExecuted;
		do {
			hasExecuted = false;
			for (Vertex vertex : network.getChildren()) {
				int nbFiring = 0;
				Actor actor = vertex.getAdapter(Actor.class);
				ActorInterpreter interpreter = interpreters.get(actor);

				while (interpreter.schedule()) {
					// check for cancelation
					if (isCanceled() || stopRequested) {
						return statusCode;
					}
					nbFiring++;
				}

				hasExecuted |= (nbFiring > 0);

				// check for cancelation
				if (isCanceled() || stopRequested) {
					return statusCode;
				}
			}
		} while (hasExecuted);
		OrccLogger.traceln("End of simulation");
		return statusCode;
	}

	@Override
	public int start(String mode) {
		try {
			SimulatorDescriptor.killDescriptors();
			interpreters = new HashMap<Actor, ActorInterpreter>();

			IFile file = OrccUtil.getFile(project, xdfFile, "xdf");
			ResourceSet set = new ResourceSetImpl();
			Network network = EcoreHelper.getEObject(set, file);

			// full instantiation (no more instances)
			new Instantiator(true).doSwitch(network);

			// flattens network
			new NetworkFlattener().doSwitch(network);

			// create interpreters, connect network, initialize, and run
			createInterpreters(network);
			connectNetwork(network);
			initializeNetwork(network);
			runNetwork(network);
			SimulatorDescriptor.killDescriptors();
		} finally {
			// clean up to prevent memory leak
			interpreters = null;
		}
		return statusCode;
	}
}
