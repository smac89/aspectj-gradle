package com.github.smac89.aspectj

import groovy.transform.CompileStatic
import groovy.transform.TupleConstructor
import org.gradle.api.Project

/**
 * @author Chigozirim Chukwu
 */
@CompileStatic
@TupleConstructor(excludes = ['ajcOptions'])
class AspectJExtension {
    final Project project

    /**
     * DO NOT TOUCH THIS EXTENSION PROPERTY
     */
    @Deprecated
    @Delegate(excludes = ['project'])
    final Map<String, Object> ajcOptions = [:]
}
