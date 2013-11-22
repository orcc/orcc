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
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Type;
import net.sf.orcc.xdf.ui.styles.StyleUtil;
import net.sf.orcc.xdf.ui.util.XdfUtil;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IDirectEditingInfo;
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
import org.eclipse.graphiti.mm.algorithms.Polygon;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeCreateService;

/**
 * This bastract class
 * 
 * @author alorence
 * 
 */
abstract public class NetworkPortPattern extends AbstractPatternWithProperties {

	protected final int PORT_HEIGHT = 34;
	protected final int PORT_WIDTH = (int) (PORT_HEIGHT * 0.866);
	private final int TEXT_PORT_SPACE = 4;
	private final int TEXT_DEFAULT_WIDTH = 50;
	private final int TEXT_DEFAULT_HEIGHT = 12;

	private final String SHAPE_ID = "PORT_SHAPE";
	private final String LABEL_ID = "PORT_LABEL";
	private final String[] validIds = { getPortIdentifier(), SHAPE_ID, LABEL_ID };

	public NetworkPortPattern() {
		super(null);
	}

	@Override
	abstract public String getCreateName();

	abstract protected Polygon getPortPolygon(Shape shape, IGaService gaService);

	abstract protected String getPortIdentifier();

	abstract protected void addPortToNetwork(Port port, Network network);

	@Override
	protected String[] getValidIdentifiers() {
		return validIds;
	}

	@Override
	protected boolean isPatternRoot(PictogramElement pe) {
		return isExpectedPe(pe, getPortIdentifier());
	}

	@Override
	public boolean canDirectEdit(IDirectEditingContext context) {
		return isPatternControlled(context.getPictogramElement());
	}

	@Override
	public int getEditingType() {
		return IDirectEditing.TYPE_TEXT;
	}

	@Override
	public String getInitialValue(IDirectEditingContext context) {
		Port obj = (Port) getBusinessObjectForPictogramElement(context.getPictogramElement());
		return obj.getName();
	}

	@Override
	public void setValue(String value, IDirectEditingContext context) {
		PictogramElement pe = context.getPictogramElement();
		Port port = (Port) getBusinessObjectForPictogramElement(pe);
		port.setName(value);

		// layout(pe) and update(pe) can only be called with the root element.
		// This method can be used on the shape or on the text label. Before
		// requesting an update, we need to get the root element
		if (!isPatternRoot(pe)) {
			pe = (PictogramElement) pe.eContainer();
		}

		updatePictogramElement(pe);
	}

	@Override
	public boolean stretchFieldToFitText() {
		return true;
	}

	/*
	 * A port can't be removed from a graph. Instead it must be deleted.
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
		// Create the Port instance
		Port newPort = DfFactory.eINSTANCE.createPort();

		// Set default type to i32 for the port
		newPort.setType(IrFactory.eINSTANCE.createTypeInt());

		// Add the newly created port to the network
		addPortToNetwork(newPort, (Network) getBusinessObjectForPictogramElement(getDiagram()));

		addGraphicalRepresentation(context, newPort);

		// activate direct editing after object creation
		getFeatureProvider().getDirectEditingInfo().setActive(true);

		return new Object[] { newPort };
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

		final Port addedDomainObject = (Port) context.getNewObject();

		// provide information to support direct-editing directly
		// after object creation (must be activated additionally)
		IDirectEditingInfo directEditingInfo = getFeatureProvider().getDirectEditingInfo();

		// Create the container
		final ContainerShape containerShape = peCreateService.createContainerShape(targetDiagram, true);
		final Rectangle topLevelInvisibleRect = gaService.createInvisibleRectangle(containerShape);
		{
			Shape shape = peCreateService.createShape(containerShape, false);
			setIdentifier(shape, SHAPE_ID);
			
			/* Polygon polygon = */getPortPolygon(shape, gaService);

			link(shape, addedDomainObject);
		}

		{
			Shape shape = peCreateService.createShape(containerShape, false);
			final Text text = gaService.createPlainText(shape);
			text.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
			text.setVerticalAlignment(Orientation.ALIGNMENT_MIDDLE);
			text.setStyle(StyleUtil.getStyleForPortText(getDiagram()));
			// We define an arbitrary width to text, allowing user to see chars
			// when first direct editing port name
			gaService.setLocationAndSize(text, 0, PORT_HEIGHT + TEXT_PORT_SPACE, TEXT_DEFAULT_WIDTH,
					TEXT_DEFAULT_HEIGHT);

			if (addedDomainObject.getName() != null) {
				text.setValue(addedDomainObject.getName());
			}

			setIdentifier(shape, LABEL_ID);
			link(shape, addedDomainObject);

			// set shape and graphics algorithm where the editor for
			// direct editing shall be opened after object creation
			directEditingInfo.setPictogramElement(shape);
			directEditingInfo.setGraphicsAlgorithm(text);
		}

		directEditingInfo.setMainPictogramElement(containerShape);
		setIdentifier(containerShape, getPortIdentifier());
		link(containerShape, addedDomainObject);
		containerShape.getLink().getBusinessObjects().add(addedDomainObject.getType());

		peCreateService.createChopboxAnchor(containerShape);

		gaService.setLocationAndSize(topLevelInvisibleRect, context.getX(), context.getY(),
				Math.max(TEXT_DEFAULT_WIDTH, PORT_WIDTH), PORT_HEIGHT
				+ TEXT_PORT_SPACE + TEXT_DEFAULT_HEIGHT);
		layoutPictogramElement(containerShape);

		return containerShape;
	}

	/**
	 * @see NetworkPortPattern#layout(ILayoutContext)
	 */
	@Override
	public boolean canResizeShape(IResizeShapeContext context) {
		return false;
	}

	@Override
	public boolean canLayout(ILayoutContext context) {
		return isPatternRoot(context.getPictogramElement());
	}

	/**
	 * Port can't be resized. In that particular case, layout() does everything
	 * needed for a nice display. It adapts the size of text to its value and
	 * ensures everything is always centered
	 * 
	 * @param context
	 * @return
	 */
	@Override
	public boolean layout(ILayoutContext context) {
		final PictogramElement topLevelPe = context.getPictogramElement();

		final Text txt = (Text) getPeFromIdentifier(topLevelPe, LABEL_ID).getGraphicsAlgorithm();
		final Polygon poly = (Polygon) getPeFromIdentifier(topLevelPe, SHAPE_ID).getGraphicsAlgorithm();

		final int minTxtWidth = XdfUtil.getTextMinWidth(txt);
		final int newTextWidth = Math.max(minTxtWidth, PORT_WIDTH);

		if (minTxtWidth > PORT_WIDTH) {
			int xscale = (minTxtWidth - PORT_WIDTH) / 2;
			poly.setX(xscale);
		}

		// Set the new width for the port text
		txt.setWidth(newTextWidth);
		topLevelPe.getGraphicsAlgorithm().setWidth(newTextWidth);

		return true;
	}

	@Override
	public boolean update(IUpdateContext context) {
		PictogramElement pe = context.getPictogramElement();

		if (!isPatternRoot(pe)) {
			return false;
		}

		Text txt = (Text) getPeFromIdentifier(pe, LABEL_ID).getGraphicsAlgorithm();
		Port port = (Port) getBusinessObjectForPictogramElement(pe);
		txt.setValue(port.getName());

		return true;
	}

	/**
	 * Returns the PictogramElement to use as main element for displaying a
	 * selection arount a port.
	 * 
	 * @param pe
	 * @return
	 */
	public GraphicsAlgorithm getSelectionBorder(PictogramElement pe) {
		if (isPatternRoot(pe)) {
			return getPeFromIdentifier(pe, SHAPE_ID).getGraphicsAlgorithm();
		}
		return null;
	}

	public Type getTypeFromShape(PictogramElement pe) {
		if (isPatternRoot(pe)) {
			pe.getLink().getBusinessObjects();
			for (EObject businessObject : pe.getLink().getBusinessObjects()) {
				if (businessObject instanceof Type) {
					return (Type) businessObject;
				}
			}
		}
		return null;
	}

	public String getNameFromShape(PictogramElement pe) {
		if (isPatternRoot(pe)) {
			Text label = (Text) getPeFromIdentifier(pe, LABEL_ID).getGraphicsAlgorithm();
			return label.getValue();
		}
		return "";
	}

	public Anchor getAnchor(AnchorContainer container) {
		return Graphiti.getPeService().getChopboxAnchor(container);
	}
}
