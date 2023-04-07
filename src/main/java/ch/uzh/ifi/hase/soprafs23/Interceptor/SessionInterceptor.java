package ch.uzh.ifi.hase.soprafs23.Interceptor;

import ch.uzh.ifi.hase.soprafs23.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;

public class SessionInterceptor implements HandlerInterceptor {
    public static final ThreadLocal<User> SESSION_USER = new ThreadLocal<>();
    public static final  String TOKEN_KEY = "Authorization";
}
