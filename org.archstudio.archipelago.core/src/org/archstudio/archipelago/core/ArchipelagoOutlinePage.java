package org.archstudio.archipelago.core;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.archstudio.archipelago.core.structure.StructureTreePlugin;
import org.archstudio.eclipse.ui.views.AbstractArchStudioOutlinePage;
import org.archstudio.editormanager.IEditorManager;
import org.archstudio.filemanager.IFileManager;
import org.archstudio.filemanager.IFileManagerListener;
import org.archstudio.graphlayout.IGraphLayout;
import org.archstudio.resources.IResources;
import org.archstudio.swtutils.LocalSelectionTransfer;
import org.archstudio.swtutils.SWTWidgetUtils;
import org.archstudio.xarchadt.IXArchADT;
import org.archstudio.xarchadt.IXArchADTFileListener;
import org.archstudio.xarchadt.IXArchADTModelListener;
import org.archstudio.xarchadt.ObjRef;
import org.archstudio.xarchadt.XArchADTFileEvent;
import org.archstudio.xarchadt.XArchADTModelEvent;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DragSourceEvent;
import org.eclipse.swt.dnd.DragSourceListener;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Item;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchActionConstants;

public class ArchipelagoOutlinePage extends AbstractArchStudioOutlinePage implements IFileManagerListener {
	protected List<IArchipelagoTreePlugin> treePluginList = new ArrayList<IArchipelagoTreePlugin>();
	protected IArchipelagoTreePlugin[] treePlugins = new IArchipelagoTreePlugin[0];

	protected ArchipelagoServices AS = null;

	public ArchipelagoOutlinePage(ArchipelagoEditor editor, IXArchADT xarch, ObjRef documentRootRef,
			IResources resources, IFileManager fileman, IEditorManager editorManager, IPreferenceStore prefs,
			IGraphLayout graphLayout) {
		super(xarch, documentRootRef, resources, false, true);

		IArchipelagoEventBus eventBus = new DefaultArchipelagoEventBus();
		//This stuff lets us open multiple editors on the same document.
		ArchipelagoDataCache servicesCache = ArchipelagoDataCache.getInstance();
		TreeNodeDataCache data = servicesCache.getData(documentRootRef);
		if (data == null) {
			data = new TreeNodeDataCache();
		}
		AS = new ArchipelagoServices(eventBus, new DefaultArchipelagoEditorPane(editor), data, xarch, resources,
				fileman, editorManager, prefs, graphLayout);
		servicesCache.addCacheEntry(this, documentRootRef, data);
	}

	@Override
	public void createControl(Composite parent) {
		super.createControl(parent);

		getTreeViewer().setSorter(new ViewerSorter());

		addTreePlugin(new RootTreePlugin(getTreeViewer(), AS, documentRootRef));
		addTreePlugin(new FolderNodeTreePlugin(getTreeViewer(), AS, documentRootRef));
		addTreePlugin(new StructureTreePlugin(getTreeViewer(), AS, documentRootRef));
		//addTreePlugin(new FlowTreePlugin(getTreeViewer(), AS, documentRootRef));
		//addTreePlugin(new MemoryTreePlugin(getTreeViewer(), AS, documentRootRef));
		//addTreePlugin(new TypesTreePlugin(getTreeViewer(), AS, xArchRef));
		//addTreePlugin(new OptionsTreePlugin(getTreeViewer(), AS, xArchRef));
		//addTreePlugin(new VariantsTreePlugin(getTreeViewer(), AS, xArchRef));
		//addTreePlugin(new StatechartsTreePlugin(getTreeViewer(), AS, xArchRef));
		//addTreePlugin(new StatelineTreePlugin(getTreeViewer(), AS, xArchRef));
		//addTreePlugin(new HPCTreePlugin(getTreeViewer(), AS, documentRootRef));

		// scan eclipse plugins and create additional tree plugins
		// TODO: sort these using some scheme?
		for (IConfigurationElement element : Platform.getExtensionRegistry().getConfigurationElementsFor(
				"org.archstudio.archipelago")) {
			try {
				String className = element.getAttribute("class");
				String pluginName = element.getContributor().getName();
				Class<?> clazz = Platform.getBundle(pluginName).loadClass(className);
				Constructor<?> classConstructor = clazz.getConstructor(new Class<?>[0]);
				Object obj = classConstructor.newInstance(new Object[0]);
				addTreePlugin((IArchipelagoTreePlugin) obj);
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

		getTreeViewer().expandToLevel(4);
		getTreeViewer().reveal(documentRootRef);
		getTreeViewer().addDoubleClickListener(new DoubleClickListener());
		initDragAndDrop();

		getTreeViewer().setColumnProperties(new String[] { "treeNode" });
		getTreeViewer().setCellEditors(new CellEditor[] { new TextCellEditor(getTreeViewer().getTree()) });
		getTreeViewer().setCellModifier(new DefaultArchipelagoCellModifier());
	}

	public void dispose() {
		super.dispose();
		ArchipelagoDataCache.getInstance().removeCacheEntry(ArchipelagoOutlinePage.this);
	}

	protected void initDragAndDrop() {
		Transfer[] transfers = new Transfer[] { LocalSelectionTransfer.getInstance() };
		getTreeViewer().addDragSupport(DND.DROP_LINK, transfers, new ArchipelagoOutlinePageDragSourceListener());
	}

	@Override
	protected ITreeContentProvider createViewContentProvider() {
		return new ViewContentProvider();
	}

	@Override
	protected ILabelProvider createViewLabelProvider() {
		return new ViewLabelProvider();
	}

	@Override
	public void updateOutlinePage() {
		super.updateOutlinePage();
	}

	public Object[] getSelectedNodes() {
		ISelection selection = getSelection();
		if (selection instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection) selection;
			Object[] nodes = ss.toArray();
			return nodes;
		}
		else {
			return new Object[0];
		}
	}

	@Override
	protected void fillContextMenu(IMenuManager menuMgr) {
		Object[] selectedNodes = getSelectedNodes();

		if (selectedNodes.length == 0) {
			Action noAction = new Action("[No Selection]") {
				@Override
				public void run() {
				}
			};
			noAction.setEnabled(false);
			menuMgr.add(noAction);
		}
		else {
			for (IArchipelagoTreePlugin treePlugin : treePlugins) {
				IArchipelagoTreeContextMenuFiller[] ls = treePlugin.getContextMenuFillers();
				if (ls != null) {
					for (IArchipelagoTreeContextMenuFiller element : ls) {
						element.fillContextMenu(menuMgr, selectedNodes);
					}
				}
			}
		}
		if (menuMgr.getItems() != null && menuMgr.getItems().length == 0) {
			Action noAction = new Action("[No Actions Available]") {
				@Override
				public void run() {
				}
			};
			noAction.setEnabled(false);
			menuMgr.add(noAction);
		}
		menuMgr.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	public void handleXArchFlatEvent(XArchADTModelEvent evt) {
		for (IArchipelagoTreePlugin treePlugin : treePlugins) {
			IXArchADTModelListener l = treePlugin.getXArchADTModelListener();
			if (l != null) {
				l.handleXArchADTModelEvent(evt);
			}
		}
	}

	public void handleXArchFileEvent(XArchADTFileEvent evt) {
		for (IArchipelagoTreePlugin treePlugin : treePlugins) {
			IXArchADTFileListener l = treePlugin.getXArchADTFileListener();
			if (l != null) {
				l.handleXArchADTFileEvent(evt);
			}
		}
	}

	public void fileSaving(final ObjRef xArchRef, IProgressMonitor monitor) {
		final IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				monitor.beginTask("Notifying Archipelago Elements", treePlugins.length);
				for (int i = 0; i < treePlugins.length; i++) {
					IArchipelagoTreePlugin treePlugin = treePlugins[i];
					IFileManagerListener l = treePlugin.getFileManagerListener();
					if (l != null) {
						l.fileSaving(xArchRef, monitor);
					}
					monitor.worked(i);
				}
				monitor.done();
			}
		};
		SWTWidgetUtils.async(getTreeViewer(), new Runnable() {
			@Override
			public void run() {
				try {
					ProgressMonitorDialog pmd = new ProgressMonitorDialog(getSite().getShell());
					pmd.run(true, false, op);
				}
				catch (InvocationTargetException ite) {
					ite.printStackTrace();
				}
				catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void fileDirtyStateChanged(ObjRef xArchRef, boolean dirty) {
		for (IArchipelagoTreePlugin treePlugin : treePlugins) {
			IFileManagerListener l = treePlugin.getFileManagerListener();
			if (l != null) {
				l.fileDirtyStateChanged(xArchRef, dirty);
			}
		}
	}

	@Override
	public void focusEditor(String editorName, ObjRef[] refs) {
		for (IArchipelagoTreePlugin treePlugin : treePlugins) {
			IArchipelagoEditorFocuser editorFocuser = treePlugin.getEditorFocuser();
			if (editorFocuser != null) {
				editorFocuser.focusEditor(editorName, refs);
			}
		}
	}

	public void addTreePlugin(IArchipelagoTreePlugin treePlugin) {
		treePluginList.add(treePlugin);
		treePlugins = treePluginList.toArray(new IArchipelagoTreePlugin[treePluginList.size()]);
	}

	public void removeTreePlugin(IArchipelagoTreePlugin treePlugin) {
		treePluginList.remove(treePlugin);
		treePlugins = treePluginList.toArray(new IArchipelagoTreePlugin[treePluginList.size()]);
	}

	class ViewContentProvider implements ITreeContentProvider {
		public Object[] getChildren(Object parentElement) {
			List<? extends Object> childrenList = Collections.emptyList();
			for (IArchipelagoTreePlugin treePlugin : treePlugins) {
				IArchipelagoTreeContentProvider contentProvider = treePlugin.getContentProvider();
				if (contentProvider != null) {
					childrenList = contentProvider.getChildren(parentElement, childrenList);
				}
			}
			Object[] children = childrenList.toArray(new Object[0]);
			return children;
		}

		public Object[] getElements(Object inputElement) {
			return getChildren(inputElement);
		}

		public Object getParent(Object element) {
			Object parent = null;
			for (IArchipelagoTreePlugin treePlugin : treePlugins) {
				IArchipelagoTreeContentProvider contentProvider = treePlugin.getContentProvider();
				if (contentProvider != null) {
					parent = contentProvider.getParent(element, parent);
				}
			}
			return parent;
		}

		public boolean hasChildren(Object element) {
			boolean hasChildren = false;
			for (IArchipelagoTreePlugin treePlugin : treePlugins) {
				IArchipelagoTreeContentProvider contentProvider = treePlugin.getContentProvider();
				if (contentProvider != null) {
					hasChildren = contentProvider.hasChildren(element, hasChildren);
				}
			}
			return hasChildren;
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			for (IArchipelagoTreePlugin treePlugin : treePlugins) {
				IArchipelagoTreeContentProvider contentProvider = treePlugin.getContentProvider();
				if (contentProvider != null) {
					contentProvider.inputChanged(viewer, oldInput, newInput);
				}
			}
		}

		public void dispose() {
			for (IArchipelagoTreePlugin treePlugin : treePlugins) {
				IArchipelagoTreeContentProvider contentProvider = treePlugin.getContentProvider();
				if (contentProvider != null) {
					contentProvider.dispose();
				}
			}
		}
	}

	class ViewLabelProvider extends LabelProvider implements ILabelProvider {
		@Override
		public String getText(Object element) {
			String text = null;
			for (IArchipelagoTreePlugin treePlugin : treePlugins) {
				IArchipelagoLabelProvider labelProvider = treePlugin.getLabelProvider();
				if (labelProvider != null) {
					text = labelProvider.getText(element, text);
				}
			}
			if (text == null) {
				return "[Error: No Label Provider for " + element + "]";
			}
			return text;
		}

		@Override
		public Image getImage(Object element) {
			Image img = null;
			for (IArchipelagoTreePlugin treePlugin : treePlugins) {
				IArchipelagoLabelProvider labelProvider = treePlugin.getLabelProvider();
				if (labelProvider != null) {
					img = labelProvider.getImage(element, img);
				}
			}
			return img;
		}
	}

	class DoubleClickListener implements IDoubleClickListener {
		public void doubleClick(DoubleClickEvent event) {
			ISelection selection = getTreeViewer().getSelection();
			if (selection instanceof IStructuredSelection) {
				IStructuredSelection structuredSelection = (IStructuredSelection) selection;
				if (structuredSelection.size() != 1) {
					return;
				}
				Object o = structuredSelection.getFirstElement();
				for (IArchipelagoTreePlugin treePlugin : treePlugins) {
					IArchipelagoTreeDoubleClickHandler doubleClickHandler = treePlugin.getDoubleClickHandler();
					if (doubleClickHandler != null) {
						doubleClickHandler.treeNodeDoubleClicked(o);
					}
				}
			}
		}
	}

	/*
	 * This is a somewhat dirty hack because Eclipse's (actually JFace's) cell
	 * editing behavior is not particularly flexible. Basically, if you set a
	 * cell editor and a cell modifier the normal way, then any selection of a
	 * node will enable editing of that node. This is too permissive, it means
	 * that editors are always being activated and it doesn't feel right. What
	 * we do instead is only enable cell editing after the user has hit a
	 * context menu option (for example), and only on a particular cell for one
	 * shot. If callers (e.g., fillContextMenu) want to allow cell editing on a
	 * particular cell, they need to call ArchipelagoUtils.beginCellEditing()
	 * which will set the appropriate data element in the viewer to allow one
	 * shot of cell editing.
	 */
	class DefaultArchipelagoCellModifier implements ICellModifier {
		public boolean canModify(Object element, String property) {
			if (element == null) {
				return false;
			}
			if (getTreeViewer().getData("allowCellEditing") != element) {
				return false;
			}
			for (IArchipelagoTreePlugin treePlugin : treePlugins) {
				ICellModifier[] cms = treePlugin.getCellModifiers();
				if (cms != null) {
					for (ICellModifier cm : cms) {
						if (cm.canModify(element, property)) {
							return true;
						}
					}
				}
			}
			return false;
		}

		public Object getValue(Object element, String property) {
			for (IArchipelagoTreePlugin treePlugin : treePlugins) {
				ICellModifier[] cms = treePlugin.getCellModifiers();
				if (cms != null) {
					for (ICellModifier cm : cms) {
						if (cm.canModify(element, property)) {
							return cm.getValue(element, property);
						}
					}
				}
			}
			return null;
		}

		public void modify(Object element, String property, Object value) {
			//SWT bug workaround
			if (element instanceof Item) {
				element = ((Item) element).getData();
			}
			for (IArchipelagoTreePlugin treePlugin : treePlugins) {
				ICellModifier[] cms = treePlugin.getCellModifiers();
				if (cms != null) {
					for (ICellModifier cm : cms) {
						if (cm.canModify(element, property)) {
							cm.modify(element, property, value);
						}
					}
				}
			}
		}
	}

	//This class purposefully only supports single-selection drag;
	//if multi-selection is needed it must be updated later.
	class ArchipelagoOutlinePageDragSourceListener implements DragSourceListener {
		private ISelection selectionOnDrag = null;

		public void dragStart(DragSourceEvent event) {
			event.doit = false;

			//We have to hang on to the current selection because on some
			//platforms (Mac) the tree selection is cleared when dragging
			//starts
			selectionOnDrag = getTreeViewer().getSelection();
			if (selectionOnDrag instanceof IStructuredSelection) {
				IStructuredSelection structuredSelection = (IStructuredSelection) selectionOnDrag;
				Object[] selectedNodes = structuredSelection.toArray();
				if (selectedNodes.length == 1) {
					Object data = selectedNodes[0];
					if (data instanceof java.io.Serializable) {
						event.data = selectedNodes[0];
						for (IArchipelagoTreePlugin treePlugin : treePlugins) {
							DragSourceListener dsl = treePlugin.getDragSourceListener();
							if (dsl != null) {
								dsl.dragStart(event);
							}
						}
					}
				}
			}
		}

		public void dragSetData(DragSourceEvent event) {
			if (LocalSelectionTransfer.getInstance().isSupportedType(event.dataType)) {
				event.data = selectionOnDrag;
				//LocalSelectionTransfer.getInstance().setSelection(selection);
				for (IArchipelagoTreePlugin treePlugin : treePlugins) {
					DragSourceListener dsl = treePlugin.getDragSourceListener();
					if (dsl != null) {
						dsl.dragSetData(event);
					}
				}
			}
		}

		public void dragFinished(DragSourceEvent event) {
			for (IArchipelagoTreePlugin treePlugin : treePlugins) {
				DragSourceListener dsl = treePlugin.getDragSourceListener();
				if (dsl != null) {
					dsl.dragFinished(event);
				}
			}
		}
	}

	static class DefaultArchipelagoEditorPane implements IArchipelagoEditorPane {
		protected ArchipelagoEditor editor = null;
		protected Map<String, Object> propertyMap = new HashMap<String, Object>();

		public DefaultArchipelagoEditorPane(ArchipelagoEditor editor) {
			this.editor = editor;
		}

		public void clearEditor() {
			editor.clearEditor();
			propertyMap.clear();
		}

		public void displayDefaultEditor() {
			editor.updateEditor();
		}

		public Composite getParentComposite() {
			return editor.getParentComposite();
		}

		public IActionBars getActionBars() {
			return ((IEditorSite) editor.getSite()).getActionBars();
		}

		public void setProperty(String name, Object value) {
			propertyMap.put(name, value);
		}

		public Object getProperty(String name) {
			return propertyMap.get(name);
		}
	}

}
