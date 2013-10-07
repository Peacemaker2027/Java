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
public class Reactor extends Module
{
    public Reactor(int mass, SimpleApplication app)
    {
        this.mass = mass;
        float scale = (float) Math.pow((this.mass/7850f), 1f/3f);
        this.spatial = app.getAssetManager().loadModel("Models/reactormodule.j3o");
        this.spatial.scale(scale*0.025f);
        Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        mat.setBoolean("UseMaterialColors", true);
        mat.setTexture("DiffuseMap", app.getAssetManager().loadTexture("Textures/Steel_Texture.jpg"));
        mat.setColor("Ambient",  ColorRGBA.White);
        mat.setColor("Diffuse",  ColorRGBA.White);
        mat.setColor("Specular", ColorRGBA.White);
        mat.setFloat("Shininess", 12);
        this.spatial.setMaterial(mat);
        this.addPlugin(new Plugin("coolant liquid", -this.mass/2, 0, false));
        this.addPlugin(new Plugin("fuel solid", -this.mass/2, 0, false));
        this.addPlugin(new Plugin("electric plasma", this.mass*2, 0, false));
        this.status(STATE.START);
        
        this.node = new Node("node");
        this.spatial.addControl(this);
        this.spatial.addControl(PhysicsTool.physicsBox(this.spatial, this.mass));
        app.getStateManager().getState(BulletAppState.class).getPhysicsSpace().add(this.spatial);
        this.node.addControl(this.spatial.getControl(RigidBodyControl.class));
        this.node.attachChild(this.spatial);
        app.getRootNode().attachChild(this.node);
    }
}
