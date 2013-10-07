package simplegame;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import simplegame.baseunit.properties.MouseHighlight;
import simplegame.utilities.WorldMap;
import simplegame.utilities.WorldMapCodex;
import simplegame.utilities.UnitRenderer;

/**
 *
 * @author Eric Bradford
 *
 */

public class SimpleGame extends BasicGame 
{
    WorldMap myworld;
    MouseHighlight mouse;
    UnitRenderer renderer;
    int mouse_sensitivity = 2;
    
    public SimpleGame()
    {
        super("Inkscape Test");
    }
    
    public void init(GameContainer container) throws SlickException 
    {
    	container.setShowFPS(false);
    	System.out.println(container.getWidth()+" x "+container.getHeight());
    	container.getGraphics().setBackground(new Color(255,255,255));
    	//container.setMouseGrabbed(true);
    	renderer = new UnitRenderer();
    	mouse = new MouseHighlight();
    	WorldMapCodex mpParse = new WorldMapCodex();
    	myworld = mpParse.loadMap("Bocage.png");
    	myworld.setViewPortDimensions(container.getWidth()/32, (container.getHeight()/32)-1);
    	myworld.setViewPortOffset(0, 0);
    }
    
    public void update(GameContainer container, int delta) throws SlickException 
    {
    	mouse.setPosition(roundCoord(Mouse.getX()), roundCoord(container.getHeight()-Mouse.getY()));
    	int valx = 0;
    	int valy = 0;
    	
    	if(Mouse.isButtonDown(1))
    	{
    		valx = Mouse.getDX()/mouse_sensitivity;
    		valy = -Mouse.getDY()/mouse_sensitivity;
    	}
    	if(Keyboard.isKeyDown(Keyboard.KEY_A))
    	{
    		valx = -1;
    	}
    	myworld.setViewPortOffset(myworld.getViewPortOffsetX()+valx, myworld.getViewPortOffsetY()+valy);
    }
    
    public void render(GameContainer container, Graphics g) throws SlickException 
    {
    	myworld.render(renderer);
    	renderer.render(mouse,mouse.getX(),mouse.getY());
    	g.drawString("["+(mouse.getX()+myworld.getViewPortOffsetX())+","+(mouse.getY()+myworld.getViewPortOffsetY())+"]", mouse.getX()*32, mouse.getY()*32);
    }
    
    public int roundCoord(int var)
    {
    	return (int)Math.round(((float)var)/32.0f);
    }
    
    public static void main(String argv[]) 
    {
            try 
            {
                    AppGameContainer container = new AppGameContainer(new SimpleGame());
                    container.setDisplayMode(800,608,false);
                    container.start();
            } 
            catch (Exception e)
            {
                    e.printStackTrace();
            }
    }
}
