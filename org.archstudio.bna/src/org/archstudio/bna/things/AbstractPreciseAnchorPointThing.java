package org.archstudio.bna.things;

import java.awt.geom.Point2D;

import org.archstudio.bna.constants.StickyMode;
import org.archstudio.bna.facets.IHasMutablePreciseAnchorPoint;
import org.archstudio.bna.facets.IIsSticky;
import org.archstudio.bna.utils.BNAUtils;
import org.eclipse.swt.graphics.Point;

public abstract class AbstractPreciseAnchorPointThing extends AbstractRelativeMovableReferencePointThing implements
		IHasMutablePreciseAnchorPoint, IIsSticky {

	public AbstractPreciseAnchorPointThing(Object id) {
		super(id);
	}

	@Override
	protected void initProperties() {
		super.initProperties();
		setPreciseAnchorPoint(new Point2D.Double(0, 0));
		addShapeModifyingKey(PRECISION_ANCHOR_POINT_KEY);
	}

	@Override
	public Point2D getPreciseAnchorPoint() {
		return get(PRECISION_ANCHOR_POINT_KEY);
	}

	@Override
	public void setPreciseAnchorPoint(Point2D p) {
		set(PRECISION_ANCHOR_POINT_KEY, p);
	}

	@Override
	public Point getReferencePoint() {
		Point2D p = getPreciseAnchorPoint();
		return new Point(BNAUtils.round(p.getX()), BNAUtils.round(p.getY()));
	}

	@Override
	public void moveRelative(final Point moveDelta) {
		if (moveDelta.x != 0 || moveDelta.y != 0) {
			Point2D ap = getPreciseAnchorPoint();
			setPreciseAnchorPoint(new Point2D.Double(ap.getX() + moveDelta.x, ap.getY() + moveDelta.y));
		}
	}

	@Override
	public Point getStickyPointNear(StickyMode stickyMode, Point nearPoint) {
		Point2D p = getPreciseAnchorPoint();
		return new Point(BNAUtils.round(p.getX()), BNAUtils.round(p.getY()));
	}
}
