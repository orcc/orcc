/*
 * Copyright (c) 2016, Heriot-Watt University
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
 *   * Neither the name of the IETR/INSA of Rennes nor the names of its
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
package net.sf.orcc.xdf.ui.features;

import net.sf.orcc.xdf.ui.patterns.InstancePattern;
import net.sf.orcc.xdf.ui.styles.StyleUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.orcc.df.Instance;
import net.sf.orcc.df.Network;
import net.sf.orcc.graph.Vertex;
import net.sf.orcc.xdf.ui.util.PropsUtil;
import net.sf.orcc.xdf.ui.util.XdfUtil;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.ui.services.GraphitiUi;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * This class reads BRAM and maximum gate depth costs that is computed at
 * compile time with the Xronos backend that generates Verilog. It parses HTML
 * and XML files in the rtl/report/ directory created by Orcc when this backend
 * is used. These costs are then highlighted on the XDF dataflow diagram. It
 * details the number of BRAM blocks needed to implement the actor, and also the
 * maximum gate depth by naming the action with the largest gate depth. The
 * container shapes corresponding to actors with relatively larger gate depths
 * become yellow boxes, and actor with relative lower gate depths become green
 * boxes. The actor contributing to the combintional critical path (i.e. which
 * actor is determining the clock frequency) is indicated with an orange box.
 *
 * @author Rob Stewart
 *
 */
public class XronosProfileFeature extends AbstractTimeConsumingCustomFeature {

	private String xronosReportsDir;
	private int COST_SPACE = 40;

	public XronosProfileFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getName() {
		return "Xronos FPGA costs";
	}

	@Override
	public String getDescription() {
		return "Highlight FPGA costs from Xronos";
	}

	@Override
	public boolean isAvailable(IContext context) {
		return super.isAvailable(context);
	}

	@Override
	/**
	 * lets the user click this highlighter if they have right clicked white
	 * space in the XDF network, and does not allow this highlighter to be used
	 * if they have right clicked any other graphical feature in the XDF
	 * interface.
	 */
	public boolean canExecute(ICustomContext context) {
		boolean canExec = false;
		PictogramElement[] pes = context.getPictogramElements();
		if (pes.length == 1) {
			final PictogramElement pe = pes[0];
			if (getBusinessObjectForPictogramElement(pe) instanceof Network) {
				canExec = true;
			}
		}
		return canExec;
	}

	@Override
	/**
	 * Uses a directory dialog box for the user to locate the rtl/report/
	 * directory generated from the Xronos backend by compiling the XDF network.
	 */
	protected void beforeJobExecution() {
		DirectoryDialog dialog = new DirectoryDialog(XdfUtil.getDefaultShell());
		dialog.setMessage("Select the rtl/report/ director");
		dialog.setText("Select the rtl/report/ directory");
		String selectedDirectoryName = dialog.open();
		xronosReportsDir = selectedDirectoryName;
	}

	@Override
	public void execute(ICustomContext context, IProgressMonitor parentMonitor) {
		final Network currentNetwork = (Network) getBusinessObjectForPictogramElement(getDiagram());

		Map<String, InstanceCosts> instanceCosts = new HashMap<String, InstanceCosts>();

		/*
		 * obtain max gate depth and BRAM costs for each instance by parsing XML
		 * and HMTL files
		 */
		List<Vertex> nodes = currentNetwork.getVertices();
		List<Integer> maxDepths = new ArrayList<Integer>();
		for (Vertex vertex : nodes) {
			if (vertex instanceof Instance) {
				Instance inst = (Instance) vertex;

				/* test that costs for the instance is is rtl/report/ */
				String reportFile = xronosReportsDir + "/" + inst.getName() + "_ResourceUtilizationReport.html";
				File f = new File(reportFile);
				if (f.exists() && !f.isDirectory()) {

					/* parse the XML file corresponding to the instance */
					MaxGateDepth maxGateDepth = maxGateDepth(inst.getName());
					maxDepths.add(maxGateDepth.getMaxDepth());
					/*
					 * parse the ResourceUtilizationReport.html corresponding to
					 * the instance
					 */
					int brams = brams(inst.getName());
					instanceCosts.put(inst.getName(), new InstanceCosts(maxGateDepth, brams));
				}
			}
		}

		/* sort the maximum gate depths to find relative costs */
		Collections.sort(maxDepths);
		/* split them into a lower half and upper half list */
		List<Integer> upperHalf;
		Integer highest;
		if (maxDepths.size() < 1) {
			upperHalf = new ArrayList<Integer>();
			highest = 0;
		} else {
			upperHalf = maxDepths.subList(maxDepths.size() / 2, maxDepths.size() - 1);
			highest = maxDepths.get(maxDepths.size() - 1);
		}

		/*
		 * Add the relative maximum gate depth cost to each InstanceCosts
		 * instance
		 */
		for (Map.Entry<String, InstanceCosts> entry : instanceCosts.entrySet()) {
			if (highest.equals(new Integer(entry.getValue().getMaxGateDepth().maxDepth))) {
				InstanceCosts ic = entry.getValue();
				ic.setRelativeDepthCost(RelativeDepthCost.HIGHEST);
				entry.setValue(ic);
			} else if (upperHalf.contains(new Integer(entry.getValue().getMaxGateDepth().maxDepth))) {
				InstanceCosts ic = entry.getValue();
				ic.setRelativeDepthCost(RelativeDepthCost.HIGH);
				entry.setValue(ic);
			} else { // lower half
				InstanceCosts ic = entry.getValue();
				ic.setRelativeDepthCost(RelativeDepthCost.LOW);
				entry.setValue(ic);
			}
		}

		/* highlight the XDF diagram with the parsed and computed costs */
		overlayCosts(getDiagram(), instanceCosts);
	}

	/**
	 * Overlays the BRAM and max gate depth costs to each instance in the XDF
	 * diagram
	 *
	 * @param diagram
	 *            The XDF diagram
	 * @param instanceCosts
	 *            The costs parsed from HTML and XML files in rtl/report/
	 */
	private void overlayCosts(Diagram diagram, Map<String, InstanceCosts> instanceCosts) {
		IGaService service = GraphitiUi.getGaService();

		EList<Shape> shapes = diagram.getChildren();
		for (Shape shape : shapes) {

			GraphicsAlgorithm grAlg = shape.getGraphicsAlgorithm();
			if (grAlg instanceof RoundedRectangle) {
				RoundedRectangle roundRect = (RoundedRectangle) grAlg;
				/* get the instance name */
				GraphicsAlgorithm gr = roundRect.getGraphicsAlgorithmChildren().get(0);
				String instanceName = ((Text) gr).getValue();

				/*
				 * only modify a rectangle box if costs are known for the
				 * instance, i.e. if there were corresponding HTML and XML files
				 * in rtl/report/ for the instance.
				 */
				if (instanceCosts.containsKey(instanceName)) {

					int rectHeight = roundRect.getHeight();

					/*
					 * if the XDF network has already been highlighted once with
					 * Xronos costs, then we first need to remove the four GUI
					 * features that get added to the rectangle corresponding to
					 * the instance.
					 */
					Iterator<GraphicsAlgorithm> gaIter = roundRect.getGraphicsAlgorithmChildren().iterator();
					while (gaIter.hasNext()) {
						GraphicsAlgorithm ga = gaIter.next();
						if (ga instanceof Text) {
							Text tmp = (Text) ga;
							if (Graphiti.getPeService().getProperty(tmp, "XDF_ID") != null) {
								if (Graphiti.getPeService().getProperty(tmp, "XDF_ID").getValue()
										.equals("maxDepth-lbl")) {
									gaIter.remove();
									/* also reduce the height of the box */
									roundRect.setHeight(rectHeight - COST_SPACE);
								}
							} else if (Graphiti.getPeService().getProperty(tmp, "XDF_ID") != null) {
								if (Graphiti.getPeService().getProperty(tmp, "XDF_ID").getValue().equals("bram-lbl")) {
									gaIter.remove();
								}
							} else if (Graphiti.getPeService().getProperty(tmp, "XDF_ID") != null) {
								if (Graphiti.getPeService().getProperty(tmp, "XDF_ID").getValue()
										.equals("maxDepthAction-lbl")) {
									gaIter.remove();
								}
							} else if (Graphiti.getPeService().getProperty(tmp, "XDF_ID") != null) {
								if (Graphiti.getPeService().getProperty(tmp, "XDF_ID").getValue()
										.equals("hardware-costs-separator")) {
									gaIter.remove();
								}
							}
						}
					}

					/* look up the costs for the instance */
					InstanceCosts costs = instanceCosts.get(instanceName);

					/*
					 * max gate depths have been ordered: low, high or highest.
					 * Low relative gate depths go green, high relative depths
					 * go yellow, whilst the highest relative gate depth does
					 * orange.
					 */
					switch (costs.getRelativeDepthCost()) {
					case LOW:
						shape.getGraphicsAlgorithm().setStyle(StyleUtil.actorInstanceShapeGreen(diagram));
						break;
					case HIGH:
						shape.getGraphicsAlgorithm().setStyle(StyleUtil.actorInstanceShapeYellow(diagram));
						break;
					case HIGHEST:
						shape.getGraphicsAlgorithm().setStyle(StyleUtil.actorInstanceShapeOrange(diagram));
						break;
					}

					/* make space in the rectangle for the cost information */
					rectHeight = roundRect.getHeight();
					roundRect.setHeight(rectHeight + COST_SPACE);

					/* a line that separators port info from cost info */
					final int[] xy = { 0, rectHeight, InstancePattern.TOTAL_MIN_WIDTH, rectHeight };
					final Polyline line = service.createPlainPolyline(roundRect, xy);
					PropsUtil.setIdentifier(line, "hardware-costs-separator");
					line.setLineWidth(1);

					/* BRAM info */
					final Text bramText = service.createPlainText(roundRect);
					PropsUtil.setIdentifier(bramText, "bram-lbl");
					bramText.setStyle(StyleUtil.costsText(getDiagram()));
					service.setLocationAndSize(bramText, 0, rectHeight + 5, InstancePattern.TOTAL_MIN_WIDTH, 10);
					String suffix = costs.getBrams() < 2 ? "" : "s";
					bramText.setValue(costs.getBrams() + " BRAM" + suffix);

					/* max gate depth number */
					final Text maxDepthText = service.createPlainText(roundRect);
					PropsUtil.setIdentifier(maxDepthText, "maxDepth-lbl");
					maxDepthText.setStyle(StyleUtil.costsText(getDiagram()));
					service.setLocationAndSize(maxDepthText, 0, rectHeight + 15, InstancePattern.TOTAL_MIN_WIDTH, 10);
					maxDepthText.setValue(costs.getMaxGateDepth().getMaxDepth() + " gate depth:");

					/* max gate depth action */
					final Text maxDepthActionText = service.createPlainText(roundRect);
					PropsUtil.setIdentifier(maxDepthActionText, "maxDepthAction-lbl");
					maxDepthActionText.setStyle(StyleUtil.costsText(getDiagram()));
					service.setLocationAndSize(maxDepthActionText, 0, rectHeight + 25, InstancePattern.TOTAL_MIN_WIDTH,
							10);
					maxDepthActionText.setValue("  " + costs.getMaxGateDepth().getActionName());

				}
			}
		}
	}

	/**
	 *
	 * @param instanceName
	 *            The name of the instance of an actor
	 * @return The total number of BRAMs for the actor, which is the sum of
	 *         BRAMs required for each action and also for global variables
	 *         (i.e. CAL arrays).
	 */
	private int brams(String instanceName) {
		String reportFile = xronosReportsDir + "/" + instanceName + "_ResourceUtilizationReport.html";
		String prefix = "Number of Block Rams:";
		int bram = 0;
		try {
			/* parse each line for this standard lexical structure */
			BufferedReader br = new BufferedReader(new FileReader(reportFile));
			String line = null;
			while ((line = br.readLine()) != null) {
				if (line.startsWith(prefix)) {
					bram += parseBramLine(line);
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bram;
	}

	private int parseBramLine(String line){
		String[] ss = line.split("Number of Block Rams:");
		String s = ss[1];
		while (s.charAt(0) == ' ' ) {
			s = s.substring(1, s.length());
		}
		return Integer.parseInt(s);
	}
	
	/**
	 *
	 * @param instanceName
	 *            The name of the instance of an actor
	 * @return The maximum gate depth in MaxGateDepth, which holds both the
	 *         maximum gate depth for the actor, and also the name of the action
	 *         responsible for that gate depth.
	 */
	private MaxGateDepth maxGateDepth(String instanceName) {
		MaxGateDepth maxDepth = new MaxGateDepth();
		String reportFile = xronosReportsDir + "/" + instanceName + ".xml";
		File fXmlFile = new File(reportFile);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList actions = doc.getElementsByTagName("Task");
			for (int i = 0; i < actions.getLength(); i++) {
				Node action = actions.item(i);
				if (action.getNodeType() == Node.ELEMENT_NODE) {
					Element actionElement = (Element) action;
					NodeList resourceList = actionElement.getElementsByTagName("Resource");
					Node resource = resourceList.item(0);
					if (resource.getNodeType() == Node.ELEMENT_NODE) {
						Element resourceElement = (Element) resource;
						int depth = Integer.parseInt(resourceElement.getAttribute("MaxGateDepth"));
						if (depth > maxDepth.getMaxDepth()) {
							maxDepth.setMaxDepth(depth, actionElement.getAttribute("name"));
						}
					}
				}
			}
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return maxDepth;
	}

	public enum RelativeDepthCost {
		LOW, HIGH, HIGHEST
	}

	/*
	 * The maximum gate depth is held in MaxGateDepth instances, which holds
	 * both the maximum gate depth for the actor, and also the name of the
	 * action responsible for that gate depth.
	 */
	private class MaxGateDepth {
		int maxDepth;
		String actionName;

		public MaxGateDepth() {
			maxDepth = 0;
			actionName = "";
		}

		void setMaxDepth(int depth, String action) {
			maxDepth = depth;
			actionName = action;
		}

		int getMaxDepth() {
			return maxDepth;
		}

		String getActionName() {
			return actionName;
		}
	}

	/*
	 * This class holds all information about the BRAM and maximum gate depth
	 * costs for an instance of an actor. The costs are parsed from HTML and XML
	 * files generated by the Xronos backend in rtl/report/ .
	 */
	class InstanceCosts {
		MaxGateDepth gateDepth;
		int brams;
		RelativeDepthCost relativeCost;

		public InstanceCosts(MaxGateDepth gateDepths, int brams) {
			this.gateDepth = gateDepths;
			this.brams = brams;
			this.relativeCost = RelativeDepthCost.LOW;
		}

		public void setRelativeDepthCost(RelativeDepthCost cost) {
			this.relativeCost = cost;
		}

		public MaxGateDepth getMaxGateDepth() {
			return gateDepth;
		}

		public int getBrams() {
			return brams;
		}

		public RelativeDepthCost getRelativeDepthCost() {
			return relativeCost;
		}
	}
}
