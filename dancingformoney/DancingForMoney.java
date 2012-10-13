/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dancingformoney;

import dancingformoney.nodes.BackWalk;
import dancingformoney.nodes.Resetter;
import dancingformoney.nodes.Trader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.powerbot.concurrent.strategy.Condition;
import org.powerbot.core.event.events.MessageEvent;
import org.powerbot.core.event.listeners.MessageListener;
import org.powerbot.core.script.ActiveScript;
import org.powerbot.core.script.job.state.Node;
import org.powerbot.game.api.Manifest;
import org.powerbot.game.api.methods.Tabs;
import org.powerbot.game.api.methods.Widgets;
import org.powerbot.game.api.methods.input.Keyboard;
import org.powerbot.game.api.methods.interactive.Players;
import org.powerbot.game.api.util.Time;
import org.powerbot.game.api.util.Timer;
import org.powerbot.game.api.wrappers.Tile;
import org.powerbot.game.api.wrappers.widget.WidgetChild;

/**
 *
 * @author VOLT
 */
@Manifest(authors = "ExKALLEBur", name = "Dancing for money", description = "Asks people for money.", version = 0.02)
public class DancingForMoney extends ActiveScript implements MessageListener {

    private static final List<Node> nodes = new ArrayList<>();

    @Override
    public void onStart() {
        Trader trader = new Trader();
        Spammer spammer = new Spammer();
        BackWalk backWalk = new BackWalk();

        nodes.add(trader);
        nodes.add(spammer);
        nodes.add(backWalk);
        nodes.add(new Resetter());
    }
    public static Tile location = null;
    public static dancingformoney.wrappers.Trader lastTrader = null;

    @Override
    public int loop() {
        synchronized (nodes) {
            for (Node node : nodes) {
                if (node.activate()) {
                    node.execute();
                }
            }
        }
        return 1;
    }


    @Override
    public void messageReceived(MessageEvent me) {
        if (me.getId() == 100) {
            String sender = me.getSender().replaceAll("\\W", " ");
            lastTrader = new dancingformoney.wrappers.Trader(sender);
            System.out.println(sender);
        }
    }

    public static boolean waitFor(Condition cond, int maxWait) {
        Timer waitTimer = new Timer(maxWait);
        while (!cond.validate() && waitTimer.isRunning()) {
            Time.sleep(100);
        }
        return waitTimer.isRunning();
    }

 

    private static class Data {

        final static int DANCE_EMOTES[] = {10, 12, 13, 14, 15};
    }

    private static class Spammer extends Node {

        @Override
        public void execute() {
            if (location == null) {
                location = Players.getLocal().getLocation();
            }

            Keyboard.sendText("Dancing for money!", true);
            if (!Tabs.EMOTES.isOpen()) {
                Tabs.EMOTES.open();
            }
            WidgetChild emote = Widgets.get(590, 8).getChild(Data.DANCE_EMOTES[new Random().nextInt(Data.DANCE_EMOTES.length)]);
            emote.click(true);
        }

        @Override
        public boolean activate() {
            return lastTrader == null || lastTrader.isTraded();
        }
    }
}
