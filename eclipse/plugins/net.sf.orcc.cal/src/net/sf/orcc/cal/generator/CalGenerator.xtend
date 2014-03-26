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

import java.io.ByteArrayOutputStream
import net.sf.orcc.cal.cal.AstEntity
import net.sf.orcc.frontend.ActorTransformer
import net.sf.orcc.frontend.UnitTransformer
import net.sf.orcc.util.OrccLogger
import net.sf.orcc.util.OrccUtil
import org.eclipse.core.resources.ResourcesPlugin
import org.eclipse.core.runtime.Path
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.generator.IFileSystemAccess
import org.eclipse.xtext.generator.IGenerator

/**
 * Generates code from your model files on save.
 * 
 * see http://www.eclipse.org/Xtext/documentation.html#TutorialCodeGeneration
 */
class CalGenerator implements IGenerator {

	private ActorTransformer actorTransformer = new ActorTransformer
	private UnitTransformer unitTransformer = new UnitTransformer

	override void doGenerate(Resource calResource, IFileSystemAccess fsa) {
		fsa.generateFile(calResource.irRelativePath, calResource.entity.serialize)
	}

	/**
	 * Returns a serialized version of the given AStEntity
	 * 
	 */
	def serialize(AstEntity astEntity) {
		val entity =
			if (astEntity.unit != null) {
				unitTransformer.doSwitch(astEntity.unit)
			} else if (astEntity.actor != null) {
				actorTransformer.doSwitch(astEntity.actor)
			} else {
				null
			}

		if (entity == null) {
			OrccLogger.warnln("Unable to transform the CAL content")
			return ""
		}

		val resource = astEntity.eResource.resourceSet.createResource(OrccUtil::getIrUri(astEntity.eResource.URI))
		resource.contents.add(entity)

		val outputStream = new ByteArrayOutputStream
		resource.save(outputStream, newHashMap)

		outputStream.toString
	}

	/**
	 * Return the AstEntity contained in the given resource
	 */
	def private getEntity(Resource calResource) {
		calResource.contents.head as AstEntity
	}

	/**
	 * Returns the path where IR file corresponding to given calResource should be stored.
	 * Important: the returned path is relative to the output folder (not the project)
	 * 
	 * @param calResource An EMF resource representing a CAL file
	 * @return Path of the IR file as String
	 */
	def private getIrRelativePath(Resource calResource) {
		val path = new Path(calResource.URI.toPlatformString(true))
		val file = ResourcesPlugin.workspace.root.getFile(path)
		file.projectRelativePath.removeFirstSegments(1) // Remove folder (src) part
			.removeFileExtension						// Remove suffix (cal)
			.addFileExtension(OrccUtil.IR_SUFFIX) 		// Add the new suffix (ir)
			.toString									// Returns the string representation of the path
	}
}
