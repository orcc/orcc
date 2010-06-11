/*
 * Copyright (c) 2009, Samuel Keller EPFL
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
 *   * Neither the name of the EPFL nor the names of its
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
package net.sf.orcc.backends.xlim.templates;

/**
 * XlimOperationTemplate interface holds operations for XLIM printing
 * 
 * @author Samuel Keller EPFL
 */
public interface XlimOperationTemplate {
	public static final String ADD = "$add";
	public static final String AND = "$and";
	public static final String ASSIGN = "assign";
	public static final String BITAND = "bitand";
	public static final String BITNOT = "bitnot";
	public static final String BITOR = "bitor";
	public static final String BITXOR = "bitxor";
	public static final String DIV = "$div";
	public static final String ELTS = "$elts";

	public static final String EQ = "$eq";
	public static final String EXP = "$exp";
	public static final String GE = "$ge";
	public static final String GT = "$gt";
	public static final String IDIV = "$idiv";
	public static final String LE = "$le";
	public static final String LITINT = "$literal_Integer";
	public static final String LSHIFT = "lshift";
	public static final String LT = "$lt";
	public static final String MOD = "$mod";
	public static final String MUL = "$mul";
	public static final String NE = "$ne";
	public static final String NEG = "$negate";
	public static final String NOOP = "noop";
	public static final String NOT = "$not";
	public static final String OR = "$or";
	public static final String PINPEEK = "pinPeek";
	public static final String PINREAD = "pinRead";
	public static final String PINSTATUS = "pinStatus";
	public static final String PINWRITE = "pinWrite";

	public static final String RSHIFT = "rshift";
	public static final String SUB = "$sub";
	public static final String TASKCALL = "taskCall";
	public static final String VARREF = "var_ref";
}
