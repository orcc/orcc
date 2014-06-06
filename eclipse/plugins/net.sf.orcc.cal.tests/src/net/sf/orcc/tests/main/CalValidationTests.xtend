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
import net.sf.orcc.cal.validation.CalJavaValidator
import net.sf.orcc.cal.validation.StructuralValidator
import net.sf.orcc.cal.validation.TypeValidator
import net.sf.orcc.cal.validation.WarningValidator
import net.sf.orcc.tests.util.CalTestsHelper
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.validation.ValidationTestHelper
import org.eclipse.xtext.junit4.validation.ValidatorTester
import org.junit.Test
import org.junit.runner.RunWith

import static net.sf.orcc.cal.CalDiagnostic.*
import static net.sf.orcc.cal.cal.CalPackage.Literals.*
import static org.eclipse.xtext.diagnostics.Diagnostic.*

@RunWith(typeof(XtextRunner))
@InjectWith(typeof(CalInjectorProvider))
class CalValidationTests extends CalTestsHelper {

	@Inject extension ValidationTestHelper
	@Inject
	protected CalJavaValidator defaultValidator
	@Inject
	protected StructuralValidator structuralValidator
	@Inject
	protected TypeValidator typesValidator
	@Inject
	protected WarningValidator warningsValidator

	/*
	 * Test the given EObject with the available validators. The returned
	 * AssertableDiagnostics object can be checked for specific errors/warning
	 * set from them
	 */

	def private defaultValidation(EObject object) {
		new ValidatorTester<CalJavaValidator>(defaultValidator, injector).validate(object)
	}
	def private structuralValidation(EObject object) {
		new ValidatorTester<StructuralValidator>(structuralValidator, injector).validate(object)
	}
	def private typesValidation(EObject object) {
		new ValidatorTester<TypeValidator>(typesValidator, injector).validate(object)
	}
	def private warningsValidation(EObject object) {
		new ValidatorTester<WarningValidator>(warningsValidator, injector).validate(object)
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

	@Test
	def testPattern1 () {
		val entity = '''
			actor Pattern1() int I ==> int O :
				action O:[o] ==>
				end
			end
		'''.parse

		// Before validation, everything is ok here
		entity.assertNotNull

		// Input references an unknown port (i.e. an output port
		// is unknown for an input pattern
		entity.assertError(INPUT_PATTERN, LINKING_DIAGNOSTIC)
	}

	@Test
	def testPattern2 () {
		val entity = '''
			actor Pattern2() int I ==> int O :
			    action I:[42] ==>
			    end
			end
		'''.parse

		// Before validation, everything is ok here
		entity.assertNotNull
		// 42 is not a valid token, a variable must be declared instead
		entity.assertError(INPUT_PATTERN, SYNTAX_DIAGNOSTIC)
	}

	@Test
	def testPattern3 () {
		val entity = '''
			actor Pattern3() int I ==> :
			    action I:[x], I:[y] ==>
			    end
			end
		'''.parse(URI::createPlatformResourceURI("Pattern3.cal", true), resourceSetProvider.get)

		// Before validation, everything is ok here
		entity.assertNotNull
		// There shouldn't be any structural issue here
		entity.assertNoErrors
		entity.defaultValidation.assertOK

		// 42 is not a valid token, a variable must be declared instead
		entity.structuralValidation.assertError(ERROR_DUPLICATE_PORT_REFERENCE, "duplicate reference to port I")
	}

	@Test
	def testPattern4 () {
		val entity = '''
			actor Pattern4() ==> int O :
			    action ==> O:[1], O:[2]
			    end
			end
		'''.parse(URI::createPlatformResourceURI("Pattern4.cal", true), resourceSetProvider.get)

		// Before validation, everything is ok here
		entity.assertNotNull
		// There shouldn't be any structural issue here
		entity.assertNoErrors
		entity.defaultValidation.assertOK

		// 42 is not a valid token, a variable must be declared instead
		entity.structuralValidation.assertError(ERROR_DUPLICATE_PORT_REFERENCE, "duplicate reference to port O")
	}

	@Test
	def testTypeError1 () {
		val entity = '''
			unit TypeError1 :
				function abc(uint c) --> bool :
					c > 4
				end
				procedure buggy()
				var
					uint a[3][2],
					bool b := abc(a[2])
				begin
				end
			end
		'''.parse(URI::createPlatformResourceURI("TypeError1.cal", true), resourceSetProvider.get)

		entity.assertNotNull
		// There shouldn't be any structural issue here
		entity.assertNoErrors
		entity.defaultValidation.assertOK
		// a[2] is a list, abc() procedure needs an int as argument
		entity.typesValidation.assertError(ERROR_TYPE_CONVERSION,
			"Type mismatch: cannot convert from List")
	}

	@Test
	def testTypeError2 () {
		val entity = '''
			actor TypeError2() ==> :
				@native procedure compare_init() end
				@native procedure compare_NBytes(uint(size=8) outTable[1], uint(size=12) nbTokenToRead) end
				readByte : action ==>
				var
					int(size=8)  tmp := 17
				do
					compare_NBytes(tmp, 1);
				end
			end
		'''.parse(URI::createPlatformResourceURI("TypeError2.cal", true), resourceSetProvider.get)

		entity.assertNotNull
		// There shouldn't be any structural issue here
		entity.assertNoErrors
		entity.defaultValidation.assertOK
		// Passing int to a procedure with uint argument
		entity.typesValidation.assertError(ERROR_TYPE_CONVERSION,
			"Type mismatch: cannot convert from int")
	}

	@Test
	def testTypeError3 () {
		val entity = '''
			actor TypeError3 () ==> :
				int(size=-42) x;
			end
		'''.parse(URI::createPlatformResourceURI("TypeError3.cal", true), resourceSetProvider.get)

		entity.assertNotNull
		// There shouldn't be any structural issue here
		entity.assertNoErrors
		entity.defaultValidation.assertOK
		// Passing int with size < 0 is invalid
		entity.typesValidation.assertError(ERROR_TYPE_SYNTAX,
			"This size must evaluate to a compile-time constant greater than zero")
	}
	
	@Test
	def testWarningVariableUnused () {
		val entity = '''
			actor UnusedVariable() ==> :
				int aStateVariable := 8;

				action1: action ==> 
				do
					print("something");
				end
			end
		'''.parse

		entity.assertNoIssues
		entity.warningsValidation.assertWarning(WARNING_UNUSED, "The variable aStateVariable is never used")
	}
}
