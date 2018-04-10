package com.github.smac89.aspectj;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Chigozirim Chukwu
 */
public enum WeaveOption {
    COMPILE, LOAD;

    private final static Set<String> names = Arrays.stream(values())
            .map(Enum::name)
            .collect(Collectors.toSet());

    public static WeaveOption safeName(String name, WeaveOption defaultIfNotExist) {
        return names.contains(name.toUpperCase()) ? WeaveOption.valueOf(name) : defaultIfNotExist;
    }
}
