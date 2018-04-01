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
    Map<String, String> ajcArgs = [:]

    @TaskAction
    def compile() {
        AntBuilder ant = getAnt()
        println ajcArgs
    }
}
