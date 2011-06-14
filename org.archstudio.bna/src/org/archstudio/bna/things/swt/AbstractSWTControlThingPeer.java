package org.archstudio.bna.things.swt;

import org.archstudio.bna.IBNAView;
import org.archstudio.bna.ICoordinate;
import org.archstudio.bna.ICoordinateMapper;
import org.archstudio.bna.IRegion;
import org.archstudio.bna.IResources;
import org.archstudio.bna.IThing;
import org.archstudio.bna.IThing.IThingKey;
import org.archstudio.bna.IThingListener;
import org.archstudio.bna.ThingEvent;
import org.archstudio.bna.things.AbstractRectangleThingPeer;
import org.archstudio.swtutils.SWTWidgetUtils;
import org.eclipse.draw2d.Graphics;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public abstract class AbstractSWTControlThingPeer<T extends AbstractSWTThing, C extends Control> extends
		AbstractRectangleThingPeer<T> {

	private C control;

	public AbstractSWTControlThingPeer(T thing) {
		super(thing);
		t.addThingListener(new IThingListener() {

			@Override
			public <ET extends IThing, EK extends IThingKey<EV>, EV> void thingChanged(ThingEvent<ET, EK, EV> thingEvent) {
				if (!AbstractSWTControlThingPeer.this.t.isEditing() && control != null) {
					disposeControl(control);
					control = null;
				}
			}
		});
	}

	abstract protected C createControl(IBNAView view, ICoordinateMapper cm, Graphics g, IResources r);

	abstract protected void updateControl(C control, IBNAView view, ICoordinateMapper cm, Graphics g, IResources r);

	protected void disposeControl(final C control) {
		SWTWidgetUtils.async(control, new Runnable() {
			@Override
			public void run() {
				control.dispose();
			}
		});
	}

	@Override
	public void dispose() {
		super.dispose();
		disposeControl(control);
		control = null;
	}

	@Override
	public void draw(IBNAView view, ICoordinateMapper cm, Graphics g, IResources r, IRegion localClip, IRegion worldClip) {
		Composite composite = r.getComposite();
		if (composite == null) {
			return;
		}

		if (t.isEditing() && control == null) {
			//It has been made visible but we have no corresponding control
			control = createControl(view, cm, g, r);
			if (composite.isFocusControl()) {
				control.forceFocus();
			}
		}
		else if (!t.isEditing() && control != null) {
			//It has been made invisible but we are still showing the control
			disposeControl(control);
			control = null;
		}
		else if (control != null && !control.isDisposed()) {
			//Update the control
			updateControl(control, view, cm, g, r);
		}
	}

	@Override
	public boolean isInThing(IBNAView view, ICoordinateMapper cm, ICoordinate location) {
		return false;
	}
}