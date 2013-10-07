package starships;

import com.jme3.app.SimpleApplication;
import mygame.Clans;
import mygame.Starship;

/**
 *
 * @author ebradford
 */
public class Capital_Battleship extends Starship
{
    public Capital_Battleship(SimpleApplication app, Clans clan)
    {
        super(app,clan,16,3,10,16,10,5,9072000);
    }
    @Override
    public Object special(Object o)
    {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
