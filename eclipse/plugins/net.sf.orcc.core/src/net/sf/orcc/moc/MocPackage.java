/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.moc;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see net.sf.orcc.moc.MocFactory
 * @model kind="package"
 * @generated
 */
public interface MocPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "moc";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://orcc.sf.net/model/2011/MoC";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "net.sf.orcc.moc";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	MocPackage eINSTANCE = net.sf.orcc.moc.impl.MocPackageImpl.init();

	/**
	 * The meta object id for the '{@link net.sf.orcc.moc.impl.MoCImpl <em>Mo C</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.moc.impl.MoCImpl
	 * @see net.sf.orcc.moc.impl.MocPackageImpl#getMoC()
	 * @generated
	 */
	int MO_C = 0;

	/**
	 * The number of structural features of the '<em>Mo C</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MO_C_FEATURE_COUNT = 0;

	/**
	 * The meta object id for the '{@link net.sf.orcc.moc.impl.CSDFMoCImpl <em>CSDF Mo C</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.moc.impl.CSDFMoCImpl
	 * @see net.sf.orcc.moc.impl.MocPackageImpl#getCSDFMoC()
	 * @generated
	 */
	int CSDF_MO_C = 1;

	/**
	 * The feature id for the '<em><b>Input Pattern</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CSDF_MO_C__INPUT_PATTERN = MO_C_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Number Of Phases</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CSDF_MO_C__NUMBER_OF_PHASES = MO_C_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Output Pattern</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CSDF_MO_C__OUTPUT_PATTERN = MO_C_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Invocations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CSDF_MO_C__INVOCATIONS = MO_C_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Delay Pattern</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CSDF_MO_C__DELAY_PATTERN = MO_C_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>CSDF Mo C</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CSDF_MO_C_FEATURE_COUNT = MO_C_FEATURE_COUNT + 5;

	/**
	 * The meta object id for the '{@link net.sf.orcc.moc.impl.DPNMoCImpl <em>DPN Mo C</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.moc.impl.DPNMoCImpl
	 * @see net.sf.orcc.moc.impl.MocPackageImpl#getDPNMoC()
	 * @generated
	 */
	int DPN_MO_C = 2;

	/**
	 * The number of structural features of the '<em>DPN Mo C</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DPN_MO_C_FEATURE_COUNT = MO_C_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link net.sf.orcc.moc.impl.KPNMoCImpl <em>KPN Mo C</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.moc.impl.KPNMoCImpl
	 * @see net.sf.orcc.moc.impl.MocPackageImpl#getKPNMoC()
	 * @generated
	 */
	int KPN_MO_C = 3;

	/**
	 * The number of structural features of the '<em>KPN Mo C</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KPN_MO_C_FEATURE_COUNT = MO_C_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link net.sf.orcc.moc.impl.QSDFMoCImpl <em>QSDF Mo C</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.moc.impl.QSDFMoCImpl
	 * @see net.sf.orcc.moc.impl.MocPackageImpl#getQSDFMoC()
	 * @generated
	 */
	int QSDF_MO_C = 4;

	/**
	 * The number of structural features of the '<em>QSDF Mo C</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int QSDF_MO_C_FEATURE_COUNT = MO_C_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link net.sf.orcc.moc.impl.SDFMoCImpl <em>SDF Mo C</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.moc.impl.SDFMoCImpl
	 * @see net.sf.orcc.moc.impl.MocPackageImpl#getSDFMoC()
	 * @generated
	 */
	int SDF_MO_C = 5;

	/**
	 * The feature id for the '<em><b>Input Pattern</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SDF_MO_C__INPUT_PATTERN = CSDF_MO_C__INPUT_PATTERN;

	/**
	 * The feature id for the '<em><b>Number Of Phases</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SDF_MO_C__NUMBER_OF_PHASES = CSDF_MO_C__NUMBER_OF_PHASES;

	/**
	 * The feature id for the '<em><b>Output Pattern</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SDF_MO_C__OUTPUT_PATTERN = CSDF_MO_C__OUTPUT_PATTERN;

	/**
	 * The feature id for the '<em><b>Invocations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SDF_MO_C__INVOCATIONS = CSDF_MO_C__INVOCATIONS;

	/**
	 * The feature id for the '<em><b>Delay Pattern</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SDF_MO_C__DELAY_PATTERN = CSDF_MO_C__DELAY_PATTERN;

	/**
	 * The number of structural features of the '<em>SDF Mo C</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SDF_MO_C_FEATURE_COUNT = CSDF_MO_C_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link net.sf.orcc.moc.impl.InvocationImpl <em>Invocation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see net.sf.orcc.moc.impl.InvocationImpl
	 * @see net.sf.orcc.moc.impl.MocPackageImpl#getInvocation()
	 * @generated
	 */
	int INVOCATION = 6;

	/**
	 * The feature id for the '<em><b>Action</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INVOCATION__ACTION = 0;

	/**
	 * The number of structural features of the '<em>Invocation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INVOCATION_FEATURE_COUNT = 1;

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.moc.MoC <em>Mo C</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Mo C</em>'.
	 * @see net.sf.orcc.moc.MoC
	 * @generated
	 */
	EClass getMoC();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.moc.CSDFMoC <em>CSDF Mo C</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>CSDF Mo C</em>'.
	 * @see net.sf.orcc.moc.CSDFMoC
	 * @generated
	 */
	EClass getCSDFMoC();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.moc.CSDFMoC#getInputPattern <em>Input Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Input Pattern</em>'.
	 * @see net.sf.orcc.moc.CSDFMoC#getInputPattern()
	 * @see #getCSDFMoC()
	 * @generated
	 */
	EReference getCSDFMoC_InputPattern();

	/**
	 * Returns the meta object for the attribute '{@link net.sf.orcc.moc.CSDFMoC#getNumberOfPhases <em>Number Of Phases</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Number Of Phases</em>'.
	 * @see net.sf.orcc.moc.CSDFMoC#getNumberOfPhases()
	 * @see #getCSDFMoC()
	 * @generated
	 */
	EAttribute getCSDFMoC_NumberOfPhases();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.moc.CSDFMoC#getOutputPattern <em>Output Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Output Pattern</em>'.
	 * @see net.sf.orcc.moc.CSDFMoC#getOutputPattern()
	 * @see #getCSDFMoC()
	 * @generated
	 */
	EReference getCSDFMoC_OutputPattern();

	/**
	 * Returns the meta object for the containment reference list '{@link net.sf.orcc.moc.CSDFMoC#getInvocations <em>Invocations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Invocations</em>'.
	 * @see net.sf.orcc.moc.CSDFMoC#getInvocations()
	 * @see #getCSDFMoC()
	 * @generated
	 */
	EReference getCSDFMoC_Invocations();

	/**
	 * Returns the meta object for the containment reference '{@link net.sf.orcc.moc.CSDFMoC#getDelayPattern <em>Delay Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Delay Pattern</em>'.
	 * @see net.sf.orcc.moc.CSDFMoC#getDelayPattern()
	 * @see #getCSDFMoC()
	 * @generated
	 */
	EReference getCSDFMoC_DelayPattern();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.moc.DPNMoC <em>DPN Mo C</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>DPN Mo C</em>'.
	 * @see net.sf.orcc.moc.DPNMoC
	 * @generated
	 */
	EClass getDPNMoC();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.moc.KPNMoC <em>KPN Mo C</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>KPN Mo C</em>'.
	 * @see net.sf.orcc.moc.KPNMoC
	 * @generated
	 */
	EClass getKPNMoC();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.moc.QSDFMoC <em>QSDF Mo C</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>QSDF Mo C</em>'.
	 * @see net.sf.orcc.moc.QSDFMoC
	 * @generated
	 */
	EClass getQSDFMoC();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.moc.SDFMoC <em>SDF Mo C</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>SDF Mo C</em>'.
	 * @see net.sf.orcc.moc.SDFMoC
	 * @generated
	 */
	EClass getSDFMoC();

	/**
	 * Returns the meta object for class '{@link net.sf.orcc.moc.Invocation <em>Invocation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Invocation</em>'.
	 * @see net.sf.orcc.moc.Invocation
	 * @generated
	 */
	EClass getInvocation();

	/**
	 * Returns the meta object for the reference '{@link net.sf.orcc.moc.Invocation#getAction <em>Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Action</em>'.
	 * @see net.sf.orcc.moc.Invocation#getAction()
	 * @see #getInvocation()
	 * @generated
	 */
	EReference getInvocation_Action();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	MocFactory getMocFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link net.sf.orcc.moc.impl.MoCImpl <em>Mo C</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.moc.impl.MoCImpl
		 * @see net.sf.orcc.moc.impl.MocPackageImpl#getMoC()
		 * @generated
		 */
		EClass MO_C = eINSTANCE.getMoC();
		/**
		 * The meta object literal for the '{@link net.sf.orcc.moc.impl.CSDFMoCImpl <em>CSDF Mo C</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.moc.impl.CSDFMoCImpl
		 * @see net.sf.orcc.moc.impl.MocPackageImpl#getCSDFMoC()
		 * @generated
		 */
		EClass CSDF_MO_C = eINSTANCE.getCSDFMoC();
		/**
		 * The meta object literal for the '<em><b>Input Pattern</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CSDF_MO_C__INPUT_PATTERN = eINSTANCE
				.getCSDFMoC_InputPattern();
		/**
		 * The meta object literal for the '<em><b>Number Of Phases</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CSDF_MO_C__NUMBER_OF_PHASES = eINSTANCE
				.getCSDFMoC_NumberOfPhases();
		/**
		 * The meta object literal for the '<em><b>Output Pattern</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CSDF_MO_C__OUTPUT_PATTERN = eINSTANCE
				.getCSDFMoC_OutputPattern();
		/**
		 * The meta object literal for the '<em><b>Invocations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CSDF_MO_C__INVOCATIONS = eINSTANCE.getCSDFMoC_Invocations();
		/**
		 * The meta object literal for the '<em><b>Delay Pattern</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CSDF_MO_C__DELAY_PATTERN = eINSTANCE
				.getCSDFMoC_DelayPattern();
		/**
		 * The meta object literal for the '{@link net.sf.orcc.moc.impl.DPNMoCImpl <em>DPN Mo C</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.moc.impl.DPNMoCImpl
		 * @see net.sf.orcc.moc.impl.MocPackageImpl#getDPNMoC()
		 * @generated
		 */
		EClass DPN_MO_C = eINSTANCE.getDPNMoC();
		/**
		 * The meta object literal for the '{@link net.sf.orcc.moc.impl.KPNMoCImpl <em>KPN Mo C</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.moc.impl.KPNMoCImpl
		 * @see net.sf.orcc.moc.impl.MocPackageImpl#getKPNMoC()
		 * @generated
		 */
		EClass KPN_MO_C = eINSTANCE.getKPNMoC();
		/**
		 * The meta object literal for the '{@link net.sf.orcc.moc.impl.QSDFMoCImpl <em>QSDF Mo C</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.moc.impl.QSDFMoCImpl
		 * @see net.sf.orcc.moc.impl.MocPackageImpl#getQSDFMoC()
		 * @generated
		 */
		EClass QSDF_MO_C = eINSTANCE.getQSDFMoC();
		/**
		 * The meta object literal for the '{@link net.sf.orcc.moc.impl.SDFMoCImpl <em>SDF Mo C</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.moc.impl.SDFMoCImpl
		 * @see net.sf.orcc.moc.impl.MocPackageImpl#getSDFMoC()
		 * @generated
		 */
		EClass SDF_MO_C = eINSTANCE.getSDFMoC();
		/**
		 * The meta object literal for the '{@link net.sf.orcc.moc.impl.InvocationImpl <em>Invocation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see net.sf.orcc.moc.impl.InvocationImpl
		 * @see net.sf.orcc.moc.impl.MocPackageImpl#getInvocation()
		 * @generated
		 */
		EClass INVOCATION = eINSTANCE.getInvocation();
		/**
		 * The meta object literal for the '<em><b>Action</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INVOCATION__ACTION = eINSTANCE.getInvocation_Action();

	}

} //MocPackage
