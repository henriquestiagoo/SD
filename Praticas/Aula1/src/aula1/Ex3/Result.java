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
public class Result {
    
    protected int ires;

  /**
   *   Representação <code>inteira</code> da <em>propagação de saída</em>.
   *
   *    @serialField ipvo
   */

   protected int ipvo;

  /**
   *   Representação <code>tipo caracter</code> do <em>resultado</em>.
   *
   *    @serialField res
   */

   protected char res;

  /**
   *   Representação <code>tipo caracter</code> da <em>propagação de saída</em>.
   *
   *    @serialField pvo
   */

   protected char pvo;

  /**
   *   Inicialização de variável (deve ser sempre feita usando este construtor).
   *
   *      @param ires resultado
   *      @param ipvo propagação de saída
   */

   public Result (int ires, int ipvo)
   {
      this.ires = ires;
      this.ipvo = ipvo;
      res = Character.forDigit (ires, 10);
      pvo = Character.forDigit (ipvo, 10);
   }

   /*
   public int getResult(){
       return result;
   }
   
   public int getCarry(){
       return carry;
   }
   */
}
