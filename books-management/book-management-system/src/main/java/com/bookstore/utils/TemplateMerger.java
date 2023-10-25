package com.bookstore.utils;


import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

@Component
public class TemplateMerger {

    private final Configuration configuration;

    @Autowired
    public TemplateMerger(Configuration configuration) {
        this.configuration = configuration;
    }

    public String getContentUsingTemplate(Map<String, Object> dataModel, String templateName) throws IOException, TemplateException {
        Template template = configuration.getTemplate(templateName + ".ftl");
        StringWriter writer = new StringWriter();
        template.process(dataModel, writer);
        return writer.toString();
    }
}
