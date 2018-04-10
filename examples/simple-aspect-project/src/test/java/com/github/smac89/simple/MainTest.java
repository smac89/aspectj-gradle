package com.github.smac89.simple;

import org.junit.Test;

public class MainTest {

    @Test(expected = IllegalArgumentException.class)
    public void runs_this_test() {
        throw new IllegalArgumentException("It worked!!");
    }
}
