package org.archstudio.bna.things.shapes;

import org.archstudio.bna.IBNAView;
import org.archstudio.bna.ICoordinateMapper;
import org.archstudio.bna.IThingPeer;
import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

/*
 * DO NOT EDIT THIS FILE, it is automatically generated. ANY MODIFICATIONS WILL BE OVERWRITTEN.
 *
 * To modify, update the thingdefinition extension at
 * org.archstudio.bna/Package[name=org.archstudio.bna.things.shapes]/Thing[name=Spline].
 */

@SuppressWarnings("all")
@NonNullByDefault
public abstract class SplineThingBase extends org.archstudio.bna.things.AbstractThing
		implements org.archstudio.bna.IThing, org.archstudio.bna.facets.IHasMutableArrowheads,
		org.archstudio.bna.facets.IHasBoundingBox, org.archstudio.bna.facets.IHasMutableEndpoints,
		org.archstudio.bna.facets.IHasMutableGlow, org.archstudio.bna.facets.IHasMutableLineData,
		org.archstudio.bna.facets.IHasMutableMidpoints, org.archstudio.bna.facets.IHasPoints,
		org.archstudio.bna.facets.IHasMutableReferencePoint, org.archstudio.bna.facets.IHasMutableSelected,
		org.archstudio.bna.logics.coordinating.StickPointLogic.IHasSecondaryPoint {
	public SplineThingBase(@Nullable Object id) {
		super(id);
	}

	@Override
	public IThingPeer<? extends SplineThing> createPeer(IBNAView view, ICoordinateMapper cm) {
		return new SplineThingPeer<>((SplineThing) this, view, cm);
	}

	@Override
	protected void initProperties() {
		initProperty(org.archstudio.bna.facets.IHasArrowheads.ARROWHEAD_1_COLOR_KEY, null);
		initProperty(org.archstudio.bna.facets.IHasArrowheads.ARROWHEAD_1_EDGE_COLOR_KEY, null);
		initProperty(org.archstudio.bna.facets.IHasArrowheads.ARROWHEAD_1_LENGTH_KEY, 20);
		initProperty(org.archstudio.bna.facets.IHasArrowheads.ARROWHEAD_1_SHAPE_KEY,
				org.archstudio.bna.constants.ArrowheadShape.TRIANGLE);
		initProperty(org.archstudio.bna.facets.IHasArrowheads.ARROWHEAD_1_WIDTH_KEY, 10);
		initProperty(org.archstudio.bna.facets.IHasArrowheads.ARROWHEAD_2_COLOR_KEY, null);
		initProperty(org.archstudio.bna.facets.IHasArrowheads.ARROWHEAD_2_EDGE_COLOR_KEY, null);
		initProperty(org.archstudio.bna.facets.IHasArrowheads.ARROWHEAD_2_LENGTH_KEY, 20);
		initProperty(org.archstudio.bna.facets.IHasArrowheads.ARROWHEAD_2_SHAPE_KEY,
				org.archstudio.bna.constants.ArrowheadShape.TRIANGLE);
		initProperty(org.archstudio.bna.facets.IHasArrowheads.ARROWHEAD_2_WIDTH_KEY, 10);
		initProperty(org.archstudio.bna.facets.IHasBoundingBox.BOUNDING_BOX_KEY,
				new org.eclipse.swt.graphics.Rectangle(0, 0, 30, 20));
		initProperty(org.archstudio.bna.facets.IHasEdgeColor.EDGE_COLOR_KEY, new org.eclipse.swt.graphics.RGB(0, 0, 0));
		initProperty(org.archstudio.bna.facets.IHasEndpoints.ENDPOINT_1_KEY, new java.awt.geom.Point2D.Double(10, 0));
		addShapeModifyingKey(org.archstudio.bna.facets.IHasEndpoints.ENDPOINT_1_KEY);
		initProperty(org.archstudio.bna.facets.IHasEndpoints.ENDPOINT_2_KEY, new java.awt.geom.Point2D.Double(0, 10));
		addShapeModifyingKey(org.archstudio.bna.facets.IHasEndpoints.ENDPOINT_2_KEY);
		initProperty(org.archstudio.bna.facets.IHasGlow.GLOW_ALPHA_KEY, 0.75d);
		initProperty(org.archstudio.bna.facets.IHasGlow.GLOW_COLOR_KEY, null);
		initProperty(org.archstudio.bna.facets.IHasGlow.GLOW_WIDTH_KEY, 20);
		initProperty(org.archstudio.bna.facets.IHasLineStyle.LINE_STYLE_KEY,
				org.archstudio.swtutils.constants.LineStyle.SOLID);
		initProperty(org.archstudio.bna.facets.IHasLineWidth.LINE_WIDTH_KEY, 1);
		initProperty(org.archstudio.bna.facets.IHasMidpoints.MIDPOINTS_KEY,
				new java.util.ArrayList<java.awt.geom.Point2D>());
		addShapeModifyingKey(org.archstudio.bna.facets.IHasMidpoints.MIDPOINTS_KEY);
		initProperty(org.archstudio.bna.facets.IHasPoints.POINTS_KEY,
				com.google.common.collect.Lists.<java.awt.geom.Point2D>newArrayList(
						new java.awt.geom.Point2D.Double(-1, 1), new java.awt.geom.Point2D.Double(0, -1),
						new java.awt.geom.Point2D.Double(1, 1)));
		initProperty(org.archstudio.bna.facets.IHasRotatingOffset.ROTATING_OFFSET_KEY, 0);
		initProperty(org.archstudio.bna.facets.IHasSelected.SELECTED_KEY, false);
		initProperty(org.archstudio.bna.facets.IHasRotatingOffset.TICKS_PER_INCREMENT_KEY, 10);
		super.initProperties();
	}

	@Override
	public @Nullable org.eclipse.swt.graphics.RGB getArrowhead1Color() {
		return get(org.archstudio.bna.facets.IHasArrowheads.ARROWHEAD_1_COLOR_KEY);
	}

	@Override
	public void setArrowhead1Color(@Nullable org.eclipse.swt.graphics.RGB arrowhead1Color) {
		set(org.archstudio.bna.facets.IHasArrowheads.ARROWHEAD_1_COLOR_KEY, arrowhead1Color);
	}

	@Override
	public @Nullable org.eclipse.swt.graphics.RGB getArrowhead1EdgeColor() {
		return get(org.archstudio.bna.facets.IHasArrowheads.ARROWHEAD_1_EDGE_COLOR_KEY);
	}

	@Override
	public void setArrowhead1EdgeColor(@Nullable org.eclipse.swt.graphics.RGB arrowhead1EdgeColor) {
		set(org.archstudio.bna.facets.IHasArrowheads.ARROWHEAD_1_EDGE_COLOR_KEY, arrowhead1EdgeColor);
	}

	@Override
	public int getArrowhead1Length() {
		return get(org.archstudio.bna.facets.IHasArrowheads.ARROWHEAD_1_LENGTH_KEY);
	}

	@Override
	public void setArrowhead1Length(int arrowhead1Length) {
		set(org.archstudio.bna.facets.IHasArrowheads.ARROWHEAD_1_LENGTH_KEY, arrowhead1Length);
	}

	@Override
	public org.archstudio.bna.constants.ArrowheadShape getArrowhead1Shape() {
		return get(org.archstudio.bna.facets.IHasArrowheads.ARROWHEAD_1_SHAPE_KEY);
	}

	@Override
	public void setArrowhead1Shape(org.archstudio.bna.constants.ArrowheadShape arrowhead1Shape) {
		set(org.archstudio.bna.facets.IHasArrowheads.ARROWHEAD_1_SHAPE_KEY, arrowhead1Shape);
	}

	@Override
	public int getArrowhead1Width() {
		return get(org.archstudio.bna.facets.IHasArrowheads.ARROWHEAD_1_WIDTH_KEY);
	}

	@Override
	public void setArrowhead1Width(int arrowhead1Width) {
		set(org.archstudio.bna.facets.IHasArrowheads.ARROWHEAD_1_WIDTH_KEY, arrowhead1Width);
	}

	@Override
	public @Nullable org.eclipse.swt.graphics.RGB getArrowhead2Color() {
		return get(org.archstudio.bna.facets.IHasArrowheads.ARROWHEAD_2_COLOR_KEY);
	}

	@Override
	public void setArrowhead2Color(@Nullable org.eclipse.swt.graphics.RGB arrowhead2Color) {
		set(org.archstudio.bna.facets.IHasArrowheads.ARROWHEAD_2_COLOR_KEY, arrowhead2Color);
	}

	@Override
	public @Nullable org.eclipse.swt.graphics.RGB getArrowhead2EdgeColor() {
		return get(org.archstudio.bna.facets.IHasArrowheads.ARROWHEAD_2_EDGE_COLOR_KEY);
	}

	@Override
	public void setArrowhead2EdgeColor(@Nullable org.eclipse.swt.graphics.RGB arrowhead2EdgeColor) {
		set(org.archstudio.bna.facets.IHasArrowheads.ARROWHEAD_2_EDGE_COLOR_KEY, arrowhead2EdgeColor);
	}

	@Override
	public int getArrowhead2Length() {
		return get(org.archstudio.bna.facets.IHasArrowheads.ARROWHEAD_2_LENGTH_KEY);
	}

	@Override
	public void setArrowhead2Length(int arrowhead2Length) {
		set(org.archstudio.bna.facets.IHasArrowheads.ARROWHEAD_2_LENGTH_KEY, arrowhead2Length);
	}

	@Override
	public org.archstudio.bna.constants.ArrowheadShape getArrowhead2Shape() {
		return get(org.archstudio.bna.facets.IHasArrowheads.ARROWHEAD_2_SHAPE_KEY);
	}

	@Override
	public void setArrowhead2Shape(org.archstudio.bna.constants.ArrowheadShape arrowhead2Shape) {
		set(org.archstudio.bna.facets.IHasArrowheads.ARROWHEAD_2_SHAPE_KEY, arrowhead2Shape);
	}

	@Override
	public int getArrowhead2Width() {
		return get(org.archstudio.bna.facets.IHasArrowheads.ARROWHEAD_2_WIDTH_KEY);
	}

	@Override
	public void setArrowhead2Width(int arrowhead2Width) {
		set(org.archstudio.bna.facets.IHasArrowheads.ARROWHEAD_2_WIDTH_KEY, arrowhead2Width);
	}

	@Override
	public org.eclipse.swt.graphics.Rectangle getBoundingBox() {
		return get(org.archstudio.bna.facets.IHasBoundingBox.BOUNDING_BOX_KEY);
	}

	/* package */ void setBoundingBox(org.eclipse.swt.graphics.Rectangle boundingBox) {
		set(org.archstudio.bna.facets.IHasBoundingBox.BOUNDING_BOX_KEY, boundingBox);
	}

	@Override
	public @Nullable org.eclipse.swt.graphics.RGB getEdgeColor() {
		return get(org.archstudio.bna.facets.IHasEdgeColor.EDGE_COLOR_KEY);
	}

	@Override
	public void setEdgeColor(@Nullable org.eclipse.swt.graphics.RGB edgeColor) {
		set(org.archstudio.bna.facets.IHasEdgeColor.EDGE_COLOR_KEY, edgeColor);
	}

	@Override
	public java.awt.geom.Point2D getEndpoint1() {
		return get(org.archstudio.bna.facets.IHasEndpoints.ENDPOINT_1_KEY);
	}

	@Override
	public void setEndpoint1(java.awt.geom.Point2D endpoint1) {
		set(org.archstudio.bna.facets.IHasEndpoints.ENDPOINT_1_KEY, endpoint1);
	}

	@Override
	public java.awt.geom.Point2D getEndpoint2() {
		return get(org.archstudio.bna.facets.IHasEndpoints.ENDPOINT_2_KEY);
	}

	@Override
	public void setEndpoint2(java.awt.geom.Point2D endpoint2) {
		set(org.archstudio.bna.facets.IHasEndpoints.ENDPOINT_2_KEY, endpoint2);
	}

	@Override
	public double getGlowAlpha() {
		return get(org.archstudio.bna.facets.IHasGlow.GLOW_ALPHA_KEY);
	}

	@Override
	public void setGlowAlpha(double glowAlpha) {
		set(org.archstudio.bna.facets.IHasGlow.GLOW_ALPHA_KEY, glowAlpha);
	}

	@Override
	public @Nullable org.eclipse.swt.graphics.RGB getGlowColor() {
		return get(org.archstudio.bna.facets.IHasGlow.GLOW_COLOR_KEY);
	}

	@Override
	public void setGlowColor(@Nullable org.eclipse.swt.graphics.RGB glowColor) {
		set(org.archstudio.bna.facets.IHasGlow.GLOW_COLOR_KEY, glowColor);
	}

	@Override
	public int getGlowWidth() {
		return get(org.archstudio.bna.facets.IHasGlow.GLOW_WIDTH_KEY);
	}

	@Override
	public void setGlowWidth(int glowWidth) {
		set(org.archstudio.bna.facets.IHasGlow.GLOW_WIDTH_KEY, glowWidth);
	}

	@Override
	public org.archstudio.swtutils.constants.LineStyle getLineStyle() {
		return get(org.archstudio.bna.facets.IHasLineStyle.LINE_STYLE_KEY);
	}

	@Override
	public void setLineStyle(org.archstudio.swtutils.constants.LineStyle lineStyle) {
		set(org.archstudio.bna.facets.IHasLineStyle.LINE_STYLE_KEY, lineStyle);
	}

	@Override
	public int getLineWidth() {
		return get(org.archstudio.bna.facets.IHasLineWidth.LINE_WIDTH_KEY);
	}

	@Override
	public void setLineWidth(int lineWidth) {
		set(org.archstudio.bna.facets.IHasLineWidth.LINE_WIDTH_KEY, lineWidth);
	}

	@Override
	public java.util.List<java.awt.geom.Point2D> getMidpoints() {
		return get(org.archstudio.bna.facets.IHasMidpoints.MIDPOINTS_KEY);
	}

	@Override
	public void setMidpoints(java.util.List<java.awt.geom.Point2D> midpoints) {
		set(org.archstudio.bna.facets.IHasMidpoints.MIDPOINTS_KEY, midpoints);
	}

	@Override
	public java.util.List<java.awt.geom.Point2D> getPoints() {
		return get(org.archstudio.bna.facets.IHasPoints.POINTS_KEY);
	}

	/* package */ void setPoints(java.util.List<java.awt.geom.Point2D> points) {
		set(org.archstudio.bna.facets.IHasPoints.POINTS_KEY, points);
	}

	@Override
	public int getRotatingOffset() {
		return get(org.archstudio.bna.facets.IHasRotatingOffset.ROTATING_OFFSET_KEY);
	}

	@Override
	public void setRotatingOffset(int rotatingOffset) {
		set(org.archstudio.bna.facets.IHasRotatingOffset.ROTATING_OFFSET_KEY, rotatingOffset);
	}

	@Override
	public boolean isSelected() {
		return get(org.archstudio.bna.facets.IHasSelected.SELECTED_KEY);
	}

	@Override
	public void setSelected(boolean selected) {
		set(org.archstudio.bna.facets.IHasSelected.SELECTED_KEY, selected);
	}

	@Override
	public int getTicksPerIncrement() {
		return get(org.archstudio.bna.facets.IHasRotatingOffset.TICKS_PER_INCREMENT_KEY);
	}

	@Override
	public void setTicksPerIncrement(int ticksPerIncrement) {
		set(org.archstudio.bna.facets.IHasRotatingOffset.TICKS_PER_INCREMENT_KEY, ticksPerIncrement);
	}
}