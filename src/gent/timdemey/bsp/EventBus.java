package gent.timdemey.bsp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CopyOnWriteArrayList;

import gent.timdemey.bsp.hud.HudEvent;
import gent.timdemey.bsp.rendering.IRenderer;
import gent.timdemey.bsp.rendering.RenderEvent;

public class EventBus
{
	private final BlockingQueue<HudEvent> hudEvents;
	//private final BlockingQueue<RenderEvent> renderEvents;
	
	private final List<IRenderer> renderers;

	public EventBus()
	{
		this.hudEvents = new ArrayBlockingQueue<>(10);
		//this.renderEvents = new ArrayBlockingQueue<>(10);
		this.renderers = new CopyOnWriteArrayList<IRenderer>();
	}
	
	public void AddHudEvent(HudEvent event)
	{
		hudEvents.add(event);
	}
	
	public List<HudEvent> GetHudEvents()
	{
		List<HudEvent> list = new ArrayList<>();
		hudEvents.drainTo(list);
		return list;
	}
	
	public void AddRenderer (IRenderer renderer)
	{
		renderers.add(renderer);
	}
	
	public void AddRenderEvent(RenderEvent event)
	{
		for (IRenderer renderer : renderers)
		{
			renderer.Render(event);
		}
	}
}
