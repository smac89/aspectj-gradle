package com.github.smac89.aspectj.internal;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Chigozirim Chukwu
 */
public enum WeaveOption {
    COMPILE, LOAD;

    private final static Set<String> names = new HashSet<>();
    static {
        for (WeaveOption weaveOption: values()) {
            names.add(weaveOption.name());
        }
    }

    public static WeaveOption safeName(String name, WeaveOption defaultIfNotExist) {
        name = name.toUpperCase();
        return names.contains(name) ? WeaveOption.valueOf(name) : defaultIfNotExist;
    }
}
