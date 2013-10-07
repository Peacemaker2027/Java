package Game.Base;

/**
 *
 * @author ebradford
 */
public class Plugin
{
    public String kind = "";
    public int rate = 0;
    public int units = 0;
    public Plugin connection = null;
    public boolean accrue = false;
    public Plugin(String kind, int rate, int units, boolean accrue)//for IO only
    {
        this.kind = kind;
        this.rate = rate;
        this.units = units;
        this.accrue = accrue;
    }
    public String getIO()
    {
        if(rate < 0)
        {
            return "input";
        }
        if(rate > 0)
        {
            return "output";
        }
        return "neutral";//if the rate is 0
    }
}
