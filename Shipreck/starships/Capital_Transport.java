package starships;

import com.jme3.app.SimpleApplication;
import mygame.Clans;
import mygame.Starship;

/**
 *
 * @author ebradford
 */
public class Capital_Transport extends Starship
{
    public Capital_Transport(SimpleApplication app, Clans clan)
    {
        super(app,clan,1,8,20,6,20,5,9072000);
    }
    @Override
    public Object special(Object o)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
