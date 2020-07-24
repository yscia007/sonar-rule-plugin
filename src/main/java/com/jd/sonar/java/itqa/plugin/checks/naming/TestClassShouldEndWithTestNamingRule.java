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
package com.jd.sonar.java.itqa.plugin.checks.naming;


import com.google.common.collect.Lists;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.JavaFileScanner;
import org.sonar.plugins.java.api.JavaFileScannerContext;
import org.sonar.plugins.java.api.semantic.Symbol;
import org.sonar.plugins.java.api.tree.BaseTreeVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;

import java.io.File;
import java.util.Deque;

/**
 * @author: zhangliwei29
 *  @date: 2019/2/19
 * */

@Rule(key = "p3c032")
public class TestClassShouldEndWithTestNamingRule extends BaseTreeVisitor implements JavaFileScanner {

    private Deque<Boolean> isEqualClassName = Lists.newLinkedList();

    private JavaFileScannerContext context;

    @Override
    public void scanFile(JavaFileScannerContext context) {
        this.context = context;
        scan(context.getTree());
        isEqualClassName.clear();
    }

    @Override
    public void visitClass(ClassTree tree) {
        String testClassName = "";
        String className = tree.simpleName().name();
        Symbol.TypeSymbol symbol = tree.symbol();
        File javaFile = context.getFile();
        String dir = javaFile.getParent();
        isEqualClassName.push(false);
        if (!symbol.isAbstract()&& !symbol.isInterface()){
            if (dir.contains("test") && dir.contains("java")) {
                if (className.endsWith("Test")){
                testClassName = className.substring(0,className.length()-4);}
                if (!(className.endsWith("Test")&&isEqualClassName.peek())) {
                    context.reportIssue(this, tree, "测试类命名以它要测试的类的名称开始，以 Test 结尾。");
                }
            }
        }
        if (testClassName.equals(className)){
            isEqualClassName.push(true);
        }

        isEqualClassName.pop();

    }
}
