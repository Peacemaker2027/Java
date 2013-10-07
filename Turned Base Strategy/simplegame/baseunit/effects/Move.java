package simplegame.baseunit.effects;

import simplegame.utilities.WorldMap;

public interface Move
{
	public boolean moveTo(WorldMap map, int column, int row);
}
