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

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Port;
import net.sf.orcc.xdf.ui.styles.StyleUtil;
import net.sf.orcc.xdf.ui.util.XdfUtil;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.graphiti.datatypes.IDimension;
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
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.FixPointAnchor;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.pattern.AbstractPattern;
import org.eclipse.graphiti.pattern.IPattern;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;
import org.eclipse.graphiti.services.IPeService;
import org.eclipse.graphiti.ui.services.GraphitiUi;
import org.eclipse.graphiti.ui.services.IUiLayoutService;

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

	private static int PORTS_LIST_WIDTH = 58;
	private static int PORT_SIDE_WITH = 12;
	private static int PORT_MARGIN = 2;

	private int separator_size;

	private static String PROPERTY_ID = "XDF_ID";

	public enum IDS {
		LABEL, INPUTS, OUTPUTS
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
			text.setVerticalAlignment(Orientation.ALIGNMENT_MIDDLE);
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

			separator_size = line.getStyle().getLineWidth();
		}

		{
			// The containers for inputs and outputs ports
			final ContainerShape inShape = peCreateService
					.createContainerShape(containerShape, false);
			Rectangle r = gaService.createInvisibleRectangle(inShape);
			gaService.setLocationAndSize(r, 0, LABEL_HEIGHT + separator_size,
					PORTS_LIST_WIDTH, TOTAL_HEIGHT
							- (LABEL_HEIGHT + separator_size));

			final ContainerShape outShape = peCreateService
					.createContainerShape(containerShape, false);
			r = gaService.createInvisibleRectangle(outShape);
			gaService.setLocationAndSize(r, TOTAL_WIDTH - PORTS_LIST_WIDTH,
					LABEL_HEIGHT + separator_size, PORTS_LIST_WIDTH,
					TOTAL_HEIGHT - (LABEL_HEIGHT + separator_size));

			peService.setPropertyValue(inShape, PROPERTY_ID, IDS.INPUTS.name());
			peService.setPropertyValue(outShape, PROPERTY_ID,
					IDS.OUTPUTS.name());
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

		if (obj instanceof Instance) {
			return true;
		}
		if (isExpectedPe(elt, IDS.INPUTS) || isExpectedPe(elt, IDS.OUTPUTS)) {
			return true;
		}
		if (isExpectedPe(elt, IDS.LABEL)) {
			return true;
		}

		return false;
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

	@Override
	public IReason updateNeeded(IUpdateContext context) {
		// TODO Auto-generated method stub
		return super.updateNeeded(context);
	}

	/**
	 * Remove all inputs ports from the given PictogramElement
	 * 
	 * @param pe
	 */
	public void cleanInputsPorts(ContainerShape cs) {
		ContainerShape ctr = getContainer(cs, IDS.INPUTS);
		List<Shape> copyList = new ArrayList<Shape>(ctr.getChildren());
		for (Shape shape : copyList) {
			EcoreUtil.delete(shape, true);
		}
		updatePictogramElement(ctr);
	}

	/**
	 * Remove all inputs ports from the given PictogramElement
	 * 
	 * @param pe
	 */
	public void cleanOutputsPorts(ContainerShape cs) {
		ContainerShape ctr = getContainer(cs, IDS.OUTPUTS);
		List<Shape> copyList = new ArrayList<Shape>(ctr.getChildren());
		for (Shape shape : copyList) {
			EcoreUtil.delete(shape, true);
		}
		updatePictogramElement(ctr);
	}

	public void addInputsPorts(ContainerShape cs, EList<Port> ports) {
		final ContainerShape portsCtr = getContainer(cs, IDS.INPUTS);

		// final IPeService peService = Graphiti.getPeService();
		final IPeCreateService peCreateService = Graphiti.getPeCreateService();
		final IGaService gaService = Graphiti.getGaService();
		final IUiLayoutService uiLayoutService = GraphitiUi.getUiLayoutService();

		int i = 0;
		for (Port port : ports) {
			// Create text
			Shape txtShape = peCreateService.createShape(portsCtr, false);
			Text txt = gaService.createText(txtShape, port.getName());
			txt.setStyle(StyleUtil.getStyleForInstanceText(getDiagram()));

			// Create square for port anchor
			Shape rectShape = peCreateService.createShape(portsCtr, false);
			Rectangle rect = gaService.createPlainRectangle(rectShape);
			rect.setStyle(StyleUtil.getStyleForInstancePort(getDiagram()));

			int txtHeight = uiLayoutService.calculateTextSize(txt.getValue(), txt.getStyle().getFont()).getHeight();
			int baseY = i * (Math.max(txtHeight, PORT_SIDE_WITH) + PORT_MARGIN * 2) + PORT_MARGIN;

			gaService.setLocation(txt, PORT_SIDE_WITH + PORT_MARGIN, baseY);
			gaService.setSize(txt, -1, txtHeight);
			txt.setVerticalAlignment(Orientation.ALIGNMENT_MIDDLE);
			gaService.setLocationAndSize(rect, 0, baseY, PORT_SIDE_WITH, PORT_SIDE_WITH);

			FixPointAnchor anchor = peCreateService
					.createFixPointAnchor(portsCtr);
			anchor.setReferencedGraphicsAlgorithm(rect);
			++i;
		}
		GraphicsAlgorithm portCtrGa = portsCtr.getGraphicsAlgorithm();
		gaService.setSize(portCtrGa, portCtrGa.getWidth(), portCtrGa.getHeight());
		updatePictogramElement(portsCtr);
	}

	public void addOutputsPorts(ContainerShape cs, EList<Port> ports) {
		final ContainerShape portsCtr = getContainer(cs, IDS.OUTPUTS);

		// final IPeService peService = Graphiti.getPeService();
		final IPeCreateService peCreateService = Graphiti.getPeCreateService();
		final IGaService gaService = Graphiti.getGaService();
		final IUiLayoutService uiLayoutService = GraphitiUi.getUiLayoutService();

		int i = 0;
		for (Port port : ports) {
			// Create text
			Shape txtShape = peCreateService.createShape(portsCtr, false);
			Text txt = gaService.createText(txtShape, port.getName());
			txt.setStyle(StyleUtil.getStyleForInstanceText(getDiagram()));

			// Create square for port anchor
			Shape rectShape = peCreateService.createShape(portsCtr, false);
			Rectangle rect = gaService.createPlainRectangle(rectShape);
			rect.setStyle(StyleUtil.getStyleForInstancePort(getDiagram()));

			final IDimension txtSize = uiLayoutService.calculateTextSize(txt.getValue(), txt.getStyle().getFont());
			int txtWidth = txtSize.getWidth();
			int txtHeight = txtSize.getHeight();

			int baseY = i * (Math.max(txtHeight, PORT_SIDE_WITH) + PORT_MARGIN * 2) + PORT_MARGIN;

			gaService.setLocation(txt, PORTS_LIST_WIDTH - (txtWidth + PORT_SIDE_WITH + PORT_MARGIN), baseY);
			gaService.setSize(txt, -1, txtHeight);
			txt.setVerticalAlignment(Orientation.ALIGNMENT_MIDDLE);
			gaService
					.setLocationAndSize(rect, PORTS_LIST_WIDTH - PORT_SIDE_WITH, baseY, PORT_SIDE_WITH, PORT_SIDE_WITH);

			FixPointAnchor anchor = peCreateService.createFixPointAnchor(portsCtr);
			anchor.setReferencedGraphicsAlgorithm(rect);
			++i;
		}
		GraphicsAlgorithm portCtrGa = portsCtr.getGraphicsAlgorithm();
		gaService.setSize(portCtrGa, portCtrGa.getWidth(), portCtrGa.getHeight());
		updatePictogramElement(portsCtr);
	}

	private boolean isExpectedPe(PictogramElement pe, IDS expectedType) {
		String objectType = Graphiti.getPeService()
				.getProperty(pe, PROPERTY_ID).getValue();
		return expectedType.name().equals(objectType);
	}
	
	private ContainerShape getContainer(ContainerShape cs, IDS id) {
		return (ContainerShape) XdfUtil.getShapeFromProperty(cs, PROPERTY_ID,
				id.name());
	}
}
