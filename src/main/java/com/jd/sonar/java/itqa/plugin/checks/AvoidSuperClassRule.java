package com.jd.sonar.java.itqa.plugin.checks;

import com.google.common.collect.ImmutableList;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.ClassTree;
import org.sonar.plugins.java.api.tree.Tree;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: yangshuo27
 * @date: 2019/1/15
 * @time: 下午4:35
 * @desc: Avoid Super Class Rule Implementation.
 */
@Rule(
        key = "AvoidSuperClass",
        name = "AvoidSuperClass",
        description = "Only to bring out the unit test requirement about classpath when bytecode methods used",
        priority = Priority.MAJOR,
        tags = {"bug", "itqa"})
public class AvoidSuperClassRule extends IssuableSubscriptionVisitor {

    public static final List<String> SUPER_CLASS_AVOID = ImmutableList.of("org.slf4j.Logger");

    @Override
    public List<Tree.Kind> nodesToVisit() {
        // Register to the kind of nodes you want to be called upon visit.
        return ImmutableList.of(Tree.Kind.CLASS);
    }

    @Override
    public void visitNode(Tree tree) {
        // Visit CLASS node only => cast could be done
        ClassTree treeClazz = (ClassTree) tree;

        // No extends => stop to visit class
        if (treeClazz.superClass() == null) {
            return;
        }

        // For 'symbolType' usage, jar in dependencies must be on classpath, !unknownSymbol! result otherwise
        String superClassName = treeClazz.superClass().symbolType().fullyQualifiedName();

        // Check if superClass avoid
        if (SUPER_CLASS_AVOID.contains(superClassName)) {
            reportIssue(tree, String.format("The usage of super class %s is forbidden", superClassName));
        }
    }
}
