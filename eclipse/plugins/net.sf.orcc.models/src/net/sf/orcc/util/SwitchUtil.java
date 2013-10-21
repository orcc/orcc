/*
 * Copyright (c) 2013, Synflow SAS
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
package net.sf.orcc.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.Switch;

/**
 * This class defines utility stuff for EMF-switch based code transformations.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class SwitchUtil {

	/**
	 * to use in cascading switch
	 */
	public static final Void CASCADE = null;

	/**
	 * to use for non-cascading switch;
	 */
	public static final Void DONE = new Void();

	/**
	 * Checks the given objects with the given EMF switch, and returns <code>true</code> as soon as
	 * the {@link Switch#doSwitch(EObject)} method returns true. Otherwise returns false. If an
	 * object is null, returns false.
	 * 
	 * @param emfSwitch
	 *            an EMF switch
	 * @param eObjects
	 *            a varargs array of objects
	 * @return a boolean
	 */
	public static boolean check(Switch<Boolean> emfSwitch, EObject... eObjects) {
		for (EObject eObject : eObjects) {
			if (doSwitchBoolean(emfSwitch, eObject)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks the given objects with the given EMF switch, and returns <code>true</code> as soon as
	 * the {@link Switch#doSwitch(EObject)} method returns true. Otherwise returns false. If an
	 * object is null, returns false.
	 * 
	 * @param emfSwitch
	 *            an EMF switch
	 * @param eObjects
	 *            an iterable of objects
	 * @return a boolean
	 */
	public static boolean check(Switch<Boolean> emfSwitch, Iterable<? extends EObject> eObjects) {
		for (EObject eObject : eObjects) {
			if (doSwitchBoolean(emfSwitch, eObject)) {
				return true;
			}
		}
		return false;
	}

	private static boolean doSwitchBoolean(Switch<Boolean> emfSwitch, EObject eObject) {
		if (eObject == null) {
			return false;
		}
		return emfSwitch.doSwitch(eObject);
	}

	private static void doSwithVoid(Switch<Void> emfSwitch, EObject eObject) {
		if (eObject != null) {
			emfSwitch.doSwitch(eObject);
		}
	}

	public static Void visit(Switch<Void> emfSwitch, EObject... eObjects) {
		for (EObject eObject : eObjects) {
			doSwithVoid(emfSwitch, eObject);
		}
		return DONE;
	}

	public static Void visit(Switch<Void> emfSwitch, Iterable<? extends EObject> eObjects) {
		for (EObject eObject : eObjects) {
			doSwithVoid(emfSwitch, eObject);
		}
		return DONE;
	}

}
