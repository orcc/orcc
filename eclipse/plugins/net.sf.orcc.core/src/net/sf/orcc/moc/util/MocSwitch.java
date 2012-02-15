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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see net.sf.orcc.moc.MocPackage
 * @generated
 */
public class MocSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static MocPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MocSwitch() {
		if (modelPackage == null) {
			modelPackage = MocPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
		case MocPackage.MO_C: {
			MoC moC = (MoC) theEObject;
			T result = caseMoC(moC);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case MocPackage.CSDF_MO_C: {
			CSDFMoC csdfMoC = (CSDFMoC) theEObject;
			T result = caseCSDFMoC(csdfMoC);
			if (result == null)
				result = caseMoC(csdfMoC);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case MocPackage.DPN_MO_C: {
			DPNMoC dpnMoC = (DPNMoC) theEObject;
			T result = caseDPNMoC(dpnMoC);
			if (result == null)
				result = caseMoC(dpnMoC);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case MocPackage.KPN_MO_C: {
			KPNMoC kpnMoC = (KPNMoC) theEObject;
			T result = caseKPNMoC(kpnMoC);
			if (result == null)
				result = caseMoC(kpnMoC);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case MocPackage.QSDF_MO_C: {
			QSDFMoC qsdfMoC = (QSDFMoC) theEObject;
			T result = caseQSDFMoC(qsdfMoC);
			if (result == null)
				result = caseMoC(qsdfMoC);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case MocPackage.SDF_MO_C: {
			SDFMoC sdfMoC = (SDFMoC) theEObject;
			T result = caseSDFMoC(sdfMoC);
			if (result == null)
				result = caseCSDFMoC(sdfMoC);
			if (result == null)
				result = caseMoC(sdfMoC);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		case MocPackage.INVOCATION: {
			Invocation invocation = (Invocation) theEObject;
			T result = caseInvocation(invocation);
			if (result == null)
				result = defaultCase(theEObject);
			return result;
		}
		default:
			return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Mo C</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Mo C</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseMoC(MoC object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>CSDF Mo C</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>CSDF Mo C</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCSDFMoC(CSDFMoC object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>DPN Mo C</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>DPN Mo C</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseDPNMoC(DPNMoC object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>KPN Mo C</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>KPN Mo C</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseKPNMoC(KPNMoC object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>QSDF Mo C</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>QSDF Mo C</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseQSDFMoC(QSDFMoC object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>SDF Mo C</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>SDF Mo C</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseSDFMoC(SDFMoC object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Invocation</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Invocation</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseInvocation(Invocation object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //MocSwitch
