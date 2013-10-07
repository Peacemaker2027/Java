package mygame;

import clans.*;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.material.Material;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import java.util.ArrayList;


/**
 *
 * @author ebradford
 */
public abstract class Starship extends Stats
{
    private ArrayList<Equipment> mounts;
    private Clans clan;
    private int basePoints;
    private String modelPath;
    private Spatial myShip;
    public abstract Object special(Object o);
    public Starship(SimpleApplication app, Clans clan, int damage, int speed, int armor, int hardpoints, int shield, int tracking, long tonnage)
    {
        //Load and set variables block
        mounts = new ArrayList<Equipment>();
        this.clan = clan;
        this.damage(damage+this.clan.damage());
        this.speed(speed+this.clan.speed());
        this.armor(armor+this.clan.armor());
        this.hardpoints(hardpoints+this.clan.hardpoints());
        this.shield(shield+this.clan.shield());
        this.tracking(tracking+this.clan.tracking());
        
        float tonnes = Math.round((float)tonnage*(((float)this.clan.tonnage())/100.0f));
        
        this.tonnage((long)tonnes);
        this.basePoints = damage+speed+armor+hardpoints+shield+tracking;
        
        //Load model block
        this.modelPath = this.getClass().getName().substring(this.getClass().getName().lastIndexOf("_")+1);
        this.modelPath = "Models/"+this.modelPath+"s/"+clan.name()+"_"+this.modelPath+".j3o";
        this.myShip = app.getAssetManager().loadModel(this.modelPath);
        Material mat_default = new Material(app.getAssetManager(), "Common/MatDefs/Misc/ShowNormals.j3md");
        this.myShip.setMaterial(mat_default);
        app.getRootNode().attachChild(this.myShip);
        
        //Load physics block
        CollisionShape shipShape = CollisionShapeFactory.createDynamicMeshShape(this.myShip);
        RigidBodyControl rbc = new RigidBodyControl(shipShape,(float)this.tonnage());
        this.myShip.addControl(rbc);
        app.getStateManager().getState(BulletAppState.class).getPhysicsSpace().add(rbc);
        
    }
    public Clans clan()
    {
        return this.clan;
    }
    public String modelPath()
    {
        return this.modelPath;
    }
    public boolean mount(Equipment e)
    {
        if(this.hardpoints()+e.hardpoints()<0)
        {
            return false;
        }
        
        this.damage(this.damage()+e.damage());
        this.speed(this.speed()+e.speed());
        this.armor(this.armor()+e.armor());
        this.hardpoints(this.hardpoints()+e.hardpoints());
        this.shield(this.shield()+e.shield());
        this.tracking(this.tracking()+e.tracking());
        this.tonnage(this.tonnage()+e.tonnage());
        
        this.myShip.getControl(RigidBodyControl.class).setMass(this.tonnage());
        
        return this.mounts.add(e);
    }
    public Equipment unmount(Equipment e)
    {
        int found = this.mounts.indexOf(e);
        
        if(found<0)
        {
            return null;
        }
        
        this.damage(this.damage()-this.mounts.get(found).damage());
        this.speed(this.speed()-this.mounts.get(found).speed());
        this.armor(this.armor()-this.mounts.get(found).armor());
        this.hardpoints(this.hardpoints()-this.mounts.get(found).hardpoints());
        this.shield(this.shield()-this.mounts.get(found).shield());
        this.tracking(this.tracking()-this.mounts.get(found).tracking());
        this.tonnage(this.tonnage()-e.tonnage());
        
        this.myShip.getControl(RigidBodyControl.class).setMass(this.tonnage());
        
        return this.mounts.remove(found);
    }
    public int basePoints()
    {
        return this.basePoints;
    }
}
