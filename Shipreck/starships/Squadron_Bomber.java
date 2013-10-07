package starships;

import com.jme3.app.SimpleApplication;
import mygame.Clans;
import mygame.Starship;

/**
 *
 * @author ebradford
 */
public class Squadron_Bomber extends Starship
{
    public Squadron_Bomber(SimpleApplication app, Clans clan)
    {
        super(app,clan,2,2,2,3,2,4,21);
    }
    @Override
    public Object special(Object o) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
