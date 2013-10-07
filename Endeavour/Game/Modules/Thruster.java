package Game.Modules;

import Game.Base.Module;
import Game.Base.PhysicsTool;
import Game.Base.Plugin;
import com.bulletphysics.collision.broadphase.Dbvt.Node;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.util.TangentBinormalGenerator;

/**
 *
 * @author ebradford
 */
public class Thruster extends Module
{
    public Thruster(int mass, SimpleApplication app)
    {
        this.mass = mass;
        float scale = (float) Math.pow((this.mass/7850f), 1f/3f);
        Box boxshape = new Box(scale, scale, scale);
        
        /*this.spatial.addControl(PhysicsTool.physicsBox(.9f, 100));
        this.spatial.getControl(RigidBodyControl.class).setPhysicsLocation(new Vector3f(0f,10,0f));
        //mainNode.attachChild(node1);
        app.getStateManager().getState(BulletAppState.class).getPhysicsSpace().add(this.spatial);*/
        
        this.spatial = new Geometry("shape", boxshape);
        TangentBinormalGenerator.generate(this.spatial);
        Material mat = new Material(app.getAssetManager(), "Common/MatDefs/Light/Lighting.j3md");
        mat.setBoolean("UseMaterialColors", true);
        mat.setColor("Ambient",  ColorRGBA.Orange);
        mat.setColor("Diffuse",  ColorRGBA.Orange);
        mat.setColor("Specular", ColorRGBA.White);
        mat.setFloat("Shininess", 12);
        this.spatial.setMaterial(mat);
        this.addPlugin(new Plugin("electric plasma", (int)(-this.mass*0.1), 0, false));
        this.addPlugin(new Plugin("electric plasma", (int)(this.mass*0.1), 0, true));
        this.status(STATE.START);
        this.spatial.addControl(this);
        app.getRootNode().attachChild(this.spatial);
    }
}
