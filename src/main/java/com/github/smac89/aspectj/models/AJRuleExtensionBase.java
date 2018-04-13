package com.github.smac89.aspectj.models;

import com.github.smac89.aspectj.internal.WeaveOption;
import org.gradle.api.JavaVersion;
import org.gradle.api.artifacts.Configuration;
import org.gradle.model.Managed;
import org.gradle.model.Unmanaged;

import java.nio.charset.Charset;

@Managed
public interface AJRuleExtensionBase {
    void setAppWeave(WeaveOption appWeave);
    WeaveOption getAppWeave();

    void setTestWeave(WeaveOption testWeave);
    WeaveOption getTestWeave();

    void setFork(boolean fork);
    boolean getFork();

    void setMaxmem(String maxmem);
    String getMaxmem();

    void setXlint(String Xlint);
    String getXlint();

    void setShowWeaveInfo(boolean showWeaveInfo);
    boolean getShowWeaveInfo();

    void setVerbose(boolean verbose);
    boolean getVerbose();

    void setLog(String log);
    String getLog();

    void setSource(JavaVersion source);
    JavaVersion getSource();

    void setTarget(JavaVersion target);
    JavaVersion getTarget();

    void setProc(String proc);
    String getProc();

    void setAspectPath(Configuration aspectPath);
    @Unmanaged
    Configuration getAspectPath();

    void setEncoding(Charset encoding);
    @Unmanaged
    Charset getEncoding();

    void setOutxml(boolean outxml);
    boolean getOutxml();
}
