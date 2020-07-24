package com.jd.sonar.java.itqa.plugin;

import org.sonar.api.Plugin;

/**
 * Created with IntelliJ IDEA.
 *
 * @author: yangshuo27
 * @date: 2019/1/15
 * @time: 下午1:58
 * @desc: itqa java rule plugin
 */
public class ItqaJavaRulesPlugin implements Plugin {

    @Override
    public void define(Context context) {

        // server extensions -> objects are instantiated during server startup
        context.addExtension(ItqaJavaRulesDefinition.class);

        // batch extensions -> objects are instantiated during code analysis
        context.addExtension(ItqaJavaFileCheckRegistrar.class);

        context.addExtension(XmlFileSensor.class);
    }
}
