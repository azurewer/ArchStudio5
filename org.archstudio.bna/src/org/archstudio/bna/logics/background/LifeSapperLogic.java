package org.archstudio.bna.logics.background;

import org.archstudio.bna.IBNAModel;
import org.archstudio.bna.IThing;
import org.archstudio.bna.facets.IHasMutableLife;
import org.archstudio.bna.logics.AbstractThingLogic;
import org.archstudio.bna.logics.tracking.ThingTypeTrackingLogic;

public class LifeSapperLogic extends AbstractThingLogic {

	protected ThingTypeTrackingLogic tttl;
	protected LifeSapper sapper = null;

	public LifeSapperLogic() {
	}

	@Override
	protected void init() {
		super.init();
		tttl = getBNAWorld().getThingLogicManager().addThingLogic(ThingTypeTrackingLogic.class);
		sapper = new LifeSapper();
		sapper.setName("LifeSapper");
		sapper.setDaemon(true);
		sapper.start();
	}

	@Override
	protected void destroy() {
		if (sapper != null) {
			sapper.terminate();
			sapper = null;
		}
		super.destroy();
	}

	public class LifeSapper extends Thread {

		protected volatile boolean shouldTerminate = false;

		protected LifeSapper() {
		}

		public synchronized void terminate() {
			shouldTerminate = true;
		}

		@Override
		public void run() {
			while (!shouldTerminate) {
				try {
					Thread.sleep(250);
				}
				catch (InterruptedException e) {
				}

				final IBNAModel model = getBNAModel();
				if (model != null) {
					model.beginBulkChange();
					try {
						for (final IThing t : model.getThings(tttl.getThingIDs(IHasMutableLife.class))) {
							if (t instanceof IHasMutableLife) {
								final IHasMutableLife tl = (IHasMutableLife) t;
								tl.synchronizedUpdate(new Runnable() {
									@Override
									public void run() {
										int life;
										tl.setLife(life = tl.getLife() - 1);
										if (life < 0) {
											model.removeThing(tl);
										}
									}
								});
							}
						}
					}
					finally {
						model.endBulkChange();
					}
				}
			}
		}
	}
}
