package net.sf.orcc.tools.merger.actor;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.FSM;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.State;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.Var;

public class MergerUtil {

	public static int findPort(List<Port> portList, String name) {
		for (int i = 0; i < portList.size(); i++) {
			if (portList.get(i).getName().equals(name)) {
				return i;
			}
		}
		return -1;
	}

	public static Action findAction(Actor actor, String actionName) {
		for(Action action : actor.getActions()) {
			if(action.getName().equals(actionName)) {
				return action;
			}
		}
		return null;
	}
	
	public static State findState(FSM fsm, String stateName) {
		for(State state : fsm.getStates()) {
			if(state.getName().equals(stateName))
				return state;
		}
		return null;
	}
	
	/**
	 * Locate actor that owns specified action and port
	 * 
	 * @param network
	 *            the associated network
	 * @param action
	 *            the action used as a search criteria
	 * @param port
	 *            the port used as a search criteria
	 */
	public static Actor getOwningActor(Network network, Action action, Port port) {
		for(Actor actor : network.getAllActors()) {
			for(Action candidateAction : actor.getActions()) {
				if (candidateAction.getName().equals(action.getName())) {
					for(Port candidatePort : candidateAction.getOutputPattern().getPorts()) {
						if(candidatePort.getName().equals(port.getName())) {
							return actor;
						}				
					}
				}
			}
		}
		return null;
	}

	public static boolean testFilePresence(String fileName) {
		try {
			InputStream is = new FileInputStream(fileName);
			is.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	public static Var createIndexVar(Port port) {
		return IrFactory.eINSTANCE.createVar(0,
				IrFactory.eINSTANCE.createTypeList(1, port.getType()),
				new String("index_" + port.getName()), true, 0);
	}

	public static List<Expression> createModuloIndex(Port port, Var indexVar) {
		Var sizeVar = IrFactory.eINSTANCE.createVar(0,
				IrFactory.eINSTANCE.createTypeList(1, port.getType()),
				new String("SIZE_" + port.getName()), true, 0);
		List<Expression> indexExpression = new ArrayList<Expression>(1);
		indexExpression.add(IrFactory.eINSTANCE.createExprBinary(
				IrFactory.eINSTANCE.createExprVar(indexVar), OpBinary.MOD,
				IrFactory.eINSTANCE.createExprVar(sizeVar), port.getType()));
		return indexExpression;
	}
	
	public static Instruction createBinOpStore(Var variable, OpBinary operation, int constant) {
		return IrFactory.eINSTANCE.createInstStore(variable, 
				IrFactory.eINSTANCE.createExprBinary(IrFactory.eINSTANCE.createExprVar(variable), operation, 
				IrFactory.eINSTANCE.createExprInt(constant), IrFactory.eINSTANCE.createTypeInt()));		
	}
	
}
