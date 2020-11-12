package net.runelite.client.plugins.duelarena;

import lombok.Getter;
import lombok.Setter;
import net.runelite.api.Player;

@Getter @Setter
public class Duel {

    private Player me;
    private Player opponent;

    private int playerOneStake;
    private int playerTwoStake;

    public int getTotalStake() {
        return playerOneStake + playerTwoStake;
    }

    public int calculateTax() {
        int totalStake = getTotalStake();
        if (totalStake == 0) {
            return 0;
        }

        if (totalStake < 10_000_000) {
            return (int) Math.floor(totalStake * (1-0.0025));
        } else if (totalStake < 100_000_000) {
            return (int) Math.floor(totalStake * (1-0.0050));
        } else {
            return (int) Math.floor(totalStake * (1-0.0100));
        }
    }

}
