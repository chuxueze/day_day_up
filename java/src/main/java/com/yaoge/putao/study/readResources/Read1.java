package com.yaoge.putao.study.readResources;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

public class Read1 {

    private static ResourceBundle bundle = null;

    public static ResourceBundle setPropertiesFile(String PropertiesFileName) {
        bundle = PropertyResourceBundle.getBundle(PropertiesFileName);
        return bundle;
    }

    /**
     * @param key 配置文件中的key
     * @return 配置文件中的value
     */
    public static String getStringValue(String key) {
        String value = "";
        if (bundle != null) {
            if (bundle.containsKey(key)) {
                value = bundle.getString(key);
                return value;
            }
        }
        return value;
    }

    /**
     * @param key 配置文件中的key
     * @return 配置文件中的value
     */
    public static int getIntValue(String key) {
        if (bundle != null) {
            if (bundle.containsKey(key)) {
                String value = bundle.getString(key);
                if (value != null) {
                    return Integer.parseInt(value);
                }
            }
        }
        return 0;
    }


    public static void main(String[] args) {
        setPropertiesFile("hbase");
        System.out.println(getStringValue("hbase.master"));
    }
}
