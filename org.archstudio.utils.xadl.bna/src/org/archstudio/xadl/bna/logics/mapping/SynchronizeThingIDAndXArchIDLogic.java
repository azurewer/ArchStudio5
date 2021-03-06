package org.archstudio.xadl.bna.logics.mapping;

import static org.archstudio.sysutils.SystemUtils.firstOrNull;

import java.util.Set;

import org.archstudio.bna.IBNAModelListener;
import org.archstudio.bna.IBNAWorld;
import org.archstudio.bna.IThing;
import org.archstudio.bna.keys.IThingKey;
import org.archstudio.bna.keys.IThingMetakey;
import org.archstudio.bna.keys.IThingRefKey;
import org.archstudio.bna.keys.ThingMetakey;
import org.archstudio.bna.logics.AbstractKeyCoordinatingThingLogic;
import org.archstudio.bna.utils.BNAUtils;
import org.archstudio.xadl.bna.facets.IHasXArchID;

import com.google.common.collect.Sets;

public class SynchronizeThingIDAndXArchIDLogic extends AbstractKeyCoordinatingThingLogic implements IBNAModelListener {

	private static final String XARCH_ID_NAME = ".xArchID";
	private final Set<IThingMetakey<?, IThingRefKey<?>, String>> xArchIDMetakeys = Sets.newHashSet();

	public SynchronizeThingIDAndXArchIDLogic(IBNAWorld world) {
		super(world, true);
		track(IHasXArchID.XARCH_ID_KEY);
	}

	public IThingMetakey<?, IThingRefKey<?>, String> syncXArchIDKeyToThingIDKey(IThingRefKey<?> thingRefKey) {
		BNAUtils.checkLock();

		track(thingRefKey);
		IThingMetakey<?, IThingRefKey<?>, String> xArchIDMetakey = _syncXArchIDKeyToThingIDKey(thingRefKey);
		xArchIDMetakeys.add(xArchIDMetakey);
		track(xArchIDMetakey);
		return xArchIDMetakey;
	}

	private IThingMetakey<?, IThingRefKey<?>, String> _syncXArchIDKeyToThingIDKey(IThingRefKey<?> thingRefKey) {
		return ThingMetakey.<String, IThingRefKey<?>, String> create(XARCH_ID_NAME, thingRefKey);
	}

	@Override
	protected void update(IThing thing, IThingKey<?> key) {
		if (IHasXArchID.XARCH_ID_KEY.equals(key)) {
			String xArchID = thing.get(IHasXArchID.XARCH_ID_KEY);
			if (xArchID != null) {
				for (IThingMetakey<?, IThingRefKey<?>, String> xArchIDKey : xArchIDMetakeys) {
					for (IThing thingWithXArchID : valueLogic.getThings(xArchIDKey, xArchID)) {
						thingWithXArchID.set(xArchIDKey.getKey(), thing.getID());
					}
				}
			}
		}
		else if (key instanceof IThingMetakey && XARCH_ID_NAME.equals(((IThingMetakey<?, ?, ?>) key).getName())) {
			@SuppressWarnings("unchecked")
			IThingMetakey<?, IThingRefKey<?>, String> xArchIDKey = (IThingMetakey<?, IThingRefKey<?>, String>) key;
			String xArchID = thing.get(xArchIDKey);
			if (xArchID != null) {
				thing.set(xArchIDKey.getKey(), firstOrNull(valueLogic.getThingIDs(IHasXArchID.XARCH_ID_KEY, xArchID)));
			}
			else {
				thing.remove(xArchIDKey.getKey());
			}
		}
		else if (key instanceof IThingRefKey) {
			IThingRefKey<?> refKey = (IThingRefKey<?>) key;
			IThingMetakey<?, IThingRefKey<?>, String> xArchIDKey = _syncXArchIDKeyToThingIDKey(refKey);
			String xArchID = null;
			if (thing.has(xArchIDKey)) {
				IThing referencedThing = refKey.get(thing, model);
				xArchID = referencedThing != null ? referencedThing.get(IHasXArchID.XARCH_ID_KEY) : null;
			}
			if (xArchID != null) {
				thing.set(xArchIDKey, xArchID);
			}
			else {
				thing.remove(xArchIDKey);
			}
		}
	}
}
