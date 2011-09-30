/*
 * Copyright (c) 2010-2011, IETR/INSA of Rennes
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
package net.sf.orcc.cal.validation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import net.sf.orcc.cache.CacheManager;
import net.sf.orcc.cal.cal.AstEntity;
import net.sf.orcc.cal.services.TypeCycleDetector;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.validation.Check;
import org.eclipse.xtext.validation.CheckType;

import com.google.inject.Inject;

/**
 * This class describes the validation of an RVC-CAL actor. The checks tagged as
 * "normal" are only performed when the file is saved and before code
 * generation.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class CalJavaValidator extends AbstractCalJavaValidator {

	@Inject
	private StructuralValidator structuralValidator;

	@Inject
	private WarningValidator warningValidator;

	@Inject
	private TypeValidator typeValidator;

	/**
	 * Creates a new CAL validator written in Java.
	 */
	public CalJavaValidator() {
	}

	@Check(CheckType.NORMAL)
	public void checkAstEntity(AstEntity entity) {
		// to hold errors
		List<ValidationError> errors = new ArrayList<ValidationError>(0);

		// check there are no cycles in type definitions
		if (new TypeCycleDetector().detectCycles(entity, errors)) {
			showErrors(errors);

			// don't perform further checks, because cyclic type definitions
			// mess things up
			return;
		}

		// clean cache for this entity and its dependencies
		List<Resource> resources = new ArrayList<Resource>();
		resources.add(entity.eResource());

		// add dependencies
		Map<EObject, Collection<Setting>> crossRefs = EcoreUtil.ExternalCrossReferencer
				.find(entity);
		for (EObject eObject : crossRefs.keySet()) {
			Resource resource = eObject.eResource();
			if (resource != null) {
				if (resources.contains(resource)) {
					resources.add(resource);
				}
			}
		}

		// clean cache for resources found
		for (Resource resource : resources) {
			CacheManager.instance.removeCache(resource.getURI());
		}

		// perform structural validation and type checking
		structuralValidator.validate(entity, getChain(), getContext());
		typeValidator.validate(entity, getChain(), getContext());
		warningValidator.validate(entity, getChain(), getContext());
	}

	private void showErrors(List<ValidationError> errors) {
		for (ValidationError error : errors) {
			error(error.getMessage(), error.getSource(), error.getFeature(),
					error.getIndex());
		}
	}

}
