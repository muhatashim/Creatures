/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.generation;

import ai.generation.wrappers.Food;
import ai.generation.wrappers.Pac;
import ai.generation.wrappers.Task;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author VOLT
 */
public class AIGeneration {

    public static Paint paint;

    public static AIGeneration get() {
        return AIGeneration.instance;
    }
    public JFrame frame = new JFrame();
    public static final ArrayList<Pac> pacs = new ArrayList<>();
    public static final Food[] foods = new Food[100];
    /**
     * @param args the command line arguments
     */
    private static AIGeneration instance;

    public static void main(String[] args) {
        try {
            instance = new AIGeneration();
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    paint = new Paint();
                    paint.setSize(500, 500);
                    AIGeneration.get().frame.setSize(500, 500);
                    AIGeneration.get().frame.add(paint);
                    AIGeneration.get().frame.setVisible(true);
                    AIGeneration.get().frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
            });

        } catch (Exception ex) {
            Logger.getLogger(AIGeneration.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (int i = 0; i < foods.length; i++) {
            foods[i] = new Food(new Point(random(0, 500), random(0, 500)));
        }
        for (int i = 0; i < 100; i++) {
            pacs.add(new Pac(new Point(random(0, 500), random(0, 500))));
        }
    }

    public static int random(final int min, final int max) {
        Random r = new Random();
        if (max < min) {
            return max + r.nextInt(min - max);
        }
        return min + (max == min ? 0 : r.nextInt(max - min));
    }

    public static Point randomize(Point p, int radius) {
        if (p.x - radius <= 0 || p.y - radius <= 0) {
            return p;
        }
        return new Point(random(p.x - radius, p.x + radius + 1), random(p.y - radius, p.y + radius + 1));
    }
    int births = 0;

    public void run(Graphics g) {
        g.setColor(Color.green);
        int eaten = 0;
        for (Food f : foods) {
            if (!f.isEaten()) {
                Point p = f.getLoc();
                g.fillOval(p.x, p.y, 10, 10);
            } else {
                eaten++;
            }
        }
        g.setColor(Color.red);
        for (Pac pac : pacs) {
            if (pac.isAlive()) {
                Point p = pac.getLoc();
                g.fillOval(p.x, p.y, (int) pac.size, (int) pac.size);
            }
        }

        int fi = pacs.size();
        for (int i = 0; i < fi; i++) {
            final Pac pac = pacs.get(i);
            if (pac.isAlive()) {
                Object[] ints = pac.tasks.keySet().toArray();
                if (ints.length > 0) {
                    //System.out.println(ints.length);
                    Arrays.sort(ints);
                    int ind = (int) ints[ints.length - 1];
                    Task task = pac.tasks.get(ind);
                    task.start(pac);
                } else {
                    Task hardCodeStart = new Task() {
                        @Override
                        public int run() {
                            if (pac.isAlive()) {
                                int e = (int) pac.energy;
                                System.out.println("ee " + e);
                                pac.setLoc(randomize(pac.getLoc(), e));
                                pac.update();
                                return (int) pac.health;
                            } else {
                                return 0;
                            }
                        }
                    };
                    hardCodeStart.start(pac);
                }
                if (pac.makeOffspring()) {
                    pacs.add(new Pac(pac.getLoc(), pac));
                    births++;
                }
            }
        }
        g.setColor(Color.black);
        g.setFont(new Font("Tahoma", 0, 15));
        g.drawString("Eaten: " + eaten, 520, 20);
        g.drawString("New borns: " + births, 520, 35);

//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(AIGeneration.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
}
