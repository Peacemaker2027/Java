package starships;

import com.jme3.app.SimpleApplication;
import mygame.Clans;
import mygame.Starship;

/**
 *
 * @author ebradford
 */
public class Cruiser_Destroyer extends Starship
{
    public Cruiser_Destroyer(SimpleApplication app, Clans clan)
    {
        super(app,clan,7,3,6,7,3,4,3024);
    }
    @Override
    public Object special(Object o)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
