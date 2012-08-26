/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.generation.wrappers;

/**
 *
 * @author VOLT
 */
public abstract class Task {

    public abstract int run(Pac pac);
    
    public void start(Pac pac) {
        pac.tasks.put(this.run(pac), this);
    }
}
