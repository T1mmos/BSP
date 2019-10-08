package gent.timdemey.bsp.hud;

import java.awt.Point;

public class HudEvent
{
	public Point MouseLocation;
	
	public KeyChange KeyUp = KeyChange.Unchanged;
	public KeyChange KeyDown= KeyChange.Unchanged;
	public KeyChange KeyLeft= KeyChange.Unchanged;
	public KeyChange KeyRight= KeyChange.Unchanged;
}
