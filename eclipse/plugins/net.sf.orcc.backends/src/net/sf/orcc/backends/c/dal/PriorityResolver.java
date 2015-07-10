package net.sf.orcc.backends.c.dal;

import java.util.List;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Port;
import net.sf.orcc.graph.Edge;
import net.sf.orcc.graph.Graph;
import net.sf.orcc.graph.GraphFactory;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.util.OrccLogger;

/**
 * Class tries to accurately resolve action priorities
 *
 * @author Jani Boutellier
 */
public class PriorityResolver {

	Graph localGraph;
	Graph actorGraph;
	Actor theActor;
	boolean debug = false;

	public PriorityResolver(Actor actor) {
		this.localGraph = GraphFactory.eINSTANCE.createGraph();
		this.actorGraph = GraphFactory.eINSTANCE.createGraph();
		this.theActor = actor;
	}

	private String makeActionSetString(String prefix, List<Action> actions) {
		String str = new String(prefix);
		for (Action a : actions) {
			str = str.concat(a.getName() + " ");
		}
		return str;
	}

	public boolean resolve(List<Action> actions, String tokens) {
		if (debug) {
			OrccLogger.traceln(makeActionSetString("--- Action Set:", actions));
		}
		if (constructActorGraph(actorGraph, theActor.getActions(), theActor.getActionsOutsideFsm()) == false) {
			OrccLogger.warnln("Failed to construct actor graph");
			return false;
		}
		if (constructLocalGraph(localGraph, actorGraph, actions) == false) {
			OrccLogger.warnln("Failed to construct local graph");
			return false;
		}
		if (localGraph.getVertices() == null) {
			OrccLogger.traceln("Local graph contains no vertices");
			return false;
		}
		Vertex sink = findSink(localGraph);
		if (sink == null) {
			OrccLogger.warnln("No sink action found in local graph");
			return false;
		}
		Vertex source = findSource(localGraph);
		if (source == null) {
			OrccLogger.warnln("No source action found local graph");
			return false;
		}
		if (checkLinearity(localGraph) == false) {
			return false;
		}
		if (checkInputDependency(localGraph, actions, source, sink, tokens) == false) {
			return false;
		}
		return true;
	}

	private boolean checkPatternOverLap(Action higherP, Action lowerP) {
		for (Port p : higherP.getInputPattern().getPorts()) {
			if (!lowerP.getInputPattern().contains(p)) {
				OrccLogger.traceln("Action " + lowerP.getName() + " does not have port " + p.getName() + " -- is possibly time-dependent");
				return false;
			} else if (higherP.getInputPattern().getNumTokens(p) > lowerP.getInputPattern().getNumTokens(p)) {
				OrccLogger.traceln("Action " + lowerP.getName() + " consumes less tokens from port " + p.getName() + " -- is possibly time-dependent");
				return false;
			}
		}
		return true;
	}

	private boolean checkInputDependency(Graph g, List<Action> actions, Vertex source, Vertex sink, String tokens) {
		boolean done = false;
		Vertex v = sink;

		while (!done) {
			Action highPri = findActionByName(actions, v.getLabel());
			if (v.getPredecessors().size() < 1) {
				return true;
			}
			if (highPri.getInputPattern().getPorts().size() > 0) {
				Action nextPri = findActionByName(actions, v.getPredecessors().get(0).getLabel());
				if (checkPatternOverLap(highPri, nextPri) == false) {
					return false;
				}
			}

			if (v == source) {
				done = true;
			} else {
				v = v.getPredecessors().get(0);
			}
		}
		return true;
	}

	private Action findActionByName(List<Action> actions, String name) {
		for (Action a : actions) {
			if (a.getName().equals(name)) {
				return a;
			}
		}
		OrccLogger.warnln("Could not find action with name " + name);
		return null;
	}

	private Vertex findVertexByName(List<Vertex> vertexes, String name) {
		for (Vertex v : vertexes) {
			if (v.getLabel().equals(name)) {
				return v;
			}
		}
		OrccLogger.warnln("Could not find vertex with name " + name);
		return null;
	}

	private boolean checkLinearity(Graph g) {
		int predSum = 0;
		int succSum = 0;
		int reference = g.getVertices().size() - 1;
		for (Vertex v : g.getVertices()) {
			predSum += v.getPredecessors().size();
			succSum += v.getSuccessors().size();
		}
		if (predSum != reference) {
			return false;
		}
		if (succSum != reference) {
			return false;
		}
		return true;
	}

	private Vertex findSink(Graph g) {
		for (Vertex v : g.getVertices()) {
			if (v == null) {
				break;
			}
			if (v.getSuccessors() != null) {
				if (v.getSuccessors().size() == 0) {
					return v;
				}
			} else {
				return null;
			}
		}
		return null;
	}

	private Vertex findSource(Graph g) {
		for (Vertex v : g.getVertices()) {
			if (v == null) {
				break;
			}
			if (v.getPredecessors() != null) {
				if (v.getPredecessors().size() == 0) {
					return v;
				}
			} else {
				return null;
			}
		}
		return null;
	}

	private boolean constructActorGraph(Graph graph, List<Action> actions, List<Action> outside) {
		if (debug) {
			OrccLogger.traceln("<<< building actor graph");
		}
		for (Action a : actions) {
			Vertex v = GraphFactory.eINSTANCE.createVertex();
			v.setLabel(a.getName());
			if (debug) {
				OrccLogger.traceln(" added vertex " + a.getName());
			}
			if (a.hasAttribute("IsPrecededBy")) {
				String str = a.getValueAsString("IsPrecededBy");
				v.setAttribute("IsPrecededBy", str);
			}
			graph.add(v);
		}

		for (Vertex v : graph.getVertices()) {
			if (v.hasAttribute("IsPrecededBy")) {
				String str = v.getValueAsString("IsPrecededBy");
				String[] parts = str.split("-");
				for (String preceding : parts) {
					Edge e = GraphFactory.eINSTANCE.createEdge();
					e.setSource(v);
					Vertex t = locateVertex(graph, preceding);
					if (t == null) {
						continue;
					}
					e.setTarget(t);
					graph.add(e);
					if (debug) {
						OrccLogger.traceln(" added edge " + v.getLabel() + " --> " + t.getLabel());
					}
				}
			}
		}

		for (Action a : outside) {
			Vertex v = findVertexByName(graph.getVertices(), a.getName());
			if (v.getPredecessors().size() == 0 && v.getSuccessors().size() == 0) {
				if (debug) {
					OrccLogger.traceln("Processing ut action " + a.getName());
				}
				for (Action b : actions) {
					if (!a.equals(b)) {
						Vertex t = findVertexByName(graph.getVertices(), b.getName());
						if (t.getSuccessors().size() == 0) {
							Edge e = GraphFactory.eINSTANCE.createEdge();
							e.setSource(t);
							e.setTarget(v);
							graph.add(e);
							if (debug) {
								OrccLogger.traceln(" added ut edge " + t.getLabel() + " --> " + v.getLabel());
							}
						}
					}
				}
			}
		}

		if (debug) {
			OrccLogger.traceln(">>>");
		}
		return true;
	}

	private boolean constructLocalGraph(Graph localGraph, Graph actorGraph, List<Action> actions) {
		for (Action a : actions) {
			Vertex v = GraphFactory.eINSTANCE.createVertex();
			v.setLabel(a.getName());
			if (debug) {
				OrccLogger.traceln("added vertex " + v.getLabel());
			}
			Action p = discoverClosestPredecessor(actorGraph, v.getLabel(), actions);
			if (p != null) {
				if (debug) {
					OrccLogger.traceln(" that is preceded by " + p.getName());
				}
				v.setAttribute("IsPrecededBy", p.getName());
			}
			localGraph.add(v);
		}

		for (Vertex v : localGraph.getVertices()) {
			if (v.hasAttribute("IsPrecededBy")) {
				Edge e = GraphFactory.eINSTANCE.createEdge();
				e.setSource(v);
				String preceding = v.getValueAsString("IsPrecededBy");
				Vertex t = locateVertex(localGraph, preceding);
				if (t != null) {
					if (debug) {
						OrccLogger.traceln("created edge " + v.getLabel() + " -> " + t.getLabel());
					}
					e.setTarget(t);
					localGraph.add(e);
				}
			}
		}
		return true;
	}

	private Vertex locateVertex(Graph graph, String preceding) {
		for (Vertex v : graph.getVertices()) {
			if (v.getLabel().equals(preceding)) {
				return v;
			}
		}
		return null;
	}

	private Action discoverClosestPredecessor(Graph graph, String origin, List<Action> actions) {
		int[] distList = new int[256];
		for (int i = 0; i <  actions.size(); i++) {
			Action a = actions.get(i);
			GraphTraverser traverser = new GraphTraverser(graph);
			int dist = traverser.isPredecessor(origin, a.getName());
			if (debug) {
				OrccLogger.traceln("  predecessor " + a.getName() + " is at distance " + dist);
			}
			distList[i] = dist;
		}
		int minInd = findMinimum(distList, actions.size());
		if (minInd == -1) {
			return null;
		}
		return actions.get(minInd);
	}

	private int findMinimum(int[] list, int size) {
		int min = 20000000;
		int minInd = -1;
		for (int i = 0; i < size; i++) {
			if (list[i] < min && list[i] > 0) {
				min = list[i];
				minInd = i;
			}
		}
		return minInd;
	}
}
