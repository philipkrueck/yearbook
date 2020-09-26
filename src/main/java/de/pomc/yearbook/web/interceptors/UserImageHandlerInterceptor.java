package de.pomc.yearbook.web.interceptors;

import de.pomc.yearbook.user.User;
import de.pomc.yearbook.user.UserService;
import de.pomc.yearbook.web.exceptions.ForbiddenException;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@Component
@RequiredArgsConstructor
public class UserImageHandlerInterceptor extends HandlerInterceptorAdapter {

    private static final String userImageAttributeName = "currentUserImage";

    private final UserService userService;

    @Override
    public void postHandle(final HttpServletRequest request,
                           final HttpServletResponse response, final Object handler,
                           final ModelAndView modelAndView) throws Exception {

        if (modelAndView != null) {
            User currentUser = userService.findCurrentUser();

            modelAndView.getModelMap().
                    addAttribute(userImageAttributeName,
                            currentUser != null ? currentUser.getImageBase64() : null);
        }
    }

}
