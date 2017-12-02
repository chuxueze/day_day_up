package com.yaoge.putao.study.annocation;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class Girl {

    private int age;

    @Yaoge
    private String phoneNum;

    public Girl() {
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
    public static void main(String[] args){
        System.out.println(new Girl());
    }

}
