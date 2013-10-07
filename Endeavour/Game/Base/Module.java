package Game.Base;

import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.Control;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author ebradford
 */

public class Module implements Control
{
    protected String password;
    protected int mass;
    protected float eff = 1.0f;
    protected Spatial spatial;
    protected Node node;
    protected boolean enabled = true;
    
    public enum STATE {START, ONLINE, FAULT, OFFLINE};
    public STATE state = STATE.OFFLINE;
    
    protected Map<String,Plugin> plugins = new HashMap<String,Plugin>();
    
    protected long start = System.currentTimeMillis();
    protected long increment = 1000;
    
    public Node getNode()
    {
        return this.node;
    }
    
    public void setSpatial(Spatial spatial)
    {
        this.spatial = spatial;
    }
    
    public Spatial getSpatial()
    {
        return this.spatial;
    }

    @Override
    public void render(RenderManager rm, ViewPort vp) 
    {
        // optional for advanced users, e.g. to display a debug shape
    }
 
    @Override
    public Control cloneForSpatial(Spatial spatial) 
    {
        Module control = new Module();
        // set custom properties
        control.setSpatial(spatial);
        control.setEnabled(isEnabled()); 
        // set some more properties here...
        return control;
    }
 
    @Override
    public void setEnabled(boolean enabled) 
    {
        this.enabled = enabled;
    }
 
    @Override
    public boolean isEnabled() 
    {
        return enabled;
    }
 
    @Override
    public void write(JmeExporter ex) throws IOException 
    {
        //super.write(ex);
        OutputCapsule oc = ex.getCapsule(this);
        oc.write(enabled, "enabled", true);
        oc.write(spatial, "spatial", null);
        // write custom variables ....
    }
    
    @Override
    public void read(JmeImporter im) throws IOException 
    {
        //super.read(im);
        InputCapsule ic = im.getCapsule(this);
        enabled = ic.readBoolean("enabled", true);
        spatial = (Spatial) ic.readSavable("spatial", null);
        // read custom variables ....
    }

    public String getInfo()
    {
        String[] module = this.getClass().getName().split("[.]");
        String info = module[module.length-1] + "\n";
        info += this.mass + "kg\n" + this.eff*100 + "%\n" + this.state.name() + "\n";
        for(Map.Entry<String,Plugin> entry : this.plugins.entrySet())
        {
            info += entry.getValue().kind + ":" + entry.getValue().units + "\n";
        }
        return info;
    }
    
    public int getMass()
    {
        return this.mass;
    }
    
    @Override
    public void update(float tpf)//default implementation can be overidden
    {
        if(this.clock())
        {
            if(this.state == STATE.ONLINE)
            {
                this.executeOutputs(this.executeInputs());
            }
        }
    }
    
    protected float executeInputs()
    {
        float diffeff = 0.0f;
        for(Map.Entry<String,Plugin> entry : this.plugins.entrySet())//calculate input side and efficiency
        {
            if(entry.getValue().connection != null)//if a connection exists on input
            {
                if((entry.getValue().connection.getIO().equals("output"))||(entry.getValue().connection.getIO().equals("neutral")))
                {
                    if(entry.getValue().connection.units <= 0)
                    {
                        this.status(STATE.FAULT);
                    }
                    else
                    {
                        diffeff += 1+(entry.getValue().rate/entry.getValue().connection.units);
                        entry.getValue().connection.units += entry.getValue().rate;
                    }
                }
            }
            else
            {
                if(entry.getValue().getIO().equals("input"))
                {
                    this.status(STATE.FAULT);
                }
            }
        }
        return diffeff;
    }
    
    protected void executeOutputs(float diffeff)
    {
        for(Map.Entry<String,Plugin> entry : this.plugins.entrySet())
        {
            if(diffeff+this.eff>=0)
            {
                if(entry.getValue().accrue)//if the module sums its output...
                {
                    if(entry.getValue().units < (int)Math.pow(this.mass, 3.0))//...and the module is not full
                    {
                        entry.getValue().units += (int)(entry.getValue().rate*(this.eff+diffeff));
                    }
                }
                else//if the module does not sum its output
                {
                    entry.getValue().units = (int)(entry.getValue().rate*(this.eff+diffeff));
                }
            }
            if(entry.getValue().units < 0)
            {
                entry.getValue().units = 0;
            }
            if((this.state == STATE.FAULT)&&(!entry.getValue().accrue))//if the module recieves a fault and the modules output does not accrue
            {
                entry.getValue().units = 0;
            }
        }
    }
    
    public boolean clock()
    {
        if(System.currentTimeMillis()-start>=this.increment)
        {
            this.start = System.currentTimeMillis();
            return true;
        }
        return false;
    }
    
    public boolean lock(String password)//USER OPERATION EXPOSED
    {
        if("".equals(this.password))
        {
            this.password = password;
            return true;
        }
        return false;
    }
    
    public boolean unlock(String password)//USER OPERATION EXPOSED
    {
        if(password.equals(this.password))
        {
            this.password = "";
            return true;
        }
        return false;
    }
    
    public boolean isLocked()
    {
        if("".equals(this.password))
        {
            return false;
        }
        return true;
    }
    
    public int repair(int mass)//USER OPERATION EXPOSED
    {
        if(mass>0)
        {
            if(this.repairCost(1.0f-this.eff) > mass)
            {
                float repairpower = (float)(mass/(Math.pow(this.mass, (1/3))))/100;
                this.eff += repairpower;
                mass = 0;
            }
            else
            {
                mass -= this.repairCost(1.0f-this.eff);
                this.eff = 1.0f;
            }
        }
        return mass;
    }
    
    public int repairCost(float in)//USER OPERATION EXPOSED
    {
        return (int)((in*100)*Math.pow(this.mass, (1/3)));
    }
    
    public STATE status()//USER EXPOSED
    {
        return this.state;
    }
    
    public void status(STATE state)//USER OPERATION EXPOSED
    {
        switch(state)
        {
            case START://module gets initial value on startup to engage
                for(Map.Entry<String,Plugin> entry : this.plugins.entrySet())
                {
                    if(entry.getValue().getIO().equals("output"))
                    {
                        entry.getValue().units = (int)(entry.getValue().rate*this.eff);
                    }
                }
                this.state = STATE.ONLINE;
                //this.enabled = true;
                break;
            case ONLINE://module is active and engaged
                this.state = state;
                //this.enabled = true;
                break;
            case FAULT://module has recieved a fault on input and will continue in ONLINE mode producing with limited bonuses
                this.state = state;
                //this.enabled = true;
                break;
            case OFFLINE://module is not running and is shutdown
                this.state = state;
                //this.enabled = false;
                break;
            default:
                break;
        }
    }
    
    public Plugin getPlugin(String search)
    {
        return this.plugins.get(search);
    }
    
    public void addPlugin(Plugin plugin)
    {
        this.plugins.put(plugin.kind, new Plugin(plugin.kind, plugin.rate, plugin.units, plugin.accrue));
    }
    
    public void deletePlugin(String search)
    {
        this.plugins.remove(search);
    }
    
    public void connect(Plugin plugin)
    {
        if(plugin != null)
        {
            if(this.plugins.containsKey(plugin.kind))//check if the connection has any plugin outputs of the same type
            {
                String myIO = this.plugins.get(plugin.kind).getIO();
                String otherIO = plugin.getIO();
                if(myIO.equals("input"))
                {
                    if((otherIO.equals("output"))||(otherIO.equals("neutral")))
                    {
                        this.plugins.get(plugin.kind).connection = plugin;
                    }
                }
            }
        }
    }
    
    public void disconnect(String pluginKind)
    {
        if(this.plugins.get(pluginKind).getIO().equals("input"))//set any of my inputs.connection, that match pluginKind, to null
        {
            this.plugins.get(pluginKind).connection = null;
        }
    }
}
