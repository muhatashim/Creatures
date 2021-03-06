/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.generation.wrappers;

import ai.generation.AIGeneration;
import ai.generation.utils.Calculations;
import java.awt.Point;
import java.util.Hashtable;

/**
 *
 * @author VOLT
 */
public final class Pac {

    private Point p;
    public double health = 100d;
    public double energy = 100d;
    public double size = 10d;
    public double maxSize = 50d;
    public double love = 5d;
    public Hashtable<Integer, Task> tasks = new Hashtable<>();

    public Pac(final Point p) {
        this.p = p;
    }

    public Pac(final Point p, Pac previousKnowledge) {
        this.p = p;
        this.tasks = previousKnowledge.tasks;
        this.size = previousKnowledge.size / 1.2d;
        this.maxSize = (previousKnowledge.maxSize + previousKnowledge.size / 3d);
        this.love = previousKnowledge.love + 1;
    }

    public boolean isAlive() {
        return health > 0 && energy > 0 && size <= maxSize;
    }

    public boolean makeOffspring() {
        this.love -= size - 11.7; //changing 12 to higher would make larger populations
        return health > 20 && energy > 50 && size <= maxSize / 1.3d && size >= 10 && this.love > 1;
    }

    public Point getLoc() {
        if (p.x > 500) {
            p.x = 0;
        }
        if (p.y > 500) {
            p.y = 0;
        }
        if (p.x < 0) {
            p.x = 500;
        }
        if (p.y < 0) {
            p.y = 500;
        }
        return p;
    }

    public void setLoc(Point newPoint) {
        energy -= Calculations.distanceTo(p, newPoint);
        p = newPoint;
    }

    public void update() {
        if (this.isAlive()) {
            for (Food f : AIGeneration.foods) {
                if (!f.isEaten()) {
                    Point p = f.getLoc();
                    if (Calculations.distanceTo(p, this.getLoc()) <= this.size) {
                        f.setEaten(true, this);
                        System.out.println(this.health);
                    }
                }
            }
        }
    }
}
