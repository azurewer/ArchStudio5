package org.archstudio.resources;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.archstudio.sysutils.SystemUtils;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.ISharedImages;

public class ArchStudioCommonResources {
	public static final String ICON_STRUCTURE = "archstudio:icon/structure";
	public static final String ICON_XML_DOCUMENT = "archstudio:icon/xml-document";

	public static final String ICON_OVERLAY_XML = "archstudio:icon-overlay/xml";

	public static final String ICON_COMPONENT = "archstudio:icon/component";
	
	//***************wjw201705***************//
	public static final String ICON_COALAGENT = "archstudio:icon/coalAgent";
	public static final String ICON_FUNAGENT = "archstudio:icon/funAgent";
	public static final String ICON_ENCAPSULATEDUNIT = "archstudio:icon/encapsulatedUnit";
	//***************wjw201705***************//
	
	
	public static final String ICON_CONNECTOR = "archstudio:icon/connector";
	public static final String ICON_INTERFACE = "archstudio:icon/interface";
	public static final String ICON_LINK = "archstudio:icon/link";
	public static final String ICON_TYPES = "archstudio:icon/types";
	public static final String ICON_COMPONENT_TYPE = "archstudio:icon/component-type";
	
	//***************wjw201705***************//
		public static final String ICON_COALAGENT_TYPE = "archstudio:icon/coalAgent-type";
		public static final String ICON_FUNAGENT_TYPE = "archstudio:icon/funAgent-type";
		public static final String ICON_ENCAPSULATEDUNIT_TYPE = "archstudio:icon/encapsulatedUnit-type";
		//***************wjw201705***************//
	
	
	public static final String ICON_CONNECTOR_TYPE = "archstudio:icon/connector-type";
	public static final String ICON_INTERFACE_TYPE = "archstudio:icon/interface-type";

	public static final String ICON_STATECHART = "archstudio:icon/statechart";
	public static final String ICON_STATE = "archstudio:icon/state";
	public static final String ICON_INITIAL_STATE = "archstudio:icon/initial-state";
	public static final String ICON_FINAL_STATE = "archstudio:icon/final-state";

	public static final String ICON_GRID = "archstudio:icon/grid";

	protected static Set<IResources> initedResources = new HashSet<IResources>();

	public static void init(IResources resources) {
		if (initedResources.contains(resources)) {
			return;
		}

		try {
			resources.createImage(ICON_STRUCTURE,
					SystemUtils.blt(ArchStudioCommonResources.class.getResourceAsStream("res/icon-structure.gif")));
			resources.createImage(ICON_TYPES,
					SystemUtils.blt(ArchStudioCommonResources.class.getResourceAsStream("res/icon-types.gif")));

			resources.createImage(ICON_OVERLAY_XML,
					SystemUtils.blt(ArchStudioCommonResources.class.getResourceAsStream("res/decorator-xml.gif")));
			Image xmlOverlay = resources.getImage(ICON_OVERLAY_XML);
			resources.createOverlayImage(ICON_XML_DOCUMENT, resources.getPlatformImage(ISharedImages.IMG_OBJ_FOLDER),
					new Image[] { xmlOverlay }, new int[] { IResources.BOTTOM_RIGHT });

			resources.createImage(ICON_COMPONENT,
					SystemUtils.blt(ArchStudioCommonResources.class.getResourceAsStream("res/icon-component.gif")));
			
			//***************wjw201705***************//
			resources.createImage(ICON_COALAGENT,
					SystemUtils.blt(ArchStudioCommonResources.class.getResourceAsStream("res/icon-coalAgent.gif")));
			resources.createImage(ICON_FUNAGENT,
					SystemUtils.blt(ArchStudioCommonResources.class.getResourceAsStream("res/icon-funAgent.gif")));
			resources.createImage(ICON_ENCAPSULATEDUNIT,
					SystemUtils.blt(ArchStudioCommonResources.class.getResourceAsStream("res/icon-encapsulatedUnit.gif")));
			//***************wjw201705***************//
			
			resources.createImage(ICON_CONNECTOR,
					SystemUtils.blt(ArchStudioCommonResources.class.getResourceAsStream("res/icon-connector.gif")));
			resources.createImage(ICON_INTERFACE,
					SystemUtils.blt(ArchStudioCommonResources.class.getResourceAsStream("res/icon-interface.gif")));
			resources.createImage(ICON_LINK,
					SystemUtils.blt(ArchStudioCommonResources.class.getResourceAsStream("res/icon-link.gif")));

			resources
					.createImage(ICON_COMPONENT_TYPE, SystemUtils.blt(ArchStudioCommonResources.class
							.getResourceAsStream("res/icon-component-type.gif")));
			
			//***************wjw201705***************//
			resources
					.createImage(ICON_COALAGENT_TYPE, SystemUtils.blt(ArchStudioCommonResources.class
					.getResourceAsStream("res/icon-coalAgent-type.gif")));
			
			resources
					.createImage(ICON_FUNAGENT_TYPE, SystemUtils.blt(ArchStudioCommonResources.class
					.getResourceAsStream("res/icon-funAgent-type.gif")));
			resources
					.createImage(ICON_ENCAPSULATEDUNIT_TYPE, SystemUtils.blt(ArchStudioCommonResources.class
					.getResourceAsStream("res/icon-encapsulatedUnit-type.gif")));
			//***************wjw201705***************//
			
			
			resources
					.createImage(ICON_CONNECTOR_TYPE, SystemUtils.blt(ArchStudioCommonResources.class
							.getResourceAsStream("res/icon-connector-type.gif")));
			resources
					.createImage(ICON_INTERFACE_TYPE, SystemUtils.blt(ArchStudioCommonResources.class
							.getResourceAsStream("res/icon-interface-type.gif")));

			resources.createImage(ICON_STATECHART,
					SystemUtils.blt(ArchStudioCommonResources.class.getResourceAsStream("res/icon-statechart.gif")));
			resources.createImage(ICON_STATE,
					SystemUtils.blt(ArchStudioCommonResources.class.getResourceAsStream("res/icon-state.gif")));
			resources.createImage(ICON_INITIAL_STATE,
					SystemUtils.blt(ArchStudioCommonResources.class.getResourceAsStream("res/icon-initialstate.gif")));
			resources.createImage(ICON_FINAL_STATE,
					SystemUtils.blt(ArchStudioCommonResources.class.getResourceAsStream("res/icon-finalstate.gif")));

			resources.createImage(ICON_GRID,
					SystemUtils.blt(ArchStudioCommonResources.class.getResourceAsStream("res/icon-grid.gif")));
		}
		catch (IOException ioe) {
			throw new RuntimeException("This shouldn't happen.");
		}

		initedResources.add(resources);
	}
}
