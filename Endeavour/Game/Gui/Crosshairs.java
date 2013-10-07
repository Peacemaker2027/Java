package Game.Gui;

import com.jme3.app.SimpleApplication;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;

/**
 *
 * @author ebradford
 */


public class Crosshairs
{
    static public void init(SimpleApplication app, int width, int height)
    {
        app.getGuiNode().detachAllChildren();
        BitmapFont fnt = app.getAssetManager().loadFont("Interface/Fonts/Default.fnt");
        BitmapText ch = new BitmapText(fnt, false);
        ch.setSize(fnt.getCharSet().getRenderedSize() * 2);
        ch.setText("+"); // crosshairs

        ch.setLocalTranslation( // center
        width / 2 - ch.getFont().getCharSet().getRenderedSize() / 3 * 2,
        height / 2 + ch.getLineHeight() / 2, 0);
        app.getGuiNode().attachChild(ch);
    }
}
