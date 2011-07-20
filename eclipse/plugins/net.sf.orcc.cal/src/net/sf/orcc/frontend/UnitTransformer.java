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

import java.util.HashMap;
import java.util.Map;

import net.sf.orcc.cal.cal.AstFunction;
import net.sf.orcc.cal.cal.AstProcedure;
import net.sf.orcc.cal.cal.AstUnit;
import net.sf.orcc.cal.cal.AstVariable;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Unit;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EObject;

/**
 * This class transforms an AST unit to its IR equivalent.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class UnitTransformer {

	/**
	 * Transforms the given AST unit to an IR unit.
	 * 
	 * @param file
	 *            the .cal file where the unit is defined
	 * @param astUnit
	 *            the AST of the unit
	 * @return the unit in IR form
	 */
	public Unit transform(IFile file, AstUnit astUnit) {
		AstTransformer astTransformer = new AstTransformer();
		Unit unit = IrFactory.eINSTANCE.createUnit();
		// TODO add file name
		// unit.setFileName(file.getFullPath().toString());

		// TODO add line number
		// int lineNumber = Util.getLocation(astUnit);
		// unit.setLineNumber(lineNumber);

		Map<EObject, EObject> mapAstToIr = new HashMap<EObject, EObject>();
		astTransformer.setMapAstToIr(mapAstToIr);
		astTransformer.setLists(unit.getProcedures(), unit.getConstants());

		// constants
		for (AstVariable astVariable : astUnit.getVariables()) {
			astTransformer.transformGlobalVariable(astVariable);
		}

		// functions
		for (AstFunction function : astUnit.getFunctions()) {
			astTransformer.transformFunction(function);
		}

		// procedures
		for (AstProcedure procedure : astUnit.getProcedures()) {
			astTransformer.transformProcedure(procedure);
		}

		// TODO set unit name
		// AstEntity entity = (AstEntity) astUnit.eContainer();
		// unit.setName(net.sf.orcc.cal.util.Util.getQualifiedName(entity));

		return unit;
	}

}
