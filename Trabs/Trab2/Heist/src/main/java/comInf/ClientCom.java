/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comInf;

import java.io.*;
import java.net.*;

/**
 * Class for all the client communications, this will have the serverhostName and serverPortNumb 
 * and it will send the proxy information.
 * @author Tiago Henriques nmec: 73046; Miguel Oliveira nmec: 72638
 */
public class ClientCom {
    
    private Socket commSocket = null;
    private String serverHostName = null;
    private final int serverPortNumb;
    private ObjectInputStream in = null;  
    private ObjectOutputStream out = null;
    
    /**
     * Constructor to create a ClientCom with hostName and portNumb.
     * @param hostName - Host Name
     * @param portNumb - Port Number Identifier
     */
   public ClientCom (String hostName, int portNumb) {
      this.serverHostName = hostName;
      this.serverPortNumb = portNumb;
   }
    
    /**
     * Method that opens a communication socket.
     * @return success
     */
    public boolean open() {
        boolean success = true;
        SocketAddress serverAddress = new InetSocketAddress(this.serverHostName, serverPortNumb);

        try {
            commSocket = new Socket();
            commSocket.connect(serverAddress);
        } catch (UnknownHostException e) {
            System.out.println(Thread.currentThread().getName()
                    + " - o nome do sistema computacional onde reside o servidor é desconhecido: "
                    + this.serverHostName + "!");
            System.exit(1);
        } catch (NoRouteToHostException e) {
            System.out.println(Thread.currentThread().getName()
                    + " - o nome do sistema computacional onde reside o servidor é inatingível: "
                    + this.serverHostName + "!");
            System.exit(1);
        } catch (ConnectException e) {
            System.out.println(Thread.currentThread().getName()
                    + " - o servidor não responde em: " + this.serverHostName + "." + serverPortNumb + "!\n");
            if (e.getMessage().equals("Connection refused")) {
                success = false;
            } else {
                System.out.println(e.getMessage() + "!");
                System.exit(1);
            }
        } catch (SocketTimeoutException e) {
            System.out.println(Thread.currentThread().getName()
                    + " - ocorreu um time out no estabelecimento da ligação a: "
                    + this.serverHostName + "." + serverPortNumb + "!");
            success = false;
        } catch (IOException e) // erro fatal --- outras causas
        {
            System.out.println(Thread.currentThread().getName()
                    + " - ocorreu um erro indeterminado no estabelecimento da ligação a: "
                    + this.serverHostName + "." + serverPortNumb + "!");
            System.exit(1);
        }

        if (!success) {
            return (success);
        }

        try {
            out = new ObjectOutputStream(commSocket.getOutputStream());
        } catch (IOException e) {
            System.out.println(Thread.currentThread().getName()
                    + " - não foi possível abrir o canal de saída do socket!");
            System.exit(1);
        }

        try {
            in = new ObjectInputStream(commSocket.getInputStream());
        } catch (IOException e) {
            System.out.println(Thread.currentThread().getName()
                    + " - não foi possível abrir o canal de entrada do socket!");
            System.exit(1);
        }

        return (success);
    }
   
    /**
     * Method thats closes the communication channel.
     */
    public void close() {
        try {
            in.close();
        } catch (IOException e) {
            System.out.println(Thread.currentThread().getName()
                    + " - não foi possível fechar o canal de entrada do socket!");
            System.exit(1);
        }

        try {
            out.close();
        } catch (IOException e) {
            System.out.println(Thread.currentThread().getName()
                    + " - não foi possível fechar o canal de saída do socket!");
            System.exit(1);
        }

        try {
            commSocket.close();
        } catch (IOException e) {
            System.out.println(Thread.currentThread().getName()
                    + " - não foi possível fechar o socket de comunicação!");
            System.exit(1);
        }
    }
    
    /**
     * Method that reads a object.
     * @return fromServer
     */
    public Object readObject() {
        Object fromServer = null;
        
        try {
            fromServer = in.readObject();
        } catch (InvalidClassException e) {
            System.out.println(Thread.currentThread().getName()
                    + " - o objecto lido não é passível de desserialização!");
            System.exit(1);
        } catch (IOException e) {
            System.out.println(Thread.currentThread().getName()
                    + " - erro na leitura de um objecto do canal de entrada do socket de comunicação!");
            System.exit(1);
        } catch (ClassNotFoundException e) {
            System.out.println(Thread.currentThread().getName()
                    + " - o objecto lido corresponde a um tipo de dados desconhecido!");
            System.exit(1);
        }

        return fromServer;
    }
    
    /**
     * Method that writes a object.
     * @param toServer - Object toServer
     */
    public void writeObject(Object toServer) {
        try {
            out.writeObject(toServer);
        } catch (InvalidClassException e) {
            System.out.println(Thread.currentThread().getName()
                    + " - o objecto a ser escrito não é passível de serialização!");
            System.exit(1);
        } catch (NotSerializableException e) {
            System.out.println(Thread.currentThread().getName()
                    + " - o objecto a ser escrito pertence a um tipo de dados não serializável!");
            System.exit(1);
        } catch (IOException e) {
            System.out.println(Thread.currentThread().getName()
                    + " - erro na escrita de um objecto do canal de saída do socket de comunicação!");
            System.exit(1);
        }
    }
}
