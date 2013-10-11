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

import net.sf.orcc.xdf.ui.styles.StyleUtil;

import org.eclipse.graphiti.mm.algorithms.Polygon;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.IGaService;

/**
 * This class define an Input port for a network
 * 
 * @author Antoine Lorence
 * 
 */
public class InputNetworkPortPattern extends NetworkPortPattern {

	private static String INOUT_ID = "IN_PORT";

	@Override
	public String getCreateName() {
		return "Input port";
	}

	@Override
	protected Polygon getPortPolygon(Shape shape, IGaService gaService) {
		int[] points = { 0, PORT_WIDTH / 2, PORT_HEIGHT, 0, PORT_HEIGHT, PORT_WIDTH };
		Polygon polygon = gaService.createPlainPolygon(shape, points);
		polygon.setStyle(StyleUtil.getStyleForInputPort(getDiagram()));
		return polygon;
	}

	@Override
	protected String getInOutIdentifier() {
		return INOUT_ID;
	}
}
