package com.bookstore.config;

import freemarker.template.TemplateExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.TimeZone;

//@Configuration
public class BookStoreTemplateConfig {

    //@Bean
    //@Primary
    public freemarker.template.Configuration freeMarkerTemplateConfig() throws IOException {
        freemarker.template.Configuration configuration = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_31);
        configuration.setDirectoryForTemplateLoading(Paths.get("resources/templates").toFile());
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        configuration.setLogTemplateExceptions(true);
        configuration.setWrapUncheckedExceptions(true);
        configuration.setFallbackOnNullLoopVariable(false);
        configuration.setSQLDateAndTimeTimeZone(TimeZone.getDefault());
        return configuration;
    }
}
