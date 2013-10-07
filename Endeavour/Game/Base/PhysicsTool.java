package Game.Base;

import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.joints.PhysicsJoint;
import com.jme3.bullet.joints.SixDofJoint;
import com.jme3.bullet.util.CollisionShapeFactory;
import com.jme3.material.Material;
import com.jme3.math.LineSegment;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import java.util.List;

/**
 *
 * @author ebradford
 */
public class PhysicsTool
{
    public static RigidBodyControl physicsBox(Spatial spatial, float mass)
    {
        RigidBodyControl control = new RigidBodyControl(CollisionShapeFactory.createDynamicMeshShape(spatial), mass);
        control.setAngularDamping(0.99f);
        control.setAngularSleepingThreshold(0.5f);
        return control;
    }
    public static RigidBodyControl physicsBox(Vector3f bounds, float mass)
    {
        RigidBodyControl control = new RigidBodyControl(new BoxCollisionShape(bounds), mass);
        control.setAngularDamping(0.99f);
        control.setAngularSleepingThreshold(0.5f);
        return control;
    }
    public static RigidBodyControl physicsBox(float length, float mass)
    {
        RigidBodyControl control = new RigidBodyControl(new BoxCollisionShape(new Vector3f(length,length,length)), mass);
        control.setAngularDamping(0.99f);
        control.setAngularSleepingThreshold(0.5f);
        return control;
    }
    public static void createRigidJoints(SimpleApplication app, Material mat, Node pivot, Node order[])
    {
        for(int i = 0; i < order.length; i++)
        {
            createRigidJoint(app, mat, pivot, order[i]);
        }
    }
    public static void createRigidJoint(SimpleApplication app, Material mat, Node from, Node to)
    {
        LineSegment seg = new LineSegment(from.getControl(RigidBodyControl.class).getPhysicsLocation(), to.getControl(RigidBodyControl.class).getPhysicsLocation());
        Vector3f dir = seg.getDirection();
        float dist = from.getControl(RigidBodyControl.class).getPhysicsLocation().distance(to.getControl(RigidBodyControl.class).getPhysicsLocation());
        Vector3f newvector = dir.mult(dist);
        SixDofJoint joint = new SixDofJoint(from.getControl(RigidBodyControl.class), to.getControl(RigidBodyControl.class), newvector, Vector3f.ZERO, false);
        app.getStateManager().getState(BulletAppState.class).getPhysicsSpace().add(joint);
        
        seg = new LineSegment(to.getControl(RigidBodyControl.class).getPhysicsLocation(), from.getControl(RigidBodyControl.class).getPhysicsLocation());
        dir = seg.getDirection();
        dist = to.getControl(RigidBodyControl.class).getPhysicsLocation().distance(from.getControl(RigidBodyControl.class).getPhysicsLocation());
        newvector = dir.mult(dist);
        
        joint = new SixDofJoint(to.getControl(RigidBodyControl.class), from.getControl(RigidBodyControl.class), newvector, Vector3f.ZERO, false);
        app.getStateManager().getState(BulletAppState.class).getPhysicsSpace().add(joint);
        
        Vector3f pointA = new Vector3f(from.getControl(RigidBodyControl.class).getPhysicsLocation());
        Vector3f pointB = new Vector3f(to.getControl(RigidBodyControl.class).getPhysicsLocation());
        float distance = pointA.distance(pointB);
        seg = new LineSegment(pointA,pointB);
        Vector3f center = seg.getDirection().mult(distance/2);
        Box boxshape = new Box(0.1f, distance/2, 0.1f);
        Geometry geo = new Geometry("Beam", boxshape);
        geo.setMaterial(mat);
        Node beam = new Node("Beam"+to.hashCode());
        beam.setLocalTranslation(center);
        beam.attachChild(geo);
        beam.rotateUpTo(seg.getDirection());
        from.attachChild(beam);
    }
    public static void destroyRigidJoints(SimpleApplication app, Node pivot, Node order[])
    {
        for(int i = 0; i < order.length; i++)
        {
            destroyRigidJoint(app, pivot, order[i]);
        }
    }
    public static void destroyRigidJoint(SimpleApplication app, Node from, Node to)//destroy single node joint
    {
        List<PhysicsJoint> joints = from.getControl(RigidBodyControl.class).getJoints();
        SixDofJoint joint = null;
        for(int i = 0; i < joints.size(); i++)
        {
            if((joints.get(i).getBodyA().equals(to.getControl(RigidBodyControl.class)))&&(joints.get(i).getBodyB().equals(from.getControl(RigidBodyControl.class))))
            {
                joint = (SixDofJoint)joints.get(i);
                break;
            }
        }
        if(from.getChild("Beam"+to.hashCode()) != null)
        {
            from.getChild("Beam"+to.hashCode()).removeFromParent();
        }
        if(joint != null)
        {
            app.getStateManager().getState(BulletAppState.class).getPhysicsSpace().remove(joint);
            joint.destroy();
        }
        
        joints = from.getControl(RigidBodyControl.class).getJoints();
        joint = null;
        for(int i = 0; i < joints.size(); i++)
        {
            if((joints.get(i).getBodyA().equals(from.getControl(RigidBodyControl.class)))&&(joints.get(i).getBodyB().equals(to.getControl(RigidBodyControl.class))))
            {
                joint = (SixDofJoint)joints.get(i);
                break;
            }
        }
        if(to.getChild("Beam"+from.hashCode()) != null)
        {
            to.getChild("Beam"+from.hashCode()).removeFromParent();
        }
        if(joint != null)
        {
            app.getStateManager().getState(BulletAppState.class).getPhysicsSpace().remove(joint);
            joint.destroy();
        }
    }
}
