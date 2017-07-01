/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Monitor.Many;

/**
 *
 * @author omp
 */
public class TConsumer extends Thread {

    private final  ItfConsumer consumer;
    private final int id;
    private final int decrement;
    
    public TConsumer(ItfConsumer consumer, int id, int decrement) {
        this.consumer = consumer;
        this.id = id;
        this.decrement = decrement;
    }
    @Override
    public void run() {
        while (true) {
            try {
                System.out.printf("Consumer[%d]: waiting\n", id);
                int value = consumer.get();
                System.out.printf("Consumer[%d]: %d - %d\n", id, value, (value-decrement));
            } catch (Exception e) { }
        }
    }
}
