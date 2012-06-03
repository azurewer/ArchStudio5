package org.archstudio.bna.logics.coordinating;

import org.archstudio.bna.BNAModelEvent;
import org.archstudio.bna.IBNAModel;
import org.archstudio.bna.IBNAModelListener;
import org.archstudio.bna.IThing;
import org.archstudio.bna.IThing.IThingKey;
import org.archstudio.bna.ThingEvent;
import org.archstudio.bna.keys.IThingRefKey;
import org.archstudio.bna.keys.ThingRefKey;
import org.archstudio.bna.logics.AbstractThingLogic;

public class ReparentToThingIDLogic extends AbstractThingLogic implements IBNAModelListener {

	public static final IThingRefKey<IThing> REPARENT_TO_THING_KEY = ThingRefKey.create(ReparentToThingIDLogic.class);

	public IThingRefKey<IThing> getReparentToThingIDKey() {
		return REPARENT_TO_THING_KEY;
	}

	@Override
	public <ET extends IThing, EK extends IThingKey<EV>, EV> void bnaModelChanged(BNAModelEvent<ET, EK, EV> evt) {
		switch (evt.getEventType()) {
		case THING_ADDED: {
			reparentThing(evt.getSource(), evt.getTargetThing());
			break;
		}
		case THING_CHANGED: {
			ThingEvent<ET, EK, EV> te = evt.getThingEvent();
			if (REPARENT_TO_THING_KEY.equals(te.getPropertyName())) {
				reparentThing(evt.getSource(), te.getTargetThing());
			}
			break;
		}
		}
	}

	private void reparentThing(IBNAModel model, IThing childThing) {
		IThing parentThing = REPARENT_TO_THING_KEY.get(childThing, model);
		if (parentThing != null) {
			model.reparent(parentThing, childThing);
		}
	}
}
