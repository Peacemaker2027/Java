package simplegame.utilities;

import java.awt.Font;
import java.io.File;
import java.io.InputStream;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.newdawn.slick.Color;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.util.ResourceLoader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import simplegame.baseunit.properties.Unit;
import simplegame.utilities.Consts.CELL;

public class UnitRenderer
{
	private SpriteSheet sheet;
	private Hashtable<String, RenderData> renderXMLData;
	private final int height, width;
	private TrueTypeFont font,font_italic;
	
	public UnitRenderer()
	{
		this.height = this.width = 32;
		try 
		{
			this.sheet = new SpriteSheet(Consts.SPRITESHEETNAME, this.width, this.height, new Color(255,174,201));
			
			InputStream inputStream	= ResourceLoader.getResourceAsStream(Consts.FONTFILENAME);
			Font awtFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
			awtFont = awtFont.deriveFont(9f);
			awtFont = awtFont.deriveFont(Font.PLAIN);
			this.font = new TrueTypeFont(awtFont, true);
			awtFont = awtFont.deriveFont(9f);
			awtFont = awtFont.deriveFont(Font.ITALIC);
			this.font_italic = new TrueTypeFont(awtFont, true);
			
			this.renderXMLData = new Hashtable<String, RenderData>();
			File fXmlFile = new File(Consts.SPRITEXMLDATA);
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
			NodeList nList = doc.getElementsByTagName("unit");
			
			for (int temp = 0; temp < nList.getLength(); temp++)
			{
				Node nNode = nList.item(temp);
				if (nNode.getNodeType() == Node.ELEMENT_NODE)
				{
					Element eElement = (Element) nNode;
					RenderData rd = new RenderData();
					
					if(eElement.getElementsByTagName("arg0").getLength()>0)
					{
						rd.column(CELL.ARG0, this.convertToValue(eElement.getElementsByTagName("arg0").item(0).getTextContent(), 0));
						rd.row(CELL.ARG0, this.convertToValue(eElement.getElementsByTagName("arg0").item(0).getTextContent(), 1));
					}
					if(eElement.getElementsByTagName("arg1").getLength()>0)
					{
						rd.column(CELL.ARG1, this.convertToValue(eElement.getElementsByTagName("arg1").item(0).getTextContent(), 0));
						rd.row(CELL.ARG1, this.convertToValue(eElement.getElementsByTagName("arg1").item(0).getTextContent(), 1));
					}
					if(eElement.getElementsByTagName("arg2").getLength()>0)
					{
						rd.column(CELL.ARG2, this.convertToValue(eElement.getElementsByTagName("arg2").item(0).getTextContent(), 0));
						rd.row(CELL.ARG2, this.convertToValue(eElement.getElementsByTagName("arg2").item(0).getTextContent(), 1));
					}
					if(eElement.getElementsByTagName("arg3").getLength()>0)
					{
						rd.column(CELL.ARG3, this.convertToValue(eElement.getElementsByTagName("arg3").item(0).getTextContent(), 0));
						rd.row(CELL.ARG3, this.convertToValue(eElement.getElementsByTagName("arg3").item(0).getTextContent(), 1));
					}
					if(eElement.getElementsByTagName("arg4").getLength()>0)
					{
						rd.column(CELL.ARG4, this.convertToValue(eElement.getElementsByTagName("arg4").item(0).getTextContent(), 0));
						rd.row(CELL.ARG4, this.convertToValue(eElement.getElementsByTagName("arg4").item(0).getTextContent(), 1));
					}
					
					this.renderXMLData.put(eElement.getAttribute("id"), rd);
				}
			}
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	private int convertToValue(String haystack, int index)
	{
		return Integer.parseInt(haystack.split("[,]")[index].trim().replaceAll("\\D", ""));
	}
	
	public void render(Unit unit, int offsetX, int offsetY)
	{
		if(this.renderXMLData.get(unit.getName()).isValid(unit.getDirection()))
		{
			this.sheet.startUse();
			this.sheet.getSubImage(this.renderXMLData.get(unit.getName()).column(unit.getDirection()), this.renderXMLData.get(unit.getName()).row(unit.getDirection())).drawEmbedded(offsetX*this.width, offsetY*this.height);
			this.sheet.endUse();
		}
	}
	
	@SuppressWarnings("unused")
	private void renderUnitData(Unit unit)
	{
		this.font.drawString(unit.getX()+(this.width*0.8f), unit.getY()+15, unit.getName(), Color.black);
		this.font_italic.drawString(unit.getX()+(this.width/2.0f), unit.getY()+(this.width*3.0f), unit.getUnitInfo(), Color.black);
	}
}
