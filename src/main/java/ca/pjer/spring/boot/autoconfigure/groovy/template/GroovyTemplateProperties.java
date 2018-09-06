package ca.pjer.spring.boot.autoconfigure.groovy.template;

import groovy.text.XmlTemplateEngine;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "groovy.template")
@SuppressWarnings({"unused", "WeakerAccess"})
public class GroovyTemplateProperties {

    private final TemplateEngineViewResolver resolver = new TemplateEngineViewResolver(".html", "text/html", XmlTemplateEngine.class);

    public TemplateEngineViewResolver getResolver() {
        return resolver;
    }
}
