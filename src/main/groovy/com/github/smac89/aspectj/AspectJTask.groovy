package com.github.smac89.aspectj

import org.gradle.api.AntBuilder
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

/**
 * @author Chigozirim Chukwu
 */
class AspectJTask extends DefaultTask {
    @Input
    public Map<String, String> additionalAjcArgs

    @Input
    Map<String, String> defaultAjcArgs

    AspectJTask() {
        if (additionalAjcArgs != null) {
            defaultAjcArgs += additionalAjcArgs
        }
    }

    @TaskAction
    def compile() {
        AntBuilder ant = getAnt()
        ant.getProperties().forEach { key, value ->
            System.out.printf("%s -> %s\n", key, value)
        }
    }
}
