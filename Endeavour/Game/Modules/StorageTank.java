package Game.Modules;

import Game.Base.Module;
import Game.Base.Plugin;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.util.TangentBinormalGenerator;
import com.jme3.scene.shape.Box;

/**
 *
 * @author ebradford
 */
public class StorageTank extends Module
{
    public StorageTank(int mass, SimpleApplication app)
    {
        this.mass = mass;
        float scale = (float) Math.pow((this.mass/7850f), 1f/3f);
        Box boxshape = new Box(scale, scale, scale);
        this.spatial = new Geometry("shape", boxshape);
        TangentBinormalGenerator.generate(this.spatial);
        Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        mat.setBoolean("UseMaterialColors", true);
        mat.setColor("Ambient",  ColorRGBA.Orange);
        mat.setColor("Diffuse",  ColorRGBA.Orange);
        mat.setColor("Specular", ColorRGBA.White);
        mat.setFloat("Shininess", 12);
        this.spatial.setMaterial(mat);
        this.spatial.addControl(this);
        app.getRootNode().attachChild(this.spatial);
    }
    @Override
    public void addPlugin(Plugin plugin)
    {
        if((plugin.kind.contains("liquid"))||(plugin.kind.contains("gas")))
        {
            this.plugins.put(plugin.kind, new Plugin(plugin.kind, 0, plugin.units, true));
        }
    }
}
