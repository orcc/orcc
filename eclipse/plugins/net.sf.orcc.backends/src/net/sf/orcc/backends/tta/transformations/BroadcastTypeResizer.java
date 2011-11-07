package net.sf.orcc.backends.tta.transformations;

import java.util.List;

import net.sf.orcc.OrccException;
import net.sf.orcc.df.Broadcast;
import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.df.Port;
import net.sf.orcc.df.transformations.INetworkTransformation;
import net.sf.orcc.ir.Type;
import net.sf.orcc.ir.TypeInt;
import net.sf.orcc.ir.TypeList;
import net.sf.orcc.ir.TypeUint;

public class BroadcastTypeResizer implements INetworkTransformation {

	@Override
	public void transform(Network network) throws OrccException {
		for (Instance instance : network.getInstances()) {
			if (instance.isBroadcast()) {
				Broadcast broadcast = instance.getBroadcast();
				checkPorts(broadcast.getInputs());
				checkPorts(broadcast.getOutputs());
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
		checkType(type, 0);
	}
	
	private void checkType(Type type, int newSize) {
		int size;
		if (type.isInt()) {
			TypeInt intType = (TypeInt) type;
			if (newSize > 0) {
				size = newSize;
			} else {
				size = getIntSize(intType.getSize());
			}
			intType.setSize(size);
		} else if (type.isUint()) {
			TypeUint uintType = (TypeUint) type;
			if (newSize > 0) {
				size = newSize;
			} else {
				size = getIntSize(uintType.getSize());
			}
			uintType.setSize(size);
		} else if (type.isList()) {
			TypeList listType = (TypeList) type;
			checkType(listType.getType(), newSize);
		}
	}

	private int getIntSize(int size) {
		if (size <= 8) {
			return 8;
		} else if (size <= 16) {
			return 16;
		} else {
			return 32;
		}
	}

}
