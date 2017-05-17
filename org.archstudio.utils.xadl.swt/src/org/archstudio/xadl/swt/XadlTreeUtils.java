package org.archstudio.xadl.swt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.archstudio.resources.ArchStudioCommonResources;
import org.archstudio.resources.IResources;
import org.archstudio.xarchadt.IXArchADT;
import org.archstudio.xarchadt.ObjRef;
import org.eclipse.swt.graphics.Image;

public class XadlTreeUtils {

	public enum Type {
		UNKNOWN("unknown"), DOCUMENTROOT("document root element"), XADLELEMENT("xADL element"), STRUCTURE(
				"structure"), COMPONENT("component"), COMPONENT_INTERFACE("component interface"), CONNECTOR(
						"connector"), CONNECTOR_INTERFACE("connector interface"), COALAGENT(
								"coalAgent"), COALAGENT_INTERFACE("coalAgent interface"), FUNAGENT(
										"funAgent"), FUNAGENT_INTERFACE("funAgent interface"), ENCAPSULATEDUNIT(
												"encapsulatedUnit"), ENCAPSULATEDUNIT_INTERFACE(
														"encapsulatedUnit interface"),;

		private String description;

		private Type(String description) {
			this.description = description;
		}

		public String getDescription() {
			return description;
		}
	}

	protected static Map<String, Type> pathToTypeMap = new HashMap<String, Type>();

	static {
		pathToTypeMap.put("", Type.DOCUMENTROOT);
		pathToTypeMap.put("xADL", Type.XADLELEMENT);
		pathToTypeMap.put("xADL/structure", Type.STRUCTURE);
		pathToTypeMap.put("xADL/structure/component", Type.COMPONENT);
		pathToTypeMap.put("xADL/structure/component/interface", Type.COMPONENT_INTERFACE);

		// ***************wjw201705***************//
		pathToTypeMap.put("xADL/structure/coalAgent", Type.COALAGENT);
		pathToTypeMap.put("xADL/structure/coalAgent/interface", Type.COALAGENT_INTERFACE);
		pathToTypeMap.put("xADL/structure/funAgent", Type.FUNAGENT);
		pathToTypeMap.put("xADL/structure/funAgent/interface", Type.FUNAGENT_INTERFACE);
		pathToTypeMap.put("xADL/structure/encapsulatedUnit", Type.ENCAPSULATEDUNIT);
		pathToTypeMap.put("xADL/structure/encapsulatedUnit/interface", Type.ENCAPSULATEDUNIT_INTERFACE);
		// ***************wjw201705***************//

		pathToTypeMap.put("xADL/structure/connector", Type.CONNECTOR);
		pathToTypeMap.put("xADL/structure/connector/interface", Type.CONNECTOR_INTERFACE);
	}

	public static Type getType(IXArchADT xarch, ObjRef ref) {
		String pathString = xarch.getTagsOnlyPathString(ref);
		Type type = pathToTypeMap.get(pathString);
		return type == null ? Type.UNKNOWN : type;
	}

	public static Image getIconForType(IResources resources, Type type) {
		ArchStudioCommonResources.init(resources);
		switch (type) {
		case UNKNOWN:
			return null;
		case DOCUMENTROOT:
			return resources.getImage(ArchStudioCommonResources.ICON_XML_DOCUMENT);
		case XADLELEMENT:
			return resources.getImage(ArchStudioCommonResources.ICON_XML_DOCUMENT);
		case STRUCTURE:
			return resources.getImage(ArchStudioCommonResources.ICON_STRUCTURE);
		case COMPONENT:
			return resources.getImage(ArchStudioCommonResources.ICON_COMPONENT);

		// ***************wjw201705***************//
		case COALAGENT:
			return resources.getImage(ArchStudioCommonResources.ICON_COALAGENT);

		case FUNAGENT:
			return resources.getImage(ArchStudioCommonResources.ICON_FUNAGENT);
		case ENCAPSULATEDUNIT:
			return resources.getImage(ArchStudioCommonResources.ICON_ENCAPSULATEDUNIT);
		// ***************wjw201705***************//

		case CONNECTOR:
			return resources.getImage(ArchStudioCommonResources.ICON_CONNECTOR);
		case COMPONENT_INTERFACE:
		case CONNECTOR_INTERFACE:
			// ***************wjw201705***************//
		case COALAGENT_INTERFACE:
		case FUNAGENT_INTERFACE:
		case ENCAPSULATEDUNIT_INTERFACE:
			// ***************wjw201705***************//
			return resources.getImage(ArchStudioCommonResources.ICON_INTERFACE);
		default:
			return null;
		}
	}

	public static List<String> typesToStrings(Set<Type> types) {
		List<String> stringList = new ArrayList<String>();

		for (Type t : types) {
			stringList.add(t.getDescription());
		}
		return stringList;
	}

	private XadlTreeUtils() {
	}
}
