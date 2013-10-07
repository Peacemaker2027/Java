package Game.Gui;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.Trigger;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;

/**
 *
 * @author ebradford
 */
public class MouseSelector
{
    protected Geometry touching;
    protected boolean engaged = true;
    protected String keyname;
    protected String specialkeyname;
    public void setKey(SimpleApplication app, Trigger key)
    {
        this.keyname = key.getName();
        app.getInputManager().addMapping(this.keyname, key);
        app.getInputManager().addListener(this.mouseAnalogListener(app), this.keyname);
    }
    public void setSpecialKey(SimpleApplication app, Trigger special)
    {
        this.engaged = false;
        this.specialkeyname = special.getName();
        app.getInputManager().addMapping(this.specialkeyname, special);
        app.getInputManager().addListener(this.enableActionListener(), this.specialkeyname);
    }
    public Geometry getTouchingGeometry()
    {
        return touching;
    }
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
    private AnalogListener mouseAnalogListener(final SimpleApplication app)
    {
        AnalogListener analogListener = new AnalogListener() 
        {
            public void onAnalog(String name, float intensity, float tpf) 
            {
                if (name.equals(keyname)) 
                {
                    CollisionResults results = new CollisionResults();
                    Vector2f click2d = app.getInputManager().getCursorPosition();
                    Vector3f click3d = app.getCamera().getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
                    Vector3f dir = app.getCamera().getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d);
                    Ray ray = new Ray(click3d, dir);
                    app.getRootNode().collideWith(ray, results);
                    if (results.size() > 0) 
                    {
                        touching = results.getClosestCollision().getGeometry();
                    }
                    else
                    {
                        touching = null;
                    }
                }
            }
        };
        return analogListener;
    }
}