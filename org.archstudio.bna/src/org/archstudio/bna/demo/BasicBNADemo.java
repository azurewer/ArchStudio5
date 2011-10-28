package org.archstudio.bna.demo;

import java.util.Arrays;

import org.archstudio.bna.BNACanvas;
import org.archstudio.bna.CoordinateMapperAdapter;
import org.archstudio.bna.IBNAModel;
import org.archstudio.bna.IBNAView;
import org.archstudio.bna.IBNAWorld;
import org.archstudio.bna.IThingLogicManager;
import org.archstudio.bna.LinearCoordinateMapper;
import org.archstudio.bna.facets.IHasMutableColor;
import org.archstudio.bna.facets.IHasMutableGradientFill;
import org.archstudio.bna.facets.IHasMutableSelected;
import org.archstudio.bna.facets.IHasMutableSize;
import org.archstudio.bna.facets.IHasMutableText;
import org.archstudio.bna.facets.IRelativeMovable;
import org.archstudio.bna.logics.background.RotatingOffsetLogic;
import org.archstudio.bna.logics.coordinating.WorldThingExternalEventsLogic;
import org.archstudio.bna.logics.editing.ClickSelectionLogic;
import org.archstudio.bna.logics.editing.DragMovableLogic;
import org.archstudio.bna.logics.editing.EditTextLogic;
import org.archstudio.bna.logics.editing.KeyNudgerLogic;
import org.archstudio.bna.logics.editing.MarqueeSelectionLogic;
import org.archstudio.bna.logics.editing.ReshapeRectangleLogic;
import org.archstudio.bna.logics.editing.ReshapeSplineLogic;
import org.archstudio.bna.logics.editing.SplineBreakLogic;
import org.archstudio.bna.logics.editing.StandardCursorLogic;
import org.archstudio.bna.logics.information.ToolTipLogic;
import org.archstudio.bna.logics.navigating.MousePanAndZoomLogic;
import org.archstudio.bna.things.glass.EndpointGlassThing;
import org.archstudio.bna.things.glass.RectangleGlassThing;
import org.archstudio.bna.things.glass.SplineGlassThing;
import org.archstudio.bna.utils.Assemblies;
import org.archstudio.bna.utils.DefaultBNAModel;
import org.archstudio.bna.utils.DefaultBNAView;
import org.archstudio.bna.utils.DefaultBNAWorld;
import org.archstudio.bna.utils.UserEditableUtils;
import org.archstudio.swtutils.constants.Flow;
import org.archstudio.swtutils.constants.Orientation;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class BasicBNADemo {

	public static void main(String args[]) {
		Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());

		final IBNAModel m = new DefaultBNAModel();

		IBNAWorld bnaWorld1 = new DefaultBNAWorld("bna", m);
		setupTopWorld(bnaWorld1);
		populateModel(bnaWorld1);

		IBNAView bnaView1 = new DefaultBNAView(null, bnaWorld1, new LinearCoordinateMapper());

		IBNAModel m2 = new DefaultBNAModel();
		IBNAWorld bnaWorld2 = new DefaultBNAWorld("subworld", m2);
		setupWorld(bnaWorld2);
		populateModel(bnaWorld2);

		populateWithViews(bnaWorld1, bnaView1, bnaWorld2);

		final BNACanvas bnaComposite = new BNACanvas(shell, SWT.V_SCROLL | SWT.H_SCROLL | SWT.DOUBLE_BUFFERED, bnaView1);

		bnaComposite.setSize(500, 500);
		bnaComposite.setBackground(display.getSystemColor(SWT.COLOR_WHITE));

		shell.setSize(400, 400);
		shell.open();

		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		display.dispose();
		System.exit(0);
	}

	static void setupTopWorld(IBNAWorld bnaWorld) {
		IThingLogicManager logicManager = bnaWorld.getThingLogicManager();
		logicManager.addThingLogic(new MousePanAndZoomLogic());
		setupWorld(bnaWorld);
	}

	static void setupWorld(IBNAWorld bnaWorld) {
		IThingLogicManager logicManager = bnaWorld.getThingLogicManager();

		logicManager.addThingLogic(new ClickSelectionLogic());
		logicManager.addThingLogic(new DragMovableLogic());
		logicManager.addThingLogic(new EditTextLogic());
		logicManager.addThingLogic(new KeyNudgerLogic());
		logicManager.addThingLogic(new MarqueeSelectionLogic());
		logicManager.addThingLogic(new MousePanAndZoomLogic());
		logicManager.addThingLogic(new ReshapeRectangleLogic());
		logicManager.addThingLogic(new ReshapeSplineLogic());
		logicManager.addThingLogic(new RotatingOffsetLogic());
		logicManager.addThingLogic(new SplineBreakLogic());
		logicManager.addThingLogic(new StandardCursorLogic());
		logicManager.addThingLogic(new ToolTipLogic());

		logicManager.addThingLogic(new WorldThingExternalEventsLogic());
	}

	static void populateModel(IBNAWorld world) {
		final IBNAModel model = world.getBNAModel();
		final RectangleGlassThing[] boxes = new RectangleGlassThing[50];

		for (int i = 0; i < boxes.length; i++) {
			RectangleGlassThing box = Assemblies.createRectangle(world, null, null);
			((IHasMutableGradientFill) Assemblies.BACKGROUND_KEY.get(box, model)).setGradientFilled(true);
			((IHasMutableColor) Assemblies.BACKGROUND_KEY.get(box, model)).setColor(new RGB(0, 0, 0));
			Assemblies.TEXT_KEY.get(box, model).setText(
					"Now is the time for all good men to come to the aid of their country");
			((IHasMutableColor) Assemblies.TEXT_KEY.get(box, model)).setColor(new RGB(255, 0, 0));
			box.setBoundingBox(new Rectangle(CoordinateMapperAdapter.getDefaultBounds().getSize().width / 2 + 20 + i
					* 10, CoordinateMapperAdapter.getDefaultBounds().getSize().height / 2 + 20 + i * 10, 100, 100));
			box.setSelected(i % 2 == 0);

			UserEditableUtils.addEditableQualities(box, IHasMutableSelected.USER_MAY_SELECT,
					IHasMutableSize.USER_MAY_RESIZE, IRelativeMovable.USER_MAY_MOVE);
			UserEditableUtils.addEditableQualities(Assemblies.TEXT_KEY.get(box, model),
					IHasMutableText.USER_MAY_EDIT_TEXT);

			boxes[i] = box;
		}

		EndpointGlassThing endpoint = Assemblies.createEndpoint(world, null, boxes[0]);
		((IHasMutableColor) Assemblies.BACKGROUND_KEY.get(endpoint, model)).setColor(new RGB(0, 0, 0));
		Assemblies.LABEL_KEY.get(endpoint, model).setColor(new RGB(0, 0, 0));
		Assemblies.LABEL_KEY.get(endpoint, model).setOrientation(Orientation.NORTHWEST);
		Assemblies.LABEL_KEY.get(endpoint, model).setFlow(Flow.INOUT);
		endpoint.setAnchorPoint(new Point(CoordinateMapperAdapter.getDefaultBounds().getSize().width / 2 + 20,
				CoordinateMapperAdapter.getDefaultBounds().getSize().height / 2 + 20));

		SplineGlassThing spline = Assemblies.createSpline(world, null, null);
		spline.setPoints(Arrays.asList(//
				new Point(CoordinateMapperAdapter.getDefaultBounds().getSize().width / 2 + 20, CoordinateMapperAdapter
						.getDefaultBounds().getSize().height / 2 + 20), //
				new Point(CoordinateMapperAdapter.getDefaultBounds().getSize().width / 2 + 30, CoordinateMapperAdapter
						.getDefaultBounds().getSize().height / 2 + 30), //
				new Point(CoordinateMapperAdapter.getDefaultBounds().getSize().width / 2 + 50, CoordinateMapperAdapter
						.getDefaultBounds().getSize().height / 2 + 50), //
				new Point(CoordinateMapperAdapter.getDefaultBounds().getSize().width / 2 + 200, CoordinateMapperAdapter
						.getDefaultBounds().getSize().height / 2 + 200)));

		// TODO: reimplement Arrowheads

		//spline.getEndpoint1ArrowheadThing().setArrowheadShape(ArrowheadShape.TRIANGLE);
		//spline.getEndpoint1ArrowheadThing().setArrowheadSize(10);
		//spline.getEndpoint1ArrowheadThing().setSecondaryColor(new RGB(128, 128, 128));
		//spline.getEndpoint1ArrowheadThing().setArrowheadFilled(true);
		//
		//spline.getEndpoint2ArrowheadThing().setArrowheadShape(ArrowheadShape.TRIANGLE);
		//spline.getEndpoint2ArrowheadThing().setArrowheadSize(10);
		//spline.getEndpoint2ArrowheadThing().setSecondaryColor(new RGB(128, 128, 128));
		//spline.getEndpoint2ArrowheadThing().setArrowheadFilled(true);

	}

	static void populateWithViews(IBNAWorld world, IBNAView parentView, IBNAWorld internalWorld) {
		IBNAModel model = world.getBNAModel();

		RectangleGlassThing vbox1 = Assemblies.createRectangleWithWorld(world, null, null);
		((IHasMutableGradientFill) Assemblies.BACKGROUND_KEY.get(vbox1, model)).setGradientFilled(true);
		((IHasMutableColor) Assemblies.BACKGROUND_KEY.get(vbox1, model)).setColor(new RGB(0, 0, 0));
		Assemblies.TEXT_KEY.get(vbox1, model).setText("Viewsion 1");
		((IHasMutableColor) Assemblies.TEXT_KEY.get(vbox1, model)).setColor(new RGB(255, 0, 0));
		vbox1.setBoundingBox(new Rectangle(CoordinateMapperAdapter.getDefaultBounds().getSize().width / 2 + 20 + 200,
				CoordinateMapperAdapter.getDefaultBounds().getSize().height / 2 + 20 + 100, 200, 200));
		Assemblies.WORLD_KEY.get(vbox1, model).setWorld(internalWorld);

		UserEditableUtils.addEditableQualities(vbox1, IHasMutableSelected.USER_MAY_SELECT,
				IHasMutableSize.USER_MAY_RESIZE, IRelativeMovable.USER_MAY_MOVE);
		UserEditableUtils.addEditableQualities(Assemblies.TEXT_KEY.get(vbox1, model),
				IHasMutableText.USER_MAY_EDIT_TEXT);

		RectangleGlassThing vbox2 = Assemblies.createRectangleWithWorld(world, null, null);
		((IHasMutableGradientFill) Assemblies.BACKGROUND_KEY.get(vbox2, model)).setGradientFilled(true);
		((IHasMutableColor) Assemblies.BACKGROUND_KEY.get(vbox2, model)).setColor(new RGB(0, 0, 0));
		Assemblies.TEXT_KEY.get(vbox2, model).setText("Viewsion 2");
		((IHasMutableColor) Assemblies.TEXT_KEY.get(vbox2, model)).setColor(new RGB(255, 0, 0));
		vbox2.setBoundingBox(new Rectangle(CoordinateMapperAdapter.getDefaultBounds().getSize().width / 2 + 20 + 400,
				CoordinateMapperAdapter.getDefaultBounds().getSize().height / 2 + 20 + 100, 200, 200));
		Assemblies.WORLD_KEY.get(vbox2, model).setWorld(internalWorld);

		UserEditableUtils.addEditableQualities(vbox2, IHasMutableSelected.USER_MAY_SELECT,
				IHasMutableSize.USER_MAY_RESIZE, IRelativeMovable.USER_MAY_MOVE);
		UserEditableUtils.addEditableQualities(Assemblies.TEXT_KEY.get(vbox2, model),
				IHasMutableText.USER_MAY_EDIT_TEXT);
	}
}
