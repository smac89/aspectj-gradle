package com.github.smac89.aspectj.internal

import org.gradle.api.provider.Provider
import org.gradle.api.tasks.Input

trait AspectTaskWithDefaults {
    private final Provider<Map<String, String>> defaultAjcArgs = project.provider {
        project.aspectj.ajcOptions.findResults { prop, value ->
            value == null ?: [prop.toString(), value.toString()]
        }.collectEntries() as Map<String, String>
    }

    @Input
    Map<String, String> getDefaultAjcArgs() {
        defaultAjcArgs.get()
    }
}
