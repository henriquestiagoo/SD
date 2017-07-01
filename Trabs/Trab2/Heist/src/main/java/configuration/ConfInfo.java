/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package configuration;

import java.util.HashMap;
import main.Constants;
import static main.Constants.getHost;
import static main.Constants.getPort;

/**
 * Configuration Settings.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public class ConfInfo {
    
    public static HashMap<String, Integer> SERVER_PORTS;
    public static HashMap<String, String> SERVER_HOSTS;
    private final String filename;

    /**
     * Configuration Information Constructor.
     */
    public ConfInfo() {
        filename = Constants.FILENAME;
        /* Hosts */
        SERVER_HOSTS = new HashMap<>();
        SERVER_HOSTS.put("Conf", getHost("Conf", filename));                                          // Configuration
        SERVER_HOSTS.put("ConcentrationSite", getHost("ConcentrationSite", filename));                // ConcSite 
        SERVER_HOSTS.put("ControlAndCollectionSite", getHost("ControlAndCollectionSite", filename));  // ContControlSite
        SERVER_HOSTS.put("GeneralRepository", getHost("GeneralRepository", filename));                // GenRep
        SERVER_HOSTS.put("Museum", getHost("Museum", filename));                                      // Museum
        SERVER_HOSTS.put("AssaultParty0", getHost("AssaultParty0", filename));                        // AP0
        SERVER_HOSTS.put("AssaultParty1", getHost("AssaultParty1", filename));                        // AP1
        /* Ports */
        SERVER_PORTS = new HashMap<>();
        SERVER_PORTS.put("Conf", getPort("Conf", "conf.xml"));                                        // Configuration
        SERVER_PORTS.put("ConcentrationSite", getPort("ConcentrationSite", filename));                // ConcSite
        SERVER_PORTS.put("ControlAndCollectionSite", getPort("ControlAndCollectionSite", filename));  // ContControlSite
        SERVER_PORTS.put("GeneralRepository", getPort("GeneralRepository", filename));                // GenRep
        SERVER_PORTS.put("Museum", getPort("Museum", filename));                                      // Museum
        SERVER_PORTS.put("AssaultParty0", getPort("AssaultParty0", filename));                        // AP0
        SERVER_PORTS.put("AssaultParty1", getPort("AssaultParty1", filename));                        // AP1              
    }  
    
    /**
     * Method that returns the ports HashMap.
     * @return ConfInfo.SERVER_PORTS
     */
    public static HashMap<String, Integer> getServerPortsHashMap(){
        return ConfInfo.SERVER_PORTS;
    }
    
    /**
     * Method that returns the hosts HashMap.
     * @return ConfInfo.SERVER_HOSTS
     */
    public static HashMap<String, String> getServerHostsHashMap(){
        return ConfInfo.SERVER_HOSTS;
    }   
}
