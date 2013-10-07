package simplegame.baseunit.mechanical;

import simplegame.baseunit.effects.Cost;
import simplegame.baseunit.effects.Damage;
import simplegame.baseunit.effects.Move;
import simplegame.baseunit.properties.Biological;
import simplegame.baseunit.properties.Mechanical;
import simplegame.utilities.WorldMap;

public class HeavyAttackVehicle extends Mechanical implements Damage, Move, Cost
{
	private final int class_constant = -128;
	
	public HeavyAttackVehicle()
	{
		super(128+32, 128+32, "Heavy Attack Vehicle", "Heavy attack vehicle to fortify the front.", 0, 0, 2);
	}
	
	@Override
	public int getCost()
	{
		return this.class_constant;
	}
	
	@Override
	public void setDamage(Mechanical unit)
	{
		if(this.isPlayable())
		{
			unit.setStructure(this.class_constant);
		}
	}
	
	@Override
	public void setDamage(Biological unit)
	{
		if(this.isPlayable())
		{
			unit.setHealth(this.class_constant-32);
		}
	}
	
	@Override
	public int getDamage()
	{
		return this.class_constant;
	}

	@Override
	public boolean moveTo(WorldMap map, int column, int row)
	{
		/*if((map.getPoint(column, row)>=0)&&(map.getPoint(column, row)<=2))
		{
			
		}*/
		return false;
	}
}