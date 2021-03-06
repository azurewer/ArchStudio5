package org.archstudio.xadl.bna.logics.mapping;

import static org.archstudio.sysutils.SystemUtils.castOrNull;
import static org.archstudio.sysutils.SystemUtils.firstOrNull;

import java.awt.geom.Point2D;

import org.archstudio.bna.BNAModelEvent;
import org.archstudio.bna.IBNAModelListener;
import org.archstudio.bna.IBNAWorld;
import org.archstudio.bna.IThing;
import org.archstudio.bna.ThingEvent;
import org.archstudio.bna.facets.IHasInternalWorldPoint;
import org.archstudio.bna.facets.IHasStickyShape;
import org.archstudio.bna.facets.IHasWorld;
import org.archstudio.bna.keys.IThingKey;
import org.archstudio.bna.keys.IThingMetakey;
import org.archstudio.bna.keys.ThingMetakey;
import org.archstudio.bna.logics.AbstractThingLogic;
import org.archstudio.bna.logics.events.WorldThingInternalEventsLogic;
import org.archstudio.bna.logics.events.WorldThingInternalEventsLogic.IInternalBNAModelListener;
import org.archstudio.bna.logics.tracking.ThingValueTrackingLogic;
import org.archstudio.bna.things.shapes.MappingThing;
import org.archstudio.bna.utils.BNAUtils;
import org.archstudio.xadl.bna.facets.IHasObjRef;
import org.archstudio.xarchadt.ObjRef;

public class MaintainMappingLogic extends AbstractThingLogic implements IBNAModelListener, IInternalBNAModelListener {

	public static final IThingMetakey<String, IThingKey<Point2D>, Object> INTERNAL_THING_KEY = ThingMetakey.create(
			".&internalThingID", IHasInternalWorldPoint.INTERNAL_POINT_KEY);
	public static final IThingMetakey<String, IThingKey<Point2D>, ObjRef> INTERNAL_OBJREF_KEY = ThingMetakey.create(
			".internalObjRef", IHasInternalWorldPoint.INTERNAL_POINT_KEY);

	protected final ThingValueTrackingLogic valueLogic;

	public MaintainMappingLogic(IBNAWorld world) {
		super(world);
		this.valueLogic = logics.addThingLogic(ThingValueTrackingLogic.class);
		logics.addThingLogic(WorldThingInternalEventsLogic.class);
	}

	@Override
	public void bnaModelChanged(BNAModelEvent evt) {
		BNAUtils.checkLock();

		switch (evt.getEventType()) {
		case THING_ADDED: {
			IThing t = evt.getTargetThing();
			if (t instanceof MappingThing) {
				updateThing((MappingThing) t);
			}
		}
			break;
		case THING_CHANGED: {
			IThing t = evt.getTargetThing();
			if (t instanceof MappingThing) {
				if (evt.getThingEvent().getPropertyName().equals(INTERNAL_THING_KEY)
						|| evt.getThingEvent().getPropertyName().equals(INTERNAL_OBJREF_KEY)
						|| evt.getThingEvent().getPropertyName().equals(IHasInternalWorldPoint.INTERNAL_WORLD_KEY)) {
					updateThing((MappingThing) t);
				}
			}
			break;
		}
		default:
			// do nothing
		}
	}

	@Override
	public void internalBNAModelChanged(IHasWorld src, BNAModelEvent evt) {
		BNAUtils.checkLock();

		switch (evt.getEventType()) {
		case THING_ADDED: {
			IThing t = evt.getTargetThing();
			updateObjRef(src, t.getID(), null, t.get(IHasObjRef.OBJREF_KEY));
			updateEndpoint(src, t);
		}
			break;
		case THING_CHANGED: {
			ThingEvent tevt = evt.getThingEvent();
			IThing t = evt.getTargetThing();
			if (evt.getThingEvent().getPropertyName().equals(IHasObjRef.OBJREF_KEY)) {
				updateObjRef(src, t.getID(), (ObjRef) tevt.getOldPropertyValue(), (ObjRef) tevt.getNewPropertyValue());
			}
			updateEndpoint(src, t);
		}
			break;
		case THING_REMOVED: {
			IThing t = evt.getTargetThing();
			updateObjRef(src, t.getID(), t.get(IHasObjRef.OBJREF_KEY), null);
		}
			break;
		default:
			// do nothing
		}

	}

	private void updateThing(MappingThing t) {
		IHasWorld worldThing = t.getInternalWorld(model);
		if (worldThing != null) {
			IBNAWorld iWorld = worldThing.getWorld();
			if (iWorld != null) {
				ObjRef objRef = t.get(INTERNAL_OBJREF_KEY);
				if (objRef != null) {
					ThingValueTrackingLogic iValueLogic = iWorld.getThingLogicManager().addThingLogic(
							ThingValueTrackingLogic.class);
					IHasStickyShape iThing = castOrNull(
							firstOrNull(iValueLogic.getThings(IHasObjRef.OBJREF_KEY, objRef)), IHasStickyShape.class);
					if (iThing != null) {
						t.set(INTERNAL_THING_KEY, iThing.getID());
						t.setInternalPoint(BNAUtils.getCentralPoint(iThing));
					}
				}
			}
		}
	}

	protected void updateObjRef(IHasWorld worldThing, Object innerThingId, ObjRef oldObjRef, ObjRef newObjRef) {
		if (oldObjRef != null) {
			for (IThing t : valueLogic.getThings(INTERNAL_OBJREF_KEY, oldObjRef,
					IHasInternalWorldPoint.INTERNAL_WORLD_KEY, worldThing.getID())) {
				t.set(INTERNAL_THING_KEY, null);
			}
		}
		if (newObjRef != null) {
			for (IThing t : valueLogic.getThings(INTERNAL_OBJREF_KEY, newObjRef,
					IHasInternalWorldPoint.INTERNAL_WORLD_KEY, worldThing.getID())) {
				t.set(INTERNAL_THING_KEY, innerThingId);
			}
		}
	}

	private void updateEndpoint(IHasWorld worldThing, IThing changedThing) {
		if (changedThing instanceof IHasStickyShape) {
			Point2D endpoint = BNAUtils.getCentralPoint(changedThing);
			for (IThing t : valueLogic.getThings(INTERNAL_THING_KEY, changedThing.getID(),
					IHasInternalWorldPoint.INTERNAL_WORLD_KEY, worldThing.getID())) {
				t.set(IHasInternalWorldPoint.INTERNAL_POINT_KEY, endpoint);
			}
		}
	}
}
