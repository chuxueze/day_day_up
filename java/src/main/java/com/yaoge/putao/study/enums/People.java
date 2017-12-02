package com.yaoge.putao.study.enums;

public enum People {
    BOY(0, "男人"),
    GIRL(1, "女人");

    private int code;
    private String desc;

    People(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

}
