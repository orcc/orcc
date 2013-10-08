/*
 * Copyright (c) 2013, IETR/INSA of Rennes
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
package net.sf.orcc.xdf.ui.patterns;

import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Instance;
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.xdf.ui.styles.StyleUtil;

import org.eclipse.emf.common.util.EList;
import org.eclipse.graphiti.features.IDirectEditingInfo;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.context.IDeleteContext;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.context.IRemoveContext;
import org.eclipse.graphiti.features.context.IResizeShapeContext;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.pattern.AbstractPattern;
import org.eclipse.graphiti.pattern.IPattern;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;
import org.eclipse.graphiti.services.IPeService;

/**
 * This class configure as most features as possible, relative to Instances that
 * can be added to a Network.
 * 
 * @author Antoine Lorence
 * 
 */
public class InstancePattern extends AbstractPattern implements IPattern {

	private static int TOTAL_WIDTH = 120;
	private static int TOTAL_HEIGHT = 140;
	private static int LABEL_HEIGHT = 40;

	private static String PROPERTY_ID = "XDF_ID";

	private enum IDS {
		LABEL, INPORTS_LIST, OUTPORTS_LIST
	};

	public InstancePattern() {
		super(null);
	}

	@Override
	public String getCreateName() {
		return "Instance";
	}

	@Override
	public boolean isMainBusinessObjectApplicable(Object mainBusinessObject) {
		return mainBusinessObject instanceof Instance;
	}

	@Override
	protected boolean isPatternControlled(PictogramElement pictogramElement) {
		Object domainObject = getBusinessObjectForPictogramElement(pictogramElement);
		return isMainBusinessObjectApplicable(domainObject);
	}

	@Override
	protected boolean isPatternRoot(PictogramElement pictogramElement) {
		Object domainObject = getBusinessObjectForPictogramElement(pictogramElement);
		return isMainBusinessObjectApplicable(domainObject);
	}

	/*
	 * An instance can't be removed from a graph. Instead it must be deleted.
	 * 
	 * @see
	 * org.eclipse.graphiti.pattern.AbstractPattern#canRemove(org.eclipse.graphiti
	 * .features.context.IRemoveContext)
	 */
	@Override
	public boolean canRemove(IRemoveContext context) {
		return false;
	}

	@Override
	public boolean canDelete(IDeleteContext context) {
		return true;
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		return context.getTargetContainer() instanceof Diagram;
	}

	@Override
	public Object[] create(ICreateContext context) {
		Instance newInstance = null;
		if (true) {
			newInstance = DfFactory.eINSTANCE.createInstance();
		}

		// TODO: We must add the new instance to an Xdf resource created
		// earlier, instead of in the .diragram file directly
		getDiagram().eResource().getContents().add(newInstance);

		addGraphicalRepresentation(context, newInstance);
		return new Object[] { newInstance };
	}

	@Override
	public boolean canAdd(IAddContext context) {
		boolean canAdd = false;
		if (context.getTargetContainer() instanceof Diagram) {
			if (context.getNewObject() instanceof Instance) {
				canAdd = true;
			}
		}
		return canAdd;
	}

	@Override
	public PictogramElement add(IAddContext context) {
		final Diagram targetDiagram = (Diagram) context.getTargetContainer();
		final IPeService peService = Graphiti.getPeService();
		final IPeCreateService peCreateService = Graphiti.getPeCreateService();
		final IGaService gaService = Graphiti.getGaService();

		// Create the container
		final ContainerShape containerShape;
		{
			containerShape = peCreateService.createContainerShape(
					targetDiagram, true);
			final RoundedRectangle roundedRectangle = gaService
					.createPlainRoundedRectangle(containerShape, 5, 5);
			roundedRectangle.setStyle(StyleUtil
					.getStyleForInstance(getDiagram()));
			gaService.setLocationAndSize(roundedRectangle, context.getX(),
					context.getY(), TOTAL_WIDTH, TOTAL_HEIGHT);
		}

		{
			// The text label for Instance name
			final Shape shape = peCreateService.createShape(containerShape,
					false);
			final Text text = gaService.createPlainText(shape);
			text.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
			text.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
			text.setStyle(StyleUtil.getStyleForInstanceText(getDiagram()));
			gaService.setLocationAndSize(text, 0, 0, TOTAL_WIDTH, LABEL_HEIGHT);

			peService.setPropertyValue(shape, PROPERTY_ID, IDS.LABEL.name());

		}

		{
			// The line separator
			final Shape shape = peCreateService.createShape(containerShape,
					false);
			final int[] xy = { 0, LABEL_HEIGHT, TOTAL_WIDTH, LABEL_HEIGHT };
			final Polyline line = gaService.createPlainPolyline(shape, xy);
			line.setStyle(StyleUtil.getCommonStyle(getDiagram()));
		}

		// We link graphical representation and domain model object
		Instance addedDomainObject = (Instance) context.getNewObject();
		link(containerShape, addedDomainObject);

		return containerShape;
	}

	/*
	 * We don't want to authorize user to resize the Instance pictogram
	 * 
	 * @see
	 * org.eclipse.graphiti.pattern.AbstractPattern#canResizeShape(org.eclipse
	 * .graphiti.features.context.IResizeShapeContext)
	 */
	@Override
	public boolean canResizeShape(IResizeShapeContext context) {
		return false;
	}

	@Override
	public boolean canLayout(ILayoutContext context) {
		PictogramElement elt = context.getPictogramElement();
		Object obj = getBusinessObjectForPictogramElement(elt);
		return elt instanceof ContainerShape && obj instanceof Instance;
	}

	@Override
	public boolean layout(ILayoutContext context) {
		PictogramElement pictogramElement = context.getPictogramElement();
		if (pictogramElement instanceof ContainerShape) {
			ContainerShape containerShape = (ContainerShape) pictogramElement;
			GraphicsAlgorithm outerGraphicsAlgorithm = containerShape
					.getGraphicsAlgorithm();
			if (outerGraphicsAlgorithm instanceof RoundedRectangle) {
				RoundedRectangle roundedRectangle = (RoundedRectangle) outerGraphicsAlgorithm;
				EList<Shape> children = containerShape.getChildren();
				if (children.size() > 0) {
					Shape shape = children.get(0);
					GraphicsAlgorithm graphicsAlgorithm = shape
							.getGraphicsAlgorithm();
					if (graphicsAlgorithm instanceof Text) {
						Graphiti.getGaLayoutService().setLocationAndSize(
								graphicsAlgorithm, 0, 0,
								roundedRectangle.getWidth(),
								roundedRectangle.getHeight());
						return true;
					}
				}
			}
		}
		return false;
	}
}
