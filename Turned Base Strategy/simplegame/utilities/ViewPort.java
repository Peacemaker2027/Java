package simplegame.utilities;

public class ViewPort
{
	private int viewport_width, viewport_height, v_scroll, h_scroll;
	
	public ViewPort(int viewport_width, int viewport_height, int screen_height, int screen_width)
	{
		this.viewport_width = viewport_width;
		this.viewport_height = viewport_height;
		this.v_scroll = Math.round(screen_height/2.0f);
		this.h_scroll = Math.round(screen_width/2.0f);
	}
	
	public int getWidth()
	{
		return this.viewport_width;
	}
	
	public int getHeight()
	{
		return this.viewport_height;
	}
	
	public int getHScroll()
	{
		return this.h_scroll;
	}
	
	public int getVScroll()
	{
		return this.v_scroll;
	}
	
	public void scroll(int x_increment, int y_increment)
	{
		this.h_scroll += x_increment;
		this.v_scroll += y_increment;
	}
}
