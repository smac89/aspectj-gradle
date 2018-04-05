package com.github.smac89.aspectj

import org.gradle.api.Project

import java.nio.charset.StandardCharsets

/**
 * @author Chigozirim Chukwu
 */
class AspectJPluginExtension {

    @Delegate
    final Map<String, Object> ajcOptions

    AspectJPluginExtension(Project project) {
        ajcOptions = [fork         : true,
                      maxmem       : 1024,
                      Xlint        : 'ignore',
                      showWeaveInfo: true,
                      verbose      : false,
                      log          : 'iajc.log',
                      source       : project.sourceCompatibility,
                      target       : project.targetCompatibility,
                      proc         : 'none',
                      aspectPath   : project.configurations."${AspectJPlugin.ASPECTS}".asPath,
                      encoding     : StandardCharsets.UTF_8.name()] as Map<String, Object>
    }
}
