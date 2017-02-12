package org.protobeans.mvc.config;

import javax.servlet.Filter;
import javax.servlet.ServletContext;

import org.protobeans.core.config.CoreConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

@Order(5)//для того, чтобы фильтры SpringSecurity инициализировались позже фильтров WebMVC
public class MvcInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    public static ApplicationContext rootApplicationContext;
    
    public static Filter[] filters;
    
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return null;
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return CoreConfig.getWebAppContextConfigClasses().toArray(new Class<?>[] {});
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }
    
    @Override
    protected Filter[] getServletFilters() {
        return filters;
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
