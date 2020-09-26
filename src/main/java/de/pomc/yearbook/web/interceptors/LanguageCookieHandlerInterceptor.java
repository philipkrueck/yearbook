package de.pomc.yearbook.web.interceptors;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LanguageCookieHandlerInterceptor extends HandlerInterceptorAdapter {

    private static final String langAttributeName = "language";
    private static final String languageCookieName = "org.springframework.web.servlet.i18n.CookieLocaleResolver.LOCALE";

    private String getlangAttribute(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, languageCookieName);
        return cookie != null ? cookie.getValue() : null;
    }

    @Override
    public void postHandle(final HttpServletRequest request,
                           final HttpServletResponse response, final Object handler,
                           final ModelAndView modelAndView) {

        if (modelAndView != null) {
            modelAndView.getModelMap().addAttribute(langAttributeName, getlangAttribute(request));
        }
    }
}
