package com.charting;

import net.runelite.client.config.*;

@ConfigGroup("charting")
public interface ChartingConfig extends Config
{
	@ConfigSection(
			name = "Charting",
			description = "Configure your exploration",
			position = 0
	)
	String exploration = "chartingsettings";

	@ConfigItem(
		keyName = ChartingPlugin.CONFIG_KEY_CHARTING_RADIUS,
		name = "Charting Radius",
		description = "Tiles revealed around your character",
		position = 5,
		section = exploration
	)
	@Range (
			min = 0,
			max = 13
	)
	default int walkingradius()
	{
		return 3;
	}


	@ConfigItem(
			keyName = ChartingPlugin.CONFIG_KEY_EXPLORE_WITH_EMOTE,
			name = "Chart with Emote",
			description = "When enabled, you can use the Explore emote to trigger new charting event",
			position = 3,
			section = exploration
	)
	default boolean exploreWithEmote() {
		return true;
	}

	@ConfigSection(
			name = "Overlays",
			description = "Configure your blackout settings",
			position = 3

	)
	String overlays = "overlaysettings";

	@ConfigItem(
			keyName = ChartingPlugin.CONFIG_KEY_BLOCK_MINIMAP,
			name = "Block Minimap",
			description = "When enabled, the minimap will be obscured",
			position = 1,
			section = overlays
	)
	default boolean blockMinimap() {
		return true;
	}

	@ConfigItem(
			keyName = ChartingPlugin.CONFIG_KEY_BLOCK_WORLDMAP,
			name = "Block World Map",
			description = "When enabled, the world map will be obscured",
			position = 2,
			section = overlays
	)
	default boolean blockWorldMap() {
		return true;
	}

	@ConfigItem(
			keyName = ChartingPlugin.CONFIG_KEY_BLOCK_INSETMAP,
			name = "Block Inset Map",
			description = "When enabled, the inset world map will be obscured",
			position = 3,
			section = overlays
	)
	default boolean blockInsetMap() {
		return true;
	}




	@ConfigSection(
			name = "Logs",
			description = "Configure your logs",
			position = 7
	)
	String log = "logsettings";

	@ConfigItem(
			keyName = ChartingPlugin.CONFIG_KEY_LOG_ENABLED,
			name = "Log",
			description = "When enabled or disabled, write to a local log",
			position = 1,
			section = log
	)
	default boolean logEnabled() {
		return true;
	}

}
