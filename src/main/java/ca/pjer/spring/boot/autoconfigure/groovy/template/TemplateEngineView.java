package ca.pjer.spring.boot.autoconfigure.groovy.template;

import groovy.lang.Writable;
import groovy.text.Template;
import groovy.text.TemplateEngine;
import org.springframework.core.io.Resource;
import org.springframework.web.servlet.view.AbstractTemplateView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

@SuppressWarnings({"unused", "WeakerAccess"})
public class TemplateEngineView extends AbstractTemplateView {
    private TemplateEngine templateEngine;
    private String charset = "UTF-8";
    private Template template;
    private long resourceLastModified = -1;

    public TemplateEngine getTemplateEngine() {
        return templateEngine;
    }

    public void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    protected Resource getResource(String url) {
        return getApplicationContext().getResource(url);
    }

    protected long getResourceLastModified(Resource resource) throws IOException {
        return resource.exists() && resource.isFile() ? resource.lastModified() : resourceLastModified;
    }

    protected Template createTemplate(Resource resource) throws IOException, ClassNotFoundException {
        Template template = this.template;
        long resourceLastModified = this.resourceLastModified;
        if (template == null || getResourceLastModified(resource) > resourceLastModified) {
            try (Reader reader = new InputStreamReader(resource.getInputStream(), getCharset())) {
                template = getTemplateEngine().createTemplate(reader);
                resourceLastModified = getResourceLastModified(resource);
            }
            this.template = template;
            this.resourceLastModified = resourceLastModified;
        }
        return template;
    }

    protected Writable makeTemplate(Template template, Map<String, Object> model) {
        return template.make(model);
    }

    protected void renderMergedTemplateModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        makeTemplate(createTemplate(getResource(getUrl())), model).writeTo(response.getWriter());
    }
}
