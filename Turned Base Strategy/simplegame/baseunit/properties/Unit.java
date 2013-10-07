/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package simplegame.baseunit.properties;

import simplegame.utilities.Consts.CELL;

/**
 *
 * @author ebradford
 */
public class Unit
{
    private boolean playable;
    private String name, unit_info;
    private int height;
    private int x;
    private int y;
    private CELL dir;
    
	public Unit(Unit unit)
	{
        this.playable = unit.playable;
        this.height = unit.height;
		this.x = unit.x;
		this.y = unit.y;
		this.name = unit.name;
		this.unit_info = unit.unit_info;
		this.dir = unit.dir;
	}
	
    public Unit(String name, String unit_info, int x, int y, int height)
    {
        this.playable = false;
        this.height = height;
		this.x = x;
		this.y = y;
		this.name = name;
		this.unit_info = unit_info;
		this.dir = CELL.ARG0;
    }
    
    public boolean isPlayable()
    {
        return this.playable;
    }
    
    public int getHeight()
    {
    	return this.height;
    }
    
    public void setPlayable(boolean playable)
    {
        this.playable = playable;
    }
    
	public String getName()
	{
		return this.name;
	}
	
	public void setUnitInfo(String unit_info)
	{
		this.unit_info = unit_info;
	}
	
	public String getUnitInfo()
	{
		return this.unit_info;
	}
    
	public int getX()
	{
		return this.x;
	}
	
	public int getY()
	{
		return this.y;
	}
	
	public CELL getDirection()
	{
		return this.dir;
	}
	
	public void setDirection(CELL dir)
	{
		this.dir = dir;
	}
	
	public void setPosition(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
}