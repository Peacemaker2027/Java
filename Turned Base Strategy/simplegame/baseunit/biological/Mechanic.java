package simplegame.baseunit.biological;

import simplegame.baseunit.effects.Cost;
import simplegame.baseunit.effects.Repair;
import simplegame.baseunit.properties.Biological;
import simplegame.baseunit.properties.Mechanical;

public class Mechanic extends Biological implements Repair, Cost
{
	private final int class_constant = 64;
	public Mechanic()
	{
		super(32, 32, "Mechanic", "Can repair mechanical things", 0, 0, 1);
		// TODO Auto-generated constructor stub
	}
	@Override
	public int getCost()
	{
		// TODO Auto-generated method stub
		return -1*this.class_constant;
	}
	
	@Override
	public void repair(Mechanical unit)
	{
		unit.setStructure(this.class_constant);
	}
}
