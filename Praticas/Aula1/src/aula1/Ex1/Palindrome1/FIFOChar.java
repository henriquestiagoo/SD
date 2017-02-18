/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aula1.Ex1.Palindrome1;

/**
 *
 * @author Utilizador
 */

public class FIFOChar {
    
    private char[] fifo = null;
    private int inPT = 0;
    private int outPT = 0;
    private boolean empty = true;
    int nMax = 0;    
    
    public FIFOChar(int n){
        fifo = new char[n];
        nMax = n;
    }   
    
   public void in (char val) {
     if ((inPT != outPT) || empty) { 
         fifo[inPT] = val;
         inPT += 1;
         inPT %= nMax;
         empty = false;
     }
   }
    
   public char out () {
     char val = '0';

     if ((outPT != inPT) || !empty) { 
         val = fifo[outPT];
         outPT += 1;
         outPT %= nMax;
         empty = (inPT == outPT);
        }
     return val;
   }
   
   public boolean isEmpty()
   {
       return empty;
   } 
}