/*
 * Copyright (c) 2010, IETR/INSA of Rennes
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
package net.sf.orcc.tools.merger.actor;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.util.DfVisitor;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.util.OrccLogger;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;

/**
 * This class defines a network transformation that merges static actors.
 * 
 * @author Matthieu Wipliez
 * @author Ghislain Roquier
 * @author Herve Yviquel
 * @author Jani Boutellier
 * 
 */
public class ActorMerger extends DfVisitor<Void> {

	private final DfFactory dfFactory = DfFactory.eINSTANCE;

	private Copier copier;

	private int index;
	
	private final String definitionFileName = "schedule.xml";
	/**
	 * Transforms the network to internalize the given list of vertices in their
	 * own subnetwork and returns this subnetwork.
	 * 
	 * @param vertices
	 *            a given list of vertices
	 * @return the SDF/CSDF child network containing the list of vertices
	 */
	private Network getSubNetwork(Network network, List<Vertex> vertices) {
		// extract the sub-network
		Network subNetwork = IrUtil.copy(network);
		subNetwork.setName(getRegionName(vertices));

		List<Vertex> verticesInSubNetwork = new ArrayList<Vertex>();
		for (Vertex vertex : vertices) {
			Vertex is = subNetwork.getChild(vertex.getLabel());
			verticesInSubNetwork.add(is);
		}

		for (Vertex vertex : new ArrayList<Vertex>(subNetwork.getChildren())) {
			if (!verticesInSubNetwork.contains(vertex)) {
				subNetwork.remove(vertex);
			}
		}
		
		// Perform the renaming of ports to the superactor
		for (Vertex vertex : new ArrayList<Vertex>(subNetwork.getChildren())) {
			Actor actorCopy = vertex.getAdapter(Actor.class);
			for (Port port : actorCopy.getInputs()) {
				port.addAttribute("shortName");
				port.setAttribute("shortName", port.getName());
				port.setName(actorCopy.getName() + "_" + port.getName());
			}
			for (Port port : actorCopy.getOutputs()) {
				port.addAttribute("shortName");
				port.setAttribute("shortName", port.getName());
				port.setName(actorCopy.getName() + "_" + port.getName());
			}
		}

		for (Vertex vertex : subNetwork.getChildren()) {
			Actor actor = vertex.getAdapter(Actor.class);
			if (actor != null) {
				List<Port> unconnected = new ArrayList<Port>(actor.getInputs());
				unconnected.removeAll(actor.getIncomingPortMap().keySet());
				for (Port input : unconnected) {
					Integer size = new Integer(0);
					Port inputPort = EcoreUtil.copy(input);
					subNetwork.addInput(inputPort);
					if (actor.getIncomingPortMap().get(input) != null) {
						size = actor.getIncomingPortMap().get(input).getSize();
					}
					subNetwork.add(dfFactory.createConnection(inputPort, null,
							vertex, input, size));
				}

				unconnected = new ArrayList<Port>(actor.getOutputs());
				unconnected.removeAll(actor.getOutgoingPortMap().keySet());
				for (Port output : unconnected) {
					Integer size = new Integer(0);
					Port outputPort = EcoreUtil.copy(output);
					subNetwork.addOutput(outputPort);
					if (actor.getOutgoingPortMap().get(output) != null) {
						size = actor.getOutgoingPortMap().get(output).get(0).getSize();
					}
					subNetwork.add(dfFactory.createConnection(vertex, output,
							outputPort, null, size));
				}
			}
		}

		return subNetwork;
	}

	/*
	* When static regions (= superactors) are imported from an XML file
	* the region name is piggybacked in a special "name vertex" that
	* is read here (if present) and deleted. For StaticRegionDetector
	* the basic naming scheme is used.
	*/
	
	private String getRegionName(List<Vertex> vertices) {
		for(Vertex vertex : vertices) {
			if (vertex.hasAttribute("isNameVertex")) {
				String regionName = vertex.getLabel();
				vertices.remove(vertex);
				return regionName;
			}
		}
		return new String("cluster" + index);
	}

	/*
	* If file "schedule.xml" is found in the user's home folder, that file
	* is used to define static regions and perform actor merging. If a file
	* under that name can not be opened, automatic merging is used.
	*/
	
	@Override
	public Void caseNetwork(Network network) {
		String definitionFile = new String(System.getProperty("user.home") + "/" + definitionFileName);
		boolean fileExists = MergerUtil.testFilePresence(definitionFile);
		List<List<Vertex>> staticRegions;
		if (!fileExists) {
			OrccLogger.traceln("Could not open " + definitionFile + " - performing automatic merging");
			staticRegions = new StaticRegionDetector()
				.analyze(network);
		} else {
			OrccLogger.traceln("Performing merging based on " + definitionFile);
			RegionParser regionParser = new RegionParser(definitionFile, network);
			staticRegions = regionParser.parse();
		}
		OrccLogger.traceln(staticRegions.size() + " regions in total");

		for (List<Vertex> instances : staticRegions) {
			copier = new Copier(true);
			// return a copy of the sub-network
			Network subNetwork = getSubNetwork(network, instances);
			
			// create the static schedule of vertices
			SASLoopScheduler scheduler = null;
			if (!fileExists) {
				scheduler = new SASLoopScheduler(subNetwork);
				scheduler.schedule();
			}
			
			int actorcount = subNetwork.getChildren().size();
			int fifocount = 0;
			for (Connection conn : subNetwork.getConnections()) {
				if (conn.getSourcePort() != null
						&& conn.getTargetPort() != null) {
					fifocount++;
				}
			}

			OrccLogger.traceln(subNetwork.getName() + " (" + actorcount
					+ " actors, " + fifocount + " fifos)");
			
			// merge sub-network inside a single actor
			Actor superActor;
			if (!fileExists) {
				OrccLogger.traceln("schedule" + scheduler.getSchedule());
				superActor = new ActorMergerSDF(scheduler, copier)
					.doSwitch(subNetwork);
			} else {
				superActor = dfFactory.createActor();
				SuperactorParser superactorParser = new SuperactorParser(subNetwork, superActor);
				superactorParser.parse(definitionFile);
				ActorMergerQS actorMerger = new ActorMergerQS(subNetwork, copier, definitionFile,
						superactorParser.getScheduleList(), superactorParser.getGuardList());
				actorMerger.createMergedActor(superActor, superactorParser.getFSM());
			}
			// update the main network
			network.add(superActor);

			List<Connection> newConnections = new ArrayList<Connection>();
			for (Connection connection : network.getConnections()) {
				Vertex src = connection.getSource();
				Vertex tgt = connection.getTarget();

				if (!instances.contains(src) && instances.contains(tgt)) {
					Port tgtPort = superActor.getInput(connection.getTarget()
							.getLabel()
							+ "_"
							+ connection.getTargetPort().getName());
					// Add connection to the parent network
					newConnections.add(dfFactory.createConnection(src,
							connection.getSourcePort(), superActor, tgtPort,
							connection.getSize()));

				} else if (instances.contains(src) && !instances.contains(tgt)) {
					Port srcPort = superActor.getOutput(connection.getSource()
							.getLabel()
							+ "_"
							+ connection.getSourcePort().getName());
					// Add connection to the parent network
					newConnections.add(dfFactory.createConnection(superActor,
							srcPort, tgt, connection.getTargetPort(),
							connection.getSize()));

				}
			}

			for (Port port : superActor.getOutputs()) {
				if (port.hasAttribute("externalFifo")) {
					int superActorPortIndex = MergerUtil.findPort(superActor.getInputs(),
							port.getValueAsString("targetPort"));
					if (superActorPortIndex >= 0) {
						Integer size = ((Integer)port.getValueAsObject("externalFifo"));
						newConnections.add(dfFactory.createConnection(superActor,
							port, superActor, superActor.getInputs().get(superActorPortIndex), size));
					}
				}
			}
			network.removeVertices(instances);
			network.getChildren().removeAll(instances);
			network.getEdges().addAll(newConnections);

			index++;
		}
		return null;
	}

}
