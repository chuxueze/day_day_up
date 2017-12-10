package com.yaoge.putao.study;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class HelloServiceImpl extends UnicastRemoteObject implements HelloService {


    protected HelloServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public String sayHello(String someOne) throws RemoteException {
        return "hello "+someOne;
    }
}
