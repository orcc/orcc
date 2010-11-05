/*
 * Copyright (c) 2009-2010, IETR/INSA of Rennes
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
package net.sf.orcc.backends.transformations.threeAddressCodeTransformation;

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.Actor;
import net.sf.orcc.ir.ActorTransformation;
import net.sf.orcc.ir.transformations.AbstractActorTransformation;
import net.sf.orcc.ir.transformations.BuildCFG;

/**
 * Main transformation for applying three-address code form to actor IR.
 * <p>
 * This transformation is composed of 4 atomic transformations :
 * <ul>
 * 
 * <li>{@link #CopyPropagationTransformation()} that propagates copy of
 * variables.</li>
 * <li>{@link #ExpressionSplitterTransformation()} that splits complex
 * expressions into fundamental operations.</li>
 * <li>{@link #BuildCFG()} that builds a CFG of the actor IR.</li>
 * <li>
 * {@link #CastAdderTransformation()} that add cast in the IR when necessary.</li>
 * </ul>
 * </p>
 * 
 * @author Jérôme GORIN
 * 
 */
public class ThreeAddressCodeTransformation extends AbstractActorTransformation {
	@Override
	public void transform(Actor actor) throws OrccException {
		ActorTransformation[] transformations = {
				new CopyPropagationTransformation(),
				new ExpressionSplitterTransformation(), new BuildCFG(),
				new CastAdderTransformation() };

		for (ActorTransformation transformation : transformations) {
			transformation.transform(actor);
		}
	}

}
