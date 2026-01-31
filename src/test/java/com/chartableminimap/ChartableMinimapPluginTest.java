package com.chartableminimap;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class ChartableMinimapPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(ChartableMinimapPlugin.class);
		RuneLite.main(args);
	}
}