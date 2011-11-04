/*
 * Copyright (c) 2009, IETR/INSA of Rennes
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
package net.sf.orcc.df;

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.df.attributes.IAttribute;
import net.sf.orcc.df.attributes.IAttributeContainer;
import net.sf.orcc.df.attributes.IValueAttribute;
import net.sf.orcc.df.attributes.ValueAttribute;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.util.ExpressionEvaluator;

/**
 * This class represents a connection in a network. A connection can have a
 * number of attributes, that can be types or expressions.
 * 
 * @author Matthieu Wipliez
 * @author Herve Yviquel
 * 
 */
public class Connection implements IAttributeContainer, Comparable<Connection> {

	/**
	 * the bufferSize attribute can be attached to a FIFO to specify its size
	 */
	public static final String BUFFER_SIZE = "bufferSize";

	/**
	 * attributes
	 */
	private Map<String, IAttribute> attributes;

	/**
	 * id of associate fifo
	 */
	private int fifoId;

	/**
	 * source port
	 */
	private Port source;

	/**
	 * target port
	 */
	private Port target;

	/**
	 * Creates a connection from source port to target port with the given size.
	 * This will create a connection with the {@link #BUFFER_SIZE} attribute set
	 * to size.
	 * 
	 * @param source
	 *            source port
	 * @param target
	 *            target port
	 * @param size
	 *            the size of this FIFO
	 */
	public Connection(Port source, Port target, int size) {
		this.attributes = new HashMap<String, IAttribute>();
		attributes.put(BUFFER_SIZE,
				new ValueAttribute(IrFactory.eINSTANCE.createExprInt(size)));
		this.source = source;
		this.target = target;
	}

	/**
	 * Creates a connection from source port to target port with the given
	 * attributes.
	 * 
	 * @param source
	 *            source port
	 * @param target
	 *            target port
	 * @param attributes
	 *            a map of attributes
	 */
	public Connection(Port source, Port target,
			Map<String, IAttribute> attributes) {
		this.attributes = new HashMap<String, IAttribute>(attributes);
		this.source = source;
		this.target = target;
	}

	/**
	 * Creates a connection from source port to target port with a single given
	 * attribute.
	 * 
	 * @param source
	 *            source port
	 * @param target
	 *            target port
	 * @param name
	 *            the attribute name
	 * @param attribute
	 *            an attribute
	 */
	public Connection(Port source, Port target, String name,
			IAttribute attribute) {
		this.attributes = new HashMap<String, IAttribute>();
		attributes.put(name, attribute);
		this.source = source;
		this.target = target;
	}

	@Override
	public int compareTo(Connection connection) {
		Integer this_hashcode = new Integer(this.hashCode());
		return this_hashcode.compareTo(new Integer(connection.hashCode()));
	}

	@Override
	public IAttribute getAttribute(String name) {
		return attributes.get(name);
	}

	@Override
	public Map<String, IAttribute> getAttributes() {
		return attributes;
	}

	public int getFifoId() {
		return fifoId;
	}

	/**
	 * Returns the size of this connection.
	 * 
	 * @return the size of this connection
	 */
	public Integer getSize() {
		IAttribute attr = getAttribute(Connection.BUFFER_SIZE);
		if (attr != null && attr.getType() == IAttribute.VALUE) {
			Expression expr = ((IValueAttribute) attr).getValue();
			return new ExpressionEvaluator().evaluateAsInteger(expr);
		} else {
			return null;
		}
	}

	/**
	 * Returns this connection's source port.
	 * 
	 * @return this connection's source port
	 */
	public Port getSource() {
		return source;
	}

	/**
	 * Returns this connection's target port.
	 * 
	 * @return this connection's target port
	 */
	public Port getTarget() {
		return target;
	}

	public void setFifoId(int fifoId) {
		this.fifoId = fifoId;
	}

	/**
	 * Sets the source of this connection to the given port
	 * 
	 * @param source
	 *            a port
	 */
	public void setSource(Port source) {
		this.source = source;
	}

	/**
	 * Sets the target of this connection to the given port
	 * 
	 * @param target
	 *            a port
	 */
	public void setTarget(Port target) {
		this.target = target;
	}
}
