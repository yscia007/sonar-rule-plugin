package com.jd.sonar.java.itqa.plugin;

import org.junit.Test;
import org.sonar.api.rules.RuleType;
import org.sonar.api.server.debt.DebtRemediationFunction.Type;
import org.sonar.api.server.rule.RuleParamType;
import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.api.server.rule.RulesDefinition.Param;
import org.sonar.api.server.rule.RulesDefinition.Repository;
import org.sonar.api.server.rule.RulesDefinition.Rule;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: yangshuo27
 * @date: 2019/1/15
 * @time: 下午3:54
 * @desc: itqa java xxx definition test.
 */
public class ItqaJavaRulesDefinitionTest {

    @Test
    public void test() {
        ItqaJavaRulesDefinition rulesDefinition = new ItqaJavaRulesDefinition();
        RulesDefinition.Context context = new RulesDefinition.Context();
        rulesDefinition.define(context);
        RulesDefinition.Repository repository = context.repository(ItqaJavaRulesDefinition.REPOSITORY_KEY);

        assertThat(repository.name()).isEqualTo("JD itqa Repository");
        assertThat(repository.language()).isEqualTo("java");
        assertThat(repository.rules()).hasSize(RulesList.getChecks().size());

        assertRuleProperties(repository);
        assertParameterProperties(repository);
        assertAllRuleParametersHaveDescription(repository);
    }

    private void assertParameterProperties(Repository repository) {
        // TooManyLinesInFunctionCheck
        Param max = repository.rule("AvoidAnnotation").param("name");
        assertThat(max).isNotNull();
        assertThat(max.defaultValue()).isEqualTo("Inject");
        assertThat(max.description()).isEqualTo("Name of the annotation to avoid, without the prefix @, for instance 'Override'");
        assertThat(max.type()).isEqualTo(RuleParamType.STRING);
    }

    private void assertRuleProperties(Repository repository) {
        Rule rule = repository.rule("AvoidAnnotation");
        assertThat(rule).isNotNull();
        assertThat(rule.name()).isEqualTo("Title of AvoidAnnotation");
        assertThat(rule.debtRemediationFunction().type()).isEqualTo(Type.CONSTANT_ISSUE);
        assertThat(rule.type()).isEqualTo(RuleType.CODE_SMELL);
    }

    private void assertAllRuleParametersHaveDescription(Repository repository) {
        for (Rule rule : repository.rules()) {
            for (Param param : rule.params()) {
                assertThat(param.description()).as("description for " + param.key()).isNotEmpty();
            }
        }
    }
}
