package net.sf.orcc.xdf.ui.features.fanout;

import org.eclipse.graphiti.features.IFeatureProvider;

public class FanOut4 extends FanOutFanInFeature {
	public FanOut4(IFeatureProvider fp) {
		super(fp, 4);
	}
	@Override
	public String getName() {
		return "4";
	}
}