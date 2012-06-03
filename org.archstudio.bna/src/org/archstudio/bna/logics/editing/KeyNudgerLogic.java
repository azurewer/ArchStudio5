package org.archstudio.bna.logics.editing;

import org.archstudio.bna.IBNAModel;
import org.archstudio.bna.IBNAView;
import org.archstudio.bna.IThing;
import org.archstudio.bna.facets.IRelativeMovable;
import org.archstudio.bna.logics.AbstractThingLogic;
import org.archstudio.bna.utils.BNAUtils;
import org.archstudio.bna.utils.GridUtils;
import org.archstudio.bna.utils.IBNAKeyListener;
import org.archstudio.bna.utils.UserEditableUtils;
import org.archstudio.swtutils.constants.Orientation;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;

public class KeyNudgerLogic extends AbstractThingLogic implements IBNAKeyListener {

	@Override
	public void keyPressed(IBNAView view, KeyEvent e) {
		if (e.keyCode == SWT.ARROW_LEFT || e.keyCode == SWT.ARROW_UP || e.keyCode == SWT.ARROW_DOWN
				|| e.keyCode == SWT.ARROW_RIGHT) {
			IBNAModel model = getBNAModel();
			if (model != null) {
				model.beginBulkChange();
				try {
					Orientation o = orientationForKeyCode(e.keyCode);
					int gridSpacing = GridUtils.getGridSpacing(view.getBNAWorld().getBNAModel());
					int distance = gridSpacing == 0 ? 5 : gridSpacing;
					for (IThing t : BNAUtils.getSelectedThings(view.getBNAWorld().getBNAModel())) {
						if (t instanceof IRelativeMovable) {
							nudge(o, distance, (IRelativeMovable) t);
							//if(gridSpacing != 0){
							//	GridUtils.rectifyToGrid(gridSpacing, (IRelativeMovable)t);
							//}
						}
					}
				}
				finally {
					model.endBulkChange();
				}
			}
		}
	}

	@Override
	public void keyReleased(IBNAView view, KeyEvent e) {
	}

	private Orientation orientationForKeyCode(int keyCode) {
		switch (keyCode) {
		case SWT.ARROW_LEFT:
			return Orientation.WEST;
		case SWT.ARROW_UP:
			return Orientation.NORTH;
		case SWT.ARROW_RIGHT:
			return Orientation.EAST;
		case SWT.ARROW_DOWN:
			return Orientation.SOUTH;
		default:
			throw new IllegalArgumentException("Invalid key code");
		}
	}

	private Point nudge(Orientation o, int distance, Point p) {
		Point p2 = new Point(p.x, p.y);
		switch (o) {
		case NORTH:
			p2.y -= distance;
			break;
		case EAST:
			p2.x += distance;
			break;
		case SOUTH:
			p2.y += distance;
			break;
		case WEST:
			p2.x -= distance;
			break;
		}
		return p2;
	}

	protected void nudge(final Orientation o, final int distance, final IRelativeMovable t) {
		if (UserEditableUtils.isEditableForAllQualities(t, IRelativeMovable.USER_MAY_MOVE)) {
			t.moveRelative(nudge(o, distance, new Point(0, 0)));
		}
	}
}
