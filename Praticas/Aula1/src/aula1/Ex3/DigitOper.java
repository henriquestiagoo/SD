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
public class DigitOper {
    
    private DigitOper (){ }
    
    public static Result add (Operand op) {
      int whole;    // resultado da operação (formato não normalizado)
      whole = op.iop1 + op.iop2 + op.ipvi;
      return (new Result (whole % 10, whole / 10));
    }
    
    public static Result mult (Operand op) {
      int whole;   // resultado da operação (formato não normalizado)
      whole = op.iop1 * op.iop2 + op.ipvi;
      return (new Result (whole % 10, whole / 10));
    }
}
