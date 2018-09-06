package ca.pjer.spring.boot.autoconfigure.groovy.template;

import groovy.text.TemplateEngine;
import groovy.text.XmlTemplateEngine;
import groovy.util.XmlParser;
import org.springframework.web.servlet.view.AbstractUrlBasedView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

@SuppressWarnings({"unused", "WeakerAccess"})
public class TemplateEngineViewResolver extends UrlBasedViewResolver {
    private String charset = "UTF-8";
    private Class<? extends TemplateEngine> templateEngineClass;
    private TemplateEngine templateEngine;

    public TemplateEngineViewResolver(String suffix, String contentType, Class<? extends TemplateEngine> templateEngineClass) {
        this.templateEngineClass = templateEngineClass;
        setViewClass(TemplateEngineView.class);
        setPrefix("classpath:/templates/");
        setSuffix(suffix);
        setContentType(contentType);
    }

    public String getPrefix() {
        return super.getPrefix();
    }

    public String getSuffix() {
        return super.getSuffix();
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public Class<? extends TemplateEngine> getTemplateEngineClass() {
        return templateEngineClass;
    }

    public void setTemplateEngineClass(Class<? extends TemplateEngine> templateEngineClass) {
        this.templateEngineClass = templateEngineClass;
    }

    protected TemplateEngine createTemplateEngine() throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        Class<? extends TemplateEngine> templateEngineClass = getTemplateEngineClass();
        if (XmlTemplateEngine.class.isAssignableFrom(templateEngineClass)) {
            XmlParser xmlParser = new XmlParser(false, true);
            xmlParser.setTrimWhitespace(true);
            XmlTemplateEngine templateEngine = new XmlTemplateEngine(xmlParser, classLoader);
            templateEngine.setIndentation("");
            return templateEngine;
        }
        return getTemplateEngineClass().getConstructor(ClassLoader.class).newInstance(classLoader);
    }

    protected TemplateEngine getTemplateEngine() throws Exception {
        TemplateEngine templateEngine = this.templateEngine;
        if (templateEngine == null) {
            templateEngine = createTemplateEngine();
            this.templateEngine = templateEngine;
        }
        return templateEngine;
    }

    @Override
    protected AbstractUrlBasedView buildView(String viewName) throws Exception {
        TemplateEngineView view = (TemplateEngineView) super.buildView(viewName);
        view.setTemplateEngine(getTemplateEngine());
        view.setContentType(getContentType());
        view.setCharset(getCharset());
        return view;
    }
}
