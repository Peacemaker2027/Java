package simplegame.utilities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.newdawn.slick.Color;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import simplegame.baseunit.structure.*;

public class WorldMapCodex
{
	private ArrayList<String> mapNames;
	private Hashtable<Color, String> colorCodex;
	
	public WorldMapCodex()
	{
		mapNames = new ArrayList<String>();
		
		try
		{
			File fXmlFile = new File(Consts.MAPXMLDATA);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("map");
			for (int temp = 0; temp < nList.getLength(); temp++)
			{
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE)
				{
					Element eElement = (Element) nNode;
					mapNames.add(eElement.getAttribute("id"));
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public WorldMap loadMap(String mapName)
	{
		WorldMap wm;
		try 
		{
			this.colorCodex = new Hashtable<Color, String>();
			File fXmlFile = new File(Consts.MAPXMLDATA);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("map");
			
			
			
			for (int temp = 0; temp < nList.getLength(); temp++)
			{
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE)
				{
					Element eElement = (Element) nNode;
					if(eElement.getAttribute("id").equals(mapName))
					{
						for(int i = 0; i<eElement.getElementsByTagName("unit").getLength(); i++)
						{
							String strClass = eElement.getElementsByTagName("unit").item(i).getAttributes().item(0).getTextContent();
							String rgb = eElement.getElementsByTagName("unit").item(i).getAttributes().item(1).getTextContent();
							this.colorCodex.put(new Color(this.convertToValue(rgb, 0), this.convertToValue(rgb, 1), this.convertToValue(rgb, 2)), strClass);
						}
					}
				}
			}
			
			BufferedImage img = null;
			img = ImageIO.read(new File(Consts.RESOURCEPATH + mapName));
			wm = new WorldMap(img.getWidth(), img.getHeight());
			for(int y = 0; y<img.getHeight(); y++)
			{
				for(int x = 0; x<img.getWidth(); x++)
				{
					String className = this.colorCodex.get(new Color(img.getRGB(x, y)));
					switch(className)
					{
					case "Grass":
						Grass grass = new Grass();
						grass.setPosition(x, y);
						wm.fillPoint(grass);
						break;
					case "Mud":
						Mud mud = new Mud();
						mud.setPosition(x, y);
						wm.fillPoint(mud);
						break;
					case "Rock":
						Rock rock = new Rock();
						rock.setPosition(x, y);
						wm.fillPoint(rock);
						break;
					case "Sand":
						Sand sand = new Sand();
						sand.setPosition(x, y);
						wm.fillPoint(sand);
						break;
					case "Water":
						Water water = new Water();
						water.setPosition(x, y);
						wm.fillPoint(water);
						break;
					case "Concrete":
						Concrete concrete = new Concrete();
						concrete.setPosition(x, y);
						wm.fillPoint(concrete);
						break;
					case "Tree":
						Tree tree = new Tree();
						tree.setPosition(x, y);
						wm.fillPoint(tree);
					break;
					case "Swamp":
						Swamp swamp = new Swamp();
						swamp.setPosition(x, y);
						wm.fillPoint(swamp);
					break;
					}
				}
			}
			return wm;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public String getMapName(int index)
	{
		return this.mapNames.get(index);
	}
	
	private int convertToValue(String haystack, int index)
	{
		return Integer.parseInt(haystack.split("[,]")[index].trim().replaceAll("\\D", ""));
	}
}
