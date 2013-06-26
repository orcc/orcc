/*
 * Copyright (c) 2011, IETR/INSA of Rennes
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
package net.sf.orcc.util.sexp;

/**
 * This class defines a parser of S-Expressions.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class SExpParser {

	private static final boolean[] constituent = { false, // space
			true, // !
			false, // "
			true, // #
			true, // $
			true, // %
			true, // &
			false, // '
			false, // (
			false, // )
			true, // *
			true, // +
			false, // ,
			true, // -
			true, // .
			true, // /
			true, // 0
			true, // 1
			true, // 2
			true, // 3
			true, // 4
			true, // 5
			true, // 6
			true, // 7
			true, // 8
			true, // 9
			true, // :
			false, // ;
			true, // <
			true, // =
			true, // >
			true, // ?
			true, // @
			true, // A
			true, // B
			true, // C
			true, // D
			true, // E
			true, // F
			true, // G
			true, // H
			true, // I
			true, // J
			true, // K
			true, // L
			true, // M
			true, // N
			true, // O
			true, // P
			true, // Q
			true, // R
			true, // S
			true, // T
			true, // U
			true, // V
			true, // W
			true, // X
			true, // Y
			true, // Z
			true, // [
			false, // \
			true, // ]
			true, // ^
			true, // _
			false, // `
			true, // a
			true, // b
			true, // c
			true, // d
			true, // e
			true, // f
			true, // g
			true, // h
			true, // i
			true, // j
			true, // k
			true, // l
			true, // m
			true, // n
			true, // o
			true, // p
			true, // q
			true, // r
			true, // s
			true, // t
			true, // u
			true, // v
			true, // w
			true, // x
			true, // y
			true, // z
			true, // {
			false, // |
			true, // }
			true // ~
	};

	private int pos;

	private char[] stream;

	/**
	 * Creates a new s-expression parser.
	 * 
	 * @param contents
	 *            contents to parse
	 */
	public SExpParser(String contents) {
		this.stream = contents.toCharArray();
	}

	private boolean isConstituent(char x) {
		if (x < 32 || x > 127) {
			throw new IllegalArgumentException("illegal character " + x);
		}

		return constituent[x - 32];
	}

	/**
	 * Reads an s-expression from the stream.
	 * 
	 * @return an s-expression
	 */
	public SExp read() {
		while (pos < stream.length) {
			char x = stream[pos++];
			switch (x) {
			case ' ':
			case '\t':
			case '\r':
			case '\n':
				// ignore whitespace
				continue;

			case ';':
				skipComment();
				break;

			case '(':
				return readList(x);

			case '"':
				return readString();

			case '\\':
				return readTokenSingleEscape();

			case '|':
				throw new UnsupportedOperationException(
						"multiple escape not yet implemented");

			default:
				if (isConstituent(x)) {
					return readToken(x);
				} else {
					throw new IllegalStateException("unexpected character " + x);
				}
			}
		}
		return null;
	}

	/**
	 * Parses a list of s-expressions.
	 * 
	 * @return an SExprList
	 */
	private SExpList readList(char x) {
		SExpList list = new SExpList();

		while (pos < stream.length) {
			x = stream[pos++];
			if (x == ')') {
				return list;
			} else {
				pos--;
				list.getExpressions().add(read());
			}
		}

		if (x != ')') {
			throw new IllegalStateException(
					"unexpected end of file when parsing list");
		}

		return list;
	}

	/**
	 * Parses a string.
	 * 
	 * @return a SExprAtom
	 */
	private SExpAtom readString() {
		StringBuilder builder = new StringBuilder();
		if (pos < stream.length) {
			char c = stream[pos++];
			while (pos < stream.length && c != '"') {
				builder.append(c);
				c = stream[pos++];
				if (c == '\\') {
					if (pos < stream.length) {
						c = stream[pos++];
					} else {
						throw new IllegalStateException(
								"unexpected back-slash at the end of file");
					}
				}
			}

			if (c != '"') {
				throw new IllegalStateException(
						"unexpected end of file when parsing String");
			}
		}

		return new SExpString(builder.toString());
	}

	/**
	 * Reads a token starting with the given character.
	 * 
	 * @param y
	 *            a character starting the token
	 * @return an atomic s-expression
	 */
	private SExpAtom readToken(char y) {
		StringBuilder builder = new StringBuilder();
		builder.append(y);
		boolean keep = true;
		while (pos < stream.length && keep) {
			y = stream[pos++];
			switch (y) {
			case '\\':
				if (pos < stream.length) {
					char z = stream[pos++];
					builder.append(z);
					break;
				} else {
					throw new IllegalStateException(
							"unexpected back-slash at the end of file");
				}

			case '|':
				throw new UnsupportedOperationException(
						"multiple escape not yet implemented");

			case '"':
			case '\'':
			case '(':
			case ')':
			case ',':
			case ';':
			case '`':
				// terminating macro character => unread char
				pos--;
				keep = false;
				break;

			case ' ':
			case '\t':
			case '\r':
			case '\n':
				keep = false;
				break;

			default:
				if (isConstituent(y)) {
					builder.append(y);
				} else {
					throw new IllegalStateException("unexpected character " + y);
				}
			}
		}

		return new SExpSymbol(builder.toString());
	}

	/**
	 * Reads a token starting with a single escape.
	 * 
	 * @return an atomic s-expression
	 */
	private SExpAtom readTokenSingleEscape() {
		if (pos < stream.length) {
			char y = stream[pos++];
			return readToken(y);
		} else {
			throw new IllegalStateException(
					"unexpected back-slash at the end of file");
		}
	}

	/**
	 * Skips characters until \r or \n is encountered.
	 */
	private void skipComment() {
		if (pos < stream.length) {
			char c = stream[pos++];
			while (pos < stream.length && c != '\r' && c != '\n') {
				c = stream[pos++];
			}
		}
	}

}
