package gent.timdemey.bsp;

public class CommandCenter
{
	private final EventBus eventBus;
	private Thread gameLoopThread; 
	
	public CommandCenter (EventBus eventBus)
	{
		this.eventBus = eventBus;
	}
	
	public void StartRender()
	{
		if (gameLoopThread != null)
		{
			return;
		}
		
		Loop loop = new Loop(eventBus);
		gameLoopThread = new Thread(loop, "Game Loop");		
		gameLoopThread.start();
	}
	
	public void StopRender()
	{
		if (gameLoopThread == null)
		{
			return;
		}
		
		gameLoopThread.interrupt();
		gameLoopThread = null;
	}
}
