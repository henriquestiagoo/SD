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
public class Monitor implements ItfConsumer, ItfProducer{

    private int value = 0;
    private final int decrement;

    public Monitor(int decrement) {
        this.decrement = decrement;
    }
    @Override
    public synchronized int get() {
        while (value < 10) {
            try {
                wait();
            } catch (Exception e) {
            }
        }
        int v = value;
        value -= decrement;
        return v;        
    }
    @Override
    public synchronized void put(int increment) {
        value += increment;
        //notify();
        notifyAll();
    }
    
}
