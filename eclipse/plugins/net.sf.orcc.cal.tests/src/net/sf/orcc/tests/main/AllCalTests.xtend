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
import net.sf.orcc.cal.services.Typer
import net.sf.orcc.ir.IrFactory
import net.sf.orcc.tests.util.CalTestsHelper
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.validation.ValidationTestHelper
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(typeof(XtextRunner))
@InjectWith(typeof(CalInjectorProvider))
class AllCalTests extends CalTestsHelper {

	@Inject extension ValidationTestHelper

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

		val expected = IrFactory::eINSTANCE.createTypeUint(3)
		expected.assertEquals(Typer::getType(x))
		expected.assertEquals(Typer::getType(x.value))

		// type of "x + 1" is type of x (u3) with one more bit, so u4
		IrFactory::eINSTANCE.createTypeUint(4).assertEquals(Typer::getType(y.value))
	}
}
