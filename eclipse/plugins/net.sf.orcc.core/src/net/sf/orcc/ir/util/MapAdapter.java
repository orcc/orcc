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
package net.sf.orcc.ir.util;

import static net.sf.orcc.ir.IrPackage.eINSTANCE;

import java.util.List;
import java.util.Map;

import net.sf.orcc.df.Actor;
import net.sf.orcc.df.DfPackage;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.State;
import net.sf.orcc.df.Transitions;
import net.sf.orcc.df.impl.ActorImpl;
import net.sf.orcc.df.impl.FSMImpl;
import net.sf.orcc.ir.Procedure;
import net.sf.orcc.ir.Var;
import net.sf.orcc.ir.impl.ProcedureImpl;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;

/**
 * This class defines an adapter that maintains a map of variables from a list
 * of variables.
 * 
 * @author Matthieu Wipliez
 * 
 */
public class MapAdapter implements Adapter {

	private Notifier target;

	@SuppressWarnings("unchecked")
	private void add(Notification notification, Object object) {
		Map<? extends Object, ? extends Object> map;
		Object key;

		Object feature = notification.getFeature();
		if (feature == eINSTANCE.getProcedure_Locals()) {
			map = ((ProcedureImpl) target).getLocalsMap();
			key = ((Var) object).getIndexedName();
			((Map<Object, Object>) map).put(key, object);
		} else if (feature == DfPackage.eINSTANCE.getActor_Inputs()) {
			map = ((ActorImpl) target).getInputsMap();
			key = ((Port) object).getName();
			((Map<Object, Object>) map).put(key, object);
		} else if (feature == DfPackage.eINSTANCE.getActor_Outputs()) {
			map = ((ActorImpl) target).getOutputsMap();
			key = ((Port) object).getName();
			((Map<Object, Object>) map).put(key, object);
		} else if (feature == DfPackage.eINSTANCE.getActor_Parameters()) {
			map = ((ActorImpl) target).getParametersMap();
			key = ((Var) object).getName();
			((Map<Object, Object>) map).put(key, object);
		} else if (feature == DfPackage.eINSTANCE.getActor_Procs()) {
			map = ((ActorImpl) target).getProceduresMap();
			key = ((Procedure) object).getName();
			((Map<Object, Object>) map).put(key, object);
		} else if (feature == DfPackage.eINSTANCE.getActor_StateVars()) {
			map = ((ActorImpl) target).getStateVariablesMap();
			key = ((Var) object).getName();
			((Map<Object, Object>) map).put(key, object);
		} else if (feature == DfPackage.eINSTANCE.getFSM_Transitions()) {
			map = ((FSMImpl) target).getTransitionsMap();
			key = ((Transitions) object).getSourceState();
			if (key != null) {
				((Map<Object, Object>) map).put(key, object);
			}
		}
	}

	@Override
	public Notifier getTarget() {
		return target;
	}

	@Override
	public boolean isAdapterForType(Object type) {
		return (type == Actor.class || type == Procedure.class);
	}

	@Override
	public void notifyChanged(Notification notification) {
		switch (notification.getEventType()) {
		case Notification.ADD:
			add(notification, notification.getNewValue());
			break;

		case Notification.ADD_MANY: {
			List<?> list = (List<?>) notification.getNewValue();
			for (Object object : list) {
				add(notification, object);
			}
			break;
		}

		case Notification.MOVE: {
			remove(notification, notification.getOldValue());
			add(notification, notification.getNewValue());
			break;
		}

		case Notification.REMOVE:
			remove(notification, notification.getOldValue());
			break;

		case Notification.REMOVE_MANY: {
			List<?> list = (List<?>) notification.getOldValue();
			for (Object object : list) {
				remove(notification, object);
			}
			break;
		}

		case Notification.SET:
			set(notification, notification.getNewValue());
			break;
		}
	}

	@SuppressWarnings("unchecked")
	private void remove(Notification notification, Object object) {
		Map<? extends Object, ? extends Object> map;
		Object key;

		Object feature = notification.getFeature();
		if (feature == eINSTANCE.getProcedure_Locals()) {
			map = ((ProcedureImpl) target).getLocalsMap();
			key = ((Var) object).getIndexedName();
			((Map<Object, Object>) map).remove(key);
		} else if (feature == DfPackage.eINSTANCE.getActor_Inputs()) {
			map = ((ActorImpl) target).getInputsMap();
			key = ((Port) object).getName();
			((Map<Object, Object>) map).remove(key);
		} else if (feature == DfPackage.eINSTANCE.getActor_Outputs()) {
			map = ((ActorImpl) target).getOutputsMap();
			key = ((Port) object).getName();
			((Map<Object, Object>) map).remove(key);
		} else if (feature == DfPackage.eINSTANCE.getActor_Parameters()) {
			map = ((ActorImpl) target).getParametersMap();
			key = ((Var) object).getName();
			((Map<Object, Object>) map).remove(key);
		} else if (feature == DfPackage.eINSTANCE.getActor_Procs()) {
			map = ((ActorImpl) target).getProceduresMap();
			key = ((Procedure) object).getName();
			((Map<Object, Object>) map).remove(key);
		} else if (feature == DfPackage.eINSTANCE.getActor_StateVars()) {
			map = ((ActorImpl) target).getStateVariablesMap();
			key = ((Var) object).getName();
			((Map<Object, Object>) map).remove(key);
		} else if (feature == DfPackage.eINSTANCE.getFSM_Transitions()) {
			map = ((FSMImpl) target).getTransitionsMap();
			key = ((Transitions) object).getSourceState();
			if (key != null) {
				((Map<Object, Object>) map).remove(key);
			}
		}
	}

	private void set(Notification notification, Object object) {
		Object feature = notification.getFeature();
		if (feature == DfPackage.eINSTANCE.getTransitions_SourceState()) {
			Transitions transitions = (Transitions) target;
			EObject cter = transitions.eContainer();
			if (cter instanceof FSMImpl) {
				Map<State, Transitions> map = ((FSMImpl) cter)
						.getTransitionsMap();
				map.put((State) object, transitions);
			}
		}
	}

	@Override
	public void setTarget(Notifier newTarget) {
		this.target = newTarget;
	}

}
