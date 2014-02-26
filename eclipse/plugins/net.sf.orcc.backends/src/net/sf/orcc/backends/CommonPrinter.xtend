package net.sf.orcc.backends

import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.IOException
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.List
import net.sf.orcc.backends.ir.BlockFor
import net.sf.orcc.backends.ir.InstTernary
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
import net.sf.orcc.ir.TypeBool
import net.sf.orcc.ir.TypeFloat
import net.sf.orcc.ir.TypeInt
import net.sf.orcc.ir.TypeList
import net.sf.orcc.ir.TypeString
import net.sf.orcc.ir.TypeUint
import net.sf.orcc.ir.TypeVoid
import net.sf.orcc.ir.util.AbstractIrVisitor
import net.sf.orcc.util.OrccLogger
import org.apache.commons.lang.ArrayUtils
import org.apache.commons.lang.WordUtils
import org.eclipse.emf.ecore.EObject
import net.sf.orcc.ir.Procedure

/**
 * Define commons methods for all backends printers
 * 
 */
abstract class CommonPrinter extends AbstractIrVisitor<CharSequence> {
	
	protected var precedence = Integer::MAX_VALUE
	protected var branch = 0
	
	/**
	 * The algorithm used with MessageDigest. Can be MD, SHA, etc (see <a
	 * href="http://docs.oracle.com/javase/1.4.2/docs/guide/security/CryptoSpec.html#AppA">
	 * http://docs.oracle.com/javase/1.4.2/docs/guide/security/CryptoSpec.html#AppA</a>)
	 */
	private static val String digestAlgo = "MD5";
	
	new() {
		super(true)
	}

	/**
	 * Print extra code for array inbounds checking (ex : C assert) at each usage (load/store)
	 * If exprList is empty, return an empty string.
	 */
	def checkArrayInbounds(List<Expression> exprList, List<Integer> dims) {
		OrccLogger::warnln("This application contains array inbounds check, which is not yet supported. "+
		"Please override checkArrayInbounds() method in the backend you are using.");
		return ''''''
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
	 * Return the hash array for the byte[] content
	 * 
	 * @param content
	 * @return a byte[] containing the hash
	 */
	def private hash(byte[] content) {
		try {
			// MessageDigest is NOT thread safe, it must be created locally on
			// each call, it can't be a member of this class
			val messageDigest = MessageDigest::getInstance(digestAlgo);
			return messageDigest.digest(content);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return ArrayUtils::EMPTY_BYTE_ARRAY;
	}

	/**
	 * Return the hash array for the file
	 * 
	 * @param file
	 * @return a byte[] containing the hash
	 */
	def private hash(File file) {
		try {
			// MessageDigest is NOT thread safe, it must be created locally on
			// each call, it can't be a member of this class
			val messageDigest = MessageDigest::getInstance(digestAlgo);

			val in = new BufferedInputStream(new FileInputStream(file));
			var theByte = 0;
			try {
				while ((theByte = in.read()) != -1) {
					messageDigest.update(theByte as byte);
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				in.close();
			}
			return messageDigest.digest();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return ArrayUtils::EMPTY_BYTE_ARRAY;
	}
	
	
	/**
	 * Return true if targetFile content need to be replaced by the content's
	 * value
	 * 
	 * @param targetFile
	 * @param content
	 */
	def protected needToWriteFile(CharSequence content, File target) {
		return ! target.exists()
				|| ! MessageDigest::isEqual(hash(target), hash(content.toString.bytes));
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
		
	override caseExprFloat(ExprFloat object) {
		String::valueOf(object.value)
	}
	
	override caseExprInt(ExprInt object) {
		String::valueOf(object.value)
	}
	
	override caseExprBool(ExprBool object) {
		String::valueOf(object.value)
	}
	
	override caseExprVar(ExprVar object) {
		object.use.variable.name
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
