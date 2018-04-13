package com.github.smac89.aspectj.models

import org.gradle.model.Managed

@Managed
abstract class AJRuleExtension implements AJRuleExtensionBase {
    Map<String, String> asType(Class<Map<String, String>> clazz) {
        [
                fork         : fork as String,
                maxmem       : maxmem,
                xlint        : xlint,
                showWeaveInfo: showWeaveInfo as String,
                verbose      : verbose as String,
                log          : log,
                source       : source as String,
                target       : target as String,
                aspectpath   : aspectPath.asPath,
                encoding     : encoding.name(),
                outxml       : outxml as String,
        ]
    }
}
