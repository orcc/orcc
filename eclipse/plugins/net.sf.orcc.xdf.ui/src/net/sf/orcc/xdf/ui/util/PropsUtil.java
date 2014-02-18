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

import org.eclipse.graphiti.mm.PropertyContainer;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
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
public class PropsUtil {

	private static String IDENTIFIER_KEY = "XDF_ID";

	private static String DIRECTION_KEY = "SHAPE_DIRECTION";
	private static String INPUT = "input";
	private static String OUTPUT = "output";

	// Shapes identifiers
	private static final String INSTANCE_ID = "INSTANCE";
	private static final String INPORT_ID = "IN_PORT";
	private static final String OUTPORT_ID = "OUT_PORT";

	/**
	 * Set the given id as identifier to the given pe
	 * 
	 * @param pc
	 *            The PictogramElement
	 * @param id
	 *            The value of the identifier to set on given pe
	 */
	public static void setIdentifier(final PropertyContainer pc, final String id) {
		Graphiti.getPeService().setPropertyValue(pc, IDENTIFIER_KEY, id);
	}

	/**
	 * Get the identifier set on the given PictogramElement
	 * 
	 * @param pe
	 * @return The identifier, or null if the given pe has no identifier set
	 */
	public static String getIdentifier(final PropertyContainer pc) {
		return Graphiti.getPeService().getPropertyValue(pc, IDENTIFIER_KEY);
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
	public static boolean isExpectedPc(final PropertyContainer pc, final String id) {
		return id.equals(getIdentifier(pc));
	}

	/**
	 * Search for a PictogramElement with given id as identifier. This method
	 * check the given pe and its corresponding GraphicsAlgorithm. If the given
	 * pe is a ContainerShape, children shapes are also checked.
	 * 
	 * @param pe
	 *            A PictogramElement
	 * @param id
	 *            Identifier to found
	 * @return A PropertyContainer or null if it can't be found
	 */
	public static PropertyContainer findPcFromIdentifier(final PictogramElement pe, final String id) {

		if (isExpectedPc(pe, id)) {
			return pe;
		}

		final GraphicsAlgorithm ga = pe.getGraphicsAlgorithm();
		if (ga != null) {
			final PropertyContainer pc = findPcFromIdentifier(ga, id);
			if (pc != null) {
				return pc;
			}
		}

		if (pe instanceof ContainerShape) {
			for (final Shape child : ((ContainerShape) pe).getChildren()) {
				final PropertyContainer foundPropertyContainer = findPcFromIdentifier(child, id);
				if (foundPropertyContainer != null) {
					return foundPropertyContainer;
				}
			}
		}

		return null;
	}

	/**
	 * Search in the given GraphicsAlgorithm and its children for an object with
	 * the given identifier.
	 * 
	 * @param ga
	 * @param id
	 * @return
	 */
	public static PropertyContainer findPcFromIdentifier(final GraphicsAlgorithm ga, final String id) {

		if (isExpectedPc(ga, id)) {
			return ga;
		}

		for (final GraphicsAlgorithm child : ga.getGraphicsAlgorithmChildren()) {
			final PropertyContainer foundPropertyContainer = findPcFromIdentifier(child, id);
			if (foundPropertyContainer != null) {
				return foundPropertyContainer;
			}
		}

		return null;
	}

	public static void setInstance(final PropertyContainer pc) {
		setIdentifier(pc, INSTANCE_ID);
	}

	public static boolean isInstance(final PropertyContainer pc) {
		return isExpectedPc(pc, INSTANCE_ID);
	}

	public static void setInputPort(final PropertyContainer pc) {
		setIdentifier(pc, INPORT_ID);
	}

	public static boolean isInputPort(final PropertyContainer pc) {
		return isExpectedPc(pc, INPORT_ID);
	}

	public static void setOutputPort(final PropertyContainer pc) {
		setIdentifier(pc, OUTPORT_ID);
	}

	public static boolean isOutputPort(final PropertyContainer pc) {
		return isExpectedPc(pc, OUTPORT_ID);
	}

	public static boolean isPort(final PropertyContainer pc) {
		return isInputPort(pc) || isOutputPort(pc);
	}

	public static void setInput(final PropertyContainer pc) {
		Graphiti.getPeService().setPropertyValue(pc, DIRECTION_KEY, INPUT);
	}

	public static boolean isInput(final PropertyContainer pc) {
		return INPUT.equals(Graphiti.getPeService().getPropertyValue(pc, DIRECTION_KEY));
	}

	public static void setOutput(final PropertyContainer pc) {
		Graphiti.getPeService().setPropertyValue(pc, DIRECTION_KEY, OUTPUT);
	}

	public static boolean isOutput(final PropertyContainer pc) {
		return OUTPUT.equals(Graphiti.getPeService().getPropertyValue(pc, DIRECTION_KEY));
	}

}
