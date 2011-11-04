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
package net.sf.orcc.tools.statistics;

import java.util.List;

import net.sf.orcc.OrccException;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Network;
import net.sf.orcc.tools.AbstractActorAnalyzer;
import net.sf.orcc.tools.AbstractNetworkAnalyzer;

import org.eclipse.core.resources.IFile;

/**
 * This class define a network analyzer that compute different statistics about
 * actors.
 * 
 * @author Herve Yviquel
 * 
 */
public class Statistics extends AbstractNetworkAnalyzer {

	public static void main(String[] args) {
		main(Statistics.class, args);
	}

	private InstructionStats instructionStats;

	private MemoryStats memoryStats;
	public Statistics(AbstractActorAnalyzer actorAnalyzer) {
		super(actorAnalyzer);
		memoryStats = new MemoryStats();
		instructionStats = new InstructionStats();
	}

	@Override
	protected void analyze(Connection connection) throws OrccException {
		// TODO Auto-generated method stub

	}

	public void computeStats(Network network) {
		memoryStats.computeMemoryStats(network);
		instructionStats.computeInstructionStats(network);
	}

	@Override
	protected void doVtlAnalyzer(List<IFile> files) throws OrccException {
		// TODO Auto-generated method stub

	}

	@Override
	protected void doXdfAnalyzer(Network network) throws OrccException {
		// TODO Auto-generated method stub

	}

	public InstructionStats getGraphStats() {
		return instructionStats;
	}

	public MemoryStats getMemoryStats() {
		return memoryStats;
	}

	@Override
	public void importResults() {
		// TODO Auto-generated method stub

	}

}
