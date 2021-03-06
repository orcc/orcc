/*
 * Copyright (c) 2012, IRISA
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
package net.sf.orcc.df.util;

import net.sf.orcc.OrccRuntimeException;
import net.sf.orcc.df.Connection;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Port;
import net.sf.orcc.graph.Vertex;

/**
 * @author Herve Yviquel
 * 
 */
public class NetworkValidator extends DfVisitor<Void> {

	@Override
	public Void caseConnection(Connection connection) {
		Vertex src = connection.getSource();
		Vertex tgt = connection.getTarget();
		String srcName = src.getLabel();
		String tgtName = tgt.getLabel();

		if (!(connection.getSource() instanceof Port)) {
			Port srcPort = connection.getSourcePort();
			if (srcPort == null || srcPort.eIsProxy()) {
				throw new OrccRuntimeException("The connection between "
						+ srcName + " and " + tgtName + " has no source port.");
			}
		}
		if (!(tgt instanceof Port)) {
			Port tgtPort = connection.getTargetPort();
			if (tgtPort == null || tgtPort.eIsProxy()) {
				throw new OrccRuntimeException("The connection between "
						+ srcName + " and " + tgtName + " has no target port.");
			}
		}

		return null;
	}

	@Override
	public Void caseInstance(Instance instance) {
		if (instance.getEntity() == null) {
			throw new OrccRuntimeException(
					"The instance "
							+ instance.getName()
							+ " is invalid. Please check corresponding resource for errors, or rebuild the project.");
		}
		return super.caseInstance(instance);
	}

}
