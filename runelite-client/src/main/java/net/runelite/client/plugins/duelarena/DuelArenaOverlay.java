package net.runelite.client.plugins.duelarena;

import joptsimple.internal.Strings;
import net.runelite.api.Client;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.components.LayoutableRenderableEntity;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;

import javax.inject.Inject;
import java.awt.*;
import java.util.List;

public class DuelArenaOverlay extends OverlayPanel {

    private Duel duel;

    private final DuelArenaHelperPlugin plugin;
    private final Client client;

    @Inject
    public DuelArenaOverlay(DuelArenaHelperPlugin plugin, Client client) {
        super(plugin);
        this.plugin = plugin;
        this.client = client;
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        List<LayoutableRenderableEntity> children = panelComponent.getChildren();
        children.clear();

        String opponentName = plugin.getOpponentName();
        if (!Strings.isNullOrEmpty(opponentName)) {
            children.add(TitleComponent.builder().text("Duel").build());
            children.add(LineComponent.builder().left("Opponent").rightColor(Color.RED).right(opponentName).build());
        }

        return super.render(graphics);
    }

}
