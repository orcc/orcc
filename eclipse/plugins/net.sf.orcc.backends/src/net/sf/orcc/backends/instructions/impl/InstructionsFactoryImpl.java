/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.backends.instructions.impl;

import java.util.List;

import net.sf.orcc.backends.instructions.InstAssignIndex;
import net.sf.orcc.backends.instructions.InstRamRead;
import net.sf.orcc.backends.instructions.InstRamSetAddress;
import net.sf.orcc.backends.instructions.InstRamWrite;
import net.sf.orcc.backends.instructions.InstSplit;
import net.sf.orcc.backends.instructions.InstTernary;
import net.sf.orcc.backends.instructions.InstructionsFactory;
import net.sf.orcc.backends.instructions.InstructionsPackage;
import net.sf.orcc.ir.Expression;
import net.sf.orcc.ir.IrFactory;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.Var;

import org.eclipse.emf.common.util.EList;
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
public class InstructionsFactoryImpl extends EFactoryImpl implements
		InstructionsFactory {
	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static InstructionsPackage getPackage() {
		return InstructionsPackage.eINSTANCE;
	}

	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	public static InstructionsFactory init() {
		try {
			InstructionsFactory theInstructionsFactory = (InstructionsFactory)EPackage.Registry.INSTANCE.getEFactory("http://orcc.sf.net/backends/instructions/Instructions"); 
			if (theInstructionsFactory != null) {
				return theInstructionsFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new InstructionsFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * @generated
	 */
	public InstructionsFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case InstructionsPackage.INST_TERNARY: return createInstTernary();
			case InstructionsPackage.INST_ASSIGN_INDEX: return createInstAssignIndex();
			case InstructionsPackage.INST_SPLIT: return createInstSplit();
			case InstructionsPackage.INST_RAM_READ: return createInstRamRead();
			case InstructionsPackage.INST_RAM_SET_ADDRESS: return createInstRamSetAddress();
			case InstructionsPackage.INST_RAM_WRITE: return createInstRamWrite();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
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
			List<Expression> listIndex, Type type) {
		InstAssignIndexImpl instAssignIndex = new InstAssignIndexImpl();
		instAssignIndex.setTarget(IrFactory.eINSTANCE.createDef(indexVar));
		EList<Expression> indexes = instAssignIndex.getIndexes();
		for (Expression expr : listIndex) {
			indexes.add(expr);
		}
		instAssignIndex.setListType(type);
		return instAssignIndex;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public InstRamRead createInstRamRead() {
		InstRamReadImpl instRamRead = new InstRamReadImpl();
		return instRamRead;
	}

	@Override
	public InstRamRead createInstRamRead(int port, Var variable, Var target) {
		InstRamReadImpl instRamRead = new InstRamReadImpl();
		instRamRead.setPort(port);
		instRamRead.setVariable(variable);
		instRamRead.setTarget(IrFactory.eINSTANCE.createDef(target));
		return instRamRead;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public InstRamSetAddress createInstRamSetAddress() {
		InstRamSetAddressImpl instRamSetAddress = new InstRamSetAddressImpl();
		return instRamSetAddress;
	}

	@Override
	public InstRamSetAddress createInstRamSetAddress(int port, Var variable,
			List<Expression> indexes) {
		InstRamSetAddressImpl instRamSetAddress = new InstRamSetAddressImpl();
		instRamSetAddress.setPort(port);
		instRamSetAddress.setVariable(variable);
		instRamSetAddress.getIndexes().addAll(indexes);
		return instRamSetAddress;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public InstRamWrite createInstRamWrite() {
		InstRamWriteImpl instRamWrite = new InstRamWriteImpl();
		return instRamWrite;
	}

	@Override
	public InstRamWrite createInstRamWrite(int port, Var variable,
			Expression value) {
		InstRamWriteImpl instRamWrite = new InstRamWriteImpl();
		instRamWrite.setPort(port);
		instRamWrite.setVariable(variable);
		instRamWrite.setValue(value);
		return instRamWrite;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public InstSplit createInstSplit() {
		InstSplitImpl instSplit = new InstSplitImpl();
		return instSplit;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public InstTernary createInstTernary() {
		InstTernaryImpl instTernary = new InstTernaryImpl();
		return instTernary;
	}

	@Override
	public InstTernary createInstTernary(Var target, Expression condition,
			Expression trueValue, Expression falseValue) {
		InstTernaryImpl instTernary = new InstTernaryImpl();
		instTernary.setTarget(IrFactory.eINSTANCE.createDef(target));
		instTernary.setTrueValue(trueValue);
		instTernary.setFalseValue(falseValue);
		return instTernary;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	public InstructionsPackage getInstructionsPackage() {
		return (InstructionsPackage)getEPackage();
	}

} // InstructionsFactoryImpl
