/*
 * Copyright (c) 2010-2011, IETR/INSA of Rennes
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

import net.sf.orcc.cal.generator.CalOutputConfigurationProvider;
import net.sf.orcc.cal.parser.impl.PatchedPartialParsingHelper;
import net.sf.orcc.cal.services.CalLinkingService;
import net.sf.orcc.cal.services.CalQualifiedNameProvider;

import org.eclipse.xtext.conversion.IValueConverterService;
import org.eclipse.xtext.generator.IOutputConfigurationProvider;
import org.eclipse.xtext.linking.ILinkingService;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.parser.antlr.IPartialParsingHelper;
import org.eclipse.xtext.parser.antlr.ISyntaxErrorMessageProvider;

import com.google.inject.Binder;
import com.google.inject.Singleton;

/**
 * Use this class to register components to be used within the IDE.
 */
public class CalRuntimeModule extends net.sf.orcc.cal.AbstractCalRuntimeModule {

	@Override
	public void configure(Binder binder) {
		super.configure(binder);
		binder.bind(IOutputConfigurationProvider.class)
				.to(CalOutputConfigurationProvider.class).in(Singleton.class);
	}

	@Override
	public Class<? extends ILinkingService> bindILinkingService() {
		return CalLinkingService.class;
	}

	@Override
	public Class<? extends IQualifiedNameProvider> bindIQualifiedNameProvider() {
		return CalQualifiedNameProvider.class;
	}

	public Class<? extends ISyntaxErrorMessageProvider> bindISyntaxErrorMessageProvider() {
		return CalSyntaxErrorMessageProvider.class;
	}

	@Override
	public Class<? extends IValueConverterService> bindIValueConverterService() {
		return CalValueConverter.class;
	}

	/*
	 * [2013-09-13 - alorence] This binding resolve an issue With Xtext 2.4.x.
	 * It should be removed when the bug is fixed (planned for 2.5 release). See
	 * https://bugs.eclipse.org/bugs/show_bug.cgi?id=416913 for more information
	 */
	@Override
	public Class<? extends IPartialParsingHelper> bindIPartialParserHelper() {
		return PatchedPartialParsingHelper.class;
	}
}
