package org.archstudio.bna.logics.editing;

import java.util.List;

import org.archstudio.bna.IBNAModel;
import org.archstudio.bna.IBNAView;
import org.archstudio.bna.ICoordinate;
import org.archstudio.bna.IThing;
import org.archstudio.bna.facets.IHasBoundingBox;
import org.archstudio.bna.facets.IHasMutableSelected;
import org.archstudio.bna.logics.AbstractThingLogic;
import org.archstudio.bna.logics.tracking.ThingTypeTrackingLogic;
import org.archstudio.bna.things.borders.MarqueeBoxBorderThing;
import org.archstudio.bna.utils.BNAUtils;
import org.archstudio.bna.utils.IBNAMouseListener;
import org.archstudio.bna.utils.IBNAMouseMoveListener;
import org.archstudio.bna.utils.UserEditableUtils;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

import com.google.common.collect.Iterables;

public class MarqueeSelectionLogic extends AbstractThingLogic implements IBNAMouseListener, IBNAMouseMoveListener {

	protected ThingTypeTrackingLogic typesLogic;

	protected MarqueeBoxBorderThing marqueeSelection = null;
	protected Point initDownWorldPoint = new Point(0, 0);

	public MarqueeSelectionLogic() {
	}

	@Override
	protected void init() {
		super.init();
		this.typesLogic = addThingLogic(ThingTypeTrackingLogic.class);
	}

	@Override
	protected void destroy() {
		typesLogic = null;
		if (marqueeSelection != null) {
			IBNAModel m = getBNAModel();
			if (m != null) {
				m.removeThing(marqueeSelection);
			}
		}
		super.destroy();
	}

	@Override
	public void mouseDown(IBNAView view, MouseEvent evt, List<IThing> t, ICoordinate location) {
		if (marqueeSelection != null) {
			getBNAModel().removeThing(marqueeSelection);
			marqueeSelection = null;
		}
		if (evt.button == 1) {
			if (t.isEmpty()) {
				initDownWorldPoint = location.getWorldPoint();
				marqueeSelection = getBNAModel().addThing(new MarqueeBoxBorderThing(null));
				marqueeSelection.setBoundingBox(new Rectangle(initDownWorldPoint.x, initDownWorldPoint.y, 1, 1));
			}
		}
	}

	@Override
	public void mouseMove(IBNAView view, MouseEvent evt, List<IThing> things, ICoordinate location) {
		if (marqueeSelection != null) {
			Point worldPoint = location.getWorldPoint();
			int x1 = Math.min(initDownWorldPoint.x, worldPoint.x);
			int x2 = Math.max(initDownWorldPoint.x, worldPoint.x);
			int y1 = Math.min(initDownWorldPoint.y, worldPoint.y);
			int y2 = Math.max(initDownWorldPoint.y, worldPoint.y);
			marqueeSelection.setBoundingBox(new Rectangle(x1, y1, x2 - x1, y2 - y1));
		}
	}

	@Override
	public void mouseUp(IBNAView view, MouseEvent evt, List<IThing> t, ICoordinate location) {
		try {
			if (evt.button == 1) {
				if (marqueeSelection != null) {
					Rectangle selectionRectangle = marqueeSelection.getBoundingBox();
					selectionRectangle = BNAUtils.normalizeRectangle(selectionRectangle);

					IBNAModel model = getBNAModel();
					model.beginBulkChange();
					try {
						for (IHasMutableSelected mst : Iterables.filter(
								BNAUtils.getThings(model, typesLogic.getThingIDs(IHasMutableSelected.class)),
								IHasMutableSelected.class)) {
							if (!BNAUtils.wasControlPressed(evt)) {
								mst.setSelected(false);
							}
							if (mst instanceof IHasBoundingBox
									&& UserEditableUtils.isEditableForAllQualities(mst,
											IHasMutableSelected.USER_MAY_SELECT)) {
								Rectangle r = ((IHasBoundingBox) mst).getBoundingBox();
								if (BNAUtils.isWithin(selectionRectangle, r)) {
									if (!BNAUtils.wasControlPressed(evt)) {
										mst.setSelected(true);
									}
									else {
										mst.setSelected(!mst.isSelected());
									}
								}
							}
						}
					}
					finally {
						model.endBulkChange();
					}
				}
			}
		}
		finally {
			if (marqueeSelection != null) {
				getBNAModel().removeThing(marqueeSelection);
				marqueeSelection = null;
			}
		}
	}
}
