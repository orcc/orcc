/*
 * Copyright (c) 2012, Synflow
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
package net.sf.orcc.df.util;

import java.util.List;

import net.sf.dftools.util.util.EcoreHelper;
import net.sf.orcc.df.Actor;
import net.sf.orcc.df.DfPackage;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * This class contains utility methods to manipulate Df models.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class DfUtil {

	/**
	 * Returns the list of input ports of the given entity.
	 * 
	 * @param entity
	 *            an entity
	 * @return the list of input ports of the given entity
	 */
	public static List<Port> getInputs(EObject entity) {
		if (entity instanceof Actor) {
			return ((Actor) entity).getInputs();
		} else if (entity instanceof Network) {
			return ((Network) entity).getInputs();
		} else {
			return EcoreHelper.getList(entity, "inputs");
		}
	}

	/**
	 * Returns the list of input ports of the entity referenced by the given
	 * instance.
	 * 
	 * @param instance
	 *            an instance
	 * @return the list of input ports of the given entity referenced by the
	 *         given instance
	 */
	public static List<Port> getInputs(Instance instance) {
		return getInputs(instance.getEntity());
	}

	/**
	 * Returns the list of output ports of the given entity.
	 * 
	 * @param entity
	 *            an entity
	 * @return the list of output ports of the given entity
	 */
	public static List<Port> getOutputs(EObject entity) {
		if (entity instanceof Actor) {
			return ((Actor) entity).getOutputs();
		} else if (entity instanceof Network) {
			return ((Network) entity).getOutputs();
		} else {
			return EcoreHelper.getList(entity, "outputs");
		}
	}

	/**
	 * Returns the list of output ports of the entity referenced by the given
	 * instance.
	 * 
	 * @param instance
	 *            an instance
	 * @return the list of output ports of the given entity referenced by the
	 *         given instance
	 */
	public static List<Port> getOutputs(Instance instance) {
		return getOutputs(instance.getEntity());
	}

	/**
	 * Returns the package of the given name, which is composed of all the
	 * components but the last one.
	 * 
	 * @param name
	 *            a qualified name
	 * @return the package of the given name
	 */
	public static String getPackage(String name) {
		int index = name.lastIndexOf('.');
		if (index == -1) {
			return "";
		} else {
			return name.substring(0, index);
		}
	}

	/**
	 * Returns the simple name of the given object. The simple name is the
	 * unqualified name, and is the string that appears after the last dot in
	 * the value of the object's "name" feature.
	 * 
	 * @param eObject
	 *            an object with a "name" feature
	 * @return the simple name of the object
	 */
	public static String getSimpleName(EObject eObject) {
		String name = EcoreHelper.getFeature(eObject, "name");
		return getSimpleName(name);
	}

	/**
	 * Returns the last component of the given qualified name.
	 * 
	 * @param name
	 *            a qualified name
	 * @return the last component of the given qualified name.
	 */
	public static String getSimpleName(String name) {
		int index = name.lastIndexOf('.');
		if (index != -1) {
			name = name.substring(index + 1);
		}
		return name;
	}

	/**
	 * Returns <code>true</code> if the given port is an input port of its
	 * container. This method works for actor, broadcast and network containers.
	 * 
	 * @param port
	 *            a port
	 * @return <code>true</code> if the given port is an input port
	 */
	public static boolean isInput(Port port) {
		EObject cter = port.eContainer();
		if (cter instanceof Network) {
			Network network = (Network) cter;
			return network.getInputs().contains(port);
		}
		EStructuralFeature feature = port.eContainingFeature();
		return feature == DfPackage.eINSTANCE.getActor_Inputs()
				|| feature == DfPackage.eINSTANCE.getBroadcast_Inputs();
	}

}
