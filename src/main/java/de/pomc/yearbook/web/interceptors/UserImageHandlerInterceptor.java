package de.pomc.yearbook.web.interceptors;

import de.pomc.yearbook.user.User;
import de.pomc.yearbook.user.UserService;
import de.pomc.yearbook.web.exceptions.ForbiddenException;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

@Component
public class UserImageHandlerInterceptor extends HandlerInterceptorAdapter {
    // TODO: MAKE IT WORK - NULL POINTER EXCEPTION ON userService.findCurrentUser
    /*
    public static final String userImageAttributeName = "UserImageHandlerInterceptorUserImage";
    public UserService userService;

    public User getCurrentUser() {
        User user = userService.findCurrentUser(); // NULL POINTER EXCEPTION

        if (user == null) {
            throw new ForbiddenException();
        }

        return user;
    }

    @Override
    public void postHandle(final HttpServletRequest request,
                           final HttpServletResponse response, final Object handler,
                           final ModelAndView modelAndView) throws Exception {

        if (modelAndView != null) {
            modelAndView.getModelMap().
                    addAttribute(userImageAttributeName,
                            getCurrentUser().getImageBase64());
        }
    }

     */
}
