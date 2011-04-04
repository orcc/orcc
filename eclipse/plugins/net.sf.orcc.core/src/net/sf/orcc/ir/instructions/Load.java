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
package net.sf.orcc.ir.instructions;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.ir.Cast;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.LocalTargetContainer;
import net.sf.orcc.ir.VarLocal;
import net.sf.orcc.ir.Location;
import net.sf.orcc.ir.SourceContainer;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.impl.InstructionImpl;
import net.sf.orcc.ir.impl.InstructionInterpreter;
import net.sf.orcc.ir.impl.InstructionVisitor;
import net.sf.orcc.ir.util.CommonNodeOperations;

/**
 * This class defines an instruction that Loads data from memory to a local
 * variable. The source can be a global (scalar or array), or a local array.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Load extends InstructionImpl implements LocalTargetContainer,
		SourceContainer {

	private List<Expression> indexes;

	private Use source;

	private VarLocal target;

	public Load(VarLocal target, Use source) {
		this(target, source, new ArrayList<Expression>(0));
	}

	public Load(VarLocal target, Use source, List<Expression> indexes) {
		this(new Location(), target, source, indexes);
	}

	public Load(Location location, VarLocal target, Use source,
			List<Expression> indexes) {
		super(location);
		setIndexes(indexes);
		setSource(source);
		setTarget(target);
	}

	@Override
	public Object accept(InstructionInterpreter interpreter, Object... args) {
		return interpreter.interpret(this, args);
	}

	@Override
	public void accept(InstructionVisitor visitor) {
		visitor.visit(this);
	}

	@Override
	public Cast getCast() {
		Type tgt = target.getType();
		Type src = source.getVariable().getType();

		if (src == null) {
			return null;
		}

		Cast cast = new Cast(src, tgt);

		if (cast.isExtended() || cast.isTrunced()) {
			return cast;
		}

		return null;
	}

	/**
	 * Returns the (possibly empty) list of indexes of this load.
	 * 
	 * @return the (possibly empty) list of indexes of this load
	 */
	public List<Expression> getIndexes() {
		return indexes;
	}

	/**
	 * Returns the source of this Load. The source is a Use because it may be a
	 * local.
	 * 
	 * @return the source of this Load
	 */
	@Override
	public Use getSource() {
		return source;
	}

	@Override
	public VarLocal getTarget() {
		return target;
	}

	@Override
	public void internalSetSource(Use source) {
		this.source = source;
	}

	@Override
	public void internalSetTarget(VarLocal target) {
		this.target = target;
	}

	@Override
	public boolean isLoad() {
		return true;
	}

	/**
	 * Sets the indexes of this load instruction. Uses are updated to point to
	 * this instruction. This method is internal. Indexes should be modified
	 * solely using the {@link #getIndexes()} method.
	 * 
	 * @param indexes
	 *            a list of expressions
	 */
	private void setIndexes(List<Expression> indexes) {
		if (this.indexes != null) {
			Use.removeUses(this, this.indexes);
		}
		this.indexes = indexes;
		Use.addUses(this, indexes);
	}

	@Override
	public void setSource(Use source) {
		if (this.source != null) {
			this.source.remove();
		}
		this.source = source;
		if (source != null) {
			source.setNode(this);
		}
	}

	@Override
	public void setTarget(VarLocal target) {
		CommonNodeOperations.setTarget(this, target);
	}

	@Override
	public String toString() {
		return target + " = " + source + indexes;
	}

}
