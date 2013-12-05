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
import net.sf.orcc.util.OrccLogger;
import net.sf.orcc.xdf.ui.styles.StyleUtil;
import net.sf.orcc.xdf.ui.util.ShapePropertiesManager;
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
import org.eclipse.graphiti.features.context.IMoveShapeContext;
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
import org.eclipse.graphiti.pattern.AbstractPattern;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;

/**
 * This class configure as most features as possible, relative to Instances that
 * can be added to a Network.
 * 
 * @author Antoine Lorence
 * 
 */
public class InstancePattern extends AbstractPattern {

	// Minimal and default width for an instance shape
	private static final int TOTAL_MIN_WIDTH = 120;
	// Minimal and default height for an instance shape
	private static final int TOTAL_MIN_HEIGHT = 140;
	// Height of instance label (displaying instance name)
	private static final int LABEL_HEIGHT = 40;
	// Width of the line shape used as separator
	private static final int SEPARATOR = 1;
	// Minimal space between an input and output port on the same line
	private static final int PORTS_AREAS_SPACE = 10;
	// Width of the square representing a port
	private static final int PORT_SIDE_WITH = 12;
	// Space set around a port square
	private static final int PORT_MARGIN = 2;

	// Identifiers for important shape of an instance
	public static final String INSTANCE_ID = "INSTANCE";
	private static final String LABEL_ID = "INSTANCE_LABEL";
	private static final String SEP_ID = "INSTANCE_SEPARATOR";
	public static final String PORT_ID = "INSTANCE_PORT";
	private static final String PORT_TEXT_ID = "INSTANCE_PORT_TEXT";
	private static final String PORT_NAME_KEY = "REF_PORT_NAME";

	private static final String REFINEMENT_KEY = "refinment";

	private enum Direction {
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
	public boolean isMainBusinessObjectApplicable(Object object) {
		if (object instanceof Port) {
			final Port port = (Port) object;
			final EObject ctr = port.eContainer();
			if (ctr instanceof Actor) {
				return true;
			} else if (ctr instanceof Network) {
				// In this case, we must ensure the network containing this
				// port is NOT the network linked to the diagram.
				return ctr != getBusinessObjectForPictogramElement(getDiagram());
			}
		}
		return object instanceof Instance;
	}

	@Override
	protected boolean isPatternControlled(PictogramElement pe) {
		if (isPatternRoot(pe)) {
			return true;
		} else if (pe instanceof FixPointAnchor
				&& (ShapePropertiesManager.isInput(pe) || ShapePropertiesManager.isOutput(pe))) {

		}
		return false;
	}

	@Override
	protected boolean isPatternRoot(PictogramElement pe) {
		return ShapePropertiesManager.isExpectedPc(pe, INSTANCE_ID);
	}

	@Override
	public boolean canDirectEdit(IDirectEditingContext context) {
		boolean isText = context.getGraphicsAlgorithm() instanceof Text;
		boolean isLabel = ShapePropertiesManager.isExpectedPc(context.getGraphicsAlgorithm(), LABEL_ID);
		return isText && isLabel;
	}

	@Override
	public int getEditingType() {
		return IDirectEditing.TYPE_TEXT;
	}

	@Override
	public String getInitialValue(IDirectEditingContext context) {
		final Instance obj = (Instance) getBusinessObjectForPictogramElement(context.getPictogramElement());
		return obj.getName();
	}

	@Override
	public void setValue(String value, IDirectEditingContext context) {
		final PictogramElement pe = context.getPictogramElement();
		final Instance obj = (Instance) getBusinessObjectForPictogramElement(pe);
		obj.setName(value);

		updatePictogramElement(pe);
	}

	@Override
	public String checkValueValid(String value, IDirectEditingContext context) {
		if (value.length() < 1) {
			return "Please enter a text to name the Instance.";
		}
		if (!value.matches("[a-zA-Z0-9_]+")) {
			return "You can only use alphanumeric characters for Instance name";
		}

		// null -> value is valid
		return null;
	}

	/**
	 * An instance can't be removed from a graph. Instead it must be deleted.
	 * 
	 * @see org.eclipse.graphiti.pattern.AbstractPattern#canRemove(org.eclipse.graphiti
	 *      .features.context.IRemoveContext)
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
			if (getBusinessObjectForPictogramElement(context.getTargetContainer()) instanceof Network) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Object[] create(ICreateContext context) {
		final Instance newInstance = DfFactory.eINSTANCE.createInstance();
		newInstance.setName("");

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

		// Add the new Instance to the current Network
		final Network network = (Network) getBusinessObjectForPictogramElement(getDiagram());
		network.add(addedDomainObject);

		// Create the container shape
		final ContainerShape topLevelShape = peCreateService.createContainerShape(targetDiagram, true);
		ShapePropertiesManager.setIdentifier(topLevelShape, INSTANCE_ID);

		// Create the container graphic
		final RoundedRectangle roundedRectangle = gaService.createPlainRoundedRectangle(topLevelShape, 5, 5);
		roundedRectangle.setStyle(StyleUtil.getStyleForInstance(getDiagram()));
		gaService.setLocationAndSize(roundedRectangle, context.getX(), context.getY(), TOTAL_MIN_WIDTH,
				TOTAL_MIN_HEIGHT);

		// The text label for Instance name
		final Text text = gaService.createPlainText(roundedRectangle);
		ShapePropertiesManager.setIdentifier(text, LABEL_ID);
		// Set properties on instance label
		text.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		text.setVerticalAlignment(Orientation.ALIGNMENT_MIDDLE);
		text.setStyle(StyleUtil.getStyleForInstanceText(getDiagram()));
		gaService.setLocationAndSize(text, 0, 0, TOTAL_MIN_WIDTH, LABEL_HEIGHT);

		if (addedDomainObject.getName() != null && !addedDomainObject.getName().isEmpty()) {
			text.setValue(addedDomainObject.getName());
		}

		// The line separator
		final int[] xy = { 0, LABEL_HEIGHT, TOTAL_MIN_WIDTH, LABEL_HEIGHT };
		final Polyline line = gaService.createPlainPolyline(roundedRectangle, xy);
		ShapePropertiesManager.setIdentifier(line, SEP_ID);
		line.setLineWidth(SEPARATOR);

		// Configure direct editing
		// 1- Get the IDirectEditingInfo object
		final IDirectEditingInfo directEditingInfo = getFeatureProvider().getDirectEditingInfo();
		// 2- These 2 members will be used to retrieve the pattern to call for
		// direct editing
		directEditingInfo.setPictogramElement(topLevelShape);
		directEditingInfo.setGraphicsAlgorithm(text);
		// 3- This PictogramElement is used to locate input on the diagram
		directEditingInfo.setMainPictogramElement(topLevelShape);

		// We link graphical representation and domain model object
		link(topLevelShape, addedDomainObject);

		if (addedDomainObject.getEntity() != null) {
			setInstanceRefinment(topLevelShape, addedDomainObject.getEntity());
		}

		return topLevelShape;
	}

	@Override
	public boolean canMoveShape(IMoveShapeContext context) {
		return isPatternRoot(context.getPictogramElement());
	}

	@Override
	public boolean canResizeShape(IResizeShapeContext context) {
		// Resize is always Ok for Instance. New size is set to minimal value
		// when needed
		return isPatternRoot(context.getPictogramElement());
	}

	@Override
	public void resizeShape(IResizeShapeContext context) {

		final PictogramElement pe = context.getPictogramElement();

		final int oldWidth = pe.getGraphicsAlgorithm().getWidth();
		final int newWidth = Math.max(context.getWidth(), getInstanceMinWidth(pe));
		pe.getGraphicsAlgorithm().setWidth(newWidth);

		final int oldHeight = pe.getGraphicsAlgorithm().getHeight();
		final int newHeight = Math.max(context.getHeight(), getInstanceMinHeight(pe));
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
		return isPatternRoot(context.getPictogramElement());
	}

	/**
	 * This function set position for all elements in an Instance Shape.
	 */
	@Override
	public boolean layout(ILayoutContext context) {
		final AnchorContainer instanceAc = (AnchorContainer) context.getPictogramElement();
		final IGaService gaService = Graphiti.getGaService();

		if (!isPatternRoot(instanceAc)) {
			return false;
		}

		// Calculate the current size of the instance rectangle
		final int instanceW = gaService.calculateSize(instanceAc.getGraphicsAlgorithm(), true).getWidth();

		// Update label size and position
		final Text label = (Text) ShapePropertiesManager.findPcFromIdentifier(instanceAc, LABEL_ID);
		gaService.setLocationAndSize(label, 0, 0, instanceW, LABEL_HEIGHT);

		// Update separator points
		final Polyline sep = (Polyline) ShapePropertiesManager.findPcFromIdentifier(instanceAc, SEP_ID);
		for (final Point p : sep.getPoints()) {
			p.setY(LABEL_HEIGHT);
		}
		sep.getPoints().get(1).setX(instanceW);

		// ***********************
		// Update ports
		// ***********************
		int inIndex = 0, outIndex = 0;
		for (final Anchor anchor : instanceAc.getAnchors()) {
			if (ShapePropertiesManager.isInput(anchor)) {
				layoutPort((FixPointAnchor) anchor, inIndex++, instanceAc);
			} else if (ShapePropertiesManager.isOutput(anchor)) {
				layoutPort((FixPointAnchor) anchor, outIndex++, instanceAc);
			}
		}
		return true;
	}

	@Override
	public boolean canUpdate(IUpdateContext context) {
		return ShapePropertiesManager.isExpectedPc(context.getPictogramElement(), INSTANCE_ID);
	}

	@Override
	public IReason updateNeeded(IUpdateContext context) {
		final PictogramElement pe = context.getPictogramElement();

		if (ShapePropertiesManager.isExpectedPc(pe, INSTANCE_ID)) {
			final Text text = (Text) ShapePropertiesManager.findPcFromIdentifier(pe, LABEL_ID);
			if (text == null) {
				return Reason.createFalseReason("Label Not found !!");
			}

			final Instance instance = (Instance) getBusinessObjectForPictogramElement(pe);
			if (!text.getValue().equals(instance.getName())) {
				return Reason.createTrueReason("The instance name has been updated from outside diagram");
			}
		}

		return super.updateNeeded(context);
	}

	@Override
	public boolean update(IUpdateContext context) {
		final PictogramElement pe = context.getPictogramElement();

		if (ShapePropertiesManager.isExpectedPc(pe, INSTANCE_ID)) {
			final Text text = (Text) ShapePropertiesManager.findPcFromIdentifier(pe, LABEL_ID);
			if (text == null) {
				return false;
			}

			final Instance instance = (Instance) getBusinessObjectForPictogramElement(pe);
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
	 * @param instanceShape
	 * @param entity
	 */
	public void setInstanceRefinment(final ContainerShape instanceShape, final EObject entity) {
		if (!isPatternRoot(instanceShape)) {
			return;
		}
		if (!(entity instanceof Actor || entity instanceof Network)) {
			return;
		}

		// Set the current instance's entity
		final Instance instance = (Instance) getBusinessObjectForPictogramElement(instanceShape);
		instance.setEntity(entity);
		Graphiti.getPeService().setPropertyValue(instanceShape, REFINEMENT_KEY,
				entity.eResource().getURI().toPlatformString(true));

		// Clean all ports anchors and graphics in the instance
		final List<GraphicsAlgorithm> gaChildre = new ArrayList<GraphicsAlgorithm>(instanceShape.getGraphicsAlgorithm()
				.getGraphicsAlgorithmChildren());
		for (final GraphicsAlgorithm gaChild : gaChildre) {
			if (gaChild instanceof Text
					&& (ShapePropertiesManager.isInput(gaChild) || ShapePropertiesManager.isOutput(gaChild))) {
				EcoreUtil.delete(gaChild, true);
			}
		}
		final List<Anchor> anchors = new ArrayList<Anchor>(((AnchorContainer) instanceShape).getAnchors());
		for (final Anchor anchor : anchors) {
			// EcoreUtil.delete(anchor.getReferencedGraphicsAlgorithm(), true);
			EcoreUtil.delete(anchor, true);
		}
		// final List<Shape> children = new
		// ArrayList<Shape>(instanceShape.getChildren());
		// for (Shape child : children) {
		// if (ShapePropertiesManager.isInput(child) ||
		// ShapePropertiesManager.isOutput(child)) {
		// EcoreUtil.delete(child, true);
		// }
		// }

		// Add ports
		if (instance.isActor()) {
			addPorts(instanceShape, instance.getActor().getInputs(), Direction.INPUTS);
			addPorts(instanceShape, instance.getActor().getOutputs(), Direction.OUTPUTS);
		} else {
			addPorts(instanceShape, instance.getNetwork().getInputs(), Direction.INPUTS);
			addPorts(instanceShape, instance.getNetwork().getOutputs(), Direction.OUTPUTS);
		}

		// Resize to minimal size.
		resizeShapeToMinimal(instanceShape);
	}

	/**
	 * Add the given list of ports in the instance, according to the given
	 * direction.
	 * 
	 * This method only create objects and append them to the right parent. The
	 * layouting (setup of sizes and location) of each element is done in the
	 * layout method. layout() is called explicitly or implicitly from some
	 * methods.
	 * 
	 * setInstanceRefinment() execute this method and apply a resize just after.
	 * The layout() method is called from resizeShape() method.
	 * 
	 * @param instanceShape
	 *            The instance shape
	 * @param ports
	 *            The list of ports
	 * @param direction
	 *            The type of ports (inputs or outputs)
	 */
	private void addPorts(final ContainerShape instanceShape, final List<Port> ports, final Direction direction) {

		final IPeCreateService peCreateService = Graphiti.getPeCreateService();
		final IGaService gaService = Graphiti.getGaService();

		final GraphicsAlgorithm instanceGa = instanceShape.getGraphicsAlgorithm();

		int i = 0, j = 0;
		for (final Port port : ports) {

			// Create anchor
			final FixPointAnchor fpAnchor = peCreateService.createFixPointAnchor(instanceShape);
			fpAnchor.setUseAnchorLocationAsConnectionEndpoint(true);
			ShapePropertiesManager.setIdentifier(fpAnchor, PORT_ID);
			Graphiti.getPeService().setPropertyValue(fpAnchor, PORT_NAME_KEY, port.getName());

			// Create the square inside anchor
			final Rectangle square = gaService.createRectangle(fpAnchor);
			square.setStyle(StyleUtil.getStyleForInstancePort(getDiagram()));

			// Create text as instance rectangle child
			final Text txt = gaService.createText(instanceGa, port.getName());
			txt.setStyle(StyleUtil.getStyleForInstanceText(getDiagram()));
			txt.setVerticalAlignment(Orientation.ALIGNMENT_MIDDLE);
			ShapePropertiesManager.setIdentifier(txt, PORT_TEXT_ID);

			// Setup the linking with business object
			link(fpAnchor, port);

			// Configure direction of the port and alignment of texts
			if (direction == Direction.INPUTS) {
				ShapePropertiesManager.setInput(fpAnchor);
				ShapePropertiesManager.setInput(txt);
				txt.setHorizontalAlignment(Orientation.ALIGNMENT_LEFT);
				layoutPort(fpAnchor, i++, instanceShape);
			} else {
				ShapePropertiesManager.setOutput(fpAnchor);
				ShapePropertiesManager.setOutput(txt);
				txt.setHorizontalAlignment(Orientation.ALIGNMENT_RIGHT);
				layoutPort(fpAnchor, j++, instanceShape);
			}
		}
	}

	private void layoutPort(final FixPointAnchor anchor, final int index, final PictogramElement instancePe) {
		final IGaService gaService = Graphiti.getGaService();
		// The port square, visual representation of the anchor
		final GraphicsAlgorithm square = anchor.getGraphicsAlgorithm();
		// referenced port text
		final GraphicsAlgorithm txt = getTextFromAnchor(anchor);

		// Calculate the current size of the instance rectangle
		final int instanceW = instancePe.getGraphicsAlgorithm().getWidth();
		final int yScaleFromTop = LABEL_HEIGHT + SEPARATOR;
		final int squareAndMargin = PORT_SIDE_WITH + PORT_MARGIN;

		final int txtW = instanceW - squareAndMargin * 2;
		final int txtH = 12;
		final int txtX = squareAndMargin;
		final int txtY = yScaleFromTop + PORT_MARGIN + index * (squareAndMargin);

		final int anchorY = txtY + PORT_SIDE_WITH / 2;
		final int squareY = -PORT_SIDE_WITH / 2;

		int anchorX, squareX;
		if (ShapePropertiesManager.isInput(anchor)) {
			anchorX = 0;
			squareX = 0;
		} else if (ShapePropertiesManager.isOutput(anchor)) {
			anchorX = instanceW;
			squareX = -PORT_SIDE_WITH;
		} else {
			OrccLogger.warnln("Anchor without \"direction\" property found.");
			return;
		}

		// Text position is relative to its parent, the instance
		// roundedRectangle (classical positioning)
		gaService.setLocationAndSize(txt, txtX, txtY, txtW, txtH);
		// FixPointAnchor references the txt object. Its location is
		// relative to the text location
		anchor.setLocation(gaService.createPoint(anchorX, anchorY));
		// The square is the GA of the Anchor. Its position is calculated
		// from the anchor's position
		gaService.setLocationAndSize(square, squareX, squareY, PORT_SIDE_WITH, PORT_SIDE_WITH);
	}

	/**
	 * Resize the current instance shape to its minimal width and height. The
	 * layout() method will be called after, directly from the resize feature.
	 * 
	 * @param pe
	 *            The instance pictogram element
	 */
	private void resizeShapeToMinimal(final PictogramElement pe) {
		if (!ShapePropertiesManager.isExpectedPc(pe, INSTANCE_ID)) {
			return;
		}

		final ResizeShapeContext ctxt = new ResizeShapeContext((Shape) pe);
		ctxt.setWidth(getInstanceMinWidth(pe));
		ctxt.setHeight(getInstanceMinHeight(pe));

		resizeShape(ctxt);
	}

	/**
	 * Calculate the minimal height needed to display the longest port list
	 * (between inputs and outputs ones)
	 * 
	 * @param pe
	 *            The instance pictogram element
	 * @return The height as integer
	 */
	private int getInstanceMinHeight(final PictogramElement pe) {
		if (!ShapePropertiesManager.isExpectedPc(pe, INSTANCE_ID)) {
			return -1;
		}

		final ContainerShape instanceShape = (ContainerShape) pe;
		int inPorts = 0, outPorts = 0;
		// Compute the number of inputs and outputs ports
		for (final Anchor anchor : instanceShape.getAnchors()) {
			if (ShapePropertiesManager.isInput(anchor)) {
				++inPorts;
			} else if (ShapePropertiesManager.isOutput(anchor)) {
				++outPorts;
			}
		}

		// Keep only the max
		final int maxPorts = Math.max(inPorts, outPorts);
		if (maxPorts == 0) {
			return TOTAL_MIN_HEIGHT;
		}

		// Calculate the total minimal height needed to display the longest
		// ports list
		for (final GraphicsAlgorithm child : instanceShape.getGraphicsAlgorithm().getGraphicsAlgorithmChildren()) {
			if (child instanceof Text
					&& (ShapePropertiesManager.isInput(child) || ShapePropertiesManager.isOutput(child))) {
				final Text exampleTxt = (Text) child;
				final int newHeight = LABEL_HEIGHT + SEPARATOR + PORT_MARGIN + maxPorts
						* (XdfUtil.getTextMinHeight(exampleTxt) + PORT_MARGIN);

				// Return this max height only if it is superior the the generic
				// minimal height for an instance
				return Math.max(newHeight, TOTAL_MIN_HEIGHT);
			}
		}
		// Should never happen
		return TOTAL_MIN_HEIGHT;
	}

	/**
	 * Calculate the minimal width needed to display all contents (ports) of an
	 * instance shape.
	 * 
	 * @param pe
	 *            The instance pictogram element
	 * @return The width as integer
	 */
	private int getInstanceMinWidth(final PictogramElement pe) {
		if (!ShapePropertiesManager.isExpectedPc(pe, INSTANCE_ID)) {
			return -1;
		}

		final ContainerShape instanceShape = (ContainerShape) pe;
		final int minWidthWithoutTexts = PORTS_AREAS_SPACE + (PORT_SIDE_WITH + PORT_MARGIN) * 2;

		final List<Text> inputs = new ArrayList<Text>();
		final List<Text> outputs = new ArrayList<Text>();

		// Collect the ports text instances in 2 separate maps
		for (final GraphicsAlgorithm child : instanceShape.getGraphicsAlgorithm().getGraphicsAlgorithmChildren()) {
			if (child instanceof Text && ShapePropertiesManager.isInput(child)) {
				inputs.add((Text) child);
			} else if (child instanceof Text && ShapePropertiesManager.isOutput(child)) {
				outputs.add((Text) child);
			}
		}

		int maxTotalWidth = TOTAL_MIN_WIDTH;
		final int nbCommonports = Math.min(inputs.size(), outputs.size());

		// Compute the longest space needed to display on the same line both
		// input and output ports
		for (int i = 0; i < nbCommonports; ++i) {
			final int currentWidth = XdfUtil.getTextMinWidth(inputs.get(i)) + XdfUtil.getTextMinWidth(outputs.get(i))
					+ minWidthWithoutTexts;
			maxTotalWidth = Math.max(maxTotalWidth, currentWidth);
		}

		// Do the same for lasts in/outputs ports
		if (inputs.size() > outputs.size()) {
			for (int i = nbCommonports; i < inputs.size(); ++i) {
				final int currentWidth = XdfUtil.getTextMinWidth(inputs.get(i)) + minWidthWithoutTexts;
				maxTotalWidth = Math.max(maxTotalWidth, currentWidth);
			}
		} else if (inputs.size() < outputs.size()) {
			for (int i = nbCommonports; i < outputs.size(); ++i) {
				final int currentWidth = XdfUtil.getTextMinWidth(outputs.get(i)) + minWidthWithoutTexts;
				maxTotalWidth = Math.max(maxTotalWidth, currentWidth);
			}
		}

		return maxTotalWidth;
	}

	/**
	 * Return the name of the instance without using the business object
	 * 
	 * @param pe
	 * @return
	 */
	public String getNameFromShape(final PictogramElement pe) {
		if (isPatternRoot(pe)) {
			final Text text = (Text) ShapePropertiesManager.findPcFromIdentifier(pe, LABEL_ID);
			return text.getValue();
		}
		return "";
	}

	/**
	 * If the instance corresponding to the given PictogramElement has a
	 * refinement defined (Actor or Network), returns it. If not, returns null.
	 * 
	 * @param pe
	 * @return
	 */
	public EObject getRefinementFromShape(final PictogramElement pe) {
		if (isPatternRoot(pe)) {
			final String plateforStringUri = Graphiti.getPeService().getPropertyValue(pe, REFINEMENT_KEY);
			final Resource res = XdfUtil.getCommonResourceSet().getResource(
					URI.createPlatformResourceURI(plateforStringUri, true), true);
			if (res.getContents().size() > 0) {
				return res.getContents().get(0);
			}
		}
		return null;
	}

	/**
	 * Returns the FixPointAnchor associated with the given port in the instance
	 * represented by the given PictogramElement.
	 * 
	 * @param instancePe
	 * @param port
	 * @return
	 */
	public Anchor getAnchorForPort(final PictogramElement instancePe, final Port port) {
		if (isPatternRoot(instancePe)) {
			final ContainerShape instanceShape = (ContainerShape) instancePe;
			for (final Anchor anchor : instanceShape.getAnchors()) {
				if (getBusinessObjectForPictogramElement(anchor).equals(port)) {
					return anchor;
				}
			}
		}
		return null;
	}

	/**
	 * Returns the Text object used to display the port name. The object is
	 * searched from the given anchor, which must represents an instance port.
	 * 
	 * @param anchor
	 * @return
	 */
	public Text getTextFromAnchor(final Anchor anchor) {
		final String portName = Graphiti.getPeService().getPropertyValue(anchor, PORT_NAME_KEY);
		for (final GraphicsAlgorithm gaChild : anchor.getParent().getGraphicsAlgorithm().getGraphicsAlgorithmChildren()) {
			if (gaChild instanceof Text && ((Text) gaChild).getValue().equals(portName)) {
				return (Text) gaChild;
			}
		}
		return null;
	}

}
