package com.github.smac89.aspectj

import org.gradle.api.JavaVersion
import org.gradle.api.Project

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
                source: JavaVersion.current(),
                target: JavaVersion.current(),
                proc: 'none',
                aspectPath: project.configurations."${AspectJPlugin.ASPECTS}".asPath)

//        ajcOptions.metaClass {
//            calll = { Closure configure -> delegate.with(configure) }
//        }

        pluginOptions = new Expando(weaveOption: WeaveType.WEAVE_FINAL_JAR)
    }
}

