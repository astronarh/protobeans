package org.protobeans.security.config;

import org.protobeans.core.annotation.InjectFrom;
import org.protobeans.core.config.CoreConfig;
import org.protobeans.security.annotation.EnableSecurity;
import org.protobeans.security.util.SecurityUrlsBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.WebApplicationInitializer;

@Configuration
@InjectFrom(EnableSecurity.class)
public class SecurityConfig {
    static {
        CoreConfig.addWebAppContextConfigClass(SecurityWebConfig.class);
    }

    private String[] ignoreUrls;
    
    private String loginUrl;
    
    @Bean
    public SecurityUrlsBean securityUrlsBean() {
        return new SecurityUrlsBean(ignoreUrls, loginUrl);
    }
    
    @Bean
    public Class<? extends WebApplicationInitializer> securityInitializer() {
        return SecurityInitializer.class; 
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
