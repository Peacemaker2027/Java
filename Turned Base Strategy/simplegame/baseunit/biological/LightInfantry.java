package simplegame.baseunit.biological;

import simplegame.baseunit.effects.Cost;
import simplegame.baseunit.effects.Damage;
import simplegame.baseunit.properties.Biological;
import simplegame.baseunit.properties.Mechanical;

public class LightInfantry extends Biological implements Damage, Cost
{
	private final int class_constant = -8;
	
	public LightInfantry()
	{
		super(8, 8, "Light Infantry", "Common infantry used\n on the front lines.", 0, 0, 1);
	}
	
	@Override
	public int getCost()
	{
		return this.class_constant;
	}
	
	@Override
	public void setDamage(Biological unit)
	{
		if(this.isPlayable())
		{
			unit.setHealth(this.class_constant);
		}
	}

	@Override
	public void setDamage(Mechanical unit)
	{
		unit.setStructure(this.class_constant+2);
	}
	
	@Override
	public int getDamage()
	{
		return this.class_constant;
	}
}
