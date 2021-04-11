package ru.izlishki.dhtserver.udp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.izlishki.dhtserver.config.ServerInstance;
import ru.izlishki.dhtserver.tcp.ClientTCP;
import ru.izlishki.dhtserver.udp.ClientUDP;
import ru.izlishki.dhtserver.udp.ServerUDP;

import java.util.Iterator;
import java.util.Set;

//This thread is responsible for the UDP connection and the actions needed when a packet is received
@Component
public class ThreadUDP extends Thread {

    private final ServerInstance serverInstance;
    //initializing of variables
    String msg;
    ServerUDP server;

    @Autowired
    public ThreadUDP(ServerInstance serverInstance) {
        this.serverInstance = serverInstance;
    }

    //overriding the run method of the Thread class to change the action of this Thread to what I need it to do
    public void run() {

        while (true) {
            server = new ServerUDP();
            server.start();
            msg = server.getMsg();
            System.out.println("UDP received: " + msg);
            msgRec(msg);
        }
    }

    //this method is executed once a String has been received sung a UDP connection
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
            case 2:
                serverInstance.setH(arr[2], arr[3]);
                System.out.println("Table after adding file: " + serverInstance.getH().toString());
                break;
            case 3:
                if (!serverInstance.getH().containsKey(arr[2])) {
                    udp = new ClientUDP(msg + ";" + "404 Content Not Found");
                    break;
                }
                udp = new ClientUDP(msg + ";" + serverInstance.getH().get(arr[2]));
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
