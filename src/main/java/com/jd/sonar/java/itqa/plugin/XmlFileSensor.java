package com.jd.sonar.java.itqa.plugin;

import com.google.common.annotations.VisibleForTesting;
import lombok.extern.slf4j.Slf4j;
import org.sonar.api.batch.fs.FilePredicate;
import org.sonar.api.batch.fs.FileSystem;
import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.batch.rule.CheckFactory;
import org.sonar.api.batch.rule.Checks;
import org.sonar.api.batch.sensor.Sensor;
import org.sonar.api.batch.sensor.SensorContext;
import org.sonar.api.batch.sensor.SensorDescriptor;
import org.sonar.api.rule.RuleKey;
import org.sonarsource.analyzer.commons.ProgressReport;
import org.sonarsource.analyzer.commons.xml.ParseException;
import org.sonarsource.analyzer.commons.xml.XmlFile;
import org.sonarsource.analyzer.commons.xml.checks.SonarXmlCheck;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author: yangshuo8
 * @date: 2019-03-30 17:35
 * @desc: description
 */
@Slf4j
public class XmlFileSensor implements Sensor {

    private final Checks<SonarXmlCheck> checks;

    public XmlFileSensor(CheckFactory checkFactory) {
        this.checks = checkFactory.<SonarXmlCheck>create(ItqaJavaRulesDefinition.REPOSITORY_KEY).addAnnotatedChecks((Iterable) RulesList.getXmlChecks());
    }

    @Override
    public void describe(SensorDescriptor descriptor) {
        descriptor.name("JavaXmlSensor").onlyWhenConfiguration(configuration -> !checks.all().isEmpty());
    }

    @Override
    public void execute(SensorContext context) {
        FileSystem fs = context.fileSystem();
        FilePredicate xmlFilesPredicate = fs.predicates().matchesPathPattern("**/*.xml");

        List<InputFile> inputFiles = new ArrayList<>();
        fs.inputFiles(xmlFilesPredicate).forEach(inputFile1 -> {
            context.markForPublishing(inputFile1);
            inputFiles.add(inputFile1);
        });

        if (inputFiles.isEmpty()) {
            return;
        }

        ProgressReport progressReport = new ProgressReport("Report about progress of Java XML analyzer", TimeUnit.SECONDS.toMillis(10));
        progressReport.start(inputFiles.stream().map(InputFile::toString).collect(Collectors.toList()));

        boolean successfullyCompleted = false;
        boolean cancelled = false;
        try {
            for (InputFile inputFile : inputFiles) {
                if (context.isCancelled()) {
                    cancelled = true;
                    break;
                }
                scanFile(context, inputFile);
                progressReport.nextFile();
            }
            successfullyCompleted = !cancelled;
        } finally {
            if (successfullyCompleted) {
                progressReport.stop();
            } else {
                progressReport.cancel();
            }
        }
    }

    private void scanFile(SensorContext context, InputFile inputFile) {
        XmlFile xmlFile;
        try {
            xmlFile = XmlFile.create(inputFile);
        } catch (ParseException | IOException e) {
            log.debug("Skipped '{}' due to parsing error", inputFile);
            return;
        } catch (Exception e) {
            // Our own XML parsing may have failed somewhere, so logging as warning to appear in logs
            log.warn(String.format("Unable to analyse file '%s'.", inputFile), e);
            return;
        }

        checks.all().forEach(check -> {
            RuleKey ruleKey = checks.ruleKey(check);
            scanFile(context, xmlFile, check, ruleKey);
        });
    }

    @VisibleForTesting
    void scanFile(SensorContext context, XmlFile xmlFile, SonarXmlCheck check, RuleKey ruleKey) {
        try {
            check.scanFile(context, ruleKey, xmlFile);
        } catch (Exception e) {
            log.error(String.format("Failed to analyze '%s' with rule %s", xmlFile.getInputFile().toString(), ruleKey), e);
        }
    }
}
