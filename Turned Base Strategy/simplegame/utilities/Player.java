package simplegame.utilities;

public class Player
{
	private String name;
	
	public Player(String name)
	{
		this.name = name;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	@SuppressWarnings("unused")
	private boolean between(int lower, int middle, int upper)
	{
		return ((lower<middle)&&(middle<upper)) ? true : false;
	}
}
