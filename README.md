Gradle AspectJ plugin
=====================

##### Linux/OSX
[![Build Status](https://travis-ci.org/smac89/aspectj-gradle.svg?branch=master)](https://travis-ci.org/smac89/aspectj-gradle)
##### Windows
[![Build status](https://ci.appveyor.com/api/projects/status/gftqjxn7vowgqmna?svg=true)](https://ci.appveyor.com/project/smac89/aspectj-gradle)


Usage
-----

See https://plugins.gradle.org/plugin/com.github.smac89.aspectj

### Requirements
- Gradle 4+

### Configurations

Use the `aspectpath` `testAspectpath` configurations to specify external aspects or external code to weave

```groovy
dependencies {
    aspectpath "org.springframework:spring-aspects:${springVersion}"
}
```

_gradle.properties_

```groovy
aspectjVersion = '1.8.13'
```

**NOTE:** This plugin is rule based and as such follows the rule based model structure.
See the example project for an example configuration.


### [Aspectj Options](https://www.eclipse.org/aspectj/doc/released/devguide/antTasks-iajc.html)
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
