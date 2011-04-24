package org.archstudio.xadlswt.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import org.archstudio.xadl.common.XadlUtils;
import org.archstudio.xadlswt.common.XadlTreeUtils.Type;
import org.archstudio.xarchadt.common.IXArchADT;
import org.archstudio.xarchadt.common.ObjRef;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.IWorkbenchSite;

public class XadlTreeContentProvider implements ITreeContentProvider{

	protected static final Object[] EMPTY_ARRAY = new Object[0];
	
	protected IXArchADT xarch = null;
	protected ObjRef rootRef;
	protected Set<Type> flags = EnumSet.noneOf(Type.class);
	
	protected List<Filter> filters = Collections.synchronizedList(new ArrayList<Filter>());
	
	public XadlTreeContentProvider(IXArchADT xarch, ObjRef rootRef, Set<Type> flags){
		this.xarch = xarch;
		this.rootRef = rootRef;
		this.flags = flags;
	}
	
	public void addFilter(List<ObjRef> showOnlyTheseElements, Type type){
		Filter f = new Filter(showOnlyTheseElements, type);
		filters.add(f);
	}
	
	public void clearFilters(){
		filters.clear();
	}
	
	protected Filter getFilter(Type type){
		for(Filter f : filters) {
			if(f.getType().equals(type)) return f;
		}
		return null;
	}
	
	public boolean shouldShow(Type type){
		return flags.contains(type);
	}
	
	public Object[] getElements(Object inputElement){
		return getChildren(inputElement);
	}
	
	protected boolean isAllowedByFilter(ObjRef ref, Type type){
		Filter f = getFilter(type);
		if(f == null) return true;
		
		for(ObjRef elt : f.getElts()) {
			if(ref.equals(elt)){
				return true;
			}
		}
		return false;
	}
	
	private void addChildren(List<ObjRef> l, ObjRef ref, String childName, Type childType){
		if(shouldShow(childType)){
			List<ObjRef> childRefs = xarch.getAll(ref, childName);
			for(ObjRef childRef : childRefs) {
				if(isAllowedByFilter(childRef, childType)){
					l.add(childRef);
				}
			}
		}
	}
	
	public Object[] getChildren(Object parentElement){
		if(parentElement instanceof XadlTreeInput){
			return new Object[]{xarch.get(rootRef, "xADL")};
		}
		else if(parentElement instanceof IWorkbenchSite){
			return new Object[]{xarch.get(rootRef, "xADL")};
		}
		else if(parentElement instanceof ObjRef){
			ObjRef ref = (ObjRef)parentElement;
			Type type = XadlTreeUtils.getType(xarch, ref);
			
			List<ObjRef> childList = new ArrayList<ObjRef>();
			if(type.equals(Type.DOCUMENTROOT)){
				return new Object[]{xarch.get(ref, "xADL")};
			}
			else if(type.equals(Type.XADLELEMENT)){
				if(shouldShow(Type.STRUCTURE)){
					List<ObjRef> structureRefs = XadlUtils.getAllSubstitutionGroupElementsByTag(xarch, ref, "topLevelElement", "structure");
					for(ObjRef structureRef : structureRefs) {
						if(isAllowedByFilter(structureRef, Type.STRUCTURE)){
							childList.add(structureRef);
						}
					}
				}
			}
			else if(type.equals(Type.STRUCTURE)){
				addChildren(childList, ref, "component", Type.COMPONENT);
				addChildren(childList, ref, "connector", Type.CONNECTOR);
			}
			else if(type.equals(Type.COMPONENT)){
				addChildren(childList, ref, "interface", Type.COMPONENT_INTERFACE);
			}
			else if(type.equals(Type.CONNECTOR)){
				addChildren(childList, ref, "interface", Type.CONNECTOR_INTERFACE);
			}
			return childList.toArray();
		}
		return EMPTY_ARRAY;
	}
	
	public Object getParent(Object element){
		if(element instanceof ObjRef){
			return xarch.getParent((ObjRef)element);
		}
		return null;
	}
	
	public boolean hasChildren(Object element){
		return getChildren(element).length > 0;
	}
	
	public void dispose(){
	}
	
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput){
	}
	
	
	protected static class Filter{
		public List<ObjRef> elts;
		public Type type;
		
		public Filter(List<ObjRef> elts, Type type){
			this.elts = Collections.unmodifiableList(new ArrayList<ObjRef>(elts));
			this.type = type;
		}

		public List<ObjRef> getElts(){
			return elts;
		}

		public Type getType(){
			return type;
		}
	}
	
	public static class XadlTreeInput{
	}
}
