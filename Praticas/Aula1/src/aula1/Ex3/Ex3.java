/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aula1.Ex3;
import java.util.Scanner;

/**
 *
 * @author Utilizador
 */
public class Ex3 {
    
    static Scanner kb = new Scanner(System.in);
    
    public static void main (String [] args){
        
        String op1,                                      // operando 1 como um string
               op2,                                      // operando 2 como um string
               res = "";                                 // resultado como um string
        int toper;                                       // tipo de operação

        System.out.print("Operando 1: ");
        op1 = kb.nextLine();
        if (op1 == null) op1 = "";
        System.out.print("\nOperando 2: ");
        op2 = kb.nextLine();
        if (op2 == null) op2 = "";
        System.out.print("\n     1 - Adição\n     2 - Multiplicação");
        System.out.print("\nOpção: ");
        toper = kb.nextInt();
        try
        { 
            if (toper == 1)
                res = BasicOper.add(op1, op2);
            else 
                res = BasicOper.mult(op1, op2);
        }
        catch (NumberFormatException e)
        { e.printStackTrace ();
          System.exit(1);
        }
        
        if (toper == 1)
           System.out.println("A soma é " + res);
        else 
           System.out.println("O produto é " + res);
    }    
}


