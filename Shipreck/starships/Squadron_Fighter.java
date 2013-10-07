package starships;

import com.jme3.app.SimpleApplication;
import mygame.Clans;
import mygame.Starship;

/**
 *
 * @author ebradford
 */
public class Squadron_Fighter extends Starship
{
    public Squadron_Fighter(SimpleApplication app, Clans clan)
    {
        super(app,clan,4,3,2,2,2,2,21);
    }
    @Override
    public Object special(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
