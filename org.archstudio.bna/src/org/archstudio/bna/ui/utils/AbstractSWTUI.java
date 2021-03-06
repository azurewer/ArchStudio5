package org.archstudio.bna.ui.utils;

import java.util.List;
import java.util.Map;

import org.archstudio.bna.IBNAView;
import org.archstudio.bna.ICoordinate;
import org.archstudio.bna.IThing;
import org.archstudio.bna.constants.DNDData;
import org.archstudio.bna.constants.DNDType;
import org.archstudio.bna.constants.FocusType;
import org.archstudio.bna.constants.GestureType;
import org.archstudio.bna.constants.KeyType;
import org.archstudio.bna.constants.MouseType;
import org.archstudio.bna.ui.IBNAUI;
import org.archstudio.bna.ui.IUITransferProvider;
import org.archstudio.bna.utils.BNAUtils;
import org.archstudio.bna.utils.BNAUtils2;
import org.archstudio.bna.utils.BNAUtils2.ThingsAtLocation;
import org.archstudio.bna.utils.DefaultCoordinate;
import org.archstudio.sysutils.Finally;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.swt.SWT;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.DropTargetListener;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.dnd.TransferData;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.GestureEvent;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;

import com.google.common.collect.Maps;

public abstract class AbstractSWTUI extends AbstractBNAUI implements IBNAUI {

	class SWTMouseListener implements MouseListener {

		MouseEvent mouseDownEvent = null;

		@Override
		public void mouseUp(MouseEvent e) {
			mouseEventSWT(MouseType.UP, e);
			if (mouseDownEvent != null) {
				if (e.time - mouseDownEvent.time < 250) {
					mouseEventSWT(MouseType.CLICK, e);
				}
				mouseDownEvent = null;
			}
		}

		@Override
		public void mouseDown(MouseEvent e) {
			mouseEventSWT(MouseType.DOWN, mouseDownEvent = e);
		}

		@Override
		public void mouseDoubleClick(MouseEvent e) {
		}
	}

	class SWTMouseEventListener implements Listener {

		MouseType mouseType;

		public SWTMouseEventListener(MouseType mouseType) {
			this.mouseType = mouseType;
		}

		@Override
		public void handleEvent(Event event) {
			mouseEventSWT(mouseType, new MouseEvent(event));
		}
	}

	class SWTGestureEventListener implements Listener {

		@Override
		public void handleEvent(Event event) {
			gestureEventSWT(GestureType.fromSwtEvent(event.detail), new GestureEvent(event));
		}
	}

	class SWTMouseMoveListener implements MouseMoveListener {

		@Override
		public void mouseMove(MouseEvent e) {
			mouseEventSWT(MouseType.MOVE, e);
		}
	}

	class SWTMouseTrackListener implements MouseTrackListener {

		@Override
		public void mouseEnter(MouseEvent e) {
			mouseEventSWT(MouseType.ENTER, e);
		}

		@Override
		public void mouseExit(MouseEvent e) {
			mouseEventSWT(MouseType.EXIT, e);
		}

		@Override
		public void mouseHover(MouseEvent e) {
		}
	}

	class SWTKeyListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent e) {
			keyEventSWT(KeyType.PRESSED, e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			keyEventSWT(KeyType.RELEASED, e);
		}
	}

	class SWTFocusListener implements FocusListener {

		@Override
		public void focusGained(FocusEvent e) {
			focusEventSWT(FocusType.GAINED, e);
		}

		@Override
		public void focusLost(FocusEvent e) {
			focusEventSWT(FocusType.LOST, e);
		}
	}

	class SWTDropTargetListener implements DropTargetListener {

		protected void dndEvent(DNDType type, DropTargetEvent e) {
			try (Finally lock = BNAUtils.lock()) {
				DNDData data = new DNDData();
				TransferData td = e.currentDataType;
				for (IUITransferProvider provider : transferProviders.values()) {
					if (provider.isSupportedType(td)) {
						try {
							provider.addData(td, data);
						}
						catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
				Point p = swtComposite.toControl(e.x, e.y);
				ICoordinate location = DefaultCoordinate.forLocal(new Point(p.x, p.y), view.getCoordinateMapper());
				try {
					ThingsAtLocation thingsAtLocation = BNAUtils2.getThingsAtLocation(view, location);
					dndEvent2(view, type, data, location, thingsAtLocation);
				}
				catch (Exception e1) {
					e1.printStackTrace();
				}
				try {
					List<IThing> things = view.getThingsAt(location);
					dndEvent1(type, data, things, location);
				}
				catch (Exception e1) {
					e1.printStackTrace();
				}
				e.detail = data.getDropType().toSWTDNDDropOperation();
			}
			catch (Exception e2) {
				e2.printStackTrace();
			}
		}

		@SuppressWarnings("deprecation")
		@Override
		public void dragEnter(DropTargetEvent e) {
			dndEvent(DNDType.ENTER, e);
		}

		@Override
		public void dragOver(DropTargetEvent e) {
			dndEvent(DNDType.DRAG, e);
		}

		@Override
		public void dragOperationChanged(DropTargetEvent e) {
			dndEvent(DNDType.DRAG, e);
		}

		@SuppressWarnings("deprecation")
		@Override
		public void dragLeave(DropTargetEvent e) {
			dndEvent(DNDType.EXIT, e);
		}

		@Override
		public void dropAccept(DropTargetEvent e) {
			dndEvent(DNDType.DRAG, e);
		}

		@Override
		public void drop(DropTargetEvent e) {
			dndEvent(DNDType.DROP, e);
		}
	}

	protected Composite swtComposite;
	protected DropTarget swtDropTarget;
	protected Map<Transfer, IUITransferProvider> transferProviders = Maps.newHashMap();

	public AbstractSWTUI(IBNAView view) {
		super(view);
	}

	protected void init(Composite swtComposite, boolean registerListeners) {
		super.init(swtComposite);
		this.swtComposite = swtComposite;
		this.swtDropTarget = new DropTarget(swtComposite, //
				DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_DEFAULT | DND.DROP_LINK);
		if (Platform.isRunning()) {
			IExtensionRegistry reg = Platform.getExtensionRegistry();
			// The Extension Registry can be null if we're running outside of Eclipse
			for (IConfigurationElement configurationElement : reg
					.getConfigurationElementsFor("org.archstudio.bna.dndtransferprovider")) {
				String transferClassName = configurationElement.getAttribute("class");
				if (transferClassName != null) {
					String bundleName = configurationElement.getDeclaringExtension().getContributor().getName();
					try {
						Class<?> packageClass = Platform.getBundle(bundleName).loadClass(transferClassName);
						Transfer provider = (Transfer) packageClass.getMethod("getInstance").invoke(null);
						transferProviders.put(provider, (IUITransferProvider) provider);
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		swtDropTarget.setTransfer(transferProviders.keySet().toArray(new Transfer[transferProviders.size()]));
		if (registerListeners) {
			swtComposite.addMouseListener(new SWTMouseListener());
			swtComposite.addMouseMoveListener(new SWTMouseMoveListener());
			swtComposite.addMouseTrackListener(new SWTMouseTrackListener());
			swtComposite.addKeyListener(new SWTKeyListener());
			swtComposite.addFocusListener(new SWTFocusListener());
			swtComposite.addListener(SWT.MouseHorizontalWheel, new SWTMouseEventListener(MouseType.HORIZONTAL_WHEEL));
			swtComposite.addListener(SWT.MouseVerticalWheel, new SWTMouseEventListener(MouseType.VERTICAL_WHEEL));
			swtComposite.addListener(SWT.Gesture, new SWTGestureEventListener());
			swtDropTarget.addDropListener(new SWTDropTargetListener());

			// Register menu.
			final MenuDetectEvent[] menuDetectEvent = new MenuDetectEvent[1];
			swtComposite.addMenuDetectListener(new MenuDetectListener() {
				@Override
				public void menuDetected(MenuDetectEvent e) {
					menuDetectEvent[0] = e;
				}
			});
			MenuManager menuMgr = new MenuManager();
			menuMgr.setRemoveAllWhenShown(true);
			menuMgr.addMenuListener(new IMenuListener() {
				@Override
				public void menuAboutToShow(IMenuManager manager) {
					menuEventSWT(menuDetectEvent[0], manager);
				}
			});
			Menu menu = menuMgr.createContextMenu(swtComposite);
			swtComposite.setMenu(menu);
		}
	}

	@Override
	public void dispose() {
		swtDropTarget.dispose();
	}
}