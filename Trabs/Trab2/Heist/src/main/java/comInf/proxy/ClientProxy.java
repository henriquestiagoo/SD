/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comInf.proxy;

import comInf.ClientCom;
import comInf.message.Message;
import comInf.message.MessageType;
import comInf.message.MessageContainer;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.GeneralRepositoryProxy;

/**
 * Client Proxy.
 * Este tipo de dados define o thread agente prestador de serviço para uma solução do Heist of the Museum
 * que implementa o modelo cliente-servidor de tipo 2 (replicação do servidor).
 * A comunicação baseia-se em passagem de mensagens sobre sockets usando o protocolo TCP.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public class ClientProxy extends Thread {
    
    private final String clientProxyServerName;
    private final int toServerPort;
    private final Message outMessage;
    private final MessageContainer result;
    
    /**
     * Constructor for the client proxy.
     * @param clientProxyServerName - Client Proxy Server name
     * @param toServerPort - Server Port Identifier
     * @param result - Message Container Identifier
     * @param outMessage - Message name
     */
    public ClientProxy(String clientProxyServerName, int toServerPort, MessageContainer result, Message outMessage){
        this.clientProxyServerName = clientProxyServerName;
        this.toServerPort = toServerPort;
        this.outMessage = outMessage;
        this.result = result;
    }
    
    /**
     * Client proxy wrapper.
     * @param logServerName - Server Name
     * @param logServerPort - Server Port Identifier
     * @param msg - Message Identifier
     * @return result
     */
    public static MessageContainer connect(String logServerName, int logServerPort, Message msg){
        MessageContainer result = new MessageContainer();
        ClientProxy cp = new ClientProxy(logServerName, logServerPort, result, msg);
        cp.start();
        
        try {
            //System.out.printf("[%s][%d][%s] Join\n", logServerName, logServerPort, msg.getType().toString()); 
            cp.join(); 
        } catch (InterruptedException ex) {
            Logger.getLogger(GeneralRepositoryProxy.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if ((result.getMessage().getType() != MessageType.ACK) && (result.getMessage().getType() != MessageType.SERVER_REPLY)) {
            System.out.println("Invalid Type. Message: " + result.getMessage().toString());
            System.exit(1);
        }
        return result;
    }
    
    /**
     * Client proxy run.
     */
    @Override
    public void run(){
        try {
            ClientCom con = new ClientCom(this.clientProxyServerName, this.toServerPort);
            
            while (!con.open())
            {
                try {
                    sleep((long) (10));
                } catch (InterruptedException e) {
                }
            }   
            
            con.writeObject(outMessage);
            this.result.setMessage((Message) con.readObject());
            con.close();
            
        } catch (Exception ex) {
            Logger.getLogger(ClientProxy.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
