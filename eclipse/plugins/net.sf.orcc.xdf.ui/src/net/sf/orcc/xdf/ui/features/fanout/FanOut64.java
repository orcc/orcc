package net.sf.orcc.xdf.ui.features.fanout;

import org.eclipse.graphiti.features.IFeatureProvider;

public class FanOut64 extends FanOutFanInFeature {
	public FanOut64(IFeatureProvider fp) {
		super(fp, 64);
	}
	@Override
	public String getName() {
		return "64";
	}
}