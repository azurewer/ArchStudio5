package org.archstudio.bna.logics.coordinating;

import org.archstudio.bna.BNAModelEvent;
import org.archstudio.bna.IBNAModel;
import org.archstudio.bna.IBNASynchronousModelListener;
import org.archstudio.bna.IThing;
import org.archstudio.bna.IThing.IThingKey;
import org.archstudio.bna.ThingEvent;
import org.archstudio.bna.facets.IHasBoundingBox;
import org.archstudio.bna.keys.IThingRefKey;
import org.archstudio.bna.keys.ThingRefKey;
import org.archstudio.bna.logics.AbstractThingLogic;
import org.archstudio.bna.things.labels.DirectionalLabelThing;

public class ReorientToThingIDLogic extends AbstractThingLogic implements IBNASynchronousModelListener {

	public static final IThingRefKey<IHasBoundingBox> REORIENT_TO_THING_KEY = ThingRefKey
			.create(ReorientToThingIDLogic.class);

	public IThingRefKey<IHasBoundingBox> getReorientToThingKey() {
		return REORIENT_TO_THING_KEY;
	}

	OrientDirectionalLabelLogic orientLogic = null;

	@Override
	protected void init() {
		super.init();

		orientLogic = addThingLogic(OrientDirectionalLabelLogic.class);
	}

	@Override
	public <ET extends IThing, EK extends IThingKey<EV>, EV> void bnaModelChangedSync(BNAModelEvent<ET, EK, EV> evt) {
		switch (evt.getEventType()) {
		case THING_ADDED: {
			reorientThing(evt.getSource(), evt.getTargetThing());
			break;
		}
		case THING_CHANGED: {
			ThingEvent<ET, EK, EV> te = evt.getThingEvent();
			if (REORIENT_TO_THING_KEY.equals(te.getPropertyName())) {
				reorientThing(evt.getSource(), te.getTargetThing());
			}
			break;
		}
		}
	}

	private void reorientThing(IBNAModel model, IThing orientationThing) {
		if (orientationThing instanceof DirectionalLabelThing) {
			IHasBoundingBox boundThing = REORIENT_TO_THING_KEY.get(orientationThing, model);
			if (boundThing != null) {
				orientLogic.orient(boundThing, (DirectionalLabelThing) orientationThing);
			}
			else {
				orientLogic.unorient((DirectionalLabelThing) orientationThing);
			}
		}
	}
}
