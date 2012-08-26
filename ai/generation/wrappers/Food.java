/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.generation.wrappers;

import ai.generation.AIGeneration;
import java.awt.Point;

/**
 *
 * @author VOLT
 */
public class Food {

    private final Point p;
    private boolean eaten = false;

    public Food(final Point p) {
        this.p = p;
    }

    public Point getLoc() {
        return p;
    }

    public boolean isEaten() {
        return eaten;
    }

    public void setEaten(boolean yes, Pac pac) {
        eaten = yes;
        if (yes) {
            pac.health++;
            pac.energy += pac.size;
            pac.size *= 2;
        }
    }
}
