package com.chartableminimap;

import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class ChartableMinimapPanel extends PluginPanel {

    private final ChartableMinimapPlugin plugin;


    public ChartableMinimapPanel(ChartableMinimapPlugin plugin) {
        this.plugin = plugin;
        //rebuild();
    }

    protected void rebuild() {
        removeAll();

        //Main Panel
        int vGap = 5;
        int hGap = 5;
        JPanel mainPanel = new JPanel(new GridLayout(0, 1, hGap, vGap));
        mainPanel.setBorder(new EmptyBorder(vGap, hGap, vGap, hGap));
        mainPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        add(mainPanel);


    }

}
