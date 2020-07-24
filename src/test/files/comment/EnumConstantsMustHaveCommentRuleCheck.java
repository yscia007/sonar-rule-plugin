package com.jd.sonar.test.file;

public enum Level {
    HIGH, MEDIUM, LOW   // Noncompliant
}

public enum Level1 {
    /**
     * high, medium and low
     */
    HIGH, MEDIUM, LOW
}