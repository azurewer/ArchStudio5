package org.archstudio.bna.facets;

import org.eclipse.jdt.annotation.NonNullByDefault;
import org.eclipse.jdt.annotation.Nullable;

/*
 * DO NOT EDIT THIS FILE, it is automatically generated. ANY MODIFICATIONS WILL BE OVERWRITTEN.
 *
 * To modify, update the thingdefinition extension at
 * org.archstudio.bna/Package[name=org.archstudio.bna.facets]/Facet[name=Arrowhead].
 */

@SuppressWarnings("all")
@NonNullByDefault
public interface IHasMutableArrowhead extends IHasArrowhead {
	public void setArrowheadColor(@Nullable org.eclipse.swt.graphics.RGB arrowheadColor);

	public void setArrowheadEdgeColor(@Nullable org.eclipse.swt.graphics.RGB arrowheadEdgeColor);

	public void setArrowheadLength(int arrowheadLength);

	public void setArrowheadShape(org.archstudio.bna.constants.ArrowheadShape arrowheadShape);

	public void setArrowheadWidth(int arrowheadWidth);
}