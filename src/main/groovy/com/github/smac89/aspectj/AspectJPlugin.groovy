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

    @Override
    void apply(Project project) {
        project.getPluginManager().apply(JavaPlugin)

        ajtools = project.configurations.maybeCreate(AJTOOLS)
        aspectj = project.extensions.create(ASPECTJ, AspectJPluginExtension)
        sourceSets = project.properties.get("sourceSets") as SourceSetContainer
        aspects = project.configurations.maybeCreate(ASPECTS)

        def aspectjVersion = project.findProperty('aspectjVersion') as String
        if (!aspectjVersion) {
            aspectjVersion = '1.8.13'
            project.logger.warn "Could not find property for aspectjVersion; Using version $aspectjVersion instead"
        }

        ajtools.dependencies.add(project.dependencies.create("org.aspectj:aspectjtools:$aspectjVersion"))

        project.afterEvaluate {
            def defaultArgs = aspectj.properties.findResults { prop, value ->
                value == null ?: [prop.toString(), value.toString()]
            } collectEntries()

            def weaveOption = defaultArgs.getProperties().remove("weaveOption") ?: WeaveType.WEAVE_FINAL_JAR.name()

            for (sourceSet in sourceSets) {
                project.logger.warn("Checking sourceset: ${sourceSet.name}")
                switch (sourceSet.name) {
                    case SourceSet.MAIN_SOURCE_SET_NAME:
                        createTask(project, sourceSet, "compileAspect")
//                        it.task("compileAspect", overwrite: true, type: AspectJTask) {
//                            description = "Compiles AspectJ for ${sourceSet.name} source set"
//                            group = "ajc"
//                            defaultAjcArgs = defaultArgs
//                        }
                        break
                    case SourceSet.TEST_SOURCE_SET_NAME:
                        createTask(project, sourceSet, "compileTestAspect")
                        break
                }
            }
        }
    }

    private static Task createTask(Project project, SourceSet sourceSet, String taskName) {
        return project.task(taskName, overwrite: true, type: AspectJTask) {
            description = "Compiles AspectJ for ${sourceSet.name} source set"
//            group = "ajc"
        }
    }
}
