package com.github.smac89.aspectj.models

import org.gradle.model.Managed

@Managed
abstract class AJRuleExtension implements AJRuleExtensionBase {
    Map<String, ?> asType(Class<Map<String, ?>> clazz) {
        [
                fork         : fork,
                maxmem       : maxmem,
                proc         : proc,
                Xlint        : xlint,
                showWeaveInfo: showWeaveInfo,
                verbose      : verbose,
                log          : log,
                source       : source,
                target       : target,
                aspectpath   : aspectPath?.asPath,
                encoding     : encoding.name().toLowerCase(),
                outxml       : outxml,
                debug        : debug,
        ]
    }
}
