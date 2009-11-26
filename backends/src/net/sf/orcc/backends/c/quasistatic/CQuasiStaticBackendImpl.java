/*
 * Copyright(c)2009 Victor Martin, Jani Boutellier
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the EPFL and University of Oulu nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY  Victor Martin, Jani Boutellier ``AS IS'' AND ANY 
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL  Victor Martin, Jani Boutellier BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package net.sf.orcc.backends.c.quasistatic;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import net.sf.orcc.OrccException;
import net.sf.orcc.backends.AbstractBackend;
import net.sf.orcc.backends.c.quasistatic.scheduler.exceptions.QuasiStaticSchedulerException;
import net.sf.orcc.backends.c.quasistatic.scheduler.main.Scheduler;
import net.sf.orcc.backends.c.quasistatic.scheduler.util.FileUtilities;
import net.sf.orcc.backends.c.transforms.IncrementPeephole;
import net.sf.orcc.backends.c.transforms.MoveReadsWritesTransformation;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.ActorTransformation;
import net.sf.orcc.ir.transforms.DeadGlobalElimination;
import net.sf.orcc.ir.transforms.PhiRemoval;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.serialize.XDFParser;
import net.sf.orcc.network.transforms.BroadcastAdder;

public class CQuasiStaticBackendImpl extends AbstractBackend {

	private String workingDirectoryPath;
	private CQuasiStaticActorPrinter printer;
	private HashMap<String, List<String>> scheduleMap;

	/*public void generateCode(String fileName, int fifoSize) throws Exception {
		// CQuasiStaticActorParser.initFSMs();
		super.generateCode(fileName, 64);
		// parses top network
		Network network = new XDFParser(fileName).parseNetwork();
		// print schedule
		printSchedule(network);
	}*/

	@Override
	protected void init() throws IOException {
		printer = new CQuasiStaticActorPrinter();

		// Inits scheduler's stuff
		setDirectories();
	}

	protected void setDirectories() {
		workingDirectoryPath = path + File.separator + "schedule"
				+ File.separator;
	}

	@Override
	protected void printActor(String id, Actor actor) throws Exception {
		ActorTransformation[] transformations = { new DeadGlobalElimination(),
				new PhiRemoval(), new IncrementPeephole(),
				new MoveReadsWritesTransformation() };

		for (ActorTransformation transformation : transformations) {
			transformation.transform(actor);
		}

		String outputName = path + File.separator + id + ".c";
		printer.printActor(outputName, id, actor);
		
	}

	@Override
	protected void printNetwork(Network network) throws Exception {
		CQuasiStaticNetworkPrinter networkPrinter = new CQuasiStaticNetworkPrinter();
		String outputName = path + File.separator + network.getName() + ".c";

		// Add broadcasts before printing
		new BroadcastAdder().transform(network);
		networkPrinter.printNetwork(outputName, network, false, fifoSize);
		printSchedule(network);
	}

	protected void printSchedule(Network network) throws IOException,
			OrccException, QuasiStaticSchedulerException {
		scheduleMap = new Scheduler(workingDirectoryPath, network)
				.performSchedule();
		CQuasiStaticSchedulePrinter schedulePrinter = new CQuasiStaticSchedulePrinter();
		String outputName = path + File.separator + "scheduling.c";
		removeOutputDirectories();
		schedulePrinter.printSchedule(outputName, scheduleMap);
	}

	/**
	 * 
	 */
	private void removeOutputDirectories() {
		FileUtilities.deleteFile(new File(workingDirectoryPath));
	}

}
