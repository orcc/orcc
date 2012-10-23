package net.sf.orcc.backends.util

import net.sf.orcc.ir.util.AbstractIrVisitor
import net.sf.orcc.ir.util.ExpressionPrinter
import net.sf.orcc.ir.Expression
import java.util.List

class TemplateUtil extends AbstractIrVisitor<CharSequence> {
	
	protected ExpressionPrinter exprPrinter
	
	new() {
		super(true)
		exprPrinter = new ExpressionPrinter
	}
		
	/**
	 * Print indexes list when accessing to an array (ex : <code>[INDEX][2][i + 1]</code>)
	 * or when declaring it. If list is empty, return an empty string.
	 */
	def printArrayIndexes(List<Expression> exprList)
		'''«FOR expr : exprList»[«expr.doSwitch»]«ENDFOR»'''
}