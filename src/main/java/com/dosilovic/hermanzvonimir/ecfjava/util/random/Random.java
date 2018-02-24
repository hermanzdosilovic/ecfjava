package com.dosilovic.hermanzvonimir.ecfjava.util.random;

import com.dosilovic.hermanzvonimir.ecfjava.util.random.providers.IRandomProvider;
import com.dosilovic.hermanzvonimir.ecfjava.util.random.providers.ThreadLocalRandomProvider;

import java.util.Properties;

public class Random {

    private static IRandomProvider provider;

    static {
        try {
            Properties  properties  = new Properties();
            ClassLoader classLoader = Random.class.getClassLoader();
            properties.load(classLoader.getResourceAsStream("ecfjava.properties"));
            Class<?> clazz = classLoader.loadClass(properties.getProperty("random.provider"));
            provider = (IRandomProvider) clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            provider = new ThreadLocalRandomProvider();
        }
    }

    public static IRandom getRandom() {
        return provider.getRandom();
    }
}
