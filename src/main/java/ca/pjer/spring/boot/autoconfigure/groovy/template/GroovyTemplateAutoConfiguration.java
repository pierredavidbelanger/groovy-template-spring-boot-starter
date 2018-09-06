package ca.pjer.spring.boot.autoconfigure.groovy.template;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ComponentScan
public class GroovyTemplateAutoConfiguration implements WebMvcConfigurer {

    private final GroovyTemplateProperties properties;

    @Autowired
    public GroovyTemplateAutoConfiguration(GroovyTemplateProperties properties) {
        this.properties = properties;
    }

    public GroovyTemplateProperties getProperties() {
        return properties;
    }

    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.viewResolver(getProperties().getResolver());
    }
}
