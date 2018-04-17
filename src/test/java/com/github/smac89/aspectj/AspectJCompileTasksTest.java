package com.github.smac89.aspectj;

import org.gradle.testkit.runner.BuildResult;
import org.gradle.testkit.runner.GradleRunner;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS;
import static org.gradle.util.GFileUtils.writeFile;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class AspectJCompileTasksTest {
    @Rule
    public final TemporaryFolder testProjectDir = new TemporaryFolder();

    @Parameters(name = "Creates task {0}")
    public static Collection<String> tasks() {
        return Arrays.asList("compileAspect", "compileTestAspect");
    }

    @Parameter
    public String taskName;

    @Before
    public void setup() throws IOException {
        File buildFile = testProjectDir.newFile("build.gradle");
        writeFile("plugins { id 'com.github.smac89.aspectj' } \n model { aspectj { testWeave 'compile' }}\n",
                  buildFile, StandardCharsets.UTF_8.name());
    }

    @Test
    public void willCreateCompileTask() {
        BuildResult result = GradleRunner.create()
                .withProjectDir(testProjectDir.getRoot())
                .withArguments(taskName, "--stacktrace")
                .withPluginClasspath()
                .build();

        assertThat(result.task(":" + taskName).getOutcome(), equalTo(SUCCESS));
    }
}
