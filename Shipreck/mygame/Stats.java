package mygame;

/**
 *
 * @author ebradford
 */
public class Stats
{
    private int damage, speed, hardness, hardpoints, shield, tracking;
    private long tonnage;
    public int damage(int damage) 
    {
        return this.damage = damage;
    }
    public int damage() 
    {
        return this.damage;
    }

    public int speed(int speed) 
    {
        return this.speed = speed;
    }
    public int speed() 
    {
        return this.speed;
    }

    public int armor(int armor) 
    {
        return this.hardness = armor;
    }
    public int armor() 
    {
        return this.hardness;
    }

    public int hardpoints(int hardpoints) 
    {
        return this.hardpoints = hardpoints;
    }
    public int hardpoints() 
    {
        return this.hardpoints;
    }

    public int shield(int shield) 
    {
        return this.shield = shield;
    }
    public int shield() 
    {
        return this.shield;
    }

    public int tracking(int tracking) 
    {
        return this.tracking = tracking;
    }
    public int tracking() 
    {
        return this.tracking;
    }
    
    public long tonnage(long tonnage)
    {
        return this.tonnage = tonnage;
    }
    public long tonnage()
    {
        return this.tonnage;
    }
}
