package com.github.smac89.aspectj

import com.github.smac89.aspectj.internal.AspectTaskWithDefaults
import org.gradle.api.Action
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.*
/**
 * @author Chigozirim Chukwu
 */
class AspectJTask extends DefaultTask implements AspectTaskWithDefaults {

    SourceSet sourceSet

    @Input
    final Map<String, String> additionalAjcArgs = [:]

    @Input
    WeaveOption weaveOption

    @OutputDirectory
    String destDir

    AspectJTask() {
        ant.taskdef(resource: 'org/aspectj/tools/ant/taskdefs/aspectjTaskdefs.properties',
                classpath: project.configurations.ajtools.asPath)
    }

    @TaskAction
    def compile() {
        if (weaveOption == WeaveOption.COMPILE) {
            _actionCompile()
        }
    }

    def _actionCompile() {
        ant.iajc(ajcArgs)
    }

    def additionalAjcArgs(Action<Map<String, String>> configure) {
        configure.execute(additionalAjcArgs)
    }

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
}
