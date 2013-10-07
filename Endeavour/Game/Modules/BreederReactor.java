package Game.Modules;

import Game.Base.Module;
import Game.Base.Plugin;
import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.util.TangentBinormalGenerator;

/**
 *
 * @author ebradford
 */
public class BreederReactor extends Module
{
    public BreederReactor(int mass, SimpleApplication app)
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
        this.addPlugin(new Plugin("ore solid", -this.mass, 0, false));
        this.addPlugin(new Plugin("electric plasma", -this.mass/4, 0, false));
        this.addPlugin(new Plugin("fuel solid", this.mass, 0, true));
        this.status(STATE.START);
        this.spatial.addControl(this);
        app.getRootNode().attachChild(this.spatial);
    }
}
