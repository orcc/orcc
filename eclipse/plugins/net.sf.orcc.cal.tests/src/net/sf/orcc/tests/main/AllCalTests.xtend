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
import net.sf.orcc.cal.services.Evaluator
import net.sf.orcc.cal.services.Typer
import net.sf.orcc.df.Actor
import net.sf.orcc.ir.Expression
import net.sf.orcc.ir.IrFactory
import net.sf.orcc.tests.util.CalTestsHelper
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.util.EcoreUtil
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.validation.ValidationTestHelper
import org.junit.Test
import org.junit.runner.RunWith

import static net.sf.orcc.cal.cal.CalPackage.Literals.*
import static org.eclipse.xtext.diagnostics.Diagnostic.*
import static net.sf.orcc.cal.CalConstants.*

@RunWith(typeof(XtextRunner))
@InjectWith(typeof(CalInjectorProvider))
class AllCalTests extends CalTestsHelper {

	@Inject extension ValidationTestHelper
	
	private val irFact = IrFactory::eINSTANCE

	@Test
	def testActor_initializeOnly() {
		val entity = '''
			actor InitializePattern() int I ==> int O :
			    initialize ==> O:[42]
			    end
			end
		'''.parse

		entity.assertNotNull
		entity.assertNoErrors
	}

	@Test
	def testIntTypes() {
		val entity = '''
			actor TypeInt() ==> :
				uint(size=3) x := 7;
				int(size=15) y := x + 1;
			end
		'''.parse

		val stateVars = entity.actor.stateVariables
		val x = stateVars.get(0);
		val y = stateVars.get(1);

		val expected = irFact.createTypeUint(3)
		expected.assertEquals(Typer::getType(x))
		expected.assertEquals(Typer::getType(x.value))

		// type of "x + 1" is type of x (u3) with one more bit, so u4
		irFact.createTypeUint(4).assertEquals(Typer::getType(y.value))
	}

	@Test
	def testGenerator() {
		val entity = '''
			actor Generator() ==> :
				int stateVar[1][2][3] :=
				[
				  [
				    [ i * j * k : for int i in 1 .. 3 ] : for int j in 1 .. 2
				  ] : for int k in 1 .. 1
				];
			end
		'''.parse

		val varValue = Evaluator::getValue(entity.actor.stateVariables.head);

		val l11 = irFact.createExprList(#[
			irFact.createExprInt(1) as Expression,
			irFact.createExprInt(2) as Expression,
			irFact.createExprInt(3) as Expression
		])
		val l12 = irFact.createExprList(#[
			irFact.createExprInt(2) as Expression,
			irFact.createExprInt(4) as Expression,
			irFact.createExprInt(6) as Expression
		])
		val l1 = irFact.createExprList()
		l1.value.add(l11)
		l1.value.add(l12)

		val expected = irFact.createExprList()
		expected.value.add(l1)

		EcoreUtil::equals(expected, varValue).assertTrue
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

	@Test
	def testInvalidFSM() {
		val entity = '''
			actor BadFsm() ==> :
				action1: action ==> end
				schedule fsm State1:
					State1 (action1) --> State2;
					State1 (action1) --> State3;
				end
			end
		'''.parse

		// FSM transitions references undeclared states (State2 and State3)
		entity.assertError(AST_TRANSITION, LINKING_DIAGNOSTIC)
	}

	@Test
	def testParamIsConstant() {
		val entity = '''
			actor Param(int param) ==> :
				action ==>
				do
					param := param + 1;
				end
			end
		'''.parse(URI::createPlatformResourceURI("Param.cal", true), resourceSetProvider.get)

		// Before validation, everything is ok here
		entity.assertNotNull
		entity.assertNoErrors

		entity.typesValidation.assertError(ERROR_CONSTANT_ASSIGNATION, "param is not assignable")
	}
}
