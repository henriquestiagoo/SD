/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comInf.proxy;

import comInf.ServerCom;
import comInf.message.Message;
import comInf.message.MessageException;

/**
 * Server Proxy.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public class ServerProxy extends Thread {
    
    private static int nProxy;
    private final ServerCom sconi;
    private final IServer iServer;
        
    /**
     * ServerProxy Constructor.
     * @param sconi - ServerCom Identifier
     * @param iServer - Server Interface Identifier
     */
    public ServerProxy(ServerCom sconi, IServer iServer) {
        super("Proxy_" + getProxyId());
        this.sconi = sconi;
        this.iServer = iServer;
    }

    /**
     * Server Proxy run.
     */
    @Override
    public void run() {
        Message inMessage = null;         // mensagem de entrada
        Message ouMessage = null;         // mensagem de saída

        inMessage = (Message) sconi.readObject();
        
        try{
            ouMessage = iServer.processAndReply(inMessage);
        } catch (MessageException e) {
            System.out.println("Thread " + getName() + ": " + e.getMessage() + "!");
            System.out.println(e.getMessageVal().toString());
            System.exit(1);
        }
        
        sconi.writeObject(ouMessage);   // enviar resposta ao cliente
        sconi.close();                  // fechar canal de comunicação
        
        if(iServer.endServer()) {
            System.out.println("Closing Monitor Service ... End of Operations!");
            System.exit(0);
        }
    }

    /**
     * Method that returns the Server Proxy id.
     * @return nProxy
     */
    public static int getProxyId(){
        return nProxy;
    }
}
