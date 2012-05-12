/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.backends.ir.impl;

import java.util.List;

import net.sf.orcc.backends.ir.BlockFor;
import net.sf.orcc.backends.ir.InstAssignIndex;
import net.sf.orcc.backends.ir.InstCast;
import net.sf.orcc.backends.ir.InstTernary;
import net.sf.orcc.backends.ir.IrSpecificFactory;
import net.sf.orcc.backends.ir.IrSpecificPackage;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Var;

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
public class IrSpecificFactoryImpl extends EFactoryImpl implements
		IrSpecificFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	public static IrSpecificFactory init() {
		try {
			IrSpecificFactory theIrSpecificFactory = (IrSpecificFactory) EPackage.Registry.INSTANCE
					.getEFactory("http://orcc.sf.net/backends/ir");
			if (theIrSpecificFactory != null) {
				return theIrSpecificFactory;
			}
		} catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new IrSpecificFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	public IrSpecificFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
		case IrSpecificPackage.INST_ASSIGN_INDEX:
			return createInstAssignIndex();
		case IrSpecificPackage.INST_CAST:
			return createInstCast();
		case IrSpecificPackage.INST_TERNARY:
			return createInstTernary();
		case IrSpecificPackage.BLOCK_FOR:
			return createBlockFor();
		default:
			throw new IllegalArgumentException("The class '" + eClass.getName()
					+ "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public InstAssignIndex createInstAssignIndex() {
		InstAssignIndexImpl instAssignIndex = new InstAssignIndexImpl();
		return instAssignIndex;
	}

	@Override
	public InstAssignIndex createInstAssignIndex(Var indexVar,
			List<Expression> indexes, Type listType) {
		InstAssignIndexImpl instAssignIndex = new InstAssignIndexImpl();
		instAssignIndex.setTarget(IrFactory.eINSTANCE.createDef(indexVar));
		instAssignIndex.getIndexes().addAll(indexes);
		instAssignIndex.setListType(listType);
		return instAssignIndex;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public InstCast createInstCast() {
		InstCastImpl instCast = new InstCastImpl();
		return instCast;
	}

	@Override
	public InstCast createInstCast(Var source, Var target) {
		InstCastImpl instCast = new InstCastImpl();
		instCast.setSource(IrFactory.eINSTANCE.createUse(source));
		instCast.setTarget(IrFactory.eINSTANCE.createDef(target));
		return instCast;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public InstTernary createInstTernary() {
		InstTernaryImpl instTernary = new InstTernaryImpl();
		return instTernary;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public BlockFor createBlockFor() {
		BlockForImpl blockFor = new BlockForImpl();
		return blockFor;
	}

	@Override
	public InstTernary createInstTernary(Var target, Expression condition,
			Expression trueValue, Expression falseValue) {
		InstTernaryImpl instTernary = new InstTernaryImpl();
		instTernary.setTarget(IrFactory.eINSTANCE.createDef(target));
		instTernary.setConditionValue(condition);
		instTernary.setTrueValue(trueValue);
		instTernary.setFalseValue(falseValue);
		return instTernary;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public IrSpecificPackage getIrSpecificPackage() {
		return (IrSpecificPackage) getEPackage();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static IrSpecificPackage getPackage() {
		return IrSpecificPackage.eINSTANCE;
	}

} // IrSpecificFactoryImpl
