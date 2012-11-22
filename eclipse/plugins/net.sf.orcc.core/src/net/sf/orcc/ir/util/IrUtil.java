/*
 * Copyright (c) 2011, IETR/INSA of Rennes
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
package net.sf.orcc.ir.util;

import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.BlockBasic;
import net.sf.orcc.ir.BlockWhile;
import net.sf.orcc.ir.Def;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Use;
import net.sf.orcc.ir.impl.IrResourceFactoryImpl;
import net.sf.orcc.util.OrccUtil;
import net.sf.orcc.util.util.EcoreHelper;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;

/**
 * This class contains several methods to help the manipulation of EMF models.
 * 
 * @author Matthieu Wipliez
 * @author Herve Yviquel
 * 
 */
public class IrUtil {

	/**
	 * Add the given instruction before the given expression. If the expression
	 * is contained by an instruction then the instruction to add is put
	 * directly before, else the instruction is put to the previous block which
	 * is created if needed. Return <code>true</code> if the given instruction
	 * is added in the current block.
	 * 
	 * @param expression
	 *            an expression
	 * @param instruction
	 *            the instruction to add before the given expression
	 * @return <code>true</code> if the given instruction is added in the
	 *         current block
	 */
	public static boolean addInstBeforeExpr(Expression expression,
			Instruction instruction) {
		Instruction containingInst = EcoreHelper.getContainerOfType(expression,
				Instruction.class);
		Block containingNode = EcoreHelper.getContainerOfType(expression,
				Block.class);
		if (containingInst != null) {
			if (containingInst.isInstPhi() && isWhileJoinBlock(containingNode)) {
				BlockWhile nodeWhile = EcoreHelper.getContainerOfType(
						containingNode, BlockWhile.class);
				addToPreviousNodeBlock(nodeWhile, instruction);
				return false;
			} else {
				List<Instruction> instructions = EcoreHelper
						.getContainingList(containingInst);
				instructions.add(instructions.indexOf(containingInst),
						instruction);
				return true;
			}
		} else {
			// The given expression is contained in the condition of If/While
			if (containingNode.isBlockWhile()) {
				BlockBasic joinNode = ((BlockWhile) containingNode)
						.getJoinBlock();
				joinNode.add(instruction);
			} else {
				addToPreviousNodeBlock(containingNode, instruction);
			}
			return false;
		}
	}

	private static void addToPreviousNodeBlock(Block node,
			Instruction instruction) {
		List<Block> nodes = EcoreHelper.getContainingList(node);
		BlockBasic nodeBlock = IrFactory.eINSTANCE.createBlockBasic();
		nodeBlock.add(instruction);
		nodes.add(nodes.indexOf(node), nodeBlock);
	}

	/**
	 * Returns a deep copy of the given objects, and updates def/use chains.
	 * 
	 * @param copier
	 *            a copier
	 * @param eObjects
	 *            a list of objects
	 * @return a deep copy of the given objects with def/use chains correctly
	 *         updated
	 */
	public static <T extends EObject> Collection<T> copy(Collection<T> eObjects) {
		return copy(new Copier(), eObjects);
	}

	/**
	 * Returns a deep copy of the given objects, and updates def/use chains.
	 * 
	 * @param eObjects
	 *            a list of objects
	 * @return a deep copy of the given objects with def/use chains correctly
	 *         updated
	 */
	public static <T extends EObject> Collection<T> copy(Copier copier,
			Collection<T> eObjects) {
		Collection<T> result = copier.copyAll(eObjects);
		copier.copyReferences();

		TreeIterator<EObject> it = EcoreUtil.getAllContents(eObjects);
		while (it.hasNext()) {
			EObject object = it.next();

			if (object instanceof Def) {
				Def def = (Def) object;
				Def copyDef = (Def) copier.get(def);
				copyDef.setVariable(def.getVariable());
			} else if (object instanceof Use) {
				Use use = (Use) object;
				Use copyUse = (Use) copier.get(use);
				copyUse.setVariable(use.getVariable());
			}
		}

		return result;
	}

	/**
	 * Returns a deep copy of the given object, and updates uses.
	 * 
	 * @param copier
	 *            a copier
	 * @param expression
	 *            an expression
	 * @return a deep copy of the given object with uses correctly updated
	 */
	public static <T extends EObject> T copy(Copier copier, T eObject) {
		@SuppressWarnings("unchecked")
		T result = (T) copier.copy(eObject);
		copier.copyReferences();

		TreeIterator<EObject> it = EcoreUtil.getAllContents(eObject, true);
		while (it.hasNext()) {
			EObject object = it.next();

			if (object instanceof Def) {
				Def def = (Def) object;
				Def copyDef = (Def) copier.get(def);
				if (copyDef.getVariable() == null) {
					copyDef.setVariable(def.getVariable());
				}
			} else if (object instanceof Use) {
				Use use = (Use) object;
				Use copyUse = (Use) copier.get(use);
				if (copyUse.getVariable() == null) {
					copyUse.setVariable(use.getVariable());
				}
			}
		}

		return result;
	}

	/**
	 * Returns a deep copy of the given object, and updates uses.
	 * 
	 * @param expression
	 *            an expression
	 * @return a deep copy of the given object with uses correctly updated
	 */
	public static <T extends EObject> T copy(T eObject) {
		return copy(new Copier(), eObject);
	}

	/**
	 * Removes the def/use chains of the given object, and then removes the
	 * object itself from its container.
	 * 
	 * @param eObject
	 *            an EObject
	 */
	public static void delete(EObject eObject) {
		removeUses(eObject);
		removeDefs(eObject);
		EcoreUtil.remove(eObject);
	}

	/**
	 * Deletes the given objects, and updates the def/use chains.
	 * 
	 * @param objects
	 *            a list of objects
	 */
	public static void delete(List<? extends EObject> eObjects) {
		while (!eObjects.isEmpty()) {
			delete(eObjects.get(0));
		}
	}

	/**
	 * Returns the first block in the given list of nodes. A new block is
	 * created if there is no block in the given node list.
	 * 
	 * @param nodes
	 *            a list of nodes
	 * @return a block
	 */
	public static BlockBasic getFirst(List<Block> nodes) {
		BlockBasic block;
		if (nodes.isEmpty()) {
			block = IrFactory.eINSTANCE.createBlockBasic();
			nodes.add(block);
		} else {
			Block node = nodes.get(0);
			if (node.isBlockBasic()) {
				block = (BlockBasic) node;
			} else {
				block = IrFactory.eINSTANCE.createBlockBasic();
				nodes.add(0, block);
			}
		}

		return block;
	}

	/**
	 * Returns the last block in the given list of nodes. A new block is created
	 * if there is no block in the given node list.
	 * 
	 * @param blocks
	 *            a list of nodes
	 * @return a block
	 */
	public static BlockBasic getLast(List<Block> blocks) {
		BlockBasic block;
		if (blocks.isEmpty()) {
			block = IrFactory.eINSTANCE.createBlockBasic();
			blocks.add(block);
		} else {
			Block node = blocks.get(blocks.size() - 1);
			if (node.isBlockBasic()) {
				block = (BlockBasic) node;
			} else {
				block = IrFactory.eINSTANCE.createBlockBasic();
				blocks.add(block);
			}
		}

		return block;
	}

	private static boolean isWhileJoinBlock(Block block) {
		if (block.isBlockBasic()) {
			BlockWhile nodeWhile = EcoreHelper.getContainerOfType(block,
					BlockWhile.class);
			return (nodeWhile != null && nodeWhile.getJoinBlock() == block);
		}
		return false;
	}

	/**
	 * Removes the defs present in the given object.
	 * 
	 * @param eObject
	 *            an EObject
	 */
	public static void removeDefs(EObject eObject) {
		for (Def def : EcoreHelper.getObjects(eObject, Def.class)) {
			def.setVariable(null);
		}
	}

	/**
	 * Removes the uses present in the given object.
	 * 
	 * @param eObject
	 *            an EObject
	 */
	public static void removeUses(EObject eObject) {
		for (Use use : EcoreHelper.getObjects(eObject, Use.class)) {
			use.setVariable(null);
		}
	}

	/**
	 * Serializes the given entity to the given output folder.
	 * 
	 * @param outputFolder
	 *            an IFolder of the workspace
	 * @param entity
	 *            an entity
	 * @return <code>true</code> if the serialization succeeded
	 */
	public static boolean serializeActor(ResourceSet set, IFolder outputFolder,
			EObject entity) {
		try {
			OrccUtil.createFolder(outputFolder);
		} catch (CoreException e) {
			e.printStackTrace();
		}

		URI uri = URI.createPlatformResourceURI(
				outputFolder
						.getFullPath()
						.append(OrccUtil.getFile((String) EcoreHelper
								.getFeature(entity, "name")))
						.addFileExtension("ir").toString(), true);
		return serializeActor(set, uri, entity);
	}

	/**
	 * Serializes the given entity to the given output folder.
	 * 
	 * @param outputFolder
	 *            output folder
	 * @param entity
	 *            an entity
	 * @return <code>true</code> if the serialization succeeded
	 */
	public static boolean serializeActor(ResourceSet set, String outputFolder,
			EObject entity) {
		String pathName = outputFolder
				+ File.separator
				+ OrccUtil.getFile((String) EcoreHelper.getFeature(entity,
						"name")) + ".ir";
		URI uri = URI.createFileURI(pathName);
		return serializeActor(set, uri, entity);
	}

	/**
	 * Serializes the given entity to the given URI.
	 * 
	 * @param uri
	 *            URI
	 * @param entity
	 *            an entity
	 * @return <code>true</code> if the serialization succeeded
	 */
	private static boolean serializeActor(ResourceSet set, URI uri,
			EObject entity) {
		// check that the factory is registered
		// (only happens in command-line mode)
		// ...
		// duck you command line :)
		Map<String, Object> extToFactoryMap = Resource.Factory.Registry.INSTANCE
				.getExtensionToFactoryMap();
		Object instance = extToFactoryMap.get("ir");
		if (instance == null) {
			instance = new IrResourceFactoryImpl();
			extToFactoryMap.put("ir", instance);
		}

		return EcoreHelper.putEObject(set, uri, entity);
	}

}
