package com.yaoge.putao.study.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;


public class LoadProperties {
    private LoadProperties() {
    }

    private static final Logger logger = LoggerFactory.getLogger(LoadProperties.class);

    private static ResourceBundle bundle = null;

    static {
        bundle = PropertyResourceBundle.getBundle("bigData");
        if (bundle != null) {
            logger.info("配置文件加载完成");
        } else {
            logger.error("配置文件加载失败");
            System.exit(0);
        }
    }

    public static String getPropertityValue(String propertityKey) {
        if (!bundle.containsKey(propertityKey)) {
            logger.error("配置文件存在 propertityKey = {}",propertityKey);
            return null;
        }
        return bundle.getString(propertityKey);
    }

}
