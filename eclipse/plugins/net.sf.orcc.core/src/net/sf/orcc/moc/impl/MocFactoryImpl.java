/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.moc.impl;

import net.sf.orcc.df.Action;
import net.sf.orcc.df.DfFactory;
import net.sf.orcc.moc.CSDFMoC;
import net.sf.orcc.moc.DPNMoC;
import net.sf.orcc.moc.Invocation;
import net.sf.orcc.moc.KPNMoC;
import net.sf.orcc.moc.MocFactory;
import net.sf.orcc.moc.MocPackage;
import net.sf.orcc.moc.QSDFMoC;
import net.sf.orcc.moc.SDFMoC;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!--
 * end-user-doc -->
 * @generated
 */
public class MocFactoryImpl extends EFactoryImpl implements MocFactory {

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static MocPackage getPackage() {
		return MocPackage.eINSTANCE;
	}

	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	public static MocFactory init() {
		try {
			MocFactory theMocFactory = (MocFactory) EPackage.Registry.INSTANCE
					.getEFactory("http://orcc.sf.net/model/2011/MoC");
			if (theMocFactory != null) {
				return theMocFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new MocFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	public MocFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case MocPackage.CSDF_MO_C:
			return createCSDFMoC();
		case MocPackage.DPN_MO_C:
			return createDPNMoC();
		case MocPackage.KPN_MO_C:
			return createKPNMoC();
		case MocPackage.QSDF_MO_C:
			return createQSDFMoC();
		case MocPackage.SDF_MO_C:
			return createSDFMoC();
		case MocPackage.INVOCATION:
			return createInvocation();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 */
	public CSDFMoC createCSDFMoC() {
		CSDFMoCImpl csdfMoC = new CSDFMoCImpl();
		csdfMoC.setInputPattern(DfFactory.eINSTANCE.createPattern());
		csdfMoC.setOutputPattern(DfFactory.eINSTANCE.createPattern());
		csdfMoC.setDelayPattern(DfFactory.eINSTANCE.createPattern());
		return csdfMoC;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public DPNMoC createDPNMoC() {
		DPNMoCImpl dpnMoC = new DPNMoCImpl();
		return dpnMoC;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public Invocation createInvocation() {
		InvocationImpl invocation = new InvocationImpl();
		return invocation;
	}

	@Override
	public Invocation createInvocation(Action action) {
		InvocationImpl invocation = new InvocationImpl();
		invocation.setAction(action);
		return invocation;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public KPNMoC createKPNMoC() {
		KPNMoCImpl kpnMoC = new KPNMoCImpl();
		return kpnMoC;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public QSDFMoC createQSDFMoC() {
		QSDFMoCImpl qsdfMoC = new QSDFMoCImpl();
		return qsdfMoC;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 */
	public SDFMoC createSDFMoC() {
		SDFMoCImpl sdfMoC = new SDFMoCImpl();
		sdfMoC.setInputPattern(DfFactory.eINSTANCE.createPattern());
		sdfMoC.setOutputPattern(DfFactory.eINSTANCE.createPattern());
		sdfMoC.setDelayPattern(DfFactory.eINSTANCE.createPattern());
		sdfMoC.setNumberOfPhases(1);
		return sdfMoC;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public MocPackage getMocPackage() {
		return (MocPackage) getEPackage();
	}

} // MocFactoryImpl
