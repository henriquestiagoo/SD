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
public class BasicOper {
    
    private BasicOper() {}
    
    public static String add (String op1, String op2) throws NumberFormatException {
        
      char [] oop1,                               // operando 1 como um array de caracteres
              oop2,                               // operando 2 como um array de caracteres
              osum;                               // soma como um array de caracteres

      /* validação dos parâmetros */

      if (op1.length () == 0)
         throw new NumberFormatException ("O operando 1 é representado por um string nulo!");
      if (op2.length () == 0)
         throw new NumberFormatException ("O operando 2 é representado por um string nulo!");

      oop1 = op1.toCharArray ();
      oop2 = op2.toCharArray ();

      for (int i = 0; i < oop1.length; i++)
        if (!Character.isDigit (oop1[i]))
           throw new NumberFormatException ("O operando 1 não é um número decimal!");
      for (int i = 0; i < oop2.length; i++)
        if (!Character.isDigit (oop2[i]))
           throw new NumberFormatException ("O operando 2 não é um número decimal!");

      /* realização da operação */
      osum = internalAdd (normNum (oop1), normNum (oop2));
      return (new String (normNum (osum)));
    } 
    
    public static String mult (String op1, String op2) throws NumberFormatException {
      char [] oop1,                               // operando 1 como um array de caracteres
              oop2,                               // operando 2 como um array de caracteres
              oprod;                              // produto como um array de caracteres

      /* validação dos parâmetros */

      if (op1.length () == 0)
         throw new NumberFormatException ("O operando 1 é representado por um string nulo!");
      if (op2.length () == 0)
         throw new NumberFormatException ("O operando 2 é representado por um string nulo!");

      oop1 = op1.toCharArray ();
      oop2 = op2.toCharArray ();

      for (int i = 0; i < oop1.length; i++)
        if (!Character.isDigit (oop1[i]))
           throw new NumberFormatException ("O operando 1 não é um número decimal!");
      for (int i = 0; i < oop2.length; i++)
        if (!Character.isDigit (oop2[i]))
           throw new NumberFormatException ("O operando 2 não é um número decimal!");

      /* realização da operação */

      oprod = internalMult (normNum (oop1), normNum (oop2));
      return (new String (normNum (oprod)));
    }

  /**
   *   Operação de normalização de um número (extracção dos zeros à esquerda).
   *
   *   @param num número a normalizar
   *
   *   @return número normalizado
   */

   private static char [] normNum (char [] num)
   {
      char [] numNorm;                           // número normalizado
      int nZeroPos;                              // posição do primeiro algarismo diferente de zero

      /* determinação da posição do primeiro algarismo diferente de zero */

      nZeroPos = 0;
      while ((nZeroPos < num.length-1) && (Character.digit (num[nZeroPos], 10) == 0))
      nZeroPos += 1;

      /* reformatação do array */

      if (nZeroPos == 0)
         numNorm = num;
         else { numNorm = new char[num.length-nZeroPos];
                for (int in = 0, ii = nZeroPos; in < num.length-nZeroPos; in++, ii++)
                  numNorm[in] = num[ii];
              }

      return (numNorm);
   }

  /**
   *   Operação de adição de dois números.
   *
   *   @param op1 operando1
   *   @param op2 operando2
   *
   *   @return soma
   */

   private static char [] internalAdd (char [] op1, char [] op2)
   {
      /* determinação do número de algarismos da soma */

      int sumSize = (op1.length >= op2.length) ? op1.length + 1 : op2.length + 1;

      /* normalização dos operandos e do resultado */

      char [] top1 = op1, top2 = op2,                        // operandos
              sum = new char[sumSize];           // soma


      /* realização da operação */

      Operand top;                               // representação de algarismos dos operandos
      Result tresu = null;                       // representação de algarismos do resultado
      char pv;                                   // propagação do valor

      for (int i = 0, i1 = top1.length-i-1, i2 = top2.length-i-1, is = sum.length-i-1;
           i < top2.length; i++, i1--, i2--, is--)
      { pv = (i == 0) ? '0' : tresu.pvo;
        top = new Operand (top1[i1], top2[i2], pv);
        tresu = DigitOper.add (top);
        sum[is] = tresu.res;
      }
      for (int i = top2.length, i1 = top1.length-i-1, is = sum.length-i-1;
           i < top1.length; i++, i1--, is--)
      { pv = tresu.pvo;
        top = new Operand (top1[i1], '0', pv);
        tresu = DigitOper.add (top);
        sum[is] = tresu.res;
      }
      sum[0] = tresu.pvo;

      return (sum);
   }

  /**
   *   Operação de adição de dois números com acumulação da soma no operando 1.
   *
   *   @param n1 n. de algarismos do operando 1
   *   @param op1 operando 1 / soma
   *   @param n2 n. de algarismos do operando 2
   *   @param op2 operando 2
   */

   private static void internalAddSp (int n1, char [] op1, int n2, char [] op2)
   {
      /* realização da operação */

      Operand top;                               // representação de algarismos dos operandos
      Result tresu = null;                       // representação de algarismos do resultado
      char pv;                                   // propagação do valor

      for (int i = 0, i1 = n1-i-1, i2 = n2-i-1;
           i < n2; i++, i1--, i2--)
      { pv = (i == 0) ? '0' : tresu.pvo;
        top = new Operand (op1[i1], op2[i2], pv);
        tresu = DigitOper.add (top);
        op1[i1] = tresu.res;
      }
   }

  /**
   *   Operação de multiplicação de dois números.
   *
   *   @param op1 operando1
   *   @param op2 operando2
   *
   *   @return produto
   */

   private static char [] internalMult (char [] op1, char [] op2)
   {
      /* determinação do número de algarismos do produto */

      int prodSize = op1.length + op2.length;

      /* normalização dos operandos e do resultado */

      char [] top1, top2,                        // operandos
              prod = new char[prodSize];         // produto

      if (op1.length >= op2.length)
         { top1 = op1;
           top2 = op2;
         }
         else { top1 = op2;
                top2 = op1;
              }
      for (int i = 0; i < prod.length; i++)
        prod[i] = '0';

      /* realização da operação */

      for (int i = 0, i2 = top2.length-i-1, ip = prod.length-i-1;
           i < top2.length; i++, i2--, ip--)
        internalAddSp (prodSize-i, prod, top1.length+1, internalMultSp (top1, top2[i2]));

      return (prod);
   }

  /**
   *   Operação de multiplicação de um número por um algarismo.
   *
   *   @param op1 operando1 (número)
   *   @param op2 operando2 (algarismo)
   *
   *   @return produto
   */

   private static char [] internalMultSp (char [] op1, char op2)
   {
      /* determinação do número de algarismos do produto */

      int prodSize = op1.length + 1;
      char [] prod = new char[prodSize];         // produto

      /* realização da operação */

      Operand top;                               // representação de algarismos dos operandos
      Result tresu = null;                       // representação de algarismos do resultado
      char pv;                                   // propagação do valor

      for (int i = 0, i1 = op1.length-i-1, ip = prod.length-i-1;
           i < op1.length; i++, i1--, ip--)
      { pv = (i == 0) ? '0' : tresu.pvo;
        top = new Operand (op1[i1], op2, pv);
        tresu = DigitOper.mult (top);
        prod[ip] = tresu.res;
      }
      prod[0] = tresu.pvo;

      return (prod);
   }
    
}
