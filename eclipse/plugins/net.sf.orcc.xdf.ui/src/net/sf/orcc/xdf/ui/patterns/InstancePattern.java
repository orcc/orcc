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
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.xdf.ui.styles.StyleUtil;
import net.sf.orcc.xdf.ui.util.XdfUtil;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
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
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.FixPointAnchor;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaLayoutService;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;

/**
 * This class configure as most features as possible, relative to Instances that
 * can be added to a Network.
 * 
 * @author Antoine Lorence
 * 
 */
public class InstancePattern extends AbstractPatternWithProperties {

	// Minimal and default width for an instance shape
	private static final int TOTAL_MIN_WIDTH = 120;
	// Minimal and default height for an instance shape
	private static final int TOTAL_MIN_HEIGHT = 140;
	// Height of instance label (displaying instance name)
	private static final int LABEL_HEIGHT = 40;
	// Width of the line shape used as separator
	private static final int SEPARATOR = 1;

	// Minimal space between input and output areas
	private static final int PORTS_AREAS_SPACE = 4;
	// Width of the square representing a port
	private static final int PORT_SIDE_WITH = 12;
	// Space set around a port square
	private static final int PORT_MARGIN = 2;

	// Identifiers for important shape of an instance
	private static final String INSTANCE_ID = "INSTANCE";
	private static final String LABEL_ID = "INSTANCE_LABEL";
	private static final String SEP_ID = "INSTANCE_SEPARATOR";
	private static final String INPUTS_ID = "INPUTS_AREA";
	private static final String OUTPUTS_ID = "OUTPUTS_AREA";
	private static final String[] validIds = { INSTANCE_ID, LABEL_ID, SEP_ID, INPUTS_ID, OUTPUTS_ID };

	private static final String REFINEMENT_KEY = "refinment";

	private enum PortsType {
		INPUTS, OUTPUTS
	}

	public InstancePattern() {
		super(null);
	}

	@Override
	public String getCreateName() {
		return "Instance";
	}

	@Override
	public String getCreateDescription() {
		return "Create a new instance, to encapsulate a network or an actor";
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

			// TODO: In this case, we must ensure the network containing this
			// port is NOT the network corresponding to the diagram. To do so,
			// we need to have a Network instance created with the diagram
		}
		return object instanceof Instance;
	}

	@Override
	protected boolean isPatternControlled(PictogramElement pe) {

		// If PE is one of the objects with a defined ID
		if (super.isPatternControlled(pe)) {
			return true;
		}

		// Port squares and texts are not directly identified, but texts are
		// contained in OUTPUTS_ID or INPUTSS_ID and squares ports are contained
		// in the top level shape
		if (super.isPatternControlled((PictogramElement) pe.eContainer())) {
			return true;
		}

		return false;
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
		// We create the instance in a diagram
		if (context.getTargetContainer() instanceof Diagram) {
			// A network is associated to this diagram
			Object bo = getBusinessObjectForPictogramElement(context.getTargetContainer());
			if (bo instanceof Network) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Object[] create(ICreateContext context) {
		Instance newInstance = null;
		// TODO: allow to create from an eResource (dropped from project
		// explorer)
		if (true) {
			newInstance = DfFactory.eINSTANCE.createInstance();
		}

		// Add the new Instance to the current Network
		Network network = (Network) getBusinessObjectForPictogramElement(getDiagram());
		network.add(newInstance);

		// Request adding the shape to the diagram
		addGraphicalRepresentation(context, newInstance);

		// Activate direct editing on creation. A label input appear to allow
		// user to type a name for the instance
		getFeatureProvider().getDirectEditingInfo().setActive(true);

		return new Object[] { newInstance };
	}

	@Override
	public boolean canAdd(IAddContext context) {
		if (context.getTargetContainer() instanceof Diagram) {
			return isMainBusinessObjectApplicable(context.getNewObject());
		}
		return false;
	}

	@Override
	public PictogramElement add(IAddContext context) {
		final Diagram targetDiagram = (Diagram) context.getTargetContainer();
		final IPeCreateService peCreateService = Graphiti.getPeCreateService();
		final IGaService gaService = Graphiti.getGaService();

		final Instance addedDomainObject = (Instance) context.getNewObject();

		// provide information to support direct-editing directly
		// after object creation (must be activated additionally)
		final IDirectEditingInfo directEditingInfo = getFeatureProvider().getDirectEditingInfo();

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
			final Rectangle inRect = gaService.createInvisibleRectangle(inShape);
			final ContainerShape outShape = peCreateService.createContainerShape(containerShape, false);
			final Rectangle outRect = gaService.createInvisibleRectangle(outShape);

			final int portAreaY = LABEL_HEIGHT + SEPARATOR;
			final int portAreaLeftRightMargin = PORT_SIDE_WITH + PORT_MARGIN;
			// Without content, sizes for in and out areas are the same
			final int portAreaW = getPortsAreaMinWidth(inShape);
			final int portAreaH = getPortsAreaMinHeight(inShape);

			// Set sizes and location for both areas
			gaService.setLocationAndSize(inRect, portAreaLeftRightMargin, portAreaY, portAreaW, portAreaH);
			gaService.setLocationAndSize(outRect, TOTAL_MIN_WIDTH - portAreaLeftRightMargin - portAreaW, portAreaY,
					portAreaW, portAreaH);

			setIdentifier(inShape, INPUTS_ID);
			setIdentifier(outShape, OUTPUTS_ID);
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

		final PictogramElement pe = context.getPictogramElement();

		final int oldWidth = pe.getGraphicsAlgorithm().getWidth();
		final int newWidth = Math.max(context.getWidth(), getInstanceMinWidth(context.getPictogramElement()));
		pe.getGraphicsAlgorithm().setWidth(newWidth);

		final int oldHeight = pe.getGraphicsAlgorithm().getHeight();
		final int newHeight = Math.max(context.getHeight(), getInstanceMinHeight(context.getPictogramElement()));
		pe.getGraphicsAlgorithm().setHeight(newHeight);

		// Recalculate position of the shape if direction is NORTH or EAST
		final int rsDir = context.getDirection();
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
		final PictogramElement pe = context.getPictogramElement();

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
		final PictogramElement pe = context.getPictogramElement();
		final IGaLayoutService gaLayoutService = Graphiti.getGaLayoutService();

		if (!isPatternRoot(pe)) {
			return false;
		}

		final int instanceWidth = pe.getGraphicsAlgorithm().getWidth();

		// Update width for label and separator. Position still unchanged
		final GraphicsAlgorithm label = getPeFromIdentifier(pe, LABEL_ID).getGraphicsAlgorithm();
		label.setWidth(instanceWidth);
		final Polyline sep = (Polyline) getPeFromIdentifier(pe, SEP_ID).getGraphicsAlgorithm();
		sep.getPoints().get(1).setX(instanceWidth);

		{
			// Inputs area. Position unchanged, size must be updated
			final Shape portsPe = (Shape) getPeFromIdentifier(pe, INPUTS_ID);
			final GraphicsAlgorithm portsGa = portsPe.getGraphicsAlgorithm();

			final int newAreaWidth = getPortsAreaMinWidth(portsPe);
			gaLayoutService.setSize(portsGa, newAreaWidth, getPortsAreaMinHeight(portsPe));

			for (Shape child : ((ContainerShape) portsPe).getChildren()) {
				if (child.getGraphicsAlgorithm() instanceof Text) {
					Text txt = (Text) child.getGraphicsAlgorithm();
					txt.setWidth(newAreaWidth);
				}
			}
		}

		{
			// Outputs area, position and size need to be recalculated
			final Shape portsPe = (Shape) getPeFromIdentifier(pe, OUTPUTS_ID);
			final GraphicsAlgorithm portsGa = portsPe.getGraphicsAlgorithm();

			final int newAreaWidth = getPortsAreaMinWidth(portsPe);
			gaLayoutService.setSize(portsGa, newAreaWidth, getPortsAreaMinHeight(portsPe));
			portsGa.setX(instanceWidth - newAreaWidth - PORT_SIDE_WITH - PORT_MARGIN);

			for (Shape child : ((ContainerShape) portsPe).getChildren()) {
				if (child.getGraphicsAlgorithm() instanceof Text) {
					Text txt = (Text) child.getGraphicsAlgorithm();
					txt.setWidth(newAreaWidth);
				}
			}
		}

		{
			// Ports square anchors. Only output ports location have to be
			// recalculated
			final AnchorContainer root = (AnchorContainer) pe;
			for (Anchor anchor : root.getAnchors()) {
				final Point location = ((FixPointAnchor) anchor).getLocation();
				if (location.getX() != 0) {
					location.setX(instanceWidth - PORT_SIDE_WITH);
				}
			}
		}

		return true;
	}

	@Override
	public IReason updateNeeded(IUpdateContext context) {
		final PictogramElement pe = context.getPictogramElement();
		if (isExpectedPe(pe, LABEL_ID)) {
			Text txt = (Text) pe.getGraphicsAlgorithm();
			Instance instance = (Instance) getBusinessObjectForPictogramElement(pe);
			if (!txt.getValue().equals(instance.getName())) {
				return Reason.createTrueReason("The instance name has been updated from outside diagram");
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
	 * Update the refinement (Instance or Network) for the instance linked to
	 * the given pe. The input and output ports of the given entity are added to
	 * the shape.
	 * 
	 * This method automatically update sizes and layouts for the content.
	 * 
	 * @param pe
	 * @param entity
	 */
	public void setInstanceRefinment(PictogramElement pe, EObject entity) {
		if (!isPatternRoot(pe)) {
			return;
		}
		if (!(entity instanceof Actor || entity instanceof Network)) {
			return;
		}

		// Set the current instance's entity
		final Instance instance = (Instance) getBusinessObjectForPictogramElement(pe);
		instance.setEntity(entity);
		Graphiti.getPeService()
				.setPropertyValue(pe, REFINEMENT_KEY, entity.eResource().getURI().toPlatformString(true));

		final ContainerShape topLevelShape = (ContainerShape) pe;
		final ContainerShape inCtrShape = (ContainerShape) getPeFromIdentifier(pe, INPUTS_ID);
		final ContainerShape outCtrShape = (ContainerShape) getPeFromIdentifier(pe, OUTPUTS_ID);

		// Clean inputs & outputs ports text
		List<Shape> portsTxts = new ArrayList<Shape>(inCtrShape.getChildren());
		portsTxts.addAll(outCtrShape.getChildren());
		for (Shape shape : portsTxts) {
			EcoreUtil.delete(shape, true);
		}

		// Clean ports anchors
		List<Anchor> anchors = new ArrayList<Anchor>(((AnchorContainer) pe).getAnchors());
		for (Anchor anchor : anchors) {
			EcoreUtil.delete(anchor, true);
		}

		// Add ports
		if (instance.isActor()) {
			addPorts(topLevelShape, instance.getActor().getInputs(), PortsType.INPUTS);
			addPorts(topLevelShape, instance.getActor().getOutputs(), PortsType.OUTPUTS);
		} else {
			addPorts(topLevelShape, instance.getNetwork().getInputs(), PortsType.INPUTS);
			addPorts(topLevelShape, instance.getNetwork().getOutputs(), PortsType.OUTPUTS);
		}

		// Resize to minimal size.
		resizeShapeToMinimal(pe);
	}

	/**
	 * Add the given list of ports to the right area, according to the given
	 * portType.
	 * 
	 * This method add port names to the area according to the right port type.
	 * It also create FixPointAnchors as children if given topLevelShape, and
	 * set position for each of these elements. Port texts locations are
	 * relative to their respective ports area (input or output) when port
	 * square are contained in their anchor, which is relative to the
	 * topLevelShape
	 * 
	 * @param topLevelShape
	 *            The instance shape
	 * @param ports
	 *            The list of ports
	 * @param portType
	 *            The type of ports (inputs or outputs)
	 */
	private void addPorts(ContainerShape topLevelShape, List<Port> ports, PortsType portType) {
		final IPeCreateService peCreateService = Graphiti.getPeCreateService();
		final IGaService gaService = Graphiti.getGaService();

		final ContainerShape portsCtrShape = (ContainerShape) (portType == PortsType.INPUTS ? getPeFromIdentifier(
				topLevelShape, INPUTS_ID) : getPeFromIdentifier(topLevelShape, OUTPUTS_ID));

		final int currentAreaWidth = portsCtrShape.getGraphicsAlgorithm().getWidth();
		final int portAreaY = LABEL_HEIGHT + SEPARATOR;

		final int portXPos;
		final Orientation hOrientation;

		if (portType == PortsType.INPUTS) {
			hOrientation = Orientation.ALIGNMENT_LEFT;
			portXPos = 0;
		} else {
			hOrientation = Orientation.ALIGNMENT_RIGHT;
			portXPos = topLevelShape.getGraphicsAlgorithm().getX() - PORT_SIDE_WITH;
		}

		int portYPos;
		int i = 0;
		for (Port port : ports) {
			// Create text
			Shape txtShape = peCreateService.createShape(portsCtrShape, false);
			Text txt = gaService.createText(txtShape, port.getName());
			txt.setStyle(StyleUtil.getStyleForInstanceText(getDiagram()));
			txt.setVerticalAlignment(Orientation.ALIGNMENT_MIDDLE);
			txt.setHorizontalAlignment(hOrientation);

			// Calculate the size of a complete port line
			int txtAndSquareHeight = Math.max(XdfUtil.getTextMinHeight(txt), PORT_SIDE_WITH);
			txt.setHeight(txtAndSquareHeight);

			portYPos = i * (txtAndSquareHeight + PORT_MARGIN) + PORT_MARGIN;

			// Set properties on port text
			gaService.setLocationAndSize(txt, 0, portYPos, currentAreaWidth, txtAndSquareHeight);

			FixPointAnchor fpAnchor = peCreateService.createFixPointAnchor(topLevelShape);
			fpAnchor.setLocation(gaService.createPoint(portXPos, portYPos + portAreaY));

			Rectangle portRect = gaService.createRectangle(fpAnchor);
			portRect.setStyle(StyleUtil.getStyleForInstancePort(getDiagram()));
			gaService.setLocationAndSize(portRect, 0, 0, PORT_SIDE_WITH, PORT_SIDE_WITH);

			link(fpAnchor, port);

			++i;
		}
	}

	/**
	 * Resize the current instance shape to its minimal width and height. The
	 * layout() method will be called after, directly from the resize feature.
	 * 
	 * @param pe
	 *            The instance pictogram element
	 */
	private void resizeShapeToMinimal(PictogramElement pe) {
		if (!isExpectedPe(pe, INSTANCE_ID)) {
			return;
		}

		ResizeShapeContext ctxt = new ResizeShapeContext((Shape) pe);
		ctxt.setWidth(getInstanceMinWidth(pe));
		ctxt.setHeight(getInstanceMinHeight(pe));

		resizeShape(ctxt);
	}

	/**
	 * Calculate the minimal height needed to display the longest port list
	 * (between inputs and outputs ones)
	 * 
	 * @param pe
	 *            The port area pictogram element
	 * @return The height as integer
	 */
	private int getInstanceMinHeight(PictogramElement pe) {
		if (!isExpectedPe(pe, INSTANCE_ID)) {
			return -1;
		}

		Shape inArea = (Shape) getPeFromIdentifier(pe, INPUTS_ID);
		Shape outArea = (Shape) getPeFromIdentifier(pe, OUTPUTS_ID);

		int portsAreaMinHeight = Math.max(getPortsAreaMinHeight(inArea), getPortsAreaMinHeight(outArea));
		int newHeight = LABEL_HEIGHT + SEPARATOR + portsAreaMinHeight;

		return Math.max(newHeight, TOTAL_MIN_HEIGHT);
	}

	/**
	 * Calculate the minimal width needed to display all contents of an instance
	 * shape.
	 * 
	 * This width is calculated from minimal sizes of both input an output ports
	 * areas.
	 * 
	 * @param pe
	 *            The port area pictogram element
	 * @return The width as integer
	 */
	private int getInstanceMinWidth(PictogramElement pe) {
		if (!isExpectedPe(pe, INSTANCE_ID)) {
			return -1;
		}

		Shape inArea = (Shape) getPeFromIdentifier(pe, INPUTS_ID);
		Shape outArea = (Shape) getPeFromIdentifier(pe, OUTPUTS_ID);

		return getPortsAreaMinWidth(inArea) + getPortsAreaMinWidth(outArea) + PORTS_AREAS_SPACE + 2
				* (PORT_SIDE_WITH + PORT_MARGIN);
	}

	/**
	 * Calculate the minimal height needed to display all ports names in a port
	 * area.
	 * 
	 * If the given pe does not contain any port text, the minimal size is
	 * returned, calculated from the default sizes defined for an instance
	 * shape.
	 * 
	 * @param pe
	 *            The port area pictogram element
	 * @return The height as integer
	 */
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
			int fullPortHeight = Math.max(XdfUtil.getTextMinHeight(txt), PORT_SIDE_WITH);
			return nbPorts * (fullPortHeight + PORT_MARGIN) + PORT_MARGIN * 2;
		}
		return TOTAL_MIN_HEIGHT - LABEL_HEIGHT - SEPARATOR;
	}

	/**
	 * Calculate the minimal width needed to display the longest port name in a
	 * port area.
	 * 
	 * If the given pe does not contain any port text, the minimal size is
	 * returned, calculated from the default sizes defined for an instance
	 * shape. If all ports name are shorter than the minimal, the minimal is
	 * also returned.
	 * 
	 * @param pe
	 *            The port area pictogram element
	 * @return The width as integer
	 */
	private int getPortsAreaMinWidth(PictogramElement pe) {
		if (!isExpectedPe(pe, INPUTS_ID) && !isExpectedPe(pe, OUTPUTS_ID)) {
			return -1;
		}

		// Default minimal width for an ports area
		int minWidth = (TOTAL_MIN_WIDTH - PORTS_AREAS_SPACE) / 2 - PORT_SIDE_WITH - PORT_MARGIN;

		for (Shape child : ((ContainerShape) pe).getChildren()) {
			if (child.getGraphicsAlgorithm() instanceof Text) {
				// To resize width if needed
				Text txt = (Text) child.getGraphicsAlgorithm();
				int minTxtWidth = XdfUtil.getTextMinWidth(txt);

				if (minTxtWidth > minWidth) {
					minWidth = minTxtWidth;
				}
			}
		}
		return minWidth;
	}

	/**
	 * Return the name of the instance without using the business object
	 * 
	 * @param pe
	 * @return
	 */
	public String getNameFromShape(PictogramElement pe) {
		if (isPatternRoot(pe)) {
			final PictogramElement txtShape = getPeFromIdentifier(pe, LABEL_ID);
			final Text text = (Text) txtShape.getGraphicsAlgorithm();
			return text.getValue();
		}
		return "";
	}

	public EObject getRefinementFromShape(PictogramElement pe) {
		if (isPatternRoot(pe)) {
			String plateforStringUri = Graphiti.getPeService().getPropertyValue(pe, REFINEMENT_KEY);
			Resource res = XdfUtil.getCommonresourceSet().getResource(
					URI.createPlatformResourceURI(plateforStringUri, true),
					true);
			if (res.getContents().size() > 0) {
				return res.getContents().get(0);
			}
		}
		return null;
	}
}
