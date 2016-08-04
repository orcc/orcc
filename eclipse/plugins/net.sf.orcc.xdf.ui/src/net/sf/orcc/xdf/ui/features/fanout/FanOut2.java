package net.sf.orcc.xdf.ui.features.fanout;

import org.eclipse.graphiti.features.IFeatureProvider;

public class FanOut2 extends FanOutFanInFeature {
	public FanOut2(IFeatureProvider fp) {
		super(fp, 2);
	}
	@Override
	public String getName() {
		return "2";
	}
}