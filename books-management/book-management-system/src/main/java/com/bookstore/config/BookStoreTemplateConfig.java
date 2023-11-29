package com.bookstore.config;

import freemarker.template.TemplateExceptionHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Objects;
import java.util.TimeZone;

@Configuration
public class BookStoreTemplateConfig {

    @Value("${templates.dir}")
    private String templatesDirPath;

    private File getTemplatesDir() throws FileNotFoundException {
        File templatesDir = new File(templatesDirPath);
        if (!Files.exists(templatesDir.toPath())) throw new FileNotFoundException("Templates Dir doesn't exist");
        return templatesDir;
    }

    @Bean
    @Primary
    public freemarker.template.Configuration freeMarkerTemplateConfig() throws IOException {
        freemarker.template.Configuration configuration = new freemarker.template.Configuration(freemarker.template.Configuration.VERSION_2_3_31);
        configuration.setDirectoryForTemplateLoading(getTemplatesDir());
        configuration.setDefaultEncoding("UTF-8");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        configuration.setLogTemplateExceptions(true);
        configuration.setWrapUncheckedExceptions(true);
        configuration.setFallbackOnNullLoopVariable(false);
        configuration.setSQLDateAndTimeTimeZone(TimeZone.getDefault());
        return configuration;
    }
}
