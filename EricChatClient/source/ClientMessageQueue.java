package source;

import java.net.Socket;
import java.util.Scanner;

public class ClientMessageQueue implements Runnable
{
	private Scanner in;
	
	public ClientMessageQueue(Socket socket)
	{
		try
		{
			this.in = new Scanner(socket.getInputStream());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void run()
	{
		while(true)
		{
			if(this.in.hasNext())								//	IF THE SERVER SENT US SOMETHING
			{	
				System.out.println(this.in.nextLine());			//	PRINT IT OUT
			}
		}
	}
}
