package simplegame.baseunit.mechanical;

import java.util.ArrayList;

import simplegame.baseunit.effects.Cargo;
import simplegame.baseunit.effects.Cost;
import simplegame.baseunit.effects.Heal;
import simplegame.baseunit.effects.Move;
import simplegame.baseunit.properties.Biological;
import simplegame.baseunit.properties.Mechanical;
import simplegame.utilities.WorldMap;

public class InfantryTransport extends Mechanical implements Heal, Cargo<Biological>, Move, Cost
{
	private final int class_constant = -64;
	private ArrayList<Biological> cargoHold;
	
	public InfantryTransport()
	{
		super(64+16, 64+16, "Infantry Transport", "Brings troops to the front lines.", 0, 0, 2);
		this.cargoHold = new ArrayList<Biological>(8);
	}
	
	@Override
	public int getCost()
	{
		return this.class_constant;
	}
	
	@Override
	public void heal(Biological unit)
	{
		unit.setHealth(8);
	}

	@Override
	public boolean addCargo(Biological unit)
	{
		this.heal(unit);
		return this.cargoHold.add(unit);
	}

	@Override
	public ArrayList<Biological> unloadCargo()
	{
		ArrayList<Biological> newDeck = new ArrayList<Biological>(8);
		while(this.cargoHold.size()>0)
		{
			newDeck.add(this.cargoHold.remove(0));
		}
		return newDeck;
	}

	@Override
	public int getCapacity()
	{
		return 8;
	}

	@Override
	public int getCount()
	{
		return this.cargoHold.size();
	}

	@Override
	public boolean moveTo(WorldMap map, int column, int row) {
		// TODO Auto-generated method stub
		return false;
	}
}