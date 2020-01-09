package net.sf.orcc.cal.formatting2

import com.google.inject.Inject
import net.sf.orcc.cal.cal.AstEntity
import net.sf.orcc.cal.cal.AstUnit
import net.sf.orcc.cal.services.CalGrammarAccess
import org.eclipse.xtext.formatting2.AbstractFormatter2
import org.eclipse.xtext.formatting2.IFormattableDocument
import net.sf.orcc.cal.cal.Variable
import net.sf.orcc.cal.cal.Function
import net.sf.orcc.cal.services.CalGrammarAccess.FunctionElements
import net.sf.orcc.cal.services.CalGrammarAccess.AstActionElements
import net.sf.orcc.cal.cal.AstAction
import org.eclipse.xtext.Keyword
import org.eclipse.xtext.formatting.impl.FormattingConfig
import org.eclipse.xtext.formatting2.IHiddenRegionFormatter
import org.eclipse.xtext.formatting2.regionaccess.ISemanticRegion
import org.eclipse.emf.common.util.UniqueEList.FastCompare
import net.sf.orcc.cal.cal.Guard
import net.sf.orcc.cal.cal.Statement
import net.sf.orcc.cal.cal.AstExpression
import net.sf.orcc.cal.cal.InputPattern
import net.sf.orcc.cal.cal.OutputPattern
import net.sf.orcc.cal.services.CalGrammarAccess.AstActionElements
import net.sf.orcc.cal.services.CalGrammarAccess.AstActionElements

class CalFormatter extends AbstractFormatter2 {
	
	@Inject extension CalGrammarAccess

	def dispatch void format(AstEntity astEntity, extension IFormattableDocument document) {
		// TODO: format HiddenRegions around keywords, attributes, cross references, etc. 
		for (_import : astEntity.imports) {
			_import.format
		}
		for (astAnnotation : astEntity.annotations) {
			astAnnotation.format
		}
		astEntity.actor.format
		astEntity.unit.format
	}

	def dispatch void format(AstUnit astUnit, extension IFormattableDocument document) {
		// TODO: format HiddenRegions around keywords, attributes, cross references, etc. 
		for (function : astUnit.functions) {
			function.format
		}
		for (astProcedure : astUnit.procedures) {
			astProcedure.format
		}
		for (variable : astUnit.variables) {
			variable.format
		}
	}
	
	def dispatch void format(Function function, extension IFormattableDocument document) {

//		functionAccess.leftParenthesisKeyword_3
//		function.
		
//		FunctionElements access = f.getFunctionAccess();
//
//		c.setNoSpace().around(access.getLeftParenthesisKeyword_3());
//		c.setNoSpace().before(access.getCommaKeyword_4_1_0());
//		c.setNoSpace().before(access.getRightParenthesisKeyword_5());
//
//		// "procedure" indents
//		c.setIndentation(access.getFunctionKeyword_1(), null);
//
//		// "var" unindents and indents, configure comma rules
//		keywordAndCommas(c, access.getVarKeyword_8_0_0(),
//				access.getCommaKeyword_8_0_2_0());
//
//		c.setLinewrap().after(access.getColonKeyword_8_1());
//
//		// "end" unindents
//		c.setIndentation(null, access.getEndKeyword_9());
//
//		c.setLinewrap().before(access.getEndKeyword_9());
//		c.setLinewrap(2).after(access.getEndKeyword_9());
	}
	
//	def dispatch void format(AstAction action, extension IFormattableDocument document) {
//		
//		
//		action.interior[indent]
//		action.inputs.format
//		action.outputs.format
//		action.guard.format
//		action.variables.format
//	}
//	
//	def dispatch void format(Guard guard, extension IFormattableDocument document) {
//		
//	}

	def dispatch void format(AstAction action, extension IFormattableDocument document) {
		action.tag.prepend[newLine]
		action.regionFor.keyword(astActionAccess.colonKeyword_2_1).prepend[noSpace]

		action.inputs.format		
		action.outputs.format

		val kGuard = action.regionFor.keyword(astActionAccess.getGuardKeyword_7_0())
		val kVar = action.regionFor.keyword(astActionAccess.varKeyword_8_0)
		val kDo = action.regionFor.keyword(astActionAccess.doKeyword_9_0)
		val kEnd = action.regionFor.keyword(astActionAccess.endKeyword_10)
		
		kGuard.prepend[newLine].append[newLine]
		kVar.prepend[newLine].append[newLine]
		
		for (k : action.guard.regionFor.keywords(",")) {
			k.prepend[noSpace].append[newLine]
		}
		
		for (k : action.regionFor.keywords(",")) {
			k.prepend[noSpace].append[newLine]
		}
		
		kDo.prepend[newLine].append[newLine]
		kEnd.prepend[newLine].append[newLine]
		for (Statement s :  action.statements) {
			s.format.append[newLine]
		}
		interior(kDo, kEnd)[indent]
		// TODO indent guard and variables
	}
	
//	def dispatch void format(InputPattern inputs, extension IFormattableDocument document) {}

//	def dispatch void format(OutputPattern outputs, extension IFormattableDocument document) {}	
	// TODO: implement for Variable, AstActor, AstPort, Function, AstProcedure, Inequality, Priority, ScheduleFsm, Fsm, AstTransition, RegExp, RegExpBinary, RegExpTag, LocalFsm, AstAction, InputPattern, OutputPattern, Guard, StatementAssign, StatementCall, StatementForeach, StatementIf, StatementElsif, StatementWhile, ExpressionBinary, ExpressionUnary, ExpressionCall, ExpressionIndex, ExpressionIf, ExpressionElsif, ExpressionList, Generator, ExpressionVariable, AstTypeInt, AstTypeList, AstTypeUint, AstAnnotation, RegExpUnary
}
