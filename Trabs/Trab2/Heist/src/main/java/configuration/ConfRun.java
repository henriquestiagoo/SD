/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configuration;

import comInf.ServerCom;
import comInf.proxy.ServerProxy;

/**
 * Configuration Run Main Instance.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public class ConfRun {
    
    /* P1G3 */
    private static int SERVER_PORT;
    private static String SERVER_HOST;
    
    public static void main(String[] args) {
        
        /* canais de comunicação */
        ServerCom schan, schani;
        
        /* thread agente prestador do serviço */
        ServerProxy cliProxy;                               

        /* estabelecimento do servico */
        ConfServer confServer = new ConfServer();
        SERVER_PORT = ConfServer.SERVER_PORTS.get("Conf");
        SERVER_HOST = ConfServer.SERVER_HOSTS.get("Conf");
        
        /* criação do canal de escuta e sua associação */
        schan = new ServerCom(SERVER_PORT);    
        schan.start();
        
        System.out.println("Configuration Service has started!");
        System.out.printf("Server is listening on: %s.%d ... \n", SERVER_HOST, SERVER_PORT);

        /* processamento de pedidos */ 
        while (true) {
            /* entrada em processo de escuta */
            schani = schan.accept();
            /* lançamento do agente prestador do serviço */
            cliProxy = new ServerProxy(schani, confServer); 
            cliProxy.start();
        }
    }   
}