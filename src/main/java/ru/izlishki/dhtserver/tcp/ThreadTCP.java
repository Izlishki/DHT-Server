package ru.izlishki.dhtserver.tcp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.izlishki.dhtserver.config.ServerInstance;
import ru.izlishki.dhtserver.tcp.ClientTCP;
import ru.izlishki.dhtserver.tcp.ServerTCP;
import ru.izlishki.dhtserver.udp.ClientUDP;

import java.util.Iterator;
import java.util.Set;

//the DHT servers use 2 threads, 1 more the TCP connection and the other for the UDP connection
//this thread is responsible for accepting TCP String packets and performing the tasks based on what is received
@Component
public class ThreadTCP extends Thread {

    //initializing
    String msg;
    ServerTCP server;
    private final ServerInstance serverInstance;

    @Autowired
    public ThreadTCP(ServerInstance serverInstance) {
        this.serverInstance = serverInstance;
    }

    //overriding the run method of the Thread class to perform the tasks that my program needs to
    public void run() {
        while (true) {
            server = new ServerTCP();
            msg = server.getMsg();
            System.out.println("TCP received: " + msg);
            msgRec(msg);
            server = null;
            System.gc();
        }
    }

    //this method is executed once a message has been received
    //based on the first number in the String, the action needed to be performed, changes
    public void msgRec(String msg) {
        String[] arr = msg.split(";");
        ClientUDP udp;
        ClientTCP tcp;
        String temp;
        Set<String> keys;
        Iterator<String> it;
        switch (Integer.parseInt(arr[0])) {
            case 1:
                if (msg.contains(serverInstance.getIp())) {
                    udp = new ClientUDP(msg);
                    System.out.println("Table after init: " + serverInstance.getH().toString());
                    break;
                }
                msg = msg + ";" + serverInstance.getId() + ";" + serverInstance.getIp();
                tcp = new ClientTCP(serverInstance.getIpSuc(), msg);
                break;
            case 4:
                if (msg.contains(serverInstance.getIp())) {
                    System.out.println("Table after exit: " + serverInstance.getH().toString());
                    break;
                }
                if (serverInstance.getH().containsValue(arr[1])) {
                    keys = serverInstance.getH().keySet();
                    it = keys.iterator();
                    while (it.hasNext()) {
                        temp = it.next();
                        if (serverInstance.getH().get(temp).equals(arr[1])) {
                            it.remove();
                        }
                    }
                }
                msg = msg + ";" + serverInstance.getIp();
                tcp = new ClientTCP(serverInstance.getIpSuc(), msg);
                break;
            default:
                break;
        }
    }
}
