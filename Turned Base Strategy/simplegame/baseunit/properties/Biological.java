package simplegame.baseunit.properties;


public class Biological extends Unit
{
	private int health, max_health;
	
	public Biological(Biological unit)
	{
		super(unit);
		this.health = unit.getHealth();
		this.max_health = unit.getMaxHealth();
	}
	
	public Biological(int health, int max_health, String name, String description, int x, int y, int height)
	{
		super(name, description, 0, 0, height);
		this.health = health;
		this.max_health = max_health;
	}
	
	public int getHealth()
    {
    	return this.health;
    }
    
    public void setHealth(int health)
    {
    	this.health = health;
    	if(this.health <= 0)
    	{
    		this.health = 0;
    		this.setPlayable(false);
    	}
    	if(this.health > 0)
    	{
    		this.setPlayable(true);
    	}
    	if(this.health > this.max_health)
    	{
    		this.health = this.max_health;
    	}
    	this.setUnitInfo("\n"+Integer.toString(this.health)+"/"+Integer.toString(this.max_health)+this.getUnitInfo());
    }
    
    public int getMaxHealth()
    {
    	return this.max_health;
    }
}
