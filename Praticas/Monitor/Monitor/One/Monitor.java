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
public class Monitor implements ItfConsumer, ItfProducer{

    private int value = 0;
            

    @Override
    public synchronized int get() {
        while (value < 10) {
            try {
                wait();
                System.out.println("acordei");
            } catch (Exception e) {
            }
        }
        int v = value;
        value -= 10;
        return v;
        
    }
    @Override
    public synchronized void put(int increment) {
        value += increment;
        notify();
    }
    
}
