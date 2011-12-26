package org.archstudio.bna.things;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import java.util.List;

import org.archstudio.bna.IThing;
import org.archstudio.bna.IThingListener;
import org.archstudio.bna.ThingEvent;
import org.archstudio.bna.facets.IHasEndpoints;
import org.archstudio.bna.facets.IHasMidpoints;
import org.archstudio.bna.facets.IHasMutableEndpoints;
import org.archstudio.bna.facets.IHasMutableMidpoints;
import org.archstudio.bna.facets.IHasPoints;
import org.eclipse.swt.graphics.Point;

import com.google.common.collect.Lists;

public class AbstractSplineThing extends AbstractPointsThing implements IHasMutableEndpoints, IHasMutableMidpoints {

	public AbstractSplineThing(Object id) {
		super(id);
		addThingListener(new IThingListener() {
			@Override
			public <ET extends IThing, EK extends IThingKey<EV>, EV> void thingChanged(ThingEvent<ET, EK, EV> thingEvent) {
				if (!IHasPoints.POINTS_KEY.equals(thingEvent.getPropertyName())) {
					set(IHasPoints.POINTS_KEY, getPoints());
				}
			}
		});
	}

	@Override
	protected void initProperties() {
		super.initProperties();
		setEndpoint1(new Point(0, 0));
		setEndpoint2(new Point(0, 0));
	}

	@Override
	public Point getEndpoint1() {
		return get(IHasEndpoints.ENDPOINT_1_KEY);
	}

	@Override
	public void setEndpoint1(Point endpoint1) {
		checkNotNull(endpoint1);

		set(IHasEndpoints.ENDPOINT_1_KEY, endpoint1);
	}

	@Override
	public Point getEndpoint2() {
		return get(IHasEndpoints.ENDPOINT_2_KEY);
	}

	@Override
	public void setEndpoint2(Point endpoint2) {
		checkNotNull(endpoint2);

		set(IHasEndpoints.ENDPOINT_2_KEY, endpoint2);
	}

	@Override
	public List<Point> getMidpoints() {
		return get(IHasMidpoints.MIDPOINTS_KEY);
	}

	@Override
	public void setMidpoints(List<Point> midpoints) {
		checkNotNull(midpoints);

		set(IHasMidpoints.MIDPOINTS_KEY, midpoints);
	}

	@Override
	public List<Point> getPoints() {
		List<Point> points = Lists.newArrayList();
		points.add(checkNotNull(getEndpoint1()));
		points.addAll(getMidpoints());
		points.add(checkNotNull(getEndpoint2()));
		return points;
	}

	@Override
	public void setPoints(final List<Point> points) {
		checkArgument(points.size() >= 2, "%s requires at least two points", this.getClass());

		synchronizedUpdate(new Runnable() {
			@Override
			public void run() {
				setEndpoint1(points.get(0));
				setMidpoints(points.subList(1, points.size() - 1));
				setEndpoint2(points.get(points.size() - 1));
			}
		});
	}
}
