/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aula1.Ex3;

/**
 *
 * @author Utilizador
 */
public class Operand {
    
    protected int iop1;

  /**
   *   Representação <code>inteira</code> do <em>operando 2</em>.
   *
   *    @serialField iop2
   */

   protected int iop2;

  /**
   *   Representação <code>inteira</code> da <em>propagação de entrada</em>.
   *
   *    @serialField ipvi
   */

   protected int ipvi;

  /**
   *   Representação <code>tipo caracter</code> do <em>operando 1</em>.
   *
   *    @serialField op1
   */

   protected char op1;

  /**
   *   Representação <code>tipo caracter</code> do <em>operando 2</em>.
   *
   *    @serialField op2
   */

   protected char op2;

  /**
   *   Representação <code>tipo caracter</code> da <em>propagação de entrada</em>.
   *
   *    @serialField pvi
   */

   protected char pvi;

  /**
   *   Inicialização de variável (deve ser sempre feita usando este construtor).
   *
   *      @param op1 operando 1
   *      @param op2 operando 2
   *      @param pvi propagação de entrada
   */

   public Operand (char op1, char op2, char pvi)
   {
      this.op1 = op1;
      this.op2 = op2;
      this.pvi = pvi;
      iop1 = Character.digit (op1, 10);
      iop2 = Character.digit (op2, 10);
      ipvi = Character.digit (pvi, 10);
   }

   /*
   public int getV1(){ 
      return val1; 
   }
    
     public int getV2(){ 
       return val2; 
    }
    */
}
