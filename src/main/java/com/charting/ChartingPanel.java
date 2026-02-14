package com.charting;

import net.runelite.client.Notifier;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;

import lombok.extern.slf4j.Slf4j;

import javax.inject.Inject;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

@Slf4j

public class ChartingPanel extends PluginPanel {

    private final ChartingPlugin plugin;
    private JLabel playerIDLabel;

    @Inject
    private Notifier notifier;

    public ChartingPanel(ChartingPlugin plugin) {
        this.plugin = plugin;
        //rebuild();
    }

    protected void rebuild() {
        removeAll();
        Font buttonFont = new Font("Runescape", Font.PLAIN, 32);

        //Main Panel
        int vGap = 5;
        int hGap = 5;
        JPanel mainPanel = new JPanel(new GridLayout(0, 1, hGap, vGap));
        mainPanel.setBorder(new EmptyBorder(vGap, hGap, vGap, hGap));
        mainPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        add(mainPanel);

        //Player ID label
        if (plugin.currentLocalAccountHash == 0 || plugin.currentLocalAccountHash == -1) {
            playerIDLabel = new JLabel("Player ID: unknown");
        }
        else {
            playerIDLabel = new JLabel("Player ID: " + plugin.currentLocalAccountHash);
        }
        playerIDLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainPanel.add(playerIDLabel);

        //Look Around! button
        JButton lookAroundButton = new JButton("look around");
        lookAroundButton.setFont(buttonFont);
        lookAroundButton.addActionListener(l -> chart());
        mainPanel.add(lookAroundButton);


    }

    private void chart() {
        log.debug("You have just charted!");
        plugin.chart();

    }

    protected void updatePlayerID() {
        this.plugin.currentLocalAccountHash = plugin.currentLocalAccountHash;
        if (this.plugin.currentLocalAccountHash == -1 || this.plugin.currentLocalAccountHash == 0) {
            playerIDLabel = new JLabel("Player ID: unavailable");
        } else {
            playerIDLabel.setText("Player ID: " + this.plugin.currentLocalAccountHash);
        }
    }
}
