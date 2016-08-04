package net.sf.orcc.xdf.ui.features.fanout;

import org.eclipse.graphiti.features.IFeatureProvider;

public class FanOut8 extends FanOutFanInFeature {
	public FanOut8(IFeatureProvider fp) {
		super(fp, 8);
	}
	@Override
	public String getName() {
		return "8";
	}
}