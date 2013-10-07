package simplegame.baseunit.effects;

import simplegame.baseunit.properties.Biological;
import simplegame.baseunit.properties.Mechanical;

public interface Damage
{
	public abstract void setDamage(Biological unit);
	public abstract void setDamage(Mechanical unit);
	public abstract int getDamage();
}
