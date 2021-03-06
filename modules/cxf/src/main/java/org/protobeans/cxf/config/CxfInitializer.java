package org.protobeans.cxf.config;

import java.util.Collections;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.SessionTrackingMode;
import javax.servlet.ServletRegistration.Dynamic;

import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ConfigurableWebApplicationContext;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextListener;

public class CxfInitializer implements WebApplicationInitializer {
    public static String SERVLET_PATH;
    
    public static ConfigurableWebApplicationContext rootApplicationContext;
    
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.addListener(RequestContextListener.class);//Для того, чтобы запрос был доступен в фильтрах, например в SocialAuthenticationFilter
        servletContext.setSessionTrackingModes(Collections.singleton(SessionTrackingMode.COOKIE));

        servletContext.setAttribute(WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE, rootApplicationContext);

        if (rootApplicationContext.getServletContext() == null) {//in case then spring-test already inject MockServletContext
            rootApplicationContext.setServletContext(servletContext);
            
            //Некоторые классы вроде WebMvcConfigurationSupport зависят от servletContext, но получается, что пост-процессор
            //почему-то срабатывает позже, чем это нужно поэтому распихиваем servletContext вручную
            ConfigurableListableBeanFactory bf = (ConfigurableListableBeanFactory) rootApplicationContext.getAutowireCapableBeanFactory();
            bf.getBeansOfType(ServletContextAware.class).values().forEach(b -> b.setServletContext(servletContext));
        }
        
        Dynamic servlet = servletContext.addServlet("cxf", new CXFServlet());
        
        servlet.addMapping(SERVLET_PATH);
        servlet.setInitParameter("static-resources-list", "/wsdl-viewer.xsl");
        servlet.setLoadOnStartup(100);
    }
}
