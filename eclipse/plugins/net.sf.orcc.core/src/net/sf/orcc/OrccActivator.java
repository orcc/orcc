/*
 * Copyright (c) 2009-2011, IETR/INSA of Rennes
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
package net.sf.orcc;

import static org.eclipse.core.runtime.Platform.getPreferencesService;

import org.eclipse.core.runtime.Plugin;
import org.eclipse.core.runtime.preferences.IEclipsePreferences;
import org.eclipse.core.runtime.preferences.IScopeContext;
import org.eclipse.core.runtime.preferences.InstanceScope;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class OrccActivator extends Plugin {

	// The shared instance
	private static OrccActivator plugin;

	// The plug-in ID
	public static final String PLUGIN_ID = "net.sf.orcc.core";

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static OrccActivator getDefault() {
		return plugin;
	}

	private IScopeContext[] contexts;

	/**
	 * Return the value stored in the preference store for the given key. If the
	 * key is not defined then return the specified default value.
	 * 
	 * @param key
	 *            the name of the preference
	 * @param defaultValue
	 *            the value to use if the preference is not defined
	 * @return the value of the preference or the given default value
	 */
	public String getPreference(String key, String defaultValue) {
		return getPreferencesService().getString(PLUGIN_ID, key, defaultValue,
				contexts);
	}

	/**
	 * Sets the value of the given key in the preference store.
	 * 
	 * @param key
	 *            key with which the specified value is to be associated
	 * @param value
	 *            value to be associated with the specified key
	 */
	public void setPreference(String key, String value) {
		IEclipsePreferences preferences = contexts[0]
				.getNode(OrccActivator.PLUGIN_ID);
		preferences.put(key, value);
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		contexts = new IScopeContext[] { InstanceScope.INSTANCE };
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

}
