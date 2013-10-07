package Game.Base;
import com.jme3.app.SimpleApplication;
import java.io.*;
import java.util.ArrayList;
import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import java.net.*;

/**
 *
 * @author ebradford
 */
public class LoadModuleClasses
{
    public LoadModuleClasses(String Manifest) throws Exception
    {
        BufferedReader br;
        String strLine;
        URL online = new URL(Manifest);
        URLConnection yc = online.openConnection();
        br = new BufferedReader(new InputStreamReader(yc.getInputStream()));
        while ((strLine = br.readLine()) != null)
        {
            Class cls = Class.forName("Game.Modules." + strLine);
            classLibrary.put(strLine, cls);
        }
        br.close();
    }
    public List<String> getClassNames()
    {
        List<String> result = new ArrayList<String>();
        Iterator itr = this.classLibrary.entrySet().iterator();
        while (itr.hasNext())
        {
            result.add(((Map.Entry)itr.next()).getKey().toString());
        }
        return result;
    }
    public List<Object> getClasses()
    {
        List<Object> result = new ArrayList<Object>();
        Iterator itr = this.classLibrary.entrySet().iterator();
        while (itr.hasNext())
        {
            result.add(((Map.Entry)itr.next()).getValue());
        }
        return result;
    }
    public Module newClass(String name, int mass, SimpleApplication app) throws Exception
    {
        Module result = null;
        Constructor ctor = classLibrary.get(name).getDeclaredConstructor(int.class, SimpleApplication.class);
        ctor.setAccessible(true);
        result = (Module)ctor.newInstance(mass, app);
        return result;
    }
    private Map<String,Class> classLibrary = new HashMap<String,Class>();
} 