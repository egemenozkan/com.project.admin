package com.project.client.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerAutoConfiguration;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import no.api.freemarker.java8.Java8ObjectWrapper;

@Configuration
public class FreemarkerConfig extends FreeMarkerAutoConfiguration {

    public FreemarkerConfig(ApplicationContext applicationContext, FreeMarkerProperties properties) {
		super(applicationContext, properties);
	}

	@Autowired
    private freemarker.template.Configuration configuration;

    @PostConstruct
    public void postConstruct() {
        configuration.setObjectWrapper(
                new Java8ObjectWrapper(freemarker.template.Configuration.getVersion())); // VERSION_2_3_26
    }
}
