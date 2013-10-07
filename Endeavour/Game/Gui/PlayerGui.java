package Game.Gui;

import Game.Base.LoadModuleClasses;
import Game.Base.Module;
import com.jme3.app.SimpleApplication;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.KeyInput;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.*;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Line;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.NiftyMethodInvoker;
import de.lessvoid.nifty.builder.LayerBuilder;
import de.lessvoid.nifty.builder.PanelBuilder;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.controls.DropDown;
import de.lessvoid.nifty.controls.Label;
import de.lessvoid.nifty.controls.TextField;
import de.lessvoid.nifty.controls.button.builder.ButtonBuilder;
import de.lessvoid.nifty.controls.dropdown.builder.DropDownBuilder;
import de.lessvoid.nifty.controls.label.builder.LabelBuilder;
import de.lessvoid.nifty.controls.textfield.builder.TextFieldBuilder;
import de.lessvoid.nifty.screen.DefaultScreenController;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author ebradford
 */
public class PlayerGui 
{
    private NiftyJmeDisplay niftyDisplay;
    private SimpleApplication app;
    private LoadModuleClasses objectcollections;
    private Selector from;
    private Selector to;
    private Geometry touching;
    
    public PlayerGui(final SimpleApplication app)
    {
        this.app = app;
        niftyDisplay= new NiftyJmeDisplay(this.app.getAssetManager(), this.app.getInputManager(), this.app.getAudioRenderer(), this.app.getGuiViewPort());
        Nifty nifty = niftyDisplay.getNifty();
        this.app.getGuiViewPort().addProcessor(niftyDisplay);
        this.app.getFlyByCamera().setDragToRotate(true);
        
        nifty.loadStyleFile("nifty-default-styles.xml");
        nifty.loadControlFile("nifty-default-controls.xml");
        
        ScreenBuilder PlayerScreen = new ScreenBuilder("PlayerScreen");
        PlayerScreen.controller(new DefaultScreenController());
        
        LayerBuilder BackGround = new LayerBuilder("BackGround");
        BackGround.childLayoutCenter();
        
        LayerBuilder ForeGround  = new LayerBuilder("foreground");
        ForeGround.childLayoutHorizontal();
        ForeGround.backgroundColor("#0000");
        
        PanelBuilder LeftSpacer = new PanelBuilder("LeftSpacer");
        LeftSpacer.childLayoutVertical();
        LeftSpacer.height("100%");
        LeftSpacer.width("85%");
        
        PanelBuilder RightSpacer = new PanelBuilder("RightSpacer");
        RightSpacer.childLayoutVertical();
        RightSpacer.height("100%");
        RightSpacer.width("15%");
        
        PanelBuilder TopRight1 = new PanelBuilder("TopRight1");
        TopRight1.childLayoutVertical();
        TopRight1.height("30%");
        TopRight1.width("100%");
        
        PanelBuilder BottomRight = new PanelBuilder("BottomRight");
        BottomRight.childLayoutCenter();
        BottomRight.backgroundColor("#0000");
        BottomRight.height("70%");
        BottomRight.width("100%");
        
        DropDownBuilder ModuleItems = new DropDownBuilder("ModuleItems");
        ModuleItems.width("100%");
        
        ButtonBuilder BuildButton = new ButtonBuilder("Build", "Build");
        BuildButton.width("100%");
        BuildButton.visibleToMouse(true);
        
        ButtonBuilder RecycleButton = new ButtonBuilder("Recycle", "Recycle");
        RecycleButton.visibleToMouse(true);
        RecycleButton.width("100%");
        
        ButtonBuilder CommandButton = new ButtonBuilder("Operate", "Operate");
        CommandButton.visibleToMouse(true);
        CommandButton.width("100%");
        
        TextFieldBuilder TextField = new TextFieldBuilder("Build Mass", "");
        TextField.visibleToMouse(true);
        TextField.width("100%");
        
        int massbalance = 1000000;//DB_PLAYER_DATA
        LabelBuilder MassBalance = new LabelBuilder("Mass Balance", String.valueOf(massbalance));
        MassBalance.visibleToMouse(true);
        MassBalance.width("100%");
        
        LabelBuilder InfoLabel = new LabelBuilder("Information", "");
        InfoLabel.visibleToMouse(true);
        InfoLabel.valignTop();
        InfoLabel.height("100%");
        InfoLabel.width("100%");
        
        TopRight1.control(ModuleItems);
        TopRight1.control(BuildButton);
        TopRight1.control(RecycleButton);
        TopRight1.control(CommandButton);
        TopRight1.control(TextField);
        TopRight1.control(MassBalance);
        
        BottomRight.control(InfoLabel);
        
        RightSpacer.panel(TopRight1);
        RightSpacer.panel(BottomRight);
        
        ForeGround.panel(LeftSpacer);
        ForeGround.panel(RightSpacer);
        
        PlayerScreen.layer(BackGround);
        PlayerScreen.layer(ForeGround);
        
        nifty.addScreen("PlayerScreen", PlayerScreen.build(nifty));
        
        niftyDisplay.getNifty().getScreen("PlayerScreen").findElementByName("Build")
                .getElementInteraction().getPrimary().setOnClickMethod(
                new NiftyMethodInvoker(niftyDisplay.getNifty(), "buildModule()", this));
        
        niftyDisplay.getNifty().getScreen("PlayerScreen").findElementByName("Recycle")
                .getElementInteraction().getPrimary().setOnClickMethod(
                new NiftyMethodInvoker(niftyDisplay.getNifty(), "recycleModule()", this));
        
        this.from = new Selector("From", app, SelectorEmitters.EMITTER_FROM(), ColorRGBA.Red);
        this.from.setKey(app, new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        this.from.setSpecialKey(app, new KeyTrigger(KeyInput.KEY_LCONTROL));
        
        this.to = new Selector("To", app, SelectorEmitters.EMITTER_TO(), ColorRGBA.Blue);
        this.to.setKey(app, new MouseButtonTrigger(MouseInput.BUTTON_RIGHT));
        this.to.setSpecialKey(app, new KeyTrigger(KeyInput.KEY_LCONTROL));
        
        ActionListener actionListener = new ActionListener() 
        {
            public void onAction(String name, boolean keypressed, float tpf) 
            {
                if (name.equals("operate")) 
                {
                    CollisionResults results = new CollisionResults();
                    Vector2f click2d = app.getInputManager().getCursorPosition();
                    Vector3f click3d = app.getCamera().getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 0f).clone();
                    Vector3f dir = app.getCamera().getWorldCoordinates(new Vector2f(click2d.x, click2d.y), 1f).subtractLocal(click3d);
                    Ray ray = new Ray(click3d, dir);
                    app.getRootNode().collideWith(ray, results);
                    Iterator<CollisionResult> itr = results.iterator();
                    while(itr.hasNext())
                    {
                        CollisionResult o = itr.next();
                        if(!o.getGeometry().getName().contains("geom"))
                        {
                            itr.remove();
                        }
                    }
                    if ((results.size() > 0)&&(results.getClosestCollision().getGeometry().getName().contains("geom")))
                    {
                        niftyDisplay.getNifty().getScreen("PlayerScreen").
                        findNiftyControl("Information", Label.class).
                        setText(results.getClosestCollision().getGeometry().getControl(Module.class).getInfo());
                    }
                    else
                    {
                        niftyDisplay.getNifty().getScreen("PlayerScreen").
                        findNiftyControl("Information", Label.class).
                        setText("");
                        touching = null;
                    }
                }
            }
        };
        app.getInputManager().addMapping("operate", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
        app.getInputManager().addListener(actionListener, "operate");
        this.start();
    }
    private void start()
    {        
        try
        {
            objectcollections = new LoadModuleClasses("http://hitpointfactory.dyndns.org/manifest");
            niftyDisplay.getNifty().gotoScreen("PlayerScreen");
 
            this.addItems(objectcollections.getClassNames());
        }
        catch(Exception e)
        {
            ;
        }
    }
    public void addItems(List items)
    {
        DropDown dropdown = niftyDisplay.getNifty().getScreen("PlayerScreen").findNiftyControl("ModuleItems", DropDown.class);
        if(dropdown != null)
        {
            dropdown.addAllItems(items);
        }
    }
    public Object getSelected()
    {
        DropDown dropdown = niftyDisplay.getNifty().getScreen("PlayerScreen").findNiftyControl("ModuleItems", DropDown.class);
        return dropdown.getSelection();
    }
    public void buildModule()
    {
        int balance = Integer.valueOf(niftyDisplay.getNifty().getScreen("PlayerScreen").findNiftyControl("Mass Balance", Label.class).getText());
        int mass = Integer.valueOf(niftyDisplay.getNifty().getScreen("PlayerScreen").findNiftyControl("Build Mass", TextField.class).getText());
        if((mass <= balance)&&(mass > 0))
        {
            try
            {
                Module newModule = objectcollections.newClass((String)this.getSelected(), mass, this.app);
                Vector3f dir = new Vector3f(this.app.getCamera().getDirection());
                Vector3f loc = new Vector3f(this.app.getCamera().getLocation());
                dir = dir.mult((float)Math.pow((mass/7850f),1f/3f)*10f);
                Vector3f newvec = new Vector3f(loc.add(dir));
                newModule.getNode().getControl(RigidBodyControl.class).setPhysicsLocation(newvec);
                newModule.getNode().getControl(RigidBodyControl.class).setPhysicsRotation(this.app.getCamera().getRotation());
                balance -= mass;
                niftyDisplay.getNifty().getScreen("PlayerScreen").findNiftyControl("Mass Balance", Label.class).setText(String.valueOf(balance));
            }
            catch(Exception e)
            {
                ;
            }
        }
    }
    public void recycleModule()
    {
        ;
    }
    public void physicsConnect()
    {
        ;
    }
}