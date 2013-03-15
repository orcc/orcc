/*
 * Copyright (c) 2011, EPFL
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

package net.sf.orcc.simulators.runtime.impl;

import net.sf.orcc.util.OrccLogger;

public class GenericDisplay {
	/**
	 * display is disabled.
	 */
	public static final int DISPLAY_DISABLE = 0;

	/**
	 * display is enabled. 2 instead of 1 for historical reasons
	 */
	public static final int DISPLAY_ENABLE = 2;

	protected static String goldenReference = "";

	protected static int displayStatus = DISPLAY_ENABLE;

	public static String getGoldenReference() {
		return goldenReference;
	}

	public static void setGoldenReference(String fileName) {
		GenericDisplay.goldenReference = fileName;
	}

	public static void setDisplayEnabled() {
		displayStatus = DISPLAY_ENABLE;
		OrccLogger.debugln("Display has been enabled");
	}

	public static void setDisplayDisabled() {
		displayStatus = DISPLAY_DISABLE;
		OrccLogger.debugln("Display has been disabled");
	}
}
