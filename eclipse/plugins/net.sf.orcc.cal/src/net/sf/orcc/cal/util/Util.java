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
package net.sf.orcc.cal.util;

import static net.sf.orcc.cal.cal.CalPackage.eINSTANCE;

import java.util.List;

import net.sf.dftools.util.Attributable;
import net.sf.orcc.cal.cal.AnnotationArgument;
import net.sf.orcc.cal.cal.AstAnnotation;
import net.sf.orcc.cal.cal.AstEntity;
import net.sf.orcc.cal.cal.Variable;
import net.sf.orcc.ir.ExprList;
import net.sf.orcc.ir.IrFactory;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;

/**
 * This class defines utility functions for the net.sf.orcc.cal plug-in.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class Util {

	/**
	 * Returns the line on which the given object is defined.
	 * 
	 * @param object
	 *            an AST object
	 * @return the line on which the given object is defined
	 */
	public static int getLocation(EObject object) {
		ICompositeNode node = NodeModelUtils.getNode(object);
		if (node == null) {
			return 0;
		} else {
			return node.getStartLine();
		}
	}

	/**
	 * Returns the qualified name of the given entity as
	 * <code>package + "." + name</code>. If <code>package</code> is
	 * <code>null</code>, only the name is returned.
	 * 
	 * @param entity
	 *            an entity
	 * @return the qualified name of the given entity
	 */
	public static String getQualifiedName(AstEntity entity) {
		String packageName = entity.getPackage();
		String simpleName = entity.getName();

		String name = simpleName;
		if (packageName != null) {
			name = packageName + "." + name;
		}

		return name;
	}

	/**
	 * Returns <code>true</code> if the given annotation list contains an
	 * annotation whose name equals the given name.
	 * 
	 * @param name
	 *            a name
	 * @param annotations
	 *            a list of annotations
	 * @return <code>true</code> if the given annotation list contains an
	 *         annotation whose name equals the given name
	 */
	public static boolean hasAnnotation(String name,
			List<AstAnnotation> annotations) {
		for (AstAnnotation annotation : annotations) {
			if (name.equals(annotation.getName())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns <code>true</code> if the variable can be assigned a value.
	 * 
	 * @param variable
	 *            a variable
	 * @return <code>true</code> if the variable can be assigned a value
	 */
	public static boolean isAssignable(Variable variable) {
		EStructuralFeature feature = variable.eContainingFeature();
		if (variable.isConstant()) {
			return false;
		} else {
			return feature != eINSTANCE.getAstActor_Parameters()
					&& feature != eINSTANCE.getAstUnit_Variables();
		}
	}

	/**
	 * Transforms the AST annotations to IR.
	 * 
	 * @param attr
	 *            an attributable object
	 * @param annotations
	 *            a list of annotations
	 */
	public static void transformAnnotations(Attributable attr,
			List<AstAnnotation> annotations) {
		for (AstAnnotation astAnnotation : annotations) {
			String name = astAnnotation.getName();

			ExprList arguments = IrFactory.eINSTANCE.createExprList();
			for (AnnotationArgument arg : astAnnotation.getArguments()) {
				ExprList pair = IrFactory.eINSTANCE.createExprList();
				pair.getValue().add(
						IrFactory.eINSTANCE.createExprString(arg.getName()));
				
				// Add value if exist
				if (arg.getValue() != null){
					pair.getValue().add(
							IrFactory.eINSTANCE.createExprString(arg.getValue()));
				}
				arguments.getValue().add(pair);
			}

			if (arguments.getValue().isEmpty()) {
				arguments = null;
			}
			attr.setAttribute(name, arguments);
		}
	}

}
