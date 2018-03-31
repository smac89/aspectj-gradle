package com.github.smac89.aspectj

import org.gradle.api.JavaVersion

/**
 * @author Chigozirim Chukwu
 */
class AspectJPluginExtension {

    // COMPILER
    Boolean fork
    Integer maxmem

    // COMPILER OTHER
    String xlint
    Boolean showWeaveInfo
    Boolean verbose

    // EXTRAS
    String log
    JavaVersion source
    JavaVersion target

    String proc

    WeaveType weaveOption

    AspectJPluginExtension() {
        fork = true
        maxmem = 1024

        xlint = "ignore"
        showWeaveInfo = true
        verbose = false

        log = "iajc.log"
        source = JavaVersion.current()
        target = JavaVersion.current()

        proc = "none"

        weaveOption = WeaveType.WEAVE_FINAL_JAR
    }
}

