package simplegame.utilities;

import java.util.Arrays;

import simplegame.utilities.Consts.CELL;

public class RenderData
{
	protected int[] columns, rows;
	
	public RenderData()
	{
		this.columns = new int[CELL.values().length];
		this.rows = new int[CELL.values().length];
		Arrays.fill(this.columns, -1);
		Arrays.fill(this.rows, -1);
	}
	
	public RenderData(RenderData rd)
	{
		for(int i = 0; i<columns.length; i++)
		{
			this.columns[i] = rd.columns[i];
			this.rows[i] = rd.rows[i];
		}
	}
	
	public boolean isValid(CELL dir)
	{
		if((this.columns[dir.ordinal()]<0)||(this.rows[dir.ordinal()]<0))
		{
			return false;
		}
		return true;
	}
	
	public void column(CELL dir, int x)
	{
		this.columns[dir.ordinal()] = x;
	}
	
	public void row(CELL dir, int y)
	{
		this.rows[dir.ordinal()] = y;
	}
	
	public int column(CELL dir)
	{
		return this.columns[dir.ordinal()];
	}
	
	public int row(CELL dir)
	{
		return this.rows[dir.ordinal()];
	}
}