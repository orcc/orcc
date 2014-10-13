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
import net.sf.orcc.cal.cal.AstActor
import net.sf.orcc.cal.cal.AstEntity
import net.sf.orcc.cal.services.Typer
import net.sf.orcc.ir.IrFactory
import net.sf.orcc.tests.util.CalTestsHelper
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.validation.ValidationTestHelper
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(typeof(XtextRunner))
@InjectWith(typeof(CalInjectorProvider))

class TypesTests extends CalTestsHelper {

	private var AstEntity entity
	private var AstActor actor

	private val irFact = IrFactory::eINSTANCE
	@Inject extension ValidationTestHelper

	private def stateVar(AstActor actor, String varName) {
		for(variable : actor.stateVariables) {
			if(variable.name.equals(varName)) return variable
		}
		return null
	}

	@Before
	def initializeActor() {
		entity = '''
			actor TypesTester() ==> :
				int 			i32	:= -123;
				int(size=16) 	i16	:= -111;
				int(size=4) 	i4	:= -1;
				int(size=46) 	i46	:= 145147;
				int(size=64) 	i64	:= 145147;

				uint			u32	:= 456;
				uint(size=16)	u16	:= 9999;
				uint(size=60)	u60	:= 12345678900;
				uint(size=1)	u1	:= 1;

				float			fl	:= 5.2;
				half			ha	:= 5.2;
				double			db	:= 5.2;

				bool			bo	:= true;

				// Additions
				 int 			res1	= i32 + i32;
				uint			res2	= u16 + u16;
				 int			res3	= i16 + u16;
				 int			res4	= u60 + i16;
				float			res5	= i16 + fl;
				half			res6	= i32 + ha;
				double			res7	= u60 + db;

				// Subtractions
				 int			res8	= i32 - 4;
				 int			res9	= i32 - i32;
				 int			res10	= u32 - i32;
				float			res11	= i32 - fl;
			end
		'''.parse
		
		actor = entity.actor
		return
	}

	@Test
	def checkEntityIssues() {
		entity.assertNotNull
		entity.assertNoErrors
	}

	@Test
	def testAdditions() {
		val a = actor.stateVar("res1").value;
		val b = actor.stateVar("res2").value;
		val c = actor.stateVar("res3").value;
		val d = actor.stateVar("res4").value;
		val e = actor.stateVar("res5").value;
		val f = actor.stateVar("res6").value;
		val g = actor.stateVar("res7").value;

		irFact.createTypeInt(33).assertEquals(Typer::getType(a))
		irFact.createTypeUint(17).assertEquals(Typer::getType(b))
		irFact.createTypeInt(18).assertEquals(Typer::getType(c))
		irFact.createTypeInt(62).assertEquals(Typer::getType(d))
		irFact.createTypeFloat.assertEquals(Typer::getType(e))
		irFact.createTypeFloat(16).assertEquals(Typer::getType(f))
		irFact.createTypeFloat(64).assertEquals(Typer::getType(g))
	}

	@Test
	def testSubtractions() {
		val a = actor.stateVar("res8").value;
		val b = actor.stateVar("res9").value;
		val c = actor.stateVar("res10").value;
		val d = actor.stateVar("res11").value;

		irFact.createTypeInt(32).assertEquals(Typer::getType(a))
		irFact.createTypeInt(32).assertEquals(Typer::getType(b))
		irFact.createTypeInt(33).assertEquals(Typer::getType(c))
		irFact.createTypeFloat.assertEquals(Typer::getType(d))
	}
}