package Game.Gui;

import Game.Base.Module;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.effect.ParticleEmitter;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.Trigger;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import java.util.Iterator;

/**
 *
 * @author ebradford
 */

public class Selector
{
    private Node node;
    private ParticleEmitter emitter;
    private Geometry touching;
    private boolean engaged = true;
    private String keyname;
    private String specialkeyname;
    public Selector(String name, SimpleApplication app, ParticleEmitter pe, ColorRGBA color)
    {
        node = new Node(name);
        this.setParticleEmitter(pe);
    }
    /** Declaring the "Shoot" action and mapping to its triggers. */
    public void setKey(SimpleApplication app, Trigger key)
    {
        keyname = key.getName();
        app.getInputManager().addMapping(keyname, key);
        app.getInputManager().addListener(this.selectorActionListener(app), keyname);
    }
    public void setSpecialKey(SimpleApplication app, Trigger special)
    {
        engaged = false;
        specialkeyname = special.getName();
        app.getInputManager().addMapping(specialkeyname, special);
        app.getInputManager().addListener(this.enableActionListener(), specialkeyname);
    }
    public void setParticleEmitter(ParticleEmitter pe)
    {
        emitter = pe.clone();
        node.attachChild(emitter);
    }
    public Geometry getTouchingGeometry()
    {
        return touching;
    }
    /** Defining the "Shoot" action: Determine what was hit and how to respond. */
    private ActionListener enableActionListener()
    {
        ActionListener actionListener = new ActionListener()
        {
            public void onAction(String name, boolean keyPressed, float tpf)
            {
                if(name.equals(specialkeyname) && keyPressed)
                {
                    engaged = true;
                }
                else
                {
                    engaged = false;
                }
            }
        };
        return actionListener;
    }
    private ActionListener selectorActionListener(final SimpleApplication app)
    {
        ActionListener actionListener = new ActionListener()
        {
            public void onAction(String name, boolean keyPressed, float tpf)
            {
                if (name.equals(keyname) && keyPressed && engaged)
                {
                    CollisionResults results = new CollisionResults();
                    Vector2f click2d = app.getInputManager().getCursorPosition();
                    Vector3f click3d = app.getCamera().getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
                    Vector3f dir = app.getCamera().getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d);
                    Ray ray = new Ray(click3d, dir);
                    app.getRootNode().collideWith(ray, results);
                    Iterator itr = results.iterator();
                    while(itr.hasNext())
                    {
                        CollisionResult stank = (CollisionResult)itr.next();
                        if(!stank.getGeometry().getName().contains("geom"))
                        {
                            itr.remove();
                        }
                    }
                    if (results.size() > 0)
                    {
                        CollisionResult closest = results.getClosestCollision();
                        touching = closest.getGeometry();
                        
                        if(emitter.getStartSize()>emitter.getEndSize())
                        {
                            emitter.setStartSize((float)Math.pow(touching.getControl(Module.class).getMass(), (1.0f/3.0f))/10.0f);
                            emitter.setEndSize(0.0f);
                        }
                        else
                        {
                            emitter.setStartSize(0.0f);
                            emitter.setEndSize((float)Math.pow(touching.getControl(Module.class).getMass(), (1.0f/3.0f))/10.0f);
                        }
                        touching.getParent().attachChild(node);
                    }
                    else if(touching != null)
                    {
                        touching.getParent().detachChild(node);
                        touching = null;
                    }
                }
            }
        };
        return actionListener;
    }
}
