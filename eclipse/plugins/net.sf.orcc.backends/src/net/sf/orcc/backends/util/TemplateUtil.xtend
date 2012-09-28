package net.sf.orcc.backends.util

import net.sf.orcc.ir.util.AbstractIrVisitor
import net.sf.orcc.ir.util.ExpressionPrinter
import net.sf.orcc.ir.Expression
import java.util.List

class TemplateUtil extends AbstractIrVisitor<CharSequence> {
	protected ExpressionPrinter exprPrinter
	
		
	/**
	 * Print indexes list when accessing to an array (ex : "[INDEX][2][i + 1]" ) or when declare it
	 */
	def printArrayIndexes(List<Expression> list)
		'''«FOR expr : list»[«expr.doSwitch»]«ENDFOR»'''
}