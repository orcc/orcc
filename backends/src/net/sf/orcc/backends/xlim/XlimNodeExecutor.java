package net.sf.orcc.backends.xlim;

import java.util.List;
import java.util.Map;

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.CFGNode;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.nodes.BlockNode;
import net.sf.orcc.ir.nodes.IfNode;
import net.sf.orcc.ir.nodes.NodeVisitor;
import net.sf.orcc.ir.nodes.WhileNode;

/**
 * XlimNodeExecutor executes initialization to get initial values
 * 
 * @author Samuel Keller
 */
public class XlimNodeExecutor implements NodeVisitor {

	/**
	 * Instruction executor
	 */
	private XlimInstructionExecutor exec;

	/**
	 * Variables datas
	 */
	private Map<String, Object> datas;

	/**
	 * Constructs XlimNodeExecutor
	 */
	public XlimNodeExecutor() {
		exec = new XlimInstructionExecutor();
		datas = exec.getDatas();
	}

	/**
	 * Get obtained tables
	 * 
	 * @return Obtained tables
	 */
	public Map<String, List<Integer>> getTables() {
		return exec.getTables();
	}

	/**
	 * Visit BlockNode
	 * 
	 * @param node
	 *            BlockNode
	 */
	public Object visit(BlockNode node, Object... args) {
		for (Instruction instruction : node.getInstructions()) {
			instruction.accept(exec);
		}
		return null;
	}

	/**
	 * Visit IfNode
	 * 
	 * @param node
	 *            IfNode
	 */
	public Object visit(IfNode node, Object... args) {
		XlimExpressionExecutor expr = new XlimExpressionExecutor(datas);
		try {
			if ((Boolean) node.getValue().accept(expr)) {
				for (CFGNode nodes : node.getThenNodes()) {
					nodes.accept(this);
				}
			} else {
				for (CFGNode nodes : node.getElseNodes()) {
					nodes.accept(this);
				}
			}
		} catch (OrccException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Visit WhileNode
	 * 
	 * @param node
	 *            WhileNode
	 */
	public Object visit(WhileNode node, Object... args) {
		XlimExpressionExecutor expr = new XlimExpressionExecutor(datas);
		try {
			while ((Boolean) node.getValue().accept(expr)) {
				for (CFGNode nodes : node.getNodes()) {
					nodes.accept(this);
				}
			}
		} catch (OrccException e) {
			e.printStackTrace();
		}
		return null;
	}

}
