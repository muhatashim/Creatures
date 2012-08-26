/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.generation.utils;

import java.awt.Point;
import java.util.Random;

/**
 *
 * @author VOLT
 */
public class RetardedMethods {

    public static int random(final int min, final int max) {
        Random r = new Random();
        if (max < min) {
            return max + r.nextInt(min - max);
        }
        return min + (max == min ? 0 : r.nextInt(max - min));
    }

    public static Point randomize(Point p, int radius) {
        return new Point(random(p.x - radius, p.x + radius + 1), random(p.y - radius, p.y + radius + 1));
    }
}
