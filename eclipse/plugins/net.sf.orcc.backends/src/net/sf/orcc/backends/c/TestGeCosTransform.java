package net.sf.orcc.backends.c;

//import fr.irisa.cairn.ecore.tools.query.EMFUtils;
import fr.irisa.cairn.gecos.gscop.GScopFSM;
import fr.irisa.cairn.gecos.gscop.GScopRoot;
import fr.irisa.cairn.gecos.model.c.generator.XtendCGenerator;
import fr.irisa.cairn.gecos.model.cdtfrontend.CDTFrontEnd;
import fr.irisa.cairn.gecos.model.factory.GecosUserBlockFactory;
import fr.irisa.cairn.gecos.model.factory.GecosUserCoreFactory;
import fr.irisa.cairn.gecos.model.factory.GecosUserTypeFactory;
import fr.irisa.cairn.gecos.model.modules.AddSourceToGecosProject;
import fr.irisa.cairn.gecos.model.modules.CreateProject;
import fr.irisa.cairn.gecos.model.scop.almaflow.GecosToCALInfo;
import fr.irisa.cairn.gecos.model.scop.almaflow.PortInfoGecosToCAL;
import fr.irisa.cairn.gecos.model.scop.almaflow.UR1OptimizationFlowModule;
import fr.irisa.cairn.tools.ecore.query.EMFUtils;
import gecos.blocks.BasicBlock;
import gecos.blocks.CompositeBlock;
import gecos.blocks.ForBlock;
import gecos.blocks.IfBlock;
import gecos.blocks.WhileBlock;
import gecos.core.ParameterSymbol;
import gecos.core.ProcedureSet;
import gecos.core.Symbol;
import gecosproject.GecosProject;
import gecosproject.GecosSourceFile;
import gecosproject.GecosprojectFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.core.resources.IFile;

import net.sf.orcc.backends.c.NetworkPortInfo.NetworkPortType;
import net.sf.orcc.backends.ir.BlockFor;
import net.sf.orcc.df.Action;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.Port;
import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.BlockIf;
import net.sf.orcc.ir.BlockWhile;
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
import net.sf.orcc.util.OrccUtil;


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
		BasicBlock gecosBB = GecosUserBlockFactory.BBlock();
		for ( Instruction inst : bb.getInstructions() ) {
			gecos.instrs.Instruction gecosInst = TransformToGecosInstruction.convertToGecosInst(inst, gecosProc);
			gecosBB.addInstruction(gecosInst);
		}
		return gecosBB;
	}
	
	static gecos.blocks.WhileBlock processWhileBlock(BlockWhile wbb ) {
		WhileBlock gecosWhile = GecosUserBlockFactory.While(GecosUserBlockFactory.BBlock());
		return gecosWhile;
	}

	static IfBlock processIfBlock ( BlockIf ifbb) {
		gecos.instrs.Instruction condInst = TransformToGecosInstruction.convertToGecosInst(ifbb.getCondition(), gecosProc);
		CompositeBlock thenBlock = GecosUserBlockFactory.CompositeBlock();
		for ( Block b : ifbb.getThenBlocks() ) {
			gecos.blocks.Block gecosBlock = transformToGecosBlock(b);
			thenBlock.addChildren(gecosBlock);
		}
		if ( ifbb.getElseBlocks().size() != 0 ) {
			CompositeBlock elseBlock = GecosUserBlockFactory.CompositeBlock();
			for ( Block b : ifbb.getElseBlocks() ) {
				gecos.blocks.Block gecosBlock = transformToGecosBlock(b);
				elseBlock.addChildren(gecosBlock);
			}
			return GecosUserBlockFactory.IfThenElse(condInst, thenBlock, elseBlock);
		} else {
			return GecosUserBlockFactory.IfThen(condInst, thenBlock);
		}
	}

	static ForBlock processForBlock ( BlockFor fbb) {
		BasicBlock init = GecosUserBlockFactory.BBlock();
		for ( Instruction inst : fbb.getInit() ) {
			gecos.instrs.Instruction gecosInst = TransformToGecosInstruction.convertToGecosInst(inst, gecosProc);
			init.addInstruction(gecosInst);
		}
		BasicBlock test = GecosUserBlockFactory.BBlock();
		gecos.instrs.Instruction condInst = TransformToGecosInstruction.convertToGecosInst(fbb.getCondition(), gecosProc);
		test.addInstruction(condInst);
		BasicBlock step = GecosUserBlockFactory.BBlock();
		for ( Instruction inst : fbb.getStep() ) {
			gecos.instrs.Instruction gecosInst = TransformToGecosInstruction.convertToGecosInst(inst, gecosProc);
			step.addInstruction(gecosInst);
		}
		CompositeBlock bodyBlock = GecosUserBlockFactory.CompositeBlock();
		for ( Block b : fbb.getBlocks() ) {
			gecos.blocks.Block gecosBlock = transformToGecosBlock(b);
			bodyBlock.addChildren(gecosBlock);
		}
		return GecosUserBlockFactory.For(init, test, step, bodyBlock);
	}
	

	/*%strategy visitCFG() extends Identity(){ 
		visit Block {
			BlockBasic -> { } 
		} 
	} */

	public static gecos.core.ParameterSymbol convertToGecosParameter(Param p, int value) {
		String name = p.getVariable().getName();
		gecos.types.Type paramType = TransformToGecosInstruction.convertToGeCosType(p.getVariable().getType());
		gecos.core.ParameterSymbol gecosParam = GecosUserCoreFactory.paramSymbol(name, paramType);
		return gecosParam;
	}
	
	public static gecos.core.Procedure createProcedure (Procedure body, Action action, 
			ProcedureSet procedureSet, CompositeBlock gecosBody, List<Attribute> arrayAttributes ) {
		/*Get Parameters */
		List<ParameterSymbol> paramList = new ArrayList<ParameterSymbol>();
		int numParam = 0;
		for ( Port p : action.getInputPattern().getPorts() ) {
			/* to differentiate between port name and the buffer */
			String name = action.getInputPattern().getVariable(p).getName();
			gecos.types.Type paramType = TransformToGecosInstruction.convertToGeCosType(p.getType());
			int size = action.getInputPattern().getNumTokensMap().get(p);
			paramType = getPortType(name, paramType, size, arrayAttributes);
			gecos.core.ParameterSymbol paramSym = GecosUserCoreFactory.paramSymbol(name, paramType);
			paramList.add(paramSym);
			procedureSet.getScope().getTypes().add(paramSym.getType());
			numParam++;
		}
		for ( Port p : action.getOutputPattern().getPorts() ) {
			String name = action.getOutputPattern().getVariable(p).getName();
			gecos.types.Type paramType = TransformToGecosInstruction.convertToGeCosType(p.getType());
			int size = action.getOutputPattern().getNumTokensMap().get(p);
			paramType = getPortType(name, paramType, size, arrayAttributes);
			gecos.core.ParameterSymbol paramSym = GecosUserCoreFactory.paramSymbol(name, paramType);
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
			Symbol sym = GecosUserCoreFactory.symbol(v.getName(), 
					TransformToGecosInstruction.convertToGeCosType(v.getType()), gecosBody.getScope());
			gecosBody.getScope().getSymbols().add(sym);
			gecosBody.getScope().getTypes().add(sym.getType());
		}
		gecosProc = GecosUserCoreFactory.proc(procedureSet, body.getName(), gecosRetType, gecosBody, paramList);
		/*int numParam = 0;
		for ( Param p : body.getParameters() ) {
			gecos.core.ParameterSymbol paramSym = convertToGecosParameter(p, numParam);
			paramSym.setScope(gecosProc.getScope());
			gecosProc.getScope().getSymbols().add(paramSym);
			numParam++;
		} */
		
		return gecosProc;
	}


	private static gecos.types.Type getPortType(String arrayName, gecos.types.Type paramType, int size,
			List<Attribute> arrayAttributes) {
	
		for ( Attribute att : arrayAttributes ) {
			if ( att.getName().equals(arrayName) ) {
				String[] str = att.getStringValue().split(",");
				int numDims = Integer.parseInt(str[0]);
				gecos.types.Type type = paramType.copy();
				for (int i = numDims; i > 0; i--  ) {
					type = GecosUserTypeFactory.ARRAY(type, Integer.parseInt(str[i]));
				}
				return type;
			}
		}
		return GecosUserTypeFactory.ARRAY(paramType, size);
	}

	public static void exec(Actor actor) {

		OrccLogger.traceln(actor.getName());
		System.out.println("PkgName " + actor.getPackage());	
		if ( actor.hasAttribute("gecos")) {
			
			OrccLogger.traceln("Actor " + actor.getName() + " will be analyzed by gecos");
			if ( actor.getActions().size() > 1 ) {
				OrccLogger.traceln("Actor has more than one action");
			}
			Attribute att = actor.getAttribute("gecos");
			List<Attribute> val = att.getAttributes();
			int numActors = 3;
			int tileSize = 4;
			if ( val.get(0).getName().compareTo("tile") == 0 ) {
				String str = val.get(0).getStringValue();
				tileSize = Integer.parseInt(str);
			}
			if ( val.get(1).getName().compareTo("actors") == 0 ) {
				String str = val.get(1).getStringValue();
				numActors = Integer.parseInt(str);
			}
			/*if ( val.get(1).isExprList() ) {
				ExprList list = (ExprList)val.get(1);
				if ( list.get(1).isExprString() ) {
					ExprString str = (ExprString)list.get(1);
					numActors = Integer.parseInt(str.getValue());
				}
			}*/
			String actorName = actor.getName();
			
			Action action = actor.getActions().get(0);
			Attribute arrAtt = action.getAttribute("gecos");
			List<Attribute> arrayAtts = new ArrayList<Attribute>();
			if ( arrAtt != null )
				arrayAtts = arrAtt.getAttributes();
			Procedure body = action.getBody();
			ProcedureSet procedureSet = GecosUserCoreFactory.procedureSet();
			GecosUserTypeFactory.setScope(procedureSet.getScope());
			/* Add state variables of actor int procedureSet scope */
			for ( Var v : actor.getStateVars() ) {
				Symbol sym = GecosUserCoreFactory.symbol(v.getName(), 
						TransformToGecosInstruction.convertToGeCosType(v.getType()), procedureSet.getScope());
			//	gecos.instrs.Instruction value = TransformToGecosInstruction.convertToGecosInst(v.getInitialValue(), proc);
			//	sym.setValue(value);
				procedureSet.getScope().getSymbols().add(sym);
			}
			gecos.core.Scope s = GecosUserCoreFactory.scope();
			CompositeBlock gecosBody = GecosUserBlockFactory.CompositeBlock(s);
			gecosProc = createProcedure(body, action, procedureSet, gecosBody, arrayAtts);
			
			for ( Block b : body.getBlocks() ) {
				gecos.blocks.Block gecosBlock = transformToGecosBlock(b);
				gecosBody.addChildren(gecosBlock);
			}
			
			procedureSet.addProcedure(gecosProc);
			GecosProject gecosProject = GecosprojectFactory.eINSTANCE.createGecosProject();
			GecosSourceFile gecosSourceFile = GecosprojectFactory.eINSTANCE.createGecosSourceFile();
			gecosProject.getSources().add(gecosSourceFile);
			gecosSourceFile.setName("dummyProgram.c");
			gecosSourceFile.setModel(procedureSet);
			String pathName = actor.getFile().getRawLocation().toOSString();
			pathName = pathName.substring(0, pathName.lastIndexOf("/")+1);
			new XtendCGenerator(gecosProject, pathName).compute();
			/*String[] cmd = {"bash", "-c", "sed -i \"s/\\(\\* *[a-z,A-Z,_,0-9]\\+ *\\)\\++/][/g\" /home/mythri/src-regen/_dummyProgram.c"};
			System.out.println(cmd);
			try {
				Process p = Runtime.getRuntime().exec(cmd);
				p.waitFor();
				int retVal = p.exitValue();
				System.out.println("RetrunVale " + retVal);
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			CreateProject createProj = new CreateProject("compaGecosProj");
			GecosProject newProject = createProj.compute();
			AddSourceToGecosProject a = new AddSourceToGecosProject(newProject, pathName+"/dummyProgram.c");
			a.compute();
			CDTFrontEnd frontend = new CDTFrontEnd(newProject);
			frontend.compute();
			
//			GecosProject newProject = gecosProject;
			UR1OptimizationFlowModule actorSplitTrans = new UR1OptimizationFlowModule(newProject,0,numActors,tileSize);
			UR1OptimizationFlowModule.setFSMBasedCodeGeneration(true);
			actorSplitTrans.compute();
			System.out.println("Project: " + actor.getPackage());
			
			printActorNetwork(actorName, numActors, actor, action, newProject,	actorSplitTrans);
			transformToActorModel();
			
//			File f = new File(pathName+"/dummyProgram.c");
//			f.delete();
//			
			OrccLogger.traceln("Transforming Procedure " + body.getName());
		} 
	}

	private static void transformToActorModel() {
		/* Convert Procedures into Actor and print them */
		
		/* Create a network */
		
	}

	private static void printActorNetwork(String actorName, int numActors,
			Actor actor, Action action, GecosProject newProject,
			UR1OptimizationFlowModule actorSplitTrans) {
		
		Map<Integer, List<GecosToCALInfo>> actorsMap = actorSplitTrans.getActors(); 
		//new XtendCGenerator(gecosProject, "./src-regen/").compute();
		ActorTemplate actorGen = new ActorTemplate();
		ActorTemplate.arraySizeMap = new HashMap<Symbol, List<gecos.instrs.Instruction>>();
		ActorTemplate.arraySizeMap.putAll(actorSplitTrans.getArraySizeMap());
		
		//FIXME: get correct way of getting package info
		String pkgName = actor.getFileName();
		pkgName = pkgName.substring(pkgName.indexOf("/")+1);
		pkgName = pkgName.substring(pkgName.indexOf("/")+1);
		pkgName = pkgName.substring(pkgName.indexOf("/")+1);
		pkgName = pkgName.substring(0, pkgName.lastIndexOf("/"));
		pkgName = pkgName.replace("/",".");
		
		String pathName = actor.getFile().getRawLocation().toOSString();
		pathName = pathName.substring(0, pathName.lastIndexOf("/")+1);
		String fileName = actorName;
		
		
		/* Print Actors */
		for ( int i = 0; i < numActors+1; i++ ) {
			List<ActorPortInfo> ipportList = new ArrayList<ActorPortInfo>();
			List<ActorPortInfo> opportList = new ArrayList<ActorPortInfo>();
			System.out.println("Actor@ " + i + "  " + actorsMap.size());
			List<GecosToCALInfo> list = actorsMap.get(i);
			GecosToCALInfo actorEntry = list.get(0);
			if ( i == numActors )  { //That is scatter
				ipportList = getPortListFromCALPorts(action, action.getInputPattern().getPorts(), true);
				List<ActorPortInfo> l = getPortList(actorEntry.ipPortList, action);
				ipportList.addAll(l);
			} else {
				ipportList = getPortList(actorEntry.ipPortList, action);
			}
			if ( i == numActors  ) { //This is Scatter
				opportList = getPortListFromCALPorts(action, action.getOutputPattern().getPorts(), false);
				List<ActorPortInfo> l = getPortList(actorEntry.opPortList, action);
				opportList.addAll(l);
			} else {
				opportList = getPortList(actorEntry.opPortList, action);
			}
			String suffix = "_part_"+i+".cal";
			String actorSuffix = "_part_" + i;
			if ( i == numActors ) {
				suffix = "_scatter.cal";
				actorSuffix = "_scatter";
			} else if ( i == numActors + 1 ) {
				suffix = "_gather.cal";
				actorSuffix = "_gather";
			}
			String name = OrccUtil.createFile(pathName, fileName+suffix);
			File f = new File(name);
			CharSequence c = actorGen.printActorSignature(pkgName, actorName+actorSuffix, ipportList, opportList);
			String content = c.toString();
			List<Symbol> symList = getSymbolsForActor(actorEntry);
			c = actorGen.printActorSymbols(symList);
			//content.concat(c.toString());
			content = content + c.toString();
			//list.remove(0);
			int actionId = 0;
			int first = 0;
			int doneStateId = list.size() + 1;
			for ( GecosToCALInfo info : list ) {
				if ( first == 0 ) {
					first = 1;
					continue;
				}
				opportList.clear();
				ipportList.clear();
				opportList = getPortList(info.opPortList, action);
				ipportList = getPortList(info.ipPortList, action);

				List<GScopFSM> fsms = EMFUtils.eAllContentsInstancesOf(info.proc, GScopFSM.class);
				List<GScopRoot> roots = EMFUtils.eAllContentsInstancesOf(info.proc, GScopRoot.class);
				boolean scatterInit = false;
				if ( (first == 1) && (i == numActors) ) {
					ipportList = getPortListFromCALPorts(action, action.getInputPattern().getPorts(), true);
					first = 2;
					scatterInit = true;
				}
				if ( fsms.size() != 0 ) {
					c = actorGen.printAction("action_"+actionId,info.proc, fsms.get(0), ipportList, 
										opportList, info.guard, info.stateChange, scatterInit, doneStateId);
				} else {
					c = actorGen.printAction("action_"+actionId,info.proc, roots.get(0), ipportList, 
							opportList, info.guard, info.stateChange, scatterInit);
				}
				content = content + c.toString();
				actionId += 1;
			}
			opportList = getPortListFromCALPorts(action, action.getOutputPattern().getPorts(), false);
			if ( i == numActors ) {
				c = actorGen.printScatterOut(opportList, i, doneStateId);
				content = content + c.toString();
			}
			c = actorGen.printActorEnd();
			content = content + c.toString();
			//content.concat(c.toString());
			OrccUtil.printFile(content, f);
			
		}
		
		
		NetworkTemplate networkGen = new NetworkTemplate();
		List<String> instanceList = new ArrayList<String>();
		instanceList.add(actorName+"_scatter");
		//instanceList.add(actorName+"_gather");
		for ( int i = 0; i < numActors; i++ ) {
			instanceList.add(actorName+"_part_"+i);
		}
		/* Add input ports to the network. These are same as input ports of original actor */
		List<NetworkPortInfo> portInfo = getNetworkPorts(actorName, action);
		List<EdgeInfo> edgeInfo = new ArrayList<EdgeInfo>();
		/* Add ports for the initial data from scatter to actors*/
	//	GecosToCALInfo scatter_info = actorsMap.get(numActors).get(0);
	//	addEdgesFromScatter(actorName, edgeInfo, scatter_info.opPortList);
		//GecosToCALInfo gather_info = actorsMap.get(numActors+1).get(0);
		//addEdgesToGather(actorName, edgeInfo, gather_info.ipPortList);
		/* Add ports for the data between actors */
		for ( int i = 0; i < numActors + 1; i++ ) {
			GecosToCALInfo actor_info = actorsMap.get(i).get(0);
			for ( PortInfoGecosToCAL p : actor_info.opPortList ) {
				int actorId = p.actorId;
				System.out.println("Send & RcvActorId " + i + "  " + actorId);
				List<GecosToCALInfo> rcvActorInfoList = actorsMap.get(actorId);
				String rcvPort = null;
				for ( GecosToCALInfo rcvActorInfo :   rcvActorInfoList ) {
					rcvPort = "Port_"+getPortName(p.arrayName,  rcvActorInfo.ipPortList, i);
					if ( rcvPort != null )
						break;
				}
				String rcvActorName = actorName+"_part_"+p.actorId;
				if ( p.actorId == numActors  ) {
					rcvActorName = actorName+"_scatter";
				} 
				String sendActorName = actorName+"_part_"+i;
				if ( i == numActors ) {
					sendActorName = actorName+"_scatter";
				}
				EdgeInfo e = new EdgeInfo(sendActorName, "Port_"+p.bufferName, 
						rcvActorName, rcvPort);
				edgeInfo.add(e);
			}
		}
		networkGen.printNetwork(actorName+"_network", instanceList, pkgName, edgeInfo, portInfo, pathName);
	}

	private static List<Symbol> getSymbolsForActor(GecosToCALInfo actorEntry) {
//		List<Symbol> symList = new ArrayList<Symbol>();
//		return symList;
		CompositeBlock body = (CompositeBlock)actorEntry.proc.getBody();
		return body.getChildren().get(1).getScope().getSymbols();
	}

	private static String getPortName(String arrayName, List<PortInfoGecosToCAL> ipPortList, int srcActorId) {
		for ( PortInfoGecosToCAL p : ipPortList ) {
			if ( (p.actorId == srcActorId) && (p.arrayName.compareTo(arrayName) == 0) ) {
				return p.bufferName;
			}
		}
		return null;
	}

	private static void addEdgesToGather(String actorName,
			List<EdgeInfo> edgeInfo, List<PortInfoGecosToCAL> ports) {
		for ( PortInfoGecosToCAL p : ports ) {
			int actorId = p.actorId;
			EdgeInfo e = new EdgeInfo(actorName+"_part_"+actorId, "Port_"+p.bufferName, actorName+"_gather", "Port_"+p.bufferName);
			edgeInfo.add(e);
		}
	}

private static void addEdgesFromScatter(String actorName,
		List<EdgeInfo> edgeInfo, List<PortInfoGecosToCAL> ports) {
	for ( PortInfoGecosToCAL p : ports ) {
		int actorId = p.actorId;
		EdgeInfo e = new EdgeInfo(actorName+"_scatter", 
				"Port_"+p.bufferName, actorName+"_part_"+actorId, "Port_"+p.bufferName);
		edgeInfo.add(e);
	}
}

private static List<NetworkPortInfo> getNetworkPorts(String actorName, Action action ) {
	List<NetworkPortInfo> portInfo = new ArrayList<NetworkPortInfo>();
	for ( Port p : action.getInputPattern().getPorts() ) {
		String baseType = getBaseType(p.getType());
		NetworkPortInfo info = new NetworkPortInfo(p.getName(), p.getType().getSizeInBits(), 
				actorName+"_scatter", baseType, NetworkPortType.Input);
		portInfo.add(info);
	}
	for ( Port p : action.getOutputPattern().getPorts() ) {
		String baseType = getBaseType(p.getType());
		NetworkPortInfo info = new NetworkPortInfo(p.getName(), p.getType().getSizeInBits(), 
				actorName+"_scatter", baseType, NetworkPortType.Output);
		portInfo.add(info);
	}
	return portInfo;
}

	private static  List<ActorPortInfo> getPortListFromCALPorts(Action action, List<Port> inputPorts, boolean isInput) {
		List<ActorPortInfo> portList = new ArrayList<ActorPortInfo>();
		for ( Port p : inputPorts ) {
			int size = 0;
			if ( isInput )
				size = action.getInputPattern().getNumTokensMap().get(p);
			else 
				size = action.getOutputPattern().getNumTokensMap().get(p);
			ActorPortInfo info = new ActorPortInfo(p.getName(), size, 
					p.getName()+"_initBuffer", p.getType().toString() );
			portList.add(info);
		}
		return portList;
	}
	
	private static List<ActorPortInfo> getPortList(List<PortInfoGecosToCAL> list, Action action) {
		List<ActorPortInfo> opportList = new ArrayList<ActorPortInfo>();
		for ( PortInfoGecosToCAL e : list ) {
			String arrayName = e.arrayName;
			if ( e.arrayName.lastIndexOf("_") != -1 ) {
				arrayName = e.arrayName.substring(0,e.arrayName.lastIndexOf("_"));
				if ( arrayName.lastIndexOf("_") != -1 ) {
					arrayName = e.arrayName.substring(0,arrayName.lastIndexOf("_"));
				}
			}
			String arrayType = getTypeOfArray(arrayName, action);
			ActorPortInfo p = new ActorPortInfo("Port_"+e.bufferName, (int)e.bufferSize, e.bufferName, arrayType);
			opportList.add(p);
		}
		return opportList;
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

	private static String getTypeOfArray(String arrayName, Action action) {
		String type = null;
		for ( Port p : action.getInputPattern().getPorts() ) {
			String name = p.getName();
			if ( name.compareTo(arrayName) == 0 ) {
				type = p.getType().toString();
				return type;
			}			
		}
		for ( Port p : action.getOutputPattern().getPorts() ) {
			String name = p.getName();
			if ( name.compareTo(arrayName) == 0 ) {
				type = p.getType().toString();
				return type;
			}
		}
		for ( Var v : action.getBody().getLocals() ) {
			if ( v.getName().equals(arrayName) ) {
				return v.getType().toString();
			}
		}
		return type;
	}
}
