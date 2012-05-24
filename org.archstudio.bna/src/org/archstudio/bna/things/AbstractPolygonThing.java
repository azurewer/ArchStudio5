package org.archstudio.bna.things;

import java.util.List;

import org.archstudio.bna.ICoordinateMapper;
import org.archstudio.bna.constants.StickyMode;
import org.archstudio.bna.facets.IHasMutableAnchorPoint;
import org.archstudio.bna.facets.IHasMutablePoints;
import org.archstudio.bna.facets.IHasMutableReferencePoint;
import org.archstudio.bna.facets.IHasPoints;
import org.archstudio.bna.facets.IIsSticky;
import org.archstudio.bna.utils.BNAUtils;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionPoint;
import org.eclipse.draw2d.geometry.Rectangle;

public abstract class AbstractPolygonThing extends AbstractPointsThing implements IHasMutableAnchorPoint,
		IHasMutablePoints, IHasMutableReferencePoint, IIsSticky {

	public AbstractPolygonThing(Object id) {
		super(id);
	}

	@Override
	protected void initProperties() {
		setAnchorPoint(new Point(0, 0));
		super.initProperties();
		addShapeModifyingKey(POINTS_KEY);
		addShapeModifyingKey(ANCHOR_POINT_KEY);
	}

	@Override
	protected Rectangle calculateBoundingBox() {
		Rectangle r = super.calculateBoundingBox();
		Point a = getAnchorPoint();
		r.x += a.x;
		r.y += a.y;
		return r;
	}

	@Override
	public Point getAnchorPoint() {
		return get(ANCHOR_POINT_KEY);
	}

	@Override
	public void setAnchorPoint(Point p) {
		set(ANCHOR_POINT_KEY, p);
	}

	@Override
	public List<Point> getPoints() {
		return get(IHasPoints.POINTS_KEY);
	}

	@Override
	public void setPoints(List<Point> points) {
		set(IHasPoints.POINTS_KEY, points);
	}

	@Override
	public Point getReferencePoint() {
		return getAnchorPoint();
	}

	@Override
	public void setReferencePoint(Point worldPoint) {
		setAnchorPoint(worldPoint);
	}

	@Override
	public void moveRelative(final Point moveDelta) {
		if (moveDelta.x != 0 || moveDelta.y != 0) {
			synchronizedUpdate(new Runnable() {
				@Override
				public void run() {
					Point a = getAnchorPoint();
					a.x += moveDelta.x;
					a.y += moveDelta.y;
					setAnchorPoint(a);
				}
			});
		}
	}

	@Override
	public PrecisionPoint getStickyPointNear(StickyMode stickyMode, Point nearPoint) {
		Rectangle bb = getBoundingBox();
		Point center = new Point(bb.x + bb.width / 2, bb.y + bb.height / 2);
		switch (stickyMode) {
		case CENTER:
			return new PrecisionPoint(center.x, center.y);
		case EDGE:
			return BNAUtils.getClosestPointOnPolygon(
					BNAUtils.toXYArray(ICoordinateMapper.IDENTITY, getPoints(), getAnchorPoint()), nearPoint.x,
					nearPoint.y);
		case EDGE_FROM_CENTER:
			return BNAUtils.getClosestPointOnPolygon(
					BNAUtils.toXYArray(ICoordinateMapper.IDENTITY, getPoints(), getAnchorPoint()), nearPoint.x,
					nearPoint.y, center.x, center.y);
		}
		throw new IllegalArgumentException(stickyMode.name());
	}
}
