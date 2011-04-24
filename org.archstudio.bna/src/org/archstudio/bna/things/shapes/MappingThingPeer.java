package org.archstudio.bna.things.shapes;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

import org.archstudio.bna.DefaultCoordinateMapper;
import org.archstudio.bna.IBNAView;
import org.archstudio.bna.ICoordinateMapper;
import org.archstudio.bna.IThing;
import org.archstudio.bna.things.utility.WorldThing;
import org.archstudio.bna.things.utility.WorldThingPeer;

public class MappingThingPeer extends SplineThingPeer {
	protected MappingThing lt;
	protected DefaultCoordinateMapper cm2 = new DefaultCoordinateMapper();

	public MappingThingPeer(IThing t) {
		super(t);
		if (!(t instanceof MappingThing)) {
			throw new IllegalArgumentException("MappingThingPeer can only peer for MappingThing");
		}
		this.lt = (MappingThing) t;
	}

	protected WorldThing getViewThing(IBNAView outerView) {
		String viewThingID = lt.getWorldThingID();
		if (viewThingID != null) {
			IThing potentialViewThing = outerView.getWorld().getBNAModel().getThing(viewThingID);
			if ((potentialViewThing != null) && (potentialViewThing instanceof WorldThing)) {
				return (WorldThing) potentialViewThing;
			}
		}
		return null;
	}

	protected IBNAView getInternalView(IBNAView outerView, WorldThing vt) {
		if (vt == null)
			return null;
		WorldThingPeer vtp = (WorldThingPeer) outerView.getPeer(vt);
		if (vtp == null)
			return null;
		IBNAView internalView = vtp.getInnerView();
		//IBNAView internalView = ((IHasWorld)vt).getView();
		if (internalView != null) {
			return internalView;
		}
		return null;
	}

	protected void updateLocalBoundingBox(IBNAView view, ICoordinateMapper cm) {
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int maxY = Integer.MIN_VALUE;

		updateLocalPoints(view, cm);
		for (int i = 0; i < localPoints.length; i += 2) {
			int x = localPoints[i];
			int y = localPoints[i + 1];
			if (x < minX)
				minX = x;
			if (x > maxX)
				maxX = x;
			if (y < minY)
				minY = y;
			if (y > maxY)
				maxY = y;
		}
		localBoundingBox = new Rectangle(minX, minY, maxX - minX, maxY - minY);
		//localBoundingBox =  BNAUtils.worldToLocal(cm, BNAUtils.normalizeRectangle(lt.getBoundingBox()));
	}

	protected void updateLocalPoints(IBNAView view, ICoordinateMapper cm) {
		super.updateLocalPoints(view, cm);

		Point wp2 = lt.getEndpoint2();
		if (wp2 == null)
			wp2 = new Point(0, 0);

		WorldThing vt = getViewThing(view);
		IBNAView internalView = getInternalView(view, vt);
		if (internalView != null) {
			WorldThingPeer.setupCoordinateMapper(vt, cm, cm2);
		}

		//Here, we calculate endpoint 2 in cm2's coordinate system
		localPoints[localPoints.length - 2] = cm2.worldXtoLocalX(wp2.x);
		localPoints[localPoints.length - 1] = cm2.worldYtoLocalY(wp2.y);
	}
}
