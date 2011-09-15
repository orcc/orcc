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
package net.sf.orcc.frontend.schedule;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.List;

import net.sf.orcc.cal.cal.AstTag;
import net.sf.orcc.cal.cal.Inequality;
import net.sf.orcc.cal.cal.Priority;
import net.sf.orcc.ir.Action;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Tag;
import net.sf.orcc.util.ActionList;

import org.jgrapht.DirectedGraph;
import org.jgrapht.ext.DOTExporter;
import org.jgrapht.ext.VertexNameProvider;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.traverse.TopologicalOrderIterator;

/**
 * This class defines a function to sort actions based on the priorities
 * defined.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class ActionSorter {

	/**
	 * This class defines a {@link VertexNameProvider} for actions. The
	 * {@link #getVertexName(Action)} simply calls the <code>toString</code>
	 * method of the given action.
	 * 
	 * @author Matthieu Wipliez
	 * 
	 */
	private class ActionNameProvider implements VertexNameProvider<Action> {

		@Override
		public String getVertexName(Action action) {
			return action.toString();
		}

	}

	private ActionList actionList;

	private DirectedGraph<Action, DefaultEdge> graph;

	/**
	 * Creates a new action sorter.
	 * 
	 * @param actionList
	 *            an action list
	 */
	public ActionSorter(ActionList actionList) {
		graph = new DefaultDirectedGraph<Action, DefaultEdge>(DefaultEdge.class);
		this.actionList = actionList;
	}

	/**
	 * Creates a list of actions based on the given priorities.
	 * 
	 * @param priorities
	 */
	public ActionList applyPriority(List<Priority> priorities) {
		buildGraph(priorities);

		ActionList actions = new ActionList();

		// adds untagged actions by document order
		for (Action action : actionList) {
			if (action.getTag().isEmpty()) {
				actions.add(action);
			}
		}

		// topological sort only work for graphs with no cycles
		// cycle detection is done in the validator
		TopologicalOrderIterator<Action, DefaultEdge> it = new TopologicalOrderIterator<Action, DefaultEdge>(
				graph);

		// adds tagged actions by priority order
		while (it.hasNext()) {
			Action action = it.next();
			actions.add(action);
		}

		return actions;
	}

	/**
	 * Builds the priority graph from the given priorities and action list. The
	 * algorithm is pretty straightforward.
	 * 
	 * <pre>
	 * for every inequality of the form t(1) &gt; t(2) &gt; ... &gt; t(n)
	 *   for every pair of tags (t(i), t(i+1)) where 1 &lt;= i &lt; n
	 *     link all actions matching t(i) to all actions matching t(i+1)
	 * </pre>
	 * 
	 * <p>
	 * A tag that references no action is an error.
	 * </p>
	 * <p>
	 * Named actions (ie whose tag is not empty) that are in the action list but
	 * are not referenced by priorities are added to the priority graph but are
	 * not linked to any other action, therefore their order will be unknown.
	 * </p>
	 * 
	 * @param priorities
	 *            a list of inequalities, an inequality being a list of tags
	 *            (and a tag is a list of strings)
	 */
	private void buildGraph(List<Priority> priorities) {
		for (Action action : actionList) {
			if (!action.getTag().isEmpty()) {
				graph.addVertex(action);
			}
		}

		for (Priority priority : priorities) {
			for (Inequality inequality : priority.getInequalities()) {
				// the grammar requires there be at least two tags
				Iterator<AstTag> it = inequality.getTags().iterator();
				Tag previousTag = IrFactory.eINSTANCE.createTag(it.next()
						.getIdentifiers());
				while (it.hasNext()) {
					Tag tag = IrFactory.eINSTANCE.createTag(it.next()
							.getIdentifiers());
					List<Action> sources = actionList
							.getTaggedActions(previousTag);
					List<Action> targets = actionList.getTaggedActions(tag);
					for (Action source : sources) {
						for (Action target : targets) {
							graph.addEdge(source, target);
						}
					}

					previousTag = tag;
				}
			}
		}
	}

	/**
	 * Prints the priority graph to the given output stream
	 * 
	 * @param out
	 *            an output stream
	 */
	public void printGraph(OutputStream out) {
		VertexNameProvider<Action> provider = new ActionNameProvider();
		DOTExporter<Action, DefaultEdge> exporter = new DOTExporter<Action, DefaultEdge>(
				provider, null, null);
		exporter.export(new OutputStreamWriter(out), graph);
	}

}
