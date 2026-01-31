package com.chartableminimap;

import net.runelite.client.config.*;

@ConfigGroup("chartableminimap")
public interface ChartableMinimapConfig extends Config
{
	@ConfigItem(
		keyName = ChartableMinimapPlugin.CONFIG_KEY_WALKING_RADIUS,
		name = "Welcome Greeting",
		description = "Tiles revealed around your character",
		position = 5
	)
	@Range (
			min = 0,
			max = 13
	)
	default int walkingradius()
	{
		return 3;
	}
}
