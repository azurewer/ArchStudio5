package org.archstudio.bna.things;

import org.archstudio.bna.IBNAView;
import org.archstudio.bna.ICoordinate;
import org.archstudio.bna.ICoordinateMapper;
import org.eclipse.swt.graphics.Rectangle;

public abstract class AbstractRectangleThingPeer<T extends AbstractRectangleThing> extends AbstractThingPeer<T> {

	public AbstractRectangleThingPeer(T thing) {
		super(thing);
	}

	@Override
	public boolean isInThing(IBNAView view, ICoordinateMapper cm, ICoordinate location) {
		return t.getBoundingBox().contains(location.getWorldPoint());
	}

	@Override
	public Rectangle getLocalBounds(IBNAView view, ICoordinateMapper cm) {
		return cm.worldToLocal(t.getBoundingBox());
	}
}
