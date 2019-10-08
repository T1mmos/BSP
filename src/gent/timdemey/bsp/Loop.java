package gent.timdemey.bsp;

import java.util.List;

import gent.timdemey.bsp.gamedata.GameData;
import gent.timdemey.bsp.hud.HudEvent;
import gent.timdemey.bsp.hud.KeyChange;
import gent.timdemey.bsp.rendering.RenderEvent;
import gent.timdemey.bsp.rendering.RenderingState;

public class Loop implements Runnable
{
	private final EventBus eventBus;
	private final GameData gameData;
	private long prevTickTime;
	
	public Loop (EventBus eventBus)
	{
		this.eventBus = eventBus;
		this.gameData = new GameData();
	}
	
	@Override
	public void run()
	{		
		eventBus.AddRenderEvent(new RenderEvent()
		{{
			renderingState = RenderingState.Active;
		}});
		while(!Thread.interrupted())
		{
			HandleHudInput();
			Update();
			Render();
			try
			{
				Thread.sleep(1);
			} catch (InterruptedException e)
			{
				Thread.currentThread().interrupt();
			}
		}
		eventBus.AddRenderEvent(new RenderEvent()
		{{
			renderingState = RenderingState.Stopped; 
			announcement = null;
		}});
	}
	
	private void HandleHudInput()
	{
		// handle user input
		
		List<HudEvent> events = eventBus.GetHudEvents();
		
		if (events != null && events.size() > 0)
		{
			for (HudEvent event : events)
			{
				if (event.MouseLocation != null)
				{
					gameData.mouseData.location = event.MouseLocation;
				}
				
				if (event.KeyUp != null && event.KeyUp != KeyChange.Unchanged)
				{
					gameData.keyboardData.Up = event.KeyUp == KeyChange.Pressed;
				}
				if (event.KeyDown != null && event.KeyDown != KeyChange.Unchanged)
				{
					gameData.keyboardData.Down = event.KeyDown == KeyChange.Pressed;
				}
				if (event.KeyLeft != null && event.KeyLeft != KeyChange.Unchanged)
				{
					gameData.keyboardData.Left = event.KeyLeft == KeyChange.Pressed;
				}
				if (event.KeyRight != null && event.KeyRight != KeyChange.Unchanged)
				{
					gameData.keyboardData.Right = event.KeyRight == KeyChange.Pressed;
				}			
			}
		}
	}
	
	private void Update()
	{
		long currTickTime = System.currentTimeMillis();
		if (prevTickTime != 0)
		{
			int moveForward = 0, moveLeft = 0;
			
			moveForward += gameData.keyboardData.Up ? 1 : 0;
			moveForward += gameData.keyboardData.Down ? -1 : 0;
			moveLeft += gameData.keyboardData.Left ? 1 : 0;
			moveLeft += gameData.keyboardData.Right ? -1 : 0;			

			long diffTickTime = currTickTime - prevTickTime;
			double fracsec = 1.0 * diffTickTime / 1000;
			
			// rotation
			if (moveLeft != 0)
			{
				gameData.playerData.rotAngle += moveLeft * fracsec * Math.PI / 2;
				gameData.playerData.rotX = Math.cos(gameData.playerData.rotAngle);
				gameData.playerData.rotY = Math.sin(gameData.playerData.rotAngle);
			}
			
			// position
			if (moveForward != 0)
			{
				gameData.playerData.posX += 200* moveForward * fracsec * gameData.playerData.rotX;
				gameData.playerData.posY += 200*moveForward * fracsec * gameData.playerData.rotY;
			}
		}
		
		prevTickTime = currTickTime;		
	}
	
	private void Render()
	{
		eventBus.AddRenderEvent(new RenderEvent()
		{{
			renderingState = RenderingState.Active;
			announcement = gameData.hudAnnouncement.Text;
			posX = (int) gameData.playerData.posX;
			posY = (int) gameData.playerData.posY;
		}});
	}
}
