package net.sf.orcc.backends.xlim;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.instructions.Assign;
import net.sf.orcc.ir.instructions.Call;
import net.sf.orcc.ir.instructions.HasTokens;
import net.sf.orcc.ir.instructions.InitPort;
import net.sf.orcc.ir.instructions.InstructionVisitor;
import net.sf.orcc.ir.instructions.Load;
import net.sf.orcc.ir.instructions.Peek;
import net.sf.orcc.ir.instructions.PhiAssignment;
import net.sf.orcc.ir.instructions.Read;
import net.sf.orcc.ir.instructions.ReadEnd;
import net.sf.orcc.ir.instructions.Return;
import net.sf.orcc.ir.instructions.SpecificInstruction;
import net.sf.orcc.ir.instructions.Store;
import net.sf.orcc.ir.instructions.Write;
import net.sf.orcc.ir.instructions.WriteEnd;

/**
 * XlimInstructionExecutor executes initialization to get initial values
 * 
 * @author Samuel Keller
 */
public class XlimInstructionExecutor implements InstructionVisitor {

	/**
	 * Variables data
	 */
	private Map<String, Object> datas;

	/**
	 * Obtained tables
	 */
	private Map<String, List<Integer>> tables;

	/**
	 * Constructs XlimInstructionExecutor
	 */
	public XlimInstructionExecutor() {
		datas = new TreeMap<String, Object>();
		tables = new TreeMap<String, List<Integer>>();
	}

	/**
	 * Get the variables datas
	 * 
	 * @return Variables datas
	 */
	public Map<String, Object> getDatas() {
		return datas;
	}

	/**
	 * Get the obtained tables
	 * 
	 * @return Obtained tables
	 */
	public Map<String, List<Integer>> getTables() {
		return tables;
	}

	/**
	 * Visit assign node : store new variable data
	 * 
	 * @param node
	 *            Assign node
	 */
	public void visit(Assign node, Object... args) {
		try {
			datas.put(node.getTarget().toString(), node.getValue().accept(
					new XlimExpressionExecutor(datas)));
		} catch (OrccException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Visit call node
	 * 
	 * @param node
	 *            Call node
	 */
	public void visit(Call node, Object... args) {
	}

	/**
	 * Visit HasTokens node
	 * 
	 * @param node
	 *            HasTokens node
	 */
	public void visit(HasTokens node, Object... args) {
	}

	/**
	 * Visit InitPort node
	 * 
	 * @param node
	 *            InitPort node
	 */
	public void visit(InitPort node, Object... args) {
	}

	/**
	 * Visit Load node
	 * 
	 * @param node
	 *            Load node
	 */
	public void visit(Load node, Object... args) {
	}

	/**
	 * Visit Peek node
	 * 
	 * @param node
	 *            Peek node
	 */
	public void visit(Peek node, Object... args) {
	}

	/**
	 * Visit PhiAssignment node
	 * 
	 * @param node
	 *            PhiAssignment node
	 */
	public void visit(PhiAssignment node, Object... args) {
	}

	/**
	 * Visit Read node
	 * 
	 * @param node
	 *            Read node
	 */
	public void visit(Read node, Object... args) {
	}

	/**
	 * Visit ReadEnd node
	 * 
	 * @param node
	 *            ReadEnd node
	 */
	public void visit(ReadEnd node, Object... args) {
	}

	/**
	 * Visit Return node
	 * 
	 * @param node
	 *            Return node
	 */
	public void visit(Return node, Object... args) {
	}

	/**
	 * Visit SpecificInstruction node
	 * 
	 * @param node
	 *            SpecificInstruction node
	 */
	public void visit(SpecificInstruction node, Object... args) {
	}

	/**
	 * Visit Store node : store the obtained value in table
	 * 
	 * @param node
	 *            Store node
	 */
	public void visit(Store node, Object... args) {
		String name = node.getTarget().getVariable().getName();
		List<Integer> list;
		if (tables.containsKey(name)) {
			list = tables.get(name);
		} else {
			list = new LinkedList<Integer>();
			tables.put(name, list);
		}
		try {
			list.add((Integer) node.getValue().accept(
					new XlimExpressionExecutor(datas)));
		} catch (OrccException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Visit Write node
	 * 
	 * @param node
	 *            Write node
	 */
	public void visit(Write node, Object... args) {
	}

	/**
	 * Visit WriteEnd node
	 * 
	 * @param node
	 *            WriteEnd node
	 */
	public void visit(WriteEnd node, Object... args) {
	}

}
