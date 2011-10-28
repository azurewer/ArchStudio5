package org.archstudio.bna.utils;

import java.lang.ref.WeakReference;

import org.archstudio.bna.IThing;
import org.archstudio.bna.IThing.IThingKey;
import org.archstudio.bna.IThingListener;
import org.archstudio.bna.ThingEvent;

public class WeakThingListener implements IThingListener {

	protected IThing t = null;
	protected WeakReference<IThingListener> listenerRef = null;

	public WeakThingListener(IThing t, IThingListener realListener) {
		this.t = t;
		this.listenerRef = new WeakReference<IThingListener>(realListener);
	}

	@Override
	public <ET extends IThing, EK extends IThingKey<EV>, EV> void thingChanged(ThingEvent<ET, EK, EV> thingEvent) {
		IThingListener realListener = listenerRef.get();
		if (realListener == null) {
			t.removeThingListener(this);
		}
		else {
			realListener.thingChanged(thingEvent);
		}
	}
}
