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
package com.jd.sonar.java.itqa.plugin.checks.xml;

import org.sonar.api.utils.log.Logger;
import org.sonar.api.utils.log.Loggers;
import org.sonarsource.analyzer.commons.xml.XmlFile;
import org.sonarsource.analyzer.commons.xml.checks.SimpleXPathBasedCheck;
import org.sonarsource.analyzer.commons.xml.checks.SonarXmlCheck;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.annotation.CheckForNull;
import javax.xml.XMLConstants;
import javax.xml.namespace.NamespaceContext;
import javax.xml.xpath.*;
import java.util.Iterator;
import java.util.List;

/**
 * @author: yangshuo8
 * @date: 2019-10-30 11:27
 * @desc: description
 */
public abstract class AbstractScapXpathBasedCheck extends SonarXmlCheck {

    private static final Logger LOG = Loggers.get(SimpleXPathBasedCheck.class);
    private final XPath xpath = XPathFactory.newInstance().newXPath();

    public AbstractScapXpathBasedCheck() {
    }

    public XPathExpression getXPathExpression(String expression) {
        xpath.setNamespaceContext(new NamespaceContext() {
            @Override
            public String getNamespaceURI(String prefix) {
                if (prefix == null) {
                    throw new NullPointerException("Null prefix");
                } else if ("jsf".equals(prefix)) {
                    return "http://jsf.jd.com/schema/jsf";
                } else if ("xml".equals(prefix)) {
                    return XMLConstants.XML_NS_PREFIX;
                }
                return XMLConstants.NULL_NS_URI;
            }

            @Override
            public String getPrefix(String namespaceURI) {
                return null;
            }

            @Override
            public Iterator getPrefixes(String namespaceURI) {
                return null;
            }
        });
        try {
            return this.xpath.compile(expression);
        } catch (XPathExpressionException var3) {
            throw new IllegalStateException(String.format("[%s] Fail to compile XPath expression '%s'.", this.ruleKey(), expression), var3);
        }
    }

    @CheckForNull
    public NodeList evaluate(XPathExpression expression, Node node) {
        try {
            return (NodeList)expression.evaluate(node, XPathConstants.NODESET);
        } catch (XPathExpressionException var4) {
            if (LOG.isDebugEnabled()) {
                LOG.error(String.format("[%s] Unable to evaluate XPath expression on file %s", this.ruleKey(), this.inputFile()), var4);
            }

            return null;
        }
    }

    public List<Node> evaluateAsList(XPathExpression expression, Node node) {
        return XmlFile.asList(this.evaluate(expression, node));
    }

}
