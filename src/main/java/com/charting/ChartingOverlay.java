package com.charting;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.client.ui.overlay.*;

import javax.inject.Inject;
import java.awt.*;

@Slf4j

public class ChartingOverlay extends Overlay
{

    @Inject
    private ChartingPlugin plugin;

    @Inject
    private ChartingOverlay(ChartingPlugin plugin) {
        this.plugin = plugin;


        setLayer(OverlayLayer.ABOVE_WIDGETS);
        setPosition(OverlayPosition.DYNAMIC);
    }

    @Inject
    private Client client;
    @Inject
    private ChartingConfig config;



    @Override
    public Dimension render(Graphics2D g)
    {
        if (this.config.blockMinimap()) {

            Point center = Perspective.localToMinimap(client, client.getLocalPlayer().getLocalLocation());
            int radius = 77;
            g.setColor(Color.BLACK);
            g.fillOval(center.getX() - radius, center.getY() - radius, radius * 2, radius * 2);

        }

        return null;

    }

    public ChartingOverlay() {
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
    }
}
