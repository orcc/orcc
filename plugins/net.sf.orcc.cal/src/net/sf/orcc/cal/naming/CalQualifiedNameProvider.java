/*
 * Copyright (c) 2010, IETR/INSA of Rennes
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
package net.sf.orcc.cal.naming;

import java.util.Iterator;

import net.sf.orcc.cal.cal.AstAction;
import net.sf.orcc.cal.cal.AstGenerator;
import net.sf.orcc.cal.cal.AstStatementForeach;
import net.sf.orcc.cal.cal.AstTag;

import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;

/**
 * This class defines a qualified name provider for RVC-CAL.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class CalQualifiedNameProvider extends
		DefaultDeclarativeQualifiedNameProvider {

	private int blockCount;

	private int untaggedCount;

	/**
	 * Resets the block count and returns the qualified name of the tag of the
	 * given action.
	 * 
	 * @param action
	 *            an action
	 * @return the action's tag qualified name
	 */
	public String qualifiedName(AstAction action) {
		AstTag tag = action.getTag();
		if (tag == null) {
			return "untagged_" + untaggedCount++;
		} else {
			return getQualifiedName(tag);
		}
	}

	public String qualifiedName(AstGenerator generator) {
		return "generator" + blockCount++;
	}

	public String qualifiedName(AstStatementForeach foreach) {
		return "foreach" + blockCount++;
	}

	public String qualifiedName(AstTag tag) {
		Iterator<String> it = tag.getIdentifiers().iterator();
		StringBuilder builder = new StringBuilder();
		if (it.hasNext()) {
			builder.append(it.next());
			while (it.hasNext()) {
				builder.append('.');
				builder.append(it.next());
			}
		}

		return builder.toString();
	}

	/**
	 * Resets the block count.
	 */
	public void resetBlockCount() {
		blockCount = 0;
	}

	/**
	 * Resets the untagged action count.
	 */
	public void resetUntaggedCount() {
		untaggedCount = 0;
	}

}
