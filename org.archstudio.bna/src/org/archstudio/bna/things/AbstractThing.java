package org.archstudio.bna.things;

import static com.google.common.base.Preconditions.checkState;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.OverridingMethodsMustInvokeSuper;

import org.archstudio.bna.IThing;
import org.archstudio.bna.IThingListener;
import org.archstudio.bna.IThingPeer;
import org.archstudio.bna.ThingEvent;
import org.archstudio.sysutils.SystemUtils;
import org.archstudio.sysutils.TypedHashMap;
import org.archstudio.sysutils.TypedMap;
import org.archstudio.sysutils.TypedMap.Key;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.MapMaker;
import com.google.common.collect.Sets;

public class AbstractThing implements IThing {

	private static final AtomicLong atomicLong = new AtomicLong(0);

	private static final Map<Class<IThing>, Class<IThingPeer<?>>> autoDefaultPeerClass = new MapMaker()
			.makeComputingMap(new Function<Class<IThing>, Class<IThingPeer<?>>>() {
				@SuppressWarnings("unchecked")
				@Override
				public Class<IThingPeer<?>> apply(Class<IThing> input) {
					Class<?> thingClass = input;
					while (thingClass != null) {
						try {
							return (Class<IThingPeer<?>>) Class.forName(//
									thingClass.getName() + "Peer", false, thingClass.getClassLoader());
						}
						catch (ClassNotFoundException e) {
						}
						thingClass = thingClass.getSuperclass();
					}
					throw new RuntimeException(new ClassNotFoundException("IThingPeer not found: " + input.getName()));
				}
			});

	private final Object id;
	private final TypedMap properties = TypedHashMap.create();
	private boolean initedProperties = false;

	private int synchronizedUpdateCount = 0;
	private List<ThingEvent<?, ?, ?>> synchronizedUpdateEvents = null;

	public AbstractThing(Object id) {
		this.id = id == null ? Long.valueOf(atomicLong.getAndIncrement()) : id;
		initProperties();
		checkState(initedProperties, "Thing %s must call super.initPropeties().", this.getClass().getName());
	}

	@Override
	public final Object getID() {
		return id;
	}

	@OverridingMethodsMustInvokeSuper
	protected void initProperties() {
		checkState(!initedProperties, "Thing " + this.getClass().getName()
				+ " may only call super.initPropeties() once.");
		initedProperties = true;
	}

	@Override
	public Class<? extends IThingPeer<?>> getPeerClass() {
		return autoDefaultPeerClass.get(this.getClass());
	}

	private final CopyOnWriteArrayList<IThingListener> thingListeners = new CopyOnWriteArrayList<IThingListener>();

	@Override
	public void addThingListener(IThingListener thingListener) {
		thingListeners.add(thingListener);
	}

	@Override
	public void removeThingListener(IThingListener thingListener) {
		thingListeners.remove(thingListener);
	}

	protected <EK extends IThingKey<EV>, EV> void fireThingEvent(ThingEvent<IThing, EK, EV> evt) {
		for (IThingListener l : thingListeners) {
			try {
				l.thingChanged(evt);
			}
			catch (Throwable t) {
				t.printStackTrace();
			}
		}
	}

	@Override
	public <V> V get(IThingKey<V> key) {
		return key.postRead(getRaw(key));
	}

	private <V> V getRaw(IThingKey<V> key) {
		synchronized (properties) {
			return properties.get(key);
		}
	}

	@Override
	public <V> V set(IThingKey<V> key, V value) {
		return setRaw(key, key.preWrite(value));
	}

	@Override
	@Deprecated
	public <V> V setProperty(IThingKey<V> key, V value) {
		return set(key, value);
	}

	private <V> V setRaw(IThingKey<V> key, V value) {
		V oldValue;
		ThingEvent<IThing, IThingKey<V>, V> evt = null;
		synchronized (properties) {
			modCount++;
			oldValue = properties.put(key, value);
			if (key.isFireEventOnChange() && !SystemUtils.nullEquals(oldValue, value)) {
				evt = ThingEvent.<IThing, IThingKey<V>, V> create(ThingEvent.EventType.PROPERTY_SET, this, key,
						oldValue, value);
				if (synchronizedUpdateEvents != null) {
					synchronizedUpdateEvents.add(evt);
					return oldValue;
				}
			}
		}
		// note: avoid deadlock by not being 'synchronized' when actually firing the event
		if (evt != null) {
			fireThingEvent(evt);
		}
		return oldValue;
	}

	@Override
	public boolean has(IThingKey<?> key) {
		synchronized (properties) {
			return properties.containsKey(key);
		}
	}

	@Override
	public <V> boolean has(IThing.IThingKey<V> key, V value) {
		synchronized (properties) {
			return SystemUtils.nullEquals(properties.get(key), value);
		}
	};

	@Override
	public <V> V remove(IThingKey<V> key) {
		V oldValue;
		ThingEvent<IThing, IThingKey<V>, V> evt = null;
		synchronized (properties) {
			modCount++;
			boolean containedValue = properties.containsKey(key);
			oldValue = properties.remove(key);
			if (containedValue && key.isFireEventOnChange()) {
				evt = ThingEvent.<IThing, IThingKey<V>, V> create(ThingEvent.EventType.PROPERTY_REMOVED, this, key,
						oldValue, null);
				if (synchronizedUpdateEvents != null) {
					synchronizedUpdateEvents.add(evt);
					return oldValue;
				}
			}
		}
		// note: avoid deadlock by not being 'synchronized' when actually firing the event
		if (evt != null) {
			fireThingEvent(evt);
		}
		return oldValue;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void synchronizedUpdate(Runnable r) {
		List<ThingEvent<?, ?, ?>> updateEvents = null;
		synchronized (properties) {
			try {
				if (synchronizedUpdateCount++ == 0) {
					updateEvents = Lists.newArrayList();
					synchronizedUpdateEvents = updateEvents;
				}
				r.run();
			}
			finally {
				if (--synchronizedUpdateCount != 0) {
					return;
				}
				synchronizedUpdateEvents = null;
			}
		}
		// note: avoid deadlock by not being 'synchronized' when actually firing the event
		for (ThingEvent<?, ?, ?> evt : updateEvents) {
			fireThingEvent((ThingEvent<IThing, IThingKey<Object>, Object>) evt);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<IThingKey<?>> keySet() {
		synchronized (properties) {
			return Sets.newHashSet((Set<IThingKey<?>>) properties.keySet());
		}
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(SystemUtils.simpleName(this.getClass())).append("[id=").append(id);
		for (Key<?> key : SystemUtils.sorted(properties.keySet())) {
			sb.append(",").append(key).append("=").append(properties.get(key));
		}
		sb.append("]");
		return sb.toString();
	}

	int modCount = 0;

	@Override
	public int getModCount() {
		return modCount;
	}
}