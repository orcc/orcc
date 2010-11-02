/*
 * Copyright (c) 2009, IETR/INSA of Rennes
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
package net.sf.orcc.network.transforms;

import net.sf.orcc.OrccException;
import net.sf.orcc.moc.CSDFMoC;
import net.sf.orcc.moc.DynamicMoC;
import net.sf.orcc.moc.MoC;
import net.sf.orcc.moc.SDFMoC;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.tools.classifier.ActorClassifierIndependent;

/**
 * This class defines a network transformation that classifies all actors using
 * the {@link ActorClassifierIndependent} class.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class NetworkClassifier implements INetworkTransformation {

	private static int CSDF = 1;

	private static int DYNAMIC = 2;

	private static int SDF = 0;

	private MoC getMoC(Network network) {
		MoC moc = new SDFMoC();

		int currentClass = SDF;

		for (Instance instance : network.getInstances()) {
			if (instance.isActor()) {
				MoC clasz = instance.getActor().getMoC();
				if (clasz != null) {
					if (clasz.isDynamic() || clasz.isQuasiStatic()) {
						if (currentClass < DYNAMIC) {
							moc = new DynamicMoC();
							currentClass = DYNAMIC;
						}
					} else if (clasz.isCSDF()) {
						if (currentClass < CSDF) {
							moc = new CSDFMoC();
							currentClass = CSDF;
						}
					}
				}
			}
		}

		return moc;
	}

	@Override
	public void transform(Network network) throws OrccException {
		network.setMoC(getMoC(network));
	}

}
