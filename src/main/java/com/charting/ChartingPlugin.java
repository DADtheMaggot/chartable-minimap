package com.charting;

import com.google.inject.Provides;
import javax.inject.Inject;
import javax.swing.*;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.Player;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.AccountHashChanged;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.gameval.AnimationID;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.ui.overlay.OverlayManager;
import net.runelite.client.util.ImageUtil;

import static net.runelite.client.RuneLite.RUNELITE_DIR;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;


@PluginDescriptor(
	name = "Charting",
	description = "A plugin that blocks your map so you can uncover it as you explore Gielinor.",
		tags = {"chart", "charting", "chartable", "map", "minimap", "FoW", "Fog of War", "Explore", "sextant", "spyglass"},
		enabledByDefault = false
)
@Slf4j

public class ChartingPlugin extends Plugin {

	// Initialize Image buffers and config keys

	static final String CONFIG_KEY_CHARTING_RADIUS = "walkingRadius";
	static final String CONFIG_KEY_EXPLORE_WITH_EMOTE = "exploreWithEmote";
	static final String CONFIG_KEY_LOG_ENABLED = "logSettings";

	static final String CONFIG_KEY_BLOCK_MINIMAP = "blockMinimap";
	static final String CONFIG_KEY_BLOCK_WORLDMAP = "blockWorldMap";
	static final String CONFIG_KEY_BLOCK_INSETMAP = "blockInsetMap";

	protected final File CHARTING_DIR = new File(RUNELITE_DIR.toString(), "charting");

	// Initialize Map Constants
	static final int CHART_WIDTH = 2944;
	static final int CHART_HEIGHT = 2048;
	static final int CHART_OFFSET_X = 1024; //TODO verify these values
	static final int CHART_OFFSET_Y = 2112;

	boolean[][] chart = new boolean[CHART_HEIGHT][CHART_WIDTH];

	// Initialize Variables
	private Widget worldMapWidget;
	private Player player;
	private WorldPoint playerPosition;
	protected long currentLocalAccountHash;

	protected ChartingPanel panel;

	@Inject
	private Client client;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private ChartingConfig config;

	@Inject
	private ChartingOverlay minimapOverlay;

	@Inject
	private ClientToolbar clientToolbar;


	private NavigationButton navigationButton;

	@Override
	protected void startUp() throws Exception
	{
		log.debug("\n~~~~~~~~~~CM is in Startup~~~~~~~~~~\n");

		log.debug("CM is about to instantiate a new panel and rebuild");
		panel = new ChartingPanel(this);
		panel.rebuild();

		final BufferedImage icon = ImageUtil.loadImageResource(getClass(), "/charting_icon.png");

		log.debug("CM is about to build the Navigation button");
		navigationButton = NavigationButton.builder()
				.tooltip("Charting")
				.icon(icon)
				.priority(2)
				.panel(panel)
				.build();
		log.debug("CM Navigation Button Built");

		clientToolbar.addNavigation(navigationButton);
		log.debug("CM Navigation Button added to toolbar");

		log.debug("CM is about to overlay the minimap");
		overlayManager.add(minimapOverlay);



		log.debug("\n\n  ' *  ,  Charting Minimap started! *    , \n" +
				  " ~ ~ ~ ~  C H A R T   O N   B A B Y  ~ ~ ~ ~ \n\n");
	}

	@Override
	protected void shutDown() throws Exception
	{


		clientToolbar.removeNavigation(navigationButton);
		log.debug("CM Navigation Button removed from toolbar");

		log.debug("Charting stopped :/\n");
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Charting says " + config.walkingradius(), null);
		}
	}

	@Subscribe
	public void onAccountHashChanged(AccountHashChanged event) {
		currentLocalAccountHash = client.getAccountHash();
		SwingUtilities.invokeLater(panel::updatePlayerID);
	}


	@Subscribe
	public void onAnimationChanged(AnimationChanged event) {
		if (event.getActor() != client.getLocalPlayer()) {
			return;
		}

		int animation = client.getLocalPlayer().getAnimation();
		if (this.config.exploreWithEmote()) {
			log.debug("Explore with emote is enabled.");
		} else {
			log.debug("Explore with emote is not enabled");
		}
		if (this.config.exploreWithEmote() && (animation == AnimationID.EMOTE_EXPLORE || animation == AnimationID.EMOTE_EXPLORE_LOOP)) {
			chart(client.getLocalPlayer());
		}
	}

	private void updateWorldMap() {
		worldMapWidget = client.getWidget(35913750);
		if(worldMapWidget != null) {
			log.debug("World map fetched, woohoo!");
		}
	}

	void chart() {
		System.out.println("You take a look around. ");
		log.debug("You clicked on the 'look around' button. ");
		if (client.getGameState() != GameState.LOGGED_IN) {
			System.out.println("You don't see much, seeing as how your character isn't logged in. ");
		}
		else {
			if (playerPosition != null && playerPosition.getPlane() == 0) {
				System.out.println("You take in your surroundings. ");
				System.out.println("You appear to be in the overworld, your location is  " + playerPosition.toString());
				System.out.println("You'd better write that down!");
				log.debug("");
			}
		}


	}

	private void chart(Player currentPlayer) {
		WorldPoint currentPosition = currentPlayer.getWorldLocation();
		if (currentPosition.getPlane() != 0) {
			log.debug("Charting is only supported in the overworld. Anyway, keep on chartin'!");
			return;
		}

		System.out.println("You have charted!");
		System.out.println(currentPosition.toString()+"\n\n\n\n\n\n\n\n\n\n");




		log.debug("You have charted!");
	}



	@Provides
    ChartingConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(ChartingConfig.class);
	}
}
