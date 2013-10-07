package Game;

import Game.Gui.*;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.light.AmbientLight;
import com.jme3.light.PointLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.shadow.BasicShadowRenderer;
import com.jme3.util.SkyFactory;

/**
 *
 * @author ebradford
 */
public class Driver extends SimpleApplication
{
    PlayerGui overlay;
    BasicShadowRenderer bsr;
    public static void main(String[] args)
    {
        Driver app = new Driver();
        app.start();
    }

    @Override
    public void simpleInitApp()
    {
        try
        {
            this.setDisplayFps(false);
            this.setDisplayStatView(false);
            Crosshairs.init(this, this.settings.getWidth(), this.settings.getHeight());
            overlay = new PlayerGui(this);
            PointLight light = new PointLight();
            light.setRadius(1000);
            light.setColor(ColorRGBA.White.mult(1.0f));
            rootNode.addLight(light);
            AmbientLight al = new AmbientLight();
            al.setColor(ColorRGBA.White.mult(0.1f));
            rootNode.addLight(al);
            rootNode.attachChild(SkyFactory.createSky(assetManager, "Textures/Green_Nebula.dds", false));//"Textures/Sky/Bright/BrightSky.dds"
            BulletAppState bulletAppState = new BulletAppState();
            stateManager.attach(bulletAppState);
            //bulletAppState.getPhysicsSpace().enableDebug(assetManager);
            bulletAppState.getPhysicsSpace().setGravity(Vector3f.ZERO);
            
            /*storagecontainer.addPlugin(new Plugin("ore solid", 0, 100000, true));
            
            breederreactor.connect(reactor.getPlugin("electric plasma"));
            breederreactor.connect(storagecontainer.getPlugin("ore solid"));
            
            reactor.connect(breederreactor.getPlugin("fuel solid"));
            reactor.connect(coolanttower.getPlugin("coolant liquid"));
            
            coolanttower.connect(reactor.getPlugin("electric plasma"));*/
        }
        catch(Exception ex)
        {
            System.out.println("AN ERROR OCCURED:" + ex.toString());
        }
    }
}
