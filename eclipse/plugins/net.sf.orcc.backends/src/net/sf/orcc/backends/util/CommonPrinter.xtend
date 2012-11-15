package net.sf.orcc.backends.util

import java.io.BufferedInputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.PrintStream
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.List
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

import static net.sf.orcc.backends.util.CommonPrinter.*

/**
 * Define commons methods for all backends printers
 * 
 */
class CommonPrinter extends AbstractIrVisitor<CharSequence> {
	
	protected var overwriteAllFiles = false
	
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
	 * Print indexes list when accessing to an array (ex : <code>[INDEX][2][i + 1]</code>)
	 * or when declaring it. If list is empty, return an empty string.
	 */
	def printArrayIndexes(List<Expression> exprList) {
		exprList.join("", ['''[«doSwitch»]'''])
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
	
	/**
	 * Return the hash array for the byte[] content
	 * 
	 * @param content
	 * @return a byte[] containing the hash
	 */
	def hash(byte[] content) {
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
	def hash(File file) {
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
	def needToWriteFile(CharSequence content, File target) {
		return overwriteAllFiles || ! target.exists()
				|| ! MessageDigest::isEqual(hash(target), hash(content.toString.bytes));
	}
	
	/**
	 * Create a file and print content inside it. If parent folder doesn't
	 * exists, create it.
	 * 
	 * @param content
	 *            text to write in file
	 * @param target
	 *            file to write content to
	 * @return true if the file has correctly been written
	 */
	def printFile(CharSequence content, File target) {
		try {
			if ( ! target.getParentFile().exists()) {
				target.getParentFile().mkdirs();
			}
			val ps = new PrintStream(new FileOutputStream(target));
			ps.print(content);
			ps.close();
			return true;
		} catch (FileNotFoundException e) {
			OrccLogger::severe("Unable to write file " + target.path + " : " + e.cause)
			OrccLogger::severe(e.localizedMessage)
			e.printStackTrace();
			return false;
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
	
	def stringRepresentation(OpBinary op) {
		op.text
	}
	
	def stringRepresentation(OpUnary op) {
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
		object.use.variable.indexedName
	}
	
	override caseExprList(ExprList expr) {
		'''{«expr.value.join(", ", [printExpr(Integer::MAX_VALUE, 0)])»}'''
	}

	override caseExprString(ExprString expr) {
		// note the difference with the caseExprString method from the
		// expression evaluator: quotes around the string
		return '''"«String::valueOf(expr.value)»"''';
	}
}