QA Team Sonar Java Custom Rules
==========

This repository contains project you can directly clone to bootstrap your own project to write custom rules for Java.

Related documentation is there: http://docs.sonarqube.org/display/DEV/Adding+Coding+Rules+using+Java

SonarQube Home Page: https://www.sonarqube.org/

SonarLint: 
   - Intellij: https://www.sonarlint.org/intellij/
    
   - Eclipse: https://www.sonarlint.org/eclipse/

Sonar Custom Rule: https://github.com/SonarSource/sonar-custom-rules-examples

Sonar Custom Plugin: https://github.com/SonarSource/sonar-custom-plugin-example

## Preface
   
   SonarQube是一款开源的代码质量管理工具，可用于对团队代码质量进行管理，SonarLint是一款IDE检测插件，用于开发者本地执行自检，
   支持实时检测及commit检测，同时规则支持与SonarQube服务端同步，保持开发者本地与服务端规则的一致性，但SonarLint无法支持使用
   第三方扫描引擎实现的规则，例如已开源的p3c-pmd等，如果服务端使用了多种扫描引擎实现的规则，开发者本地无法通过使用SonarLint
   插件来应用其他扫描引擎实现的规则，给开发者带来不便，因此本项目主要通过使用SonarAnalyzer来实现部分p3c java编码规约，
   以便通过SonarLint应用p3c规则，同时也支持增加自定义编码规则。

## Environment
   
  - SonarQube: 6.7.3 (Community)
  - SonarJava: 5.11.0.17289
   
## Usage

1. maven：`mvn clean install`

2. copy target/sonar-java-itqa-plugin-1.0-SNAPSHOT.jar to your SonarQube installed directory.(extensions/plugins)

3. restart SonarQube service

## Rules
  p3c java 编码规范
  
  自定义编码规范
  
## Supported file types

  - .java 
  
  - Maven pom.xml 
  
  - Mybatis mapper.xml
    

## Join us
   If you have any questions or comments, please contact yangshuo by email at yangshuo8@jd.com,
   
   and please join us to make project itqa perfect for more programmers.

