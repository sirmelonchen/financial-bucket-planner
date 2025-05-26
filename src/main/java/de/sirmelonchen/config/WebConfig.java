package de.sirmelonchen.config;

import de.sirmelonchen.interceptor.SetupInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final SetupInterceptor setupInterceptor;

    public WebConfig(SetupInterceptor setupInterceptor) {
        this.setupInterceptor = setupInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(setupInterceptor).addPathPatterns("/setup/**");
    }
}
