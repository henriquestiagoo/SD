/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Monitor.Many;

import java.util.Random;

/**
 *
 * @author omp
 */
public class TProducer extends Thread {
    private final ItfProducer producer;
    private final Random random = new Random();
    

    public TProducer(ItfProducer producer) {
        this.producer = producer;
    }
    public void run() {
        try {
            while (true) {
                sleep(2000);
                int value = random.nextInt(30);
                System.out.println("PRODUCER ADDING: " + value);
                producer.put(value);
            }
        } catch (Exception e) {
        }
    }
    
    
}
