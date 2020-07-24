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
package com.jd.sonar.java.itqa.plugin.checks.flowcontrol;

import com.google.common.collect.ImmutableList;
import org.sonar.check.Rule;
import org.sonar.plugins.java.api.IssuableSubscriptionVisitor;
import org.sonar.plugins.java.api.tree.SwitchStatementTree;
import org.sonar.plugins.java.api.tree.Tree;
import org.sonar.plugins.java.api.tree.Tree.Kind;

import java.util.List;

/**
 * @author: zhangliwei29
 *  @date: 2019/2/15
 * */

@Rule(key = "p3c020")
public class SwitchStatementRule extends IssuableSubscriptionVisitor {

    @Override
    public List<Kind> nodesToVisit() {
        return ImmutableList.of(Kind.SWITCH_STATEMENT);
    }


    @Override
    public void visitNode(Tree tree) {
        SwitchStatementTree switchStatement = (SwitchStatementTree) tree;
//            List<CaseGroupTree> caseGroupTrees = switchStatement.cases();
//            CFG cfg = CFG.buildCFG(Collections.singletonList(tree), true);
//            Set<CFG.Block> switchSuccessors = cfg.entryBlock().successors();
//
//            Map<CFG.Block, CaseGroupTree> cfgBlockToCaseGroupMap = createMapping(switchSuccessors, caseGroupTrees);
//            switchSuccessors.stream()
//                    .filter(cfgBlockToCaseGroupMap.keySet()::contains)
//                    .flatMap(cfgBlock -> getForbiddenCaseGroupPredecessors(cfgBlock, cfgBlockToCaseGroupMap))
//                    .map(CaseGroupTree::labels)
//                    .map(caseGroupLabels -> caseGroupLabels.get(caseGroupLabels.size() - 1))
//                    .forEach(label -> reportIssue(label, "End this switch case with an unconditional break, return or throw statement."));

        if(!hasDefaultClause(switchStatement)) {
            reportIssue(tree, "在一个 switch 块内，都必须包含一个 default 语句并且放在最后，即使它什么代码也没有。");
        }
    }


    private static boolean hasDefaultClause(SwitchStatementTree switchStatement) {
        return switchStatement.cases().stream()
                .flatMap(caseGroupTree -> caseGroupTree.labels().stream())
                .anyMatch(caseLabelTree -> caseLabelTree.caseOrDefaultKeyword().text().equals("default"));
    }

//    private static Map<CFG.Block, CaseGroupTree> createMapping(Set<CFG.Block> switchSuccessors, List<CaseGroupTree> caseGroupTrees) {
//        return switchSuccessors.stream()
//                .filter(cfgBlock -> cfgBlock.caseGroup() != null && caseGroupTrees.contains(cfgBlock.caseGroup()))
//                .collect(
//                        Collectors.toMap(
//                                Function.identity(),
//                                CFG.Block::caseGroup));
//    }
//
//    private static Stream<CaseGroupTree> getForbiddenCaseGroupPredecessors(CFG.Block cfgBlock, Map<CFG.Block, CaseGroupTree> cfgBlockToCaseGroupMap) {
//        CaseGroupTree caseGroup = cfgBlockToCaseGroupMap.get(cfgBlock);
//        return cfgBlock.predecessors().stream()
//                .map(predecessor -> getForbiddenCaseGroupPredecessor(predecessor, cfgBlockToCaseGroupMap, new HashSet<>()))
//                .filter(Objects::nonNull)
//                .filter(predecessor -> !intentionalFallThrough(predecessor, caseGroup))
//                .distinct();
//    }
//
//    @Nullable
//    private static CaseGroupTree getForbiddenCaseGroupPredecessor(CFG.Block predecessor, Map<CFG.Block, CaseGroupTree> cfgBlockToCaseGroupMap, Set<CFG.Block> seen) {
//        if (cfgBlockToCaseGroupMap.get(predecessor) != null) {
//            return cfgBlockToCaseGroupMap.get(predecessor);
//        }
//
//        if (seen.contains(predecessor)) {
//            return null;
//        }
//
//        seen.add(predecessor);
//        return predecessor.predecessors().stream()
//                .map(previousPredecessors -> getForbiddenCaseGroupPredecessor(previousPredecessors, cfgBlockToCaseGroupMap, seen))
//                .filter(Objects::nonNull)
//                .findFirst()
//                .orElse(null);
//    }
//
//    private static boolean intentionalFallThrough(Tree caseGroup, Tree nextCaseGroup) {
//        // Check first token of next case group when comment is last element of case group it is attached to next group.
//        FallThroughCommentVisitor visitor = new FallThroughCommentVisitor();
//        List<Tree> treesToScan = ImmutableList.<Tree>builder().addAll(((CaseGroupTree) caseGroup).body())
//                .add(nextCaseGroup.firstToken()).build();
//        visitor.scan(treesToScan);
//        return visitor.hasComment;
//    }
//
//    private static class FallThroughCommentVisitor extends SubscriptionVisitor {
//        private static final Pattern FALL_THROUGH_PATTERN = Pattern.compile("falls?[\\-\\s]?thro?u[gh]?", Pattern.CASE_INSENSITIVE);
//        boolean hasComment = false;
//
//        @Override
//        public List<Tree.Kind> nodesToVisit() {
//            return Collections.singletonList(Tree.Kind.TRIVIA);
//        }
//
//        @Override
//        public void visitTrivia(SyntaxTrivia syntaxTrivia) {
//            if (!hasComment && FALL_THROUGH_PATTERN.matcher(syntaxTrivia.comment()).find()) {
//                hasComment = true;
//            }
//        }
//
//        private void scan(List<Tree> trees) {
//            for (Tree tree : trees) {
//                if (hasComment) {
//                    return;
//                }
//                scanTree(tree);
//            }
//        }
//    }


}
