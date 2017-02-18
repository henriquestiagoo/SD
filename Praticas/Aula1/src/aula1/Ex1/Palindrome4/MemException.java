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

/**
 *    Descrição geral:
 *       definição de uma excepção por acesso a uma memória de tipo stack ou fifo
 *       nas condições seguintes:
 *          - operação de escrita sobre uma memória cheia
 *          - operação de leitura sobre uma memória vazia.
 */

public class MemException extends Exception {
    
    public MemException (String errorMessage) {
        super (errorMessage);
    }

   public MemException (String errorMessage, Throwable cause) {
        super (errorMessage, cause);
    }  
}
