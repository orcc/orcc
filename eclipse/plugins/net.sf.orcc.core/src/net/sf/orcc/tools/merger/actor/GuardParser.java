package net.sf.orcc.tools.merger.actor;

import static net.sf.orcc.ir.IrFactory.eINSTANCE;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Pattern;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Port;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.OpBinary;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Var;
import net.sf.orcc.util.DomUtil;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * This class provides a way to import guards from XML files.
 * 
 * @author Jani Boutellier
 * @author Ghislain Roquier
 * 
 */

public class GuardParser {

	private String definitionFile;
	
	private Procedure scheduler;
	
	private Pattern peekPattern;
	
	private Actor superActor;

	private int peekCount;

	private List<Port> peekPorts;
	
	private List<Var> peekVariables;
	
	private static final int T_LEFT = 0;
	private static final int T_RIGHT = 1;
	private static final String S_LEFT = "left";
	private static final String S_RIGHT = "right";

	
	public GuardParser(String definitionFile, String actionName, Actor superactor) {
		this.definitionFile = definitionFile;
		this.superActor = superactor;
		this.scheduler = eINSTANCE
				.createProcedure("isSchedulable_" + actionName, 0,
						eINSTANCE.createTypeBool());
		this.peekPattern = DfFactory.eINSTANCE.createPattern();
		peekVariables = new ArrayList<Var>();
		peekPorts = new ArrayList<Port>();
		peekCount = 0;
	}

	public void parse(String superactor, String superaction) {
		try {
			InputStream is = new FileInputStream(definitionFile);
			Document document = DomUtil.parseDocument(is);
			parseSuperactorList(document, superactor, superaction);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Procedure getGuard() {
		return scheduler;
	}

	public Pattern getPeekPattern() {
		return peekPattern;
	}
	
	private void parseSuperactorList(Document doc, String superactor, String superaction) {
		Element root = doc.getDocumentElement();
		Node node = root.getFirstChild();
		while (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element element = (Element) node;
				if (node.getNodeName().equals("superactor") && (element.getAttribute("name").equals(superactor))) {
					parseSuperactor(element, superaction);
				} else {
					// TODO: manage error
				}
			}
			node = node.getNextSibling();
		}
	}
	
	private void parseSuperactor(Element element, String superaction) {
		Node node = element.getFirstChild();
		while (node != null) {
			if (node instanceof Element) {
				Element elt = (Element) node;
				if (node.getNodeName().equals("superaction") && (elt.getAttribute("name").equals(superaction))) {
					Expression comparison = parseSuperaction(elt);
					createPeekInstructions();
					scheduler.getLast().add(
						IrFactory.eINSTANCE.createInstReturn(comparison));
					} else {
					// TODO: manage error
				}
			}
			node = node.getNextSibling();
		}
	}
	
	private void createPeekInstructions() {
		for (int i = 0; i < peekCount; i++) {
			scheduler.getLast().add(createPeekInstruction(peekVariables.get(i), peekPorts.get(i)));
		}
	}
	
	private Instruction createPeekInstruction(Var target, Port port) {
		return IrFactory.eINSTANCE.createInstLoad(target, IrFactory.eINSTANCE.createVar(0,
				IrFactory.eINSTANCE.createTypeList(1, port.getType()),
				"tokens_" + port.getName(), true, 0), createPeekIndex(port));
	}

	private List<Expression> createPeekIndex(Port port) {
		Var sizeVar = IrFactory.eINSTANCE.createVar(0,
				IrFactory.eINSTANCE.createTypeList(1, port.getType()),
				new String("SIZE_" + port.getName()), true, 0);
		Var indexVar = IrFactory.eINSTANCE.createVar(0,
				IrFactory.eINSTANCE.createTypeList(1, port.getType()),
				new String("index_" + port.getName()), true, 0);
		List<Expression> indexExpression = new ArrayList<Expression>(1);
		indexExpression.add(eINSTANCE.createExprBinary(IrFactory.eINSTANCE.createExprVar(indexVar),
				OpBinary.MOD, IrFactory.eINSTANCE.createExprVar(sizeVar), port.getType()));
		return indexExpression;
	}
	
	private Expression parseSuperaction(Element element) {
		Expression guard = null;
		Node node = element.getFirstChild();
		while (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element elt = (Element) node;
				if (node.getNodeName().equals("guard")) {
					guard = parseGuard(elt);			
				} 
			}
			node = node.getNextSibling();
		}
		if (guard == null) {
			guard = IrFactory.eINSTANCE.createExprBool(true);
		}
		return guard;
	}

	private Expression parseGuard(Element element) {
		Node node = element.getFirstChild();
		while (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element elt = (Element) node;
				String nodeName = node.getNodeName();
				if (nodeName.equals("binexp")) {
					return eINSTANCE.createExprBinary(parseSide(elt, T_LEFT), OpBinary.getOperator(elt.getAttribute("kind")),
							parseSide(elt, T_RIGHT), eINSTANCE.createTypeBool());
				} else if (nodeName.equals("operand")) {
					return parseOperand(elt.getAttribute("name"), elt.getAttribute("kind"));
				} else {
					// TODO: manage error
				}
			}
			node = node.getNextSibling();
		}
		return null;
	}

	private Expression parseSide(Element element, int side) {
		Node node = element.getFirstChild();
		while (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element elt = (Element) node;
				String nodeName = node.getNodeName();
				if (nodeName.equals(S_LEFT) && (side == T_LEFT)) {
					return parseGuard(elt);			
				} else if (nodeName.equals(S_RIGHT) && (side == T_RIGHT)) {
					return parseGuard(elt);			
				}
			}
			node = node.getNextSibling();
		}
		return null;
	}
	
	private Expression parseOperand(String nameStr, String typeStr) {
		Expression expression;
		if (typeStr.equals("state_variable")) {
			expression = IrFactory.eINSTANCE.createExprVar(superActor.getStateVar(nameStr));
		} else if (typeStr.equals("port")) {
			expression = IrFactory.eINSTANCE.createExprVar(findPortVariable(nameStr));
		} else if (typeStr.equals("constant")) {
			expression = IrFactory.eINSTANCE.createExprInt(Integer.parseInt(nameStr));
		} else {
			expression = null;
		}
		return expression;
	}

	private Var findPortVariable(String name) {
		for (Port port : superActor.getInputs()) {
			if (port.getName().equals(name)) {
				return addToPeekList(port);
			}
		}
		return null;
	}

	private Var addToPeekList(Port port) {
		createVariableIntroduction(port);
		Var peekVar = IrFactory.eINSTANCE.createVar(0,
				IrFactory.eINSTANCE.createTypeList(1, port.getType()),
				new String(port.getName() + "_peek"), true, 0);
		peekVariables.add(peekVar);
		peekCount ++;
		peekPorts.add(port);
		peekPattern.setNumTokens(port, 1);
		peekPattern.setVariable(port, peekVar);
		return peekVar;
	}

	private void createVariableIntroduction(Port port) {
		scheduler.newTempLocalVariable(
				IrFactory.eINSTANCE.createTypeInt(32), port.getName() + "_peek");
	}
	
}
