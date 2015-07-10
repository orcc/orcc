package net.sf.orcc.backends.c.dal.transform;

import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.BlockIf;
import net.sf.orcc.ir.ExprBinary;
import net.sf.orcc.ir.ExprUnary;
import net.sf.orcc.ir.ExprVar;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.InstAssign;
import net.sf.orcc.ir.InstCall;
import net.sf.orcc.ir.InstLoad;
import net.sf.orcc.ir.InstReturn;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.OpUnary;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.util.AbstractIrVisitor;
import net.sf.orcc.ir.util.ExpressionPrinter;
import net.sf.orcc.ir.util.IrUtil;
import net.sf.orcc.util.OrccLogger;

/**
 * Perform if-conversion (branch predication) on guards
 *
 * @author Jani Boutellier
 *
 */
public class IfConverter {

	private Detector irVisitor;
	private List<Var> varList;
	private Expression preExpression;
	private Expression conditionExpression;
	private Expression thenExpression;
	private Expression elseExpression;
	private Expression postExpressionCopy;
	private Expression tmpIfParent;
	private ExpressionPrinter exPr;

	private class Detector extends AbstractIrVisitor<Void> {

		public Detector() {
			super(true);
		}

		@Override
		public Void caseExprVar(ExprVar expr) {
			boolean found = false;
			for (Var var : varList) {
				if (var.getName().equals(expr.getUse().getVariable().getName())) {
					found = true;
				}
			}
			if (!found) {
				varList.add(expr.getUse().getVariable());
			}
			return null;
		}
	}

	private class VarFinder extends AbstractIrVisitor<Void> {

		private String name;
		private List<Expression> exprList;

		public VarFinder(String name) {
			this.name = name;
			this.exprList = new ArrayList<Expression>();
		}

		private void checkOperand(Expression expr, Expression operand) {
			if (operand.isExprVar()) {
				if (name.equals(((ExprVar)operand).getUse().getVariable().getName())) {
					exprList.add(expr);
				}
			}
		}

		@Override
		public Void caseExprUnary(ExprUnary expr) {
			doSwitch(expr.getExpr());
			checkOperand(expr, expr.getExpr());
			return null;
		}

		@Override
		public Void caseExprBinary(ExprBinary expr) {
			doSwitch(expr.getE1());
			checkOperand(expr, expr.getE1());
			doSwitch(expr.getE2());
			checkOperand(expr, expr.getE2());
			return null;
		}

		public List<Expression> getList() {
			return exprList;
		}
	}

	private List<VarInstructionPair> loadList;

	private class VarInstructionPair {
		Var var;
		Instruction inst;
		VarInstructionPair(Var var, Instruction inst) {
			this.var = var;
			this.inst = inst;
		}
	}

	private Instruction getInstruction(Var var) {
		for (VarInstructionPair pair : loadList) {
			if (var.getName().equals(pair.var.getName())) {
				return pair.inst;
			}
		}
		OrccLogger.warnln("No load instruction found for variable "
				+ var.getName());
		return null;
	}

	public IfConverter () {
		this.irVisitor = new Detector();
		this.loadList = new ArrayList<VarInstructionPair>();
		this.varList = new ArrayList<Var>();
		this.exPr = new ExpressionPrinter();
	}

	private boolean extractExpressions(Action action) {
		for (Block b : action.getScheduler().getBlocks()) {
			if (b.isBlockBasic()) {
				if (conditionExpression == null) {
					preExpression = extractComputeExpression(((BlockBasic) b).getInstructions());
					if (preExpression != null) {
						OrccLogger.warnln("IfConverter: PreExpression contains compute expression");
					}
				} else {
					if (postExpressionCopy != null) {
						OrccLogger.warnln("IfConverter: Multiple PostExpressions");
					}
					postExpressionCopy = IrUtil.copy(extractComputeExpression(((BlockBasic) b).getInstructions()));
				}
			} else if (b.isBlockIf()) {
				conditionExpression = ((BlockIf) b).getCondition();
				if (((BlockIf) b).getThenBlocks().size() < 1) {
					OrccLogger.warnln("IfConverter: ThenBlock empty in "
							+ action.getName());
				} else if (((BlockIf) b).getThenBlocks().size() > 1) {
					OrccLogger.warnln("IfConverter: more than one ThenBlock in "
							+ action.getName());
				} else {
					for (Block bb : ((BlockIf) b).getThenBlocks()) {
						thenExpression = extractComputeExpression(((BlockBasic) bb).getInstructions());
						if (thenExpression == null) {
							thenExpression = createAssignFromLastLoad(((BlockBasic) bb).getInstructions());
							if (thenExpression == null) {
								OrccLogger.warnln("Failed to extract compute expression from ThenBlock of " + action.getName());
							}
						}
					}
				}
				if (((BlockIf) b).getElseBlocks() != null) {
					if (((BlockIf) b).getElseBlocks().size() < 1) {
						OrccLogger.warnln("IfConverter: ElseBlock empty in "
								+ action.getName());
					} else if (((BlockIf) b).getElseBlocks().size() > 1) {
						OrccLogger.warnln("IfConverter: more than one ElseBlock in "
								+ action.getName());
					} else {
						for (Block bb : ((BlockIf) b).getElseBlocks()) {
							elseExpression = extractComputeExpression(((BlockBasic) bb).getInstructions());
							if (elseExpression == null) {
								elseExpression = createAssignFromLastLoad(((BlockBasic) bb).getInstructions());
								if (elseExpression == null) {
									OrccLogger.warnln("Failed to extract compute expression from ElseBlock of " + action.getName());
								}
							}
						}
					}
				} else {
					OrccLogger.warnln("IfConverter: no compute expression in ElseBlock of"
							+ action.getName());
				}
				if(extractComputeExpression(((BlockIf) b).getJoinBlock().getInstructions()) != null) {
					OrccLogger.warnln("IfConverter: unsupported JoinBlock encountered "
						+ action.getName());
				}
			} else {
				OrccLogger.warnln("IfConverter: unsupported block type in guard of "
						+ action.getName());
				return false;
			}
		}
		return true;
	}

	private Expression extractComputeExpression(List<Instruction> guard) {
		Expression compute = null;
		for (Instruction i : guard) {
			if (i.isInstAssign()) {
				return ((InstAssign) i).getValue();
			} else if (i.isInstReturn()) {
				return ((InstReturn) i).getValue();
			}
		}
		return compute;
	}

	private Expression createAssignFromLastLoad(List<Instruction> guard) {
		Expression compute = null;
		Instruction lastInstruction = guard.get(guard.size() - 1);
		if (lastInstruction.isInstLoad()) {
			compute = IrFactory.eINSTANCE.createExprVar(((InstLoad) lastInstruction).getSource().getVariable());
		}
		return compute;
	}

	private void extractLoadBlockBasic(BlockBasic b) {
		for (Instruction i : b.getInstructions()) {
			if (i.isInstLoad()) {
				loadList.add(new VarInstructionPair(
						((InstLoad) i).getTarget().getVariable(), i));
			} else if (i.isInstCall()) {
				loadList.add(new VarInstructionPair(
						((InstCall) i).getTarget().getVariable(), i));
			}
		}
	}

	private boolean extractLoads(Action action) {
		for (Block b : action.getScheduler().getBlocks()) {
			if (b.isBlockBasic()) {
				extractLoadBlockBasic((BlockBasic) b);
			} else if (b.isBlockIf()) {
				for (Block bb : ((BlockIf) b).getThenBlocks()) {
					extractLoadBlockBasic((BlockBasic)bb);
				}
				if (((BlockIf) b).getElseBlocks() != null) {
					for (Block bb : ((BlockIf) b).getElseBlocks()) {
						extractLoadBlockBasic((BlockBasic)bb);
					}
				}
				extractLoadBlockBasic(((BlockIf) b).getJoinBlock());
			} else {
				OrccLogger.warnln("IfConverter: unsupported block type in guard of "
						+ action.getName());
				return false;
			}
		}
		return true;
	}

	private boolean hasIfBlock(Procedure proc) {
		if (proc != null) {
			for (Block b : proc.getBlocks()) {
				if (b.isBlockIf()) {
					return true;
				}
			}
		}
		return false;
	}

	public void ifConvertGuards(Actor actor) {
		for (Action a : actor.getActions()) {
			if (hasIfBlock(a.getScheduler())) {
				Procedure newScheduler = doIfConversion(a);
				if (newScheduler != null) {
					a.setScheduler(newScheduler);
				}
			}
		}
	}

	private void extractTmpIf(Expression expr) {
		VarFinder varFinder = new VarFinder("tmp_if");
		varFinder.doSwitch(expr);
		if (varFinder.getList().size() > 1) {
			OrccLogger.warnln("IfConverter: more than one instances of tmp_if in expression");
		} else if (varFinder.getList().size() == 1) {
			tmpIfParent = varFinder.getList().get(0);
		} else {
			if (expr.isExprVar()) {
				tmpIfParent = expr;
			} else {
				OrccLogger.warnln("IfConverter: Error locating join point in " + exPr.doSwitch(expr));
			}
		}
	}

	private Procedure doIfConversion(Action action) {
		if (extractLoads(action)) {
			preExpression = null;
			conditionExpression = null;
			thenExpression = null;
			elseExpression = null;
			postExpressionCopy = null;
			tmpIfParent = null;
			extractExpressions(action);
			extractTmpIf(postExpressionCopy);
			return createNewGuard(action);
		}
		return null;
	}

	private Expression negateExpression(Expression expr) {
		return IrFactory.eINSTANCE.createExprUnary(OpUnary.LOGIC_NOT, expr,
				expr.getType());
	}

	private Expression andExpression(Expression expr1, Expression expr2) {
		return IrFactory.eINSTANCE.createExprBinary(expr1, OpBinary.LOGIC_AND,
				expr2, expr1.getType());
	}

	private Expression orExpression(Expression expr1, Expression expr2) {
		return IrFactory.eINSTANCE.createExprBinary(expr1, OpBinary.LOGIC_OR,
				expr2, expr1.getType());
	}

	private void replaceSubExpr1(ExprBinary binPar, Expression compute) {
		if (binPar.getE1().isExprVar()) {
			if(((ExprVar)binPar.getE1()).getUse().getVariable().getName().equals("tmp_if")) {
				binPar.setE1(compute);
			}
		}
	}

	private void replaceSubExpr2(ExprBinary binPar, Expression compute) {
		if (binPar.getE2().isExprVar()) {
			if(((ExprVar)binPar.getE2()).getUse().getVariable().getName().equals("tmp_if")) {
				binPar.setE2(compute);
			}
		}
	}

	private Expression replaceTmpIf(Expression tmpIfPar, Expression compute) {
		if (tmpIfPar.isExprVar()) {
			return compute;
		} else if (tmpIfPar.isExprUnary()) {
			((ExprUnary)tmpIfPar).setExpr(compute);
		} else {
			replaceSubExpr1((ExprBinary)tmpIfPar, compute);
			replaceSubExpr2((ExprBinary)tmpIfPar, compute);
		}
		return tmpIfPar;
	}

	private Procedure createNewGuard(Action action) {
		Procedure proc = IrFactory.eINSTANCE.createProcedure("isSchedulable_"
				+ action.getName(), 0,
				IrFactory.eINSTANCE.createTypeBool());
		Var result = proc.newTempLocalVariable(
				IrFactory.eINSTANCE.createTypeBool(), "result");

		Expression leftExpression = andExpression(IrUtil.copy(conditionExpression), IrUtil.copy(thenExpression));
		Expression rightExpression = andExpression(negateExpression(IrUtil.copy(conditionExpression)), IrUtil.copy(elseExpression));
		Expression computeExpression = orExpression(leftExpression, rightExpression);
		computeExpression = replaceTmpIf(tmpIfParent, computeExpression);
		computeExpression = postExpressionCopy;

		irVisitor.doSwitch(computeExpression);
		proc.getBlocks().add(IrFactory.eINSTANCE.createBlockBasic());
		for(Var var : varList) {
			Instruction inst = getInstruction(var);
			if(inst == null) {
				return null;
			}
			proc.getLast().add(IrUtil.copy(inst));
		}
		InstAssign assign = IrFactory.eINSTANCE.createInstAssign(result,
				computeExpression);
		proc.getLast().add(assign);
		proc.getLast().add(IrFactory.eINSTANCE.createInstReturn(
				IrFactory.eINSTANCE.createExprVar(result)));
		return proc;
	}
}
