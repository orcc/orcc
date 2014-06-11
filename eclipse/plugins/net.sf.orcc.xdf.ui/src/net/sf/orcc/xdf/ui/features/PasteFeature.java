/*
 * Copyright (c) 2014, IETR/INSA of Rennes
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
package net.sf.orcc.xdf.ui.features;

import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.xdf.ui.util.XdfUtil;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IPasteContext;
import org.eclipse.graphiti.features.context.impl.AddContext;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.ui.features.AbstractPasteFeature;

/**
 * Implements the ability to paste previously copied/cut objects into diagram.
 * 
 * @author Antoine Lorence
 * 
 */
public class PasteFeature extends AbstractPasteFeature {

	public PasteFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canPaste(IPasteContext context) {
		// Can paste, if all objects on the clipboard are Instance or Port
		final Object[] fromClipboard = getFromClipboard();
		if (fromClipboard == null || fromClipboard.length == 0) {
			return false;
		}
		for (final Object object : fromClipboard) {
			if (!(object instanceof Instance || object instanceof Port)) {
				return false;
			}
		}
		return true;
	}


	@Override
	public void paste(IPasteContext context) {
		final Network network = (Network) getBusinessObjectForPictogramElement(getDiagram());

		final Object[] objects = getFromClipboard();
		for (final Object object : objects) {

			if (object instanceof Port) {
				final Port origPort = (Port) object;
				final Port port = EcoreUtil.copy(origPort);
				port.setName(XdfUtil.uniqueVertexName(network, port.getName()));
				if (XdfUtil.isInputNetworkPort(origPort)) {
					network.addInput(port);
				} else if (XdfUtil.isOutputNetworkPort(origPort)) {
					network.addOutput(port);
				} else {
					OrccLogger.warnln("The port " + origPort.getName()
							+ " can't be recognized as input or output");
				}
				addToDiagram(context, getDiagram(), port);
			} else if (object instanceof Instance) {
				final Instance instance = EcoreUtil.copy((Instance) object);
				instance.setName(XdfUtil.uniqueVertexName(network,
						instance.getName()));
				network.add(instance);
				addToDiagram(context, getDiagram(), instance);
			}
		}
	}

	private void addToDiagram(final IPasteContext context,
			final Diagram diagram, final EObject object) {
		final AddContext ac = new AddContext();
		// For simplicity paste all objects at the location given in the
		// context (no stacking or similar)
		ac.setLocation(context.getX(), context.getY());
		ac.setTargetContainer(diagram);
		addGraphicalRepresentation(ac, object);
	}
}
