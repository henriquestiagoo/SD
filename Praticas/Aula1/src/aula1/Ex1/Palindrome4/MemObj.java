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
public abstract class MemObj {
    
    protected Object[] memObj = null;
    protected int nMax = 0;
    
    protected MemObj(int n){
        memObj = new Object[n];
        nMax = n;
    }
    
    protected abstract void write(Object obj) throws MemException;
    
    protected abstract Object read() throws MemException;
    
}

