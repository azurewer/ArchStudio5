package org.archstudio.bna.logics.editing;

import javax.annotation.Nullable;

import org.archstudio.bna.constants.StickyMode;
import org.archstudio.bna.facets.IHasPoints;
import org.archstudio.bna.facets.IIsSticky;

public interface IReshapeSplineGuide {

	public boolean shouldBeStuck(IHasPoints pointsThing, int index);

	public @Nullable
	StickyMode getStickyMode(IHasPoints pointsThing, IIsSticky stickyThing, int pointIndex);

}
