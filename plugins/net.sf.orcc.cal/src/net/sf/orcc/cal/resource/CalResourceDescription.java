/*
 * Copyright (c) 2010, IETR/INSA of Rennes
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
package net.sf.orcc.cal.resource;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import net.sf.orcc.cal.cal.AstActor;
import net.sf.orcc.cal.cal.AstEntity;
import net.sf.orcc.cal.cal.AstFunction;
import net.sf.orcc.cal.cal.AstUnit;
import net.sf.orcc.cal.cal.AstVariable;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.impl.DefaultResourceDescription;

import com.google.common.collect.Lists;

/**
 * This class defines a CAL resource description that returns a set of exported
 * objects that only contains the actor and its ports.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class CalResourceDescription extends DefaultResourceDescription {

	private final static Logger log = Logger
			.getLogger(CalResourceDescription.class);

	public CalResourceDescription(Resource resource,
			IQualifiedNameProvider nameProvider) {
		super(resource, nameProvider);
	}

	@Override
	protected List<IEObjectDescription> computeExportedObjects() {
		loadResourceIfNecessary();
		if (getResource().getContents().isEmpty()) {
			return Collections.emptyList();
		}

		List<IEObjectDescription> result = Lists.newArrayList();
		AstEntity entity = (AstEntity) getResource().getContents().get(0);

		AstActor actor = entity.getActor();
		if (actor == null) {
			AstUnit unit = entity.getUnit();

			IEObjectDescription description = createIEObjectDescription(unit);
			result.add(description);

			for (AstVariable variable : unit.getVariables()) {
				description = createIEObjectDescription(variable);
				if (description != null) {
					result.add(description);
				}
			}

			for (AstFunction function : unit.getFunctions()) {
				description = createIEObjectDescription(function);
				if (description != null) {
					result.add(description);
				}
			}
		} else {
			IEObjectDescription description = createIEObjectDescription(actor);
			result.add(description);
		}

		return result;
	}

	protected void loadResourceIfNecessary() {
		if (!getResource().isLoaded()) {
			try {
				getResource().load(null);
			} catch (IOException e) {
				log.error(e.getMessage(), e);
			}
		}
	}

}
