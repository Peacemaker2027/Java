package simplegame.utilities;

import java.util.Arrays;

import simplegame.baseunit.properties.Structure;

public class WorldMap
{
	private Structure[][] area;
	private int height, width, viewportheight, viewportwidth, viewportoffsetx, viewportoffsety;
	
	public WorldMap(int width, int height)
	{
		this.width = width;
		this.height = height;
		this.viewportheight = 0;
		this.viewportwidth = 0;
		this.viewportoffsetx = 0;
		this.viewportoffsety = 0;
		
		this.area = new Structure[height][width];
		for (Structure[] row : this.area)
		{
			Arrays.fill(row, null);
		}
	}
	
	public int getWidth()
	{
		return this.width;
	}
	
	public int getHeight()
	{
		return this.height;
	}
	
	public void setViewPortDimensions(int viewWidth, int viewHeight)
	{
		this.viewportwidth = viewWidth;
		this.viewportheight = viewHeight;
	}
	
	public void setViewPortOffset(int x, int y)
	{
		if((x>=0)&&(x<=this.width-this.viewportwidth))
		{
			this.viewportoffsetx = x;
		}
		if((y>=0)&&(y<=this.height-this.viewportheight))
		{
			this.viewportoffsety = y;
		}
	}
	
	public int getViewPortOffsetX()
	{
		return this.viewportoffsetx;
	}
	
	public int getViewPortOffsetY()
	{
		return this.viewportoffsety;
	}
	
	public void fillPoint(Structure value)
	{
		this.area[value.getY()][value.getX()] = value;
	}
	
	public Structure getPoint(int x, int y)
	{
		return this.area[y][x];
	}
	
	public void render(UnitRenderer ur)
	{
		for(int y=0; y<this.viewportheight; y++)
    	{
    		for(int x=0; x<this.viewportwidth; x++)
    		{
    			ur.render(this.getPoint(x+this.viewportoffsetx, y+this.viewportoffsety), x, y);
    		}
    	}
	}
	
	/*public boolean checkPoint(int x, int y)
	{
		return (area[x][y]>0) ? true : false;
	}
	
	public boolean checkHorizontal(int x0, int x1, int y)
	{
		for(int x = x0; x<=x1; x++)
		{
				if(area[x][y]>0)
				{
					return true;
				}
		}
		return false;
	}
	
	public boolean checkVertical(int y0, int y1, int x)
	{
		for(int y = y0; x<=y1; x++)
		{
				if(area[x][y]>0)
				{
					return true;
				}
		}
		return false;
	}
	
	public boolean checkPath(int x0, int y0, int x1, int y1)
	{
		return false;
	}
	
	public boolean checkBox(int x0, int y0, int x1, int y1)
	{
		for(int y = y0; y<=y1; y++)
		{
			for(int x = x0; x<=x1; x++)
			{
				if(area[x][y]>0)
				{
					return true;
				}
			}
		}
		return false;
	}*/
}
