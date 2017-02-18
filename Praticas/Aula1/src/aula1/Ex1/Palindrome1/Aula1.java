/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aula1.Ex1.Palindrome1;
import java.util.Scanner;

/**
 *
 * @author Utilizador
 */
public class Aula1 {
    
    static Scanner kb = new Scanner(System.in);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        menu();
        System.out.print("Exercicio: ");
        int ex = kb.nextInt();
        //
        switch(ex){
            case 1: ex1();
                    break;
                    
            case 3: ex3();
                    break;
            
            default:
                    System.out.println("Opcao inválida!!");
                    System.exit(0);
        }
          
    }
    
    public static void menu(){
        System.out.println("__________________________");
        System.out.println("Palindroma 1 ");
        System.out.println("1 - Palindrome");
        System.out.println("3 - Operações aritméticas");
        System.out.println("__________________________");
    }
    
    public static void ex1() {
        Scanner ks = new Scanner(System.in);
        System.out.print("Introduza uma palavra: ");
        String word = ks.nextLine();
        
        FIFOChar fifochar = new FIFOChar(word.length());
        StackChar stackchar = new StackChar(word.length());
        
        for (int i = 0; i < word.length (); i++) {   
            fifochar.in(word.charAt(i));
            stackchar.push(word.charAt(i));
        }

        while(!fifochar.isEmpty()) {
            if (fifochar.out() != stackchar.pop()){
                System.out.println(word + " não é Palindroma");
                System.exit(0);
            }
        }      
        
        System.out.println(word + " é Palindroma");    
    }
    
    public static void ex3(){
        //TODO
        System.out.println("Não implementado ainda!!!!");
    }
}
