/*
 * Copyright (c) 2013, IETR/INSA of Rennes
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 *   * Redistributions of source code must retain the above copyright notice,
 *     this list of conditions and the following disclaimer.
 *   * Redistributions in binary form must reproduce the above copyright notice,
 *     this list of conditions and the following disclaimer in the documentation
 *     and/or other materials provided with the distribution.
 *   * Neither the name of IRISA nor the names of its
 *     contributors may be used to endorse or promote products derived from this
 *     software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT,
 * STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY
 * WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */

package net.sf.orcc.xdf.ui.styles;

import org.eclipse.emf.common.util.EList;
import org.eclipse.graphiti.mm.algorithms.styles.AdaptedGradientColoredAreas;
import org.eclipse.graphiti.mm.algorithms.styles.GradientColoredArea;
import org.eclipse.graphiti.mm.algorithms.styles.GradientColoredAreas;
import org.eclipse.graphiti.mm.algorithms.styles.LocationType;
import org.eclipse.graphiti.mm.algorithms.styles.StylesFactory;
import org.eclipse.graphiti.util.IGradientType;
import org.eclipse.graphiti.util.IPredefinedRenderingStyle;
import org.eclipse.graphiti.util.PredefinedColoredAreas;

/**
 * Defines some gradients used in Xdf diagrams
 * 
 * @author Antoine Lorence
 * 
 */
public class XdfGradients extends PredefinedColoredAreas {

	public static final String INPORT_ID = "inport";
	public static final String OUTPORT_ID = "outport";
	public static final String NETWORK_ID = "network";
	public static final String ACTOR_ID = "actor";

	private static GradientColoredAreas inputPortGradientAreas() {
		final GradientColoredAreas gradientColoredAreas = StylesFactory.eINSTANCE.createGradientColoredAreas();
		final EList<GradientColoredArea> gcas = gradientColoredAreas.getGradientColor();

		addGradientColoredArea(gcas, "9FBF60", 0, LocationType.LOCATION_TYPE_ABSOLUTE_START, "CBDFA2", 0,
				LocationType.LOCATION_TYPE_ABSOLUTE_END);

		gradientColoredAreas.setStyleAdaption(IPredefinedRenderingStyle.STYLE_ADAPTATION_DEFAULT);
		return gradientColoredAreas;
	}

	public static AdaptedGradientColoredAreas inputPortGradient() {
		final AdaptedGradientColoredAreas agca = StylesFactory.eINSTANCE.createAdaptedGradientColoredAreas();
		agca.setDefinedStyleId(INPORT_ID);
		agca.setGradientType(IGradientType.HORIZONTAL);
		agca.getAdaptedGradientColoredAreas().add(STYLE_ADAPTATION_DEFAULT, inputPortGradientAreas());
		return agca;
	}

	private static GradientColoredAreas outputPortGradientAreas() {
		final GradientColoredAreas gradientColoredAreas = StylesFactory.eINSTANCE.createGradientColoredAreas();
		final EList<GradientColoredArea> gcas = gradientColoredAreas.getGradientColor();

		addGradientColoredArea(gcas, "CBDFA2", 0, LocationType.LOCATION_TYPE_ABSOLUTE_START, "9FBF60", 0,
				LocationType.LOCATION_TYPE_ABSOLUTE_END);

		gradientColoredAreas.setStyleAdaption(IPredefinedRenderingStyle.STYLE_ADAPTATION_DEFAULT);
		return gradientColoredAreas;
	}

	public static AdaptedGradientColoredAreas outputPortGradient() {
		final AdaptedGradientColoredAreas agca = StylesFactory.eINSTANCE.createAdaptedGradientColoredAreas();
		agca.setDefinedStyleId(OUTPORT_ID);
		agca.setGradientType(IGradientType.HORIZONTAL);
		agca.getAdaptedGradientColoredAreas().add(STYLE_ADAPTATION_DEFAULT, outputPortGradientAreas());
		return agca;
	}

	private static GradientColoredAreas networkGradientAreas() {
		final GradientColoredAreas gradientColoredAreas = StylesFactory.eINSTANCE.createGradientColoredAreas();
		final EList<GradientColoredArea> gcas = gradientColoredAreas.getGradientColor();

		addGradientColoredArea(gcas, "FFFADD", 0, LocationType.LOCATION_TYPE_ABSOLUTE_START, "FFEE84", 4,
				LocationType.LOCATION_TYPE_ABSOLUTE_START);
		addGradientColoredArea(gcas, "FFEE84", 4, LocationType.LOCATION_TYPE_ABSOLUTE_START, "FFF3B5", 0,
				LocationType.LOCATION_TYPE_ABSOLUTE_END);

		gradientColoredAreas.setStyleAdaption(IPredefinedRenderingStyle.STYLE_ADAPTATION_DEFAULT);
		return gradientColoredAreas;
	}

	public static AdaptedGradientColoredAreas networkGradient() {
		final AdaptedGradientColoredAreas agca = StylesFactory.eINSTANCE.createAdaptedGradientColoredAreas();
		agca.setDefinedStyleId(NETWORK_ID);
		agca.setGradientType(IGradientType.VERTICAL);
		agca.getAdaptedGradientColoredAreas().add(STYLE_ADAPTATION_DEFAULT, networkGradientAreas());
		return agca;
	}


	private static GradientColoredAreas actorGradientAreas() {
		final GradientColoredAreas gradientColoredAreas = StylesFactory.eINSTANCE.createGradientColoredAreas();
		final EList<GradientColoredArea> gcas = gradientColoredAreas.getGradientColor();

		addGradientColoredArea(gcas, "B4CAE0", 0, LocationType.LOCATION_TYPE_ABSOLUTE_START, "9EBEE0", 4,
				LocationType.LOCATION_TYPE_ABSOLUTE_START);
		addGradientColoredArea(gcas, "9EBEE0", 4, LocationType.LOCATION_TYPE_ABSOLUTE_START, "DAEAFB", 0,
				LocationType.LOCATION_TYPE_ABSOLUTE_END);

		gradientColoredAreas.setStyleAdaption(IPredefinedRenderingStyle.STYLE_ADAPTATION_DEFAULT);
		return gradientColoredAreas;
	}

	public static AdaptedGradientColoredAreas actorGradient() {
		final AdaptedGradientColoredAreas agca = StylesFactory.eINSTANCE.createAdaptedGradientColoredAreas();
		agca.setDefinedStyleId(ACTOR_ID);
		agca.setGradientType(IGradientType.VERTICAL);
		agca.getAdaptedGradientColoredAreas().add(STYLE_ADAPTATION_DEFAULT, actorGradientAreas());
		return agca;
	}
}
