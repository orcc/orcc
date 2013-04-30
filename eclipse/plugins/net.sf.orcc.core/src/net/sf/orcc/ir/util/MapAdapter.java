/*
 * Copyright (c) 2011-2013, IETR/INSA of Rennes, Synflow SAS
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
package net.sf.orcc.ir.util;

import java.util.List;
import java.util.Map;

import net.sf.orcc.df.Actor;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EReference;

/**
 * This class defines an adapter that maintains a map of variables from a list
 * of variables.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class MapAdapter extends AdapterImpl {

	private final Map<String, Var> map;

	private final EReference reference;

	public MapAdapter(Map<String, Var> map, EReference reference) {
		this.map = map;
		this.reference = reference;
	}

	private void add(Object object) {
		Var var = (Var) object;
		map.put(var.getIndexedName(), var);
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return (type == Actor.class || type == Procedure.class);
	}

	@Override
	public void notifyChanged(Notification notification) {
		if (reference != notification.getFeature()) {
			return;
		}

		switch (notification.getEventType()) {
		case Notification.ADD:
			add(notification.getNewValue());
			break;

		case Notification.ADD_MANY: {
			List<?> list = (List<?>) notification.getNewValue();
			for (Object object : list) {
				add(object);
			}
			break;
		}

		case Notification.MOVE: {
			remove(notification.getOldValue());
			add(notification.getNewValue());
			break;
		}

		case Notification.REMOVE:
			remove(notification.getOldValue());
			break;

		case Notification.REMOVE_MANY: {
			List<?> list = (List<?>) notification.getOldValue();
			for (Object object : list) {
				remove(object);
			}
			break;
		}
		}
	}

	private void remove(Object object) {
		map.remove(((Var) object).getIndexedName());
	}

}
