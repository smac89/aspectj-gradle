package com.github.smac89.aspectj

import com.github.smac89.aspectj.internal.WeaveOption
import groovy.transform.CompileStatic
import org.gradle.api.Project
import org.gradle.api.provider.Property

import java.nio.charset.StandardCharsets

/**
 * @author Chigozirim Chukwu
 */
@CompileStatic
class AspectJPluginExtension {

    @Delegate
    final Map<String, Object> ajcOptions

    Property<WeaveOption> appWeave

    Property<WeaveOption> testWeave

    AspectJPluginExtension(Project project) {
        ajcOptions = [fork         : true,
                      maxmem       : '1024m',
                      Xlint        : 'ignore',
                      showWeaveInfo: true,
                      verbose      : false,
                      log          : 'iajc.log',
                      source       : project.property('sourceCompatibility'),
                      target       : project.property('targetCompatibility'),
                      proc         : 'none',
                      aspectPath   : project.configurations.findByName("${AspectJPlugin.ASPECTS}")?.asPath,
                      encoding     : StandardCharsets.UTF_8.name()] as Map<String, Object>

        appWeave = project.objects.property(WeaveOption)
        appWeave.set(WeaveOption.COMPILE)

        testWeave = project.objects.property(WeaveOption)
        testWeave.set(WeaveOption.COMPILE)
    }

    def setAppWeave(String weaveType) {
        appWeave.set(WeaveOption.safeName(weaveType, WeaveOption.COMPILE))
    }

    def setTestWeave(String weaveType) {
        testWeave.set(WeaveOption.safeName(weaveType, WeaveOption.COMPILE))
    }
}
