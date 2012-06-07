package org.archstudio.bna.graphs;

import org.archstudio.bna.AbstractCoordinateMapper;
import org.archstudio.bna.CoordinateMapperEvent.EventType;
import org.archstudio.bna.IMutableCoordinateMapper;
import org.archstudio.bna.IScrollableCoordinateMapper;
import org.archstudio.bna.utils.BNAUtils;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;

public class GraphCoordinateMapper extends AbstractCoordinateMapper implements IMutableCoordinateMapper,
		IScrollableCoordinateMapper {

	public static enum Type {
		LINEAR, LOGARITHMIC
	}

	protected Type xAxisType = Type.LOGARITHMIC;
	protected double localXAxisLogarithmicScale = 100;
	protected Type yAxisType = Type.LOGARITHMIC;
	protected double localYAxisLogarithmicScale = 100;

	public GraphCoordinateMapper(Type xAxisType, Type yAxisType) {
		this.xAxisType = xAxisType;
		this.yAxisType = yAxisType;
	}

	@Override
	public Rectangle getLocalBounds() {
		return new Rectangle(//
				BNAUtils.round(worldBounds.x * localScale), //
				BNAUtils.round(worldBounds.y * localScale), //
				BNAUtils.round(worldBounds.width * localScale), //
				BNAUtils.round(worldBounds.height * localScale));
	}

	public Type getXAxisType() {
		return xAxisType;
	}

	public void setXAxisType(Type xAxisType) {
		this.xAxisType = xAxisType;
		fireCoordinateMapperEvent(EventType.OTHER);
	}

	public Type getYAxisType() {
		return yAxisType;
	}

	public void setYAxisType(Type yAxisType) {
		this.yAxisType = yAxisType;
		fireCoordinateMapperEvent(EventType.OTHER);
	}

	@Override
	public Point worldToLocal(Point worldPoint) {

		double x = worldPoint.x;
		double y = worldPoint.y;

		switch (xAxisType) {
		case LINEAR:
			x = x * localScale - localOrigin.x;
			break;
		case LOGARITHMIC:
			x = log(x) * localXAxisLogarithmicScale * localScale - localOrigin.x;
			break;
		}

		switch (yAxisType) {
		case LINEAR:
			y = y * localScale - localOrigin.y;
			break;
		case LOGARITHMIC:
			y = log(y) * localYAxisLogarithmicScale * localScale - localOrigin.y;
			break;
		}

		return new Point(BNAUtils.round(x), BNAUtils.round(y));
	}

	@Override
	public Point localToWorld(Point localPoint) {

		double x = localPoint.x;
		double y = localPoint.y;

		switch (xAxisType) {
		case LINEAR:
			x = (x + localOrigin.x) / localScale;
			break;
		case LOGARITHMIC:
			x = pow((x + localOrigin.x) / localScale / localXAxisLogarithmicScale);
			break;
		}

		switch (yAxisType) {
		case LINEAR:
			y = (y + localOrigin.y) / localScale;
			break;
		case LOGARITHMIC:
			y = pow((y + localOrigin.y) / localScale / localYAxisLogarithmicScale);
			break;
		}

		return new Point(BNAUtils.round(x), BNAUtils.round(y));
	}

	protected double log(double n) {
		return n == 0 ? 0 : n < 0 ? -Math.log(-n) : Math.log(n);
	}

	protected double pow(double n) {
		return n < 0 ? -Math.pow(Math.E, -n) : Math.pow(Math.E, n);
	}
}