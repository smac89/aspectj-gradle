package com.github.smac89.aspectj

import org.gradle.api.AntBuilder
import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

/**
 * @author Chigozirim Chukwu
 */
class AspectJTask extends DefaultTask {
    private final Provider<Map<String, String>> defaultAjcArgs = project.provider {
        project.aspectj.ajcOptions.properties.findResults { prop, value ->
            value == null ?: [prop.toString(), value.toString()]
        } collectEntries()
    }

    @Input
    Map<String, String> additionalAjcArgs = [:]

    @TaskAction
    def compile() {
        AntBuilder ant = getAnt()
        println "The additional args are: ${additionalAjcArgs}"
        println "The default args are: ${defaultAjcArgs}"
    }

    @Input
    Map<String, String> getDefaultAjcArgs() {
        defaultAjcArgs.get()
    }
}
