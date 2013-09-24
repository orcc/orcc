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

import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.sf.orcc.df.util.DfUtil;
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
		Block containingBlock = EcoreHelper.getContainerOfType(expression,
				Block.class);
		if (containingInst != null) {
			if (containingInst.isInstPhi() && isWhileJoinBlock(containingBlock)) {
				BlockWhile blockWhile = EcoreHelper.getContainerOfType(
						containingBlock, BlockWhile.class);
				addToPreviousBlockBasic(blockWhile, instruction);
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
			if (containingBlock.isBlockWhile()) {
				BlockBasic joinBlock = ((BlockWhile) containingBlock)
						.getJoinBlock();
				joinBlock.add(instruction);
			} else {
				addToPreviousBlockBasic(containingBlock, instruction);
			}
			return false;
		}
	}

	/**
	 * Add the given block before the given expression. If the expression is
	 * contained by an instruction in a basic block, this basic block is split
	 * to insert the block in the right place. Else the block is put after the
	 * previous block of the block containing the expression. Return
	 * <code>true</code> if the given instruction has split the current basic
	 * block.
	 * 
	 * @param expression
	 *            an expression
	 * @param block
	 *            the block to add before the given expression
	 * @return <code>true</code> if the given block is added in the current
	 *         block
	 */
	public static boolean addBlockBeforeExpr(Expression expression, Block block) {
		Instruction containingInst = EcoreHelper.getContainerOfType(expression,
				Instruction.class);
		Block containingBlock = EcoreHelper.getContainerOfType(expression,
				Block.class);
		if (containingInst != null) {
			if (containingInst.isInstPhi() && isWhileJoinBlock(containingBlock)) {
				BlockWhile blockWhile = EcoreHelper.getContainerOfType(
						containingBlock, BlockWhile.class);
				addBlockBeforeBlock(block, blockWhile);
				return false;
			} else {
				List<Instruction> instructions = EcoreHelper
						.getContainingList(containingInst);

				BlockBasic blockBasic = IrFactory.eINSTANCE.createBlockBasic();

				// Split the basic block
				blockBasic.getInstructions().addAll(
						instructions.subList(0,
								instructions.indexOf(containingInst)));
				addBlockBeforeBlock(blockBasic, containingBlock);
				addBlockBeforeBlock(block, containingBlock);
				return true;
			}
		} else {
			// The given expression is contained in the condition of If/While
			addBlockBeforeBlock(block, containingBlock);
			return false;
		}
	}

	private static void addBlockBeforeBlock(Block newBlock, Block block) {
		List<Block> blocks = EcoreHelper.getContainingList(block);
		blocks.add(blocks.indexOf(block), newBlock);
	}

	private static void addToPreviousBlockBasic(Block block,
			Instruction instruction) {
		List<Block> blocks = EcoreHelper.getContainingList(block);
		BlockBasic blockBasic = IrFactory.eINSTANCE.createBlockBasic();
		blockBasic.add(instruction);
		blocks.add(blocks.indexOf(block), blockBasic);
	}

	/**
	 * Returns a deep copy of the given objects, and updates def/use chains.
	 * 
	 * @param eObjects
	 *            A Collection of objects
	 * @return a deep copy of the given objects with def/use chains correctly
	 *         updated
	 */
	public static <T extends EObject> Collection<T> copy(Collection<T> eObjects) {
		return copy(new Copier(), eObjects, true);
	}

	/**
	 * Returns a deep copy of the given objects, using the given Copier instance
	 * and updates def/use chains. If <i>copyReferences</i> is set to true,
	 * referenced objects will be duplicated in the same time their referrer
	 * will be.
	 * 
	 * @param copier
	 *            A Copier instance
	 * @param eObjects
	 *            A Collection of objects
	 * @param copyReferences
	 *            Flag to control if references must be copied
	 * @return a deep copy of the given objects with def/use chains correctly
	 *         updated
	 */
	public static <T extends EObject> Collection<T> copy(Copier copier,
			Collection<T> eObjects, boolean copyReferences) {
		Collection<T> result = copier.copyAll(eObjects);
		if (copyReferences) {
			copier.copyReferences();
		}

		TreeIterator<EObject> it = EcoreUtil.getAllContents(eObjects);
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
	 * Returns a deep copy of the given object, and updates def/use chains.
	 * 
	 * @param eObject
	 *            The EObject to copy
	 * @return a deep copy of the given object with uses correctly updated
	 */
	public static <T extends EObject> T copy(T eObject) {
		return copy(new Copier(), eObject);
	}

	/**
	 * Returns a deep copy of the given object, using the given Copier instance
	 * and updates def/use chains.
	 * 
	 * @param copier
	 *            A Copier instance
	 * @param eObject
	 *            The EObject to copy
	 * @return a deep copy of the given object with uses correctly updated
	 */
	public static <T extends EObject> T copy(Copier copier, T eObject) {
		return copy(copier, eObject, true);
	}

	/**
	 * Returns a deep copy of the given object, and updates def/use chains. If
	 * <i>copyReferences</i> is set to true, referenced objects will be
	 * duplicated in the same time their referrer will be.
	 * 
	 * @param eObject
	 *            The EObject to copy
	 * @param copyReferences
	 *            Flag to control if references must be copied
	 * @return a deep copy of the given object with uses correctly updated
	 */
	public static <T extends EObject> T copy(T eObject, boolean copyReferences) {
		return copy(new Copier(), eObject, copyReferences);
	}

	/**
	 * Returns a deep copy of the given object, using the given Copier instance
	 * and updates def/use chains. If <i>copyReferences</i> is set to true,
	 * referenced objects will be duplicated in the same time their referrer
	 * will be.
	 * 
	 * @param copier
	 *            A Copier instance
	 * @param eObject
	 *            The EObject to copy
	 * @param copyReferences
	 *            Flag to control if references must be copied
	 * @return a deep copy of the given object with uses correctly updated
	 */
	public static <T extends EObject> T copy(Copier copier, T eObject,
			boolean copyReferences) {
		@SuppressWarnings("unchecked")
		T result = (T) copier.copy(eObject);
		if (copyReferences) {
			copier.copyReferences();
		}

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
	 * Returns the first block in the given list of blocks. A new block is
	 * created if there is no block in the given block list.
	 * 
	 * @param blocks
	 *            a list of blocks
	 * @return a block
	 */
	public static BlockBasic getFirst(List<Block> blocks) {
		BlockBasic block;
		if (blocks.isEmpty()) {
			block = IrFactory.eINSTANCE.createBlockBasic();
			blocks.add(block);
		} else {
			Block firstBlock = blocks.get(0);
			if (firstBlock.isBlockBasic()) {
				block = (BlockBasic) firstBlock;
			} else {
				block = IrFactory.eINSTANCE.createBlockBasic();
				blocks.add(0, block);
			}
		}

		return block;
	}

	/**
	 * Returns the last block in the given list of blocks. A new block is
	 * created if there is no block in the given block list.
	 * 
	 * @param blocks
	 *            a list of blocks
	 * @return a block
	 */
	public static BlockBasic getLast(List<Block> blocks) {
		BlockBasic block;
		if (blocks.isEmpty()) {
			block = IrFactory.eINSTANCE.createBlockBasic();
			blocks.add(block);
		} else {
			Block lastBlock = blocks.get(blocks.size() - 1);
			if (lastBlock.isBlockBasic()) {
				block = (BlockBasic) lastBlock;
			} else {
				block = IrFactory.eINSTANCE.createBlockBasic();
				blocks.add(block);
			}
		}

		return block;
	}

	private static boolean isWhileJoinBlock(Block block) {
		if (block.isBlockBasic()) {
			BlockWhile blockWhile = EcoreHelper.getContainerOfType(block,
					BlockWhile.class);
			return (blockWhile != null && blockWhile.getJoinBlock() == block);
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

		URI uri = URI.createPlatformResourceURI(outputFolder.getFullPath()
				.append(DfUtil.getFile(entity)).addFileExtension("ir")
				.toString(), true);
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
