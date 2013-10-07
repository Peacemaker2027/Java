package source;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientObject implements Runnable
{

	private Socket socket;										//	MAKE SOCKET INSTANCE VARIABLE
	private String clientName;
	
	public ClientObject(Socket s, String clientName)
	{
		socket = s;												//	INSTANTIATE THE INSTANCE VARIABLE
		this.clientName = clientName;
	}
	
	@Override
	public void run() 											//	INHERIT THE RUN METHOD FROM THE Runnable INTERFACE
	{
		try
		{
			Scanner chat = new Scanner(System.in);						//	GET THE INPUT FROM THE CMD			
			PrintWriter out = new PrintWriter(socket.getOutputStream());//	GET THE CLIENTS OUTPUT STREAM (USED TO SEND DATA TO THE SERVER)
			boolean isExiting = false;
			Thread t = new Thread(new ClientMessageQueue(socket));
			t.start();
			
			while (!isExiting)										//	WHILE THE PROGRAM IS RUNNING
			{
				String line = chat.nextLine();
				if(line == "exit;")
				{
					isExiting = true;
				}
				if((line == "help;")||(line == "?;"))
				{
					System.out.println(
							"?;help; = for help in a list of commands.\n" +
							"exit; = exit the program safely, closing the connection.\n" + 
							"list; = list the users connected to the chat service." +
							"user_level; = gets the current user a privelege."
							);
				}
				
				String input = "["+this.clientName+"] "+line;					//	SET NEW VARIABLE input TO THE VALUE OF WHAT THE CLIENT TYPED IN
				out.println(input);								//	SEND IT TO THE SERVER
				out.flush();									//	FLUSH THE STREAM
			}
			this.socket.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();								//	MOST LIKELY WONT BE AN ERROR, GOOD PRACTICE TO CATCH THOUGH
		}
	}
}
