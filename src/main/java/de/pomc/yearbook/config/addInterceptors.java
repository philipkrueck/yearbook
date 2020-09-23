package de.pomc.yearbook.config;

import de.pomc.yearbook.user.UserService;
import de.pomc.yearbook.web.interceptors.LanguageCookieHandlerInterceptor;
import de.pomc.yearbook.web.interceptors.UserImageHandlerInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class addInterceptors implements WebMvcConfigurer {

    @Autowired // not recommended but application crashes otherwise
    UserImageHandlerInterceptor userImageHandlerInterceptor;
    LanguageCookieHandlerInterceptor languageCookieHandlerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserImageHandlerInterceptor());
        registry.addInterceptor(new LanguageCookieHandlerInterceptor());
    }
}
