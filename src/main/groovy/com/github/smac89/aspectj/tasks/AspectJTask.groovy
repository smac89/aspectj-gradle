package com.github.smac89.aspectj.tasks

import com.github.smac89.aspectj.internal.AspectTaskWithDefaults
import org.gradle.api.Action
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*

import static com.github.smac89.aspectj.AspectJPlugin.AJTOOLS
/**
 * @author Chigozirim Chukwu
 */
class AspectJTask extends DefaultTask implements AspectTaskWithDefaults {

    SourceSet sourceSet

    @Input
    final Map<String, String> additionalAjcArgs

    @OutputDirectory
    String destDir

    AspectJTask() {
        additionalAjcArgs = [:]
        ant.taskdef(resource: 'org/aspectj/tools/ant/taskdefs/aspectjTaskdefs.properties',
                classpath: project.configurations.findByName(AJTOOLS)?.asPath)
    }

    @TaskAction
    def compile() {
        ant.iajc(ajcArgs)
    }

    def additionalAjcArgs(Action<Map<String, String>> configure) {
        configure.execute(additionalAjcArgs)
        additionalAjcArgs
    }

    @Input
    def getAjcArgs() {
        return defaultAjcArgs + [destDir: destDir] + additionalAjcArgs
    }

    @InputFiles
    def getSourceSet() {
        sourceSet.allJava.sourceDirectories
    }

    def setDestDir(File value) {
        destDir = value.absolutePath
    }

    def setDestDir(String value) {
        destDir = value
    }
}
