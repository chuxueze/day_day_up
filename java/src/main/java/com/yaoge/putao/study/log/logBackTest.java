package com.yaoge.putao.study.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class logBackTest {

    private static final Logger logger = LoggerFactory.getLogger(logBackTest.class);

    public static void main(String[] args){
        test1();
        logger.info("--->hello world---> ");
    }

    public static void test1(){
        logger.error("nice boy");
    }
}
