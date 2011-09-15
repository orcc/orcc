package net.sf.orcc.backends.tta.transformations;

import java.util.List;

import net.sf.orcc.OrccException;
import net.sf.orcc.ir.Port;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.TypeUint;
import net.sf.orcc.network.Broadcast;
import net.sf.orcc.network.Instance;
import net.sf.orcc.network.Network;
import net.sf.orcc.network.transformations.INetworkTransformation;

public class BroadcastTypeResizer implements INetworkTransformation {

	@Override
	public void transform(Network network) throws OrccException {
		for (Instance instance : network.getInstances()) {
			if (instance.isBroadcast()) {
				Broadcast broadcast = instance.getBroadcast();
				checkPorts(broadcast.getInputs().getList());
				checkPorts(broadcast.getOutputs().getList());
			}
		}
	}

	private void checkPorts(List<Port> ports) {
		for (Port port : ports) {
			if (!port.isNative()) {
				checkType(port.getType());
			}
		}
	}

	private void checkType(Type type) {
		int size;
		if (type.isInt()) {
			TypeInt intType = (TypeInt) type;
			size = getIntSize(intType.getSize());
			intType.setSize(size);
		} else if (type.isUint()) {
			TypeUint uintType = (TypeUint) type;
			size = getIntSize(uintType.getSize());
			uintType.setSize(size);
		} else if (type.isList()) {
			TypeList listType = (TypeList) type;
			checkType(listType.getType());
		}
	}

	private int getIntSize(int size) {
		if (size <= 8) {
			return 8;
		} else if (size <= 16) {
			return 16;
		} else if (size <= 32) {
			return 32;
		} else {
			return 64;
		}
	}

}
