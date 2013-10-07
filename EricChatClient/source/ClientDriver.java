package source;
import java.io.IOException;
import java.net.Socket;


public class ClientDriver 
{

					//	SET A CONSTANT VARIABLE HOST
	
	public static void main(String[] args) throws IOException
	{
		int PORT = 6143;						//	SET A CONSTANT VARIABLE PORT
		String HOST = "localhost";
		String NAME = "ANONYMOUS";
		
		if(args.length>0)
		{
			PORT = Integer.parseInt(args[0]);
		}
		if(args.length>1)
		{
			HOST = args[1];
		}
		if(args.length>2)
		{
			NAME = args[2];
		}
		try 
		{
			Socket s = new Socket(HOST, PORT);					//	CONNECT	TO THE SERVER
			
			System.out.println("You connected to " + HOST);		//	IF CONNECTED THEN PRINT IT OUT
			
			ClientObject client = new ClientObject(s, NAME);						//	START NEW CLIENT OBJECT
			
			Thread t = new Thread(client);						//	INITIATE NEW THREAD
			t.start();											//	START THREAD
			
		} 
		catch (Exception noServer)								//	IF DIDNT CONNECT PRINT THAT THEY DIDNT
		{
			System.out.println("The server might not be up at this time.");
			System.out.println("Please try again later.");
		}
	}
}
