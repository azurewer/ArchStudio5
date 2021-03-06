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
 * org.archstudio.bna/Package[name=org.archstudio.bna.things.shapes]/Thing[name=Ellipse].
 */

@SuppressWarnings("all")
@NonNullByDefault
public abstract class EllipseThingBase extends org.archstudio.bna.things.AbstractThing
		implements org.archstudio.bna.IThing, org.archstudio.bna.facets.IHasMutableAlpha,
		org.archstudio.bna.facets.IHasMutableBoundingBox, org.archstudio.bna.facets.IHasMutableCount,
		org.archstudio.bna.facets.IHasMutableGlow, org.archstudio.bna.facets.IHasMutableGradientFilled,
		org.archstudio.bna.facets.IHasMutableLineData, org.archstudio.bna.facets.IHasMutableMinimumSize,
		org.archstudio.bna.facets.IHasMutableReferencePoint, org.archstudio.bna.facets.IHasMutableSelected,
		org.archstudio.bna.facets.IHasMutableStickyShape {
	public EllipseThingBase(@Nullable Object id) {
		super(id);
	}

	@Override
	public IThingPeer<? extends EllipseThing> createPeer(IBNAView view, ICoordinateMapper cm) {
		return new EllipseThingPeer<>((EllipseThing) this, view, cm);
	}

	@Override
	protected void initProperties() {
		initProperty(org.archstudio.bna.facets.IHasAlpha.ALPHA_KEY, 1d);
		initProperty(org.archstudio.bna.facets.IHasBoundingBox.BOUNDING_BOX_KEY,
				new org.eclipse.swt.graphics.Rectangle(0, 0, 30, 20));
		addShapeModifyingKey(org.archstudio.bna.facets.IHasBoundingBox.BOUNDING_BOX_KEY);
		initProperty(org.archstudio.bna.facets.IHasColor.COLOR_KEY, new org.eclipse.swt.graphics.RGB(0, 0, 0));
		initProperty(org.archstudio.bna.facets.IHasCount.COUNT_KEY, 1);
		initProperty(org.archstudio.bna.facets.IHasEdgeColor.EDGE_COLOR_KEY, new org.eclipse.swt.graphics.RGB(0, 0, 0));
		initProperty(org.archstudio.bna.facets.IHasGlow.GLOW_ALPHA_KEY, 0.75d);
		initProperty(org.archstudio.bna.facets.IHasGlow.GLOW_COLOR_KEY, null);
		initProperty(org.archstudio.bna.facets.IHasGlow.GLOW_WIDTH_KEY, 20);
		initProperty(org.archstudio.bna.facets.IHasGradientFilled.GRADIENT_FILLED_KEY, true);
		initProperty(org.archstudio.bna.facets.IHasLineStyle.LINE_STYLE_KEY,
				org.archstudio.swtutils.constants.LineStyle.SOLID);
		initProperty(org.archstudio.bna.facets.IHasLineWidth.LINE_WIDTH_KEY, 1);
		initProperty(org.archstudio.bna.facets.IHasMinimumSize.MINIMUM_SIZE_KEY, new java.awt.Dimension(1, 1));
		initProperty(org.archstudio.bna.facets.IHasRotatingOffset.ROTATING_OFFSET_KEY, 0);
		initProperty(org.archstudio.bna.facets.IHasSecondaryColor.SECONDARY_COLOR_KEY,
				new org.eclipse.swt.graphics.RGB(192, 192, 192));
		initProperty(org.archstudio.bna.facets.IHasSelected.SELECTED_KEY, false);
		initProperty(org.archstudio.bna.facets.IHasRotatingOffset.TICKS_PER_INCREMENT_KEY, 10);
		super.initProperties();
	}

	@Override
	public double getAlpha() {
		return get(org.archstudio.bna.facets.IHasAlpha.ALPHA_KEY);
	}

	@Override
	public void setAlpha(double alpha) {
		set(org.archstudio.bna.facets.IHasAlpha.ALPHA_KEY, alpha);
	}

	@Override
	public org.eclipse.swt.graphics.Rectangle getBoundingBox() {
		return get(org.archstudio.bna.facets.IHasBoundingBox.BOUNDING_BOX_KEY);
	}

	@Override
	public void setBoundingBox(org.eclipse.swt.graphics.Rectangle boundingBox) {
		set(org.archstudio.bna.facets.IHasBoundingBox.BOUNDING_BOX_KEY, boundingBox);
	}

	@Override
	public @Nullable org.eclipse.swt.graphics.RGB getColor() {
		return get(org.archstudio.bna.facets.IHasColor.COLOR_KEY);
	}

	@Override
	public void setColor(@Nullable org.eclipse.swt.graphics.RGB color) {
		set(org.archstudio.bna.facets.IHasColor.COLOR_KEY, color);
	}

	@Override
	public int getCount() {
		return get(org.archstudio.bna.facets.IHasCount.COUNT_KEY);
	}

	@Override
	public void setCount(int count) {
		set(org.archstudio.bna.facets.IHasCount.COUNT_KEY, count);
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
	public boolean isGradientFilled() {
		return get(org.archstudio.bna.facets.IHasGradientFilled.GRADIENT_FILLED_KEY);
	}

	@Override
	public void setGradientFilled(boolean gradientFilled) {
		set(org.archstudio.bna.facets.IHasGradientFilled.GRADIENT_FILLED_KEY, gradientFilled);
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
	public java.awt.Dimension getMinimumSize() {
		return get(org.archstudio.bna.facets.IHasMinimumSize.MINIMUM_SIZE_KEY);
	}

	@Override
	public void setMinimumSize(java.awt.Dimension minimumSize) {
		set(org.archstudio.bna.facets.IHasMinimumSize.MINIMUM_SIZE_KEY, minimumSize);
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
	public @Nullable org.eclipse.swt.graphics.RGB getSecondaryColor() {
		return get(org.archstudio.bna.facets.IHasSecondaryColor.SECONDARY_COLOR_KEY);
	}

	@Override
	public void setSecondaryColor(@Nullable org.eclipse.swt.graphics.RGB secondaryColor) {
		set(org.archstudio.bna.facets.IHasSecondaryColor.SECONDARY_COLOR_KEY, secondaryColor);
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