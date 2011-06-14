package org.archstudio.bna.things;

import java.util.Set;

import org.archstudio.bna.constants.StickyMode;
import org.archstudio.bna.facets.IHasMutableAnchorPoint;
import org.archstudio.bna.facets.IIsSticky;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PrecisionPoint;

import com.google.common.collect.Sets;

public abstract class AbstractAnchorPointThing extends AbstractRelativeMovableThing implements IHasMutableAnchorPoint,
		IIsSticky {

	public AbstractAnchorPointThing(Object id) {
		super(id);
	}

	@Override
	protected void initProperties() {
		super.initProperties();
		setAnchorPoint(new Point(0, 0));
		addEdgeModifyingKey(ANCHOR_POINT_KEY);
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
	public Point getReferencePoint() {
		return getAnchorPoint();
	}

	@Override
	public void moveRelative(final Point moveDelta) {
		if (moveDelta.x != 0 || moveDelta.y != 0) {
			synchronizedUpdate(new Runnable() {
				@Override
				public void run() {
					setAnchorPoint(getAnchorPoint().translate(moveDelta));
				}
			});
		}
	}

	protected void addEdgeModifyingKey(final IThingKey<?> key) {
		synchronizedUpdate(new Runnable() {
			@Override
			public void run() {
				Set<IThingKey<?>> keys = Sets.newHashSet(getStickyModifyingKeys());
				keys.add(key);
				set(STICKY_MODIFYING_KEYS_KEY, keys);
			}
		});
	}

	@Override
	public Iterable<IThingKey<?>> getStickyModifyingKeys() {
		return get(STICKY_MODIFYING_KEYS_KEY);
	}

	@Override
	public PrecisionPoint getStickyPointNear(StickyMode stickyMode, Point nearPoint, Point refPoint) {
		return new PrecisionPoint(getAnchorPoint());
	}
}