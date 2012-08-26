/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.generation.wrappers;

import java.awt.Point;

/**
 *
 * @author VOLT
 */
public class Calculations {

    public static double distanceTo(Point p, Point p2) {
        return Math.hypot(p2.x - p.x, p2.y - p.y);
    }
}
