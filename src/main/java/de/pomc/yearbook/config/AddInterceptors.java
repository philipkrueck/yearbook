package de.pomc.yearbook.config;

import de.pomc.yearbook.user.UserService;
import de.pomc.yearbook.web.interceptors.LanguageCookieHandlerInterceptor;
import de.pomc.yearbook.web.interceptors.UserImageHandlerInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class AddInterceptors implements WebMvcConfigurer {

    private final UserService userService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserImageHandlerInterceptor(userService));
        registry.addInterceptor(new LanguageCookieHandlerInterceptor());
    }
}
