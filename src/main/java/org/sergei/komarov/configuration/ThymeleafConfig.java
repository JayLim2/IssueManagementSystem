package org.sergei.komarov.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.UrlTemplateResolver;

@Configuration
public class ThymeleafConfig {
    @Bean
    public SpringSecurityDialect springSecurityDialect(){
        return new SpringSecurityDialect();
    }
}
