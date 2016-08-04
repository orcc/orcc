package net.sf.orcc.xdf.ui.features.fanout;

import org.eclipse.graphiti.features.IFeatureProvider;

public class FanOut32 extends FanOutFanInFeature {
	public FanOut32(IFeatureProvider fp) {
		super(fp, 32);
	}
	@Override
	public String getName() {
		return "32";
	}
}