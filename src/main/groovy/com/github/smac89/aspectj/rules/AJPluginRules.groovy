package com.github.smac89.aspectj.rules

import com.github.smac89.aspectj.AspectJExtension
import com.github.smac89.aspectj.internal.WeaveOption
import com.github.smac89.aspectj.models.AJRuleExtension
import com.github.smac89.aspectj.tasks.AspectJTask
import org.gradle.api.*
import org.gradle.api.plugins.ExtensionContainer
import org.gradle.api.tasks.JavaExec
import org.gradle.api.tasks.SourceSet
import org.gradle.api.tasks.testing.Test
import org.gradle.jvm.tasks.Jar
import org.gradle.model.*

import java.nio.charset.StandardCharsets

class AJPluginRules extends RuleSource {

    @Model
    void aspectj(AJRuleExtension aspectj) {}

    @Model
    Project _project(@Path("extensionContainer") final ExtensionContainer extensions) {
        extensions.getByType(AspectJExtension).project
    }

    @Defaults
    void setDefaultOptions(AJRuleExtension aspectJ, @Path("_project") Project project) {
        println "Defaults for extension"
        aspectJ.appWeave = WeaveOption.COMPILE
        aspectJ.testWeave = WeaveOption.COMPILE

        aspectJ.fork = true
        aspectJ.maxmem = '1024m'

        aspectJ.xlint = 'ignore'
        aspectJ.showWeaveInfo = true
        aspectJ.verbose = false
        aspectJ.log = 'iajc.log'

        aspectJ.source = JavaVersion.toVersion(project.properties.sourceCompatibility)
        aspectJ.target = JavaVersion.toVersion(project.properties.targetCompatibility)

        aspectJ.proc = 'none'
        aspectJ.encoding = StandardCharsets.UTF_8
        aspectJ.outxml = true

        aspectJ.aspectPath = project.configurations.findByName("aspects")
    }

    @Finalize
    void createTaskDefaultOptions(AJRuleExtension aspectJ, @Path("_project") Project project) {
        println "Finalizing project options"
        project.extensions.aspectjext << (aspectJ as Map)
    }

    @Mutate
    void createMainTasks(ModelMap<Task> tasks,
                         @Path("_project") Project project,
                         @Path("aspectj.appWeave") WeaveOption appWeave,
                         @Path("ajSourceSets.main") SourceSet sourceSet) {
        println "Creating main tasks"
        if (appWeave == WeaveOption.COMPILE) {
            createCompileTask(tasks, sourceSet, "compileAspect", "jar") {
                it.destDir = "${project.buildDir}/aspect/"
            }
        } else {
            createLoadTask(tasks, "runAspectApplication", sourceSet) {
                it.jvmArgs += "-javaagent:${project.configurations.weave.asPath}"
            }
        }
    }

    @Mutate
    void createTestTasks(ModelMap<Task> tasks,
                         @Path("_project") Project project,
                         @Path("aspectj.testWeave") WeaveOption testWeave,
                         @Path("ajSourceSets.test") SourceSet sourceSet) {
        println "Creating test tasks"
        if (testWeave == WeaveOption.COMPILE) {
            def jarTaskName = "aspectTestJar"
            createCompileTask(tasks, sourceSet, "compileTestAspect", jarTaskName) {
                it.destDir = "${project.buildDir}/test-aspect/"
            }

            tasks.create(jarTaskName, Jar) {
                it.group = 'build'
                it.classifier = 'tests'
                it.from sourceSet.output.classesDirs
            }
        } else {
            tasks.withType(Test).get('test').configure {
                jvmArgs += "-javaagent:${project.configurations.weave.asPath}"
            }
        }
    }

    @Validate
    void testWeaveOptionNotNull(@Path("aspectj.testWeave") WeaveOption testWeave) {
        println "Check test weave for null"
        checkAssert("") {
            assert testWeave != null
        }
    }

    @Validate
    void appWeaveOptionNotNull(@Path("aspectj.appWeave") WeaveOption appWeave) {
        println "Check app weave for null"
        checkAssert("") {
            assert appWeave != null
        }
    }

    private static void createCompileTask(ModelMap<Task> tasks, SourceSet sourceSet,
                                   String taskName, String jarTaskName, Action<? super AspectJTask> configure = {}) {
        tasks.create(taskName, AspectJTask) {
            it.group = "ajc"
            it.dependsOn += jarTaskName
            it.description = "Compiles aspectj for ${sourceSet.name} jar"

            it.sourceSet = sourceSet
            it.additionalAjcArgs {
                classpath = (sourceSet.runtimeClasspath + sourceSet.compileClasspath).filter { it.exists() }.asPath
                inpath = tasks.withType(Jar).get(jarTaskName)?.archivePath?.absolutePath
            }
            configure.execute(it)
        }
    }

    private static void createLoadTask(ModelMap<Task> tasks, String taskName, SourceSet sourceSet,
                                       Action<? super JavaExec> configure = {}) {
        tasks.create(taskName, JavaExec) {
            it.description = "Run aspectJ application and weave aspect at load time for ${sourceSet.name}"
            it.group = 'ajc'

            it.classpath = (sourceSet.runtimeClasspath + sourceSet.compileClasspath).filter { it.exists() }
            configure.execute(it)
        }
    }

    private static void checkAssert(final String message, final Closure assertion) {
        try {
            assertion()
        } catch (AssertionError error) {
            final exceptionMessage = new StringBuilder(error.message)
            if (message) {
                exceptionMessage << System.lineSeparator() << System.lineSeparator() << message
            }

            throw new GradleException(exceptionMessage.toString(), error)
        }
    }
}
