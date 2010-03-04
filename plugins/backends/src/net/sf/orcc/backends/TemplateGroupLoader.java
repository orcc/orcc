/*
 * Copyright (c) 2009, IETR/INSA of Rennes
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
package net.sf.orcc.backends;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.antlr.stringtemplate.StringTemplateGroup;
import org.antlr.stringtemplate.StringTemplateGroupInterface;
import org.antlr.stringtemplate.StringTemplateGroupLoader;
import org.antlr.stringtemplate.language.DefaultTemplateLexer;

/**
 * An implementation of {@link StringTemplateGroupLoader} that loads a template
 * group by looking in the "templates" folder of this bundle.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class TemplateGroupLoader implements StringTemplateGroupLoader {

	public TemplateGroupLoader() {
		StringTemplateGroup.registerGroupLoader(this);
	}

	@Override
	public StringTemplateGroup loadGroup(String groupName) {
		return loadGroup(groupName, null);
	}

	@Override
	@SuppressWarnings("rawtypes")
	public StringTemplateGroup loadGroup(String groupName, Class templateLexer,
			StringTemplateGroup superGroup) {
		StringTemplateGroup group = null;

		try {
			String groupPath = "/net/sf/orcc/templates/" + groupName + ".stg";
			Class<?> clasz = this.getClass();
			InputStream is = clasz.getResourceAsStream(groupPath);

			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			group = new StringTemplateGroup(br, templateLexer, null, superGroup);
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return group;
	}

	@Override
	public StringTemplateGroup loadGroup(String groupName,
			StringTemplateGroup superGroup) {
		return loadGroup(groupName, DefaultTemplateLexer.class, superGroup);
	}

	@Override
	public StringTemplateGroupInterface loadInterface(String interfaceName) {
		throw new UnsupportedOperationException("loadInterface");
	}

}
