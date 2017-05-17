package org.archstudio.graphlayout;

import java.util.HashMap;
import java.util.Map;

public class GraphLayoutParameters implements java.io.Serializable {

	private static final long serialVersionUID = 7404468772149560496L;

	// Relative factors should be close to 1.0, but if you want connectors to be
	// twice as wide as they are tall, set relativeConnectorHeight to 1.0
	// and relativeConnectorWidth to 2.0. Dot seems to work best if the smallest
	// dimension given is 1.0d

	// The scale factor tells how many units '1.0' should be.

	protected double scale = 100.0d;

	protected double relativeComponentWidth = 1.0d;
	protected double relativeComponentHeight = 1.0d;

	protected double relativeCoalAgentWidth = 1.0d;
	protected double relativeCoalAgentHeight = 1.0d;

	protected double relativeFunAgentWidth = 1.0d;
	protected double relativeFunAgentHeight = 1.0d;
	protected double relativeEncapsulatedUnitWidth = 1.0d;
	protected double relativeEncapsulatedUnitHeight = 1.0d;

	protected double relativeConnectorWidth = 1.0d;
	protected double relativeConnectorHeight = 1.0d;

	protected Map<String, Object> otherProperties;

	public GraphLayoutParameters() {
		otherProperties = new HashMap<String, Object>();
	}

	public void setProperty(String name, Object value) {
		otherProperties.put(name, value);
	}

	public Object getProperty(String name) {
		return otherProperties.get(name);
	}

	public void removeProperty(String name) {
		otherProperties.remove(name);
	}

	public double getRelativeComponentHeight() {
		return relativeComponentHeight;
	}

	public double getRelativeComponentWidth() {
		return relativeComponentWidth;
	}

	// ***************wjw201705***************//
	public double getRelativeCoalAgentHeight() {
		return relativeCoalAgentHeight;
	}

	public double getRelativeCoalAgentWidth() {
		return relativeCoalAgentWidth;
	}

	public double getRelativeFunAgentHeight() {
		return relativeFunAgentHeight;
	}

	public double getRelativeFunAgentWidth() {
		return relativeFunAgentWidth;
	}

	public double getRelativeEncapsulatedUnitHeight() {
		return relativeEncapsulatedUnitHeight;
	}

	public double getRelativeEncapsulatedUnitWidth() {
		return relativeEncapsulatedUnitWidth;
	}
	// ***************wjw201705***************//

	public double getRelativeConnectorHeight() {
		return relativeConnectorHeight;
	}

	public double getRelativeConnectorWidth() {
		return relativeConnectorWidth;
	}

	public double getScale() {
		return scale;
	}

	public void setRelativeComponentHeight(double d) {
		relativeComponentHeight = d;
	}

	public void setRelativeComponentWidth(double d) {
		relativeComponentWidth = d;
	}

	// ***************wjw201705***************//
	public void setRelativeCoalAgentHeight(double d) {
		relativeCoalAgentHeight = d;
	}

	public void setRelativeCoalAgentWidth(double d) {
		relativeCoalAgentWidth = d;
	}

	public void setRelativeFunAgentHeight(double d) {
		relativeFunAgentHeight = d;
	}

	public void setRelativeFunAgentWidth(double d) {
		relativeFunAgentWidth = d;
	}

	public void setRelativeEncapsulatedUnitHeight(double d) {
		relativeEncapsulatedUnitHeight = d;
	}

	public void setRelativeEncapsulatedUnitWidth(double d) {
		relativeEncapsulatedUnitWidth = d;
	}
	// ***************wjw201705***************//

	public void setRelativeConnectorHeight(double d) {
		relativeConnectorHeight = d;
	}

	public void setRelativeConnectorWidth(double d) {
		relativeConnectorWidth = d;
	}

	public void setScale(double d) {
		scale = d;
	}

}
