package gent.timdemey.bsp;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.List;

import gent.timdemey.bsp.bsp.BspData;
import gent.timdemey.bsp.gamedata.GameData;
import gent.timdemey.bsp.hud.HudEvent;
import gent.timdemey.bsp.hud.KeyChange;
import gent.timdemey.bsp.rendering.RenderEvent;
import gent.timdemey.bsp.rendering.RenderUtils;
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
					gameData.inputData.mouseData.location = event.MouseLocation;
				}
				
				if (event.KeyUp != null && event.KeyUp != KeyChange.Unchanged)
				{
					gameData.inputData.keyboardData.Up = event.KeyUp == KeyChange.Pressed;
				}
				if (event.KeyDown != null && event.KeyDown != KeyChange.Unchanged)
				{
					gameData.inputData.keyboardData.Down = event.KeyDown == KeyChange.Pressed;
				}
				if (event.KeyLeft != null && event.KeyLeft != KeyChange.Unchanged)
				{
					gameData.inputData.keyboardData.Left = event.KeyLeft == KeyChange.Pressed;
				}
				if (event.KeyRight != null && event.KeyRight != KeyChange.Unchanged)
				{
					gameData.inputData.keyboardData.Right = event.KeyRight == KeyChange.Pressed;
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
			
			moveForward += gameData.inputData.keyboardData.Up ? 1 : 0;
			moveForward += gameData.inputData.keyboardData.Down ? -1 : 0;
			moveLeft += gameData.inputData.keyboardData.Left ? 1 : 0;
			moveLeft += gameData.inputData.keyboardData.Right ? -1 : 0;			

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
				gameData.playerData.posX += moveForward * fracsec * gameData.playerData.rotX;
				gameData.playerData.posY += moveForward * fracsec * gameData.playerData.rotY;
			}
		}
		
		gameData.renderData.fovDeg = 75;
		gameData.renderData.posX = (int) (200 * gameData.playerData.posX);
		gameData.renderData.posY = (int) (200 * gameData.playerData.posY);
		gameData.renderData.rotFovForwardDeg = (int) Math.toDegrees(gameData.playerData.rotAngle);
		gameData.renderData.rotFovLeftDeg = gameData.renderData.rotFovForwardDeg + gameData.renderData.fovDeg / 2;
		gameData.renderData.rotFovRightDeg = gameData.renderData.rotFovForwardDeg - gameData.renderData.fovDeg / 2;
		
		
		
		prevTickTime = currTickTime;		
	}
	
	private void Render()
	{
		int width = 300, height = 300;
		BufferedImage bimg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
		Graphics2D g = bimg.createGraphics();
		
        g.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON); 
        	    
	    g.setFont(Font.decode("Tahoma 36 bold"));
	    g.setColor(Color.DARK_GRAY);
		g.drawRect(0, 0, width, height);


	    
	    // player
    	g.setColor(new Color(200, 50, 0));
    	g.fillArc(gameData.renderData.posX, gameData.renderData.posY, 100, 100, gameData.renderData.rotFovRightDeg, gameData.renderData.fovDeg);
 	
		eventBus.AddRenderEvent(new RenderEvent()
		{{
			renderingState = RenderingState.Active;
			image = bimg;
		}});
	}
}
