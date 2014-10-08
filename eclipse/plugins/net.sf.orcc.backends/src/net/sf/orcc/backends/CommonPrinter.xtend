package net.sf.orcc.backends

import java.util.List
import java.util.Map
import net.sf.orcc.backends.ir.BlockFor
import net.sf.orcc.backends.ir.InstTernary
import net.sf.orcc.df.Connection
import net.sf.orcc.df.Instance
import net.sf.orcc.df.Port
import net.sf.orcc.graph.Vertex
import net.sf.orcc.ir.ExprBinary
import net.sf.orcc.ir.ExprBool
import net.sf.orcc.ir.ExprFloat
import net.sf.orcc.ir.ExprInt
import net.sf.orcc.ir.ExprList
import net.sf.orcc.ir.ExprString
import net.sf.orcc.ir.ExprUnary
import net.sf.orcc.ir.ExprVar
import net.sf.orcc.ir.Expression
import net.sf.orcc.ir.OpBinary
import net.sf.orcc.ir.OpUnary
import net.sf.orcc.ir.Procedure
import net.sf.orcc.ir.TypeBool
import net.sf.orcc.ir.TypeFloat
import net.sf.orcc.ir.TypeInt
import net.sf.orcc.ir.TypeList
import net.sf.orcc.ir.TypeString
import net.sf.orcc.ir.TypeUint
import net.sf.orcc.ir.TypeVoid
import net.sf.orcc.ir.util.AbstractIrVisitor
import net.sf.orcc.util.OrccLogger
import org.apache.commons.lang.WordUtils
import org.eclipse.emf.ecore.EObject

import static net.sf.orcc.OrccLaunchConstants.*

/**
 * Define commons methods for all backends printers
 * 
 */
abstract class CommonPrinter extends AbstractIrVisitor<CharSequence> {
	
	protected var precedence = Integer::MAX_VALUE
	protected var branch = 0

	protected var int fifoSize
	
	new() {
		super(true)
	}

	def setOptions(Map<String, Object> options) {
		if (options.containsKey(FIFO_SIZE)) {
			fifoSize = options.get(FIFO_SIZE) as Integer
		} else {
			fifoSize = DEFAULT_FIFO_SIZE
		}
		// Force the method return type to void
		return
	}
		
	/**
	 * Print indexes list when accessing to an array (ex : <code>[INDEX][2][i + 1]</code>)
	 * or when declaring it. If list is empty, return an empty string.
	 */
	def protected printArrayIndexes(List<Expression> exprList) {
		exprList.join("")['''[«doSwitch»]''']
	}
	
	/**
	 * Split the string into lines with a max of n characters
	 */
	def protected wrap(CharSequence charSeq, int n) {
		WordUtils::wrap(charSeq.toString, n)
	}
	
	def protected wrap(CharSequence charSeq) {
		wrap(charSeq, 80)
	}

	/**
	 * Check if the given connection has a specified BUFFER_SIZE.
	 */
	def protected hasSpecificSize(Connection connection) {
		connection?.size != null
	}

	/**
	 * Return the specified size of the connection, or the
	 * default size for all other connections
	 */
	def protected safeSize(Connection connection) {
		if(connection.hasSpecificSize) {
			connection.size.intValue
		} else {
			fifoSize
		}
	}

	override caseTypeBool(TypeBool type) {
		"bool"
	}

	override caseTypeFloat(TypeFloat type) {
		if(type.half) "half"
		else if (type.double) "double"
		else "float"
	}
	
	override caseTypeInt(TypeInt type) {
		'''int(size=«type.size»);'''
	}
	
	override caseTypeUint(TypeUint type) {
		'''uint(size=«type.size»);'''
	}
	
	override caseTypeString(TypeString type) {
		"String"
	}
	
	override caseTypeList(TypeList type) {
		'''List(type:«type.type.doSwitch», size=«type.size»)'''
	}
	
	override caseTypeVoid(TypeVoid object) {
		"void"
	}
	
	/**
	 * Print expression after saving informations to correctly
	 * add parenthesis everywhere it is needed
	 */
	def protected printExpr(Expression expr, int newPrecedence, int newBranch) {
		
		val oldBranch = branch;
		val oldPrecedence = precedence;

		branch = newBranch;
		precedence = newPrecedence;
		
		val resultingExpr = expr.doSwitch;
		
		precedence = oldPrecedence;
		branch = oldBranch;
		
		return resultingExpr
	}
	
	def protected stringRepresentation(OpBinary op) {
		op.text
	}
	
	def protected stringRepresentation(OpUnary op) {
		op.text
	}
	
	override caseExprBinary(ExprBinary expr) {
		val op = expr.op
		val resultingExpr =
			'''«expr.e1.printExpr(op.precedence, 0)» «op.stringRepresentation» «expr.e2.printExpr(op.precedence, 1)»'''
		
		if ( op.needsParentheses(precedence, branch)) {
			'''(«resultingExpr»)'''
		} else {
			resultingExpr
		}
	}
	
	override caseExprUnary(ExprUnary expr)
		'''«expr.op.stringRepresentation»«expr.expr.printExpr(Integer::MIN_VALUE, branch)»'''
		
	override caseExprFloat(ExprFloat expr) {
		String::valueOf(expr.value)
	}
	
	override caseExprInt(ExprInt expr) {
		String::valueOf(expr.value)
	}
	
	override caseExprBool(ExprBool expr) {
		String::valueOf(expr.value)
	}
	
	override caseExprVar(ExprVar expr) {
		expr.use.variable.name
	}
	
	override caseExprList(ExprList expr) {
		'''{«expr.value.join(", ")[printExpr(Integer::MAX_VALUE, 0)]»}'''
	}

	override caseExprString(ExprString expr) {
		// note the difference with the caseExprString method from the
		// expression evaluator: quotes around the string
		return '''"«String::valueOf(expr.value)»"''';
	}
	
	/**
	 * The default case is useful to support code generation
	 * for specific instructions
	 */
	override defaultCase(EObject object) {
		if(object instanceof BlockFor) {
			caseBlockFor(object as BlockFor)
		} else if (object instanceof InstTernary) {
			caseInstTernary(object as InstTernary)
		}
	}
	
	/**
	 * Print specific BlockFor object.
	 * @param block For block
	 * @see #defaultCase(EObject object)
	 */
	def caseBlockFor(BlockFor block) {
		OrccLogger::warnln("This application contains for loop, which is not yet supported. "+
		"Please override caseBlockFor() method in the backend you are using.");
		return ''''''
	}
	
	/**
	 * Print specific InstTernary object.
	 * @param inst The ternary instruction
	 * @see #defaultCase(EObject object)
	 */
	def caseInstTernary(InstTernary inst) {
		OrccLogger::warnln("This application contains ternary instructions, which is not yet supported. "+
		"Please override caseInstTernary() method in the backend you are using.");
		return ''''''
	}
	
	/**
	 * Filter ports, and return only thus which are not native as a list
	 */
	def protected getNotNative(Iterable<? extends Port> ports) {
		ports.filter[!native]
	}
	
	/**
	 * Filter procedures, and return only thus which are not native as a list
	 */
	def protected getNotNativeProcs(Iterable<? extends Procedure> procs) {
		procs.filter[!native]
	}
	
	/**
	 * Filter vertex, return only Actor's Instances.
	 */
	def protected getActorInstances(List<Vertex> vertices) {
		vertices.filter(typeof(Instance)).filter[isActor]
	}
}
