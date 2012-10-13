/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dancingformoney.nodes;

import dancingformoney.DancingForMoney;
import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Walking;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Filter;
import org.powerbot.game.api.wrappers.interactive.Player;

/**
 *
 * @author VOLT
 */
public class Trader extends Node {

    @Override
    public void execute() {
        if (!Widgets.get(334).validate()) {
            if (!Widgets.get(335).validate()) {
                Player player = Players.getNearest(new Filter<Player>() {
                    @Override
                    public boolean accept(Player t) {
                        return t.getName().replaceAll("\\W", " ").equals(DancingForMoney.lastTrader.getName());
                    }
                });
                if (player != null) {
                    if (player.isOnScreen()) {
                        player.interact("Trade");
                        DancingForMoney.waitFor(new Condition() {
                            @Override
                            public boolean validate() {
                                return Widgets.get(335).validate();
                            }
                        }, 5000);
                    } else {
                        Walking.walk(player);
                    }
                } else {
                    DancingForMoney.lastTrader = null;
                    System.out.println("Resetted");
                }
            } else {
                if (!Widgets.get(335, 49).getText().contains("No net")) {
                    Widgets.get(335, 19).click(true);
                }
            }
        } else {
            if (Widgets.get(334, 36).click(true)) {
                DancingForMoney.lastTrader.setTraded();
            }
        }
    }

    @Override
    public boolean activate() {
        return ((DancingForMoney.lastTrader != null && !DancingForMoney.lastTrader.isTraded())
                || Widgets.get(334).validate() || Widgets.get(335).validate());
    }
}
