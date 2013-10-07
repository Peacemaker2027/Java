package mygame;

import clans.*;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.BulletAppState;
import com.jme3.math.Vector3f;
import equipment.*;
import starships.*;

public class Main extends SimpleApplication
{
    private BulletAppState bulletAppState;
    Starship player;
    public static void main(String[] args)
    {
        Main app = new Main();
        app.start();
    }
    @Override
    public void simpleInitApp()
    {
        bulletAppState = new BulletAppState();
        stateManager.attach(bulletAppState);
        bulletAppState.getPhysicsSpace().enableDebug(assetManager);
        bulletAppState.getPhysicsSpace().setGravity(Vector3f.ZERO);
        this.player = new Cruiser_Frigate(this,new Clan_Belter());
        bulletAppState = new BulletAppState();
    }
    public static String getData(Starship s)
    {
        return s.clan().description()+"\nDamage:"+s.damage()+"\nSpeed:"+s.speed()+"\nArmor:"+s.armor()+"\nHardpoints:"+s.hardpoints()+"\nShield:"+s.shield()+"\nTracking:"+s.tracking();
    }
}


