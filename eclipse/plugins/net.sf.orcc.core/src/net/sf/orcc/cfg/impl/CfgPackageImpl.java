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
package net.sf.orcc.cfg.impl;

import net.sf.dftools.graph.GraphPackage;

import net.sf.dftools.util.UtilPackage;

import net.sf.orcc.cfg.Cfg;
import net.sf.orcc.cfg.CfgFactory;
import net.sf.orcc.cfg.CfgPackage;

import net.sf.orcc.df.DfPackage;

import net.sf.orcc.df.impl.DfPackageImpl;

import net.sf.orcc.ir.IrPackage;

import net.sf.orcc.ir.impl.IrPackageImpl;

import net.sf.orcc.moc.MocPackage;

import net.sf.orcc.moc.impl.MocPackageImpl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class CfgPackageImpl extends EPackageImpl implements CfgPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass cfgEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see net.sf.orcc.cfg.CfgPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private CfgPackageImpl() {
		super(eNS_URI, CfgFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link CfgPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static CfgPackage init() {
		if (isInited)
			return (CfgPackage) EPackage.Registry.INSTANCE
					.getEPackage(CfgPackage.eNS_URI);

		// Obtain or create and register package
		CfgPackageImpl theCfgPackage = (CfgPackageImpl) (EPackage.Registry.INSTANCE
				.get(eNS_URI) instanceof CfgPackageImpl ? EPackage.Registry.INSTANCE
				.get(eNS_URI) : new CfgPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		GraphPackage.eINSTANCE.eClass();
		UtilPackage.eINSTANCE.eClass();

		// Obtain or create and register interdependencies
		DfPackageImpl theDfPackage = (DfPackageImpl) (EPackage.Registry.INSTANCE
				.getEPackage(DfPackage.eNS_URI) instanceof DfPackageImpl ? EPackage.Registry.INSTANCE
				.getEPackage(DfPackage.eNS_URI) : DfPackage.eINSTANCE);
		IrPackageImpl theIrPackage = (IrPackageImpl) (EPackage.Registry.INSTANCE
				.getEPackage(IrPackage.eNS_URI) instanceof IrPackageImpl ? EPackage.Registry.INSTANCE
				.getEPackage(IrPackage.eNS_URI) : IrPackage.eINSTANCE);
		MocPackageImpl theMocPackage = (MocPackageImpl) (EPackage.Registry.INSTANCE
				.getEPackage(MocPackage.eNS_URI) instanceof MocPackageImpl ? EPackage.Registry.INSTANCE
				.getEPackage(MocPackage.eNS_URI) : MocPackage.eINSTANCE);

		// Create package meta-data objects
		theCfgPackage.createPackageContents();
		theDfPackage.createPackageContents();
		theIrPackage.createPackageContents();
		theMocPackage.createPackageContents();

		// Initialize created meta-data
		theCfgPackage.initializePackageContents();
		theDfPackage.initializePackageContents();
		theIrPackage.initializePackageContents();
		theMocPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theCfgPackage.freeze();

		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(CfgPackage.eNS_URI, theCfgPackage);
		return theCfgPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCfg() {
		return cfgEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCfg_Entry() {
		return (EReference) cfgEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCfg_Exit() {
		return (EReference) cfgEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CfgFactory getCfgFactory() {
		return (CfgFactory) getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated)
			return;
		isCreated = true;

		// Create classes and their features
		cfgEClass = createEClass(CFG);
		createEReference(cfgEClass, CFG__ENTRY);
		createEReference(cfgEClass, CFG__EXIT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized)
			return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		GraphPackage theGraphPackage = (GraphPackage) EPackage.Registry.INSTANCE
				.getEPackage(GraphPackage.eNS_URI);
		IrPackage theIrPackage = (IrPackage) EPackage.Registry.INSTANCE
				.getEPackage(IrPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		cfgEClass.getESuperTypes().add(theGraphPackage.getGraph());

		// Initialize classes and features; add operations and parameters
		initEClass(cfgEClass, Cfg.class, "Cfg", !IS_ABSTRACT, !IS_INTERFACE,
				IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCfg_Entry(), theIrPackage.getNode(), null, "entry",
				null, 0, 1, Cfg.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCfg_Exit(), theIrPackage.getNode(), null, "exit",
				null, 0, 1, Cfg.class, !IS_TRANSIENT, !IS_VOLATILE,
				IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES,
				!IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //CfgPackageImpl
