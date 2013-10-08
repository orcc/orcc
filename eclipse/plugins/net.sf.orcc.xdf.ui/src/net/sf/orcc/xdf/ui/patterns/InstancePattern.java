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
import net.sf.orcc.xdf.ui.styles.StyleUtil;

import org.eclipse.graphiti.features.IDirectEditingInfo;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.context.IDeleteContext;
import org.eclipse.graphiti.features.context.IDirectEditingContext;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.context.IRemoveContext;
import org.eclipse.graphiti.features.context.IResizeShapeContext;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.func.IDirectEditing;
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
		LABEL
	};

	public InstancePattern() {
		super(null);
	}

	@Override
	public String getCreateName() {
		return "Instance";
	}

	@Override
	public boolean canDirectEdit(IDirectEditingContext context) {
		boolean isText = context.getGraphicsAlgorithm() instanceof Text;

		return isText && isExpectedPe(context.getPictogramElement(), IDS.LABEL);
	}

	@Override
	public int getEditingType() {
		return IDirectEditing.TYPE_TEXT;
	}

	@Override
	public String getInitialValue(IDirectEditingContext context) {
		Instance obj = (Instance) getBusinessObjectForPictogramElement(context
				.getPictogramElement());
		return obj.getName();
	}

	@Override
	public void setValue(String value, IDirectEditingContext context) {
		PictogramElement pe = context.getPictogramElement();
		Instance obj = (Instance) getBusinessObjectForPictogramElement(pe);
		obj.setName(value);

		updatePictogramElement(pe);
	}

	@Override
	public String checkValueValid(String value, IDirectEditingContext context) {
		if (value.length() < 1) {
			return "Please enter any text as Instance name.";
		}
		if (!value.matches("[a-zA-Z0-9_]+")) {
			return "You can only use alphanumeric characters for Instance name";
		}

		// null -> value is valid
		return null;
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

		getFeatureProvider().getDirectEditingInfo().setActive(true);

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

		final Instance addedDomainObject = (Instance) context.getNewObject();

		// provide information to support direct-editing directly
		// after object creation (must be activated additionally)
		IDirectEditingInfo directEditingInfo = getFeatureProvider()
				.getDirectEditingInfo();

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

			link(shape, addedDomainObject);

			// set shape and graphics algorithm where the editor for
			// direct editing shall be opened after object creation
			directEditingInfo.setPictogramElement(shape);
			directEditingInfo.setGraphicsAlgorithm(text);
		}

		{
			// The line separator
			final Shape shape = peCreateService.createShape(containerShape,
					false);
			final int[] xy = { 0, LABEL_HEIGHT, TOTAL_WIDTH, LABEL_HEIGHT };
			final Polyline line = gaService.createPlainPolyline(shape, xy);
			line.setStyle(StyleUtil.getCommonStyle(getDiagram()));
		}

		// set container shape for direct editing after object creation
		directEditingInfo.setMainPictogramElement(containerShape);

		// We link graphical representation and domain model object
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
		return obj instanceof Instance;
	}

	@Override
	public boolean layout(ILayoutContext context) {
		if (isExpectedPe(context.getPictogramElement(), IDS.LABEL)) {
			Shape shape = (Shape) context.getPictogramElement();
			Instance instance = (Instance) getBusinessObjectForPictogramElement(shape);

			Text text = (Text) shape.getGraphicsAlgorithm();
			text.setValue(instance.getName());
			return true;
		}
		return false;
	}

	private boolean isExpectedPe(PictogramElement pe, IDS expectedType) {
		String objectType = Graphiti.getPeService()
				.getProperty(pe, PROPERTY_ID).getValue();
		return expectedType.name().equals(objectType);
	}
}
