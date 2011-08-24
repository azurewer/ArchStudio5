package org.archstudio.bna.things.utility;

import org.archstudio.bna.IBNAWorld;
import org.archstudio.bna.facets.IHasMutableWorld;
import org.archstudio.bna.things.AbstractRectangleThing;

public class WorldThing extends AbstractRectangleThing implements IHasMutableWorld {

	public WorldThing(Object id) {
		super(id);
	}

	public IBNAWorld getWorld() {
		return get(WORLD_KEY);
	}

	public void setWorld(IBNAWorld world) {
		set(WORLD_KEY, world);
	}

	public void clearWorld() {
		remove(WORLD_KEY);
	}
}
