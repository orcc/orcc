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

import org.eclipse.graphiti.mm.Property;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.pattern.AbstractPattern;
import org.eclipse.graphiti.pattern.IPattern;
import org.eclipse.graphiti.pattern.config.IPatternConfiguration;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IPeService;

/**
 * 
 * @author Antoine Lorence
 * 
 */
abstract public class AbstractPatternWithProperties extends AbstractPattern implements IPattern {

	private static String PROPERTY_ID = "XDF_ID";

	private final IPeService peService;

	public AbstractPatternWithProperties(IPatternConfiguration config) {
		super(config);
		peService = Graphiti.getPeService();
	}

	/**
	 * This method must be implemented by subclasses to returns a list of all
	 * identifiers set by it. This is mostly used to easily check if a subclass
	 * can manage a PictogramElement by checking if its identifier is contained
	 * in the returned list.
	 * 
	 * @see #isPatternControlled(PictogramElement)
	 * @return The list of identifiers
	 */
	abstract protected String[] getValidIdentifiers();

	/**
	 * Check if the concrete class can manage the given PictogramElement.
	 * 
	 * @see #getValidIdentifiers()
	 * @return true if the given pe is controlled by this class
	 */
	@Override
	protected boolean isPatternControlled(PictogramElement pe) {
		String peId = getIdentifier(pe);
		for (String validId : getValidIdentifiers()) {
			if (validId.equals(peId)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Set the given id as an identifier to the given pe
	 * 
	 * @param pe
	 *            The PictogramElement
	 * @param id
	 *            The value of the identifier to set on given pe
	 */
	protected void setIdentifier(PictogramElement pe, String id) {
		peService.setPropertyValue(pe, PROPERTY_ID, id);
	}

	/**
	 * Get the identifier set on the given PictogramElement
	 * 
	 * @param pe
	 * @return The identifier, or null if the given pe has no identifier set
	 */
	private String getIdentifier(PictogramElement pe) {
		Property property = peService.getProperty(pe, PROPERTY_ID);
		if (property == null) {
			return null;
		} else {
			return property.getValue();
		}
	}

	/**
	 * Check if the given pe identifier has the given id set as identifier
	 * 
	 * @param pe
	 *            The PictogramElement
	 * @param id
	 *            The identifier to check
	 * @return true if the given pe identifier is equals to the given id value
	 */
	protected boolean isExpectedPe(PictogramElement pe, String id) {
		return id.equals(getIdentifier(pe));
	}

	protected Shape getSubShapeFromId(ContainerShape cs, String id) {
		for (Shape child : cs.getChildren()) {
			if (isExpectedPe(child, id)) {
				return child;
			}
			if (child instanceof ContainerShape) {
				Shape subResult = getSubShapeFromId((ContainerShape) child, id);
				if (subResult != null) {
					return subResult;
				}
			}
		}
		return null;
	}

}
