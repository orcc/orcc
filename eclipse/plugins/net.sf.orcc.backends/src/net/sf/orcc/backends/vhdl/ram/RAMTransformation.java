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
package net.sf.orcc.backends.vhdl.ram;

import net.sf.orcc.backends.vhdl.transformations.ActionSplitter;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.transformations.IfConverter;
import net.sf.orcc.ir.transformations.IfDeconverter;
import net.sf.orcc.ir.util.ActorVisitor;

/**
 * This class defines the transformation of IR to use RAMs.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class RAMTransformation implements ActorVisitor<Object> {

	private final int numPortsRam;

	private final int minSizeRam;

	public RAMTransformation(int numPortsRam, int minSizeRam) {
		this.numPortsRam = numPortsRam;
		this.minSizeRam = minSizeRam;
	}

	@Override
	public Object doSwitch(Actor actor) {
		new IfConverter().doSwitch(actor);
		new RAMInstructionScheduler(numPortsRam, minSizeRam).doSwitch(actor);
		new ConditionedSplitExtractor().doSwitch(actor);
		new ActionSplitter().doSwitch(actor);
		new IfDeconverter().doSwitch(actor);

		return null;
	}

}
