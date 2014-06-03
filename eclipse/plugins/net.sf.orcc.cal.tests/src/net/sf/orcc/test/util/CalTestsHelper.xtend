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
package net.sf.orcc.test.util;

import com.google.inject.Inject
import com.google.inject.Injector
import com.google.inject.Provider
import java.util.Collections
import net.sf.orcc.cal.CalInjectorProvider
import net.sf.orcc.cal.cal.AstEntity
import net.sf.orcc.cal.validation.CalJavaValidator
import net.sf.orcc.df.Actor
import net.sf.orcc.frontend.ActorTransformer
import net.sf.orcc.frontend.UnitTransformer
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.junit4.AbstractXtextTests
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.util.ParseHelper
import org.eclipse.xtext.junit4.validation.ValidatorTester
import org.eclipse.xtext.resource.XtextResourceSet

/**
 * This class provides methods to test more efficiently CAL actors and units
 * 
 * @author Antoine Lorence
 */
 @InjectWith(typeof(CalInjectorProvider))
public class CalTestsHelper extends AbstractXtextTests {

	@Inject
	private extension ParseHelper<AstEntity>
	@Inject
	private Provider<XtextResourceSet> resourceSetProvider;
	@Inject
	private CalJavaValidator validator
	@Inject
	private Injector injector

	/**
	 * Open the given path as resource stream and parse it as CAL file.
	 * Returns the parsed AstEntity, or null if the file contains syntax errors
	 */
	def parseFile(String path) {
		val input = class.getResourceAsStream(path)
		val uri = URI::createPlatformResourceURI(path, true)
		parse(input, uri, Collections::EMPTY_MAP, resourceSetProvider.get)
	}

	/**
	 * Open the given paths as resource streams and parse them. Returns the list of
	 * parsed AstEntity. Each null elements correspond to actors with syntax errors
	 */
	def parseFiles(String... paths) {
		val rs = resourceSetProvider.get
		var parsedEntities = #[]
		for(path : paths) {
			val input = class.getResourceAsStream(path)
			val uri = URI::createPlatformResourceURI(path, true)
			parsedEntities.add(parse(input, uri, Collections::EMPTY_MAP, rs))
		}
		return parsedEntities
	}

	/**
	 * Transform the given AstEntity into its Actor / Unit equivalent 
	 */
	def transformEntity(AstEntity entity) {
		if (entity.actor != null) {
			return new ActorTransformer().doSwitch(entity.actor);
		} else if (entity.unit != null) {
			return new UnitTransformer().doSwitch(entity.unit);
		}
		fail("The given AstEntity is not an Actor or a Unit.")
	}

	/**
	 * Run the specific test interpreter on the given Actor. Returns
	 * a String with all content printed while this actor's execution.
	 */
	def runInterpreter(Actor actor) {
		val interpreter = new TestInterpreter(actor);
		interpreter.initialize();
		interpreter.schedule();

		interpreter.getOutput();
	}

	/**
	 * Test the given EObject with the CalJavaValidator. The returned
	 * AssertableDiagnostics object can be checked for specific errors/warning
	 * set from this validator
	 */
	def calJavaValidation(EObject object) {
		new ValidatorTester<CalJavaValidator>(validator, injector).validate(object)
	}
}
