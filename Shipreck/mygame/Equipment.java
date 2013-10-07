package mygame;

/**
 *
 * @author ebradford
 */
public abstract class Equipment extends Stats
{
    public Equipment(int damage, int speed, int armor, int hardpoints, int shield, int tracking, long tonnage)
    {
        this.damage(damage);
        this.speed(speed);
        this.armor(armor);
        this.hardpoints(hardpoints);
        this.shield(shield);
        this.tracking(tracking);
        this.tonnage(tonnage);
    }
    public abstract Object activate(Object o);
}
