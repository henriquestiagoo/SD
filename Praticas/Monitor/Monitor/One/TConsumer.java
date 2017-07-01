/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Monitor.One;

/**
 *
 * @author omp
 */
public class TConsumer extends Thread {

    private final  ItfConsumer consumer;
    public TConsumer(ItfConsumer consumer) {
        this.consumer = consumer;
    }
    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Consumer: waiting");
                System.out.println("Consumer: " + consumer.get());
            } catch (Exception e) { }
        }
    }
}
