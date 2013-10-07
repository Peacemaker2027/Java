package Game.Gui;

import com.jme3.asset.AssetManager;
import com.jme3.effect.ParticleEmitter;
import com.jme3.effect.ParticleMesh;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.system.JmeSystem;

/**
 *
 * @author ebradford
 */
public class SelectorEmitters
{
        static public ParticleEmitter EMITTER_FROM()
        {
            ParticleEmitter particle = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 30);
            AssetManager assetManager = JmeSystem.newAssetManager(Thread.currentThread().getContextClassLoader().getResource("com/jme3/asset/Desktop.cfg"));
            Material mat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
            mat.setTexture("Texture", assetManager.loadTexture("Textures/arrow_out.png"));
            particle.setMaterial(mat);
            particle.setImagesX(1);
            particle.setImagesY(1); // 2x2 texture animation
            particle.setStartColor(new ColorRGBA(1f, 0f, 0f, 0.5f));   // red
            particle.setEndColor(new ColorRGBA(1f, 1f, 0f, 0.25f)); // yellow
            particle.getParticleInfluencer().setInitialVelocity(new Vector3f(0,0,0));
            particle.setStartSize(0.5f);
            particle.setEndSize(1.0f);
            particle.setGravity(0,0,0);
            particle.setLowLife(0.1f);
            particle.setHighLife(0.1f);
            particle.getParticleInfluencer().setVelocityVariation(0.3f);
            return particle;
        }
    static public ParticleEmitter EMITTER_TO()
    {
        ParticleEmitter particle = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 30);
        AssetManager assetManager = JmeSystem.newAssetManager(Thread.currentThread().getContextClassLoader().getResource("com/jme3/asset/Desktop.cfg"));
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", assetManager.loadTexture("Textures/arrow_in.png"));
        particle.setMaterial(mat);
        particle.setImagesX(1);
        particle.setImagesY(1); // 2x2 texture animation
        particle.setStartColor(new ColorRGBA(0f, 0f, 1f, 0.5f));   // blue
        particle.setEndColor(new ColorRGBA(0.5f, 0f, 1f, 0.25f)); // violet
        particle.getParticleInfluencer().setInitialVelocity(new Vector3f(0,0,0));
        particle.setStartSize(1.0f);
        particle.setEndSize(0.5f);
        particle.setGravity(0,0,0);
        particle.setLowLife(0.1f);
        particle.setHighLife(0.1f);
        particle.getParticleInfluencer().setVelocityVariation(0.3f);
        return particle;
    }
    static public ParticleEmitter EMITTER_ENTRY()
    {
        ParticleEmitter particle = new ParticleEmitter("Emitter", ParticleMesh.Type.Triangle, 30);
        AssetManager assetManager = JmeSystem.newAssetManager(Thread.currentThread().getContextClassLoader().getResource("com/jme3/asset/Desktop.cfg"));
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Particle.j3md");
        mat.setTexture("Texture", assetManager.loadTexture("Textures/flash.png"));
        particle.setMaterial(mat);
        particle.setImagesX(2);
        particle.setImagesY(2); // 2x2 texture animation
        particle.setStartColor(ColorRGBA.White);// white
        particle.setEndColor(new ColorRGBA(0f, 0f, 0f, 0.0f)); // BLACK ALPHA
        particle.getParticleInfluencer().setInitialVelocity(new Vector3f(0,0,0));
        particle.setStartSize(0.5f);
        particle.setEndSize(1.5f);
        particle.setGravity(0,0,0);
        particle.setLowLife(0.1f);
        particle.setHighLife(0.5f);
        particle.getParticleInfluencer().setVelocityVariation(0.5f);
        return particle;
    }
}