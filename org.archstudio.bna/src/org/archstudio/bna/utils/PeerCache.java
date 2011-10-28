package org.archstudio.bna.utils;

import java.lang.reflect.Constructor;

import org.archstudio.bna.IThing;
import org.archstudio.bna.IThingPeer;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

public class PeerCache {

	protected static Cache<Class<? extends IThingPeer<?>>, Constructor<?>> constructorsCache = CacheBuilder
			.newBuilder().build(new CacheLoader<Class<? extends IThingPeer<?>>, Constructor<?>>() {

				@Override
				public Constructor<?> load(Class<? extends IThingPeer<?>> key) throws Exception {
					for (Constructor<?> c : key.getConstructors()) {
						if (c.getParameterTypes().length == 1) {
							if (IThing.class.isAssignableFrom(c.getParameterTypes()[0])) {
								return c;
							}
						}
					}
					throw new IllegalArgumentException("Cannot find appropriate constructor: " + key);
				}
			});

	protected static Cache<IThing, IThingPeer<?>> peersCache = CacheBuilder.newBuilder().weakKeys()
			.build(new CacheLoader<IThing, IThingPeer<?>>() {

				@Override
				public IThingPeer<?> load(IThing key) throws Exception {
					return (IThingPeer<?>) constructorsCache.get(key.getPeerClass()).newInstance(key);
				}
			});

	@SuppressWarnings("unchecked")
	public <T extends IThing> IThingPeer<T> getPeer(T thing) {
		return (IThingPeer<T>) peersCache.getUnchecked(thing);
	}

	public void disposePeer(IThing thing) {
		getPeer(thing).dispose();
	}
}
