package com.github.smac89.aspectj

import groovy.transform.CompileStatic
import groovy.transform.TupleConstructor
import org.gradle.api.Project

/**
 * @author Chigozirim Chukwu
 */
@TupleConstructor
@CompileStatic
class AspectJExtension {
    @Deprecated
    final Project project

    @Deprecated
    @Delegate
    Map<String, String> ajcOptions = [:]
}
