package com.github.smac89.aspectj

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.artifacts.Configuration
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.jvm.application.tasks.CreateStartScripts

/**
 * @author Chigoizirim Chukwu
 */
class AspectJPlugin implements Plugin<Project> {
    public static final String AJTOOLS = "ajtools"
    public static final String ASPECTS = "aspects"
    public static final String ASPECTJ = "aspectj"
    public static final String AJWEAVE = "weave"

    private Configuration aspects
    private Configuration ajtools
    private Configuration weave
    private SourceSetContainer sourceSets
    private AspectJPluginExtension aspectj
    private Project project

    @Override
    void apply(Project project) {
        project.getPluginManager().apply(JavaPlugin)

        this.project = project
        ajtools = project.configurations.maybeCreate(AJTOOLS)
        weave = project.configurations.maybeCreate(AJWEAVE)
        aspects = project.configurations.maybeCreate(ASPECTS)
        aspectj = project.extensions.create(ASPECTJ, AspectJPluginExtension, project)

        sourceSets = project.properties.get("sourceSets") as SourceSetContainer
        addAspectJDeps()

        for (sourceSet in sourceSets) {
            switch (sourceSet.name) {
                case SourceSet.MAIN_SOURCE_SET_NAME:
                    project.configure(createTask(sourceSet, "compileAspect")) {
                        destDir = "${project.buildDir}/aspect/"
                    }
                    break
                case SourceSet.TEST_SOURCE_SET_NAME:
                    project.configure(createTask(sourceSet, "compileTestAspect")) {
                        destDir = "${project.buildDir}/test-aspect/"
                    }

//                    project.test {
//                        jvmArgs += "-javaagent:${project.configurations.weave.asPath}"
//                    }
                    break
            }
        }
    }

    private Task createTask(SourceSet sourceSet, String taskName) {
        return project.tasks.create(name: taskName, overwrite: true, type: AspectJTask,
                description: "Compiles AspectJ for ${sourceSet.name} source set",
                group: "ajc", dependsOn: "jar") {
            it.additionalAjcArgs {
                classpath = (sourceSet.runtimeClasspath + sourceSet.compileClasspath).filter {it.exists()}.asPath
                sourceRoots = sourceSet.java.sourceDirectories.asPath
            }

            it.sourceSet = sourceSet
        }
    }

    private Task createLoadTask(SourceSet sourceSet, String taskName) {
        return project.tasks.create(name: taskName, overwrite: true, type: CreateStartScripts,
                description: "Adds load time weaving for  AspectJ for ${sourceSet.name} source set",
                group: "ajc") {
            classpath = sourceSet.runtimeClasspath.asPath
        }
    }

    private void addAspectJDeps() {
        def aspectjVersion = project.findProperty('aspectjVersion') as String ?: '1.9.0'

        project.repositories {
            jcenter()
        }

        ajtools.dependencies.add(project.dependencies.create("org.aspectj:aspectjtools:$aspectjVersion"))
        weave.dependencies.add(project.dependencies.create("org.aspectj:aspectjweaver:$aspectjVersion"))

        project.dependencies {
            compile "org.aspectj:aspectjrt:$aspectjVersion"
        }
    }
}
