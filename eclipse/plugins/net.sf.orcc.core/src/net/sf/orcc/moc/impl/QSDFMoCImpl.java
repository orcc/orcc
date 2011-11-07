/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package net.sf.orcc.moc.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import net.sf.orcc.df.Action;
import net.sf.orcc.moc.MocPackage;
import net.sf.orcc.moc.QSDFMoC;
import net.sf.orcc.moc.SDFMoC;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc --> An implementation of the model object '
 * <em><b>QSDF Mo C</b></em>'. <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class QSDFMoCImpl extends MoCImpl implements QSDFMoC {

	private Map<Action, SDFMoC> configurations;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 */
	protected QSDFMoCImpl() {
		super();
		configurations = new HashMap<Action, SDFMoC>();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return MocPackage.Literals.QSDF_MO_C;
	}

	@Override
	public Set<Action> getActions() {
		return configurations.keySet();
	}

	@Override
	public Map<Action, SDFMoC> getConfigurations(){
		return configurations;
	}

	@Override
	public SDFMoC getSDFMoC(Action action) {
		return configurations.get(action);
	}

	@Override
	public boolean isQuasiStatic() {
		return true;
	}

	@Override
	public void setSDFMoC(Action action, SDFMoC moc) {
		configurations.put(action, moc);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QSDF configurations: [");
		for (Entry<Action, SDFMoC> entry : configurations.entrySet()) {
			builder.append(entry.getKey().getName());
			builder.append(":");
			builder.append(entry.getValue());
			builder.append(" ");
		}
		builder.append("]");
		return builder.toString();
	}

} // QSDFMoCImpl
