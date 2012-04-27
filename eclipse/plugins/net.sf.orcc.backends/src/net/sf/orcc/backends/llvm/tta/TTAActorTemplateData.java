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
package net.sf.orcc.backends.llvm.tta;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.backends.llvm.aot.LLVMTemplateData;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Port;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;

/**
 * This class allows the string template accessing informations about
 * application's instances
 * 
 * @author Herve Yviquel
 * 
 */
public class TTAActorTemplateData extends LLVMTemplateData {

	private Map<Pattern, Map<Port, Integer>> portToIndexByPatternMap;

	private Map<Port, Integer> portToIndexMap;

	private Map<Port, Boolean> portToNeedCastMap;

	public TTAActorTemplateData(Actor actor) {
		super(actor);
		portToIndexByPatternMap = new HashMap<Pattern, Map<Port, Integer>>();
		portToIndexMap = new HashMap<Port, Integer>();
		portToNeedCastMap = new HashMap<Port, Boolean>();

		computeTemplateMaps(actor);
	}

	private void computePortToIndexByPatternMap(Actor actor) {
		TreeIterator<EObject> it = actor.eAllContents();
		while (it.hasNext()) {
			EObject object = it.next();
			if (object instanceof Pattern) {
				Pattern pattern = (Pattern) object;
				Map<Port, Integer> portToIndexMap = new HashMap<Port, Integer>();
				for (int i = 0; i < pattern.getPorts().size(); i++) {
					portToIndexMap.put(pattern.getPorts().get(i), i + 1);
				}
				portToIndexByPatternMap.put(pattern, portToIndexMap);
			}
		}
	}

	private void computePortToIndexMap(Actor actor) {
		for (int i = 0; i < actor.getInputs().size(); i++) {
			portToIndexMap.put(actor.getInputs().get(i), i + 1);
		}
		for (int i = 0; i < actor.getOutputs().size(); i++) {
			portToIndexMap.put(actor.getOutputs().get(i), i + 1);
		}
	}

	private void computePortToNeedCastMap(Actor actor) {
		for (Port input : actor.getInputs()) {
			portToNeedCastMap.put(input, input.getType().getSizeInBits() < 32);
		}
		for (Port output : actor.getOutputs()) {
			portToNeedCastMap
					.put(output, output.getType().getSizeInBits() < 32);
		}
	}

	public void computeTemplateMaps(Actor actor) {
		super.computeTemplateMaps(actor);
		computePortToIndexByPatternMap(actor);
		computePortToIndexMap(actor);
		computePortToNeedCastMap(actor);
	}

	public Map<Pattern, Map<Port, Integer>> getPortToIndexByPatternMap() {
		return portToIndexByPatternMap;
	}

	public Map<Port, Integer> getPortToIndexMap() {
		return portToIndexMap;
	}

	public Map<Port, Boolean> getPortToNeedCastMap() {
		return portToNeedCastMap;
	}

}
