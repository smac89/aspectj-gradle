package com.github.smac89.aspectj

import org.gradle.testkit.runner.BuildResult
import org.gradle.testkit.runner.GradleRunner
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

import static org.gradle.testkit.runner.TaskOutcome.SUCCESS
import static org.hamcrest.CoreMatchers.equalTo
import static org.hamcrest.MatcherAssert.assertThat

class AspectJLoadTasksTest {
    @Rule
    public final TemporaryFolder testProjectDir = new TemporaryFolder()

    File buildFile

    @Before
    void setup() throws IOException {
        buildFile = testProjectDir.newFile("build.gradle")
        buildFile << """
            plugins {
                id 'com.github.smac89.aspectj'
            }

            ext {
                aspectjVersion = '1.8.13'
            }
        """.stripIndent()
    }

    @Test
    void willCreateLoadTimeTask() {
        buildFile << """
            model {
                aspectj {
                    appWeave = 'load'
                }
                tasks {
                    runAspectApplication {
                        main = 'Hello'
                    }
                }
            }
        """.stripIndent()

        testProjectDir.newFolder("src", "main", "java")
        testProjectDir.newFile("src/main/java/Hello.java") << """
        public class Hello {
            public static void main(String[] args) {}
        }
        """.stripIndent()

        BuildResult result = GradleRunner.create()
                .withProjectDir(testProjectDir.getRoot())
                .withArguments("runAspectApplication", "--stacktrace")
                .withPluginClasspath()
                .build()

        assertThat(result.task(":" + "runAspectApplication").getOutcome(), equalTo(SUCCESS))
    }
}
