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
public class Main {
    
    
    public static void main(String[] args) {
        Monitor monitor = new Monitor();
        TProducer producer = new TProducer((ItfProducer)monitor);
        TConsumer consumer = new TConsumer((ItfConsumer)monitor);
        consumer.start();
        producer.start();
    }
}
