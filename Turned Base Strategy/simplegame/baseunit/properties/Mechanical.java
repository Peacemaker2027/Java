package simplegame.baseunit.properties;

import simplegame.utilities.Consts;
import simplegame.utilities.WorldMap;

public class Mechanical extends Unit
{
	private int structure, max_structure;
	
	public Mechanical(Mechanical unit)
	{
		super(unit);
		this.structure = unit.getStructure();
		this.max_structure = unit.getMaxStructure();
	}
	
	public Mechanical(int structure, int max_structure, String name, String description, int x, int y, int height)
	{
		super(name, description, 0, 0, height);
		this.structure = structure;
		this.max_structure = max_structure;
	}
	
	public int getStructure()
    {
    	return this.structure;
    }
    
    public void setStructure(int structure)
    {
    	this.structure = structure;
    	if(this.structure <= 0)
    	{
    		this.structure = 0;
    		this.setPlayable(false);
    	}
    	if(this.structure > 0)
    	{
    		this.setPlayable(true);
    	}
    	if(this.structure > this.max_structure)
    	{
    		this.structure = this.max_structure;
    	}
    	this.setUnitInfo("\n"+Integer.toString(this.structure)+"/"+Integer.toString(this.max_structure)+this.getUnitInfo());
    }
    
    public int getMaxStructure()
    {
    	return this.max_structure;
    }
    
    public boolean move(WorldMap world_map, Consts.CELL direction)
    {
    	return false;
    }
}
