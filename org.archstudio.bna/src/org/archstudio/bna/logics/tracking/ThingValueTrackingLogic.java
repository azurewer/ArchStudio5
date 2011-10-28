package org.archstudio.bna.logics.tracking;

import java.util.Collection;

import org.archstudio.bna.BNAModelEvent;
import org.archstudio.bna.IBNASynchronousModelListener;
import org.archstudio.bna.IThing;
import org.archstudio.bna.IThing.IThingKey;
import org.archstudio.bna.ThingEvent;
import org.archstudio.bna.logics.AbstractThingLogic;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;

public class ThingValueTrackingLogic extends AbstractThingLogic implements IBNASynchronousModelListener {

	private final Cache<IThingKey<?>, SetMultimap<Object, Object>> keyToValueToThingIDsCache = CacheBuilder.newBuilder()
			.build(new CacheLoader<IThingKey<?>, SetMultimap<Object, Object>>() {
				@Override
				public SetMultimap<Object, Object> load(IThingKey<?> input) {
					SetMultimap<Object, Object> m = HashMultimap.create();
					for (IThing t : getBNAModel().getAllThings()) {
						Object value = t.get(input);
						if (value != null) {
							m.put(value, t.getID());
						}
					}
					return m;
				}
			});

	public ThingValueTrackingLogic() {
	}

	@Override
	protected void destroy() {
		keyToValueToThingIDsCache.invalidateAll();
		super.destroy();
	}

	@Override
	public <ET extends IThing, EK extends IThing.IThingKey<EV>, EV> void bnaModelChangedSync(
			BNAModelEvent<ET, EK, EV> evt) {
		switch (evt.getEventType()) {
		case THING_ADDED: {
			ET thing = evt.getTargetThing();
			Object thingID = thing.getID();
			for (IThingKey<?> k : thing.keySet()) {
				update(thingID, k, null, thing.get(k));
			}
			break;
		}
		case THING_CHANGED: {
			ET thing = evt.getTargetThing();
			ThingEvent<ET, EK, EV> te = evt.getThingEvent();
			update(thing.getID(), te.getPropertyName(), te.getOldPropertyValue(), te.getNewPropertyValue());
			break;
		}
		case THING_REMOVED: {
			ET thing = evt.getTargetThing();
			Object thingID = thing.getID();
			for (IThingKey<?> k : thing.keySet()) {
				update(thingID, k, thing.get(k), null);
			}
			break;
		}
		}
	}

	private <V> void update(Object thingID, IThingKey<V> forKey, Object oldValue, Object newValue) {
		synchronized (keyToValueToThingIDsCache) {
			if (keyToValueToThingIDsCache.asMap().containsKey(forKey)) {
				Multimap<Object, Object> valueToThingIDsMap = keyToValueToThingIDsCache.getUnchecked(forKey);
				if (oldValue != null) {
					valueToThingIDsMap.get(oldValue).remove(thingID);
				}
				if (newValue != null) {
					valueToThingIDsMap.get(newValue).add(thingID);
				}
			}
		}
	}

	public <V> Collection<Object> getThingIDs(IThingKey<V> withKey, V ofValue) {
		synchronized (keyToValueToThingIDsCache) {
			return Lists.newArrayList(keyToValueToThingIDsCache.getUnchecked(withKey).get(ofValue));
		}
	}

	public <V1, V2> Collection<Object> getThingIDs(IThingKey<V1> withKey1, V1 ofValue1, IThingKey<V2> withKey2,
			V2 ofValue2) {
		synchronized (keyToValueToThingIDsCache) {
			return Lists.newArrayList(Sets.intersection(keyToValueToThingIDsCache.getUnchecked(withKey1).get(ofValue1),
					keyToValueToThingIDsCache.getUnchecked(withKey2).get(ofValue2)));
		}
	}

}
