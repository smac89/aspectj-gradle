Gradle AspectJ plugin
=====================

Usage
-----

See https://plugins.gradle.org/plugin/com.github.smac89.aspectj

_build.gradle_

### New Style

```groovy
plugins {
  id "com.github.smac89.aspectj" version "0.1.0"
}
```

### Old Style
```groovy
buildscript {
  repositories {
    maven {
      url "https://plugins.gradle.org/m2/"
    }
  }
  dependencies {
    classpath "gradle.plugin.com.github.smac89.aspectj:aspectj-gradle:0.1.0"
  }
}

apply plugin: "com.github.smac89.aspectj"
```


_gradle.properties_

```groovy
aspectjVersion = '1.8.13'
```

**NOTE:** This plugin is rule based and as such follows the model-like structure.
See the example project for an example configuration.

### Requirements
Compatible with Gradle 4+

Use the `aspectpath` `testAspectpath` configurations to specify external aspects or external code to weave

```groovy
dependencies {
    aspectpath "org.springframework:spring-aspects:${springVersion}"
}
```

### Options
By default, the following options are set in the `aspectj` rule:

```groovy
fork         : true,
maxmem       : '1024m',
proc         : 'none',
Xlint        : 'ignore',
showWeaveInfo: true,
verbose      : false,
log          : 'iajc.log',
source       : <project_default>,
target       : <project_default>,
encoding     : 'utf-8',
outxml       : true,
```

These options can either be configured in the `aspectj` rule or individually
for the `compileAspect` and `compileTestAspect` tasks and these will take precedence
over the default ones, for that task

```groovy
model {
    tasks {
        compileAspect {
            additionalAjcArgs {
                xlint = 'warning'
                maxmem = '1024m'
            }
        }
    }
}

```

See [ajc arguments](http://www.eclipse.org/aspectj/doc/released/devguide/antTasks-iajc.html#antTasks-iajc-options)
