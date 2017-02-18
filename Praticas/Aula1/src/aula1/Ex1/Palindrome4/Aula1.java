/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aula1.Ex1.Palindrome4;
import java.util.Scanner;

/**
 *
 * @author Tiago Henriques
 */
public class Aula1 {
    
    static Scanner kb = new Scanner(System.in);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here       
        int i = 0;
        Scanner ks = new Scanner(System.in);
        System.out.println("Palindroma 4 - Classe Abstrata e Exceções ");
        System.out.print("Introduza uma palavra: ");
        String word = ks.nextLine();
        
        FIFOMem fifomem = new FIFOMem(word.length());
        StackMem stackmem = new StackMem(word.length());
        
        try{
            for (i = 0; i < word.length (); i++) {   
                fifomem.write(word.charAt(i));
                stackmem.write(word.charAt(i));
            }
        }catch(MemException e){
            System.out.println("Erro: "+ e.getMessage ()+ " na iteração " + (i+1));
            System.out.println("Erro: "+ e.toString ());
            e.printStackTrace ();
            System.exit(1);
        }
        
        try{
            for (i = 0; i < word.length (); i++) {   
                while(!fifomem.isEmpty()) {
                    if (fifomem.read() != stackmem.read()){
                        System.out.println(word + " não é Palindroma");
                        System.exit(0);
                    }
                } 
            }
        }catch(MemException e){
            System.out.println("Erro: "+ e.getMessage ()+ " na iteração " + (i+1));
            System.out.println("Erro: "+ e.toString ());
            e.printStackTrace ();
            System.exit(1);
        }
                 
        System.out.println(word + " é Palindroma");    
    } 
}