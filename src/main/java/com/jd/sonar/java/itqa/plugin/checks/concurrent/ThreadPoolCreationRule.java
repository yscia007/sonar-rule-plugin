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
package com.jd.sonar.java.itqa.plugin.checks.concurrent;

import org.sonar.check.Rule;
import org.sonar.java.checks.methods.AbstractMethodDetection;
import org.sonar.java.matcher.MethodMatcher;
import org.sonar.java.matcher.NameCriteria;
import org.sonar.plugins.java.api.tree.MethodInvocationTree;

import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * @author: yangshuo27
 * @date: 2019/1/29
 * @time: 下午23:25
 * @desc: Thread Pool Creation Rule Implementation.
 */

@Rule(key = "p3c002")
public class ThreadPoolCreationRule extends AbstractMethodDetection {

   private static final MethodMatcher THREAD_EXECUTORS = MethodMatcher.create().typeDefinition("java.util.concurrent.Executors").name(NameCriteria.startsWith("new")).withAnyParameters();

    @Override
    protected List<MethodMatcher> getMethodInvocationMatchers() {
        return Collections.singletonList(THREAD_EXECUTORS);
    }

    @Override
    protected void onMethodInvocationFound(MethodInvocationTree mit) {
        reportIssue(mit, "请通过ThreadPoolExecutor的方式创建线程池，这样效果会更好哦~");
    }
}
