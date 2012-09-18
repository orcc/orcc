/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.backends.ir.util;

import net.sf.orcc.backends.ir.BlockFor;
import net.sf.orcc.backends.ir.InstAssignIndex;
import net.sf.orcc.backends.ir.InstCast;
import net.sf.orcc.backends.ir.InstTernary;
import net.sf.orcc.backends.ir.IrSpecificPackage;
import net.sf.orcc.ir.Block;
import net.sf.orcc.ir.InstSpecific;
import net.sf.orcc.ir.Instruction;
import net.sf.orcc.util.Attributable;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> The <b>Adapter Factory</b> for the model. It provides
 * an adapter <code>createXXX</code> method for each class of the model. <!--
 * end-user-doc -->
 * @see net.sf.orcc.backends.ir.IrSpecificPackage
 * @generated
 */
public class IrSpecificAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	protected static IrSpecificPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	public IrSpecificAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = IrSpecificPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc --> This implementation returns <code>true</code> if
	 * the object is either the model's package or is an instance object of the
	 * model. <!-- end-user-doc -->
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
	 * The switch that delegates to the <code>createXXX</code> methods. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected IrSpecificSwitch<Adapter> modelSwitch = new IrSpecificSwitch<Adapter>() {
		@Override
		public Adapter caseInstAssignIndex(InstAssignIndex object) {
			return createInstAssignIndexAdapter();
		}

		@Override
		public Adapter caseInstCast(InstCast object) {
			return createInstCastAdapter();
		}

		@Override
		public Adapter caseInstTernary(InstTernary object) {
			return createInstTernaryAdapter();
		}

		@Override
		public Adapter caseBlockFor(BlockFor object) {
			return createBlockForAdapter();
		}

		@Override
		public Adapter caseAttributable(Attributable object) {
			return createAttributableAdapter();
		}

		@Override
		public Adapter caseInstruction(Instruction object) {
			return createInstructionAdapter();
		}

		@Override
		public Adapter caseInstSpecific(InstSpecific object) {
			return createInstSpecificAdapter();
		}

		@Override
		public Adapter caseBlock(Block object) {
			return createBlockAdapter();
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
	 * Creates a new adapter for an object of class '{@link net.sf.orcc.backends.ir.InstAssignIndex <em>Inst Assign Index</em>}'.
	 * <!-- begin-user-doc --> This default
	 * implementation returns null so that we can easily ignore cases; it's
	 * useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see net.sf.orcc.backends.ir.InstAssignIndex
	 * @generated
	 */
	public Adapter createInstAssignIndexAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link net.sf.orcc.backends.ir.InstCast <em>Inst Cast</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see net.sf.orcc.backends.ir.InstCast
	 * @generated
	 */
	public Adapter createInstCastAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link net.sf.orcc.backends.ir.InstTernary <em>Inst Ternary</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see net.sf.orcc.backends.ir.InstTernary
	 * @generated
	 */
	public Adapter createInstTernaryAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link net.sf.orcc.backends.ir.BlockFor <em>Block For</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see net.sf.orcc.backends.ir.BlockFor
	 * @generated
	 */
	public Adapter createBlockForAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link net.sf.orcc.ir.Instruction <em>Instruction</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see net.sf.orcc.ir.Instruction
	 * @generated
	 */
	public Adapter createInstructionAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link net.sf.orcc.ir.InstSpecific <em>Inst Specific</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see net.sf.orcc.ir.InstSpecific
	 * @generated
	 */
	public Adapter createInstSpecificAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link net.sf.orcc.ir.Block <em>Block</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see net.sf.orcc.ir.Block
	 * @generated
	 */
	public Adapter createBlockAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '
	 * {@link net.sf.orcc.util.Attributable <em>Attributable</em>}'. <!--
	 * begin-user-doc --> This default implementation returns null so that we
	 * can easily ignore cases; it's useful to ignore a case when inheritance
	 * will catch all the cases anyway. <!-- end-user-doc -->
	 * 
	 * @return the new adapter.
	 * @see net.sf.orcc.util.Attributable
	 * @generated
	 */
	public Adapter createAttributableAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc --> This
	 * default implementation returns null. <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} // IrSpecificAdapterFactory
