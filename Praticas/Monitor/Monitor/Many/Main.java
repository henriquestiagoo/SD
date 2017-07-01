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
public class Main {
    
    
    public static void main(String[] args) {
        TConsumer[] consumer = new TConsumer[5];
        int decrement = 10;
        Monitor monitor = new Monitor(decrement);
        for (int i=0; i<consumer.length; i++) {
            consumer[i] = new TConsumer((ItfConsumer)monitor, i+1, decrement);
            consumer[i].setName("Consumer " + (i+1));
            consumer[i].start();
        }
        TProducer producer = new TProducer((ItfProducer)monitor);
        producer.setName("Producer");
        producer.start();
        
    }
}
