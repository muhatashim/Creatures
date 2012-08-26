/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.generation.wrappers;

import ai.generation.AIGeneration;

/**
 *
 * @author VOLT
 */
public abstract class Task {

    public abstract int run();

    public void start(Pac pac) {
        pac.tasks.put(this.run(), this);
    }
}
