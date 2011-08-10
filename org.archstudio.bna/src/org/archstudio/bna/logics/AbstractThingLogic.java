package org.archstudio.bna.logics;

import static com.google.common.base.Preconditions.checkNotNull;

import org.archstudio.bna.IBNAModel;
import org.archstudio.bna.IBNAWorld;
import org.archstudio.bna.IThingLogic;
import org.archstudio.sysutils.SystemUtils;

public abstract class AbstractThingLogic implements IThingLogic {

	private IBNAWorld bnaWorld = null;

	@Override
	public IBNAWorld getBNAWorld() {
		return bnaWorld;
	}

	@Override
	public void setBNAWorld(IBNAWorld newBNAWorld) {
		if (!SystemUtils.nullEquals(this.bnaWorld, newBNAWorld)) {
			if (this.bnaWorld != null) {
				destroy();
			}
			this.bnaWorld = newBNAWorld;
			if (newBNAWorld != null) {
				init();
			}
		}
	}

	protected IBNAModel getBNAModel() {
		IBNAWorld bnaWorld = checkNotNull(getBNAWorld());
		return bnaWorld.getBNAModel();
	}

	protected <L extends IThingLogic> L addThingLogic(Class<L> logicClass) {
		IBNAWorld bnaWorld = checkNotNull(getBNAWorld());
		return bnaWorld.getThingLogicManager().addThingLogic(logicClass);
	}

	protected void init() {
	}

	protected void destroy() {
	}
}
