/*
 * Copyright (c) 2008, IETR/INSA of Rennes
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
package net.sf.orcc.graphiti.ui.figure;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.orcc.graphiti.model.ParameterPosition;

import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.ConnectionLocator;
import org.eclipse.draw2d.geometry.Point;

/**
 * This class provides a connection locator.
 * 
 * @author Jonathan Piat
 * 
 */
public class PropertyLocator extends ConnectionLocator {

	private ParameterPosition pos;

	private HashMap<Connection, List<PropertyLocator>> positions = new HashMap<Connection, List<PropertyLocator>>();

	public PropertyLocator(Connection c, ParameterPosition p) {
		super(c);
		if (positions.get(c) == null) {
			List<PropertyLocator> list = new ArrayList<PropertyLocator>();
			positions.put(c, list);
		}
		pos = p;
		positions.get(c).add(this);
	}

	protected Point getReferencePoint() {
		Connection conn = getConnection();
		List<PropertyLocator> listOfProperties = positions.get(conn);
		int maxIndex = listOfProperties.indexOf(this);
		int dec = 5;
		for (int i = 0; i < maxIndex; i++) {
			if (listOfProperties.get(i).pos == this.pos) {
				dec += 10;
			}
		}

		int xdirec = 0;
		int ydirec = 0;
		Point p = Point.SINGLETON;
		Point f = conn.getPoints().getFirstPoint();
		Point l = conn.getPoints().getLastPoint();

		if (l.x > f.x) {
			xdirec = 1;
		} else {
			xdirec = -1;
		}

		if (l.y > f.y) {
			ydirec = 1;
		} else {
			ydirec = -1;
		}
		if (pos.equals(ParameterPosition.West)
				|| pos.equals(ParameterPosition.NorthWest)
				|| pos.equals(ParameterPosition.SouthWest)) {
			Point refP = conn.getPoints().getFirstPoint().getCopy();
			conn.getParent().translateToAbsolute(refP);
			p.setLocation(refP.x + (dec * xdirec), refP.y + (dec * ydirec));
		} else if (pos.equals(ParameterPosition.East)
				|| pos.equals(ParameterPosition.NorthEast)
				|| pos.equals(ParameterPosition.SouthEast)) {
			Point refP = conn.getPoints().getLastPoint().getCopy();
			conn.getParent().translateToAbsolute(refP);
			p.setLocation(refP.x - (dec * xdirec), refP.y - (dec * ydirec));
		} else {
			Point refP = conn.getPoints().getMidpoint().getCopy();
			conn.getParent().translateToAbsolute(refP);
			p.setLocation(refP.x - (dec * xdirec), refP.y - (dec * ydirec));
		}

		return p;
	}

}
