package org.archstudio.xarchadt.variability.ui;

import org.archstudio.myx.fw.IMyxName;
import org.archstudio.myx.fw.MyxRegistry;
import org.archstudio.myx.fw.MyxUtils;

/*
 * DO NOT EDIT THIS CLASS, it is automatically generated.
 * ANY MODIFICATIONS WILL BE OVERWRITTEN.
 *
 * To modify, update the "Variability Myx Component" MyxGen 
 * extension in the org.archstudio.xarchadt.variability.ui plugin.
 */

/**
 * Abstract Myx brick: Variability Myx Component
 * 
 * @generated
 */
public abstract class VariabilityMyxComponentStub extends org.archstudio.myx.fw.AbstractMyxSimpleBrick
		implements org.archstudio.myx.fw.IMyxDynamicBrick {
	/**
	 * The registry of objects for this brick.
	 * 
	 * @generated
	 */
	protected final MyxRegistry myxRegistry = MyxRegistry.getSharedInstance();

	/**
	 * Register this brick instance with the registry.
	 * 
	 * @generated
	 */
	@Override
	public void begin() {
		super.begin();
		myxRegistry.registerBrick(this);
	}

	/**
	 * Unregister this brick instance with the registry.
	 * 
	 * @generated
	 */
	@Override
	public void end() {
		myxRegistry.unregisterBrick(this);
		super.end();
	}

	/**
	 * Myx name for the <code>xarch</code> interface.
	 * 
	 * @generated
	 */
	public static final IMyxName OUT_XARCH = MyxUtils.createName("xarch");

	/**
	 * Service object for the xarch interface.
	 * 
	 * @see #OUT_XARCH
	 * @generated
	 */
	protected org.archstudio.xarchadt.variability.IXArchADTVariability xarch = null;

	/**
	 * Returns the service object(s) for the xarch interface.
	 * 
	 * @see #OUT_XARCH
	 * @generated
	 */
	public org.archstudio.xarchadt.variability.IXArchADTVariability getXarch() {
		if (xarch == null) {
			throw new NullPointerException("Uninitialized service object: xarch");
		}
		return xarch;
	}

	/**
	 * Myx name for the <code>modelEvents</code> interface.
	 * 
	 * @generated
	 */
	public static final IMyxName IN_MODEL_EVENTS = MyxUtils.createName("modelEvents");

	/**
	 * Service object proxy for the modelEvents interface. Calls to this proxy
	 * object are automatically delegated to all service objects in the
	 * MyxRegistry of type org.archstudio.xarchadt.IXArchADTModelListener.
	 * 
	 * @see #IN_MODEL_EVENTS
	 * @generated
	 */
	protected final org.archstudio.xarchadt.IXArchADTModelListener modelEventsProxy = (org.archstudio.xarchadt.IXArchADTModelListener) java.lang.reflect.Proxy
			.newProxyInstance(org.archstudio.xarchadt.IXArchADTModelListener.class.getClassLoader(),
					new Class[] { org.archstudio.xarchadt.IXArchADTModelListener.class },
					new java.lang.reflect.InvocationHandler() {
						@Override
						public Object invoke(Object proxy, java.lang.reflect.Method method, Object[] args)
								throws Throwable {
							for (org.archstudio.xarchadt.IXArchADTModelListener o : myxRegistry.getObjects(
									VariabilityMyxComponentStub.this,
									org.archstudio.xarchadt.IXArchADTModelListener.class)) {
								try {
									method.invoke(o, args);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							return null;
						}
					});

	/**
	 * Returns the service object(s) for the modelEvents interface.
	 * 
	 * @see #IN_MODEL_EVENTS
	 * @generated
	 */
	public org.archstudio.xarchadt.IXArchADTModelListener getModelEvents() {
		return modelEventsProxy;
	}

	/**
	 * Myx name for the <code>fileEvents</code> interface.
	 * 
	 * @generated
	 */
	public static final IMyxName IN_FILE_EVENTS = MyxUtils.createName("fileEvents");

	/**
	 * Service object proxy for the fileEvents interface. Calls to this proxy
	 * object are automatically delegated to all service objects in the
	 * MyxRegistry of type org.archstudio.xarchadt.IXArchADTFileListener.
	 * 
	 * @see #IN_FILE_EVENTS
	 * @generated
	 */
	protected final org.archstudio.xarchadt.IXArchADTFileListener fileEventsProxy = (org.archstudio.xarchadt.IXArchADTFileListener) java.lang.reflect.Proxy
			.newProxyInstance(org.archstudio.xarchadt.IXArchADTFileListener.class.getClassLoader(),
					new Class[] { org.archstudio.xarchadt.IXArchADTFileListener.class },
					new java.lang.reflect.InvocationHandler() {
						@Override
						public Object invoke(Object proxy, java.lang.reflect.Method method, Object[] args)
								throws Throwable {
							for (org.archstudio.xarchadt.IXArchADTFileListener o : myxRegistry.getObjects(
									VariabilityMyxComponentStub.this,
									org.archstudio.xarchadt.IXArchADTFileListener.class)) {
								try {
									method.invoke(o, args);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							return null;
						}
					});

	/**
	 * Returns the service object(s) for the fileEvents interface.
	 * 
	 * @see #IN_FILE_EVENTS
	 * @generated
	 */
	public org.archstudio.xarchadt.IXArchADTFileListener getFileEvents() {
		return fileEventsProxy;
	}

	/**
	 * Myx name for the <code>variabilityEvents</code> interface.
	 * 
	 * @generated
	 */
	public static final IMyxName IN_VARIABILITY_EVENTS = MyxUtils.createName("variabilityEvents");

	/**
	 * Service object proxy for the variabilityEvents interface. Calls to this
	 * proxy object are automatically delegated to all service objects in the
	 * MyxRegistry of type
	 * org.archstudio.xarchadt.variability.IXArchADTVariabilityListener.
	 * 
	 * @see #IN_VARIABILITY_EVENTS
	 * @generated
	 */
	protected final org.archstudio.xarchadt.variability.IXArchADTVariabilityListener variabilityEventsProxy = (org.archstudio.xarchadt.variability.IXArchADTVariabilityListener) java.lang.reflect.Proxy
			.newProxyInstance(org.archstudio.xarchadt.variability.IXArchADTVariabilityListener.class.getClassLoader(),
					new Class[] { org.archstudio.xarchadt.variability.IXArchADTVariabilityListener.class },
					new java.lang.reflect.InvocationHandler() {
						@Override
						public Object invoke(Object proxy, java.lang.reflect.Method method, Object[] args)
								throws Throwable {
							for (org.archstudio.xarchadt.variability.IXArchADTVariabilityListener o : myxRegistry
									.getObjects(VariabilityMyxComponentStub.this,
											org.archstudio.xarchadt.variability.IXArchADTVariabilityListener.class)) {
								try {
									method.invoke(o, args);
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
							return null;
						}
					});

	/**
	 * Returns the service object(s) for the variabilityEvents interface.
	 * 
	 * @see #IN_VARIABILITY_EVENTS
	 * @generated
	 */
	public org.archstudio.xarchadt.variability.IXArchADTVariabilityListener getVariabilityEvents() {
		return variabilityEventsProxy;
	}

	/**
	 * Returns service object(s) for IN interfaces.
	 * 
	 * @see #IN_MODEL_EVENTS
	 * @see #IN_FILE_EVENTS
	 * @see #IN_VARIABILITY_EVENTS
	 * @generated
	 */
	@Override
	public Object getServiceObject(IMyxName interfaceName) {
		if (interfaceName.equals(IN_MODEL_EVENTS)) {
			return modelEventsProxy;
		}
		if (interfaceName.equals(IN_FILE_EVENTS)) {
			return fileEventsProxy;
		}
		if (interfaceName.equals(IN_VARIABILITY_EVENTS)) {
			return variabilityEventsProxy;
		}
		throw new IllegalArgumentException("Unhandled interface: " + interfaceName.getName());
	}

	/**
	 * Update service objects based on connected OUT interfaces.
	 * 
	 * @see #OUT_XARCH
	 * @generated
	 */
	@Override
	public void interfaceConnected(IMyxName interfaceName, Object serviceObject) {
		if (serviceObject == null)
			throw new NullPointerException(interfaceName.getName());

		if (interfaceName.equals(OUT_XARCH)) {
			if (xarch != null)
				throw new IllegalStateException("Only a single connection is supported on interface: xarch");
			xarch = (org.archstudio.xarchadt.variability.IXArchADTVariability) serviceObject;
			return;
		}

		throw new IllegalArgumentException("Unhandled interface: " + interfaceName.getName());
	}

	/**
	 * Update service objects based on disconnecting OUT interfaces.
	 * 
	 * @see #OUT_XARCH
	 * @generated
	 */
	@Override
	public void interfaceDisconnecting(IMyxName interfaceName, Object serviceObject) {
		if (serviceObject == null)
			throw new NullPointerException(interfaceName.getName());
		if (interfaceName.equals(OUT_XARCH)) {
			if (xarch == null)
				throw new IllegalStateException("A connection was never made on interface: xarch");
			xarch = null;
			return;
		}
		throw new IllegalArgumentException("Unhandled interface: " + interfaceName.getName());
	}

	/**
	 * Performs no operation upon the completion of an interface disconnecting.
	 * 
	 * @generated
	 */
	@Override
	public void interfaceDisconnected(IMyxName interfaceName, Object serviceObject) {
	}
}