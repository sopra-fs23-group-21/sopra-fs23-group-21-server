package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.core.GameContext;
import ch.uzh.ifi.hase.soprafs23.model.UserVo;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import cn.hutool.extra.spring.SpringUtil;
import com.google.gson.Gson;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@Component
@ServerEndpoint(value = "/ws/ddz/sync/{roomCode}/{token}")
@Data
public class GameSyncController {


    private Session session;

    private UserService userService;

    private UserVo userVo;

    private GameContext gameContext;

    private String token;

    private String roomCode;

    private Gson gson = new Gson();

    public GameSyncController() {
        userService = SpringUtil.getBean(UserService.class);
    }

}