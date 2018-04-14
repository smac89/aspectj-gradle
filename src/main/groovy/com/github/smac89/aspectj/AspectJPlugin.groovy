package com.github.smac89.aspectj

import com.github.smac89.aspectj.rules.AJPluginRules
import com.github.smac89.aspectj.rules.AjSourceSetsRules
import groovy.transform.CompileStatic
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.api.plugins.JavaPlugin

/**
 * @author Chigoizirim Chukwu
 */
@CompileStatic
class AspectJPlugin implements Plugin<Project> {
    public static final String AJTOOLS = "ajtools"
    public static final String ASPECTS = "aspectpath"
    public static final String TEST_ASPECTS = "testAspectpath"
    public static final String ASPECTJEXT = "aspectjext"
    public static final String AJWEAVE = "weave"

    Configuration ajtools
    Configuration weave

    @Override
    void apply(Project project) {
        project.getPluginManager().apply(JavaPlugin)
        project.getPluginManager().apply(AJPluginRules)
        project.getPluginManager().apply(AjSourceSetsRules)

        project.extensions.create(ASPECTJEXT, AspectJExtension, project)

        ajtools = project.configurations.maybeCreate(AJTOOLS)
        weave = project.configurations.maybeCreate(AJWEAVE)

        project.configurations.maybeCreate(ASPECTS)
        project.configurations.maybeCreate(TEST_ASPECTS)
        configureDeps(project)
    }

    private void configureDeps(Project project) {
        def aspectjVersion = project.findProperty('aspectjVersion') as String ?: '1.9.0'

        project.repositories { RepositoryHandler handler ->
            handler.jcenter()
        }

        ajtools.dependencies.add(project.dependencies.create("org.aspectj:aspectjtools:$aspectjVersion"))
        weave.dependencies.add(project.dependencies.create("org.aspectj:aspectjweaver:$aspectjVersion"))

        project.dependencies { DependencyHandler handler ->
            handler.add("compile", "org.aspectj:aspectjrt:$aspectjVersion")
        }
    }
}
