package ch.uzh.ifi.hase.soprafs23.controller;


import ch.uzh.ifi.hase.soprafs23.config.WebSocketConfigOne;
import ch.uzh.ifi.hase.soprafs23.core.GameContext;
import ch.uzh.ifi.hase.soprafs23.model.Result;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import lombok.Data;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;

/**
 * the class for room synchronization
 */
@Component
@ServerEndpoint(value = "/ws/room/sync/{token}")
@Data
public class RoomSync {


    private static final Map<String,Session> sessionMap = Maps.newConcurrentMap();

    private String token;

    @OnOpen
    public void onOpen(Session session, @PathParam(value = "token") String token) {
        this.token = token;
        Gson gson = new Gson();

        Result<Collection<GameContext>> success = Result.success(CardsController.GAME_ROOM.values());

        try {
            session.getBasicRemote().sendText(gson.toJson(success));
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }

        sessionMap.put(token,session);

    }

    /**
     * monitor the messages
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
    }

    /**
     * synchronize the room code
     */
    public void push()  {
        Gson gson = new Gson();
        Result<Collection<GameContext>> success = Result.success(CardsController.GAME_ROOM.values());
        WebSocketConfigOne.executor.execute(()-> {
            for (Session value : sessionMap.values()) {
                if (Objects.nonNull(value)) {
                    try {
                        value.getBasicRemote().sendText(gson.toJson(success));
                    }
                    catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    /**
     * actions when closed
     *
     * @param session
     */
    @OnClose
    public void onClose(Session session) {
        sessionMap.put(token,null);

    }
}
