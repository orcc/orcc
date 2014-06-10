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
package net.sf.orcc.cal;

import org.antlr.runtime.MismatchedTokenException;
import org.antlr.runtime.RecognitionException;
import org.eclipse.xtext.diagnostics.Diagnostic;
import org.eclipse.xtext.nodemodel.SyntaxErrorMessage;
import org.eclipse.xtext.parser.antlr.ISyntaxErrorMessageProvider;

/**
 * This class defines a syntax error message provider specific to RVC-CAL.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class CalSyntaxErrorMessageProvider implements
		ISyntaxErrorMessageProvider {

	@Override
	public SyntaxErrorMessage getSyntaxErrorMessage(IParserErrorContext context) {
		RecognitionException ex = context.getRecognitionException();
		if (ex instanceof MismatchedTokenException) {
			MismatchedTokenException mte = (MismatchedTokenException) ex;
			if ("schedule".equals(mte.token.getText())) {
				return new SyntaxErrorMessage(
						"At most one \"schedule\" construct allowed",
						CalDiagnostic.ERROR_SCHEDULE);
			}
		}

		return new SyntaxErrorMessage(context.getDefaultMessage(),
				Diagnostic.SYNTAX_DIAGNOSTIC);
	}

	@Override
	public SyntaxErrorMessage getSyntaxErrorMessage(
			IValueConverterErrorContext context) {
		return new SyntaxErrorMessage(context.getDefaultMessage(),
				Diagnostic.SYNTAX_DIAGNOSTIC);
	}

}
