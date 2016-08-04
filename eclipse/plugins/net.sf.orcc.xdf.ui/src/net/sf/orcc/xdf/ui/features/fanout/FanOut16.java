package net.sf.orcc.xdf.ui.features.fanout;

import org.eclipse.graphiti.features.IFeatureProvider;

public class FanOut16 extends FanOutFanInFeature {
	public FanOut16(IFeatureProvider fp) {
		super(fp, 16);
	}
	@Override
	public String getName() {
		return "16";
	}
}