/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dancingformoney.nodes;

import dancingformoney.DancingForMoney;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.util.Timer;

/**
 *
 * @author VOLT
 */
public class Resetter extends Node {

    private final static Timer resetTimer = new Timer(40000);
    private static dancingformoney.wrappers.Trader localLastTrader = null;

    @Override
    public boolean activate() {
        return true;
    }

    @Override
    public void execute() {
        if (!resetTimer.isRunning()) {
            DancingForMoney.lastTrader = null;
            resetTimer.reset();
            System.out.println("Resetted");
        }
        if (!localLastTrader.equals(DancingForMoney.lastTrader)) {
            localLastTrader = DancingForMoney.lastTrader;
            resetTimer.reset();
            System.out.println("Resetted");
        }
    }
}
