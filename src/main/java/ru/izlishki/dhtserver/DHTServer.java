package ru.izlishki.dhtserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.izlishki.dhtserver.config.ServerInstance;
import ru.izlishki.dhtserver.tcp.ThreadTCP;
import ru.izlishki.dhtserver.udp.ThreadUDP;

import javax.annotation.PostConstruct;

@Service
public class DHTServer {


    private final ServerInstance server;
    private ThreadUDP threadUDP;
    private ThreadTCP threadTCP;

    @Autowired
    public DHTServer(ServerInstance server, ThreadUDP threadUDP, ThreadTCP threadTCP) {
        this.server = server;
        this.threadUDP = threadUDP;
        this.threadTCP = threadTCP;
    }

    @PostConstruct
    private void checkServerOn() {
        if (server.getServerOn())
            this.start();
    }

    public void start() {

        System.out.println(server.getIp());
        System.out.println("Enter ID: " + server.getId());

        System.out.println("Enter successor server IP: " + server.getIpSuc());

        threadUDP.start();

        System.out.println("UDP server has been successfully initialized!");

        threadTCP.start();

        System.out.println("TCP server has been successfully initialized!\n\nAll systems working properly!");

    }
}
