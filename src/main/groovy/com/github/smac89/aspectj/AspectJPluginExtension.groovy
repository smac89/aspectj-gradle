package com.github.smac89.aspectj

import org.gradle.api.Project

import java.nio.charset.StandardCharsets
/**
 * @author Chigozirim Chukwu
 */
class AspectJPluginExtension {

    final Expando ajcOptions

    final Expando pluginOptions

    AspectJPluginExtension(Project project) {
        ajcOptions = new Expando(fork: true, maxmem: 1024, Xlint: 'ignore', showWeaveInfo: true,
                verbose: false,
                log: 'iajc.log',
                source: project.sourceCompatibility,
                target: project.targetCompatibility,
                proc: 'none',
                aspectPath: project.configurations."${AspectJPlugin.ASPECTS}".asPath,
                encoding: StandardCharsets.UTF_8.name())

        pluginOptions = new Expando(weaveOption: WeaveType.POST_COMPILE)
    }

    def ajcOptions(Closure<Expando> configure) {
        configure.delegate = ajcOptions
        configure()
    }

    def pluginOptions(Closure<Expando> configure) {
        configure.delegate = pluginOptions
        configure()
    }
}

