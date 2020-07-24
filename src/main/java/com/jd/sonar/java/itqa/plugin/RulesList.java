package com.jd.sonar.java.itqa.plugin;

import com.google.common.collect.ImmutableList;
import com.jd.sonar.java.itqa.plugin.checks.*;
import com.jd.sonar.java.itqa.plugin.checks.comment.ClassMustHaveAuthorRule;
import com.jd.sonar.java.itqa.plugin.checks.comment.CommentsMustBeJavadocFormatRule;
import com.jd.sonar.java.itqa.plugin.checks.comment.EnumConstantsMustHaveCommentRule;
import com.jd.sonar.java.itqa.plugin.checks.concurrent.*;
import com.jd.sonar.java.itqa.plugin.checks.constant.UndefineMagicConstantRule;
import com.jd.sonar.java.itqa.plugin.checks.constant.UpperEllRule;
import com.jd.sonar.java.itqa.plugin.checks.exception.AvoidReturnInFinallyRule;
import com.jd.sonar.java.itqa.plugin.checks.exception.TransactionMustHaveRollbackRule;
import com.jd.sonar.java.itqa.plugin.checks.flowcontrol.AvoidComplexConditionRule;
import com.jd.sonar.java.itqa.plugin.checks.flowcontrol.NeedBraceRule;
import com.jd.sonar.java.itqa.plugin.checks.flowcontrol.SwitchStatementRule;
import com.jd.sonar.java.itqa.plugin.checks.naming.*;
import com.jd.sonar.java.itqa.plugin.checks.oop.*;
import com.jd.sonar.java.itqa.plugin.checks.other.AvoidApacheBeanUtilsCopyRule;
import com.jd.sonar.java.itqa.plugin.checks.other.AvoidMissUseOfMathRandomRule;
import com.jd.sonar.java.itqa.plugin.checks.other.AvoidNewDateGetTimeRule;
import com.jd.sonar.java.itqa.plugin.checks.other.AvoidPatternCompileInMethodRule;
import com.jd.sonar.java.itqa.plugin.checks.performance.*;
import com.jd.sonar.java.itqa.plugin.checks.security.JavaIoDeserializationMethodCheck;
import com.jd.sonar.java.itqa.plugin.checks.set.*;
import com.jd.sonar.java.itqa.plugin.checks.xml.maven.InsecureDependenciesCheck;
import com.jd.sonar.java.itqa.plugin.checks.xml.mybatis.ArbitrarySqlCommandExecutionCheck;
import com.jd.sonar.java.itqa.plugin.checks.xml.mybatis.DollarSignInSqlStatementCheck;
import com.jd.sonar.java.itqa.plugin.checks.xml.mybatis.SqlStatementProbeCheck;
import com.jd.sonar.java.itqa.plugin.checks.xml.mybatis.WildcardInSqlQueryStatementCheck;
import com.jd.sonar.java.itqa.plugin.checks.xml.spring.SpringDefaultLazyInitCheck;
import com.jd.sonar.java.itqa.plugin.checks.xml.spring.SpringJsfProviderCheck;
import org.sonar.plugins.java.api.JavaCheck;
import org.sonarsource.analyzer.commons.xml.checks.SonarXmlCheck;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: yangshuo27
 * @date: 2019/1/15
 * @time: 下午2:38
 * @desc: description
 */
public final class RulesList {

    private RulesList() {
    }

    public static List<Class> getChecks() {
        return ImmutableList.<Class>builder()
                .addAll(getJavaChecks())
                .addAll(getJavaTestChecks())
                .addAll(getXmlChecks())
                .build();
    }

    public static List<Class<? extends JavaCheck>> getJavaChecks() {
        return ImmutableList.<Class<? extends JavaCheck>>builder()
                // comment
                .add(ClassMustHaveAuthorRule.class)
                .add(CommentsMustBeJavadocFormatRule.class)
                .add(EnumConstantsMustHaveCommentRule.class)
                // concurrent
                .add(AvoidCallStaticSimpleDateFormatRule.class)
                .add(AvoidConcurrentCompetitionRandomRule.class)
                .add(AvoidManuallyCreateThreadRule.class)
                .add(AvoidUseTimerRule.class)
                .add(ThreadLocalShouldRemoveRule.class)
                .add(ThreadPoolCreationRule.class)
                .add(ThreadShouldSetNameRule.class)
                .add(CountDownShouldInFinallyRule.class)
                // constant
                .add(UndefineMagicConstantRule.class)
                .add(UpperEllRule.class)
                // exception
                .add(TransactionMustHaveRollbackRule.class)
                .add(AvoidReturnInFinallyRule.class)
                // flow control
                .add(AvoidComplexConditionRule.class)
                .add(NeedBraceRule.class)
                .add(SwitchStatementRule.class)
                // naming
                .add(AbstractClassShouldStartWithAbstractOrBaseRule.class)
                .add(ArrayNamingShouldHaveBracketRule.class)
                .add(AvoidStartWithDollarAndUnderLineNamingRule.class)
                .add(ClassNamingShouldBeCamelRule.class)
                .add(ConstantFieldShouldBeUpperCaseRule.class)
                .add(ExceptionClassShouldEndWithExceptionRule.class)
                .add(LowerCamelCaseVariableNamingRule.class)
                .add(PackageNamingRule.class)
                .add(ServiceOrDaoClassShouldEndWithImplRule.class)
                // oop
                .add(PojoMustOverrideToStringRule.class)
                .add(PojoMustUsePrimitiveFieldRule.class)
                .add(PojoNoDefaultValueRule.class)
                .add(WrapperTypeEqualityRule.class)
                .add(EqualsAvoidNullRule.class)
                // other
                .add(AvoidApacheBeanUtilsCopyRule.class)
                .add(AvoidMissUseOfMathRandomRule.class)
                .add(AvoidNewDateGetTimeRule.class)
                .add(AvoidPatternCompileInMethodRule.class)
                // set
                .add(ClassCastExceptionWithSubListToArrayListRule.class)
                .add(ClassCastExceptionWithToArrayRule.class)
                .add(CollectionInitShouldAssignCapacityRule.class)
                .add(ConcurrentExceptionWithModifyOriginSubListRule.class)
                .add(UnsupportedExceptionWithModifyAsListRule.class)
                .add(CollectionEmptyShouldBeDetectedByIsEmptyRule.class)
                .add(MapShouldBeTraversedByEntrySetRule.class)
                .add(DontPassCollectionToItselfRule.class)
                .add(SetShouldBeUsedToCallContainsRule.class)
                .add(SplitShouldBePaidAttentionRule.class)
                // performance
                .add(AvoidComputeSizeRepeatedlyInLoopRule.class)
                .add(AvoidUseRecursiveFunctionRule.class)
                .add(AvoidUseSynchronizedInWhileLoopRule.class)
                .add(AvoidUseSynchronizedOnMethodRule.class)
                .add(AvoidUseSystemGcRule.class)
                // Unclassified
                .add(AvoidIfElseNestingMoreThanThreeRule.class)
                .add(CompareEnumMemberEqualsRule.class)
                .add(DontUseTryCatchInLoopRule.class)
                .add(MethodCyclomaticComplexityCheck.class)
                .add(MethodCognitiveComplexityCheck.class)
                .add(JavaIoDeserializationMethodCheck.class)
                .build();
    }

    public static List<Class<? extends JavaCheck>> getJavaTestChecks() {
        return ImmutableList.<Class<? extends JavaCheck>>builder()
                .build();
    }

    public static List<Class<? extends SonarXmlCheck>> getXmlChecks() {
        return ImmutableList.<Class<? extends SonarXmlCheck>>builder()
                .add(SpringDefaultLazyInitCheck.class)
                .add(InsecureDependenciesCheck.class)
                .add(DollarSignInSqlStatementCheck.class)
                .add(WildcardInSqlQueryStatementCheck.class)
                .add(ArbitrarySqlCommandExecutionCheck.class)
                .add(SqlStatementProbeCheck.class)
                .add(SpringJsfProviderCheck.class)
                .build();
    }
}
