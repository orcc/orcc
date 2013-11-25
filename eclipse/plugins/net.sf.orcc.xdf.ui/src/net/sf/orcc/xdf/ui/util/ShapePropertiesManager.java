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
package net.sf.orcc.xdf.ui.util;

import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;

/**
 * This class is a utility to manage common properties on shapes.
 * 
 * @author Antoine Lorence
 * 
 */
public class ShapePropertiesManager {

	private static String IDENTIFIER_KEY = "XDF_ID";

	private static String DIRECTION_KEY = "SHAPE_DIRECTION";
	private static String INPUT = "input";
	private static String OUTPUT = "output";

	/**
	 * Set the given id as identifier to the given pe
	 * 
	 * @param pe
	 *            The PictogramElement
	 * @param id
	 *            The value of the identifier to set on given pe
	 */
	public static void setIdentifier(PictogramElement pe, String id) {
		Graphiti.getPeService().setPropertyValue(pe, IDENTIFIER_KEY, id);
	}

	/**
	 * Get the identifier set on the given PictogramElement
	 * 
	 * @param pe
	 * @return The identifier, or null if the given pe has no identifier set
	 */
	public static String getIdentifier(PictogramElement pe) {
		return Graphiti.getPeService().getPropertyValue(pe, IDENTIFIER_KEY);
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
	public static boolean isExpectedPe(PictogramElement pe, String id) {
		return id.equals(getIdentifier(pe));
	}

	/**
	 * Search for a PictogramElement with given id as identifier. This method
	 * check the given pe and its children recursively.
	 * 
	 * @param pe
	 * @param id
	 * @return A PictogramElement or null if it can't be found
	 */
	public static PictogramElement findPeFromIdentifier(PictogramElement pe, final String id) {

		if (isExpectedPe(pe, id)) {
			return pe;
		}

		if (pe instanceof ContainerShape) {
			for (Shape child : ((ContainerShape) pe).getChildren()) {
				final PictogramElement childPe = findPeFromIdentifier(child, id);
				if (childPe != null) {
					return childPe;
				}
			}
		}

		return null;
	}

	public static void setInput(PictogramElement pe) {
		Graphiti.getPeService().setPropertyValue(pe, DIRECTION_KEY, INPUT);
	}
	public static boolean isInput(PictogramElement pe) {
		return INPUT.equals(Graphiti.getPeService().getPropertyValue(pe, DIRECTION_KEY));
	}

	public static void setOutput(PictogramElement pe) {
		Graphiti.getPeService().setPropertyValue(pe, DIRECTION_KEY, OUTPUT);
	}
	public static boolean isOutput(PictogramElement pe) {
		return OUTPUT.equals(Graphiti.getPeService().getPropertyValue(pe, DIRECTION_KEY));
	}

}
