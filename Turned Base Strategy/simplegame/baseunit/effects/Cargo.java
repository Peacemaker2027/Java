package simplegame.baseunit.effects;

import java.util.ArrayList;

public interface Cargo<T>
{
	public abstract boolean addCargo(T unit);
	public abstract ArrayList<T> unloadCargo();
	public abstract int getCapacity();
	public abstract int getCount();
}
