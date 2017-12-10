package com.yaoge.putao.study;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class ServerMain {
    public static void main(String[] args) throws Exception {
        HelloService helloService=new HelloServiceImpl();
        LocateRegistry.createRegistry(8888);
        Naming.bind("rmi://localhost:8888/helloService",helloService);
        System.out.println("ServiceMain provide RPC servce now");
    }

}
