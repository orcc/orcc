package net.sf.orcc.backends.util

import java.util.List
import net.sf.orcc.ir.Expression
import net.sf.orcc.ir.util.AbstractIrVisitor
import net.sf.orcc.ir.util.ExpressionPrinter
import org.apache.commons.lang.WordUtils

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
	def printArrayIndexes(List<Expression> exprList) {
		exprList.map['''[«doSwitch»]'''].join
	}
	
	/**
	 * Split the string into lines with a max of n characters
	 */
	def wrap(CharSequence charSeq, int n) {
		WordUtils::wrap(charSeq.toString, n)
	}
	
	def wrap(CharSequence charSeq) {
		wrap(charSeq, 80)
	}
}