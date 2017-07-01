/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configuration;

import comInf.message.Message;
import comInf.message.MessageType;
import comInf.message.MessageContainer;
import comInf.proxy.ClientProxy;
import java.util.HashMap;

/**
 * Proxy in order for all the clients get the configuration information they need.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public class ConfProxy {
    
    private final String SERVER_HOST;
    private final int SERVER_PORT;

    /**
     * Configuration Proxy Constructor.
     */
    public ConfProxy(){        
        ConfInfo confInfo = new ConfInfo();
        this.SERVER_HOST = ConfInfo.SERVER_HOSTS.get("Conf");
        this.SERVER_PORT = ConfInfo.SERVER_PORTS.get("Conf");
    }
    
    /**
     * Method that communicates with the Server in order to make a connection.
     * @param m - Message Identifier
     */
    private MessageContainer sendMessage(Message m){
        return ClientProxy.connect(SERVER_HOST,  SERVER_PORT, m);
    }
    
    /**
    * Method that returns the server hosts hashMap.
    * @return serverHosts
    */
    public HashMap<String, String> getServerHosts() {        
        MessageContainer result = sendMessage(new Message(MessageType.CONF_SERVER_HOST));
        return result.getMessage().getHostHashMap();
    }
    
    /**
    * Method that returns the server ports hashMap.
    * @return serverPorts
    */
    public HashMap<String, Integer> getServerPorts() {
        MessageContainer result = sendMessage(new Message(MessageType.CONF_SERVER_PORT));
        return result.getMessage().getPortHashMap();
    } 
}