/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlAndCollectionSite;

import comInf.ServerCom;
import comInf.proxy.ServerProxy;
import configuration.ConfProxy;
import main.GeneralRepositoryProxy;

/**
 * ControlAndCollectionSite Site Run Main Instance.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public class ControlAndCollectionSiteRun {
    
    /* P1G3 */
    private static int SERVER_PORT;
    private static String SERVER_HOST;
    
    /**
     * This class will launch a server that is listening one port and processing requests.
     * @param args - Args input
     */
    public static void main(String[] args) {
        
        ConfProxy confProxy = new ConfProxy();
        SERVER_PORT = confProxy.getServerPorts().get("ControlAndCollectionSite");
        SERVER_HOST = confProxy.getServerHosts().get("ControlAndCollectionSite");
        
        /* canais de comunicação */
        ServerCom schan, schani;
        
        /* thread agente prestador do serviço */
        ServerProxy cliProxy;    
        
        GeneralRepositoryProxy genRepProxy = new GeneralRepositoryProxy();

        /* estabelecimento do servico */
        schan = new ServerCom(SERVER_PORT);    
        schan.start();
        
        ControlAndCollectionSiteServer contCollSiteServer = new ControlAndCollectionSiteServer(genRepProxy);
        System.out.println("Control And Collection Site service has started!");
        System.out.printf("Server is listening on: %s.%d ... \n" , SERVER_HOST, SERVER_PORT);

        /* processamento de pedidos */
        while (true) {
            /* entrada em processo de escuta */
            schani = schan.accept();
            /* lançamento do agente prestador do serviço */
            cliProxy = new ServerProxy(schani, contCollSiteServer);
            cliProxy.start();
        }
    }  
}
