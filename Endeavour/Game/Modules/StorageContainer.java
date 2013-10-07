package Game.Modules;

import Game.Base.Module;
import Game.Base.PhysicsTool;
import Game.Base.Plugin;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;

/**
 *
 * @author ebradford
 */
public class StorageContainer extends Module
{
    public StorageContainer(int mass, SimpleApplication app)
    {
        this.mass = mass;
        float scale = (float) Math.pow((this.mass/7850f), 1f/3f);
        this.spatial = app.getAssetManager().loadModel("Models/cargocontainers.j3o");
        this.spatial.scale(scale*0.025f);
        Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        mat.setBoolean("UseMaterialColors", true);
        mat.setTexture("DiffuseMap", app.getAssetManager().loadTexture("Textures/Steel_Texture.jpg"));
        mat.setColor("Ambient",  ColorRGBA.White);
        mat.setColor("Diffuse",  ColorRGBA.White);
        mat.setColor("Specular", ColorRGBA.White);
        mat.setFloat("Shininess", 12);
        this.spatial.setMaterial(mat);
        
        this.node = new Node("node");
        this.spatial.addControl(this);
        this.spatial.addControl(PhysicsTool.physicsBox(this.spatial, this.mass));
        app.getStateManager().getState(BulletAppState.class).getPhysicsSpace().add(this.spatial);
        this.node.addControl(this.spatial.getControl(RigidBodyControl.class));
        this.node.attachChild(this.spatial);
        app.getRootNode().attachChild(this.node);
    }
    @Override
    public void addPlugin(Plugin plugin)
    {
        if(plugin.kind.contains("solid"))
        {
            this.plugins.put(plugin.kind, new Plugin(plugin.kind, 0, plugin.units, true));
        }
    }
}
