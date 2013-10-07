package simplegame.baseunit.biological;

import simplegame.baseunit.effects.*;
import simplegame.baseunit.properties.Biological;

public class FieldMedic extends Biological implements Heal, Cost
{
	private final int class_constant = 32;
	
	public FieldMedic()
	{
		super(32, 32, "Field Medic", "Cures 32", 0, 0, 1);
	}

	@Override
	public int getCost()
	{
		return -1*this.class_constant;
	}

	@Override
	public void heal(Biological unit)
	{
		unit.setHealth(this.class_constant);
	}
}
