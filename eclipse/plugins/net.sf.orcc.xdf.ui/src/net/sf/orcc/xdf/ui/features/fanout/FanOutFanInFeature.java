/*
 * Copyright (c) 2016, Heriot-Watt University Edinburgh
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
package net.sf.orcc.xdf.ui.features.fanout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

import net.sf.orcc.backends.cal.InstancePrinter;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.transform.FanOutFanIn;
import net.sf.orcc.util.FilesManager;
import net.sf.orcc.xdf.ui.features.AbstractTimeConsumingCustomFeature;
import net.sf.orcc.xdf.ui.features.UpdateDiagramFeature;

/*
* @author Rob Stewart
* @author Idris Ibrahim
*/
abstract public class FanOutFanInFeature extends AbstractTimeConsumingCustomFeature {

	List<Instance> selectedInstances;
	private IFeatureProvider thisFeature;
	int parallelDegree;

	public FanOutFanInFeature(IFeatureProvider fp, int parallelDegree) {
		super(fp);
		this.thisFeature = fp;
		this.parallelDegree = parallelDegree;
	}

	@Override
	public boolean isAvailable(IContext context) {
		return super.isAvailable(context);
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		boolean canWeExecute = false;
		Network currentNetwork = (Network) getBusinessObjectForPictogramElement(getDiagram());
		selectedInstances = new ArrayList<Instance>();
		for (int i = 0; i < context.getPictogramElements().length; i++) {
			PictogramElement elem = context.getPictogramElements()[i];
			if (getBusinessObjectForPictogramElement(elem) instanceof Instance) {
				final Instance instance = (Instance) getBusinessObjectForPictogramElement(elem);
				selectedInstances.add(instance);
			}
		}

		if (selectedInstances.size() > 0) {
			canWeExecute = FanOutFanIn.canFire(currentNetwork, selectedInstances);
		}

		return canWeExecute;
	}

	@Override
	public void execute(ICustomContext context, IProgressMonitor parentMonitor) {

		FanOutFanIn fanOutInTransformation = new FanOutFanIn(parallelDegree, selectedInstances);

		Network currentNetwork = (Network) getBusinessObjectForPictogramElement(getDiagram());

		/* empty the diagram */
		getDiagram().getChildren().removeAll(getDiagram().getChildren());

		/* apply transformation */
		fanOutInTransformation.doSwitch(currentNetwork);

		final List<String> updatedNetworkWarnings = new ArrayList<String>();
		UpdateDiagramFeature updateFeature = new UpdateDiagramFeature(thisFeature);

		/* initialize */
		updateFeature.fixNetwork(currentNetwork, updatedNetworkWarnings);
		updateFeature.initializeDiagramFromNetwork(currentNetwork, getDiagram());

		/* write the actor files for new or modified actors */
		for (Actor actor : fanOutInTransformation.actorsToFile()) {
			String srcPath = new File(actor.getFile().getLocationURI()).getParent();
			InstancePrinter instancePrinter = new InstancePrinter();
			instancePrinter.setActor(actor);
			// use the CAL instance printer
			FilesManager.writeFile(instancePrinter.getFileContent(), srcPath, actor.getSimpleName() + ".cal");
		}
	}
}
