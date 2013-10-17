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
import net.sf.orcc.df.Port;
import net.sf.orcc.xdf.ui.styles.StyleUtil;
import net.sf.orcc.xdf.ui.util.XdfUtil;

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

	private final String SHAPE_ID = "PORT_SHAPE";
	private final String LABEL_ID = "PORT_LABEL";
	private final String[] validIds = { getInOutIdentifier(), SHAPE_ID, LABEL_ID };

	protected static String ATTRIBUTE_INOUT_IDENTIFIER = "port_identifier";

	public NetworkPortPattern() {
		super(null);
	}

	@Override
	abstract public String getCreateName();

	abstract protected Polygon getPortPolygon(Shape shape, IGaService gaService);

	abstract protected String getInOutIdentifier();

	@Override
	protected String[] getValidIdentifiers() {
		return validIds;
	}

	@Override
	public boolean isMainBusinessObjectApplicable(Object object) {
		if (object instanceof Port) {
			Port port = (Port) object;
			String attrValue = port.getValueAsString(ATTRIBUTE_INOUT_IDENTIFIER);
			if (getInOutIdentifier().equals(attrValue)) {
				return true;
			}
		}
		return false;
	}

	private void setInOutType(Port port) {
		port.setAttribute(ATTRIBUTE_INOUT_IDENTIFIER, getInOutIdentifier());
	}

	@Override
	protected boolean isPatternRoot(PictogramElement pe) {
		return isExpectedPe(pe, getInOutIdentifier());
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
		Port obj = (Port) getBusinessObjectForPictogramElement(pe);
		obj.setName(value);

		// layout(pe) and update(pe) can oly be called with the root element.
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
		return context.getTargetContainer() instanceof Diagram;
	}

	@Override
	public Object[] create(ICreateContext context) {
		Port newPort = DfFactory.eINSTANCE.createPort();
		setInOutType(newPort);

		// TODO: We must add the new port to an Xdf resource created
		// earlier, instead of in the .diragram file directly
		getDiagram().eResource().getContents().add(newPort);

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
		final Rectangle rect = gaService.createInvisibleRectangle(containerShape);
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
			gaService.setLocationAndSize(text, 0, PORT_HEIGHT + 4, 50, 10);

			setIdentifier(shape, LABEL_ID);
			link(shape, addedDomainObject);

			// set shape and graphics algorithm where the editor for
			// direct editing shall be opened after object creation
			directEditingInfo.setPictogramElement(shape);
			directEditingInfo.setGraphicsAlgorithm(text);
		}

		directEditingInfo.setMainPictogramElement(containerShape);
		setIdentifier(containerShape, getInOutIdentifier());
		link(containerShape, addedDomainObject);

		gaService.setLocationAndSize(rect, context.getX(), context.getY(), -1, -1);
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
		PictogramElement pe = context.getPictogramElement();

		Text txt = (Text) getPeFromIdentifier(pe, LABEL_ID).getGraphicsAlgorithm();
		Polygon poly = (Polygon) getPeFromIdentifier(pe, SHAPE_ID).getGraphicsAlgorithm();

		int minTxtWidth = XdfUtil.getTextMinWidth(txt);
		if (minTxtWidth > PORT_WIDTH) {
			txt.setWidth(minTxtWidth);
			int xscale = (minTxtWidth - PORT_WIDTH) / 2;
			poly.setX(xscale);
		} else {
			txt.setWidth(PORT_WIDTH);
		}

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

}
