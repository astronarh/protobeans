package org.protobeans.mvc.config;

import javax.servlet.ServletContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class MvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    public static ApplicationContext rootApplicationContext;
    
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] {DispatcherServletContextConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }

    @SuppressWarnings("resource")
    @Override
    protected void registerContextLoaderListener(ServletContext servletContext) {
        if (servletContext.getAttribute("rootAppCtx") == null) {
            AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
            
            ctx.setParent(rootApplicationContext);
    
            servletContext.addListener(new ContextLoaderListener(ctx));
            
            servletContext.setAttribute("rootAppCtx", ctx);
        }
    }
}
