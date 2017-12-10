package com.yaoge.putao.study;

import java.rmi.Naming;

public class ClentMain  {
    public static void main(String[] args) throws Exception {
        HelloService helloService = (HelloService) Naming.lookup("rmi://localhost:8888/helloService");
        System.out.println(helloService.sayHello("luyao"));


    }

}
