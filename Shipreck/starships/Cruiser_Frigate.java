package starships;

import com.jme3.app.SimpleApplication;
import mygame.Clans;
import mygame.Starship;

/**
 *
 * @author ebradford
 */
public class Cruiser_Frigate extends Starship
{
    public Cruiser_Frigate(SimpleApplication app, Clans clan)
    {
        super(app,clan,5,3,5,7,3,7,3024);
    }
    @Override
    public Object special(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
