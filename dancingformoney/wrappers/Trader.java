/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dancingformoney.wrappers;

/**
 *
 * @author VOLT
 */
public class Trader {

    private String name;
    private boolean traded = false;

    public Trader(String name) {
        this.name = name;
    }

    public void setTraded() {
        this.traded = true;
    }

    public String getName() {
        return this.name;
    }

    public boolean isTraded() {
        return this.traded;
    }
}