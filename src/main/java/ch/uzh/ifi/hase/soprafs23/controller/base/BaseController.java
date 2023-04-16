package ch.uzh.ifi.hase.soprafs23.controller.base;

import ch.uzh.ifi.hase.soprafs23.Interceptor.SessionInterceptor;
import ch.uzh.ifi.hase.soprafs23.entity.User;



//this is just because I can not change the commit message before, this class is used for corresponding issue

public class BaseController {
    public User getUser(){
        return SessionInterceptor.SESSION_USER.get();
    }
}
