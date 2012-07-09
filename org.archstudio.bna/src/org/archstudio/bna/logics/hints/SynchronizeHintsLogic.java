package org.archstudio.bna.logics.hints;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

import java.util.concurrent.CopyOnWriteArrayList;

import org.archstudio.bna.BNAModelEvent;
import org.archstudio.bna.IBNAModelListener;
import org.archstudio.bna.IThing;
import org.archstudio.bna.IThing.IThingKey;
import org.archstudio.bna.ThingEvent;
import org.archstudio.bna.facets.IHasAnchorPoint;
import org.archstudio.bna.facets.IHasAngle;
import org.archstudio.bna.facets.IHasBoundingBox;
import org.archstudio.bna.facets.IHasColor;
import org.archstudio.bna.facets.IHasEndpoints;
import org.archstudio.bna.facets.IHasMidpoints;
import org.archstudio.bna.facets.IHasMutableAngle;
import org.archstudio.bna.facets.IHasMutableColor;
import org.archstudio.bna.facets.IHasMutableEndpoints;
import org.archstudio.bna.facets.IHasMutableMidpoints;
import org.archstudio.bna.facets.IHasMutableSize;
import org.archstudio.bna.facets.IRelativeMovable;
import org.archstudio.bna.keys.ThingKey;
import org.archstudio.bna.logics.AbstractThingLogic;
import org.archstudio.bna.logics.editing.ShowHideTagsLogic;
import org.archstudio.bna.logics.hints.synchronizers.PropertyHintSynchronizer;
import org.archstudio.bna.utils.Assemblies;

public class SynchronizeHintsLogic extends AbstractThingLogic implements IBNAModelListener,
		IHintRepositoryChangeListener {

	private static class HintInformation {
		public final Object context;

		public HintInformation(Object context) {
			this.context = context;
		}

		@Override
		public String toString() {
			return "HintInformation [context=" + context + "]";
		}
	}

	private static final boolean DEBUG = false;

	private static final IThingKey<HintInformation> HINT_INFORMATION_KEY = ThingKey.create(SynchronizeHintsLogic.class);

	final protected IHintRepository hintRepository;

	public SynchronizeHintsLogic(IHintRepository hintRepository) {
		this.hintRepository = hintRepository;
		addHintSynchronizer(new PropertyHintSynchronizer("bounds", IHasBoundingBox.BOUNDING_BOX_KEY,
				IRelativeMovable.USER_MAY_MOVE, IHasMutableSize.USER_MAY_RESIZE));
		addHintSynchronizer(new PropertyHintSynchronizer("location", IHasAnchorPoint.ANCHOR_POINT_KEY,
				IRelativeMovable.USER_MAY_MOVE));
		addHintSynchronizer(new PropertyHintSynchronizer("tagged", ShowHideTagsLogic.SHOW_TAG_KEY,
				ShowHideTagsLogic.USER_MAY_SHOW_HIDE_TAG));
		addHintSynchronizer(new PropertyHintSynchronizer("angle", IHasAngle.ANGLE_KEY,
				IHasMutableAngle.USER_MAY_CHANGE_ANGLE));
		addHintSynchronizer(new PropertyHintSynchronizer("endpoint1", IHasEndpoints.ENDPOINT_1_KEY,
				IHasMutableEndpoints.USER_MAY_MOVE_ENDPOINT1));
		addHintSynchronizer(new PropertyHintSynchronizer("endpoint2", IHasEndpoints.ENDPOINT_2_KEY,
				IHasMutableEndpoints.USER_MAY_MOVE_ENDPOINT2));
		addHintSynchronizer(new PropertyHintSynchronizer("midpoints", IHasMidpoints.MIDPOINTS_KEY,
				IHasMutableMidpoints.USER_MAY_MOVE_MIDPOINTS));
		addHintSynchronizer(new PropertyHintSynchronizer("color", IHasColor.COLOR_KEY,
				IHasMutableColor.USER_MAY_EDIT_COLOR));
	}

	final protected CopyOnWriteArrayList<IHintSynchronizer> hintSynchronizers = new CopyOnWriteArrayList<IHintSynchronizer>();

	public void addHintSynchronizer(IHintSynchronizer hintSynchronizer) {
		checkState(getBNAWorld() == null, "Hint synchronizer updates must occur before logic initialization");

		hintSynchronizers.add(hintSynchronizer);
	}

	public void removeHintSynchronizer(IHintSynchronizer hintSynchronizer) {
		checkState(getBNAWorld() == null, "Hint synchronizer updates must occur before logic initialization");

		hintSynchronizers.remove(hintSynchronizer);
	}

	@Override
	public void init() {
		checkState(getBNAModel().getAllThings().isEmpty(), "SynchronizeHintsLogic must be added before things.");

		super.init();
		for (IHintSynchronizer hintSynchronizer : hintSynchronizers) {
			hintSynchronizer.setBNAWorld(getBNAWorld());
		}
		hintRepository.addHintRepositoryChangeListener(this);
	}

	@Override
	public void destroy() {
		hintRepository.removeHintRepositoryChangeListener(this);
		for (IHintSynchronizer hintSynchronizer : hintSynchronizers) {
			hintSynchronizer.setBNAWorld(null);
		}
		super.destroy();
	}

	@Override
	public <ET extends IThing, EK extends IThingKey<EV>, EV> void bnaModelChanged(BNAModelEvent<ET, EK, EV> evt) {
		switch (evt.getEventType()) {
		case THING_ADDED:
		case THING_CHANGED:
			ET thing = evt.getTargetThing();
			ThingEvent<ET, EK, EV> te = evt.getThingEvent();
			if (te == null || !HINT_INFORMATION_KEY.equals(te.getPropertyName())) {
				HintInformation hintInformation = createHintInformation(thing);
				if (hintInformation != null) {
					storeHints(thing, hintInformation, evt);
				}
			}
		default:
			// do nothing
		}
	}

	protected HintInformation createHintInformation(IThing thing) {
		checkNotNull(thing);

		HintInformation hintInformation = thing.get(HINT_INFORMATION_KEY);
		if (hintInformation == null) {
			Object context = hintRepository.getContextForThing(getBNAWorld(), thing);
			if (context != null) {
				hintInformation = new HintInformation(context);
				restoreHints(thing, hintInformation);
				storeHints(thing, hintInformation, null);
				thing.set(HINT_INFORMATION_KEY, hintInformation);
				for (IThing t : Assemblies.getParts(getBNAModel(), thing)) {
					createHintInformation(t);
				}
			}
		}

		return hintInformation;
	}

	protected void restoreHints(IThing thing, HintInformation hintInformation) {
		if (DEBUG) {
			System.out.println("Restore hints: " + hintInformation + " " + thing.getID());
		}
		for (IHintSynchronizer hintSynchronizer : hintSynchronizers) {
			hintSynchronizer.restoreHints(hintRepository, hintInformation.context, thing);
		}
	}

	protected <ET extends IThing, EK extends IThingKey<EV>, EV> void storeHints(ET thing,
			HintInformation hintInformation, BNAModelEvent<ET, EK, EV> evt) {
		if (DEBUG) {
			System.out.println("Store hints  : " + hintInformation + " " + thing.getID() + " " + evt);
		}
		for (IHintSynchronizer hintSynchronizer : hintSynchronizers) {
			hintSynchronizer.storeHints(hintRepository, hintInformation.context, thing, evt);
		}
	}

	//	protected void fireThingChanged(IHintRepository repository, Object context, String partPath, String[] parts,
	//			IThing thing, Object propertyName, Object oldValue, Object newValue) {
	//		if (DEBUG) {
	//			System.out.println("Thing changed: " + context + " " + thing.getID() + " " + propertyName + " from "
	//					+ oldValue + " to " + newValue);
	//		}
	//		for (IHintSynchronizer hintSynchronizer : hintSynchronizers) {
	//			hintSynchronizer
	//					.thingChanged(repository, context, partPath, parts, thing, propertyName, oldValue, newValue);
	//		}
	//	}
	//

	//	private void initializeHints(IThing thing) {
	//		if (!hintInfoMap.containsKey(thing.getID())) {
	//			IAssembly partAssembly = AssemblyUtils.getAssemblyWithPart(thing);
	//			IAssembly rootAssembly = AssemblyUtils.getAssemblyWithRoot(thing);
	//
	//			Object hintContext = null;
	//			String hintPartPath = "";
	//
	//			if (rootAssembly != null) {
	//				hintContext = hintRepository.getContextForAssembly(bnaWorld, rootAssembly);
	//			}
	//			else if (partAssembly != null) {
	//				HintInformation parentHintInformation = hintInfoMap.get(partAssembly.getRootThing().getID());
	//				if (parentHintInformation != null) {
	//					hintContext = parentHintInformation.hintContext;
	//					if (parentHintInformation.hintPartPath.length() > 0) {
	//						hintPartPath = parentHintInformation.hintPartPath + "/" + AssemblyUtils.getPartName(thing);
	//					}
	//					else {
	//						hintPartPath = AssemblyUtils.getPartName(thing);
	//					}
	//				}
	//			}
	//
	//			if (hintContext != null) {
	//				if (DEBUG) {
	//					System.out.println("Initializing hints: " + thing);
	//				}
	//
	//				HintInformation hintInformation = new HintInformation(hintContext, hintPartPath);
	//				thing.setProperty(HINT_INFORMATION_KEY, null); // now it has the property, so don't call initialize again
	//				restoreHints(thing, hintInformation);
	//				thing.setProperty(HINT_INFORMATION_KEY, hintInformation);
	//
	//				if (rootAssembly != null) {
	//					for (IThing partThing : rootAssembly.getParts()) {
	//						initializeHints(partThing);
	//					}
	//				}
	//			}
	//		}
	//	}

	//	private static final boolean DEBUG = false;
	//
	//	private static final Pattern PATH_SPLIT_PATTERN = Pattern.compile("\\/");
	//
	//	private static final String HINT_INFORMATION_KEY = SynchronizeHintsLogic.class.getName() + ":hintInformation";
	//	private static final String BEGIN_IGNORING_CHANGES = SynchronizeHintsLogic.class.getName()
	//			+ ":beginIgnoringChanges";
	//	private static final String END_IGNORING_CHANGES = SynchronizeHintsLogic.class.getName() + ":endIgnoringChanges";
	//
	//	private static class HintInformation {
	//
	//		final Object hintContext;
	//		final String hintPartPath;
	//		final String[] hintParts;
	//
	//		public HintInformation(Object hintContext, String hintPartPath) {
	//			assert hintContext != null;
	//
	//			this.hintContext = hintContext;
	//			this.hintPartPath = hintPartPath;
	//			this.hintParts = PATH_SPLIT_PATTERN.split(hintPartPath);
	//		}
	//	}
	//
	//	private final IHintRepository hintRepository;
	//	private final Map<String, HintInformation> hintInfoMap = new HashMap<String, HintInformation>();
	//
	//	volatile int holdingChanges = 0;
	//	boolean holdingMouseDown = false;
	//	final Map<Tuple, Object[]> heldChanges = new HashMap<Tuple, Object[]>();
	//
	//	private void bumpHoldingEvents(int i) {
	//		Set<Map.Entry<Tuple, Object[]>> entries = Collections.emptySet();
	//		synchronized (heldChanges) {
	//			holdingChanges += i;
	//			if (holdingChanges <= 0) {
	//				holdingChanges = 0;
	//				entries = cloneclear(heldChanges).entrySet();
	//			}
	//		}
	//		for (Map.Entry<Tuple, Object[]> entry : entries) {
	//			handleThingChanged((IThing) entry.getKey().getElement(0), entry.getKey().getElement(1),
	//					entry.getValue()[0], entry.getValue()[1]);
	//		}
	//	}
	//
	//	private <K, V> Map<K, V> cloneclear(Map<K, V> map) {
	//		Map<K, V> c = new HashMap<K, V>(map);
	//		map.clear();
	//		return c;
	//	}
	//
	//	volatile int ignoringChanges = 0;
	//	// TODO: Replace this with the ability to mark the event in some way?
	//	final Collection<Tuple> ignoringThingProperties = new HashBag<Tuple>();
	//
	//	private void bumpIgnoreChanges(int i) {
	//		synchronized (ignoringThingProperties) {
	//			ignoringChanges += i;
	//			if (ignoringChanges <= 0) {
	//				ignoringChanges = 0;
	//			}
	//		}
	//	}
	//
	//	public void ignoreBNAChanges(final Runnable r) {
	//		final IBNAModel model = getBNAModel();
	//		Lock lock = model.getLock();
	//		lock.lock();
	//		try {
	//			model.fireStreamNotificationEvent(BEGIN_IGNORING_CHANGES);
	//			try {
	//				r.run();
	//			}
	//			finally {
	//				model.fireStreamNotificationEvent(END_IGNORING_CHANGES);
	//			}
	//		}
	//		finally {
	//			lock.unlock();
	//		}
	//	}
	//
	//	public SynchronizeHintsLogic(final IHintRepository hintRepository) {
	//	}
	//
	//	protected void fireRepositoryChanged(IHintRepository repository, Object context, IAssembly[] assemblies,
	//			String hintName) {
	//		if (DEBUG) {
	//			System.out.println("Repository changed: " + context + " " + hintName + " " + assemblies.length);
	//		}
	//		for (IHintSynchronizer hintSynchronizer : hintSynchronizers) {
	//			hintSynchronizer.repositoryChanged(repository, context, assemblies, hintName);
	//		}
	//	}
	//
	//	private void restoreHints(IThing thing, HintInformation hintInformation) {
	//		if (hintInformation != null) {
	//			fireRestoreHints(hintRepository, hintInformation.hintContext, hintInformation.hintPartPath,
	//					hintInformation.hintParts, thing);
	//		}
	//	}
	//
	//	private void restoreHints(IAssembly assembly, Object context, HintInformation hintInformation) {
	//		if (hintInformation != null && context.equals(hintInformation.hintContext)) {
	//			restoreHints(assembly.getRootThing(), hintInformation);
	//			for (IThing thing : assembly.getParts()) {
	//				if (thing != null) {
	//					IAssembly partRootAssembly = AssemblyUtils.getAssemblyWithRoot(thing);
	//					HintInformation partHintInformation = hintInfoMap.get(thing.getID());
	//					if (partRootAssembly == null) {
	//						restoreHints(thing, partHintInformation);
	//					}
	//					else {
	//						restoreHints(partRootAssembly, context, partHintInformation);
	//					}
	//				}
	//			}
	//		}
	//	}
	//
	//	public void mouseDown(IBNAView view, MouseEvent evt, IThing t, int worldX, int worldY) {
	//		if (!holdingMouseDown) {
	//			holdingMouseDown = true;
	//			bumpHoldingEvents(1);
	//		}
	//	}
	//
	//	public void mouseUp(IBNAView view, MouseEvent evt, IThing t, int worldX, int worldY) {
	//		if (holdingMouseDown) {
	//			holdingMouseDown = false;
	//			bumpHoldingEvents(-1);
	//		}
	//	}
	//
	//	public void bnaModelChangedSync(final BNAModelEvent evt) {
	//		switch (evt.getEventType()) {
	//		case STREAM_NOTIFICATION_EVENT:
	//			if (BEGIN_IGNORING_CHANGES.equals(evt.getStreamNotification())) {
	//				bumpIgnoreChanges(1);
	//			}
	//			else if (END_IGNORING_CHANGES.equals(evt.getStreamNotification())) {
	//				bumpIgnoreChanges(-1);
	//			}
	//			break;
	//
	//		case BULK_CHANGE_BEGIN:
	//			bumpHoldingEvents(1);
	//			break;
	//
	//		case BULK_CHANGE_END:
	//			bumpHoldingEvents(-1);
	//			break;
	//
	//		case THING_ADDED: {
	//			IThing thing = evt.getTargetThing();
	//			hintInfoMap.remove(thing.getID());
	//			initializeHints(thing);
	//		}
	//			break;
	//
	//		case THING_REMOVED: {
	//			IThing thing = evt.getTargetThing();
	//			hintInfoMap.remove(thing.getID());
	//		}
	//			break;
	//
	//		case THING_CHANGED:
	//			if (DEBUG) {
	//				System.out.println("BNA Event: " + evt.getThingEvent());
	//			}
	//
	//			IThing thing = evt.getTargetThing();
	//			ThingEvent thingEvent = evt.getThingEvent();
	//			Object propertyName = thingEvent.getPropertyName();
	//
	//			if (!thing.hasProperty(HINT_INFORMATION_KEY)) {
	//				initializeHints(thing);
	//			}
	//			if (!HINT_INFORMATION_KEY.equals(propertyName)) {
	//				boolean ignore = ignoringChanges > 0 || hintInfoMap.get(thing.getID()) == null;
	//				if (ignore || holdingChanges > 0) {
	//					ignoringThingProperties.add(new Tuple(thing.getID(), propertyName));
	//				}
	//				if (!ignore && holdingChanges > 0) {
	//					synchronized (heldChanges) {
	//						Tuple key = new Tuple(thing, propertyName);
	//						Object[] oldNewValue = heldChanges.get(key);
	//						if (oldNewValue == null) {
	//							heldChanges.set(key, oldNewValue = new Object[2]);
	//							oldNewValue[0] = thingEvent.getOldPropertyValue();
	//						}
	//						oldNewValue[1] = thingEvent.getNewPropertyValue();
	//						break;
	//					}
	//				}
	//			}
	//			else {
	//				hintInfoMap.set(thing.getID(), (HintInformation) thingEvent.getNewPropertyValue());
	//			}
	//		}
	//	}
	//
	//	public void bnaModelChanged(BNAModelEvent evt) {
	//		switch (evt.getEventType()) {
	//
	//		case THING_CHANGED:
	//			IThing thing = evt.getTargetThing();
	//			ThingEvent thingEvent = evt.getThingEvent();
	//			Object propertyName = thingEvent.getPropertyName();
	//
	//			if (!ignoringThingProperties.isEmpty()
	//					&& ignoringThingProperties.remove(new Tuple(thing.getID(), propertyName))) {
	//				if (DEBUG) {
	//					System.out.println("Ignored: " + thing.getID() + " " + propertyName + " ("
	//							+ ignoringThingProperties.size() + ") " + thingEvent);
	//				}
	//				// do nothing
	//				break;
	//			}
	//			else {
	//				handleThingChanged(thing, propertyName, thingEvent.getOldPropertyValue(),
	//						thingEvent.getNewPropertyValue());
	//			}
	//		}
	//	}
	//
	//	private void handleThingChanged(final IThing thing, final Object propertyName, final Object oldPropertyValue,
	//			final Object newPropertyValue) {
	//		HintInformation hintInformation = hintInfoMap.get(thing.getID());
	//		if (hintInformation != null) {
	//			fireThingChanged(hintRepository, hintInformation.hintContext, hintInformation.hintPartPath,
	//					hintInformation.hintParts, thing, propertyName, oldPropertyValue, newPropertyValue);
	//		}
	//	}
	//

	@Override
	public void hintRepositoryChanged(IHintRepository repository, Object context, String hintName) {
	}

	//	public void hintRepositoryChanged(IHintRepository repository, final Object context, final String hintName) {
	//		ignoreBNAChanges(new Runnable() {
	//
	//			public void run() {
	//				IBNAWorld world = getBNAWorld();
	//				if (world != null) {
	//					IAssembly[] assemblies = hintRepository.getAssembliesForContext(world, context);
	//					if (hintName != null) {
	//						fireRepositoryChanged(hintRepository, context, assemblies, hintName);
	//					}
	//					else {
	//						for (IAssembly assembly : assemblies) {
	//							restoreHints(assembly, context, hintInfoMap.get(assembly.getRootThing().getID()));
	//						}
	//					}
	//				}
	//			}
	//		});
	//	}

}