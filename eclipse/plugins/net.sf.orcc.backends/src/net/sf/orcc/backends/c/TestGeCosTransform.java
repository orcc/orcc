package net.sf.orcc.backends.c;

import fr.irisa.cairn.ecore.tools.query.EMFUtils;
import fr.irisa.cairn.gecos.gscop.GScopFSM;
import fr.irisa.cairn.gecos.gscop.GScopPackage;
import fr.irisa.cairn.gecos.gscop.GScopRoot;
import fr.irisa.cairn.gecos.gscop.LoopIterator;
import fr.irisa.cairn.gecos.model.c.generator.XtendCGenerator;
import fr.irisa.cairn.gecos.model.cdtfrontend.CDTFrontEnd;
import fr.irisa.cairn.gecos.model.factory.ControlStructureFactory;
import fr.irisa.cairn.gecos.model.factory.GecosCoreFactory;
import fr.irisa.cairn.gecos.model.factory.TypeFactory;
import fr.irisa.cairn.gecos.model.modules.AddSourceToGecosProject;
import fr.irisa.cairn.gecos.model.modules.CreateProject;
import fr.irisa.cairn.gecos.model.modules.SaveGecosProject;
import fr.irisa.cairn.gecos.scop.analysis.dataflow.GecosArrayDataflowAnalysis;
import fr.irisa.cairn.gecos.scop.transfom.fsm.ActorSplittingTransform;
import fr.irisa.cairn.gecos.scop.transfom.fsm.HybridFSMCloogCodeGenerator;
import fr.irisa.cairn.gecos.scop.transfom.fsm.PortInfoGeCosToCAL;
import fr.irisa.cairn.model.gecos.scop.extractor.ScopExtractorPass;
import fr.irisa.cairn.model.gecos.scop.scheduling.GScopScheduler;
import gecos.blocks.BasicBlock;
import gecos.blocks.CompositeBlock;
import gecos.blocks.ForBlock;
import gecos.blocks.IfBlock;
import gecos.blocks.WhileBlock;
import gecos.core.CoreFactory;
import gecos.core.ParameterSymbol;
import gecos.core.ProcedureSet;
import gecos.core.Symbol;
import gecosproject.GecosProject;
import gecosproject.GecosSourceFile;
import gecosproject.GecosprojectFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.core.runtime.CoreException;

import net.sf.orcc.backends.c.NetworkPortInfo.NetworkPortType;
import net.sf.orcc.backends.ir.BlockFor;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Port;
import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.BlockIf;
import net.sf.orcc.ir.BlockWhile;
import net.sf.orcc.ir.ExprList;
import net.sf.orcc.ir.ExprString;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.Param;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Var;
import net.sf.orcc.util.Attribute;
import net.sf.orcc.util.OrccLogger;
//import fr.irisa.cairn.model.gecos.scop.scheduling.AbstractGScopScheduler;
//import fr.irisa.cairn.model.gecos.scop.scheduling.GScopScheduler;
//import fr.irisa.cairn.model.gecos.scop.scheduling.pluto.ScopPRDGPlutoScheduling;


public class TestGeCosTransform {
	
	static gecos.core.Procedure gecosProc;
	
	static gecos.blocks.Block transformToGecosBlock(Block a) {
		/*%match ( a ){
			BlockBasic(instl) -> { OrccLogger.traceln("BasicBlock"); }
		} */
		if ( a instanceof BlockBasic) {
			OrccLogger.traceln("Basicblock ");
			return processBasicBlock((BlockBasic)a);
		} else if ( a instanceof BlockIf ) {
			OrccLogger.traceln("BasicIf ");
			return processIfBlock((BlockIf)a);
		} else if ( a instanceof BlockWhile) {
			OrccLogger.traceln("BasicWhile ");
			return processWhileBlock((BlockWhile)a);
		} else if ( a instanceof BlockFor) {
			OrccLogger.traceln("Basicfor ");
			return processForBlock((BlockFor)a);
		}
		return null;
	}
	
	static gecos.blocks.BasicBlock processBasicBlock(BlockBasic bb) {
		BasicBlock gecosBB = ControlStructureFactory.BBlock();
		for ( Instruction inst : bb.getInstructions() ) {
			gecos.instrs.Instruction gecosInst = TransformToGecosInstruction.convertToGecosInst(inst, gecosProc);
			gecosBB.addInstruction(gecosInst);
		}
		return gecosBB;
	}
	
	static gecos.blocks.WhileBlock processWhileBlock(BlockWhile wbb ) {
		WhileBlock gecosWhile = ControlStructureFactory.While(ControlStructureFactory.BBlock());
		return gecosWhile;
	}

	static IfBlock processIfBlock ( BlockIf ifbb) {
		gecos.instrs.Instruction condInst = TransformToGecosInstruction.convertToGecosInst(ifbb.getCondition(), gecosProc);
		CompositeBlock thenBlock = ControlStructureFactory.CompositeBlock();
		for ( Block b : ifbb.getThenBlocks() ) {
			gecos.blocks.Block gecosBlock = transformToGecosBlock(b);
			thenBlock.addChildren(gecosBlock);
		}
		if ( ifbb.getElseBlocks().size() != 0 ) {
			CompositeBlock elseBlock = ControlStructureFactory.CompositeBlock();
			for ( Block b : ifbb.getElseBlocks() ) {
				gecos.blocks.Block gecosBlock = transformToGecosBlock(b);
				elseBlock.addChildren(gecosBlock);
			}
			return ControlStructureFactory.IfThenElse(condInst, thenBlock, elseBlock);
		} else {
			return ControlStructureFactory.IfThen(condInst, thenBlock);
		}
	}

	static ForBlock processForBlock ( BlockFor fbb) {
		BasicBlock init = ControlStructureFactory.BBlock();
		for ( Instruction inst : fbb.getInit() ) {
			gecos.instrs.Instruction gecosInst = TransformToGecosInstruction.convertToGecosInst(inst, gecosProc);
			init.addInstruction(gecosInst);
		}
		BasicBlock test = ControlStructureFactory.BBlock();
		gecos.instrs.Instruction condInst = TransformToGecosInstruction.convertToGecosInst(fbb.getCondition(), gecosProc);
		test.addInstruction(condInst);
		BasicBlock step = ControlStructureFactory.BBlock();
		for ( Instruction inst : fbb.getStep() ) {
			gecos.instrs.Instruction gecosInst = TransformToGecosInstruction.convertToGecosInst(inst, gecosProc);
			step.addInstruction(gecosInst);
		}
		CompositeBlock bodyBlock = ControlStructureFactory.CompositeBlock();
		for ( Block b : fbb.getBlocks() ) {
			gecos.blocks.Block gecosBlock = transformToGecosBlock(b);
			bodyBlock.addChildren(gecosBlock);
		}
		return ControlStructureFactory.For(init, test, step, bodyBlock);
	}
	

	/*%strategy visitCFG() extends Identity(){ 
		visit Block {
			BlockBasic -> { } 
		} 
	} */

	public static gecos.core.ParameterSymbol convertToGecosParameter(Param p, int value) {
		String name = p.getVariable().getName();
		gecos.types.Type paramType = TransformToGecosInstruction.convertToGeCosType(p.getVariable().getType());
		gecos.core.ParameterSymbol gecosParam = GecosCoreFactory.paramSymbol(name, paramType);
		return gecosParam;
	}
	
	public static gecos.core.Procedure createProcedure (Procedure body, Action action, ProcedureSet procedureSet, CompositeBlock gecosBody) {
		/*Get Parameters */
		List<ParameterSymbol> paramList = new ArrayList<ParameterSymbol>();
		int numParam = 0;
		for ( Port p : action.getInputPattern().getPorts() ) {
			/* to differentiate between port name and the buffer */
			String name = action.getInputPattern().getVariable(p).getName();
			gecos.types.Type paramType = TransformToGecosInstruction.convertToGeCosType(p.getType());
			int size = action.getInputPattern().getNumTokensMap().get(p);
			paramType = TypeFactory.ARRAY(paramType, size);
			gecos.core.ParameterSymbol paramSym = GecosCoreFactory.paramSymbol(name, paramType);
			paramList.add(paramSym);
			procedureSet.getScope().getTypes().add(paramSym.getType());
			numParam++;
		}
		for ( Port p : action.getOutputPattern().getPorts() ) {
			String name = action.getOutputPattern().getVariable(p).getName();
			gecos.types.Type paramType = TransformToGecosInstruction.convertToGeCosType(p.getType());
			int size = action.getOutputPattern().getNumTokensMap().get(p);
			paramType = TypeFactory.ARRAY(paramType, size);
			gecos.core.ParameterSymbol paramSym = GecosCoreFactory.paramSymbol(name, paramType);
			paramList.add(paramSym);
			procedureSet.getScope().getTypes().add(paramSym.getType());
			numParam++;
		}
		for ( Param p : body.getParameters() ) {
			gecos.core.ParameterSymbol paramSym = convertToGecosParameter(p, numParam);
			paramList.add(paramSym);
			procedureSet.getScope().getTypes().add(paramSym.getType());
			numParam++;
		} 
		Type returnType = body.getReturnType();
		gecos.types.Type gecosRetType = TransformToGecosInstruction.convertToGeCosType(returnType);
		procedureSet.getScope().getTypes().add(gecosRetType);
		/* add local variables into procedure */
		for ( Var v : body.getLocals()) {
			Symbol sym = CoreFactory.eINSTANCE.createSymbol();
			sym.setScope(gecosBody.getScope());
			sym.setType(TransformToGecosInstruction.convertToGeCosType(v.getType()));
			sym.setName(v.getName());
			gecosBody.getScope().getSymbols().add(sym);
			gecosBody.getScope().getTypes().add(sym.getType());
		}
		gecosProc = GecosCoreFactory.proc(procedureSet, body.getName(), gecosRetType, gecosBody, paramList);
		/*int numParam = 0;
		for ( Param p : body.getParameters() ) {
			gecos.core.ParameterSymbol paramSym = convertToGecosParameter(p, numParam);
			paramSym.setScope(gecosProc.getScope());
			gecosProc.getScope().getSymbols().add(paramSym);
			numParam++;
		} */
		
		return gecosProc;
	}


	public static void exec(Actor actor) {

		OrccLogger.traceln(actor.getName());
				
		if ( actor.hasAttribute("gecos")) {
			OrccLogger.traceln("Actor " + actor.getName() + " will be analyzed by gecos");
			if ( actor.getActions().size() > 1 ) {
				OrccLogger.traceln("Actor has more than one action");
			}
			Attribute att = actor.getAttribute("gecos");
			ExprList val = (ExprList)att.getContainedValue();
			int numActors = 3;
			int tileSize = 4;
			if ( val.get(0).isExprList() ) {
				ExprList list = (ExprList)val.get(0);
				if ( list.get(1).isExprString() ) {
					ExprString str = (ExprString)list.get(1);
					tileSize = Integer.parseInt(str.getValue());
				}
			}
			if ( val.get(1).isExprList() ) {
				ExprList list = (ExprList)val.get(1);
				if ( list.get(1).isExprString() ) {
					ExprString str = (ExprString)list.get(1);
					numActors = Integer.parseInt(str.getValue());
				}
			}
			String actorName = actor.getName();
			
			Action action = actor.getActions().get(0);
			Procedure body = action.getBody();
			ProcedureSet procedureSet = GecosCoreFactory.procedureSet();
			TypeFactory.setScope(procedureSet.getScope());
			/* Add state variables of actor int procedureSet scope */
			for ( Var v : actor.getStateVars() ) {
				Symbol sym = CoreFactory.eINSTANCE.createSymbol();
				sym.setScope(procedureSet.getScope());
				sym.setType(TransformToGecosInstruction.convertToGeCosType(v.getType()));
				sym.setName(v.getName());
			//	gecos.instrs.Instruction value = TransformToGecosInstruction.convertToGecosInst(v.getInitialValue(), proc);
			//	sym.setValue(value);
				procedureSet.getScope().getSymbols().add(sym);
			}
			gecos.core.Scope s = GecosCoreFactory.scope();
			CompositeBlock gecosBody = ControlStructureFactory.CompositeBlock(s);
			gecosProc = createProcedure(body, action, procedureSet, gecosBody);
			System.out.println("Gecos Procedure is " + gecosProc);
			for ( Block b : body.getBlocks() ) {
				gecos.blocks.Block gecosBlock = transformToGecosBlock(b);
				gecosBody.addChildren(gecosBlock);
			}
			System.out.println(gecosProc.getBody().getScope());
			procedureSet.getProcedures().add(gecosProc);
			GecosProject gecosProject = GecosprojectFactory.eINSTANCE.createGecosProject();
			GecosSourceFile gecosSourceFile = GecosprojectFactory.eINSTANCE.createGecosSourceFile();
			gecosProject.getSources().add(gecosSourceFile);
			gecosSourceFile.setName("dummyProgram.c");
			gecosSourceFile.setModel(procedureSet);
			new XtendCGenerator(gecosProject, "/home/mythri/src-regen/").compute();
			CreateProject createProj = new CreateProject("compaGecosProj");
			GecosProject newProject = createProj.compute();
			try {
				AddSourceToGecosProject a = new AddSourceToGecosProject(newProject, "/home/mythri/src-regen/_dummyProgram.c");
				a.compute();
			} catch (IOException e) {
				e.printStackTrace();
			}
			CDTFrontEnd frontend = new CDTFrontEnd(newProject);
			try {
				frontend.compute();
			} catch (CoreException e) {
				e.printStackTrace();
			}  
			//new XtendCGenerator(gecosProject, "./src-regen/").compute(); 
			ScopExtractorPass scopExtractorPass = new ScopExtractorPass(newProject);
			Collection<GScopRoot> scops = scopExtractorPass.compute();
			System.out.println("ScopExtraction Done");
			GecosArrayDataflowAnalysis dataflowAnalysis = new GecosArrayDataflowAnalysis(newProject);
			dataflowAnalysis.compute();
			System.out.println("DFAnalysis Done");
			GScopScheduler schedule = new GScopScheduler(newProject, "PLUTO_ISL");
			schedule.compute();
			ActorSplittingTransform actorSplitTrans = new ActorSplittingTransform(newProject, numActors, tileSize);
			actorSplitTrans.compute();
			int tileLevel = actorSplitTrans.getTileLevel();
			HybridFSMCloogCodeGenerator codeGen = new HybridFSMCloogCodeGenerator(newProject, tileLevel);
			codeGen.compute();
			printActorNetwork(actorName, numActors, action, newProject,	actorSplitTrans);
			transformToActorModel();
			OrccLogger.traceln("Transforming Procedure " + body.getName());
		} 
	}

	private static void transformToActorModel() {
		/* Convert Procedures into Actor and print them */
		
		/* Create a network */
		
	}

	private static void printActorNetwork(String actorName, int numActors,
			Action action, GecosProject newProject,
			ActorSplittingTransform actorSplitTrans) {
		Map<Integer, List<PortInfoGeCosToCAL>> flowOutMap = actorSplitTrans.getFlowOutMap();
		Map<Integer, List<PortInfoGeCosToCAL>> flowInMap = actorSplitTrans.getFlowInMap();
		List<GScopFSM> scatterFSMs = new ArrayList<GScopFSM>();
		GScopFSM gatherFSM = null;
		List<GScopFSM> actorFSMs = new ArrayList<GScopFSM>();
		List<GScopFSM> gatherFSMs = new ArrayList<GScopFSM>();
		for ( gecos.core.Procedure proc : newProject.getAllProcedures() ) {
			if ( proc.getSymbol().getName().compareTo("scatter") == 0) {
				List<GScopRoot> roots = EMFUtils.eAllContentsInstancesOf(proc, GScopPackage.eINSTANCE.getGScopRoot());
				int actorId = 0;
				for ( GScopRoot root : roots ) {
					/* append _actorId for all statements */
				/*	for ( Symbol sym : root.getScope().getSymbols() ) {
						sym.setName(sym.getName()+"_"+actorId);
					} */
					GScopFSM fsm = (GScopFSM)root.getStatements().get(0);
					for ( LoopIterator it : fsm.getIterators()) {
						String name = it.getName();
						it.setName(name+"_"+actorId);
					}
					scatterFSMs.add(fsm);
					actorId++;
				}
			} else if ( proc.getSymbol().getName().compareTo("gather") == 0) {
				List<GScopRoot> roots = EMFUtils.eAllContentsInstancesOf(proc, GScopPackage.eINSTANCE.getGScopRoot());
				gatherFSM = (GScopFSM)roots.get(0).getStatements().get(0);
			} else {
				List<GScopRoot> roots = EMFUtils.eAllContentsInstancesOf(proc, GScopPackage.eINSTANCE.getGScopRoot());
				actorFSMs.add((GScopFSM)roots.get(0).getStatements().get(0));
				gatherFSMs.add((GScopFSM)roots.get(1).getStatements().get(0));
			}
		}
		//new XtendCGenerator(gecosProject, "./src-regen/").compute();
		ActorTemplate actorGen = new ActorTemplate();
		Map<Integer, List<PortInfoGeCosToCAL>> initMapList = actorSplitTrans.getInitMap();
		Map<Integer, List<PortInfoGeCosToCAL>> writeOutMapList = actorSplitTrans.getWriteOutMap();
		String pathName = "/home/mythri/orcc-apps-code/Research/src/fr/irisa/compa/gecos/";
		String fileName = pathName+actorName;
		String pkgName = "fr.irisa.compa.gecos";
		List<ActorPortInfo> ipportList = new ArrayList<ActorPortInfo>();
		List<ActorPortInfo> opportList = new ArrayList<ActorPortInfo>();
		List<ActorPortInfo> writeoutList = new ArrayList<ActorPortInfo>();
		/* Scatter Actor */
		for ( Port p : action.getInputPattern().getPorts() ) {
			int size = action.getInputPattern().getNumTokensMap().get(p);
			ActorPortInfo info = new ActorPortInfo(p.getName(), size, 
					p.getName()+"_initBuffer", p.getType().toString() );
			ipportList.add(info);
		}
		List<List<ActorPortInfo>> opportListScatter = new ArrayList<List<ActorPortInfo>>();
		for ( Entry<Integer, List<PortInfoGeCosToCAL>> actorEntry : initMapList.entrySet() ) {
			int actorId = actorEntry.getKey();
			List<ActorPortInfo> opportforactor = new ArrayList<ActorPortInfo>();
			for ( PortInfoGeCosToCAL e : actorEntry.getValue() ) {
				String arrayType = getTypeOfArray(e.arrayName, action);
				ActorPortInfo info = new ActorPortInfo("Port_"+e.bufferName, e.bufferSize, 
					e.bufferName, arrayType);
				opportforactor.add(info);
			}
			opportListScatter.add(opportforactor);
			actorId++;
		}
		actorGen.printScatterActor(fileName+"_scatter.cal", scatterFSMs, pkgName, actorName+"_scatter", ipportList, opportListScatter);
		ipportList.clear();
		opportList.clear();
		/* Gather Actor */
		for ( Entry<Integer, List<PortInfoGeCosToCAL>> actorEntry : writeOutMapList.entrySet() ) {
			int actorId = actorEntry.getKey();
			for ( PortInfoGeCosToCAL e : actorEntry.getValue() ) {
				String arrayType = getTypeOfArray(e.arrayName, action);
				ActorPortInfo info = new ActorPortInfo("Port_"+e.bufferName, e.bufferSize, 
					e.bufferName, arrayType);
				ipportList.add(info);
			}
			actorId++;
		}
		for ( Port p : action.getOutputPattern().getPorts() ) {
			int size = action.getOutputPattern().getNumTokensMap().get(p);
			ActorPortInfo info = new ActorPortInfo(p.getName(), size, 
					"tmpBuffer_"+p.getName(), p.getType().toString());
			opportList.add(info);
		}
		actorGen.printGatherActor(fileName+"_gather.cal", gatherFSM, null, pkgName, actorName+"_gather", ipportList, opportList, writeoutList);
		/* Compute Actors */
		for (int i = 0; i < numActors; i++ ) {
			GScopFSM fsm = actorFSMs.get(i);
			GScopFSM writeoutFSM = gatherFSMs.get(i);
			ipportList.clear();
			opportList.clear();
			writeoutList.clear();
			/* Generate the list of ports with port name, size, the buffer 
			 * corresponding to the port */
			List<PortInfoGeCosToCAL> initMap = initMapList.get(i);
			if ( initMap != null ) {
				for ( PortInfoGeCosToCAL e : initMap ) {
					String varName = e.arrayName;
					String type = getTypeOfArray(varName, action);
					ActorPortInfo info = new ActorPortInfo("Port_"+e.bufferName, e.bufferSize, 
							e.bufferName, type );
					ipportList.add(info);
				}
			}
			List<PortInfoGeCosToCAL> list = flowInMap.get(i);
			if ( list != null ) {
				for ( PortInfoGeCosToCAL port : list ) {
					String type = getTypeOfArray(port.arrayName, action);
					ActorPortInfo info = new ActorPortInfo("Port_"+port.bufferName, port.bufferSize, 
							port.bufferName, type );
					ipportList.add(info);
				
				}
			}
			List<PortInfoGeCosToCAL> writeOutMap = writeOutMapList.get(i);
			if ( writeOutMap != null ) {
				for ( PortInfoGeCosToCAL e : writeOutMap ) {
					String type = getTypeOfArray(e.arrayName, action); 
					ActorPortInfo info = new ActorPortInfo("Port_"+e.bufferName, e.bufferSize, 
							e.bufferName, type );
					writeoutList.add(info);
				}
			}
			list = flowOutMap.get(i);
			if ( list != null ) {
				for ( PortInfoGeCosToCAL port : list ) {
					String type = getTypeOfArray(port.arrayName, action);
					ActorPortInfo info = new ActorPortInfo("Port_"+port.bufferName, port.bufferSize, 
							port.bufferName, type );
					opportList.add(info);
				}
			}
			actorGen.printActor(fileName+"_part_"+i+".cal", fsm, writeoutFSM, pkgName, actorName+"_part_"+i, ipportList, opportList, writeoutList);
		}
		NetworkTemplate networkGen = new NetworkTemplate();
		List<String> instanceList = new ArrayList<String>();
		instanceList.add(actorName+"_scatter");
		instanceList.add(actorName+"_gather");
		for ( int i = 0; i < numActors; i++ ) {
			instanceList.add(actorName+"_part_"+i);
		}
		List<EdgeInfo> edgeInfo = new ArrayList<EdgeInfo>();
		List<NetworkPortInfo> portInfo = new ArrayList<NetworkPortInfo>();
		/* Add ports for the initial data from scatter to actors*/
		for ( Entry<Integer, List<PortInfoGeCosToCAL>> initMap : initMapList.entrySet() ) {
			int actorId = initMap.getKey();
			for ( PortInfoGeCosToCAL entry : initMap.getValue() ) {
				EdgeInfo e = new EdgeInfo(actorName+"_scatter", 
						"Port_"+entry.bufferName, actorName+"_part_"+actorId, "Port_"+entry.bufferName);
				edgeInfo.add(e);
			}
			actorId++;
		}
		/* Add input ports to the network. These are same as input ports of original actor */
		for ( Port p : action.getInputPattern().getPorts() ) {
			String baseType = getBaseType(p.getType());
			NetworkPortInfo info = new NetworkPortInfo(p.getName(), p.getType().getSizeInBits(), 
					actorName+"_scatter", baseType, NetworkPortType.Input);
			portInfo.add(info);
		}
		for ( Port p : action.getOutputPattern().getPorts() ) {
			String baseType = getBaseType(p.getType());
			NetworkPortInfo info = new NetworkPortInfo(p.getName(), p.getType().getSizeInBits(), 
					actorName+"_gather", baseType, NetworkPortType.Output);
			portInfo.add(info);
		}
		/* Add ports for the final data from actors to gather */
		for ( Entry<Integer, List<PortInfoGeCosToCAL>> writeOutMap : writeOutMapList.entrySet() ) {
			int actorId = writeOutMap.getKey();
			for ( PortInfoGeCosToCAL entry : writeOutMap.getValue() ) {
				EdgeInfo e = new EdgeInfo(actorName+"_part_"+actorId, "Port_"+entry.bufferName, actorName+"_gather", "Port_"+entry.bufferName);
				edgeInfo.add(e);
			}
			actorId++;
		}
		/* Add ports for the data between actors */
		for ( Entry<Integer, List<PortInfoGeCosToCAL>> entry : flowOutMap.entrySet()) {
			for ( PortInfoGeCosToCAL p : entry.getValue() ) {
				String rcvPort = "Port_"+getPortName(p.arrayName, p.actorId, entry.getKey(), flowInMap);
				EdgeInfo e = new EdgeInfo(actorName+"_part_"+entry.getKey(), "Port_"+p.bufferName, 
								actorName+"_part_"+p.actorId, rcvPort);
				edgeInfo.add(e);
			}
		}
		networkGen.printNetwork(actorName+"_network", instanceList, "fr.irisa.compa.gecos", edgeInfo, portInfo, pathName);
	}

	private static String getBaseType(Type type) {
		if ( type.isInt() ) {
			return "int";
		} else if ( type.isFloat() ) {
			return "float";
		} else if ( type.isBool()) {
			return "boolean";
		} else if ( type.isUint() ) {
			return "uint";
		} 
		return type.toString();
	}

	private static String getPortName(String arrayName, int actorId, int srcActorId, Map<Integer, List<PortInfoGeCosToCAL>> flowInMap) {
		List<PortInfoGeCosToCAL> list = flowInMap.get(actorId);
		if ( list == null )
			return null;
		for ( PortInfoGeCosToCAL p : list ) {
			if ( (p.actorId == srcActorId) && (p.arrayName.compareTo(arrayName) == 0) ) {
				return p.bufferName;
			}
		}
		return null;
	}

	private static String getTypeOfArray(String arrayName, Action action) {
		String type = null;
		for ( Port p : action.getInputPattern().getPorts() ) {
			String name = p.getName();
			if ( name.compareTo(arrayName) == 0 ) {
				type = p.getType().toString();
			}			
		}
		for ( Port p : action.getOutputPattern().getPorts() ) {
			String name = p.getName();
			if ( name.compareTo(arrayName) == 0 ) {
				type = p.getType().toString();
			}
		}
		return type;
	}
}
