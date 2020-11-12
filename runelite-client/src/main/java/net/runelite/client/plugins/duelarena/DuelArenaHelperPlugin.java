package net.runelite.client.plugins.duelarena;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.VarClientStr;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetID;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.Optional;

@Slf4j
@PluginDescriptor(
        name = "Duel Arena Helper",
        description = "Utilities to help with tracking duel arena stakes",
        tags = {"duel","arena","stake"}
)
public class DuelArenaHelperPlugin extends Plugin {

    @Inject
    private Client client;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private DuelArenaOverlay overlay;

    @Getter
    private String opponentName;

    @Override
    protected void startUp() throws Exception {
        overlayManager.add(overlay);
        log.info("Started duel arena plugin!");
    }

    @Override
    protected void shutDown() throws Exception {
        overlayManager.remove(overlay);
        log.info("Stopped duel arena plugin.");
    }

    @Subscribe
    public void onGameTick(GameTick tick)
    {
        Optional<DuelScreen> currentDuelScreen = getCurrentDuelScreen();
        currentDuelScreen.ifPresent(duelScreen -> {
            if (duelScreen == DuelScreen.RESULT)  {
                // The duel has finished, and we're showing the results screen
                // do calcs.
            } else {
                // We're setting up duel rules before the stake, so read these values before the duel starts.
                if (duelScreen == DuelScreen.ONE) {
                    this.opponentName = client.getVar(VarClientStr.OPPONENT_NAME);
                }
            }
        });
    }

    private Optional<DuelScreen> getCurrentDuelScreen() {
        return Arrays.stream(DuelScreen.values()).filter(s-> {
            Widget w = client.getWidget(s.widget);
            return w != null && !w.isHidden();
        }).findFirst();
    }

    enum DuelScreen {
        ONE(WidgetInfo.DUEL_SCREEN_ONE),
        TWO(WidgetInfo.DUEL_SCREEN_TWO),
        THREE(WidgetInfo.DUEL_SCREEN_THREE),
        RESULT(WidgetInfo.DUEL_SCREEN_RESULT);

        private final WidgetInfo widget;

        DuelScreen(WidgetInfo widget) {
            this.widget = widget;
        }
    }

}
