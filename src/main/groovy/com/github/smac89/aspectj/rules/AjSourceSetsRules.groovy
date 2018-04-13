package com.github.smac89.aspectj.rules

import com.github.smac89.aspectj.models.AJSourceSets
import org.gradle.api.Project
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.SourceSetContainer
import org.gradle.model.Defaults
import org.gradle.model.Model
import org.gradle.model.Path
import org.gradle.model.RuleSource

class AjSourceSetsRules extends RuleSource {
    @Model
    void ajSourceSets(AJSourceSets sourceSets) {}

    @Defaults
    void setSourceSets(AJSourceSets aJSourceSets, @Path("_project") Project project) {
        SourceSetContainer sourceSets = project.properties.get("sourceSets") as SourceSetContainer
        aJSourceSets.main = sourceSets.findByName(SourceSet.MAIN_SOURCE_SET_NAME)
        aJSourceSets.test = sourceSets.findByName(SourceSet.TEST_SOURCE_SET_NAME)
    }
}
