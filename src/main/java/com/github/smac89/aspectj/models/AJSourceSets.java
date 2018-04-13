package com.github.smac89.aspectj.models;

import org.gradle.api.tasks.SourceSet;
import org.gradle.model.Managed;
import org.gradle.model.Unmanaged;

@Managed
public interface AJSourceSets {
    @Unmanaged
    SourceSet getMain();
    void setMain(SourceSet main);

    @Unmanaged
    SourceSet getTest();
    void setTest(SourceSet test);
}
