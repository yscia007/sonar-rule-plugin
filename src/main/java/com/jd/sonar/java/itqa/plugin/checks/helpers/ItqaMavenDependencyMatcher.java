/*
 * Copyright 2018-2020 JD.com, Inc. QA Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.jd.sonar.java.itqa.plugin.checks.helpers;

import org.apache.commons.lang.StringUtils;
import org.sonar.java.checks.xml.maven.helpers.PatternMatcher;
import org.sonar.java.checks.xml.maven.helpers.RangedVersionMatcher;
import org.sonar.java.checks.xml.maven.helpers.StringMatcher;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: yangshuo8
 * @date: 2019-04-02 10:53
 * @desc: description
 */
public class ItqaMavenDependencyMatcher {

    private static final String GROUP_ARTIFACT_SEPARATOR = ":";
    private static final Integer DEPENDENCY_SPLIT_LENGTH = 2;
    private static final String VERSION_SEPARATOR = "-";
    private static final String MULTIPLE_SEPARATOR = "|";
    private static final String MULTIPLE_SEPARATOR_REGEX = "\\|";

    private static final StringMatcher ALWAYS_MATCHING_MATCHER = StringMatcher.any();
    private final StringMatcher groupIdMatcher;
    private final StringMatcher artifactIdMatcher;
    private final List<StringMatcher> versionMatchers;

    public ItqaMavenDependencyMatcher(String dependencyName, String version) {
        String[] name = dependencyName.split(GROUP_ARTIFACT_SEPARATOR);
        if (name.length != DEPENDENCY_SPLIT_LENGTH) {
            throw new IllegalArgumentException("Invalid dependency name. Should match '[groupId]:[artifactId]' use '*' as wildcard");
        }
        this.groupIdMatcher = getMatcherForPattern(name[0].trim());
        this.artifactIdMatcher = getMatcherForPattern(name[1].trim());
        this.versionMatchers = getMatchersForVersion(version);
    }

    private static StringMatcher getMatcherForPattern(String pattern) {
        boolean notSingleWildCard = !StringUtils.isBlank(pattern) && !isWildCard(pattern);
        return notSingleWildCard ? new PatternMatcher(pattern) : ALWAYS_MATCHING_MATCHER;
    }

    private static Boolean isWildCard(String pattern) {
        return "*".equals(pattern);
    }

    private static List<StringMatcher> getMatchersForVersion(String version) {
        List<StringMatcher> versionMatchers = new ArrayList<>(4);
        // 处理多个版本范围的情况，例如："2.1.2-2.3.34|2.5.0-2.5.13"
        if (version.contains(MULTIPLE_SEPARATOR)) {
            String[] versions = version.split(MULTIPLE_SEPARATOR_REGEX);
            for (String item : versions) {
                versionMatchers.add(getMatcherForVersion(item));
            }
            return versionMatchers;
        }
        versionMatchers.add(getMatcherForVersion(version));
        return versionMatchers;
    }

    private static StringMatcher getMatcherForVersion(String version) {
        if (version.contains(VERSION_SEPARATOR)) {
            String[] bounds = version.split(VERSION_SEPARATOR);
            return new RangedVersionMatcher(bounds[0], bounds[1]);
        }
        return getMatcherForPattern(version);
    }


    public Boolean matches(String groupId, String artifactId, String version) {
        for (StringMatcher stringMatcher : this.versionMatchers) {
            boolean matched = this.groupIdMatcher.test(groupId) && this.artifactIdMatcher.test(artifactId) && stringMatcher.test(version);
            if (matched) {
                return true;
            }
        }
        return false;
    }
}
