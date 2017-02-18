/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aula1.Ex1.Palindrome4;

/**
 *
 * @author Tiago Henriques
 */
public class StackMem extends MemObj {
    
    private int stackPnt = 0;             // stack pointer

    public StackMem(int n) {
        super(n);
    }

    @Override
    protected void write(Object obj) throws MemException {
        if (stackPnt != nMax) { 
            memObj[stackPnt] = obj;
            stackPnt += 1;
        } else
            throw new MemException("Stack full!!");
    }

    @Override
    protected Object read() throws MemException {
        Object val = null;

        if (stackPnt != 0){ 
             stackPnt -= 1;
             val = memObj[stackPnt];
        } else
            throw new MemException("Stack empty!!");
        return val;
    }
    
}
