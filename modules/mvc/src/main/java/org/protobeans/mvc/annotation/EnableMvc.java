package org.protobeans.mvc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.protobeans.mvc.config.MvcConfig;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(MvcConfig.class)
@Configuration
public @interface EnableMvc {
    String resourcesPath();
    
    String resourcesUrl();
    
    Class<?>[] basePackageClasses() default {};
}