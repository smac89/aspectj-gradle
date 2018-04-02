package com.github.smac89.aspectj

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task
import org.gradle.api.artifacts.Configuration
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer

/**
 * @author Chigoizirim Chukwu
 */
class AspectJPlugin implements Plugin<Project> {
    public static final String AJTOOLS = "ajtools"
    public static final String ASPECTS = "aspects"
    public static final String ASPECTJ = "aspectj"

    private Configuration aspects
    private Configuration ajtools
    private SourceSetContainer sourceSets
    private AspectJPluginExtension aspectj
    private Project project

    @Override
    void apply(Project project) {
        project.getPluginManager().apply(JavaPlugin)

        this.project = project
        ajtools = project.configurations.maybeCreate(AJTOOLS)
        aspects = project.configurations.maybeCreate(ASPECTS)
        aspectj = project.extensions.create(ASPECTJ, AspectJPluginExtension, project)
        sourceSets = project.properties.get("sourceSets") as SourceSetContainer

        addAspectJDeps()

        def weaveOption = aspectj.pluginOptions.weaveOption ?: WeaveType.COMPILE_TIME

        for (sourceSet in sourceSets) {
            switch (sourceSet.name) {
                case SourceSet.MAIN_SOURCE_SET_NAME:
                    createTask(sourceSet, "compileAspect")
                    break
                case SourceSet.TEST_SOURCE_SET_NAME:
                    createTask(sourceSet, "compileTestAspect")
                    break
            }
        }
    }

    private Task createTask(SourceSet sourceSet, String taskName) {
        return project.tasks.create(name: taskName, overwrite: true, type: AspectJTask,
                description: "Compiles AspectJ for ${sourceSet.name} source set",
                group: "ajc")
    }

    private void addAspectJDeps() {
        def aspectjVersion = project.findProperty('aspectjVersion') as String
        if (!aspectjVersion) {
            aspectjVersion = '1.8.13'
        }

        ajtools.dependencies.add(project.dependencies.create("org.aspectj:aspectjtools:$aspectjVersion"))
        project.repositories {
            jcenter()
        }

        project.dependencies {
            compile "org.aspectj:aspectjrt:$aspectjVersion"
        }
    }
}
