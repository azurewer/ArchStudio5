package org.archstudio.myx.java.conn;

import org.archstudio.myx.fw.IMyxName;
import org.archstudio.myx.fw.MyxRegistry;
import org.archstudio.myx.fw.MyxUtils;

/*
 * DO NOT EDIT THIS CLASS, it is automatically generated.
 * ANY MODIFICATIONS WILL BE OVERWRITTEN.
 *
 * To modify, update the "Synchronous Multiway Proxy Impl" MyxGen 
 * extension in the org.archstudio.myx.java plugin.
 */

/**
 * Abstract Myx brick: Synchronous Multiway Proxy Impl
 * 
 * @generated
 */
public abstract class SynchronousMultiwayProxyConnectorStub extends org.archstudio.myx.fw.AbstractMyxSimpleBrick
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
	 * Myx name for the <code>results</code> interface.
	 * 
	 * @generated
	 */
	public static final IMyxName IN_RESULTS = MyxUtils.createName("results");

	/**
	 * Service object for the results interface.
	 * 
	 * @see #IN_RESULTS
	 * @generated
	 */
	protected org.archstudio.myx.java.conn.IMultiwayResults results = null;

	/**
	 * Returns the service object(s) for the results interface.
	 * 
	 * @see #IN_RESULTS
	 * @generated
	 */
	public org.archstudio.myx.java.conn.IMultiwayResults getResults() {
		if (results == null) {
			throw new NullPointerException("Uninitialized service object: results");
		}
		return results;
	}

	/**
	 * Myx name for the <code>out</code> interface.
	 * 
	 * @generated
	 */
	public static final IMyxName OUT_OUT = MyxUtils.createName("out");

	/**
	 * Service objects for the out interface.
	 * 
	 * @see #OUT_OUT
	 * @generated
	 */
	protected final java.util.Collection<java.lang.Object> out = new java.util.concurrent.CopyOnWriteArrayList<java.lang.Object>();

	/**
	 * Returns the service object(s) for the out interface.
	 * 
	 * @see #OUT_OUT
	 * @generated
	 */
	public java.util.Collection<java.lang.Object> getOut() {
		return out;
	}

	/**
	 * Myx name for the <code>in</code> interface.
	 * 
	 * @generated
	 */
	public static final IMyxName IN_IN = MyxUtils.createName("in");

	/**
	 * Service object for the in interface.
	 * 
	 * @see #IN_IN
	 * @generated
	 */
	protected java.lang.Object in = null;

	/**
	 * Returns the service object(s) for the in interface.
	 * 
	 * @see #IN_IN
	 * @generated
	 */
	public java.lang.Object getIn() {
		if (in == null) {
			throw new NullPointerException("Uninitialized service object: in");
		}
		return in;
	}

	/**
	 * Myx name for the <code>progress</code> interface.
	 * 
	 * @generated
	 */
	public static final IMyxName OUT_PROGRESS = MyxUtils.createName("progress");

	/**
	 * Service objects for the progress interface.
	 * 
	 * @see #OUT_PROGRESS
	 * @generated
	 */
	protected final java.util.Collection<org.archstudio.myx.java.conn.IMultiwayProgressListener> progress = new java.util.concurrent.CopyOnWriteArrayList<org.archstudio.myx.java.conn.IMultiwayProgressListener>();

	/**
	 * Returns the service object(s) for the progress interface.
	 * 
	 * @see #OUT_PROGRESS
	 * @generated
	 */
	public java.util.Collection<org.archstudio.myx.java.conn.IMultiwayProgressListener> getProgress() {
		return progress;
	}

	/**
	 * Returns service object(s) for IN interfaces.
	 * 
	 * @see #IN_RESULTS
	 * @see #IN_IN
	 * @generated
	 */
	@Override
	public Object getServiceObject(IMyxName interfaceName) {
		if (interfaceName.equals(IN_RESULTS)) {
			if (results == null) {
				throw new NullPointerException("Uninitialized service object: results");
			}
			return results;
		}
		if (interfaceName.equals(IN_IN)) {
			if (in == null) {
				throw new NullPointerException("Uninitialized service object: in");
			}
			return in;
		}
		throw new IllegalArgumentException("Unhandled interface: " + interfaceName.getName());
	}

	/**
	 * Update service objects based on connected OUT interfaces.
	 * 
	 * @see #OUT_OUT
	 * @see #OUT_PROGRESS
	 * @generated
	 */
	@Override
	public void interfaceConnected(IMyxName interfaceName, Object serviceObject) {
		if (serviceObject == null)
			throw new NullPointerException(interfaceName.getName());

		if (interfaceName.equals(OUT_OUT)) {
			out.add((java.lang.Object) serviceObject);
			return;
		}

		if (interfaceName.equals(OUT_PROGRESS)) {
			progress.add((org.archstudio.myx.java.conn.IMultiwayProgressListener) serviceObject);
			return;
		}

		throw new IllegalArgumentException("Unhandled interface: " + interfaceName.getName());
	}

	/**
	 * Update service objects based on disconnecting OUT interfaces.
	 * 
	 * @see #OUT_OUT
	 * @see #OUT_PROGRESS
	 * @generated
	 */
	@Override
	public void interfaceDisconnecting(IMyxName interfaceName, Object serviceObject) {
		if (serviceObject == null)
			throw new NullPointerException(interfaceName.getName());
		if (interfaceName.equals(OUT_OUT)) {
			out.remove(serviceObject);
			return;
		}
		if (interfaceName.equals(OUT_PROGRESS)) {
			progress.remove(serviceObject);
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