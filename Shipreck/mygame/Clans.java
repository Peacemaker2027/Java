package mygame;

/**
 *
 * @author ebradford
 */
public abstract class Clans extends Stats
{
    private String description;
    private String name;
    public Clans(int damage, int speed, int armor, int hardpoints, int shield, int tracking, int tonnage, String name, String description)
    {
        this.damage(damage);
        this.speed(speed);
        this.armor(armor);
        this.hardpoints(hardpoints);
        this.shield(shield);
        this.tracking(tracking);
        this.tonnage(tonnage);
        this.name = name;
        this.description = description;
    }
    public String name()
    {
        return this.name;
    }
    public String description()
    {
        return description;
    }
}
