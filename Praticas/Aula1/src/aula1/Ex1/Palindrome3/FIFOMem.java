/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aula1.Ex1.Palindrome3;

/**
 *
 * @author Tiago Henriques
 */
public class FIFOMem extends MemObj {
    
    private int inPT = 0;
    private int outPT = 0;
    private boolean empty = true;

    public FIFOMem(int n) {
        super(n);
    }

    @Override
    protected void write(Object obj) {
        if ((inPT != outPT) || empty) { 
            memObj[inPT] = obj;
            inPT += 1;
            inPT %= nMax;
            empty = false;
        }
    }

    @Override
    protected Object read() {
        Object val = null;

        if ((outPT != inPT) || !empty) { 
            val = memObj[outPT];
            outPT += 1;
            outPT %= nMax;
            empty = (inPT == outPT);
           }
        return val;
    }
    
    public boolean isEmpty() {
       return empty;
    } 
    
}
