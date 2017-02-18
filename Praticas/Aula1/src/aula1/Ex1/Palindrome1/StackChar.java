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
public class StackChar {
    
   private int nMax = 0;                 // tamanho da memória
   private int stackPnt = 0;             // stack pointer
   private char [] stack = null;         // área de armazenamento

  /**
   *  Construtor de variáveis
     * @param nElem
   */

   public StackChar (int nElem)
   {
     stack = new char [nElem];
     nMax = nElem;
   }

  /**
   *  stack push -- escrita de um valor
     * @param val
   */

   public void push (char val)
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

   public char pop ()
   {
     char val = '0';

     if (stackPnt != 0)
        { 
          stackPnt -= 1;
          val = stack[stackPnt];
        }
     return val;
   }
    
}
