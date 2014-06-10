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
package net.sf.orcc.tests.main

import com.google.inject.Inject
import net.sf.orcc.cal.CalInjectorProvider
import net.sf.orcc.cal.cal.AstEntity
import net.sf.orcc.df.Actor
import net.sf.orcc.frontend.ActorTransformer
import net.sf.orcc.frontend.UnitTransformer
import net.sf.orcc.tests.util.CalTestsHelper
import net.sf.orcc.tests.util.TestInterpreter
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.validation.ValidationTestHelper
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(typeof(XtextRunner))
@InjectWith(typeof(CalInjectorProvider))
class CalInterpreterTests extends CalTestsHelper {

	@Inject extension ValidationTestHelper


	/**
	 * Transform the given AstEntity into its Actor / Unit equivalent 
	 */
	def private transformEntity(AstEntity entity) {
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
	def private runInterpreter(Actor actor) {
		val interpreter = new TestInterpreter(actor);
		interpreter.initialize();
		interpreter.schedule();

		interpreter.getOutput();
	}

	@Test
	def testElseIf() {
		val entity = parseFile("/test/pass/Elsif.cal")

		entity.assertNoErrors

		val resultString = (entity.transformEntity as Actor).runInterpreter
		"okok".assertEquals(resultString)
	}

	@Test
	def testElseIfExpr() {
		val entity = parseFile("/test/pass/ElsifExpr.cal")

		entity.assertNoErrors

		val resultString = (entity.transformEntity as Actor).runInterpreter
		"result = 0".assertEquals(resultString)
	}

	@Test
	def testElseIfStateVar() {
		val entity = parseFile("/test/pass/ElsifStateVar.cal")

		entity.assertNoErrors

		val resultString = (entity.transformEntity as Actor).runInterpreter
		"ok".assertEquals(resultString)
	}

	@Test
	def testInitStateVarFunction() {
		val entity = parseFile("/test/pass/InitStateVarFunction.cal")

		entity.assertNoErrors

		val resultString = (entity.transformEntity as Actor).runInterpreter
		"pp = 8".assertEquals(resultString)
	}

	@Test
	def testExecShadow() {
		val entity = parseFile("/test/pass/Shadowing.cal")

		entity.assertNoErrors

		val resultString = (entity.transformEntity as Actor).runInterpreter
		"x = 0".assertEquals(resultString)
	}

	@Test
	def testExecWhile() throws Exception {
		val entity = parseFile("/test/pass/CodegenWhile.cal")

		entity.assertNoErrors

		val resultString = (entity.transformEntity as Actor).runInterpreter
		"idx is 60".assertEquals(resultString)
	}
}
