/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.moc.util;

import net.sf.orcc.moc.CSDFMoC;
import net.sf.orcc.moc.DPNMoC;
import net.sf.orcc.moc.Invocation;
import net.sf.orcc.moc.KPNMoC;
import net.sf.orcc.moc.MoC;
import net.sf.orcc.moc.MocPackage;
import net.sf.orcc.moc.QSDFMoC;
import net.sf.orcc.moc.SDFMoC;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see net.sf.orcc.moc.MocPackage
 * @generated
 */
public class MocAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static MocPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MocAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = MocPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject) object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MocSwitch<Adapter> modelSwitch = new MocSwitch<Adapter>() {
		@Override
		public Adapter caseMoC(MoC object) {
			return createMoCAdapter();
		}

		@Override
		public Adapter caseCSDFMoC(CSDFMoC object) {
			return createCSDFMoCAdapter();
		}

		@Override
		public Adapter caseDPNMoC(DPNMoC object) {
			return createDPNMoCAdapter();
		}

		@Override
		public Adapter caseKPNMoC(KPNMoC object) {
			return createKPNMoCAdapter();
		}

		@Override
		public Adapter caseQSDFMoC(QSDFMoC object) {
			return createQSDFMoCAdapter();
		}

		@Override
		public Adapter caseSDFMoC(SDFMoC object) {
			return createSDFMoCAdapter();
		}

		@Override
		public Adapter caseInvocation(Invocation object) {
			return createInvocationAdapter();
		}

		@Override
		public Adapter defaultCase(EObject object) {
			return createEObjectAdapter();
		}
	};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject) target);
	}

	/**
	 * Creates a new adapter for an object of class '{@link net.sf.orcc.moc.MoC <em>Mo C</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see net.sf.orcc.moc.MoC
	 * @generated
	 */
	public Adapter createMoCAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link net.sf.orcc.moc.CSDFMoC <em>CSDF Mo C</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see net.sf.orcc.moc.CSDFMoC
	 * @generated
	 */
	public Adapter createCSDFMoCAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link net.sf.orcc.moc.DPNMoC <em>DPN Mo C</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see net.sf.orcc.moc.DPNMoC
	 * @generated
	 */
	public Adapter createDPNMoCAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link net.sf.orcc.moc.KPNMoC <em>KPN Mo C</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see net.sf.orcc.moc.KPNMoC
	 * @generated
	 */
	public Adapter createKPNMoCAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link net.sf.orcc.moc.QSDFMoC <em>QSDF Mo C</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see net.sf.orcc.moc.QSDFMoC
	 * @generated
	 */
	public Adapter createQSDFMoCAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link net.sf.orcc.moc.SDFMoC <em>SDF Mo C</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see net.sf.orcc.moc.SDFMoC
	 * @generated
	 */
	public Adapter createSDFMoCAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link net.sf.orcc.moc.Invocation <em>Invocation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see net.sf.orcc.moc.Invocation
	 * @generated
	 */
	public Adapter createInvocationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //MocAdapterFactory
