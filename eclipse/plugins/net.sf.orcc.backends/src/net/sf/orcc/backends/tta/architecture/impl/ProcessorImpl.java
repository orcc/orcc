/**
 * <copyright>
 * Copyright (c) 2009-2012, IETR/INSA of Rennes
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
 * </copyright>
 */
package net.sf.orcc.backends.tta.architecture.impl;

import java.util.Collection;

import net.sf.orcc.backends.tta.architecture.AddressSpace;
import net.sf.orcc.backends.tta.architecture.ArchitecturePackage;
import net.sf.orcc.backends.tta.architecture.Bridge;
import net.sf.orcc.backends.tta.architecture.Bus;
import net.sf.orcc.backends.tta.architecture.FunctionUnit;
import net.sf.orcc.backends.tta.architecture.GlobalControlUnit;
import net.sf.orcc.backends.tta.architecture.Implementation;
import net.sf.orcc.backends.tta.architecture.Processor;
import net.sf.orcc.backends.tta.architecture.ProcessorConfiguration;
import net.sf.orcc.backends.tta.architecture.RegisterFile;
import net.sf.orcc.backends.tta.architecture.Socket;
import net.sf.orcc.df.Instance;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Processor</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.impl.ProcessorImpl#getGcu <em>Gcu</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.impl.ProcessorImpl#getBuses <em>Buses</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.impl.ProcessorImpl#getBridges <em>Bridges</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.impl.ProcessorImpl#getSockets <em>Sockets</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.impl.ProcessorImpl#getFunctionUnits <em>Function Units</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.impl.ProcessorImpl#getRegisterFiles <em>Register Files</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.impl.ProcessorImpl#getProgram <em>Program</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.impl.ProcessorImpl#getData <em>Data</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.impl.ProcessorImpl#getHardwareDatabase <em>Hardware Database</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.impl.ProcessorImpl#getMappedInstances <em>Mapped Instances</em>}</li>
 *   <li>{@link net.sf.orcc.backends.tta.architecture.impl.ProcessorImpl#getConfiguration <em>Configuration</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProcessorImpl extends ComponentImpl implements Processor {
	/**
	 * The cached value of the '{@link #getGcu() <em>Gcu</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getGcu()
	 * @generated
	 * @ordered
	 */
	protected GlobalControlUnit gcu;

	/**
	 * The cached value of the '{@link #getBuses() <em>Buses</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBuses()
	 * @generated
	 * @ordered
	 */
	protected EList<Bus> buses;

	@Override
	public boolean isProcessor() {
		return true;
	}

	/**
	 * The cached value of the '{@link #getBridges() <em>Bridges</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBridges()
	 * @generated
	 * @ordered
	 */
	protected EList<Bridge> bridges;

	/**
	 * The cached value of the '{@link #getSockets() <em>Sockets</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSockets()
	 * @generated
	 * @ordered
	 */
	protected EList<Socket> sockets;

	/**
	 * The cached value of the '{@link #getFunctionUnits() <em>Function Units</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFunctionUnits()
	 * @generated
	 * @ordered
	 */
	protected EList<FunctionUnit> functionUnits;

	/**
	 * The cached value of the '{@link #getRegisterFiles() <em>Register Files</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRegisterFiles()
	 * @generated
	 * @ordered
	 */
	protected EList<RegisterFile> registerFiles;

	/**
	 * The cached value of the '{@link #getProgram() <em>Program</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getProgram()
	 * @generated
	 * @ordered
	 */
	protected AddressSpace program;

	/**
	 * The cached value of the '{@link #getData() <em>Data</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getData()
	 * @generated
	 * @ordered
	 */
	protected AddressSpace data;

	/**
	 * The cached value of the '{@link #getHardwareDatabase() <em>Hardware Database</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getHardwareDatabase()
	 * @generated
	 * @ordered
	 */
	protected EList<Implementation> hardwareDatabase;

	/**
	 * The cached value of the '{@link #getMappedInstances() <em>Mapped Instances</em>}' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getMappedInstances()
	 * @generated
	 * @ordered
	 */
	protected EList<Instance> mappedInstances;

	/**
	 * The default value of the '{@link #getConfiguration() <em>Configuration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConfiguration()
	 * @generated
	 * @ordered
	 */
	protected static final ProcessorConfiguration CONFIGURATION_EDEFAULT = ProcessorConfiguration.STANDARD;

	/**
	 * The cached value of the '{@link #getConfiguration() <em>Configuration</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getConfiguration()
	 * @generated
	 * @ordered
	 */
	protected ProcessorConfiguration configuration = CONFIGURATION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProcessorImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ArchitecturePackage.Literals.PROCESSOR;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GlobalControlUnit getGcu() {
		return gcu;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetGcu(GlobalControlUnit newGcu,
			NotificationChain msgs) {
		GlobalControlUnit oldGcu = gcu;
		gcu = newGcu;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, ArchitecturePackage.PROCESSOR__GCU,
					oldGcu, newGcu);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGcu(GlobalControlUnit newGcu) {
		if (newGcu != gcu) {
			NotificationChain msgs = null;
			if (gcu != null)
				msgs = ((InternalEObject) gcu).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- ArchitecturePackage.PROCESSOR__GCU, null,
						msgs);
			if (newGcu != null)
				msgs = ((InternalEObject) newGcu).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- ArchitecturePackage.PROCESSOR__GCU, null,
						msgs);
			msgs = basicSetGcu(newGcu, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ArchitecturePackage.PROCESSOR__GCU, newGcu, newGcu));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Bus> getBuses() {
		if (buses == null) {
			buses = new EObjectContainmentEList<Bus>(Bus.class, this,
					ArchitecturePackage.PROCESSOR__BUSES);
		}
		return buses;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Bridge> getBridges() {
		if (bridges == null) {
			bridges = new EObjectContainmentEList<Bridge>(Bridge.class, this,
					ArchitecturePackage.PROCESSOR__BRIDGES);
		}
		return bridges;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Socket> getSockets() {
		if (sockets == null) {
			sockets = new EObjectContainmentEList<Socket>(Socket.class, this,
					ArchitecturePackage.PROCESSOR__SOCKETS);
		}
		return sockets;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<FunctionUnit> getFunctionUnits() {
		if (functionUnits == null) {
			functionUnits = new EObjectContainmentEList<FunctionUnit>(
					FunctionUnit.class, this,
					ArchitecturePackage.PROCESSOR__FUNCTION_UNITS);
		}
		return functionUnits;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<RegisterFile> getRegisterFiles() {
		if (registerFiles == null) {
			registerFiles = new EObjectContainmentEList<RegisterFile>(
					RegisterFile.class, this,
					ArchitecturePackage.PROCESSOR__REGISTER_FILES);
		}
		return registerFiles;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AddressSpace getProgram() {
		return program;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProgram(AddressSpace newProgram,
			NotificationChain msgs) {
		AddressSpace oldProgram = program;
		program = newProgram;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, ArchitecturePackage.PROCESSOR__PROGRAM,
					oldProgram, newProgram);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProgram(AddressSpace newProgram) {
		if (newProgram != program) {
			NotificationChain msgs = null;
			if (program != null)
				msgs = ((InternalEObject) program).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- ArchitecturePackage.PROCESSOR__PROGRAM, null,
						msgs);
			if (newProgram != null)
				msgs = ((InternalEObject) newProgram).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- ArchitecturePackage.PROCESSOR__PROGRAM, null,
						msgs);
			msgs = basicSetProgram(newProgram, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ArchitecturePackage.PROCESSOR__PROGRAM, newProgram,
					newProgram));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AddressSpace getData() {
		return data;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetData(AddressSpace newData,
			NotificationChain msgs) {
		AddressSpace oldData = data;
		data = newData;
		if (eNotificationRequired()) {
			ENotificationImpl notification = new ENotificationImpl(this,
					Notification.SET, ArchitecturePackage.PROCESSOR__DATA,
					oldData, newData);
			if (msgs == null)
				msgs = notification;
			else
				msgs.add(notification);
		}
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setData(AddressSpace newData) {
		if (newData != data) {
			NotificationChain msgs = null;
			if (data != null)
				msgs = ((InternalEObject) data).eInverseRemove(this,
						EOPPOSITE_FEATURE_BASE
								- ArchitecturePackage.PROCESSOR__DATA, null,
						msgs);
			if (newData != null)
				msgs = ((InternalEObject) newData).eInverseAdd(this,
						EOPPOSITE_FEATURE_BASE
								- ArchitecturePackage.PROCESSOR__DATA, null,
						msgs);
			msgs = basicSetData(newData, msgs);
			if (msgs != null)
				msgs.dispatch();
		} else if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ArchitecturePackage.PROCESSOR__DATA, newData, newData));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Implementation> getHardwareDatabase() {
		if (hardwareDatabase == null) {
			hardwareDatabase = new EObjectContainmentEList<Implementation>(
					Implementation.class, this,
					ArchitecturePackage.PROCESSOR__HARDWARE_DATABASE);
		}
		return hardwareDatabase;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EList<Instance> getMappedInstances() {
		if (mappedInstances == null) {
			mappedInstances = new EObjectResolvingEList<Instance>(
					Instance.class, this,
					ArchitecturePackage.PROCESSOR__MAPPED_INSTANCES);
		}
		return mappedInstances;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessorConfiguration getConfiguration() {
		return configuration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setConfiguration(ProcessorConfiguration newConfiguration) {
		ProcessorConfiguration oldConfiguration = configuration;
		configuration = newConfiguration == null ? CONFIGURATION_EDEFAULT
				: newConfiguration;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET,
					ArchitecturePackage.PROCESSOR__CONFIGURATION,
					oldConfiguration, configuration));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd,
			int featureID, NotificationChain msgs) {
		switch (featureID) {
		case ArchitecturePackage.PROCESSOR__GCU:
			return basicSetGcu(null, msgs);
		case ArchitecturePackage.PROCESSOR__BUSES:
			return ((InternalEList<?>) getBuses()).basicRemove(otherEnd, msgs);
		case ArchitecturePackage.PROCESSOR__BRIDGES:
			return ((InternalEList<?>) getBridges())
					.basicRemove(otherEnd, msgs);
		case ArchitecturePackage.PROCESSOR__SOCKETS:
			return ((InternalEList<?>) getSockets())
					.basicRemove(otherEnd, msgs);
		case ArchitecturePackage.PROCESSOR__FUNCTION_UNITS:
			return ((InternalEList<?>) getFunctionUnits()).basicRemove(
					otherEnd, msgs);
		case ArchitecturePackage.PROCESSOR__REGISTER_FILES:
			return ((InternalEList<?>) getRegisterFiles()).basicRemove(
					otherEnd, msgs);
		case ArchitecturePackage.PROCESSOR__PROGRAM:
			return basicSetProgram(null, msgs);
		case ArchitecturePackage.PROCESSOR__DATA:
			return basicSetData(null, msgs);
		case ArchitecturePackage.PROCESSOR__HARDWARE_DATABASE:
			return ((InternalEList<?>) getHardwareDatabase()).basicRemove(
					otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
		case ArchitecturePackage.PROCESSOR__GCU:
			return getGcu();
		case ArchitecturePackage.PROCESSOR__BUSES:
			return getBuses();
		case ArchitecturePackage.PROCESSOR__BRIDGES:
			return getBridges();
		case ArchitecturePackage.PROCESSOR__SOCKETS:
			return getSockets();
		case ArchitecturePackage.PROCESSOR__FUNCTION_UNITS:
			return getFunctionUnits();
		case ArchitecturePackage.PROCESSOR__REGISTER_FILES:
			return getRegisterFiles();
		case ArchitecturePackage.PROCESSOR__PROGRAM:
			return getProgram();
		case ArchitecturePackage.PROCESSOR__DATA:
			return getData();
		case ArchitecturePackage.PROCESSOR__HARDWARE_DATABASE:
			return getHardwareDatabase();
		case ArchitecturePackage.PROCESSOR__MAPPED_INSTANCES:
			return getMappedInstances();
		case ArchitecturePackage.PROCESSOR__CONFIGURATION:
			return getConfiguration();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
		case ArchitecturePackage.PROCESSOR__GCU:
			setGcu((GlobalControlUnit) newValue);
			return;
		case ArchitecturePackage.PROCESSOR__BUSES:
			getBuses().clear();
			getBuses().addAll((Collection<? extends Bus>) newValue);
			return;
		case ArchitecturePackage.PROCESSOR__BRIDGES:
			getBridges().clear();
			getBridges().addAll((Collection<? extends Bridge>) newValue);
			return;
		case ArchitecturePackage.PROCESSOR__SOCKETS:
			getSockets().clear();
			getSockets().addAll((Collection<? extends Socket>) newValue);
			return;
		case ArchitecturePackage.PROCESSOR__FUNCTION_UNITS:
			getFunctionUnits().clear();
			getFunctionUnits().addAll(
					(Collection<? extends FunctionUnit>) newValue);
			return;
		case ArchitecturePackage.PROCESSOR__REGISTER_FILES:
			getRegisterFiles().clear();
			getRegisterFiles().addAll(
					(Collection<? extends RegisterFile>) newValue);
			return;
		case ArchitecturePackage.PROCESSOR__PROGRAM:
			setProgram((AddressSpace) newValue);
			return;
		case ArchitecturePackage.PROCESSOR__DATA:
			setData((AddressSpace) newValue);
			return;
		case ArchitecturePackage.PROCESSOR__HARDWARE_DATABASE:
			getHardwareDatabase().clear();
			getHardwareDatabase().addAll(
					(Collection<? extends Implementation>) newValue);
			return;
		case ArchitecturePackage.PROCESSOR__MAPPED_INSTANCES:
			getMappedInstances().clear();
			getMappedInstances().addAll(
					(Collection<? extends Instance>) newValue);
			return;
		case ArchitecturePackage.PROCESSOR__CONFIGURATION:
			setConfiguration((ProcessorConfiguration) newValue);
			return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
		case ArchitecturePackage.PROCESSOR__GCU:
			setGcu((GlobalControlUnit) null);
			return;
		case ArchitecturePackage.PROCESSOR__BUSES:
			getBuses().clear();
			return;
		case ArchitecturePackage.PROCESSOR__BRIDGES:
			getBridges().clear();
			return;
		case ArchitecturePackage.PROCESSOR__SOCKETS:
			getSockets().clear();
			return;
		case ArchitecturePackage.PROCESSOR__FUNCTION_UNITS:
			getFunctionUnits().clear();
			return;
		case ArchitecturePackage.PROCESSOR__REGISTER_FILES:
			getRegisterFiles().clear();
			return;
		case ArchitecturePackage.PROCESSOR__PROGRAM:
			setProgram((AddressSpace) null);
			return;
		case ArchitecturePackage.PROCESSOR__DATA:
			setData((AddressSpace) null);
			return;
		case ArchitecturePackage.PROCESSOR__HARDWARE_DATABASE:
			getHardwareDatabase().clear();
			return;
		case ArchitecturePackage.PROCESSOR__MAPPED_INSTANCES:
			getMappedInstances().clear();
			return;
		case ArchitecturePackage.PROCESSOR__CONFIGURATION:
			setConfiguration(CONFIGURATION_EDEFAULT);
			return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
		case ArchitecturePackage.PROCESSOR__GCU:
			return gcu != null;
		case ArchitecturePackage.PROCESSOR__BUSES:
			return buses != null && !buses.isEmpty();
		case ArchitecturePackage.PROCESSOR__BRIDGES:
			return bridges != null && !bridges.isEmpty();
		case ArchitecturePackage.PROCESSOR__SOCKETS:
			return sockets != null && !sockets.isEmpty();
		case ArchitecturePackage.PROCESSOR__FUNCTION_UNITS:
			return functionUnits != null && !functionUnits.isEmpty();
		case ArchitecturePackage.PROCESSOR__REGISTER_FILES:
			return registerFiles != null && !registerFiles.isEmpty();
		case ArchitecturePackage.PROCESSOR__PROGRAM:
			return program != null;
		case ArchitecturePackage.PROCESSOR__DATA:
			return data != null;
		case ArchitecturePackage.PROCESSOR__HARDWARE_DATABASE:
			return hardwareDatabase != null && !hardwareDatabase.isEmpty();
		case ArchitecturePackage.PROCESSOR__MAPPED_INSTANCES:
			return mappedInstances != null && !mappedInstances.isEmpty();
		case ArchitecturePackage.PROCESSOR__CONFIGURATION:
			return configuration != CONFIGURATION_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (configuration: ");
		result.append(configuration);
		result.append(')');
		return result.toString();
	}

} //ProcessorImpl
