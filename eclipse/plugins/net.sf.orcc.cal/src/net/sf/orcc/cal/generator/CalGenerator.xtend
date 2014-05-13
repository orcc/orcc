/*
 * Copyright (c) 2014, IETR/INSA of Rennes
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
package net.sf.orcc.cal.generator

import com.google.inject.Inject
import com.google.inject.Provider
import java.io.ByteArrayOutputStream
import java.util.HashSet
import net.sf.orcc.cache.CacheManager
import net.sf.orcc.cal.cal.AstEntity
import net.sf.orcc.cal.cal.Import
import net.sf.orcc.df.Unit
import net.sf.orcc.frontend.ActorTransformer
import net.sf.orcc.frontend.Frontend
import net.sf.orcc.frontend.UnitTransformer
import net.sf.orcc.util.OrccLogger
import net.sf.orcc.util.OrccUtil
import org.eclipse.core.resources.IProject
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.Path
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.emf.ecore.resource.ResourceSet
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.generator.IGenerator
import org.eclipse.xtext.generator.AbstractFileSystemAccess2

/**
 * Generates code from your model files on save.
 * 
 * see http://www.eclipse.org/Xtext/documentation.html#TutorialCodeGeneration
 * 
 * This class is used by Xtext to build IR file. The default BuilderParticipant has been
 * extended (see net.sf.orcc.cal.ui.builder.CalBuilder) to add calls to beforeBuild() and afterBuild().
 * These functions are used to manage dependency at the beginning and the end of a build session. A build
 * session build all file in a specific project. There is no way to know which project has been built before,
 * or if other project will be build after.
 * 
 * This generator class try to manage as quick as possible references between CAL Actors / Units.
 * It must deal with references between files in the same project or in other projects.
 * 
 * For each file to build, this class check Units it import. If it is an import from the same project,
 * the import is built before the current file. If it is an import from another project, we assume
 * this project is up-to-date, and we load mappings between AST/IR objects in the frontend.
 * 
 * @author Antoine Lorence
 */
class CalGenerator implements IGenerator {

	// These class will be used to transform AST objects into IR equivalent
	private val actorTransformer = new ActorTransformer
	private val unitTransformer = new UnitTransformer

	// Will contain the list of resources known by Frontend (it has mapping
	// for these resources)
	private val HashSet<Resource> loadedResources = newHashSet

	private var IProject currentProject
	private var ResourceSet calResourceSet
	private var ResourceSet irResourceSet

	@Inject
	private var Provider<ResourceSet> rsProvider

	/**
	 * Start a build session. This method is called by net.sf.orcc.cal.ui.builder.CalBuilder
	 * each time a build is triggered for a specific project.
	 * 
	 * Please note that when user perform a Clean on more that one project, this method will
	 * be called as many times as projects to clean.
	 * 
	 * Default Xtext implementation of BuilderParticipant ensure projects will be built in
	 * the right order.
	 */
	def beforeBuild(IProject project, ResourceSet rs) {
		currentProject = project
		loadedResources.clear

		calResourceSet = rs
		irResourceSet = rsProvider.get
	}

	/**
	 * Generate the IR file corresponding to the given calResource. If the given
	 * resource depends on other resources (Units), this method ensures:
	 * <ol>
	 * <li>All objects in the imported resource will be available in CacheManager</li>
	 * <li>We will not try to transform/serialize the imported resource if it is not absolutely necessary</li>
	 * </ol>
	 * 
	 * This method is called by net.sf.orcc.cal.ui.builder.CalBuilder
	 */
	override void doGenerate(Resource calResource, IFileSystemAccess fsa) {

		val irSubPath = calResource.irRelativePath

		// In some very specific cases, a single resource is built twice or more. We can't simply ignore
		// that, because if this resource is kept in derived list in BuilderParticipant, the IR corresponding
		// to this resource will be deleted. This bug is relatively difficult to understand.
		// See https://bugs.eclipse.org/bugs/show_bug.cgi?id=433199 for more information
		// Update: apparently, a fix for this bug has been pushed and will be available with Xtext v2.6. The
		// following test may be removed when Xtext 2.6 will be the default version used
		if(loadedResources.contains(calResource)) {
			fsa.generateFile(irSubPath, (fsa as AbstractFileSystemAccess2).readTextFile(irSubPath))
			return
		}

		// Build a list of resources we need to have registered in Frontend
		// before doing the serialization
		val toImport = calResource.entity.importedResource.filter[!loadedResources.contains(it)]

		for(importedResource : toImport) {
			// The imported resource is in the same project. We need to transform
			// and serialize it BEFORE the calResource
			if(importedResource.isInSameProject(calResource)) {
				importedResource.doGenerate(fsa)
			}
			// The imported resource is in another project. If Xtext did its job correctly,
			// this project was built in a previous session. Since Frontend has been cleaned
			// at the end of this session, we have to load its objects again.
			else {
				importedResource.loadMappings
			}
		}

		// Write in the IR file the content of the transformed AstEntity
		fsa.generateFile(irSubPath, calResource.serialize)

		// Ensure we will not do it again in the same session
		loadedResources.add(calResource)
	}

	/**
	 * Returns a EMF serialized version of the given AstEntity
	 */
	private def serialize(Resource calResource) {
		val astEntity = calResource.entity
		// Transform the AstEntity into an Actor or a Unit
		val entity =
			if (astEntity.unit != null) {
				unitTransformer.doSwitch(astEntity.unit)
			} else if (astEntity.actor != null) {
				actorTransformer.doSwitch(astEntity.actor)
			} else {
				null
			}

		// Check errors...
		if (entity == null) {
			OrccLogger.warnln("Unable to transform the CAL content")
			return ""
		}

		val irResource = irResourceSet.createResource(OrccUtil::getIrUri(calResource.URI))
		// Associate the current entity to its resource
		irResource.contents.add(entity)

		// Serialize in a simple String in memory
		val outputStream = new ByteArrayOutputStream
		irResource.save(outputStream, newHashMap)

		// Simply return the serialized content
		outputStream.toString
	}

	/**
	 * Load content from the given AstEntity (unit) resource into the Frontend
	 */
	private def loadMappings(Resource calResource) {
		val irResource = irResourceSet.getResource(OrccUtil::getIrUri(calResource.URI), true)
		val unit = irResource.contents.head as Unit
		val astUnit = calResource.entity.unit

		for(astConstant : astUnit.variables) {
			val irConstant = unit.getConstant(astConstant.name)
			Frontend::instance.putMapping(astConstant, irConstant)
		}

		for(function : astUnit.functions) {
			val procedure = unit.getProcedure(function.name)
			Frontend::instance.putMapping(function, procedure)
		}

		for(astProcedure : astUnit.procedures) {
			val procedure = unit.getProcedure(astProcedure.name)
			Frontend::instance.putMapping(astProcedure, procedure)
		}
		loadedResources.add(calResource)
	}

	/**
	 * Check if given resources URIs point in the same project
	 */
	private def isInSameProject(Resource a, Resource b) {
		a.URI.segment(1).equals(b.URI.segment(1))
	}

	/**
	 * Return the AstEntity contained in the given resource
	 */
	private def getEntity(Resource calResource) {
		calResource.contents.head as AstEntity
	}

	/**
	 * Returns the path where IR file corresponding to given calResource should be stored.
	 * Important: the returned path is relative to the output folder (not the project)
	 * 
	 * @param calResource An EMF resource representing a CAL file
	 * @return Path of the IR file as String
	 */
	private def getIrRelativePath(Resource calResource) {
		val path = new Path(calResource.URI.toPlatformString(true))
		val file = ResourcesPlugin.workspace.root.getFile(path)
		file.projectRelativePath.removeFirstSegments(1) // Remove folder (src) part
			.removeFileExtension						// Remove suffix (cal)
			.addFileExtension(OrccUtil.IR_SUFFIX) 		// Add the new suffix (ir)
			.toString									// Returns the string representation of the path
	}

	/**
	 * Return a list of all resources imported in the given AstEntity
	 */
	private def getImportedResource(AstEntity astEntity) {
		val dependingResource = newHashSet
		for(imp : astEntity.imports) {
			val calFile = imp.getExistingCalFile
			if(calFile != null) {
				dependingResource.add(
					calResourceSet.getResource(URI.createPlatformResourceURI(calFile.fullPath.toString, true), true)
				)
			}
		}
		return dependingResource
	}

	/**
	 * If the given import is valid, return the corresponding CAL file.
	 * If not, this method returns null.
	 */
	private def getExistingCalFile(Import imported) {
		val lastDotIndex = imported.importedNamespace.lastIndexOf('.')
		val unitQualifiedName = imported.importedNamespace.substring(0, lastDotIndex)
		val unitPath = new Path(unitQualifiedName.replace('.','/')).addFileExtension(OrccUtil::CAL_SUFFIX)

		for (folder : OrccUtil::getAllSourceFolders(currentProject)) {
			val ifile = folder.getFile(unitPath)
			if(ifile.exists) {
				return ifile
			}
		}
		return null
	}

	/**
	 * Perform cleans needed by the end of a session.
	 */
	def afterBuild() {
		// We need to flush all Caches, because it can explode the memory consumption...
		CacheManager.instance.unloadAllCaches();
		// The current build session ends, reset the sets
		loadedResources.clear
	}
}
