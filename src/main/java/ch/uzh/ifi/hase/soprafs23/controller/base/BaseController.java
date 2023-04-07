package ch.uzh.ifi.hase.soprafs23.controller.base;

import ch.uzh.ifi.hase.soprafs23.Interceptor.SessionInterceptor;
import ch.uzh.ifi.hase.soprafs23.entity.User;

public class BaseController {
    public User getUser(){
        return SessionInterceptor.SESSION_USER.get();
    }
}
