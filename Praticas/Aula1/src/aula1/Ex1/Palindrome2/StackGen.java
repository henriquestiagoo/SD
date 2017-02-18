/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aula1.Ex1.Palindrome2;

/**
 *
 * @author Tiago Henriques
 */
public class StackGen {
    
   private int nMax = 0;                 // tamanho da memória
   private int stackPnt = 0;             // stack pointer
   private Object [] stack = null;         // área de armazenamento

  /**
   *  Construtor de variáveis
     * @param nElem
   */

   public StackGen (int nElem)
   {
     stack = new Object [nElem];
     nMax = nElem;
   }

  /**
   *  stack push -- escrita de um valor
     * @param val
   */

   public void push (Object val)
   {
     if (stackPnt != nMax)
        { stack[stackPnt] = val;
          stackPnt += 1;
        }
   }

  /**
   *  stack pop -- leitura de um valor
     * @return 
   */

   public Object pop ()
   {
     Object val = null;

     if (stackPnt != 0)
        { 
          stackPnt -= 1;
          val = stack[stackPnt];
        }
     return val;
   }
    
}

