/*
 * Copyright (c) 2011, IETR/INSA of Rennes
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
package net.sf.orcc.frontend;

import net.sf.orcc.cal.cal.AstEntity;
import net.sf.orcc.cal.cal.AstProcedure;
import net.sf.orcc.cal.cal.AstUnit;
import net.sf.orcc.cal.cal.Function;
import net.sf.orcc.cal.cal.Variable;
import net.sf.orcc.cal.cal.util.CalSwitch;
import net.sf.orcc.cal.services.Typer;
import net.sf.orcc.cal.util.Util;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.df.Unit;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;

/**
 * This class transforms an AST unit to its IR equivalent.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class UnitTransformer extends CalSwitch<Unit> {

	final StructTransformer structTransformer;

	public UnitTransformer() {
		structTransformer = new StructTransformer();
	}

	/**
	 * Transforms the given AST unit to an IR unit.
	 * 
	 * @param astUnit
	 *            the AST of the unit
	 * @return the unit in IR form
	 */
	@Override
	public Unit caseAstUnit(AstUnit astUnit) {
		Unit unit = DfFactory.eINSTANCE.createUnit();
		Frontend.putMapping(astUnit, unit);

		unit.setFileName(astUnit.eResource().getURI().toPlatformString(true));

		int lineNumber = Util.getLocation(astUnit);
		unit.setLineNumber(lineNumber);

		// constants
		for (Variable variable : astUnit.getVariables()) {
			final Var var = (Var) structTransformer.doSwitch(variable);
			unit.getConstants().add(var);
		}

		// Create empty stubs for functions and procedures
		// This tip will allow to call a function/procedure declared later
		for (Function function : astUnit.getFunctions()) {
			final Procedure procedure = IrFactory.eINSTANCE.createProcedure(
					function.getName(), 0, Typer.getType(function));
			Frontend.putMapping(function, procedure);
			unit.getProcedures().add(procedure);
		}
		for (AstProcedure astProcedure : astUnit.getProcedures()) {
			final Procedure procedure = IrFactory.eINSTANCE.createProcedure(
					astProcedure.getName(), 0, Typer.getType(astProcedure));
			Frontend.putMapping(astProcedure, procedure);
			unit.getProcedures().add(procedure);
		}

		// Really transform functions / procedures
		for (Function function : astUnit.getFunctions()) {
			structTransformer.doSwitch(function);
		}
		for (AstProcedure astProcedure : astUnit.getProcedures()) {
			structTransformer.doSwitch(astProcedure);
		}


		final AstEntity entity = (AstEntity) astUnit.eContainer();
		unit.setName(net.sf.orcc.cal.util.Util.getQualifiedName(entity));

		return unit;
	}

}
