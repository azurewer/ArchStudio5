package org.archstudio.xarchadt.core;

import java.lang.reflect.*;
import java.util.*;
import java.util.concurrent.*;
import org.archstudio.myx.fw.*;
import org.archstudio.xarchadt.IXArchADTModelListener;
import org.archstudio.myx.fw.IMyxDynamicBrick;
import org.archstudio.xarchadt.IXArchADT;
import org.archstudio.myx.fw.IMyxLifecycleProcessor;
import org.archstudio.xarchadt.IXArchADTFileListener;
import org.archstudio.myx.fw.IMyxBrickItems;
import org.archstudio.myx.fw.IMyxProvidedServiceProvider;

/**
 * Abstract Myx brick: "XArch ADT Impl"
 *
 * @generated
 */
@SuppressWarnings("unused")
abstract class XArchADTMyxComponentStub extends org.archstudio.myx.fw.AbstractMyxSimpleBrick implements
		org.archstudio.myx.fw.IMyxDynamicBrick, org.archstudio.myx.fw.IMyxLifecycleProcessor,
		org.archstudio.myx.fw.IMyxProvidedServiceProvider {

	/**
	 * @generated
	 */
	protected final MyxRegistry myxRegistry = MyxRegistry.getSharedInstance();

	/**
	 * @generated
	 */
	public void begin() {
		super.begin();
		myxRegistry.register(this);
	}

	/**
	 * @generated
	 */
	public void end() {
		myxRegistry.unregister(this);
		super.end();
	}

	/**
	 * Myx interface fileEvents: <code>OUT_FILE_EVENTS</code>
	 *
	 * @generated
	 */
	public static final IMyxName OUT_FILE_EVENTS = MyxUtils.createName("fileEvents");
	/**
	 * Myx interface modelEvents: <code>OUT_MODEL_EVENTS</code>
	 *
	 * @generated
	 */
	public static final IMyxName OUT_MODEL_EVENTS = MyxUtils.createName("modelEvents");
	/**
	 * Myx interface xarch: <code>IN_XARCH</code>
	 *
	 * @generated
	 */
	public static final IMyxName IN_XARCH = MyxUtils.createName("xarch");

	/**
	 * Service object(s) for fileEvents
	 *
	 * @see #OUT_FILE_EVENTS
	 * @generated
	 */
	protected final Collection<org.archstudio.xarchadt.IXArchADTFileListener> fileEvents = new CopyOnWriteArrayList<org.archstudio.xarchadt.IXArchADTFileListener>();
	/**
	 * Proxy to service objects for fileEvents
	 *
	 * @see #OUT_FILE_EVENTS
	 * @generated
	 */
	protected final org.archstudio.xarchadt.IXArchADTFileListener fileEventsProxy = (org.archstudio.xarchadt.IXArchADTFileListener) Proxy
			.newProxyInstance(org.archstudio.xarchadt.IXArchADTFileListener.class.getClassLoader(),
					new Class[] { org.archstudio.xarchadt.IXArchADTFileListener.class }, new InvocationHandler() {
						@Override
						public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
							for (org.archstudio.xarchadt.IXArchADTFileListener o : fileEvents) {
								try {
									method.invoke(o, args);
								}
								catch (Exception e) {
									e.printStackTrace();
								}
							}
							return null;
						}
					});
	/**
	 * Service object(s) for modelEvents
	 *
	 * @see #OUT_MODEL_EVENTS
	 * @generated
	 */
	protected final Collection<org.archstudio.xarchadt.IXArchADTModelListener> modelEvents = new CopyOnWriteArrayList<org.archstudio.xarchadt.IXArchADTModelListener>();
	/**
	 * Proxy to service objects for modelEvents
	 *
	 * @see #OUT_MODEL_EVENTS
	 * @generated
	 */
	protected final org.archstudio.xarchadt.IXArchADTModelListener modelEventsProxy = (org.archstudio.xarchadt.IXArchADTModelListener) Proxy
			.newProxyInstance(org.archstudio.xarchadt.IXArchADTModelListener.class.getClassLoader(),
					new Class[] { org.archstudio.xarchadt.IXArchADTModelListener.class }, new InvocationHandler() {
						@Override
						public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
							for (org.archstudio.xarchadt.IXArchADTModelListener o : modelEvents) {
								try {
									method.invoke(o, args);
								}
								catch (Exception e) {
									e.printStackTrace();
								}
							}
							return null;
						}
					});
	/**
	 * Service object(s) for xarch
	 *
	 * @see #IN_XARCH
	 * @generated
	 */
	protected org.archstudio.xarchadt.IXArchADT xarch = null;

	/**
	 * Returns the service object(s) for <code>fileEvents</code>
	 *
	 * @see #OUT_FILE_EVENTS
	 * @generated
	 */
	public Collection<org.archstudio.xarchadt.IXArchADTFileListener> getFileEvents() {
		return fileEvents;
	}

	/**
	 * Returns the proxy service object for the <code>fileEvents</code> interface.
	 *
	 * @see #org.archstudio.xarchadt.IXArchADTFileListener fileEventsProxy
	 * @see #OUT_FILE_EVENTS
	 * @generated
	 */
	public org.archstudio.xarchadt.IXArchADTFileListener getFileEventsProxy() {
		return fileEventsProxy;
	}

	/**
	 * Returns the service object(s) for <code>modelEvents</code>
	 *
	 * @see #OUT_MODEL_EVENTS
	 * @generated
	 */
	public Collection<org.archstudio.xarchadt.IXArchADTModelListener> getModelEvents() {
		return modelEvents;
	}

	/**
	 * Returns the proxy service object for the <code>modelEvents</code> interface.
	 *
	 * @see #org.archstudio.xarchadt.IXArchADTModelListener modelEventsProxy
	 * @see #OUT_MODEL_EVENTS
	 * @generated
	 */
	public org.archstudio.xarchadt.IXArchADTModelListener getModelEventsProxy() {
		return modelEventsProxy;
	}

	/**
	 * Returns the service object(s) for <code>xarch</code>
	 *
	 * @see #IN_XARCH
	 * @generated
	 */
	public org.archstudio.xarchadt.IXArchADT getXarch() {
		return xarch;
	}

	/**
	 * @generated
	 */
	@Override
	public void interfaceConnected(IMyxName interfaceName, Object serviceObject) {
		if (serviceObject == null) {
			throw new NullPointerException(interfaceName.getName());
		}
		if (interfaceName.equals(OUT_FILE_EVENTS)) {
			fileEvents.add((org.archstudio.xarchadt.IXArchADTFileListener) serviceObject);
			return;
		}
		if (interfaceName.equals(OUT_MODEL_EVENTS)) {
			modelEvents.add((org.archstudio.xarchadt.IXArchADTModelListener) serviceObject);
			return;
		}
		throw new IllegalArgumentException("Unhandled interface connection: " + interfaceName);
	}

	/**
	 * @generated
	 */
	@Override
	public void interfaceDisconnecting(IMyxName interfaceName, Object serviceObject) {
		if (serviceObject == null) {
			throw new NullPointerException(interfaceName.getName());
		}
		if (interfaceName.equals(OUT_FILE_EVENTS)) {
			fileEvents.remove(serviceObject);
			return;
		}
		if (interfaceName.equals(OUT_MODEL_EVENTS)) {
			modelEvents.remove(serviceObject);
			return;
		}
		throw new IllegalArgumentException("Unhandled interface disconnection: " + interfaceName);
	}

	/**
	 * @generated
	 */
	@Override
	public void interfaceDisconnected(IMyxName interfaceName, Object serviceObject) {
	}

	/**
	 * @generated
	 */
	@Override
	public Object getServiceObject(IMyxName interfaceName) {
		if (interfaceName.equals(IN_XARCH)) {
			if (xarch == null) {
				throw new NullPointerException("xarch");
			}
			return xarch;
		}
		throw new IllegalArgumentException("Unhandled interface service object: " + interfaceName);
	}
}