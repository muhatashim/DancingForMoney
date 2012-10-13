/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dancingformoney.nodes;

import dancingformoney.DancingForMoney;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.methods.Calculations;
import org.powerbot.game.api.methods.Walking;

/**
 *
 * @author VOLT
 */
public class BackWalk extends Node {

    @Override
    public void execute() {
        Walking.walk(DancingForMoney.location);
        DancingForMoney.lastTrader = null;
    }

    @Override
    public boolean activate() {
        return DancingForMoney.location != null && Calculations.distanceTo(DancingForMoney.location) > 10;
    }
}
