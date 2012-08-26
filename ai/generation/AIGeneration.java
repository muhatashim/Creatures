/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ai.generation;

import ai.generation.utils.RetardedMethods;
import ai.generation.utils.Timer;
import ai.generation.wrappers.Food;
import ai.generation.wrappers.Pac;
import ai.generation.wrappers.Task;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
public class AIGeneration extends RetardedMethods {
    
    public static Paint paint;
    
    public static AIGeneration get() {
        return AIGeneration.instance;
    }
    public JFrame frame = new JFrame();
    public static ArrayList<Pac> pacs = new ArrayList<>();
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
        resetFood();
        final Timer t = new Timer(10000);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (!t.isRunning()) {
                        resetFood();
                        t.reset();
                    }
                }
            }
        };
        Thread th = new Thread(r);
        th.start();
        for (int i = 0; i < 10; i++) {
            pacs.add(new Pac(new Point(random(0, 500), random(0, 500))));
        }
    }
    
    private static void resetFood() {
        for (int i = 0; i < foods.length; i++) {
            foods[i] = new Food(new Point(random(0, 500), random(0, 500)));
        }
    }
    int births = 0;
    private static int appendY = 10;
    private static int appendX = 510;
    
    public static void appendInfo(String text, Graphics g) {
        g.setColor(Color.BLACK);
        g.drawString(text, appendX, appendY);
        appendY += 15;
    }
    
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
            Pac pac = pacs.get(i);
            //System.out.println("Ded1 " + pac.health + ", " + pac.energy + ", " + pac.love + ", " + pac.size + " (" + pac.maxSize + ")");
            if (pac.isAlive()) {
                Object[] ints = pac.tasks.keySet().toArray();
                if (ints.length > 0) {
                    Arrays.sort(ints);
                    int ind = (int) ints[ints.length - 1];
                    //System.out.println("Brain size " + ints.length + " { " + Arrays.toString(ints) + " }");
                    Task task = pac.tasks.get(ind);
                    
                    task.start(pac);
                    //System.out.println("Started ");
                    //System.out.println("Started previous knowledge task");
                } else {
                    Task hardCodeStart = new Task() {
                        @Override
                        public int run(Pac pac) {
                            if (pac.isAlive()) {
                                double e = pac.energy;
                                //System.out.println("ee " + e);
                                pac.setLoc(randomize(pac.getLoc(), 10));
                                pac.update();
                                return (int) pac.health;
                            } else {
                                //System.out.println("Ded2 " + pac.health + ", " + pac.energy + ", " + pac.love + ", " + pac.size + " (" + pac.maxSize + ")");
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
            //System.out.println("Ded3 " + pac.health + ", " + pac.energy + ", " + pac.love + ", " + pac.size + " (" + pac.maxSize + ")");
            
        }
        g.setColor(Color.black);
        g.setFont(new Font("Tahoma", 0, 15));
        
        float avgLove = 0;
        int population = 0;
        for (Pac pac : pacs) {
            avgLove += pac.love;
            if (pac.isAlive()) {
                population++;
            }
        }
        avgLove = avgLove / pacs.size();
        
        appendInfo("Eaten: " + eaten, g);
        appendInfo("New borns: " + births, g);
        appendInfo("Average love: " + avgLove, g);
        appendInfo("Births: " + pacs.size(), g);
        appendInfo("Population: " + population, g);
        System.gc();
        
        appendY = 520;
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(AIGeneration.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
}
