package org.archstudio.bna.utils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.archstudio.bna.BNAModelEvent;
import org.archstudio.bna.IBNAModelListener;
import org.archstudio.bna.IThing;
import org.archstudio.bna.IThing.IThingKey;

class ThreadModelEventProcessor {
	protected ExecutorService asyncExecutor = null;

	private final List<IBNAModelListener> modelListeners = new CopyOnWriteArrayList<IBNAModelListener>();

	public ThreadModelEventProcessor() {
		asyncExecutor = Executors.newSingleThreadExecutor();
	}

	public void addModelListener(IBNAModelListener ml) {
		modelListeners.add(ml);
	}

	public void removeModelListener(IBNAModelListener ml) {
		modelListeners.remove(ml);
	}

	public void waitForProcessing() {
		// Submit a fake task and wait for it to be processed. When
		// it's done, we're caught up in the processing to the point in the queue
		// when this was called.
		final Runnable r = new Runnable() {
			boolean hasRun = false;

			@Override
			public void run() {
				hasRun = !hasRun;
			}
		};

		Future<?> f = asyncExecutor.submit(r);
		try {
			//This call will block until the task runs
			f.get();
		}
		catch (Exception e) {
		}
	}

	public <ET extends IThing, EK extends IThingKey<EV>, EV> void fireBNAModelEvent(BNAModelEvent<ET, EK, EV> evt) {
		final BNAModelEvent<ET, EK, EV> fevt = evt;
		final List<IBNAModelListener> fmodelListeners = modelListeners;
		Runnable r = new Runnable() {
			@Override
			public void run() {
				for (IBNAModelListener ml : fmodelListeners) {
					ml.bnaModelChanged(fevt);
				}
			}
		};
		asyncExecutor.submit(r);
	}

	public void terminate(boolean t) {
		asyncExecutor.shutdown();
		try {
			asyncExecutor.awaitTermination(5, TimeUnit.SECONDS);
		}
		catch (InterruptedException ie) {
		}
	}

}
