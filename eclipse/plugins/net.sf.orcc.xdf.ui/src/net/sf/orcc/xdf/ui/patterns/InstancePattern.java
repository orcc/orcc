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

import net.sf.orcc.df.Actor;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Port;
import net.sf.orcc.xdf.ui.styles.StyleUtil;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
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
import org.eclipse.graphiti.features.context.impl.ResizeShapeContext;
import org.eclipse.graphiti.features.impl.Reason;
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
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;
import org.eclipse.graphiti.ui.services.GraphitiUi;
import org.eclipse.graphiti.ui.services.IUiLayoutService;

/**
 * This class configure as most features as possible, relative to Instances that
 * can be added to a Network.
 * 
 * @author Antoine Lorence
 * 
 */
public class InstancePattern extends AbstractPatternWithProperties {

	private static int TOTAL_MIN_WIDTH = 120;
	private static int TOTAL_MIN_HEIGHT = 140;
	private static int LABEL_HEIGHT = 40;
	private static int SEPARATOR = 1;

	private static int PORTS_AREAS_SPACE = 4;
	private static int PORT_SIDE_WITH = 12;
	private static int PORT_MARGIN = 2;

	private final String INSTANCE_ID = "INSTANCE";
	private final String LABEL_ID = "LABEL";
	private final String SEP_ID = "SEPARATOR";
	private final String INPUTS_ID = "INPUTS_AREA";
	private final String OUTPUTS_ID = "OUTPUTS_AREA";
	private final String[] validIds = { INSTANCE_ID, LABEL_ID, OUTPUTS_ID, INPUTS_ID };

	public InstancePattern() {
		super(null);
	}

	@Override
	public String getCreateName() {
		return "Instance";
	}

	@Override
	protected String[] getValidIdentifiers() {
		return validIds;
	}

	@Override
	public boolean isMainBusinessObjectApplicable(Object object) {
		if (object instanceof Port) {
			Port port = (Port) object;
			if (port.eContainer() instanceof Actor) {
				return true;
			}

			// In this case, we must ensure the network containing this port is
			// NOT the network corresponding to the diagram
		}
		return object instanceof Instance;
	}

	@Override
	protected boolean isPatternControlled(PictogramElement pe) {
		return super.isPatternControlled(pe)
				|| isMainBusinessObjectApplicable(getBusinessObjectForPictogramElement(pe));
	}

	@Override
	protected boolean isPatternRoot(PictogramElement pe) {
		return isExpectedPe(pe, INSTANCE_ID);
	}

	@Override
	public boolean canDirectEdit(IDirectEditingContext context) {
		boolean isText = context.getGraphicsAlgorithm() instanceof Text;
		return isText && isExpectedPe(context.getPictogramElement(), LABEL_ID);
	}

	@Override
	public int getEditingType() {
		return IDirectEditing.TYPE_TEXT;
	}

	@Override
	public String getInitialValue(IDirectEditingContext context) {
		Instance obj = (Instance) getBusinessObjectForPictogramElement(context.getPictogramElement());
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
		final IPeCreateService peCreateService = Graphiti.getPeCreateService();
		final IGaService gaService = Graphiti.getGaService();

		final Instance addedDomainObject = (Instance) context.getNewObject();

		// provide information to support direct-editing directly
		// after object creation (must be activated additionally)
		IDirectEditingInfo directEditingInfo = getFeatureProvider().getDirectEditingInfo();

		// Create the container
		final ContainerShape containerShape;
		{
			containerShape = peCreateService.createContainerShape(targetDiagram, true);
			final RoundedRectangle roundedRectangle = gaService.createPlainRoundedRectangle(containerShape, 5, 5);
			roundedRectangle.setStyle(StyleUtil.getStyleForInstance(getDiagram()));
			gaService.setLocationAndSize(roundedRectangle, context.getX(), context.getY(), TOTAL_MIN_WIDTH,
					TOTAL_MIN_HEIGHT);
		}

		{
			// The text label for Instance name
			final Shape shape = peCreateService.createShape(containerShape, false);
			final Text text = gaService.createPlainText(shape);
			text.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
			text.setVerticalAlignment(Orientation.ALIGNMENT_MIDDLE);
			text.setStyle(StyleUtil.getStyleForInstanceText(getDiagram()));
			gaService.setLocationAndSize(text, 0, 0, TOTAL_MIN_WIDTH, LABEL_HEIGHT);

			setIdentifier(shape, LABEL_ID);

			link(shape, addedDomainObject);

			// set shape and graphics algorithm where the editor for
			// direct editing shall be opened after object creation
			directEditingInfo.setPictogramElement(shape);
			directEditingInfo.setGraphicsAlgorithm(text);
		}

		{
			// The line separator
			final Shape shape = peCreateService.createShape(containerShape, false);
			final int[] xy = { 0, LABEL_HEIGHT, TOTAL_MIN_WIDTH, LABEL_HEIGHT };
			Polyline line = gaService.createPlainPolyline(shape, xy);
			line.setLineWidth(SEPARATOR);

			setIdentifier(shape, SEP_ID);
		}

		{
			// The containers for inputs and outputs ports
			final ContainerShape inShape = peCreateService.createContainerShape(containerShape, false);
			Rectangle rect = gaService.createInvisibleRectangle(inShape);
			gaService.setLocationAndSize(rect, 0, LABEL_HEIGHT + SEPARATOR, (TOTAL_MIN_WIDTH - PORTS_AREAS_SPACE) / 2,
					TOTAL_MIN_HEIGHT - (LABEL_HEIGHT + SEPARATOR));

			final ContainerShape outShape = peCreateService.createContainerShape(containerShape, false);
			rect = gaService.createInvisibleRectangle(outShape);
			gaService.setSize(rect, (TOTAL_MIN_WIDTH - PORTS_AREAS_SPACE) / 2, TOTAL_MIN_HEIGHT
					- (LABEL_HEIGHT + SEPARATOR));
			gaService.setLocation(rect, TOTAL_MIN_WIDTH - rect.getWidth(), LABEL_HEIGHT + SEPARATOR);

			setIdentifier(inShape, INPUTS_ID);
			setIdentifier(outShape, OUTPUTS_ID);
			link(inShape, addedDomainObject);
			link(outShape, addedDomainObject);
		}

		setIdentifier(containerShape, INSTANCE_ID);

		// set container shape for direct editing after object creation
		directEditingInfo.setMainPictogramElement(containerShape);

		// We link graphical representation and domain model object
		link(containerShape, addedDomainObject);

		return containerShape;
	}

	@Override
	public boolean canResizeShape(IResizeShapeContext context) {

		if (!isExpectedPe(context.getPictogramElement(), INSTANCE_ID)) {
			return false;
		}
		// Resize is always Ok for Instance. New size is set to minimal value
		// when needed
		return true;
	}

	@Override
	public void resizeShape(IResizeShapeContext context) {

		PictogramElement pe = context.getPictogramElement();

		int oldWidth = pe.getGraphicsAlgorithm().getWidth();
		int newWidth = Math.max(context.getWidth(), getInstanceMinWidth(context.getPictogramElement()));
		pe.getGraphicsAlgorithm().setWidth(newWidth);

		int oldHeight = pe.getGraphicsAlgorithm().getHeight();
		int newHeight = Math.max(context.getHeight(), getInstanceMinHeight(context.getPictogramElement()));
		pe.getGraphicsAlgorithm().setHeight(newHeight);

		// Recalculate position of the shape if direction is NORTH or EAST
		int rsDir = context.getDirection();
		if ((rsDir & IResizeShapeContext.DIRECTION_WEST) != 0) {
			int westMoveSize = oldWidth - newWidth;
			pe.getGraphicsAlgorithm().setX(pe.getGraphicsAlgorithm().getX() + westMoveSize);
		}
		if ((rsDir & IResizeShapeContext.DIRECTION_NORTH) != 0) {
			int northMoveSize = oldHeight - newHeight;
			pe.getGraphicsAlgorithm().setY(pe.getGraphicsAlgorithm().getY() + northMoveSize);
		}

		layoutPictogramElement(pe);
	}

	@Override
	public boolean canLayout(ILayoutContext context) {
		PictogramElement pe = context.getPictogramElement();

		if (isPatternControlled(pe)) {
			return true;
		}

		return false;
	}

	/**
	 * This function set position for all elements in an Instance Shape. It does
	 * not change anything on Y position, but it moves all shapes according to
	 * the current size of the top level shape
	 */
	@Override
	public boolean layout(ILayoutContext context) {
		PictogramElement pe = context.getPictogramElement();

		if (isExpectedPe(pe, INSTANCE_ID)) {

			int instWidth = pe.getGraphicsAlgorithm().getWidth();
			// int instHeight = pe.getGraphicsAlgorithm().getHeight();

			// Update width for label and separator. Position still unchanged
			GraphicsAlgorithm label = getSubShapeFromId((ContainerShape) pe, LABEL_ID).getGraphicsAlgorithm();
			label.setWidth(instWidth);
			Polyline sep = (Polyline) getSubShapeFromId((ContainerShape) pe, SEP_ID).getGraphicsAlgorithm();
			sep.getPoints().get(1).setX(instWidth);

			// Inputs area. Position unchanged, width must be updated
			Shape inPe = getSubShapeFromId((ContainerShape) pe, INPUTS_ID);
			GraphicsAlgorithm inGa = inPe.getGraphicsAlgorithm();
			for (Shape child : ((ContainerShape) inPe).getChildren()) {
				if (child.getGraphicsAlgorithm() instanceof Text) {
					Text txt = (Text) child.getGraphicsAlgorithm();
					txt.setWidth(getTextMinWidth(txt));

					txt.setX(PORT_SIDE_WITH + PORT_MARGIN);
				} else if (child.getGraphicsAlgorithm() instanceof Rectangle) {
					Rectangle rect = (Rectangle) child.getGraphicsAlgorithm();
					rect.setX(0);
				}
			}
			inGa.setWidth(getPortsAreaMinWidth(inPe));
			inGa.setHeight(getPortsAreaMinHeight(inPe));

			Shape outPe = getSubShapeFromId((ContainerShape) pe, OUTPUTS_ID);
			GraphicsAlgorithm outGa = outPe.getGraphicsAlgorithm();
			int outWidth = outGa.getWidth();
			for (Shape child : ((ContainerShape) outPe).getChildren()) {
				if (child.getGraphicsAlgorithm() instanceof Text) {
					Text txt = (Text) child.getGraphicsAlgorithm();
					txt.setWidth(getTextMinWidth(txt));

					txt.setX(outWidth - (PORT_SIDE_WITH + PORT_MARGIN + txt.getWidth()));
				} else if (child.getGraphicsAlgorithm() instanceof Rectangle) {
					Rectangle rect = (Rectangle) child.getGraphicsAlgorithm();
					rect.setX(outWidth - PORT_SIDE_WITH);
				}
			}
			int areaWidth = getPortsAreaMinWidth(outPe);
			outGa.setWidth(areaWidth);
			outGa.setHeight(getPortsAreaMinHeight(outPe));
			outGa.setX(instWidth - areaWidth);

			return true;
		}
		return false;
	}

	@Override
	public IReason updateNeeded(IUpdateContext context) {
		if (isExpectedPe(context.getPictogramElement(), LABEL_ID)) {
			PictogramElement pe = context.getPictogramElement();
			Text txt = (Text) pe.getGraphicsAlgorithm();
			Instance instance = (Instance) getBusinessObjectForPictogramElement(pe);
			if (txt.getValue().equals(instance.getName())) {
				return Reason.createTrueReason("The instance name has been updated outside of the diagram");
			}
		}

		return super.updateNeeded(context);
	}

	@Override
	public boolean update(IUpdateContext context) {
		PictogramElement pe = context.getPictogramElement();

		if (isExpectedPe(pe, LABEL_ID)) {
			Shape shape = (Shape) pe;
			Instance instance = (Instance) getBusinessObjectForPictogramElement(shape);

			Text text = (Text) shape.getGraphicsAlgorithm();
			text.setValue(instance.getName());
			return true;
		}
		return super.update(context);
	}

	/**
	 * Remove all inputs ports from the given PictogramElement
	 * 
	 * @param pe
	 */
	public void cleanInputsPorts(ContainerShape cs) {
		ContainerShape ctr = (ContainerShape) getSubShapeFromId(cs, INPUTS_ID);
		List<Shape> copyList = new ArrayList<Shape>(ctr.getChildren());
		for (Shape shape : copyList) {
			EcoreUtil.delete(shape, true);
		}
		layoutPictogramElement(ctr);

	}

	/**
	 * Remove all inputs ports from the given PictogramElement
	 * 
	 * @param pe
	 */
	public void cleanOutputsPorts(ContainerShape cs) {
		ContainerShape ctr = (ContainerShape) getSubShapeFromId(cs, OUTPUTS_ID);
		List<Shape> copyList = new ArrayList<Shape>(ctr.getChildren());
		for (Shape shape : copyList) {
			EcoreUtil.delete(shape, true);
		}
		layoutPictogramElement(ctr);
	}

	public void addInputsPorts(ContainerShape cs, EList<Port> ports) {
		final ContainerShape portsCtr = (ContainerShape) getSubShapeFromId(cs, INPUTS_ID);

		// final IPeService peService = Graphiti.getPeService();
		final IPeCreateService peCreateService = Graphiti.getPeCreateService();
		final IGaService gaService = Graphiti.getGaService();

		int i = 0;
		for (Port port : ports) {
			// Create text
			Shape txtShape = peCreateService.createShape(portsCtr, false);
			Text txt = gaService.createText(txtShape, port.getName());
			txt.setStyle(StyleUtil.getStyleForInstanceText(getDiagram()));
			txt.setVerticalAlignment(Orientation.ALIGNMENT_MIDDLE);

			// Create square for port anchor
			Shape rectShape = peCreateService.createShape(portsCtr, false);
			Rectangle rect = gaService.createPlainRectangle(rectShape);
			rect.setStyle(StyleUtil.getStyleForInstancePort(getDiagram()));

			// Calculate the size of a complete port line
			int txtAndSquareHeight = Math.max(getTextMinHeight(txt), PORT_SIDE_WITH);

			// Set properties on square displayed before text
			gaService.setSize(rect, PORT_SIDE_WITH, PORT_SIDE_WITH);
			gaService.setLocation(rect, 0, i * (txtAndSquareHeight + PORT_MARGIN) + PORT_MARGIN);

			// Set properties on port text (width and x coord will be set by
			// layout() feature
			txt.setHeight(txtAndSquareHeight);
			txt.setY(i * (txtAndSquareHeight + PORT_MARGIN) + PORT_MARGIN);

			// TODO: fix this when connection will be implemented
			FixPointAnchor anchor = peCreateService.createFixPointAnchor(portsCtr);
			anchor.setReferencedGraphicsAlgorithm(rect);
			++i;
		}
		resizeShapeToMinimal(cs);
		layoutPictogramElement(cs);
	}

	public void addOutputsPorts(ContainerShape cs, EList<Port> ports) {
		final ContainerShape portsCtr = (ContainerShape) getSubShapeFromId(cs, OUTPUTS_ID);

		// final IPeService peService = Graphiti.getPeService();
		final IPeCreateService peCreateService = Graphiti.getPeCreateService();
		final IGaService gaService = Graphiti.getGaService();

		int i = 0;
		for (Port port : ports) {
			// Create text
			Shape txtShape = peCreateService.createShape(portsCtr, false);
			Text txt = gaService.createText(txtShape, port.getName());
			txt.setStyle(StyleUtil.getStyleForInstanceText(getDiagram()));
			txt.setVerticalAlignment(Orientation.ALIGNMENT_MIDDLE);

			// Create square for port anchor
			Shape rectShape = peCreateService.createShape(portsCtr, false);
			Rectangle rect = gaService.createPlainRectangle(rectShape);
			rect.setStyle(StyleUtil.getStyleForInstancePort(getDiagram()));

			// Calculate the size of a complete port line
			int txtAndSquareHeight = Math.max(getTextMinHeight(txt), PORT_SIDE_WITH);
			// Calculate the required width for the area
			int areaWidth = getPortsAreaMinWidth(portsCtr);

			// Set properties on square displayed before text
			gaService.setSize(rect, PORT_SIDE_WITH, PORT_SIDE_WITH);
			gaService.setLocation(rect, areaWidth - PORT_SIDE_WITH, i * (txtAndSquareHeight + PORT_MARGIN)
					+ PORT_MARGIN);

			// Set properties on port text (width and x coord will be set by
			// layout() feature
			txt.setHeight(txtAndSquareHeight);
			txt.setY(i * (txtAndSquareHeight + PORT_MARGIN) + PORT_MARGIN);

			// TODO: fix this when connection will be implemented
			FixPointAnchor anchor = peCreateService.createFixPointAnchor(portsCtr);
			anchor.setReferencedGraphicsAlgorithm(rect);
			++i;
		}
		resizeShapeToMinimal(cs);
		layoutPictogramElement(cs);
	}

	private void resizeShapeToMinimal(PictogramElement pe) {
		if (!isExpectedPe(pe, INSTANCE_ID)) {
			return;
		}

		ResizeShapeContext ctxt = new ResizeShapeContext((Shape) pe);
		ctxt.setWidth(getInstanceMinWidth(pe));
		ctxt.setHeight(getInstanceMinHeight(pe));

		resizeShape(ctxt);
	}

	private int getInstanceMinHeight(PictogramElement pe) {
		if (!isExpectedPe(pe, INSTANCE_ID)) {
			return -1;
		}

		Shape inArea = getSubShapeFromId((ContainerShape) pe, INPUTS_ID);
		Shape outArea = getSubShapeFromId((ContainerShape) pe, OUTPUTS_ID);

		int portsAreaMinHeight = Math.max(getPortsAreaMinHeight(inArea), getPortsAreaMinHeight(outArea));
		int newHeight = LABEL_HEIGHT + SEPARATOR + portsAreaMinHeight;

		return Math.max(newHeight, TOTAL_MIN_HEIGHT);
	}

	private int getInstanceMinWidth(PictogramElement pe) {
		if (!isExpectedPe(pe, INSTANCE_ID)) {
			return -1;
		}

		Shape inArea = getSubShapeFromId((ContainerShape) pe, INPUTS_ID);
		Shape outArea = getSubShapeFromId((ContainerShape) pe, OUTPUTS_ID);

		return getPortsAreaMinWidth(inArea) + getPortsAreaMinWidth(outArea) + PORTS_AREAS_SPACE;
	}

	private int getPortsAreaMinHeight(PictogramElement pe) {
		if (!isExpectedPe(pe, INPUTS_ID) && !isExpectedPe(pe, OUTPUTS_ID)) {
			return -1;
		}

		int nbPorts = 0;

		Text txt = null;
		for (Shape child : ((ContainerShape) pe).getChildren()) {
			if (child.getGraphicsAlgorithm() instanceof Text) {
				// To resize height if needed
				++nbPorts;
				// To resize width if needed
				txt = (Text) child.getGraphicsAlgorithm();
			}
		}

		if (txt != null) {
			int fullPortHeight = Math.max(getTextMinHeight(txt), PORT_SIDE_WITH);
			return nbPorts * (fullPortHeight + PORT_MARGIN) + PORT_MARGIN * 2;
		}
		return TOTAL_MIN_HEIGHT - LABEL_HEIGHT - SEPARATOR;
	}

	private int getPortsAreaMinWidth(PictogramElement pe) {
		if (!isExpectedPe(pe, INPUTS_ID) && !isExpectedPe(pe, OUTPUTS_ID)) {
			return -1;
		}

		int minSize = (TOTAL_MIN_WIDTH - PORTS_AREAS_SPACE) / 2;

		for (Shape child : ((ContainerShape) pe).getChildren()) {
			if (child.getGraphicsAlgorithm() instanceof Text) {
				// To resize width if needed
				Text txt = (Text) child.getGraphicsAlgorithm();
				int txtWidth = getTextMinWidth(txt);

				if (PORT_SIDE_WITH + PORT_MARGIN + txtWidth > minSize) {
					minSize = PORT_SIDE_WITH + PORT_MARGIN + txtWidth;
				}
			}
		}
		return minSize;
	}

	private int getTextMinWidth(Text text) {

		final IUiLayoutService uiLayoutService = GraphitiUi.getUiLayoutService();
		if (text.getFont() != null) {
			return uiLayoutService.calculateTextSize(text.getValue(), text.getFont()).getWidth();
		} else if (text.getStyle().getFont() != null) {
			return uiLayoutService.calculateTextSize(text.getValue(), text.getStyle().getFont()).getWidth();
		}

		return -1;
	}

	private int getTextMinHeight(Text text) {

		final IUiLayoutService uiLayoutService = GraphitiUi.getUiLayoutService();
		if (text.getFont() != null) {
			return uiLayoutService.calculateTextSize(text.getValue(), text.getFont()).getHeight();
		} else if (text.getStyle().getFont() != null) {
			return uiLayoutService.calculateTextSize(text.getValue(), text.getStyle().getFont()).getHeight();
		}

		return -1;
	}
}
