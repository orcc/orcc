/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.backends.tta.architecture.util;

import net.sf.orcc.backends.tta.architecture.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see net.sf.orcc.backends.tta.architecture.ArchitecturePackage
 * @generated
 */
public class ArchitectureAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static ArchitecturePackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArchitectureAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = ArchitecturePackage.eINSTANCE;
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
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ArchitectureSwitch<Adapter> modelSwitch =
		new ArchitectureSwitch<Adapter>() {
			@Override
			public Adapter caseTTA(TTA object) {
				return createTTAAdapter();
			}
			@Override
			public Adapter caseBus(Bus object) {
				return createBusAdapter();
			}
			@Override
			public Adapter caseGlobalControlUnit(GlobalControlUnit object) {
				return createGlobalControlUnitAdapter();
			}
			@Override
			public Adapter caseFunctionalUnit(FunctionalUnit object) {
				return createFunctionalUnitAdapter();
			}
			@Override
			public Adapter caseRegisterFile(RegisterFile object) {
				return createRegisterFileAdapter();
			}
			@Override
			public Adapter casePort(Port object) {
				return createPortAdapter();
			}
			@Override
			public Adapter caseSocket(Socket object) {
				return createSocketAdapter();
			}
			@Override
			public Adapter caseOperation(Operation object) {
				return createOperationAdapter();
			}
			@Override
			public Adapter caseOperationCtrl(OperationCtrl object) {
				return createOperationCtrlAdapter();
			}
			@Override
			public Adapter caseAdressSpace(AdressSpace object) {
				return createAdressSpaceAdapter();
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
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link net.sf.orcc.backends.tta.architecture.TTA <em>TTA</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see net.sf.orcc.backends.tta.architecture.TTA
	 * @generated
	 */
	public Adapter createTTAAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link net.sf.orcc.backends.tta.architecture.Bus <em>Bus</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see net.sf.orcc.backends.tta.architecture.Bus
	 * @generated
	 */
	public Adapter createBusAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link net.sf.orcc.backends.tta.architecture.GlobalControlUnit <em>Global Control Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see net.sf.orcc.backends.tta.architecture.GlobalControlUnit
	 * @generated
	 */
	public Adapter createGlobalControlUnitAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link net.sf.orcc.backends.tta.architecture.FunctionalUnit <em>Functional Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see net.sf.orcc.backends.tta.architecture.FunctionalUnit
	 * @generated
	 */
	public Adapter createFunctionalUnitAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link net.sf.orcc.backends.tta.architecture.RegisterFile <em>Register File</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see net.sf.orcc.backends.tta.architecture.RegisterFile
	 * @generated
	 */
	public Adapter createRegisterFileAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link net.sf.orcc.backends.tta.architecture.Port <em>Port</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see net.sf.orcc.backends.tta.architecture.Port
	 * @generated
	 */
	public Adapter createPortAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link net.sf.orcc.backends.tta.architecture.Socket <em>Socket</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see net.sf.orcc.backends.tta.architecture.Socket
	 * @generated
	 */
	public Adapter createSocketAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link net.sf.orcc.backends.tta.architecture.Operation <em>Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see net.sf.orcc.backends.tta.architecture.Operation
	 * @generated
	 */
	public Adapter createOperationAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link net.sf.orcc.backends.tta.architecture.OperationCtrl <em>Operation Ctrl</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see net.sf.orcc.backends.tta.architecture.OperationCtrl
	 * @generated
	 */
	public Adapter createOperationCtrlAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link net.sf.orcc.backends.tta.architecture.AdressSpace <em>Adress Space</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see net.sf.orcc.backends.tta.architecture.AdressSpace
	 * @generated
	 */
	public Adapter createAdressSpaceAdapter() {
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

} //ArchitectureAdapterFactory
